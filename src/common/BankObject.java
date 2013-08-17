package common;

/**
 * All of the bankobjects can be hold in a class that is a subclass of the 
 * AbstractBank class
 *
 * @author Gandalf.
 *         Created 17.8.2013.
 */
public interface BankObject
{	
	/**
	 * In this method the object should prepare for the inevitable end of 
	 * its existence by stopping all of its processes
	 */
	public void kill();
}
