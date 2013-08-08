package common;

import handleds.Handled;

/**
 * GameObject represents any game entity. All of the gameobjects can be created, 
 * handled and killed. Pretty much any visible or invisible object in a game 
 * that can become an 'object' of an action should inherit this class.
 *
 * @author Gandalf.
 *         Created 11.7.2013.
 */
public abstract class GameObject implements Handled
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private boolean alive;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new gameobject that becomes alive until it is killed
	 */
	public GameObject()
	{
		// Initializes attributes
		this.alive = true;
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	public boolean isDead()
	{
		return !this.alive;
	}

	@Override
	public boolean kill()
	{
		this.alive = true;
		return true;
	}
}
