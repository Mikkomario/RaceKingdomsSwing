package sound;

import common.AbstractBank;

/**
 * This class holds multiple wavsoundtracks in it and offers them to those who 
 * need them
 *
 * @author Gandalf.
 *         Created 19.8.2013.
 */
public abstract class WavSoundTrackBank extends AbstractBank
{
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	protected Class<?> getSupportedClass()
	{
		return SoundTrack.class;
	}

	
	// OTHER METHODS	-------------------------------------------------
	
	/**
	 * Creates a new wavsoundtrack and adds it to the bank
	 *
	 * @param soundnames A table containing the names of the sounds forming the track
	 * @param loopcounts A table containing the information about how many times 
	 * each sound is looped
	 * @param soundbank A wavsoundbank containing the sounds used in the track
	 * @param trackname The name of the new track in the bank
	 */
	protected void createTrack(String[] soundnames, int[] loopcounts, 
			WavSoundBank soundbank, String trackname)
	{
		SoundTrack newtrack = new SoundTrack(soundnames, loopcounts, 
				soundbank, trackname);
		addObject(newtrack, trackname);
	}
	
	/**
	 * Returns a certain track from the bank
	 *
	 * @param trackname The name of the track in the bank
	 * @return a track with the given name or null if no track with the given name 
	 * exists in the bank
	 */
	public SoundTrack getTrack(String trackname)
	{
		return (SoundTrack) getObject(trackname);
	}
}
