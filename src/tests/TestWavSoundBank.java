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
		createSound("wavs/qrqt_sound.wav", "qrq", 0, 0);
		createSound("wavs/unit_one_sound.wav", "unit", 0, 0);
		createSound("wavs/in_the_darkness_hidas_sound.wav", "darkness", 0, 0);
	}
}
