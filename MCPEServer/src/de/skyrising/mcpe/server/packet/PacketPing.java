package de.skyrising.mcpe.server.packet;

import java.io.IOException;
import java.net.InetAddress;

import de.skyrising.mcpe.server.Constants;

public class PacketPing extends Packet
{
    public static int[] packedIds = new int[] { 0x01, 0x02 };
    private long pingId;
    private long magic1;
    private long magic2;

    public PacketPing(InetAddress from, int port, byte[] data)
    {
	super(from, port, data);
    }

    @Override
    public void handle() throws IOException
    {
	byte pid = in.readByte();
	if(!(pid == 1 || pid == 2))
	{
	    server.error(String.format("%s got wrong packet id: %u02X", this.getClass().getSimpleName(), pid));
	    in.close();
	    return;
	}
	pingId = in.readLong();
	magic1 = in.readLong();
	magic2 = in.readLong();
	boolean magic = magic1 == Constants.RAKNET_MAGIC_1 && magic2 == Constants.RAKNET_MAGIC_2;
	//server.debug("Recieved ping from " + ip + ":" + port + ", packet-id: " + pid + ", ping-id: " + pingId + (magic ? ", magic matched" : ", wrong magic: 0x" + String.format("%08X%08X", magic1, magic2)));
	PacketPong response = new PacketPong(ip, port);
	response.construct((byte) 0x1C, pingId);
	response.send();
	in.close();
    }
}
