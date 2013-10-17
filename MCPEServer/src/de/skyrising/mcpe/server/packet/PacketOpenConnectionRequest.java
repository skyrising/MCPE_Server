package de.skyrising.mcpe.server.packet;

import java.net.InetAddress;

import de.skyrising.mcpe.server.Constants;
import de.skyrising.mcpe.server.entity.EntityPlayer;

public class PacketOpenConnectionRequest extends Packet
{
    public static int[] packedIds = new int[]{0x05,0x07};

    public PacketOpenConnectionRequest(InetAddress ip, int port, byte[] data)
    {
	super(ip, port, data);
    }
    
    @Override
    public void handle() throws Exception
    {
	byte pid = in.readByte();
	if(!(pid == 5 || pid == 7))
	{
	    server.error(String.format("%s got wrong packet id: %u02X", this.getClass().getSimpleName(), pid));
	    in.close();
	    return;
	}
	if(pid == 5)
	{
	    in.readLong();
	    in.readLong();
	    byte version = in.readByte();
	    if(version != Constants.RAKNET_VERSION)
	    {
		Packet ipv = new PacketIncompatibleProtocolVersion(ip, port);
		ipv.construct();
		ipv.send();
		in.close();
		return;
	    }
	    short mtu = (short) (data.length - 18);
	    Packet reply = new PacketOpenConnectionReply(ip, port);
	    reply.construct(false, mtu);
	    reply.send();
	}else if(pid == 7)
	{
	    in.readLong();
	    in.readLong();
	    in.readByte();
	    in.readInt();
	    in.readShort();
	    short mtu = in.readShort();
	    long clientID = in.readLong();
	    server.clients.put(ip, new EntityPlayer(clientID, ip, port, mtu));
	    server.log(String.format("%016X logged in from %s:%d with mtu %d", clientID, ip, port, mtu));
	    Packet reply = new PacketOpenConnectionReply(ip, port);
	    reply.construct(true, (short)port, mtu);
	    reply.send();
	}
    }

}
