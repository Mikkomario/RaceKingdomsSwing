package tests;

import handlers.ActorHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import racing.Car;
import racing.CarSpriteBank;

/**
 * Test to see if the car can collide with a box.
 * 
 * @author Unto	18.6.2013
 *
 */
public class CollisionTest extends AbstractTest
{
	
	//ATTRIBUTES	------------------------------------------------------
	
	private Car testcar;
	private TestBox testbox;
	private CollisionHandler colhandler;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	
	/**Constructs a testcar and testbox to see if they can collide.
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 */
	public CollisionTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler) {
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);
		
		this.colhandler = new CollisionHandler(true, actorhandler);
		
		this.testcar = new CollisionTestCar(drawer, 
				this.colhandler.getCollidableHandler(), this.colhandler, actorhandler, 
				keylistenerhandler, new CarSpriteBank());
		this.testbox = new TestBox(drawer, this.colhandler.getCollidableHandler());
		
		this.testcar.inactivate();
		this.testcar.setInvisible();
		this.colhandler.inactivate();
		this.testbox.setInvisible();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void test() {
		//Let's activate our participants and make them visible
		this.testcar.activate();
		this.testcar.setVisible();
		this.testbox.setVisible();
		this.colhandler.activate();
	}
}
