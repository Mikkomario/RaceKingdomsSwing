package camera;

import drawnobjects.BasicPhysicDrawnObject;
import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * A camera, which follows the given object around.
 * 
 * @author Unto 18.6.2013
 * 
 */
public class FollowerCamera extends BasicCamera {

	// ATTRIBUTES ----------------------------------------------------

	private BasicPhysicDrawnObject followed;
	private int minimumspeed;
	private double zoommodifier;

	// CONSTRUCTOR ---------------------------------------------------

	/**
	 * Creates a new follower camera added to the given handlers and starting 
	 * from the 'followed' object's coordinates
	 * 
	 * @param drawer
	 *            The drawablehandler that will draw the camera and objects it
	 *            shows actorhandler The actorhandler that informs the camera
	 *            about the act-event
	 * @param actorhandler The actorhandler that will inform the camera of the act-event
	 * @param screenWidth The width of the screen
	 * @param screenHeight The height of the screen
	 * @param followed The followed PhysicDrawnObject e.g. the Car
	 */
	public FollowerCamera(DrawableHandler drawer, ActorHandler actorhandler,
			int screenWidth, int screenHeight, BasicPhysicDrawnObject followed) {

		super((int) (-followed.getX()), (int) (-followed.getY()), 
				screenWidth, screenHeight, drawer, actorhandler);

		this.followed = followed;
		this.minimumspeed = 2;
		this.zoommodifier = 1;
	}

	// IMPLEMENTED METHODS --------------------------------------------

	@Override
	public void act() {
		super.act();

		if (this.followed == null)
			return;

		// Follows the object
		setPosition(-this.followed.getX(), -this.followed.getY());

		// Zooms out
		double scale = 1;

		if (this.followed.getMovement().getSpeed() > this.minimumspeed + 1)
			scale = 1 / ((1 + Math.log(this.followed.getMovement().getSpeed()
					- this.minimumspeed)) * this.zoommodifier);
		setScale(scale, scale);
	}

}
