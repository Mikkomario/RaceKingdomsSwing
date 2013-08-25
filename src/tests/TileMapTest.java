package tests;

import java.util.ArrayList;

import backgrounds.TileMap;
import graphic.SpriteBank;
import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * This class tests the tilemap system
 *
 * @author Gandalf.
 *         Created 10.7.2013.
 */
public class TileMapTest extends AbstractTest
{
	// ATTRIBUTES	------------------------------------------------------
	
	private TileMap map;
	private ArrayList<SpriteBank> banks;
	private ArrayList<String> spritenames;
	private short[] bankindexes = {0, 0, 0, 0};
	private short[] nameindexes = {0, 1, 1, 0};
	private short[] rotations = {90, 0, 0, 0};
	private short[] xscales = {1, 1, 1, 1};
	private short[] yscales = {1, -1, 1, 1};
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 */
	public TileMapTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);

		// Initializes attributes
		this.banks = new ArrayList<SpriteBank>();
		this.banks.add(new TestSpriteBank());
		this.spritenames = new ArrayList<String>();
		this.spritenames.add("background");
		this.spritenames.add("background2");
		
		this.map = new TileMap(500, 300, drawer, actorhandler, null, 2, 2, 50, 
				50, this.bankindexes, this.rotations, this.xscales, this.yscales, 
				this.nameindexes);
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void test()
	{
		this.map.initialize(this.banks, this.spritenames);
	}
}
