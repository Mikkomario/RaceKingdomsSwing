package camera;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import listeners.CameraListener;
import handleds.Collidable;
import handleds.Drawable;
import handlers.ActorHandler;
import handlers.CameraListenerHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DepthConstants;
import helpAndEnums.DoublePoint;
import helpAndEnums.HelpMath;
import drawnobjects.CollidingDrawnObject;
import drawnobjects.DimensionalDrawnObject;
import drawnobjects.DrawnObject;
import drawnobjects.BasicPhysicDrawnObject;

/**
 * This object acts as the camera of the game, drawing multiple elements from the 
 * world into a smaller screen
 *
 * @author Gandalf.
 *         Created 16.6.2013.
 */
public class BasicCamera extends BasicPhysicDrawnObject
{
	// ATTRIBUTES	------------------------------------------------------
	
	private CameraDrawer followerhandler;
	private CameraListenerHandler listenerhandler;
	private int screenWidth, screenHeight;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new basic camera added to the given handlers and starting 
	 * from the given position
	 *
	 * @param x The camera's new x-coordinate
	 * @param y The camera's new y-coordinate
	 * @param drawer The drawablehandler that will draw the camera and objects 
	 * it shows
	 * @param actorhandler The actorhandler that informs the camera about 
	 * the act-event
	 * @param screenWidth The width of the screen
	 * @param screenHeight The height of the screen
	 */
	public BasicCamera(int x, int y, int screenWidth, int screenHeight,  
			DrawableHandler drawer, ActorHandler actorhandler)
	{
		super(x, y, DepthConstants.BACK, false, CollisionType.BOX, drawer, 
				null, null, actorhandler);
		
		// Initializes attributes
		this.listenerhandler = new CameraListenerHandler(true, null);
		this.followerhandler =  new CameraDrawer(false, this);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		// Informs the listeners about the camera's position
		informStatus();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public int getOriginX()
	{
		return -this.screenWidth / 2;
	}

	@Override
	public int getOriginY()
	{
		return -this.screenHeight / 2;
	}

	@Override
	public void drawSelfBasic(Graphics2D g2d)
	{
		this.followerhandler.drawSelf(g2d);
	}
	
	@Override
	public void drawSelf(Graphics2D g2d)
	{
		AffineTransform trans = g2d.getTransform();
		
		// and translates the origin to the right position
		g2d.translate((double) -getOriginX(), (double) -getOriginY());
		// scales it depending on it's xscale and yscale
		g2d.scale(getXscale(), getYscale());
		// rotates it depending on its angle
		g2d.rotate(Math.toRadians((360 - getAngle())));
		// Translates the sprite to the object's position
		g2d.translate(getX(), getY());
		
		// Finally draws the object
		drawSelfBasic(g2d);
		
		// Loads the previous transformation
		g2d.setTransform(trans);
	}
	
	@Override
	public int getWidth()
	{
		return this.screenWidth;
	}

	@Override
	public int getHeight()
	{
		return this.screenHeight;
	}
	
	/*
	public boolean objectCollides(Collidable c)
	{
		DrawnObject2D d = null;
		
		// Only works with drawnobjects currently
		if (c instanceof DrawnObject2D)
			d = (DrawnObject2D) c;
		else
			return false;
			
		// Negates the transformations for both objects
		Point negatedPosOther =
				negateTransformations((int) d.getX(), (int) d.getY(), (int) -getX(), 
						(int) -getY(), 1 / getXscale(), 1 / getYscale(), (int) -getAngle(), 
						(int) -getOriginX(), (int) -getOriginY());
		Point negatedPosThis =
				d.negateTransformations((int) -getX(), (int) -getY());
		
		int widthThis = getWidth();
		int widthOther = d.getWidth();
		int heightThis = getHeight();
		int heightOther = d.getHeight();
		
		if (negatedPosOther.x + widthOther < negatedPosThis.x)
			return false;
		else if (negatedPosOther.x > negatedPosThis.x + widthThis)
			return false;
		else if (negatedPosOther.y + heightOther < negatedPosThis.y)
			return false;
		else if (negatedPosOther.y > negatedPosThis.y + heightThis)
			return false;
		else
			return true;
	}
	*/
	
	@Override
	public Collidable pointCollides(int x, int y)
	{
		// In cameras, transformations are reversed so they need to be used 
		// in a different manner (PS: I know it's not DRY)
		
		// Negates the transformation
		Point negatedPoint = negateTransformations(x, y, -getX(), 
				-getY(), 1 / getXscale(), 1 / getYscale(), 
				-getAngle(), -getOriginX(), -getOriginY());
		
		// Returns the object if it collides with the point
		if (HelpMath.pointIsInRange(negatedPoint, 0, 
				getWidth(), 0, getHeight()))
			return this;
		else
			return null;
	}
	
	@Override
	public void onCollision(ArrayList<DoublePoint> collisionpoints, Collidable collided)
	{
		// Doesn't do anything upon collision
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Adds an drawable to the drawables the camera draws
	 *
	 * @param drawable The object the camera will draw
	 */
	public void addDrawable(Drawable drawable)
	{
		this.followerhandler.addDrawable(drawable);
	}
	
	/**
	 * Adds a new cameralistener to the camera
	 *
	 * @param listener The new cameralistener
	 */
	public void addCameraListener(CameraListener listener)
	{
		this.listenerhandler.addListener(listener);
	}
	
	private void informStatus()
	{
		// Doesn't inform the "real" values but the more easily understandable 
		// ones
		this.listenerhandler.informCameraPosition(
				(int) getX(), (int) getY(), 
				(int) Math.abs(this.screenWidth * getXscale()), 
				(int) Math.abs(this.screenHeight * getYscale()), 
				(int) HelpMath.checkDirection(getAngle()));
	}
	
	/**
	 * Tells whether an object should be drawn on the camera or not
	 *
	 * @param d The object that may be drawn
	 * @return Should the object be drawn
	 */
	protected boolean objectShouldBeDrawn(DrawnObject d)
	{
		// If the drawnobject is collidingdrawnobject, checks the drawing carefully
		if (d instanceof CollidingDrawnObject)
		{
			CollidingDrawnObject cd = (CollidingDrawnObject) d;
			
			DoublePoint[] collisionpoints = cd.getCollisionPoints();
			
			// Does NOT check if the object is solid or not! (used for drawing 
			// so the visible-status is used instead)
			// Invisible objects are never drawn
			if (!cd.isVisible())
				return false;
						
			// Returns true if any of the collisionpoints collides
			for (int i = 0; i < collisionpoints.length; i++)
			{
				if (pointCollides((int) collisionpoints[i].getX(), 
						(int) collisionpoints[i].getY()) != null)
					return true;
			}
			
			return false;
		}
		// Dimensionalobjects are drawn if they are neat the camera
		// Draws a bit more objects than necessary
		else if (d instanceof DimensionalDrawnObject)
		{
			DimensionalDrawnObject dd = (DimensionalDrawnObject) d;
			
			// Checks if it's possible that any point of the object would be shown
			double maxrange = dd.getMaxRangeFromOrigin() + getMaxRangeFromOrigin();
			
			return HelpMath.pointDistance(-getX(), -getY(), dd.getX(), dd.getY()) 
					< maxrange;
		}
		// Other objects are always drawn
		return true;
	}
}
