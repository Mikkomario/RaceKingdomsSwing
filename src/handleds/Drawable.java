package handleds;

import java.awt.Graphics2D;

/**
 * All objects which implement this class can be drawn. Each also has a depth 
 * that specifies the layer to which the object is drawn. Drawables can also be 
 * made temporarily and permanently invisible.
 *
 * @author Mikko Hilpinen.
 *         Created 26.11.2012.
 */
public interface Drawable extends Handled
{
	/**
	 * Draws the object
	 *
	 * @param g2d The graphics object that will draw the object
	 */
	public void drawSelf(Graphics2D g2d);
	
	/**
	 * @return Should the object be drawn at this time
	 */
	public boolean isVisible();
	
	/**
	 * Tries to set the object visible
	 *
	 * @return Was the object made visible
	 */
	public boolean setVisible();
	
	/**
	 * Tries to momentarily make the object invisible
	 *
	 * @return Was the object made invisible
	 */
	public boolean setInvisible();
	
	/**
	 * @return How deep should the object be drawn (object with positive depth 
	 * are drawn to the bottom, objects with negative depth are drawn to the top)
	 * @see depthConstants
	 */
	public int getDepth();
	
	/**
	 * Tries to change the objects depth
	 * 
	 * @param depth The object's new depth (negative = top, positive = bottom)
	 * @return Was the object's depth successfully changed
	 * @see depthConstants
	 */
	public boolean setDepth(int depth);
}
