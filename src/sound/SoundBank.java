package sound;

import common.AbstractBank;

/**
 * Soundbank is a bank that contains any types of sounds and can also provide 
 * any kinds of sound types.
 *
 * @author Gandalf.
 *         Created 22.8.2013.
 */
public abstract class SoundBank extends AbstractBank
{
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	protected Class<?> getSupportedClass()
	{
		// Soundbanks accept sound objects
		return Sound.class;
	}
	
	
	// OTHER METHODS	-------------------------------------------------
	
	/**
	 * Return a sound from the bank
	 *
	 * @param soundname The name of the sound fin the bank
	 * @return the sound from the bank
	 */
	public Sound getSound(String soundname)
	{
		return (Sound) getObject(soundname);
	}
}
