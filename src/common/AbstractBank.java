package common;

import java.util.HashMap;

/**
 * Abstractbank is the superclass of all the bank objects providing some 
 * necessary methods for the subclasses. The class also handles neccessary 
 * data handling.
 *
 * @author Mikko Hilpinen.
 *         Created 17.8.2013.
 * @see BankObject
 */
public abstract class AbstractBank
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private HashMap<String, BankObject> bank;
	private boolean initialized;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new uninitialized abstractbank. The bank will be initialized 
	 * when an object tries to get something from it.
	 */
	public AbstractBank()
	{
		// Initializes attributes
		this.bank = new HashMap<String, BankObject>();
		this.initialized = false;
	}
	
	
	// ABSTRACT METHODS	-------------------------------------------------
	
	/**
	 * @return The class the bank supports. In other words, what kind of instances 
	 * does the bank hold
	 */
	protected abstract Class<?> getSupportedClass();
	
	/**
	 * A method that adds the needed objects into the bank using the addObject 
	 * method or another similar method.
	 * 
	 * @see addObject
	 */
	protected abstract void initialize();
	
	
	// OTHER METHODS	-------------------------------------------------
	
	/**
	 * Tries to retrieve an object from the bank. Calling this method initializes 
	 * the bank if it hasn't yet been initialized
	 *
	 * @param objectname The name of the object in the bank
	 * @return The object in the bank or null if no object with the given name 
	 * was found
	 */
	protected BankObject getObject(String objectname)
	{
		// If the bank hasn't yet been initialized, initializes it
		if (!this.initialized)
		{
			initialize();
			this.initialized = true;
		}
		
		// Tries to get the object from the map and return it
		if (this.bank.containsKey(objectname))
			return this.bank.get(objectname);
		else
		{
			System.err.println("The bank " + getClass().getName() + 
					" doesn't hold object named " + objectname);
			return null;
		}
	}
	
	/**
	 * Adds a new object to the bank
	 *
	 * @param o The object to be added (must have the supported class)
	 * @param name The name of the object in the bank
	 * @see getSupportedClass
	 */
	protected void addObject(BankObject o, String name)
	{
		// Checks the given name
		if (name == null)
			return;
		// Checks whether the object's class is supported
		if (!getSupportedClass().isInstance(o))
			return;
		
		// Adds the object to the bank
		this.bank.put(name, o);
	}
	
	/**
	 * Uninitializes the contents of the bank. The bank will be reinitialized 
	 * when something is tried to retrieve from it
	 * 
	 * @warning calling this method while the objects in the bank are in use 
	 * may crash the program depending on the circumstances. It would be safe 
	 * to uninitialize the bank only when the content is not in use.
	 */
	public void uninitialize()
	{
		// If the object wasn't initialized yet, doesn't do anything
		if (!this.initialized)
			return;
		
		// Goes through all the objects in the bank and kills them
		for (String name: this.bank.keySet())
			this.bank.get(name).kill();
		
		// Clears the bank
		this.bank.clear();
		this.initialized = false;
	}
}
