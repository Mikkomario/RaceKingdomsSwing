package drawnobjects;

import handleds.Actor;
import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.Movement;

/**
 * In addition to CollidingDrawnObject's abilities Physicobject handles 
 * basic physical methods like moving and rotating
 *
 * @author Gandalf.
 *         Created 28.11.2012.
 */
public abstract class BasicPhysicDrawnObject extends CollidingDrawnObject 
		implements Actor
{	
	// ATTRIBUTES	------------------------------------------------------
	
	private double rotation, friction, rotFriction, maxspeed, 
			maxrotation;
	private Movement movement;
	
	
	// CONSTRUCTOR	------------------------------------------------------

	/**
	 * Creates a new physicobject with the given information. The object will
	 * be static until motion is applied. There's no friction or rotation friction 
	 * either until those are added. The object is active at default.
	 *
	 * @param x The ingame x-coordinate of the new object
	 * @param y The ingame y-coordinate of the new object
	 * @param depth How 'deep' the object is drawn
	 * @param isSolid Can the object be collided with
	 * @param collisiontype What is the shape of the object collisionwise
	 * @param drawer The drawablehandler that draws the object (optional)
	 * @param collidablehandler The collidablehandler that handles the object's 
	 * collision checking (optional)
	 * @param collisionhandler Collisionhandler that informs the object about collisions (optional)
	 * @param actorhandler The actorhandler that calls the object's act event (optional)
	 */
	public BasicPhysicDrawnObject(int x, int y, int depth, boolean isSolid, 
			CollisionType collisiontype, DrawableHandler drawer, 
			CollidableHandler collidablehandler, CollisionHandler collisionhandler, 
			ActorHandler actorhandler)
	{
		super(x, y, depth, isSolid, collisiontype, drawer, collidablehandler, 
				collisionhandler);
		
		// Initializes attributes
		this.movement = new Movement(0, 0);
		this.rotation = 0;
		this.friction = 0;
		this.rotFriction = 0;
		this.maxspeed = -1;
		this.maxrotation = -1;
		
		// Adds the object to the actorhandler if possible
		if (actorhandler != null)
			actorhandler.addActor(this);
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------
	
	@Override
	public void act()
	{
		// Handles the movement of the object
		move();
		rotate();
	}
	
	
	// GETTERS & SETTERS	-----------------------------------------------
	
	/**
	 * @return The current movement of the object
	 */
	public Movement getMovement()
	{
		return this.movement;
	}
	
	/**
	 * Changes the object's movement speed
	 *
	 * @param hspeed The new horizontal speed (pxl / step)
	 * @param vspeed The new vertical speed (pxl / step)
	 */
	public void setVelocity(double hspeed, double vspeed)
	{
		this.movement = new Movement(hspeed, vspeed);
	}
	
	/**
	 * Changes the object's movement
	 *
	 * @param movement The object's new movement
	 */
	public void setMovement(Movement movement)
	{
		this.movement = movement;
	}
	
	/**
	 * 
	 * Adds some horizontal and vertical motion to the object. The movement stacks 
	 * with the previous movement speed.
	 *
	 * @param haccelration How much speed is increased horizontally (pxl / step)
	 * @param vacceltarion How much speed is increased vertically (pxl / step)
	 */
	public void addVelocity(double haccelration, double vacceltarion)
	{
		this.movement = Movement.movementSum(getMovement(), 
				new Movement(haccelration, vacceltarion));
	}
	
	/**
	 * @return How much the object is rotated at each step (degrees / step)
	 */
	public double getRotation()
	{
		return this.rotation;
	}
	
	/**
	 * Changes how fast the object rotates around its origin
	 *
	 * @param rotation The speed with which the object rotates (degrees / step)
	 */
	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}
	
	/**
	 * @return How much the object's speed is reduced at each step (pxl / step)
	 */
	public double getFriction()
	{
		return this.friction;
	}
	
	/**
	 * Changes how much the object's speed is reduced at each step
	 *
	 * @param friction the new friction of the object (pxl / step)
	 */
	public void setFriction(double friction)
	{
		this.friction = friction;
	}
	
	/**
	 * @return How much the rotation of the sprite is reduced at each step
	 */
	public double getRotationFriction()
	{
		return this.rotFriction;
	}
	
	/**
	 * Changes how much the rotation of the sprite is reduced at each step
	 * 
	 * @param rotationFriction How much the rotation is reduced at each step (degrees / step)
	 */
	public void setRotationFriction(double rotationFriction)
	{
		this.rotFriction = rotationFriction;
	}
	
	/**
	 *Changes how much the object rotates at each step. The rotation accelration 
	 *stacks with the previous rotation speed.
	 *
	 * @param raccelration How much faster will the object be rotated (degrees / step)
	 */
	public void addRotation(double raccelration)
	{
		this.rotation += raccelration;
	}
	
	/**
	 * Adds the objects movement towards the given direction
	 *
	 * @param direction Direction towards wich the force is applied (degrees)
	 * @param force The amount of force applied to the object (pxl / step)
	 */
	public void addMotion(double direction, double force)
	{
		this.movement = Movement.movementSum(getMovement(), 
				Movement.createMovement(direction, force));
	}
	
	/**
	 * 
	 * Makes the object move towards given direction with given speed
	 *
	 * @param direction Towards what direction will the object move (degrees)
	 * @param speed How fast the objec will be moving (pxl / step)
	 */
	public void setMotion(double direction, double speed)
	{	
		this.movement = Movement.createMovement(direction, speed);
	}
	
	/**
	 * Changes the object's maximum speed
	 *
	 * @param maxspeed The new maximum speed of the object (negative if you 
	 * don't want to limit the speed (default))
	 */
	public void setMaxSpeed(double maxspeed)
	{
		this.maxspeed = maxspeed;
	}
	
	/**
	 * @return The maximum speed of the object (not used if negative)
	 */
	public double getMaxSpeed()
	{
		return this.maxspeed;
	}
	
	/**
	 * @return How fast the object can rotate
	 */
	public double getMaxRotation()
	{
		return this.maxrotation;
	}
	
	/**
	 * Changes how fast the object can rotate
	 *
	 * @param maxrotation How fast the object can rotate (negative if no limit)
	 */
	public void setMaxRotation(double maxrotation)
	{
		this.maxrotation = maxrotation;
	}
	
	
	// OTHER METHODS	----------------------------------------------------
	
	// Moves the object and handles the friction
	private void move()
	{
		addPosition(getMovement());
		
		// Checks the friction
		if (getFriction() != 0)
			implyFriction();
		
		// Also checks the maximum speed and rotation
		checkMaxSpeed();
		checkMaxRotation();
	}
	
	// Rotates teh object and handles the rotation friction
	private void rotate()
	{
		//this.angle += getRotation();
		addAngle(getRotation());
		
		if (getRotationFriction() == 0)
			return;
		
		implyRotationFriction();
	}
	
	// Slows the speed the amount of given friction
	private void implyFriction()
	{
		getMovement().diminishSpeed(getFriction());
	}
	
	// Slows the rotation speed the amoutn of given friction
	private void implyRotationFriction()
	{	
		// Slows down the object's rotation
		if (Math.abs(getRotation()) <= getRotationFriction())
			this.rotation = 0;
		else if (getRotation() > 0)
			this.rotation -= getRotationFriction();
		else
			this.rotation += getRotationFriction();
	}
	
	private void checkMaxSpeed()
	{
		if (this.maxspeed >= 0 && getMovement().getSpeed() > this.maxspeed)
			getMovement().setSpeed(this.maxspeed);
	}
	
	private void checkMaxRotation()
	{
		// If maxrotation is negative, skips the whole thing
		if (this.maxrotation < 0)
			return;
		
		// Limits the rotation speed (if needed)
		if (Math.abs(getRotation()) > this.maxrotation)
		{
			if (getRotation() < 0)
				setRotation(-this.maxrotation);
			else
				setRotation(this.maxrotation);
		}
	}
}
