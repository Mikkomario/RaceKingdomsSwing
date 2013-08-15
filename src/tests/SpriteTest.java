package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * This class tests the drawing of the sprites
 *
 * @author Gandalf.
 *         Created 15.8.2013.
 */
public class SpriteTest extends AbstractTest
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private TestSpriteObject spriteobj;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public SpriteTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);

		// Initializes attributes
		this.spriteobj = new TestSpriteObject(200, 200, drawer, actorhandler);
		
		// Deactivates the object
		this.spriteobj.setInvisible();
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public void test()
	{
		this.spriteobj.setVisible();
	}
}
