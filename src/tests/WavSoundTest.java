package tests;

import listeners.AdvancedKeyListener;
import sound.WavSound;
import sound.WavSoundBank;
import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * WavSoundtest plays a wavsound when the user presses a button. Also tests 
 * other methods of the wavsound class. (InputTest needs to be working before 
 * this can be used)
 *
 * @author Gandalf.
 *         Created 18.8.2013.
 */
public class WavSoundTest extends AbstractTest implements AdvancedKeyListener
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private boolean dead, active;
	private WavSoundBank soundbank;
	private WavSound testsound;
	private static String testsoundname = "noo";
	
	
	// CONSTRUCTOR	----------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 */
	public WavSoundTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);

		// Initializes attributes
		this.dead = false;
		this.active = false;
		this.soundbank = new TestWavSoundBank();
		this.testsound = this.soundbank.getSound(testsoundname);
		
		// Adds the test to the handler
		keylistenerhandler.addKeyListener(this);
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public void test()
	{
		// Activates the test
		activate();
		System.out.println("Test started (press q, w, e, a, s or d");
	}

	@Override
	public boolean isActive()
	{
		return this.active;
	}

	@Override
	public boolean activate()
	{
		this.active = true;
		return true;
	}

	@Override
	public boolean inactivate()
	{
		this.active = false;
		return true;
	}

	@Override
	public boolean isDead()
	{
		return this.dead;
	}

	@Override
	public boolean kill()
	{
		this.dead = true;
		// Also uninitializes the soundbank
		this.soundbank.uninitialize();
		return true;
	}

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded)
	{
		// Tests the functions of the wavsound
		if (!coded)
		{
			// On d plays the sound
			if (key == 'd')
			{
				this.testsound.play();
				System.out.println("Plays a sound");
			}
			// On e loops the sound
			else if (key == 'e')
			{
				this.testsound.loop();
				System.out.println("Loops a sound");
			}
			// On a stops the sounds
			else if (key == 'a')
			{
				this.testsound.stopAll();
				System.out.println("Stops the sounds");
			}
			// On s pauses all sounds
			else if (key == 's')
			{
				this.testsound.pauseAll();
				System.out.println("Pauses the sounds");
			}
			// On w unpauses all sounds
			else if (key == 'w')
			{
				this.testsound.unpauseAll();
				System.out.println("Unpauses the sounds");
			}
		}
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}
}
