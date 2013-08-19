package listeners;

import handleds.LogicalHandled;

/**
 * Soundlistener reacts to a start and/or end of a sound playing
 *
 * @author Gandalf.
 *         Created 19.8.2013.
 */
public interface SoundListener extends LogicalHandled
{
	/**
	 * This method is called when a sound the listener listens to is played
	 *
	 * @param soundname The name of the sound that differentiates it from 
	 * different sounds (same as the sound's name in the soundbank)
	 */
	public void onSoundStart(String soundname);
	
	/**
	 * This method is called when a sound the listener listens to ends
	 *
	 * @param soundname The name of the sound that differentiates it from 
	 * different sounds (same as the sound's name in the soundbank)
	 */
	public void onSoundEnd(String soundname);
}
