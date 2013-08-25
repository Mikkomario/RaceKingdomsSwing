package racing;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import graphic.MaskChecker;
import graphic.SpriteBank;
import graphic.SpriteDrawer;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DepthConstants;
import helpAndEnums.DoublePoint;
import helpAndEnums.HelpMath;
import helpAndEnums.Material;
import drawnobjects.AdvancedPhysicDrawnObject;

/**
 * Car is the playable car that races around the stages
 *
 * @author Gandalf.
 *         Created 15.6.2013.
 */
public class Car extends AdvancedPhysicDrawnObject implements listeners.AdvancedKeyListener
{	
	// ATTRIBUTES	-----------------------------------------------------
	
	private SpriteDrawer spritedrawer;
	private MaskChecker maskChecker;
	
	private double maxdrivespeed, acceleration, turning, maxturning;
	private double turningfriction, turnrate, brakepower, maxreversespeed;
	private double slidepower, rotfriction, slideturnmodifier, turbopower; 
	private double turbospeed;
	private boolean sliding;
	private Point axelposition;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new car
	 *
	 * @param x The new x-coordinate of the car (ingame pxl)
	 * @param y The new y-coordinate of the car (ingame pxl)
	 * @param drawer The drawablehandler that draws the car (optional)
	 * @param collidablehandler The collidablehandler that handles the car's collision checking
	 * @param collisionhandler The collisionhandler that informs the car about 
	 * collisions 
	 * @param actorhandler The actorhandler that moves the car (optional)
	 * @param keyhandler The keylistenerhandler that informs the car of the 
	 * keypresses (optional)
	 * @param bank The spritebank that holds the car's sprite and mask
	 * @param carspritename The name of the car's sprite in the bank
	 * @param carmaskname The name of the car's mask in the bank (null if no mask is used)
	 */
	public Car(int x, int y, DrawableHandler drawer, 
			CollidableHandler collidablehandler, CollisionHandler collisionhandler, 
			ActorHandler actorhandler, KeyListenerHandler keyhandler, 
			SpriteBank bank, String carspritename, String carmaskname)
	{
		super(x, y, DepthConstants.NORMAL, true, CollisionType.BOX, drawer, 
				collidablehandler, collisionhandler, actorhandler);
		
		// Initializes attributes
		this.sliding = false;
		
		this.maxdrivespeed = 10;		// How fast the car can drive (> 0)
		this.turning = 0.02;			// How fast the car changes its direction (> 0)
		this.acceleration = 0.1;		// How fast the car starts moving (> 0)
		this.maxturning = 0.4;			// How much the car can turn (> 0)
		this.turningfriction = 0.03;	// How much turning affects the car's movement (>= 0)
		this.turnrate = 1.5;			// How much speed is kept while turning (0+)
		this.brakepower = 0.02;			// How effectively the car brakes (>= 0)
		this.maxreversespeed = 4;		// How fast the car can move backwards (> 0)
		this.slidepower = 0.7;			// How effective the slide is (0 - 1)
		this.rotfriction = 0.7;			// How fast the rotation diminishes (>= 0)
		this.slideturnmodifier = 1;		// How much the slide affects the turning (0 - 1)
		this.turbopower = 1;			// What is the accelration of the turbos
		this.turbospeed = 15;			// What is the maximum speed with the turbo
		
		this.spritedrawer = new SpriteDrawer(bank.getSprite(carspritename), actorhandler);
		this.maskChecker = new MaskChecker(bank.getSprite(carmaskname));
		
		// The turning exel is a bit to the right from the center of the object
		this.axelposition = new Point(
				(int) (getSpriteDrawer().getSprite().getWidth() * 0.75), 
				(int) (getSpriteDrawer().getSprite().getHeight() * 0.5));
		
		// Initializes some stats
		setMaxRotation(20);				// How much the car can possibly spin (> maxturning)
		setMaxSpeed(25);				// How fast the car can possibly go (> maxdrivespeed)
		setFriction(0.05);				// How much speed diminishes over time
		setRotationFriction(this.rotfriction);
		
		// Adds the car to the keyhandler (if possible)
		if (keyhandler != null)
			keyhandler.addKeyListener(this);
		
		// Resets the collisionpoints
		setBoxCollisionPrecision(3, 4);
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		if (coded)
		{
			// Turns with left / right arrowkey
			if (keyCode == KeyEvent.VK_LEFT)
			{
				//System.out.println(calculateTurning());
				turn(calculateTurning());
			}
			else if (keyCode == KeyEvent.VK_RIGHT)
				turn(-calculateTurning());
			
			// Goes forward with up arrowkey
			else if (keyCode == KeyEvent.VK_UP)
			{
				addCheckedBoost(getAngle(), getFriction() + this.acceleration, 
						this.maxdrivespeed);
			}
			// Goes backwards with bottomkey
			else if (keyCode == KeyEvent.VK_DOWN)
				addCheckedBoost(HelpMath.checkDirection(getAngle() + 180), 
						getFriction() + this.brakepower, this.maxreversespeed);
		}
		else
		{
			// If C was pressed, turbos
			if (key == 'c')
			{
				addCheckedBoost(getAngle(), this.turbopower, this.turbospeed);
			}
		}
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded)
	{
		if (!coded)
		{
			// Adds turbo if C was pressed
			/*
			if (key == 'c')
				addTurboBoost(10);
			// Slides around if X was pressed
			else */
			if (key == 'x')
			{
				//System.out.println("Sliding!");
				this.sliding = true;
				// Also negates some of the rotationfriction
				setRotationFriction(this.rotfriction * (1 - this.slidepower));
			}
		}
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		if (!coded)
		{
			// If V was released, sstops sliding
			if (key == 'x')
			{
				this.sliding = false;
				setRotationFriction(this.rotfriction);
			}
		}
	}
	
