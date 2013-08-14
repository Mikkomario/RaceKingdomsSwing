package tests;

import java.awt.Graphics2D;
import java.awt.Point;

import handlers.CollidableHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DepthConstants;
import drawnobjects.DimensionalDrawnObject;

/**
 * This class is a simple box that can be drawn
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public class TestBox extends DimensionalDrawnObject
{
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new testbox that does nothing but can be drawn
	 * @param drawer The handler that will draw the object (optional)
	 * @param collidablehandler The collidablehandler that handles the box's 
	 * collision checking.
	 */
	public TestBox(DrawableHandler drawer, CollidableHandler collidablehandler)
	{
		super(300, 300, DepthConstants.NORMAL, true, CollisionType.BOX, drawer, 
				collidablehandler);
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public int getOriginX()
	{
		return 50;
	}

	@Override
	public int getOriginY()
	{
		return 50;
	}

	@Override
	public void drawSelfBasic(Graphics2D g2d)
	{
		g2d.drawRect(0, 0, 100, 100);
	}

	@Override
	public int getWidth()
	{
		return 100;
	}

	@Override
	public int getHeight()
	{
		return 100;
	}
	
	// OTHER METHODS	-------------------------------------------------
	
	/**
	 * Prints both the given and transformed arguments
	 *
	 * @param x The x to be transformed
	 * @param y The y to be transformed
	 */
	protected void testTransformation(int x, int y)
	{
		Point testpoint = negateTransformations(x, y);
		
		//System.out.println("X: " + x + ", Y: " + y + ", X2: " + testpoint.x 
		//		+ ", Y2: " + testpoint.y);
		
		System.out.println("DX: " + (x - testpoint.x) + ", DY: " + (y - testpoint.y));
	}
}
