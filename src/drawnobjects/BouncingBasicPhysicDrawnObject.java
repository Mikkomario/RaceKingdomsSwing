package drawnobjects;

import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DoublePoint;
import helpAndEnums.HelpMath;
import helpAndEnums.Movement;

/**
 * This is a direct extension of the basicphysicobject class that can bounce 
 * from other objects (without moments)
 *
 * @author Gandalf.
 *         Created 24.8.2013.
 */
public abstract class BouncingBasicPhysicDrawnObject extends BasicPhysicDrawnObject
{
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
	public BouncingBasicPhysicDrawnObject(int x, int y, int depth,
			boolean isSolid, CollisionType collisiontype,
			DrawableHandler drawer, CollidableHandler collidablehandler,
			CollisionHandler collisionhandler, ActorHandler actorhandler)
	{
		super(x, y, depth, isSolid, collisiontype, drawer, collidablehandler,
				collisionhandler, actorhandler);
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Slows the objects directional (tangentual to the opposing force) 
	 * movement with the given modifier
	 *
	 * @param oppmovement The opposing force (N) that causes the friction
	 * @param frictionmodifier f with which the friction is calculated [0, 1]
	 */
	protected void addWallFriction(Movement oppmovement, double frictionmodifier)
	{
		double friction = oppmovement.getSpeed() * frictionmodifier;
		// Diminishes the speed that was not affected by the oppposing force
		setMovement(getMovement().getDirectionalllyDiminishedMovement(
				oppmovement.getDirection() + 90, friction));
	}
	
	/**
	 * The object bounces with a certain object it collides with This doesn't 
	 * cause rotational movement.
	 *
	 * @param d The object collided with
	 * @param collisionpoint The point in which the collision happens (absolute)
	 * @param bounciness How much the object bounces away from the given object (0+)
	 * @param frictionmodifier How much energy is lost during the collision (0-1)
	 */
	public void bounceWithoutRotationFrom(DimensionalDrawnObject d, 
			DoublePoint collisionpoint, double bounciness, 
			double frictionmodifier)
	{	
		// If there's no speed, doesn't do anything
		if (getMovement().getSpeed() == 0)
			return;
		
		// Calculates the direction, towards which the force is applied
		double forcedir = d.getCollisionForceDirection(collisionpoint.getAsPoint());
		
		// Calculates the actual amount of force applied to the object
		Movement oppmovement = 
				getMovement().getOpposingMovement().getDirectionalMovement(forcedir);
		
		bounce(bounciness, frictionmodifier, oppmovement, forcedir);
	}
	
	private void bounce(double bounciness, double frictionmodifier, 
			Movement oppmovement, double forcedir)
	{
		double force = bounciness * (oppmovement.getSpeed());
		
		// Adds the opposing force and the force (if they are not negative)
		if (HelpMath.getAngleDifference180(
				oppmovement.getDirection(), forcedir) < 90)
		{
			addMotion(forcedir, oppmovement.getSpeed());
			if (force > 0)
				addMotion(forcedir, force);
			
			// Also adds friction if needed
			if (frictionmodifier > 0)
				addWallFriction(oppmovement, frictionmodifier);
		}
	}
}
