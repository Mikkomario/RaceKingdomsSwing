package tests;

import sound.WavSoundBank;

/**
 * This wavsoundbank holds the sounds used in the testing
 *
 * @author Gandalf.
 *         Created 18.8.2013.
 */
public class TestWavSoundBank extends WavSoundBank
{
	@Override
	protected void initialize()
	{
		// Loads the sound(s)
		createSound("wavs/wht_havio.wav", "noo", 0, 0);
	}
}
