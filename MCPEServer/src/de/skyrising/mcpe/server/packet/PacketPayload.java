package de.skyrising.mcpe.server.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;

import de.skyrising.mcpe.server.Constants;
import de.skyrising.mcpe.server.entity.EntityPlayer;


public class PacketPayload extends Packet
{
    public static int[] packetIds = new int[]{0x80,0x81,0x82,0x83,0x84,0x85,0x86,0x87,0x88,0x89,0x8A,0x8B,0x8C,0x8D,0x8E,0x8F};
    private DataInputStream payloadIn;
    byte[] payload;
    
    public PacketPayload(InetAddress ip, int port, byte[] data)
    {
	super(ip, port, data);
    }

    public PacketPayload(InetAddress ip, int port)
    {
	super(ip, port);
    }

    @Override
    public void handle() throws Exception
    {
	int pid = in.readByte();
	int count = in.readShort()<<8;
	count |= ((int)in.readByte())&0xFF;
	//if(pid < 0x80 || pid > 0x8F)return;
	byte encapsulationId = in.readByte();
	short length = (short) (in.readShort()/8);
	if(length == 0)
	{
	    server.warning(String.format("Recieved PacketPayload with 0 length (Packet id 0x%02X, Encapsulation 0x%02X, count 0x%06X)", pid, encapsulationId, count));
	    return;
	}
	payload = new byte[length];
	if(encapsulationId == 0x40 || encapsulationId == 0x60)
	{
	    in.readByte();
	    in.readByte();
	    in.readByte();
	    if(encapsulationId == 0x60)
	    {
		in.readInt();
	    }
	}
	in.read(payload);
	in.close();
	server.debug("Payload: " + Constants.MC_NAMES[((int)payload[0])&0xFF]);
	payloadIn = new DataInputStream(new ByteArrayInputStream(payload));
	EntityPlayer player = server.clients.get(ip.hashCode() + port);
	if(player == null)
	{
	    payloadIn.close();
	    server.warning("No player from " + ip + ":" + port + " exists. Packet dropped.");
	    return;
	}
	player.count = count;
	player.handleDataPacket(payload, payloadIn);
	payloadIn.close();
    }
    
    @Override
    public void construct(Object... data)
    {
        byte[] b = (byte[])data[1];
        int count = (short)(int)data[0];
        try
	{
	    out.writeInt(count | 0x80000000);
	    out.write(0x00);
	    out.writeShort(b.length);
	    out.write(b);
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
        
    }
}
