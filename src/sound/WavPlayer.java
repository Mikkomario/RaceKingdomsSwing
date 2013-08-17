package sound;

import java.io.File;
import java.io.IOException; 
import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.DataLine; 
import javax.sound.sampled.FloatControl; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.SourceDataLine; 
import javax.sound.sampled.UnsupportedAudioFileException; 

/**
 * An external class that answers for playing the sound effects in this game.
 * 
 * @author http://www.anyexample.com/programming/java/java_play_wav_sound_file.xml
 * Modified by: Gandalf
 */
 
public class WavPlayer extends Thread
{  
	// ATTRIBUTES	------------------------------------------------------
	
    private String filename;
    private float pan, volume;
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    private boolean paused;
 
    
    // CONSTRUCTOR	-----------------------------------------------------
    
    /**
     * Creates a new playwave with default settings
     *
     * @param wavfilename The path to the wav-audiofile
     */
	public WavPlayer(String wavfilename)
	{ 
        this.filename = wavfilename;
        this.pan = 0;
        this.volume = 0;
        this.paused = false;
    } 
	
	/**
	 * Creates a new playwave with custom settings
	 *
	 * @param wavfilename The path to the wav-audiofile
	 * @param pan How much the sound is panned [-1 (left speaker only), 
	 * 1 (right speaker only)] (0 by default)
	 * @param volume How much the volume is adjusted in desibels (default 0)
	 */
	public WavPlayer(String wavfilename, float pan, float volume)
	{ 
        this.filename = wavfilename;
        this.pan = pan;
        this.volume = volume;
        this.paused = false;
    }
	
	
	// IMPLEMENTED METHODS	----------------------------------------------
 
    @Override
	public void run()
    {
    	// Checks whether the file exists
        File soundFile = new File(this.filename);

        if (!soundFile.exists()) { 
            System.err.println("Wave file not found: " + this.filename);
            return;
        } 
    
        // Reads the file as an audioinputstream
        AudioInputStream audioInputStream = null;
        try
        {
            audioInputStream = AudioSystem.getAudioInputStream(
            		this.getClass().getResourceAsStream(this.filename));
        }
        catch (UnsupportedAudioFileException e1)
        {
        	System.err.println("Audiofile " + this.filename + " not supported!");
            e1.printStackTrace();
            return;
        }
        catch (IOException e1)
        {
        	System.err.println("Failed to load the audio file!");
            e1.printStackTrace();
            return;
        } 
 
        // Opens the audioline
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try
        { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        }
        catch (LineUnavailableException e)
        {
        	System.err.println("Audioline unavailable");
            e.printStackTrace();
            return;
        }
        /*
        catch (Exception e)
        { 
            e.printStackTrace();
            return;
        }
        */
 
        // Tries to set the correct pan (if needed)
        if (this.pan != 0 && auline.isControlSupported(FloatControl.Type.PAN))
        { 
            FloatControl pancontrol = (FloatControl) auline.getControl(
            		FloatControl.Type.PAN);
                pancontrol.setValue(this.pan);
        }
        // Tries to set the correct volume (if needed)
        if (this.volume != 0 && auline.isControlSupported(FloatControl.Type.VOLUME))
        { 
            FloatControl volumecontrol = (FloatControl) auline.getControl(
            		FloatControl.Type.VOLUME);
            volumecontrol.setValue(this.volume);
        } 
 
        // Plays the track
        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[this.EXTERNAL_BUFFER_SIZE];
 
        try
        { 
            while (nBytesRead != -1)
            {
            	// TODO: Test this pause function since it is only based on a hunch
            	if (!this.paused)
            	{
	                nBytesRead = audioInputStream.read(abData, 0, abData.length);
	                if (nBytesRead >= 0) 
	                    auline.write(abData, 0, nBytesRead);
            	}
            }
        }
        catch (IOException e)
        {
        	System.err.println("Error in playing the soundfile " + this.filename);
            e.printStackTrace();
            return;
        }
        // Closes the file after the sound has stopped playing
        finally
        { 
        	// TODO: Inform the caller that the sound has stopped
        	// TODO: But what information should be given...?
            auline.drain();
            auline.close();
        }
    }
    
    
    // OTHER METHODS	---------------------------------------------------
    
    /**
     * Temporarily stops the sound from playing (if it was playing)
     */
    public void pause()
    {
    	this.paused = true;
    }
    
    /**
     * Continues a paused sound
     */
    public void unpause()
    {
    	this.paused = false;
    }
}