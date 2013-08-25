package tests;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import drawnobjects.DimensionalDrawnObject;

import graphic.SpriteBank;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import helpAndEnums.DoublePoint;
import helpAndEnums.HelpMath;
import racing.Car;

/**
 * This car tests the collision detection
 *
 * @author Gandalf.
 *         Created 25.6.2013.
 */
public class CollisionTestCar extends Car
{
	// ATTRIBUTES	------------------------------------------------------
	
	/*
	private static Point[] relcolpoints = {new Point(1, 48), new Point(24, 27), 
		new Point(31, 16), new Point(59, 14), new Point(85, 14), new Point(111, 19), 
		new Point(127, 28), new Point(128, 42), new Point(122, 53), new Point(126, 65), 
		new Point(129, 72), new Point(117, 83), new Point(73, 87), new Point(59, 87), 
		new Point(31, 82)};
	*/
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new car with the given information. The car is automatically 
	 * added to the necessary handlers
	 *
	 * @param drawer The drawer that will draw the car
	 * @param collidablehandler The collidablehandler that will handle the 
	 * car's collision checking
	 * @param actorhandler The actorhandler that will call the car's act event
	 * @param keyhandler The keyhandler that informs the car about keypresses
	 * @param carspritebank The spritebank holding "testcar" sprite
	 * @param collisionhandler The collisionhandler that will inform the car 
	 * about collisions
	 */
	public CollisionTestCar(DrawableHandler drawer, 
			CollidableHandler collidablehandler, CollisionHandler collisionhandler,
			ActorHandler actorhandler, KeyListenerHandler keyhandler,
			SpriteBank carspritebank)
	{
		super(500, 300, drawer, collidablehandler, collisionhandler, 
				actorhandler, keyhandler, carspritebank, "test", "testcarmask");
		
		//setSprite(getMask());
		
		// Just tests the miscalculations during transform and negatetransform
		/*
		DoublePoint p = new DoublePoint(0, 0);
		System.out.println("Start: " + p);
		for (int i = 0; i < 10; i++)
		{
			p = negateTransformations(p.getX(), p.getY());
			System.out.println("Relative" + i + ": X " + p.x + " Y " + p.y);
			p = transform(p.x, p.y);
			System.out.println("Absolute" + i + ": X " + p.x + " Y " + p.y);
		}
		*/
		
		// Sets custom collisionpoints
		//setRelativeCollisionPoints(relcolpoints);
		setBoxCollisionPrecision(5, 5);
		//System.out.println(HelpMath.getVectorDirection(1, -1));
	}
	
	
	// IMPLEMENTED METHODS
	
	@Override
	public void onCollision(ArrayList<DoublePoint> collisionpoints, Collidable collided)
	{
		// Bounces away from dimensionaldrawnobjects
		if (collided instanceof DimensionalDrawnObject)
		{
			DimensionalDrawnObject d = (DimensionalDrawnObject) collided;
			
			for (int i = 0; i < collisionpoints.size(); i++)
				bounceFrom(d, collisionpoints.get(i), 0.1, 0.2);
		}
	}
	
	@Override
	public void drawSelf(Graphics2D g2d)
	{
		// Also draws the collisionpoints
		super.drawSelf(g2d);
		
		g2d.setColor(new Color(255, 0, 0));
		
		for (DoublePoint p: getCollisionPoints())
		{
			g2d.drawRect((int) p.getX(), (int) p.getY(), 5, 5);
		}
		
		g2d.setColor(new Color(0, 0, 0));
	}
	
	@Override
	public void onKeyDown(char key, int keyCode, boolean coded)
	{
		// Scales the object with W and S
		super.onKeyDown(key, keyCode, coded);
		
		if (!coded)
		{
			if (key == 'w')
				scale(1.1, 1.1);
			else if (key == 's')
				scale(0.9, 0.9);
			// With d and a the car is rotated around the origin of the screen
			else if (key == 'd')
				rotateAroundPoint(-2, new DoublePoint(500, 300));
			else if (key == 'a')
				rotateAroundPoint(2, new DoublePoint(500, 300));
			else if (key == 'e')
				addMotion(HelpMath.checkDirection(getAngle() - 90), 0.5);
			else if (key == 'q')
				addMotion(HelpMath.checkDirection(getAngle() + 90), 0.5);
		}
	}
	
	@Override
	public void onKeyPressed(char key, int keyCode, boolean coded)
	{
		System.out.println(key);
	}
}
