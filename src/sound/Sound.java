package sound;

import listeners.SoundListener;

/**
 * Sounds can be played, stopped, paused, unpaused and listened to
 *
 * @author Gandalf.
 *         Created 19.8.2013.
 */
public interface Sound
{
	/**
	 * Plays the sound
	 *
	 * @param specificlistener A listener that will be informed upon the start 
	 * and end of this specific instance of the sound (null if no listener is needed)
	 */
	public void play(SoundListener specificlistener);
	
	/**
	 * Loops the sound until it is stopped
	 *
	 * @param specificlistener A listener that will be informed upon the start 
	 * and end of this specific instance of the sound (null if no listener is needed)
	 */
	public void loop(SoundListener specificlistener);
	
	/**
	 * Stops the sound from playing
	 */
	public void stop();
	
	/**
	 * Pauses the sound from playing
	 */
	public void pause();
	
	/**
	 * Continues the sound from the spot it was paused at
	 */
	public void unpause();
	
	/**
	 * Adds a soundlistener to the listeners that are informed when the sound 
	 * starts or ends
	 *
	 * @param s The soundlistener to be informed
	 */
	public void addListener(SoundListener s);
	
	/**
	 * Removes a soundlistener from the informed listeners
	 *
	 * @param s The soundlistener to be removed
	 */
	public void removeListener(SoundListener s);
}
