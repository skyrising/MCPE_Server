package de.skyrising.mcpe.server.packet;

import java.io.IOException;
import java.net.InetAddress;

import de.skyrising.mcpe.server.Constants;
import de.skyrising.mcpe.server.Utils;

public class PacketOpenConnectionReply extends Packet
{
    public static byte[] packetIds = new byte[]{0x06,0x08};

    public PacketOpenConnectionReply(InetAddress ip, int port)
    {
	super(ip, port);
    }

    @Override
    public void construct(Object... data)
    {
	try
	{
            out.writeByte(((Boolean)data[0]) ? 8 : 6);
	    out.write(Constants.RAKNET_MAGIC);
	    if((Boolean)data[0])
	    {
		out.writeLong(server.serverId);
		out.writeShort((Short)data[1]);
		out.writeShort((Short)data[2]);
		out.write(0);
	    }else
	    {
		out.writeLong(server.serverId);
		out.write(0);
		out.writeShort((Short)data[1]);
	    }
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
    }
}
