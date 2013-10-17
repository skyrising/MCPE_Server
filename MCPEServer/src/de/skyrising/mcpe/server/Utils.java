package de.skyrising.mcpe.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class Utils
{
    public static void writeString(String s, DataOutput out) throws IOException
    {
	out.writeShort(s.length());
	out.writeBytes(s);
    }

    public static String toHex(byte[] bytes)
    {
	StringBuilder sb = new StringBuilder();
	for(byte b : bytes)
	{
	    sb.append(String.format(sb.length() == 0 ? "%02X" : " %02X", b));
	}
	return sb.toString();
    }
    
    public static String hexdump(byte[] bytes)
    {
	StringBuilder sb = new StringBuilder();
	for(int addr = 0; addr < bytes.length; addr++)
	{
	    if((addr&0xF) == 0)
	    {
		if(addr != 0)
		    sb.append('\n');
		sb.append(String.format("%08X: ", addr));
	    }
	    sb.append(String.format("%02X", bytes[addr]));
	    if((addr&0xF) != 15)
		sb.append(' ');
	}
	return sb.toString();
    }
    
    public static void dumpPacket(DatagramPacket p, InetAddress src, int srcport, InetAddress dst, int dstport, PrintStream out)
    {
	out.println("Timestamp  : " + new SimpleDateFormat().format(new Date()));
	out.println("Source     : " + src + ":" + srcport);
	out.println("Destination: " + dst + ":" + dstport);
	out.println("Data:");
	out.println(hexdump(p.getData()));
    }
}
