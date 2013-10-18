package de.skyrising.mcpe.server.entity;

import java.io.*;
import java.net.*;

import de.skyrising.mcpe.server.Server;
import de.skyrising.mcpe.server.Utils;
import de.skyrising.mcpe.server.packet.PacketPayload;

import static de.skyrising.mcpe.server.Constants.*;

public class EntityPlayer extends Entity
{
    public long clientID;
    public InetAddress ip;
    public int port;
    public int mtu;
    public boolean loggedIn = false;
    public String username;
    public int count = 0;
    private byte[] realmsData;
    private static Server server = Server.instance;
    
    public EntityPlayer(long clientID, InetAddress ip, int port, int mtu)
    {
	this.clientID = clientID;
	this.ip = ip;
	this.port = port;
	this.mtu = mtu;
    }

    public void handleDataPacket(byte[] payload, DataInputStream in) throws IOException
    {
	switch(in.readByte())
	{
	    case MC_PONG:
		break;
	    case MC_PING:
	    {
		long ptime = in.readLong();
		long time = System.currentTimeMillis();
		sendPacket(MC_PONG, ptime, time);
		break;
	    }
	    case MC_CLIENT_CONNECT:
	    {
		if(loggedIn)break;
		long clientID = in.readLong();
		long session = in.readLong();
		sendPacket(MC_SERVER_HANDSHAKE, 0x043f57f3, (byte)0xcd, (short)this.port, 
				new byte[]{(byte)0xf5,-1,-1,(byte)0xf5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
				session, new byte[]{0x00,0x00,0x00,0x00,0x04,0x44,0x0b,(byte)0xa9});
		break;
	    }
	    case MC_CLIENT_HANDSHAKE:
		break;
	    case MC_LOGIN:
	    {
		if(loggedIn)break;
		String username = Utils.readString(in);
		int protocol1 = in.readInt();
		int protocol2 = in.readInt();
		int clientId = in.readInt();
		realmsData = new byte[in.readShort()];
		in.read(realmsData);
		if(server.clients.size() >= server.maxClients)
		{
		    close("Server is full!", false);
		    break;
		}
		if(protocol1 != PROTOCOL_VERSION)
		{
		    sendPacket(MC_LOGIN_STATUS, protocol1 < PROTOCOL_VERSION ? 1 : 2);
		    close("Incorrect protocol#"+protocol1, false);
		    break;
		}
		if(username.matches("[a-zA-Z0-9_]+") && username.length() > 0)
		    this.username = username;
		else
		{
		    close("Bad Username", false);
		    break;
		}
		if(server.hasWhitelist && !server.whitelist.contains(username))
		{
		    close("Server is white-listed", false);
		    break;
		}
		if(server.banned.contains(username) || server.bannedIps.contains(ip))
		{
		    close("You are banned!", false);
		    break;
		}
		loggedIn = true;
		EntityPlayer p1 = server.players.get(username);
		if(p1 != null)
		    p1.close("logged in from another location", true);
		
		server.players.put(username, this);
		server.log(username + " logged in from " + ip + ":" + port);
		sendPacket(MC_LOGIN_STATUS, 0);
	    }
	}
    }
    
    public void close(String reason, boolean message)
    {
	
    }
    
    public void sendChat(String message, String author)
    {
	
    }
    
    public void sendChat(String message)
    {
	
    }
    
    private void sendPacket(byte id, Object... data) throws IOException
    {
	ByteArrayOutputStream bo = new ByteArrayOutputStream();
	DataOutputStream out = new DataOutputStream(bo);
	out.write(id);
	for(Object o : data)
	{
	    if(o == null)
		throw new IOException("Object is null");
	    if(o instanceof String)
		Utils.writeString((String)o, out);
	    else if(o instanceof Byte)
		out.writeLong((Byte)o);
	    else if(o instanceof Short)
		out.writeShort((Short)o);
	    else if(o instanceof Integer)
		out.writeInt((Integer)o);
	    else if(o instanceof Long)
		out.writeLong((Long)o);
	    else if(o instanceof Float)
		out.writeFloat((Float)o);
	    else if(o instanceof Double)
		out.writeDouble((Double)o);
	    else if(o instanceof Character)
		out.writeChar((Character)o);
	    else if(o instanceof byte[])
		out.write((byte[])o);
	    else
		throw new IOException("Invalid type " + o.getClass().getName());
	}
	PacketPayload p = new PacketPayload(ip, port);
	p.construct(count++, bo.toByteArray());
	p.send();
    }
}
