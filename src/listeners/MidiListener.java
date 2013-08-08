package listeners;

import music.MidiMusic;
import handleds.LogicalHandled;

/**
 * Musiclisteners are interested when a music starts or stops playing or is paused
 *
 * @author Gandalf.
 *         Created 12.7.2013.
 */
public interface MidiListener extends LogicalHandled
{
	/**
	 * This method is called each time a midi is played for the first time
	 *
	 * @param midi The midi sound that was played
	 */
	public void onMidiPlayed(MidiMusic midi);
	
	/**
	 * This method is called each time a midi is stopped
	 *
	 * @param midi The midi sound that was stopped
	 */
	public void onMidiStopped(MidiMusic midi);
	
	/**
	 * This method is caused each time a midi is paused
	 *
	 * @param midi The midi that was paused
	 */
	public void onMidiPaused(MidiMusic midi);
	
	/**
	 * This method is called each time a midi is unpaused
	 *
	 * @param midi The midi that was unpaused
	 */
	public void onMidiUnpaused(MidiMusic midi);
}
