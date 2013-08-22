package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import listeners.SoundListener;


/**
 * Musical objects which can be played.
 * 
 * @author Unto Created 10.7.2013
 * 
 */
public class MidiMusic extends Sound implements MetaEventListener
{
	/* TODO: Add the following for listener informing
	 * 
	 * Check: http://www.java2s.com/Code/Java/Development-Class/
	 * AnexamplethatplaysaMidisequence.htm
	 * 
	 *  public void meta(MetaMessage event)
	 *  {if (event.getType() == MidiPlayer.END_OF_TRACK_MESSAGE) {
	 */
	
	// ATTRIBUTES ---------------------------------------------------------

	private String fileName;
	private Sequence midiSequence;
	private Sequencer midiSequencer;
	private long pauseposition;

	
	// CONSTRUCTOR ---------------------------------------------------------

	/**
	 * Creates MidiMusic-object.
	 * 
	 * @param fileName	Where the midi's name and where it is located (src/data 
	 * automatically included).
	 * @param name The name of the midimusic (in the midimusicbank)
	 */
	public MidiMusic(String fileName, String name)
	{
		super(name);
		
		// Initializes attributes
		this.fileName = "src/data/" + fileName;
		this.pauseposition = 0;
		
		// tries to create the midisequence
		try
		{
			this.midiSequence = MidiSystem.getSequence(new File(this.fileName));
		}
		catch (InvalidMidiDataException e)
		{
			System.err.println("Couldn't find create a midisequence!");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.err.println("IOException whilst creating midisequence!");
			e.printStackTrace();
		}
		// Now let's try and set-up our midiSequencer
		try
		{
			this.midiSequencer = MidiSystem.getSequencer();
		}
		catch (MidiUnavailableException e) {
			System.err.println("Problems whilst setting up sequencer!");
			e.printStackTrace();
		} 
	}
	
	
	// IMPLEMENTED METHODS	-------------------------------------------
	
	@Override
	public void playSound()
	{
		// Plays the music once from the very beginning
		setLoopCount(0);
		setLoopStart(0);
		setLoopEnd(-1);
		startMusic(0);
	}

	@Override
	public void loopSound()
	{
		// Loops the music continuously
		setLoopCount(-1);
		setLoopStart(0);
		setLoopEnd(-1);
		startMusic(0);
	}

	@Override
	public void stopSound()
	{
		// Stops the music from playing and informs the listeners
		if (this.midiSequencer.isRunning())
		{	
			this.midiSequencer.stop();
			this.midiSequencer.close();
		}
	}

	@Override
	public void pause()
	{
		if (this.midiSequencer.isRunning())
		{
			this.midiSequencer.stop();
			this.pauseposition = this.midiSequencer.getTickPosition();
		}
	}

	@Override
	public void unpause()
	{
		if (!this.midiSequencer.isRunning())
		{
			// Starts the music from the spot it was at
			startMusic(this.pauseposition);
		}
	}
	
	@Override
	public void meta(MetaMessage event)
	{
		// Checks if a midi ended and informs the listeners
		// TODO: Check this again...
		if (event.getType() == 47)
			informSoundEnd();
	}
	

	// METHODS ---------------------------------------------------
	
	/**
	 * Starts playing the music-file from the given position.
	 * 
	 * @param startPosition	 Playback's starting tick-position.
	 * @param specificlistener A listener that will be informed about the 
	 * events caused by this specific play (null if not needed)
	 */
	public void startMusic(long startPosition, SoundListener specificlistener)
	{
		// Stops old music if still playing
		if (isPlaying())
			stop();
		
		// Informs listeners and starts the music
		informSoundStart(specificlistener);
		startMusic(startPosition);
	}

	/**
	 * @return Returns the length of a Midi-sequence in ticks.
	 */
	public long getSequenceLength() {
		return this.midiSequence.getTickLength();
	}

	private void startMusic(long startPosition)
	{
		// If a sound is already playing, stops it
		//stop();
		
		//Now let's try to set our sequence
		try
		{		
			this.midiSequencer.setSequence(this.midiSequence);
		}
		catch (InvalidMidiDataException e)
		{
			System.err.println("Midi was invalid!");
			e.printStackTrace();
		}
		try
		{
			this.midiSequencer.open();
		}
		catch (MidiUnavailableException mue)
		{
			System.err.println("Midi" + getName() +  "was unavailable!");
			mue.printStackTrace();
		}
		this.midiSequencer.setTickPosition(startPosition);
		this.midiSequencer.start();
	}

	/*
	public long pauseMusic()
	{
		if (this.midiSequencer.isRunning())
		{
			this.midiSequencer.stop();
			long pausePosition = this.midiSequencer.getTickPosition();
			return pausePosition;
		}
		else
			return 0;
	}
	*/

	/**
	 * Sets how many times the song loops.
	 * 
	 * @param loopCount	How many times the song loops. If loopCount is negative, song
	 *            will loop continuously.
	 */
	public void setLoopCount(int loopCount) {
		if (loopCount < 0)
		{
			this.midiSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		}
		else
		{
			this.midiSequencer.setLoopCount(loopCount);
		}
	}

	/**
	 * Changes where the music's loop starts.
	 * 
	 * @param loopStartPoint	The tick where music's loop starts.
	 */
	public void setLoopStart(long loopStartPoint) {
		this.midiSequencer.setLoopStartPoint(loopStartPoint);
	}

	/**
	 * Changes where the music's loop ends.
	 * 
	 * @param loopEndPoint	The tick where music's loop ends. (0 means no end point, 
	 * -1 means the end of the midi)
	 */
	public void setLoopEnd(long loopEndPoint) {
		this.midiSequencer.setLoopEndPoint(loopEndPoint);
	}
	
	/**Sets a new tempo for the midi. 1.0 is the default TempoFactor.
	 * 
	 * @param newTempoFactor	New tempoFactor for the midi.
	 */
	public void setTempoFactor (float newTempoFactor){
		this.midiSequencer.setTempoFactor(newTempoFactor);
	}
	
	/**Sets the TempoFactor to 1.0, which is the default.
	 */
	public void resetTempoFactor(){
		this.midiSequencer.setTempoFactor(1);
	}
	
	/**Returns the current TempoFactor.
	 * 
	 * @return	Returns the current TempoFactor as a float.
	 */
	public float getTempoFactor(){
		return this.midiSequencer.getTempoFactor();
	}
}
