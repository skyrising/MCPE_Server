package de.skyrising.mcpe.server.packet;

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
}
