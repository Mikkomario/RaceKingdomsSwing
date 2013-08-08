package helpAndEnums;

/**
 * This enumeration describes the style with which an object is collided with. 
 * For example, is the object like a wall or like a ball
 *
 * @author Gandalf.
 *         Created 29.6.2013.
 */
public enum CollisionType
{
	/**
	 * The object is a rectangle and has four different sides
	 */
	BOX,
	/**
	 * The object is circular and colliding objects simply bounce away from it
	 */
	CIRCLE,
	/**
	 * The object has only two sides
	 */
	WALL;
}
