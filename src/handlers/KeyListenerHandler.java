package handlers;

import handleds.Handled;
import listeners.KeyListener;

/**
 * This class handles a bunch of keylisteners
 *
 * @author Gandalf.
 *         Created 14.12.2012.
 */
public class KeyListenerHandler extends LogicalHandler implements KeyListener
{

	/**
	 * Creates a new empty keylistenerhandler. Listeners must be added manually
	 *
	 * @param autodeath Will the handler die when it runs out of living handleds
	 * @param superhandler The handler that will handle this handler (optional)
	 */
	public KeyListenerHandler(boolean autodeath, KeyListenerHandler superhandler)
	{
		super(autodeath, superhandler);
	}

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		for (int i = 0; i < getHandledNumber(); i++)
		{
			getListener(i).onKeyDown(key, keyCode, coded);
		}
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded)
	{
		for (int i = 0; i < getHandledNumber(); i++)
		{
			getListener(i).onKeyPressed(key, keyCode, coded);
		}
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		for (int i = 0; i < getHandledNumber(); i++)
		{
			getListener(i).onKeyReleased(key, keyCode, coded);
		}
	}
	
	@Override
	protected Class<?> getSupportedClass()
	{
		return KeyListener.class;
	}
	
	
	// OTHER METHODS	---------------------------------------------------
	
	// Casts a handled to listener
	private KeyListener getListener(int index)
	{
		Handled maybeListener = getHandled(index);
		
		if (maybeListener instanceof KeyListener)
			return (KeyListener) maybeListener;
		else
			return null;
	}
	
	/**
	 * Adds a new listener to the informed listeners
	 *
	 * @param k The KeyListener added
	 */
	public void addKeyListener(KeyListener k)
	{
		//System.out.println("Adds a listener " + k.getClass().getName());
		super.addHandled(k);
	}
}
