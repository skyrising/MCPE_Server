package de.skyrising.mcpe.server;

public class Constants
{
    //RakNet
    public static final long RAKNET_MAGIC_1 = 0x00ffff00fefefefeL;
    public static final long RAKNET_MAGIC_2 = 0xfdfdfdfd12345678L;
    public static final byte[] RAKNET_MAGIC = new byte[]{	 0,-1,-1, 0,  -2,  -2,  -2,  -2,
								-3,-3,-3,-3,0x12,0x34,0x56,0x78};
    public static final byte RAKNET_VERSION = 5;
    
    
    //Minecraft Protocol
    public static final byte PROTOCOL_VERSION = 12;
    
    public static final byte MC_PING = (byte)0x00;
    public static final byte MC_PONG = (byte)0x03;

    public static final byte MC_CLIENT_CONNECT = (byte)0x09;
    
    public static final byte MC_SERVER_HANDSHAKE = (byte)0x10;
    public static final byte MC_CLIENT_HANDSHAKE = (byte)0x13;

    public static final byte MC_SERVER_FULL = (byte)0x14;
    public static final byte MC_DISCONNECT = (byte)0x15;
    public static final byte MC_BANNED = (byte)0x17;

    public static final byte MC_LOGIN = (byte)0x82;
    public static final byte MC_LOGIN_STATUS = (byte)0x83;
    public static final byte MC_READY = (byte)0x84;
    public static final byte MC_CHAT = (byte)0x85;
    public static final byte MC_SET_TIME = (byte)0x86;
    public static final byte MC_START_GAME = (byte)0x87;
    
    public static final byte MC_ADD_MOB = (byte)0x88;
    public static final byte MC_ADD_PLAYER = (byte)0x89;
    public static final byte MC_REMOVE_PLAYER = (byte)0x8a;
    public static final byte MC_ADD_ENTITY = (byte)0x8c;
    public static final byte MC_REMOVE_ENTITY = (byte)0x8d;
    public static final byte MC_ADD_ITEM_ENTITY = (byte)0x8e;
    public static final byte MC_TAKE_ITEM_ENTITY = (byte)0x8f;
    public static final byte MC_MOVE_ENTITY = (byte)0x90;
    public static final byte MC_MOVE_ENTITY_POSROT = (byte)0x93;
    public static final byte MC_MOVE_PLAYER = (byte)0x94;
    
    public static final byte MC_PLACE_BLOCK = (byte)0x95;
    public static final byte MC_REMOVE_BLOCK = (byte)0x96;
    public static final byte MC_UPDATE_BLOCK = (byte)0x97;
    public static final byte MC_ADD_PAINTING = (byte)0x98;
    public static final byte MC_EXPLOSION = (byte)0x99;
    public static final byte MC_LEVEL_EVENT = (byte)0x9a;
    public static final byte MC_TILE_EVENT = (byte)0x9b;
    public static final byte MC_ENTITY_EVENT = (byte)0x9c;
    
    public static final byte MC_REQUEST_CHUNK = (byte)0x9d;
    public static final byte MC_CHUNK_DATA = (byte)0x9e;
    public static final byte MC_PLAYER_EQUIPMENT = (byte)0x9f;
    public static final byte MC_PLAYER_ARMOR_EQUIPMENT = (byte)0xa0;
    public static final byte MC_INTERACT = (byte)0xa1;
    public static final byte MC_USE_ITEM = (byte)0xa2;
    public static final byte MC_PLAYER_ACTION = (byte)0xa3;

    public static final byte MC_HURT_ARMOR = (byte)0xa5;
    public static final byte MC_SET_ENTITY_DATA = (byte)0xa6;
    public static final byte MC_SET_ENTITY_MOTION = (byte)0xa7;
    public static final byte MC_SET_RIDING_PACKET = (byte)0xa8;
    public static final byte MC_SET_HEALTH = (byte)0xa9;
    public static final byte MC_SET_SPAWN_POSITION = (byte)0xaa;
    public static final byte MC_ANIMATE = (byte)0xab;
    public static final byte MC_RESPAWN = (byte)0xac;
    public static final byte MC_SEND_INVENTORY = (byte)0xad;
    public static final byte MC_DROP_ITEM = (byte)0xae;
    public static final byte MC_CONTAINER_OPEN = (byte)0xaf;
    public static final byte MC_CONTAINER_CLOSE = (byte)0xb0;
    public static final byte MC_CONTAINER_SET_SLOT = (byte)0xb1;
    public static final byte MC_CONTAINER_SET_DATA = (byte)0xb2;
    public static final byte MC_CONTAINER_SET_CONTENT = (byte)0xb3;
    public static final byte MC_CONTAINER_ACK = (byte)0xb4;
    public static final byte MC_CLIENT_MESSAGE = (byte)0xb5;
    public static final byte MC_ADVENTURE_SETTINGS = (byte)0xb6;
    public static final byte MC_ENTITY_DATA = (byte)0xb7;
    
    public static final String[] MC_NAMES = new String[256];
    
