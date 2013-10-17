package de.skyrising.mcpe.server;

import java.io.IOException;
import java.net.DatagramPacket;

import de.skyrising.mcpe.server.packet.Packet;

public class PacketHandler
{
    public void handlePacket(DatagramPacket packet)
    {
	try
	{
	    Packet.constructAndHandle(packet);
	} catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
}
