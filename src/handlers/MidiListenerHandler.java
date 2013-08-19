package handlers;

import sound.MidiMusic;
import handleds.Handled;
import listeners.MidiListener;

/**
 * MidiListenerHandler handles a number of midilisteners and informs them when 
 * needed
 *
 * @author Gandalf.
 *         Created 12.7.2013.
 */
public class MidiListenerHandler extends LogicalHandler implements MidiListener
{
	// CONSTRUCTOR	----------------------------------------------------
	
	/**
	 * Creates a new empty midilistenerhandler
	 *
	 * @param autodeath Will the handler die automatically when it runs out 
	 * of listeners
	 * @param superhandler The midilistenerhandler that will inform this 
	 * handler about the events (optional)
	 */
	public MidiListenerHandler(boolean autodeath, MidiListenerHandler superhandler)
	{
		super(autodeath, superhandler);
	}
	
	
	// IMPLEMENTED METHODS	--------------------------------------------

	@Override
	protected Class<?> getSupportedClass()
	{
		return MidiListener.class;
	}

	@Override
	public void onMidiPlayed(MidiMusic midi)
	{
		// Cleans unnecessary handleds
		removeDeadHandleds();
		
		// Informs all active listeners
		for (int i = 0; i < getHandledNumber(); i++)
		{
			MidiListener l = getListener(i);
			if (l.isActive())
				l.onMidiPlayed(midi);
		}
	}

	@Override
	public void onMidiStopped(MidiMusic midi)
	{
		// Cleans unnecessary handleds
		removeDeadHandleds();
		
		// Informs all active listeners
		for (int i = 0; i < getHandledNumber(); i++)
		{
			MidiListener l = getListener(i);
			if (l.isActive())
				l.onMidiStopped(midi);
		}
	}

	@Override
	public void onMidiPaused(MidiMusic midi)
	{
		// Cleans unnecessary handleds
		removeDeadHandleds();
		
		// Informs all active listeners
		for (int i = 0; i < getHandledNumber(); i++)
		{
			MidiListener l = getListener(i);
			if (l.isActive())
				l.onMidiPaused(midi);
		}
	}

	@Override
	public void onMidiUnpaused(MidiMusic midi)
	{
		// Cleans unnecessary handleds
		removeDeadHandleds();
		
		// Informs all active listeners
		for (int i = 0; i < getHandledNumber(); i++)
		{
			MidiListener l = getListener(i);
			if (l.isActive())
				l.onMidiUnpaused(midi);
		}
	}

	// OTHER METHODS	-------------------------------------------------
	
	/**
	 * Adds a new listener to the handled listeners
	 *
	 * @param m The listener to be added
	 */
	public void addMidiListener(MidiListener m)
	{
		addHandled(m);
	}
	
	/**
	 * Removes a midilistener from the informed listeners
	 *
	 * @param m The midilistener to be removed
	 */
	public void removeMidiListener(MidiListener m)
	{
		removeHandled(m);
	}
	
	private MidiListener getListener(int index)
	{
		// Casts the handled to either listener or null
		Handled maybelistener = getHandled(index);
		
		if (maybelistener instanceof MidiListener)
			return (MidiListener) maybelistener;
		else
			return null;
	}
}
