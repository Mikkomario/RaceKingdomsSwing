package worlds;

import graphic.SpriteBank;

import java.util.ArrayList;

import backgrounds.Background;
import backgrounds.TileMap;

/**
 * Room represents a single restricted area in a game. A room contains a 
 * background and a tilemap as well as a group of objects. A room can start 
 * and end and it will inform objects about such events.
 *
 * @author Gandalf.
 *         Created 11.7.2013.
 */
public class TiledRoom extends Room
{	
	// ATTRIBUTES	-----------------------------------------------------
	
	private ArrayList<SpriteBank> texturebanks;
	private ArrayList<String> texturenames;
	private TileMap tilemap;
	
	
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
	public TiledRoom(ArrayList<Background> backgrounds, 
			TileMap tilemap, ArrayList<SpriteBank> tiletexturebanks, 
			ArrayList<String> tiletexturenames)
	{
		super(backgrounds);
		
		// Initializes attributes
		this.tilemap = tilemap;
		this.texturebanks = tiletexturebanks;
		this.texturenames = tiletexturenames;
		
		// TODO: An error will propably occur since attributes aren't fully 
		// initialized at the time when the room is uninitialized in the 
		// super constructor
		//uninitialize();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	public boolean kill()
	{
		// In addition to the normal killing process, kills the tilemap as well
		if (this.tilemap != null)
		{
			this.tilemap.kill();
			this.tilemap = null;
		}
		
		return super.kill();
	}
	
	@Override
	protected void initialize()
	{
		super.initialize();
		// In addition to normal initialization, initializes the tilemap
		if (this.tilemap != null)
			this.tilemap.initialize(this.texturebanks, this.texturenames);
	}
	
	@Override
	protected void uninitialize()
	{
		// In addition to the normal uninitialization, also clears the tilemap
		super.uninitialize();
		// Clears the tilemap
		if (this.tilemap != null)
			this.tilemap.clear();
	}
	
	
	// GETTERS & SETTERS	---------------------------------------------
	
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
}
