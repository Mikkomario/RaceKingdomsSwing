package listeners;

import handleds.LogicalHandled;

/**
 * Keylisteners are interested in the user's activities on the keyboard and should 
 * be notified when a key is activated
 *
 * @author Gandalf.
 *         Created 28.11.2012.
 */
public interface AdvancedKeyListener extends LogicalHandled
{
	/**
	 * This method is called at each step when a key is down
	 *
	 * @param key The key that is currently pressed
	 * @param keyCode The key's keycode (used for some keys)
	 * @param coded Is the pressed key coded
	 */
	public void onKeyDown(int key, int keyCode, boolean coded);
	
	/**
	 * This method is called once when a key is pressed
	 *
	 * @param key The key that is currently pressed
	 * @param keyCode The key's keycode (used for some keys)
	 * @param coded Is the pressed key coded
	 */
	public void onKeyPressed(int key, int keyCode, boolean coded);
	
	/**
	 * This method is called when a key is released
	 *
	 * @param key The key that is currently pressed
	 * @param keyCode The key's keycode (used for some keys)
	 * @param coded Is the pressed key coded
	 */
	public void onKeyReleased(int key, int keyCode, boolean coded);
}
