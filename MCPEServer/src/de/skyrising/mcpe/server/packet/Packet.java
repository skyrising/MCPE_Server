package de.skyrising.mcpe.server.packet;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;

import de.skyrising.mcpe.server.*;

public abstract class Packet
{
    public static int[] packedIds = new int[0];
    public byte[] data;
    public int port;
    public InetAddress ip;
    
    protected DataInputStream in;
    protected ByteArrayOutputStream bout;
    protected DataOutputStream out;
    protected static Server server = Server.instance;
    
    public Packet(InetAddress ip, int port, byte[] data)
    {
	this.ip = ip;
	this.port = port;
	this.data = data;
	this.in = new DataInputStream(new ByteArrayInputStream(data));
    }
    
    public Packet(InetAddress ip, int port)
    {
	this.ip = ip;
	this.port = port;
	this.bout = new ByteArrayOutputStream();
	this.out = new DataOutputStream(bout);
    }
    
    public void handle() throws Exception
    {
	server.error("Packet type " + this.getClass().getSimpleName() + " cannot be handled");
    }
    
    public void send()
    {
	if(bout != null)
	this.data = bout.toByteArray();
	server.debug("Sending " + this);
	server.sendToClient(new DatagramPacket(data, data.length, ip, port));
	try
	{
	    out.close();
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
    }
    
    public void construct(Object... data)
    {
	server.error("Packet type " + this.getClass().getSimpleName() + " cannot be constructed");
    }
    
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + (data.length > 0 ? String.format(" 0x%02X", data[0]) : "");
    }
    
    public static Packet construct(DatagramPacket packet)
    {
	byte[] data = packet.getData();
	if(data.length == 0)
	{
	    server.warning("Empty packet from " + packet.getAddress() + ":" + packet.getPort());
	    return null;
	}
	Class<? extends Packet> clazz = classes[((int)data[0])&0xFF];
	if(clazz == null)
	{
	    server.warning(String.format("Invalid packet id 0x%02X from %s:%d", ((int)data[0])&0xFF, packet.getAddress(), packet.getPort()));
	    return null;
	}
	try
	{
	    Constructor<? extends Packet> constructor = clazz.getConstructor(InetAddress.class, int.class, byte[].class);
	    Packet p = constructor.newInstance(packet.getAddress(), packet.getPort(), data);
	    server.debug("Recieving " + p);
	    return p;
	} catch(Exception e)
	{
	    server.error(e.toString());
	    return null;
	}
    }
    
    public static void constructAndHandle(DatagramPacket packet) throws Exception
    {
	Packet p = construct(packet);
	if(p != null)
	    p.handle();
    }
    
    @SuppressWarnings("unchecked")
    public static Class<? extends Packet>[] classes = new Class[256];
    
    static
    {
	classes[0x01] = PacketPing.class;
	classes[0x02] = PacketPing.class;
	classes[0x05] = PacketOpenConnectionRequest.class;
	classes[0x06] = PacketOpenConnectionReply.class;
	classes[0x07] = PacketOpenConnectionRequest.class;
	classes[0x08] = PacketOpenConnectionReply.class;
	classes[0xC0] = PacketAck.class;
	classes[0x1A] = PacketIncompatibleProtocolVersion.class;
	classes[0x1C] = PacketPong.class;
	classes[0x1D] = PacketPong.class;
	classes[0x80] = PacketPayload.class;
	classes[0x81] = PacketPayload.class;
	classes[0x82] = PacketPayload.class;
	classes[0x83] = PacketPayload.class;
	classes[0x84] = PacketPayload.class;
	classes[0x85] = PacketPayload.class;
	classes[0x86] = PacketPayload.class;
	classes[0x87] = PacketPayload.class;
	classes[0x88] = PacketPayload.class;
	classes[0x89] = PacketPayload.class;
	classes[0x8A] = PacketPayload.class;
	classes[0x8B] = PacketPayload.class;
	classes[0x8C] = PacketPayload.class;
	classes[0x8D] = PacketPayload.class;
	classes[0x8E] = PacketPayload.class;
	classes[0x8F] = PacketPayload.class;
    }
}
