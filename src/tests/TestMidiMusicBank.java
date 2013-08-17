package tests;

import java.io.FileNotFoundException;

import sound.MidiMusicBank;


/**
 * This class holds a midi used in testing.
 * 
 * @author Unto 
 * 			Created 12.7.2013
 * 
 */
public class TestMidiMusicBank extends MidiMusicBank{

	
	//No constructor needed!?
	
	// IMPLEMENTED METHODS	------------------------------------------------
	
	@Override
	public void createMidis() throws FileNotFoundException {
		createMidiMusic("data/midis/testmidi.mid", "test");
	}
	
	

}