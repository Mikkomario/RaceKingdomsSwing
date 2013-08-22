package sound;

import listeners.SoundListener;

/**
 * WavSoundTrack is a class that plays multiple wavsounds in order, forming a 
 * track
 *
 * @author Gandalf.
 *         Created 19.8.2013.
 */
public class SoundTrack extends Sound implements SoundListener
{
	// ATTRIBUTES	------------------------------------------------------
	
	private String[] soundnames;
	private int[] loopcounts;
	private SoundBank soundbank;
	private int currentindex, currentloopcount;
	private Sound currentsound;
	private boolean paused, delayed, loops;
	
	
	// CONSTRUCTROR	------------------------------------------------------
	
	/**
	 * Creates a new wavsoundtrack with the given information
	 *
	 * @param soundnames A table containing the names of the sounds that create 
	 * the track (in a specific order)
	 * @param loopcounts A table containing the numbers about how many times 
	 * each sound is repeated in a row (should have as many indexes as the 
	 * soundnames table)
	 * @param soundbank The Wavsoundbank that contains each of the sounds used 
	 * in the track
	 * @param name The name of the track
	 */
	public SoundTrack(String[] soundnames, int[] loopcounts, 
			SoundBank soundbank, String name)
	{
		super(name);
		
		// Initializes attributes
		this.soundbank = soundbank;
		this.soundnames = soundnames;
		this.loopcounts = loopcounts;
		this.currentindex = 0;
		this.currentloopcount = 0;
		this.currentsound = null;
		this.paused = false;
		this.delayed = false;
		this.loops = false;
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	public boolean isActive()
	{
		// The track is always active so it doesn't break (inactivating is done 
		// by pausing)
		return true;
	}

	@Override
	public boolean activate()
	{
		// The track is always active
		return true;
	}

	@Override
	public boolean inactivate()
	{
		// The track is always active
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
		// If the sound was stopped, doesn't do anything
		if (!isPlaying())
			return;
		// Plays the next sound (if not paused, in which case delays the sound)
		if (this.paused)
		{
			this.delayed = true;
			System.out.println("Delays");
		}
		else
			playnextsound();
	}
	
	/**
	 * Stops the track from playing
	 */
	@Override
	public void stopSound()
	{
		// Stops the current sound and the track
		this.delayed = false;
		this.paused = false;
		this.currentsound.stop();
	}
	
	/**
	 * Pauses the track. The track can be continued from the same spot with 
	 * unpause method
	 */
	@Override
	public void pause()
	{
		this.paused = true;
		this.currentsound.pause();
	}
	
	/**
	 * Unpauses the track from the last state
	 */
	@Override
	public void unpause()
	{
		// TODO: Unpausing doesn't always seem to work (when not delayed)
		this.paused = false;
		// Continues the track if it was delayed
		if (this.delayed)
			playnextsound();
		// Otherwise just continues the former sound
		else
			this.currentsound.unpause();
	}
	
	/**
	 * Plays through the track once
	 */
	@Override
	public void playSound()
	{
		// Doesn't work if the track was killed
		if (isDead())
			return;
		
		// Updates information
		this.currentindex = 0;
		this.currentloopcount = this.loopcounts[this.currentindex];
		this.currentsound = this.soundbank.getSound(this.soundnames[this.currentindex]);
		this.paused = false;
		this.delayed = false;
		this.loops = false;
		
		// Plays the first sound
		this.currentsound.play(this);
	}
	
	/**
	 * Plays through the track repeatedly until stopped
	 */
	@Override
	public void loopSound()
	{
		playSound();
		this.loops = true;
	}
	
	
	// GETTERS & SETTERS	---------------------------------------------
	
	/**
	 * Changes the soundbank used in the track. This can be done in the midle 
	 * of playing a track and the change will take place once the current sound 
	 * stops
	 *
	 * @param bank The new soundbank to be used
	 */
	public void setSoundBank(SoundBank bank)
	{
		this.soundbank = bank;
	}
	
	/**
	 * Changes the sounds used in the track. This can be done in the midle 
	 * of playing a track and the change will take place once the current sound 
	 * stops
	 *
	 * @param soundnames The new sound names to be used
	 */
	public void setSoundNames(String[] soundnames)
	{
		this.soundnames = soundnames;
	}
	
	/**
	 * Changes the loopcounts used in the track. This can be done in the midle 
	 * of playing a track and the change will take place once the current sound 
	 * stops
	 *
	 * @param loopcounts The set of loopcounts to be used
	 */
	public void setLoopCounts(int[] loopcounts)
	{
		this.loopcounts = loopcounts;
	}	
	
	
	// OTHER METHODS	--------------------------------------------------
	
	private void playnextsound()
	{
		// Only plays the next sound if the track is still playing
		if (!isPlaying())
			return;
		
		// The sound is no longer delayed
		this.delayed = false;
		
		// Checks whether more loops are needed
		// Loops the current sound if needed
		if (this.currentloopcount > 0)
		{
			this.currentloopcount --;
			this.currentsound = this.soundbank.getSound(
					this.soundnames[this.currentindex]);
			this.currentsound.play(this);
		}
		// Or plays the next sound
		else
		{
			// Gathers the information
			this.currentindex ++;
			
			// If the end of the track was reached, either repeats or stops
			if (this.currentindex >= this.soundnames.length 
					|| this.currentindex >= this.loopcounts.length)
			{
				if (this.loops)
					this.currentindex = 0;
				else
				{
					stop();
					return;
				}
			}
			
			this.currentloopcount = this.loopcounts[this.currentindex];
			this.currentsound = this.soundbank.getSound(this.soundnames[this.currentindex]);
			
			// And plays the new sound
			this.currentsound.play(this);
		}
	}
}
