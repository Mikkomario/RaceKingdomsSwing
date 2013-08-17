package sound;

import java.util.HashMap;

/**
 * A wavsoundbank holds numerous wavsounds and gives them for the other objects 
 * to use
 *
 * @author Gandalf.
 *         Created 17.8.2013.
 */
public abstract class WavSoundBank
{
	// ATTRIBUTES ---------------------------------------------------------

	private HashMap<String, WavSound> sounds;
	

	// CONSTRUCTOR ---------------------------------------------------------
	
	/**
	 * Creates a new MidiMusicBank and loads all the Midis it needs.
	 */
	public WavSoundBank()
	{
		this.sounds = new HashMap<String, WavSound>();
		this.initializeSounds();
	}

	
	// ABSTRACT METHODS -----------------------------------------------------
	
	/**
	 * Creates Midis with the createSound()-method.
	 */
	public abstract void createSounds();

	
	// METHODS ---------------------------------------------------

	private void initializeSounds()
	{
		createSounds();
	}

	/**
	 * Creates and puts a sound to the bank
	 * 
	 * @param filename The name of the wav-file
	 * @param soundname The name of the sound in the bank
	 * @param defvolume How many desibels the volume is adjusted by default
	 * @param defpan How much the sound is panned by default [-1 (left speaker 
	 * only, 1 (right speaker only)]
	 */
	protected void createSound(String filename, String soundname, float defvolume, 
			float defpan)
	{
		WavSound newsound = new WavSound(filename, soundname, defvolume, defpan);
		this.sounds.put(soundname, newsound);
	}

	/**
	 * Returns a sound from the bank.
	 * 
	 * @param soundname The name of the sound in the bank
	 * @return The sound with the given name or null if no such sound was found
	 */
	public WavSound getSound(String soundname)
	{
		if (this.sounds.containsKey(soundname))
		{
			return this.sounds.get(soundname);
		}
		else
		{
			System.out.println("WavSound " + soundname
					+ " doesn't exist in the bank!");
			return null;
		}
	}
}
