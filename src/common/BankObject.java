package common;

/**
 * All of the bankobjects can be hold in a certain type of bank and each of the 
 * objects can be permanently deactivated since the banks may be initialized 
 * and uninitialized at will.
 *
 * @author Mikko Hilpinen.
 *         Created 17.8.2013.
 * @see AbstractBank
 */
public interface BankObject
{	
	/**
	 * In this method the object should prepare for the inevitable end of 
	 * its existence by stopping all of its processes
	 * 
	 * @return Was the process successfull
	 */
	public boolean kill();
	
	/**
	 * @return Is the object dead (true) or alive (false)
	 */
	public boolean isDead();
}
