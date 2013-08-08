package handlers;

import racekingdoms.RaceKingdoms;

/**
 * This class calculates millisconds and calls all actors when a certain number 
 * of milliseconds has passed. All of the actors should be under the command of 
 * this object. This object doesn't stop functioning by itself if it runs out 
 * of actors.
 * 
 * The stephandler's act method must still be called at each frame
 *
 * @author Gandalf.
 *         Created 29.11.2012.
 */
public class StepHandler extends ActorHandler implements Runnable
{
	// ATTRIBUTES	-------------------------------------------------------
	
	private int stepduration;
	private long lastMillis;
	private boolean running;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * This creates a new stephandler. Actors are informed 
	 * when a certain number of milliseconds has passed. Actors can be 
	 * added using addActor method.
	 * 
	 * @param stepDuration How long does a single step last in milliseconds.
	 * In other words, how often are the actors updated.
	 * @param applet The applet which created the stepHandler
	 */
	public StepHandler(int stepDuration)
	{
		super(false, null); // Stephandler doesn't have a superhandler
		this.stepduration = stepDuration;
		
		this.lastMillis = 0;
		this.running = false;
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------
	
	@Override
	public void act()
	{	
		// Checks the current millis and performs a "step" if needed
		if (Math.abs(System.currentTimeMillis() - this.lastMillis) > 
			this.stepduration)
		{
			//System.out.println("STEP");
			
			// Calls all actors
			if (!isDead())
			{
				super.act();
				
				// Updates the game according to the changes
				this.applet.callScreenUpdate();
				this.applet.callMousePositionUpdate();
			}
			// Stops running if dies
			else
				this.running = false;
			
			// Remembers the time
			this.lastMillis = System.currentTimeMillis();
		}
	}

	@Override
	public void run()
	{
		this.running = true;
		
		// Starts counting steps and does it until the object is killed
		while (this.running)
			act();
	}
}
