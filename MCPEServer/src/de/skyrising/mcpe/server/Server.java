package de.skyrising.mcpe.server;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.*;

import de.skyrising.mcpe.server.entity.*;

public class Server extends Thread
{
    public static Server instance;
    public long started = System.currentTimeMillis();
    private static Level debugLevel = new Level("DEBUG", 200)
    {
	private static final long serialVersionUID = 1L;
    };
    public boolean running;
    public Logger logger;
    public DatagramSocket serverSocket;
    public Random random;
    public long serverId;
    public String serverType;
    public String serverName;
    public Map<InetAddress, EntityPlayer> clients;
    public int maxClients = 20;
    public int mtu = 1447;
    private PacketHandler handler;
    public PcapLogger pcapLogger;
    public PrintStream packetLogger;
    
    private Server(String name, int port) throws SocketException
    {
	instance = this;
	logger = Logger.getLogger("MCPE_Server");
	logger.setLevel(Level.ALL);
	logger.setUseParentHandlers(false);
	for(Handler h : logger.getHandlers())
	    logger.removeHandler(h);
	logger.addHandler(new Handler()
	{
	    DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	    @Override
	    public void publish(LogRecord record)
	    {
		Level l = record.getLevel();
		PrintStream out = l.intValue() > Level.INFO.intValue() ? System.err : System.out;
		out.println(df.format(new Date(record.getMillis())) + " [" + record.getLoggerName() + "]" + 
			"[" + l.getLocalizedName() + "] " + record.getMessage());    
	    }
	    
	    @Override
	    public void flush()
	    {
		System.out.flush();
		System.err.flush();
	    }
	    
	    @Override
	    public void close() throws SecurityException
	    {
		System.out.close();
		System.err.close();
	    }
	});
	random = new Random();
	serverId = 0x372cdc9e;
	serverName = name;
	serverType = "Demo";
	try
	{
	    pcapLogger = new PcapLogger(new FileOutputStream("packets.pcap"));
	    packetLogger = new PrintStream("packets.log");
	} catch(FileNotFoundException e)
	{
	    e.printStackTrace();
	}
	clients = new HashMap<InetAddress, EntityPlayer>();
	serverSocket = new DatagramSocket(port);
	handler = new PacketHandler();
	log("Starting Minecraft PE server on " + serverSocket.getInetAddress()+ ":" + port);
    }
    
    @Override
    public void run()
    {
        running = true;
        while(running)
        {
            DatagramPacket p = new DatagramPacket(new byte[18 + mtu], 18 + mtu);
            try
	    {
		serverSocket.receive(p);
		p.setData(p.getData(), 0, p.getLength());
		pcapLogger.logPacket(p, p.getAddress(), p.getPort(), serverSocket.getLocalAddress(), serverSocket.getLocalPort());
		Utils.dumpPacket(p, p.getAddress(), p.getPort(), serverSocket.getLocalAddress(), serverSocket.getLocalPort(), packetLogger);
		handler.handlePacket(p);
	    } catch(IOException e)
	    {
		e.printStackTrace();
	    }
        }
    }
    
    public void sendToClient(DatagramPacket packet)
    {
	try
	{
	    pcapLogger.logPacket(packet, serverSocket.getLocalAddress(), serverSocket.getLocalPort(), packet.getAddress(), packet.getPort());
	    Utils.dumpPacket(packet, serverSocket.getLocalAddress(), serverSocket.getLocalPort(), packet.getAddress(), packet.getPort(), packetLogger);
	    serverSocket.send(packet);
	} catch(IOException e)
	{
	    e.printStackTrace();
	}
    }
    
    public void log(Level level, String message)
    {
	logger.log(level, message);
    }
    
    public void log(String message)
    {
	log(Level.INFO, message);
    }
    
    public void error(String message)
    {
	log(Level.SEVERE, message);
    }
    
    public void warning(String message)
    {
	log(Level.WARNING, message);
    }
    
    public void debug(String message)
    {
	log(debugLevel, message);
    }
    
    public static void main(String[] args)
    {
	try
	{
	    new Server("SuperCraftServer", 19132).start();
	} catch(SocketException e)
	{
	    e.printStackTrace();
	}
    }
}
