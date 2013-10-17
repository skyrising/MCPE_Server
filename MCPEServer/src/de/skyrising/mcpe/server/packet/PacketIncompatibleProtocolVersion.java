package de.skyrising.mcpe.server.packet;

import java.io.IOException;
import java.net.InetAddress;

import de.skyrising.mcpe.server.Constants;

public class PacketIncompatibleProtocolVersion extends Packet
{

    public PacketIncompatibleProtocolVersion(InetAddress ip, int port)
    {
	super(ip, port);
    }

    @Override
    public void construct(Object... data)
    {
	try
	{
            out.writeByte((Byte)Constants.RAKNET_VERSION);
	    out.write(Constants.RAKNET_MAGIC);
	    out.writeLong(server.serverId);
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
    }
}
