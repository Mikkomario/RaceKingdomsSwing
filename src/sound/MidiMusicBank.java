package sound;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Creates a HashMap containing all the 'MidiMusics' used in the project. The
 * class also allows access to these objects.
 * 
 * @author Unto Created 10.7.2013
 * 
 */
public abstract class MidiMusicBank {

	// ATTRIBUTES ---------------------------------------------------------

	private HashMap<String, MidiMusic> midis;

	// CONSTRUCTOR ---------------------------------------------------------
	/**
	 * Creates a new MidiMusicBank and loads all the Midis it needs.
	 */
	public MidiMusicBank() {
		this.midis = new HashMap<String, MidiMusic>();
		this.initializeMidis();
	}

	// ABSTRACT METHODS -----------------------------------------------------
	/**
	 * Creates Midis with the createMidi()-method.
	 * 
	 * @throws FileNotFoundException	if all of the midis couldn't be loaded.
	 */
	public abstract void createMidis() throws FileNotFoundException;

	// METHODS ---------------------------------------------------

	private void initializeMidis() {
		try {
			createMidis();
		} catch (FileNotFoundException fnfe) {
			System.err.println("Could not load all of the Midis!");
		}
	}

	/**
	 * Creates and puts a Midi to the 'midis' HashMap.
	 * 
	 * @param fileName	File's name and location
	 * @param midiName	Name of the song in the bank.
	 */
	protected void createMidiMusic(String fileName, String midiName) {
		MidiMusic newMidi = new MidiMusic(fileName);
		this.midis.put(midiName, newMidi);
	}

	/**
	 * Returns a midi from the MidiMusicBank.
	 * 
	 * @param midiName	Name of the wanted midi
	 * @return Returns the wanted midi if it is in the database, otherwise
	 *         returns null.
	 */
	public MidiMusic getMidi(String midiName) {
		if (this.midis.containsKey(midiName)) {
			return this.midis.get(midiName);
		} else {
			System.out.println("Midi " + midiName
					+ " doesn't exist in the bank!");
			return null;
		}
	}

}
