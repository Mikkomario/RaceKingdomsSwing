package sound;

import handlers.SoundListenerHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import listeners.SoundListener;

import common.BankObject;

/**
 * WavSound represents a single sound that can be played during the program. 
 * Each sound has a name so it can be differentiated from other wavsounds.
 *
 * @author Gandalf.
 *         Created 17.8.2013.
 */
public class WavSound implements BankObject
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private LinkedList<WavPlayer> players;
	private String filename, name;
	private float defaultvolume, defaultpan;
	private SoundListenerHandler listenerhandler;
	
	
	// CONSTRUCTOR	----------------------------------------------------
	
	/**
	 * Creates a new wawsound with the given information
	 *
	 * @param filename The location of the wav sound file (src/data/ is 
	 * automatically included)
	 * @param name The name of the sound that differentiates it from other 
	 * sounds
	 * @param defaultvolume How many desibels the volyme is adjusted at default
	 * @param defaultpan How much pan is added at default [-1, 1]
	 */
	public WavSound(String filename, String name, float defaultvolume, 
			float defaultpan)
	{		
		// Initializes attributes
		this.filename = "/data/" + filename;
		this.name = name;
		this.defaultvolume = defaultvolume;
		this.defaultpan = defaultpan;
		this.players = new LinkedList<WavPlayer>();
		this.listenerhandler = new SoundListenerHandler(false, null);
	}
	
	
	// IMPLEMENTED METHODS	--------------------------------------------
	
	@Override
	public boolean kill()
	{
		// Stops the current sounds
		stopAll();
		// Kills the listenerhandler as well (not the listeners though)
		this.listenerhandler.killWithoutKillingHandleds();
		return true;
	}
	
	
	// GETTERS & SETTERS	--------------------------------------------
	
	/**
	 * @return The name of the sound
	 */
	public String getName()
	{
		return this.name;
	}
	
	
	// OTHER METHODS	------------------------------------------------
	
	private void startsound(float volume, float pan, boolean loops, SoundListener 
			specificlistener)
	{
		WavPlayer newplayer = new WavPlayer(pan, volume, loops, specificlistener);
		newplayer.start();
		this.players.add(newplayer);
		// Also informs the listeners
		if (specificlistener != null)
			specificlistener.onSoundStart(getName());
		this.listenerhandler.onSoundStart(getName());
	}
	
	/**
	 * Plays the sound using the give settings
	 *
	 * @param volume How many desibels the sound's volume is increased / decreased
	 * @param pan How much the sound is panned [-1, 1]
	 * @param specificlistener A listener that listens to only this instance of 
	 * the sound (null if no listener is needed)
	 */
	public void play(float volume, float pan, SoundListener specificlistener)
	{
		startsound(volume, pan, false, specificlistener);
	}
	
	/**
	 * Plays the sound using the default settings
	 * 
	 * @param specificlistener A listener that listens to only this instance of 
	 * the sound (null if no listener is needed)
	 */
	public void play(SoundListener specificlistener)
	{
		startsound(this.defaultvolume, this.defaultpan, false, specificlistener);
	}
	
	/**
	 * Loops the sound using the give settings
	 *
	 * @param volume How many desibels the sound's volume is increased / decreased
	 * @param pan How much the sound is panned [-1, 1]
	 * 
	 * @param specificlistener A listener that listens to only this instance of 
	 * the sound (null if no listener is needed)
	 */
	public void loop(float volume, float pan, SoundListener specificlistener)
	{
		startsound(volume, pan, true, specificlistener);
	}
	
	/**
	 * Loops the sound using the default settings
	 * 
	 * @param specificlistener A listener that listens to only this instance of 
	 * the sound (null if no listener is needed)
	 */
	public void loop(SoundListener specificlistener)
	{
		startsound(this.defaultvolume, this.defaultpan, true, specificlistener);
	}
	
	/**
	 * Stops the oldest playing instance of the sound
	 */
	public void stop()
	{
		// Stops the oldest wavplayer
		this.players.getFirst().stopSound();
	}
	
	/**
	 * Stops all instances of the sound from playing
	 */
	public void stopAll()
	{
		// Stops all of the sounds playing
		Iterator<WavPlayer> i = this.players.iterator();
		
		while (i.hasNext())
			i.next().stopSound();
	}
	
	/**
	 * Pauses the oldest unpaused instance of the sound
	 */
	public void pause()
	{
		Iterator<WavPlayer> i = this.players.iterator();
		
		while(i.hasNext())
		{
			WavPlayer p = i.next();
			if (!p.paused)
			{
				p.pause();
				break;
			}
		}
	}
	
	/**
	 * Pauses all instances of the sound playing
	 */
	public void pauseAll()
	{
		// Stops all of the sounds playing
		Iterator<WavPlayer> i = this.players.iterator();
		
		while (i.hasNext())
			i.next().pause();
	}
	
	/**
	 * Unpauses the oldest paused instance of the sound
	 */
	public void unpause()
	{
		Iterator<WavPlayer> i = this.players.iterator();
		
		while(i.hasNext())
		{
			WavPlayer p = i.next();
			if (p.paused)
			{
				p.unpause();
				break;
			}
		}
	}
	
	/**
	 * Unpauses all the paused instances of the sound
	 */
	public void unpauseAll()
	{
		// Stops all of the sounds playing
		Iterator<WavPlayer> i = this.players.iterator();
		
		while (i.hasNext())
			i.next().unpause();
	}
	
	/**
	 * @return Is there any instance of the sound currently playing or paused
	 */
	public boolean isPlaying()
	{
		// Paused sounds are also counted as playing
		return this.players.isEmpty();
	}
	
	/**
	 * Adds a soundlistener to the informed listeners (will be called each time 
	 * an instance of the sound starts or ends)
	 *
	 * @param l The listener to be informed
	 */
	public void addListener(SoundListener l)
	{
		this.listenerhandler.addListener(l);
	}
	
	/**
	 * Removes a soundlistener from the informed listeners
	 *
	 * @param l The listener to be removed
	 */
	public void removeListener(SoundListener l)
	{
		this.listenerhandler.removeListener(l);
	}
	
	private void onSoundEnd(WavPlayer source)
	{
		// Removes the old player from the list of players
		this.players.remove(source);
		
		// If the sound should loop, plays it again
		if (source.looping)
			loop(source.volume, source.pan, source.listener);
		// Otherwise, informs the listeners
		else
		{
			if (source.listener != null)
				source.listener.onSoundEnd(getName());
			this.listenerhandler.onSoundEnd(getName());
		}
	}
	
	
	// SUBCLASSES	-----------------------------------------------------
	
	/**
	 * An external class that answers for playing the sound effects in this game.
	 * 
	 * @author http://www.anyexample.com/programming/java/java_play_wav_sound_file.xml
	 * Modified by: Gandalf
	 */
	private class WavPlayer extends Thread
	{  
		// ATTRIBUTES	------------------------------------------------------
		
	    private float pan, volume;
	    private final int EXTERNAL_BUFFER_SIZE = 262144;//64kb //524288; // 128Kb
	    private boolean paused, looping, stopped;
	    private SoundListener listener;
	 
	    
	    // CONSTRUCTOR	-----------------------------------------------------
		
		/**
		 * Creates a new playwave with custom settings
		 *
		 * @param pan How much the sound is panned [-1 (left speaker only), 
		 * 1 (right speaker only)] (0 by default)
		 * @param volume How much the volume is adjusted in desibels (default 0)
		 * @param loops Should the sound be looped after it ends?
		 * @param specificlistener A listener that listens specifically this 
		 * instance of the sound
		 */
		public WavPlayer(float pan, float volume, boolean loops, 
				SoundListener specificlistener)
		{
	        this.pan = pan;
	        this.volume = volume;
	        this.paused = false;
	        this.looping = loops;
	        this.stopped = false;
	        this.listener = specificlistener;
	    }
		
		
		// IMPLEMENTED METHODS	----------------------------------------------
	 
	    @Override
		public void run()
	    {
	    	/*
	    	// Checks whether the file exists
	        File soundFile = new File(WavSound.this.filename);

	        if (!soundFile.exists()) { 
	            System.err.println("Wave file not found: " + 
	            		WavSound.this.filename);
	            return;
	        }
	        */
	    
	        // Reads the file as an audioinputstream
	        AudioInputStream audioInputStream = null;
	        try
	        {
	        	//System.out.println("Loads the file " + WavSound.this.filename);
	            //System.out.println(WavSound.this.getClass().getResourceAsStream(WavSound.this.filename));
	        	audioInputStream = AudioSystem.getAudioInputStream(
	            		this.getClass().getResourceAsStream(WavSound.this.filename));
	        }
	        catch (UnsupportedAudioFileException e1)
	        {
	        	System.err.println("Audiofile " + WavSound.this.filename + 
	        			" not supported!");
	            e1.printStackTrace();
	            return;
	        }
	        catch (IOException e1)
	        {
	        	System.err.println("Failed to load the audio file!");
	            e1.printStackTrace();
	            return;
	        } 
	        catch (NullPointerException npe)
	        {
	        	System.err.println("Could not find the file " + WavSound.this.filename);
	        	npe.printStackTrace();
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
	        	// Reads the stream until it ends or until the sound is forced 
	        	// to stop
	            while (nBytesRead != -1 && !this.stopped)
	            {
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
	        	System.err.println("Error in playing the soundfile " + 
	        			WavSound.this.filename);
	            e.printStackTrace();
	            return;
	        }
	        // Closes the file after the sound has stopped playing
	        finally
	        { 
	            auline.drain();
	            auline.close();
	            // Also informs the WavSound that the current file ended
	            onSoundEnd(this);
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
	    
	    /**
	     * Stops the sound from playing and looping
	     */
	    public void stopSound()
	    {
	    	this.stopped = true;
	    	this.looping = false;
	    }
	}
}