    static
    {
	MC_NAMES[((int)MC_PING)&0xFF] = "Ping";
	MC_NAMES[((int)MC_PONG)&0xFF] = "Pong";
	MC_NAMES[((int)MC_CLIENT_CONNECT)&0xFF] = "Client Connect";
	MC_NAMES[((int)MC_SERVER_HANDSHAKE)&0xFF] = "Server Handshake";
	MC_NAMES[((int)MC_CLIENT_HANDSHAKE)&0xFF] = "Client Handshake";
	MC_NAMES[((int)MC_SERVER_FULL)&0xFF] = "Server Full";
	MC_NAMES[((int)MC_DISCONNECT)&0xFF] = "Disconnect";
	MC_NAMES[((int)MC_BANNED)&0xFF] = "Banned";
	MC_NAMES[((int)MC_LOGIN)&0xFF] = "Login";
	MC_NAMES[((int)MC_LOGIN_STATUS)&0xFF] = "Login Status";
	MC_NAMES[((int)MC_READY)&0xFF] = "Ready";
	MC_NAMES[((int)MC_CHAT)&0xFF] = "Chat";
	MC_NAMES[((int)MC_SET_TIME)&0xFF] = "Set Time";
	MC_NAMES[((int)MC_START_GAME)&0xFF] = "Start Game";
	MC_NAMES[((int)MC_ADD_MOB)&0xFF] = "Add Mob";
	MC_NAMES[((int)MC_ADD_PLAYER)&0xFF] = "Add Player";
	MC_NAMES[((int)MC_REMOVE_PLAYER)&0xFF] = "Remove Player";
	MC_NAMES[((int)MC_ADD_ENTITY)&0xFF] = "Add Entity";
	MC_NAMES[((int)MC_REMOVE_ENTITY)&0xFF] = "Remove Entity";
	MC_NAMES[((int)MC_ADD_ITEM_ENTITY)&0xFF] = "Add Item";
	MC_NAMES[((int)MC_TAKE_ITEM_ENTITY)&0xFF] = "Take Item";
	MC_NAMES[((int)MC_MOVE_ENTITY)&0xFF] = "Move Entity";
	MC_NAMES[((int)MC_MOVE_ENTITY_POSROT)&0xFF] = "Move Entity PosRot";
	MC_NAMES[((int)MC_MOVE_PLAYER)&0xFF] = "Move Player";
	MC_NAMES[((int)MC_PLACE_BLOCK)&0xFF] = "Place Block";
	MC_NAMES[((int)MC_REMOVE_BLOCK)&0xFF] = "Remove Block";
	MC_NAMES[((int)MC_UPDATE_BLOCK)&0xFF] = "Update Block";
	MC_NAMES[((int)MC_ADD_PAINTING)&0xFF] = "Add Painting";
	MC_NAMES[((int)MC_EXPLOSION)&0xFF] = "Explosion";
	MC_NAMES[((int)MC_LEVEL_EVENT)&0xFF] = "Level Event";
	MC_NAMES[((int)MC_TILE_EVENT)&0xFF] = "Tile Event";
	MC_NAMES[((int)MC_ENTITY_EVENT)&0xFF] = "Entity Event";
	MC_NAMES[((int)MC_REQUEST_CHUNK)&0xFF] = "Chunk Request";
	MC_NAMES[((int)MC_CHUNK_DATA)&0xFF] = "Chunk Data";
	MC_NAMES[((int)MC_PLAYER_EQUIPMENT)&0xFF] = "Player Equipment";
	MC_NAMES[((int)MC_PLAYER_ARMOR_EQUIPMENT)&0xFF] = "Player Armor";
	MC_NAMES[((int)MC_INTERACT)&0xFF] = "Interact";
	MC_NAMES[((int)MC_USE_ITEM)&0xFF] = "Use Item";
	MC_NAMES[((int)MC_PLAYER_ACTION)&0xFF] = "Player Action";
	MC_NAMES[((int)MC_HURT_ARMOR)&0xFF] = "Hurt Armor";
	MC_NAMES[((int)MC_SET_ENTITY_DATA)&0xFF] = "Entity Data";
	MC_NAMES[((int)MC_SET_ENTITY_MOTION)&0xFF] = "Entity Motion";
	MC_NAMES[((int)MC_SET_RIDING_PACKET)&0xFF] = "Riding";
	MC_NAMES[((int)MC_SET_HEALTH)&0xFF] = "Set Health";
	MC_NAMES[((int)MC_SET_SPAWN_POSITION)&0xFF] = "Set Spawn Position";
	MC_NAMES[((int)MC_ANIMATE)&0xFF] = "Animate";
	MC_NAMES[((int)MC_RESPAWN)&0xFF] = "Respawn";
	MC_NAMES[((int)MC_SEND_INVENTORY)&0xFF] = "Send Inventory";
	MC_NAMES[((int)MC_DROP_ITEM)&0xFF] = "Drop Item";
	MC_NAMES[((int)MC_CONTAINER_OPEN)&0xFF] = "Open Container";
	MC_NAMES[((int)MC_CONTAINER_CLOSE)&0xFF] = "Close Container";
	MC_NAMES[((int)MC_CONTAINER_SET_SLOT)&0xFF] = "Set Container Slot";
	MC_NAMES[((int)MC_CONTAINER_SET_DATA)&0xFF] = "Set Container Data";
	MC_NAMES[((int)MC_CONTAINER_SET_CONTENT)&0xFF] = "Set Container Content";
	MC_NAMES[((int)MC_CONTAINER_ACK)&0xFF] = "Container Ack";
	MC_NAMES[((int)MC_CLIENT_MESSAGE)&0xFF] = "Client Message";
	MC_NAMES[((int)MC_ADVENTURE_SETTINGS)&0xFF] = "Adventure Settings";
	MC_NAMES[((int)MC_ENTITY_DATA)&0xFF] = "Entity Data";
	for(int i = 0; i < MC_NAMES.length; i++)
	{
	    if(MC_NAMES[i] == null)
		MC_NAMES[i] = "Unknown";
	}
    }
}
