package de.skyrising.mcpe.server.packet;

import java.io.IOException;
import java.net.InetAddress;

public class PacketAck extends Packet
{
    public static byte[] packetIds = new byte[]{(byte) 0xC0};

    public PacketAck(InetAddress ip, int port)
    {
	super(ip, port);
    }
    
    public PacketAck(InetAddress ip, int port, byte[] data)
    {
	super(ip, port, data);
    }
    
    @Override
    public void handle() throws Exception
    {
    }
    
    @Override
    public void construct(Object... data)
    {
	try
	{
	    out.write(0xC0);
	    out.writeShort(1);
	    out.write(((int)data[0])&0xFFFFFF | 0x01000000);
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
    }
}
