package worlds;

import graphic.SpriteBank;
import handleds.Drawable;
import handleds.Handled;
import handleds.LogicalHandled;
import handlers.Handler;
import handlers.RoomListenerHandler;

import java.util.ArrayList;

import listeners.RoomListener;

import common.GameObject;

import backgrounds.Background;
import backgrounds.TileMap;

/**
 * Room represents a single restricted area in a game. A room contains a 
 * background and a tilemap as well as a group of objects. A room can start 
 * and end and it will inform objects about such events.
 *
 *
 * @author Gandalf.
 *         Created 11.7.2013.
 */
public class Room extends Handler
{	
	// ATTRIBUTES	-----------------------------------------------------
	
	private ArrayList<Background> backgrounds;
	private ArrayList<SpriteBank> texturebanks;
	private ArrayList<String> texturenames;
	private TileMap tilemap;
	private RoomListenerHandler listenerhandler;
	//private int width, height;
	private boolean active;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new room, filled with backgrounds, tiles and objects. 
	 * The room will remain inactive until started.
	 *
	 * @param backgrounds The background(s) used in the room. Use empty list or 
	 * null if no backgrounds will be used. The backgrounds should cover the room's 
	 * area that is not covered by tiles.
	 * @param tilemap The tilemap used in the room (null if no tiles are used)
	 * @param tiletexturebanks A list of spritebanks that contained the textures 
	 * used in the tilemap
	 * @param tiletexturenames A list of the names of the textures used in the 
	 * tilemap 
	 */
	public Room(ArrayList<Background> backgrounds, 
			TileMap tilemap, ArrayList<SpriteBank> tiletexturebanks, 
			ArrayList<String> tiletexturenames)
	{
		// Rooms aren't handled by anything by default
		super(false, null);
		
		// Initializes attributes
		//this.width = width;
		//this.height = height;
		this.tilemap = tilemap;
		this.backgrounds = backgrounds;
		this.active = true;
		this.texturebanks = tiletexturebanks;
		this.texturenames = tiletexturenames;
		this.listenerhandler = new RoomListenerHandler(false, null);
		
		uninitialize();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	protected Class<?> getSupportedClass()
	{
		return GameObject.class;
	}
	
	@Override
	public boolean kill()
	{
		// In addition to the normal killing process, kills the tilemap and 
		// backgrounds as well
		if (this.backgrounds != null)
		{
			for (int i = 0; i < this.backgrounds.size(); i++)
				this.backgrounds.get(i).kill();
			
			this.backgrounds.clear();
			this.backgrounds = null;
		}
		if (this.tilemap != null)
		{
			this.tilemap.kill();
			this.tilemap = null;
		}
		
		return super.kill();
	}
	
	
	// GETTERS & SETTERS	---------------------------------------------
	
	/*
	/**
	 * Changes the size of the room
	 *
	 * @param width The room's new width (in pixels)
	 * @param height The room's new height (in pixels)
	 */
	/*
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @return The room's width (in pixels)
	 */
	/*
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * @return The room's height (in pixels)
	 */
	/*
	public int getHeight()
	{
		return this.height;
	}
	*/
	/**
	 * Changes the backgrounds shown in the room
	 *
	 * @param backgrounds The new background(s) shown in the room (null if 
	 * no background is used in the room)
	 */
	public void setBackgrounds(ArrayList<Background> backgrounds)
	{
		this.backgrounds = backgrounds;
	}
	
	/**
	 * @return The current background(s) shown in the room
	 */
	public ArrayList<Background> getBackgrounds()
	{
		return this.backgrounds;
	}
	
	/**
	 * @return The tilemap used in the room
	 */
	public TileMap getTiles()
	{
		return this.tilemap;
	}
	
	/**
	 * Changes the room's tilemap
	 *
	 * @param tiles The new tilemap used in the room (null if no tiles are used)
	 */
	public void setTiles(TileMap tiles)
	{
		this.tilemap = tiles;
	}
	
	/**
	 * @return Is the room currently in use.
	 */
	public boolean isActive()
	{
		return this.active && !isDead();
	}
	
	
	// OTHER METHODS	-------------------------------------------------
	
	/**
	 * Adds a new object to the room
	 *
	 * @param g The object to be added
	 */
	public void addOnject(GameObject g)
	{
		addHandled(g);
		// If the object is a roomlistener, adds it to the listenerhandler as well
		if (g instanceof RoomListener)
			this.listenerhandler.addRoomListener((RoomListener) g);
	}
	
	/**
	 * Removes a gameobject from the room
	 *
	 * @param g The gameobject to be removed
	 */
	public void removeObject(GameObject g)
	{
		removeHandled(g);
		// If the object was a roomlistener, removes it from there as well
		if (g instanceof RoomListener)
			this.listenerhandler.removeRoomListener((RoomListener) g);
	}
	
	/**
	 * Starts the room, activating all the objects and backgrounds in it
	 */
	public void start()
	{
		// If the room had already been started, nothing happens
		if (this.active)
			return;
		
		this.active = true;
		initialize();
		
		// Informs the listeners about the event
		this.listenerhandler.onRoomStart(this);
	}
	
	/**
	 * Ends the room, deactivating all the objects and backgrounds in it
	 */
	public void end()
	{
		// If the room had already been ended, nothing happens
		if (!this.active)
			return;
		
		// Informs the listeners about the event
		this.listenerhandler.onRoomEnd(this);
		
		this.active = false;
		uninitialize();
	}
	
	private void initialize()
	{
		// Sets the backgrounds visible and animated
		if (this.backgrounds != null)
		{
			for (int i = 0; i < this.backgrounds.size(); i++)
			{
				Background b = this.backgrounds.get(i);
				b.setVisible();
				b.getSpriteDrawer().activate();
			}
		}
		// Initializes the tilemap
		if (this.tilemap != null)
			this.tilemap.initialize(this.texturebanks, this.texturenames);
		// Activates all the objects and sets them visible (if applicable)
		for (int i = 0; i < getHandledNumber(); i++)
		{
			Handled h = getHandled(i);
			
			if (h instanceof Drawable)
				((Drawable) h).setVisible();
			if (h instanceof LogicalHandled)
				((LogicalHandled) h).activate();
		}
	}
	
	private void uninitialize()
	{
		// Sets the backgrounds invisible and unanimated
		if (this.backgrounds != null)
		{
			for (int i = 0; i < this.backgrounds.size(); i++)
			{
				Background b = this.backgrounds.get(i);
				b.setInvisible();
				
				if (b.getSpriteDrawer() != null)
					b.getSpriteDrawer().inactivate();
			}
		}
		// Clears the tilemap
		if (this.tilemap != null)
			this.tilemap.clear();
		// InActivates all the objects and sets them invisible (if applicable)
		for (int i = 0; i < getHandledNumber(); i++)
		{
			Handled h = getHandled(i);
			
			if (h instanceof Drawable)
				((Drawable) h).setInvisible();
			if (h instanceof LogicalHandled)
				((LogicalHandled) h).inactivate();
		}
	}
}