	@Override
	public void act()
	{
		super.act();
		
		// Also implies the turning friction
		applyTurningFriction();
		// And the turnboost too
		addTurnBoost();
		//System.out.println(getDirection());
	}
	
	@Override
	public void onCollision(ArrayList<DoublePoint> collisionpoints, Collidable collided)
	{
		// Does nothing
	}
	
	@Override
	public int getWidth()
	{
		return this.spritedrawer.getSprite().getWidth();
	}

	@Override
	public int getHeight()
	{
		return this.spritedrawer.getSprite().getHeight();
	}

	@Override
	public int getOriginX()
	{
		// If the spritedrawer hasn't been initialized yet, just returns a 0
		if (this.spritedrawer == null)
			return 0;
		return this.spritedrawer.getSprite().getOriginX();
	}

	@Override
	public int getOriginY()
	{
		// If the spritedrawer hasn't been initialized yet, just returns a 0
		if (this.spritedrawer == null)
			return 0;
		
		return this.spritedrawer.getSprite().getOriginY();
	}

	@Override
	public void drawSelfBasic(Graphics2D g2d)
	{
		// Draws the sprite
		if (this.spritedrawer != null)
			this.spritedrawer.drawSprite(g2d);
	}
	
	@Override
	public Collidable pointCollides(int x, int y)
	{
		// Point only collides if it also collides the mask
		Collidable collided = super.pointCollides(x, y);
		
		if (collided == null)
			return null;
		
		if (this.maskChecker.maskContainsRelativePoint(
				negateTransformations(x, y), 0))
			return collided;
		else
			return null;
	}
	
	@Override
	protected void setBoxCollisionPrecision(int edgeprecision, int insideprecision)
	{
		// Adds only the collisionpoints that are also in the mask
		super.setBoxCollisionPrecision(edgeprecision, insideprecision);
		refineRelativeCollisionPoints();
	}
	
	@Override
	protected void setCircleCollisionPrecision(int radius, int edgeprecision, int layers)
	{
		super.setCircleCollisionPrecision(radius, edgeprecision, layers);
		refineRelativeCollisionPoints();
	}
	
	@Override
	public int getZHeight()
	{
		return (int) (getHeight() * getYScale() * 0.8);
	}

