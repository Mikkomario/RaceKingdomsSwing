package tests;

import graphic.SpriteDrawer;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import helpAndEnums.DepthConstants;

import java.awt.Graphics2D;

import drawnobjects.DrawnObject;

/**
 * This class draws an animated sprite
 *
 * @author Gandalf.
 *         Created 15.8.2013.
 */
public class TestSpriteObject extends DrawnObject
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private SpriteDrawer spritedrawer;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new testspriteobject
	 *
	 * @param x The sprite's x-coordinate
	 * @param y The sprite's new y-coordinate
	 * @param drawer The drawablehandler that will draw the sprite
	 * @param animator The actorhandler that will animate the sprite
	 */
	public TestSpriteObject(int x, int y, DrawableHandler drawer, ActorHandler animator)
	{
		super(x, y, DepthConstants.NORMAL, drawer);
		
		// Initializes attributes
		this.spritedrawer = new SpriteDrawer(
				new TestSpriteBank().getSprite("mushroom"), animator);
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public int getOriginX()
	{
		// TODO: This method is called before the constructor has finished 
		// creating the object!?!
		if (this.spritedrawer == null)
			return 0;
		
		return this.spritedrawer.getSprite().getOriginX();
	}

	@Override
	public int getOriginY()
	{
		if (this.spritedrawer == null)
			return 0;
		
		return this.spritedrawer.getSprite().getOriginY();
	}

	@Override
	public void drawSelfBasic(Graphics2D g2d)
	{
		// TODO: This too, is called too early
		if (this.spritedrawer == null)
			return;
		
		// Draws the sprite
		//System.out.println("Draws the sprite");
		this.spritedrawer.drawSprite(g2d);
	}
}
