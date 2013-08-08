package handleds;

/**
 * Collidable objects can collide with each other
 *
 * @author Gandalf.
 *         Created 18.6.2013.
 */
public interface Collidable extends Handled
{
	/**
	 * Checks whether a point collides with the object
	 * @param x The x-coordinate of the point
	 * @param y The y-coordinate of the point
	 * @return The collidable that collides with the object (null if no object collides)
	 */
	public Collidable pointCollides(int x, int y);
	
	/**
	 * @return Can the object be collided with at this time
	 */
	public boolean isSolid();
	
	/**
	 * Tries to make the object solid so that the objects will collide with it
	 * @return Was the object made solid
	 */
	public boolean makeSolid();
	
	/**
	 * Tries to make the object unsolid so that no object can collide with it
	 *
	 * @return Was the object successfully made solid
	 */
	public boolean makeUnsolid();
}
