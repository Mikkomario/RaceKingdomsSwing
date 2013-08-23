package sound;

import listeners.SoundListener;

/**
 * Midimusictrack plays a single midi using certain sets of start- and endpoints
 *
 * @author Gandalf.
 *         Created 23.8.2013.
 */
public class MidiMusicTrack extends Sound implements SoundListener
{	
	// ATTRIBUTES	------------------------------------------------------
	
	private MidiMusic midi;
	private LoopPointInformation[] loopinformations;
	private int currentindex, currentloopsleft;
	private boolean looping, paused;
	
	
	// CONSTRUCTOR	------------------------------------------------------

	/**
	 * Creates a new midimusictrack with the given information
	 *
	 * @param name The name of the track
	 * @param midi The midimusic used in the track
	 * @param loopinformations The set of looppoints
	 */
	public MidiMusicTrack(String name, MidiMusic midi, 
			LoopPointInformation[] loopinformations)
	{
		super(name);
		
		// Initializes attributes
		this.midi = midi;
		this.loopinformations = loopinformations;
		this.currentindex = 0;
		this.currentloopsleft = 0;
		this.looping = false;
		this.paused = false;
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	protected void playSound()
	{
		// Resets the position and loopcount
		this.currentindex = 0;
		this.currentloopsleft = 
				this.loopinformations[this.currentindex].getLoopCount();
		playMidi();
	}

	@Override
	protected void loopSound()
	{
		// Plays and sets the track to loop
		playSound();
		this.looping = true;
	}

	@Override
	protected void stopSound()
	{
		// Stops the midi from playing
		this.midi.stop();
		this.paused = false;
		this.looping = false;
	}

	@Override
	public void pause()
	{
		// Pauses the midi (only works if the sound is playing)
		if (isPlaying())
		{
			this.paused = true;
			this.midi.pause();
		}
	}

	@Override
	public void unpause()
	{
		// Continues the midi from the last point (only works if paused)
		if (this.paused)
		{
			this.paused = false;
			this.midi.unpause();
		}
	}
	
	@Override
	public boolean isActive()
	{
		// Tracks are always active
		return true;
	}

	@Override
	public boolean activate()
	{
		// Tracks are always acive
		return true;
	}

	@Override
	public boolean inactivate()
	{
		// Tracks are always active
		return false;
	}

	@Override
	public void onSoundStart(Sound source)
	{
		// Does nothing
	}

	@Override
	public void onSoundEnd(Sound source)
	{
		// When the sound ends (and the track is still playing), either loops 
		// or continues to the next seqment
		if (!isPlaying())
			return;

		// Checks if the current seqment should loop
		if (this.currentloopsleft > 0)
		{
			this.currentloopsleft --;
			// Plays the sqment again
			this.midi.startMusic(
					this.loopinformations[this.currentindex].getStartPoint(), 
					this);
			this.midi.setLoopEnd(
					this.loopinformations[this.currentindex].getEndPoint());
		}
		// Or if a  new phase should be played
		else
		{
			this.currentindex ++;
			// Checks if the end of the track was reached
			if (this.currentindex >= this.loopinformations.length)
			{
				// Either loops the track
				if (this.looping)
				{
					this.currentindex = 0;
					playMidi();
				}
				// Or ends it
				else
				{
					this.currentindex = 0;
					this.paused = false;
					informSoundEnd();
				}
			}
			// If not, just plays the new phase
			else
				playMidi();
		}
	}
	
	
	// OTHER METHODS	-------------------------------------------------
	
	// Plays the midi using the start- and endpoints indicated by the current 
	// index
	private void playMidi()
	{
		// Starts the first midi seqment
		this.midi.startMusic(
				this.loopinformations[this.currentindex].getStartPoint(), this);
		// Sets the endpoint
		this.midi.setLoopEnd(this.loopinformations[this.currentindex].getEndPoint());
	}
	
	
	// SUBCLASSES	-----------------------------------------------------
	
	/**
	 * LoopPointInformation holds the information about midimusic's loop's 
	 * start- and endpoints.
	 *
	 * @author Gandalf.
	 *         Created 23.8.2013.
	 */
	public class LoopPointInformation
	{
		// ATTRIBUTES	--------------------------------------------------
		
		private long start;
		private long end;
		private int loops;
		
		
		// CONSTRUCTOR	--------------------------------------------------
		
		/**
		 * Creates a new looppointinformation object holding the given information
		 *
		 * @param startpoint The loop's startpoint
		 * @param endpoint The loop's endpoint
		 * @param loopcount How many times the music is repeated between the 
		 * given points
		 */
		public LoopPointInformation(long startpoint, long endpoint, int loopcount)
		{
			// Checks the arguments
			if (startpoint < 0 || endpoint < startpoint)
			{
				System.err.println("Invalid arguments in loopPointInformation");
				throw new IllegalArgumentException();
			}
			
			// Initializes attributes
			this.start = startpoint;
			this.end = endpoint;
			this.loops = loopcount;
		}
		
		
		// GETTERS & SETTERS	-------------------------------------------
		
		/**
		 * Changes the startpoint information
		 *
		 * @param startpoint The new held startpoint
		 */
		public void setStartPoint(long startpoint)
		{
			this.start = startpoint;
		}
		
		/**
		 * Changes the endpoint information
		 *
		 * @param endpoint The new held endpoint
		 */
		public void setEndPoint(long endpoint)
		{
			this.end = endpoint;
		}
		
		/**
		 * Changes how many times the interval is repeated
		 *
		 * @param loopcount How many times the interval is repeated
		 */
		public void setLoopCount(int loopcount)
		{
			this.loops = loopcount;
		}
		
		private long getStartPoint()
		{
			return this.start;
		}
		
		private long getEndPoint()
		{
			return this.end;
		}
		
		private int getLoopCount()
		{
			return this.loops;
		}
	}
}
