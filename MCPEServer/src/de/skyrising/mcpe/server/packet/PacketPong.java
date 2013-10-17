package de.skyrising.mcpe.server.packet;

import java.io.IOException;
import java.net.InetAddress;

import de.skyrising.mcpe.server.Constants;
import de.skyrising.mcpe.server.Utils;

public class PacketPong extends Packet
{
    public static int[] packedIds = new int[]{0x1C,0x1D};
    
    public PacketPong(InetAddress from, int port)
    {
	super(from, port);
    }
    
    @Override
    public void construct(Object... data)
    {
        try
	{
            out.writeByte((Byte)data[0]);
	    out.writeLong((Long)data[1]);
	    out.writeLong(server.serverId);
	    out.write(Constants.RAKNET_MAGIC);
	    Utils.writeString("MCCPP;" + server.serverType + ";" + server.serverName + " [" + server.clients.size() + "/" + server.maxClients + "]", out);
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
    }
}
