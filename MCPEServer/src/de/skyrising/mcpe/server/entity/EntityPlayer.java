package de.skyrising.mcpe.server.entity;

import java.io.*;
import java.net.*;

import static de.skyrising.mcpe.server.Constants.*;

public class EntityPlayer extends Entity
{
    public long clientID;
    public InetAddress ip;
    public int port;
    public int mtu;
    public boolean loggedIn = false;
    public String username;
    
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
	}
    }
    
    private void sendPacket(byte id, Serializable... data) throws IOException
    {
	ByteArrayOutputStream bo = new ByteArrayOutputStream();
	ObjectOutputStream oo = new ObjectOutputStream(bo);
	oo.write(id);
	for(Serializable s : data)
	    oo.writeObject(s);
    }
}
