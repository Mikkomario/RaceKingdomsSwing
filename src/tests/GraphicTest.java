package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import handlers.MouseListenerHandler;

/**
 * Tests the graphical aspects of the program
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public class GraphicTest extends AbstractTest
{
	// ATTRIBUTES	-------------------------------------------------------
	
	private TestBox box;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 */
	public GraphicTest(ActorHandler actorhandler, DrawableHandler drawer, 
			KeyListenerHandler keylistenerhandler, 
			MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);
		this.box = new TestBox(drawer, null);
		this.box.setInvisible();
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void test()
	{
		this.box.setVisible();
		this.box.addAngle(45);
		this.box.addPosition(300, 50);
		this.box.scale(2, 3);
		//this.box.setPosition(0, 0);
	}
}
