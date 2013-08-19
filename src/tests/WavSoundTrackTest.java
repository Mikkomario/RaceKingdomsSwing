package tests;

import listeners.AdvancedKeyListener;
import sound.WavSoundBank;
import sound.WavSoundTrack;
import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * WavSoundtracktest plays a wavsoundtrack when the user presses a button. Also tests 
 * other methods of the wavsoundtrack class. (InputTest needs to be working before 
 * this can be used)
 *
 * @author Gandalf.
 *         Created 18.8.2013.
 */
public class WavSoundTrackTest extends AbstractTest implements AdvancedKeyListener
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private boolean dead, active;
	private WavSoundBank soundbank;
	private WavSoundTrack testtrack;
	private static String[] soundnames = {"qrq", "darkness", "unit", 
										"darkness", "noo"};
	private static int[] loopcounts = {1, 0, 2, 1, 0};
	
	
	// CONSTRUCTOR	----------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 */
	public WavSoundTrackTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);

		// Initializes attributes
		this.dead = false;
		this.active = false;
		this.soundbank = new TestWavSoundBank();
		this.testtrack = new WavSoundTrack(soundnames, loopcounts, this.soundbank);
		
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
		// Also uninitializes the soundbank and kills the track
		this.soundbank.uninitialize();
		this.testtrack.kill();
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
				this.testtrack.play();
				System.out.println("Plays a track");
			}
			// On e loops the sound
			else if (key == 'e')
			{
				this.testtrack.loop();
				System.out.println("Loops a track");
			}
			// On a stops the sounds
			else if (key == 'a')
			{
				this.testtrack.stop();
				System.out.println("Stops the track");
			}
			// On s pauses all sounds
			else if (key == 's')
			{
				this.testtrack.pause();
				System.out.println("Pauses the track");
			}
			// On w unpauses all sounds
			else if (key == 'w')
			{
				this.testtrack.unpause();
				System.out.println("Unpauses the track");
			}
		}
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}
}
