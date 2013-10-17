package de.skyrising.mcpe.server;

import java.io.*;
import java.net.*;

public class PcapLogger
{
    private DataOutputStream out;
    private long startMillis;
    private long startNanos;
    private boolean init = false;
    
    public PcapLogger(OutputStream out)
    {
	this.out = new DataOutputStream(out);
	startMillis = System.currentTimeMillis();
	startNanos = System.nanoTime();
    }
    
    public void init()
    {
	if(init)return;
	try
	{
	    out.writeInt(0xa1b2c3d4);//Using microsecond resolution because most Java implementations return nanoTime() in steps of 1000
	    out.writeShort(2);//Version 2.4
	    out.writeShort(4);
	    out.writeInt(0);//Writing in UTC timezone
	    out.writeInt(0);//Everyone does so
	    out.writeInt(0xFFFF);//Capturing UDP so MAX_PACKET_LENGTH is 65535
	    out.writeInt(101);//Will write packets as IP packets
	    out.flush();
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
	init = true;
    }
    
    public void logPacket(DatagramPacket packet, InetAddress src, int srcport, InetAddress dst, int dstport)
    {
	if(!init)
	    init();
	int ts_sec = (int)((System.currentTimeMillis())/1000);
	int ts_usec = (int)((System.nanoTime())/1000)%1000000;
	try
	{
	    out.writeInt(ts_sec);
	    out.writeInt(ts_usec);
	    byte[] bytes = encapsulateIP(packet, src, srcport, dst, dstport);
	    out.writeInt(bytes.length&0xFFFF);
	    out.writeInt(bytes.length);
	    out.flush();
	    if(bytes.length > 0)
	    out.write(bytes, 0, bytes.length&0xFFFF);
	    out.flush();
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
    }
    
    private byte[] encapsulateIP(DatagramPacket packet, InetAddress src, int srcport, InetAddress dst, int dstport) throws IOException
    {
	boolean ipv4 = src instanceof Inet4Address;
	int data_length = packet.getLength();
	int length = data_length + (ipv4 ? 20 : 40) + 8;
	ByteArrayOutputStream bytes = new ByteArrayOutputStream(length);
	DataOutputStream tout = new DataOutputStream(bytes);
	//IP Header
	byte[] ip_header = new byte[0];
	if(ipv4)
	{
	     ip_header = new byte[]{	0x45,0x00,(byte)((length<<8)&0xFF),(byte)(length&0xFF), 
		    			0x00, 0x00, 0x00, 0x00, (byte)0xFF, 0x11, 0x00, 0x00,
		    			0,0,0,0,//src ip
		    			0,0,0,0//dst ip
		    			};
	    System.arraycopy(src.getAddress(), 0, ip_header, 12, 4);
	    System.arraycopy(dst.getAddress(), 0, ip_header, 16, 4);
	    int checksum = 0;
	    for(int i = 0; i < ip_header.length - 1; i += 2)
	    {
		checksum += ip_header[i] << 8 | ip_header[i+1];
	    }
	    checksum&=0xFFFF;
	    checksum+=2;
	    checksum = ~checksum;
	    ip_header[10] = (byte) ((checksum>>8)&0xFF);
	    ip_header[11] = (byte) (checksum&0xFF);
	    tout.write(ip_header);
	}else
	{
	    return new byte[0];//TODO: IPv6
	}
	//UDP Header
	tout.writeShort(srcport);
	tout.writeShort(dstport);
	tout.writeShort(data_length);
	tout.writeShort(0);
	
	tout.write(packet.getData());
	
	return bytes.toByteArray();
    }
}
