package tests;

import camera.ZoomingFollowerCamera;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import racing.Car;
import racing.CarSpriteBank;

/**
 * Tests the car and the camera simultaneously
 *
 * @author Gandalf.
 *         Created 18.6.2013.
 */
public class CameraCarTest extends AbstractTest
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private Car testcar;
	private ZoomingFollowerCamera testcamera;
	
	
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
	public CameraCarTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);
		
		// Initializes attributes
		this.testcar = new Car(500, 225, null, null, null, actorhandler, 
				keylistenerhandler, new CarSpriteBank(), "test", 
				"testcarmask");
		this.testcamera = new ZoomingFollowerCamera(drawer, actorhandler, 1000, 600, 
				this.testcar, 2, 1);
		
		this.testcamera.inactivate();
		this.testcamera.setInvisible();
	}

	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	public void test()
	{
		this.testcamera.activate();
		this.testcamera.setVisible();
		this.testcamera.addDrawable(this.testcar);
		this.testcamera.scale(1, 1);
		this.testcamera.setAngle(0);
	}
}
