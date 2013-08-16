package graphic;

import helpAndEnums.HelpMath;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Maskcheckker is uselful for objects that want to use masks in collision 
 * detection. Maskcheckker provides most of the tools needed for that. 
 * Animated sprites aren't currently supported and only the first subimage will 
 * be used.
 *
 * @author Gandalf.
 *         Created 2.7.2013.
 */
public class MaskChecker
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private Sprite mask;
	// Mask contains only bright red (255, 0, 0) pixels
	private static int maskcolor = -65536;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new maskchecker that uses the given mask
	 *
	 * @param mask The mask the checker uses for collision detection.
	 */
	public MaskChecker(Sprite mask)
	{
		// Initializes attributes
		this.mask = mask;
	}
	
	
	// GETTERS & SETTERS	---------------------------------------------
	
	/**
	 * @return The mask used in the spriteobject
	 */
	public Sprite getMask()
	{
		return this.mask;
	}
	
	/**
	 * @param newmask Changes the object's mask to a new one
	 */
	public void setMask(Sprite newmask)
	{
		this.mask = newmask;
	}
	
	
	// OTHER METHODS	-------------------------------------------------
	
	/**
	 * Refines the given relative collisiopoints to only hold the ones 
	 * that the sprite contains
	 *
	 * @param collisionpoints The relative collisionpoints to be refined
	 * @return The refined relative collisionpoints
	 */
	public Point[] getRefinedRelativeCollisionPoints(Point[] collisionpoints)
	{
		// In case the mask is null (= not used), simply returns the same points
		if (getMask() == null)
			return collisionpoints;
		
		// Removes the collisionpoints that aren't in the mask
		ArrayList<Point> templist = new ArrayList<Point>();
		// Adds all the relevant points to the list
		for (int i = 0; i < collisionpoints.length; i++)
		{
			if (maskContainsRelativePoint(collisionpoints[i]))
				templist.add(collisionpoints[i]);
		}
		// Adds all points from the list to the table
		Point[] newpoints = new Point[templist.size()];
		for (int i = 0; i < templist.size(); i++)
		{
			newpoints[i] = templist.get(i);
		}
		return newpoints;
	}
	
	/**
	 * Tells whether the mask contains the given relative point
	 *
	 * @param relativep The relative point tested
	 * @return Does the mask contain the given point
	 */
	public boolean maskContainsRelativePoint(Point relativep)
	{		
		// In case mask is not used (mask == null), always returns true
		if (getMask() == null)
			return true;
		
		// Checks whether the point is within the mask and returns false if 
		// it isn't
		if (!HelpMath.pointIsInRange(relativep, 0, getMask().getWidth(), 0, 
				getMask().getHeight()))
			return false;

		int c = this.mask.getSubImage(0).getRGB(relativep.x, relativep.y);
		return c == maskcolor;
	}
}
