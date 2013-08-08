package handleds;

/**
 * This is the superinterface for all of the interfaces that can be handled
 * (actor, drawable, ect.) and has their most universal information
 *
 * @author Gandalf.
 *         Created 8.12.2012.
 */
public interface Handled
{
	/**
	 * @return Should the handled object be handled anymore
	 */
	public boolean isDead();
	
	/**
	 * Tries to end the handleds all activities
	 * @return Was the handled object permanently made inactive
	 */
	public boolean kill();
}