	@Override
	public int getDensity()
	{
		return (int) (0.8 * (Material.ALUMINIUMBRONZE.getDensity() + 
				Material.ALUMINIUM.getDensity()) / 2);
	}
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * @return Spritedrawer that draws the car's sprite
	 */
	public SpriteDrawer getSpriteDrawer()
	{
		return this.spritedrawer;
	}
	
	/**
	 * @return MaskChecker that checks the car's mask
	 */
	public MaskChecker getMaskChecker()
	{
		return this.maskChecker;
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	private void addCheckedBoost(double direction, double force, double maxspeed)
	{
		// TODO: Make boost dependent on the friction?
		
		// Remembers the last speed
		double lastspeed = getMovement().getSpeed();
		// Adds the boost
		addMotion(direction, force);
		// Checks if the car is going too fast and does the necessary repairs
		if (getMovement().getSpeed() > maxspeed)
		{
			// If the car was already going too fast, boost only affects direction
			if (lastspeed < getMovement().getSpeed() && lastspeed > maxspeed)
				getMovement().setSpeed(lastspeed);
			// Otherwise, caps the speed to the max
			else
				getMovement().setSpeed(maxspeed);
		}
		
		// TODO: Rotate the car according to the accelration difference between 
		// the pixels?
	}
	
	/*
	private void addTurboBoost(double amount)
	{
		// Adds the boost
		addMotion(getAngle(), amount);
	}
	*/
	
	private void turn(double amount)
	{	
		// Remembers the last rotation
		//double lastrotation = getRotation();
		double lastrotation = getMoment(this.axelposition);
		// Adds the turn
		//addRotation(amount);
		addMoment(this.axelposition, amount);
		
		double maxturn = Math.abs(this.maxturning * getMovement().getSpeed());
		
		// Sliding affects the maxrotation (makes turning easier)
		if (this.sliding)
			maxturn *= 1  + this.slidepower * this.slideturnmodifier;
		
		double rotation = getMoment(this.axelposition);
		
		// Checks if the car is rotating too fast and does the necessary repairs
		if (Math.abs(rotation) > maxturn)
		{
			// If the car was already going too fast, doens't increase the turning
			if (Math.abs(lastrotation) > maxturn)
				setMoment(this.axelposition, lastrotation);
			// Otherwise, caps the speed to the max
			else if (rotation > 0)
				setMoment(this.axelposition, this.maxturning * getMovement().getSpeed());
			else
				setMoment(this.axelposition, -this.maxturning * getMovement().getSpeed());
		}
	}
	
	private void applyTurningFriction()
	{
		// Sliding affects turningfriction
		double modifier = 1;
		
		if (this.sliding)
			modifier = 1 - this.slidepower;
		
		getMovement().diminishSpeed(modifier * getTurningFriction());
	}
	
	// Makes the car's direction change when the car is turned
	private void addTurnBoost()
	{
		// Calculates the turnboost (a certain amount out of turningfriction)
		double turnboost = getTurningFriction();
		turnboost *= this.turnrate;
		
		// The larger the angledifference (up to 90) the smalller the turnboost
		turnboost *= HelpMath.getDirectionalForce(getMovement().getDirection(), 1, getAngle());
		
		/*
		// If the car is driving backwards, the boost is reversed
		if (getAngleDifference180() > 90)
			turnboost *= -1;
		*/
		
		// High slidingpower diminishes turnboost
		if (this.sliding)
			turnboost *= 1 - this.slidepower;
	
		addMotion(getAngle(), turnboost);
	}
	
	private double calculateTurning()
	{
		return getRotationFriction() + this.turning * getMovement().getSpeed();
	}
	
	private double getTurningFriction()
	{
		// Reduces the object's speed depending on how much the object is turning
		double angledifference = HelpMath.getAngleDifference90(getAngle(), 
				getMovement().getDirection());
		// If there's no difference or no speed, doesn't imply friction
		if (angledifference < 1 || getMovement().getSpeed() == 0)
			return 0;
		
		return this.turningfriction * Math.log(angledifference);
	}
	
	private void refineRelativeCollisionPoints()
	{
		super.setRelativeCollisionPoints(
				this.maskChecker.getRefinedRelativeCollisionPoints(
						getRelativeCollisionPoints(), 0));
	}
}
