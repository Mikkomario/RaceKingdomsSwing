package sound;

import java.io.FileNotFoundException;

import common.AbstractBank;

/**
 * Creates a HashMap containing some MidiMusics. The
 * class also allows access to these objects.
 * 
 * @author Unto Created 10.7.2013
 * 
 */
public abstract class MidiMusicBank extends AbstractBank
{
	// ABSTRACT METHODS -----------------------------------------------------
	
	/**
	 * Creates Midis with the createMidi()-method.
	 * 
	 * @throws FileNotFoundException	if all of the midis couldn't be loaded.
	 */
	public abstract void createMidis() throws FileNotFoundException;

	
	// IMPLEMENTED METHODS ---------------------------------------------------

	@Override
	protected void initialize()
	{
		// Creates the midis
		try
		{
			createMidis();
		} catch (FileNotFoundException fnfe)
		{
			System.err.println("Could not load all of the Midis!");
		}
	}
	
	@Override
	protected Class<?> getSupportedClass()
	{
		return MidiMusic.class;
	}
	
	
	// OTHER METHODS	--------------------------------------------------

	/**
	 * Creates a midi and stores it in the bank
	 * 
	 * @param fileName	File's name and location
	 * @param midiName	Name of the song in the bank.
	 */
	protected void createMidiMusic(String fileName, String midiName)
	{
		MidiMusic newMidi = new MidiMusic(fileName);
		addObject(newMidi, midiName);
	}

	/**
	 * Returns a midi from the MidiMusicBank.
	 * 
	 * @param midiName	Name of the wanted midi
	 * @return Returns the wanted midi if it is in the database, otherwise
	 *         returns null.
	 */
	public MidiMusic getMidi(String midiName)
	{
		return (MidiMusic) getObject(midiName);
	}
}
