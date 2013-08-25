package tests;

import java.awt.event.KeyEvent;

import listeners.AdvancedKeyListener;
import sound.MidiMusic;
import handlers.ActorHandler;
import handlers.DrawableHandler;

/**Tests whether midis can be started, paused, continued and stopped
 * 
 * @author Unto
 *         Created 12.7.2013.
 */
public class MidiTest extends AbstractTest implements AdvancedKeyListener
{	
	// ATTRIBUTES	------------------------------------------------------
	
	private TestMidiMusicBank testBank;
	private MidiMusic midiMusic;
	private boolean paused;
	private boolean isActive;
	private boolean isDead;
	private boolean looping;
	
	// CONSTRUCTOR	------------------------------------------------------

	/**
	 * Creates a new MidiTest with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 */
	public MidiTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler) {
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);
		
		//Let's start setting up our test
		keylistenerhandler.addKeyListener(this);
		this.testBank = new TestMidiMusicBank();
		this.midiMusic = this.testBank.getSound("test");
		this.paused = false;
		this.isActive = false;
		this.isDead = false;
		this.looping = false;
	}

	// IMPLEMENTED METHOD	---------------------------------------------
	@Override
	public void test() {
		//Let's start the test
		this.activate();
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public boolean activate() {
		this.isActive = true;
		return true;
	}

	@Override
	public boolean inactivate() {
		this.isActive = false;
		return true;
	}

	@Override
	public boolean isDead() {
		return this.isDead;
	}

	@Override
	public boolean kill() {
		this.isDead = true;
		return true;
	}

	@Override
	public void onKeyDown(char key, int keyCode, boolean coded) {
		// Not needed
		
	}

	@Override
	public void onKeyPressed(char key, int keyCode, boolean coded) {
		if(!coded){
			if(key == KeyEvent.VK_ENTER){
				//Starts playing the midi
				this.midiMusic.startMusic(0, null);
				//When you start playing a new song, it isn't paused right from the get-go.
				this.paused = false;
				//And let's reset the tempo
				this.midiMusic.resetTempoFactor();
				System.out.println("You pressed ENTER, so the music should start!");
			}
			else if (key == 'p'){
				//Pauses and continues playing music.
				if(this.paused){
					this.midiMusic.unpause();
					this.paused = false;
					System.out.println("You pressed p, so music should continue.");
				}else{
					this.midiMusic.pause();
					this.paused = true;
					System.out.println("You pressed p, so music should pause.");
				}
			}
			else if (key == 's'){
				//Stops playing the music
				this.midiMusic.stop();
				System.out.println("You pressed s, so everything should stop.");
			}
			else if (key == 't'){
				//Doubles the tempo and resets it
				if(this.midiMusic.getTempoFactor() > 1){
					this.midiMusic.resetTempoFactor();
					System.out.println("You pressed t, so music should return to normal.");
				}else{
					this.midiMusic.setTempoFactor(2);
					System.out.println("You pressed t, so tempo should increase.");
				}
			}
			else if (key == 'l'){
				//Sets new start and end points for looping
				if(this.looping){
					this.midiMusic.setLoopCount(0);
					this.midiMusic.setDefaultLoopPoints();
					this.looping = false;
					System.out.println("You pressed l, so looping should end.");
				}else{
					this.midiMusic.setLoopCount(-1);
					this.midiMusic.setLoopEnd(this.midiMusic.getSequenceLength() - 1000);
					this.looping = true;
					System.out.println("You pressed l, so looping should start.");
				}
			}
		}
		
	}

	@Override
	public void onKeyReleased(char key, int keyCode, boolean coded) {
		// Not needed
		
	}
}
