package sound;

import listeners.SoundListener;

/**
 * WavSoundTrack is a class that plays multiple wavsounds in order, forming a 
 * track
 *
 * @author Gandalf.
 *         Created 19.8.2013.
 */
public class WavSoundTrack implements SoundListener
{
	// ATTRIBUTES	------------------------------------------------------
	
	private String[] soundnames;
	private int[] loopcounts;
	private WavSoundBank soundbank;
	private int currentindex, currentloopcount;
	private WavSound currentsound;
	private boolean dead, paused, delayed, loops, playing;
	
	
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
	 */
	public WavSoundTrack(String[] soundnames, int[] loopcounts, WavSoundBank soundbank)
	{
		// Initializes attributes
		this.soundbank = soundbank;
		this.soundnames = soundnames;
		this.loopcounts = loopcounts;
		this.currentindex = 0;
		this.currentloopcount = 0;
		this.currentsound = null;
		this.dead = false;
		this.paused = false;
		this.delayed = false;
		this.loops = false;
		this.playing = false;
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
	public boolean isDead()
	{
		return this.dead;
	}

	@Override
	public boolean kill()
	{
		// Stops the sound as well
		stop();
		this.dead = true;
		return true;
	}

	@Override
	public void onSoundStart(String soundname)
	{
		// Does nothing
	}

	@Override
	public void onSoundEnd(String soundname)
	{
		// Plays the next sound (if not paused, in which case delays the sound)
		if (this.paused)
			this.delayed = true;
		else
			playnextsound();
	}
	
	
	// GETTERS & SETTERS	---------------------------------------------
	
	/**
	 * Changes the soundbank used in the track. This can be done in the midle 
	 * of playing a track and the change will take place once the current sound 
	 * stops
	 *
	 * @param bank The new soundbank to be used
	 */
	public void setSoundBank(WavSoundBank bank)
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
	
	/**
	 * Plays through the track once
	 */
	public void play()
	{
		// Doesn't work if the track was killed
		if (isDead())
			return;
		
		// Stops the former play of the track if needed
		if (this.playing)
			stop();
		
		// Updates information
		this.currentindex = 0;
		this.currentloopcount = this.loopcounts[this.currentindex];
		this.currentsound = this.soundbank.getSound(this.soundnames[this.currentindex]);
		this.paused = false;
		this.delayed = false;
		this.loops = false;
		this.playing = true;
		
		// Plays the first sound
		this.currentsound.play(this);
	}
	
	/**
	 * Plays through the track repeatedly until stopped
	 */
	public void loop()
	{
		play();
		this.loops = true;
	}
	
	/**
	 * Stops the track from playing
	 */
	public void stop()
	{
		// Stops the current sound and the track
		this.playing = false;
		this.delayed = false;
		this.paused = false;
		this.currentsound.stopAll();
	}
	
	/**
	 * Pauses the track. The track can be continued from the same spot with 
	 * unpause method
	 */
	public void pause()
	{
		this.paused = true;
		this.currentsound.pauseAll();
	}
	
	/**
	 * Unpauses the track from the last state
	 */
	public void unpause()
	{
		this.paused = false;
		// Continues the track if it was delayed
		if (this.delayed)
			playnextsound();
	}
	
	private void playnextsound()
	{
		// Only plays the next sound if the track is still playing
		if (!this.playing)
			return;
		
		// The sound is no longer delayed
		this.delayed = false;
		
		// Checks whether more loops are needed
		// Loops the current sound if needed
		if (this.currentloopcount > 0)
		{
			this.currentloopcount --;
			this.currentsound = this.soundbank.getSound(this.soundnames[this.currentindex]);
			this.currentsound.play(this);
		}
		// Or plays the next sound
		else
		{
			// Gathers the information
			this.currentindex ++;
			
			// If the end of the track was reached, either repeats or stops
			if (this.currentindex > this.soundnames.length 
					|| this.currentindex > this.loopcounts.length)
			{
				if (this.loops)
					this.currentindex = 0;
				else
					return;
			}
			
			this.currentloopcount = this.loopcounts[this.currentindex];
			this.currentsound = this.soundbank.getSound(this.soundnames[this.currentindex]);
			
			// And plays the new sound
			this.currentsound.play(this);
		}
	}
}
