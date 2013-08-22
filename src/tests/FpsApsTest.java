package tests;

import java.awt.Graphics2D;

import handleds.Actor;
import handleds.Drawable;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import helpAndEnums.DepthConstants;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Gandalf.
 *         Created 15.6.2013.
 */
public class FpsApsTest extends AbstractTest implements Actor, Drawable
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private boolean active;
	private boolean visible;
	private boolean alive;
	private long lastmillis;
	private int fps;
	private int aps;
	private int frames;
	private int actions;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public FpsApsTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);

		// Initializes the attributes
		this.active = false;
		this.visible = false;
		this.alive = true;
		this.lastmillis = System.currentTimeMillis();
		this.fps = 0;
		this.aps = 0;
		this.frames = 0;
		this.actions = 0;
		
		// Adds the object to the handlers
		actorhandler.addActor(this);
		drawer.addDrawable(this);
		
	
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void test()
	{
		setVisible();
		activate();
	}

	@Override
	public boolean isActive()
	{
		return this.active;
	}

	@Override
	public boolean activate()
	{
		this.active = true;
		return true;
	}

	@Override
	public boolean inactivate()
	{
		this.active = false;
		return true;
	}

	@Override
	public boolean isDead()
	{
		return !this.alive;
	}

	@Override
	public boolean kill()
	{
		this.alive = false;
		return true;
	}

	@Override
	public void drawSelf(Graphics2D g2d)
	{
		// Calculates the fps
		this.frames ++;
		
		// Draws the current fps and aps
		g2d.drawString("FPS: " + this.fps, 100, 100);
		g2d.drawString("APS: " + this.aps, 100, 130);
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public boolean setVisible()
	{
		this.visible = true;
		return true;
	}

	@Override
	public boolean setInvisible()
	{
		this.visible = false;
		return true;
	}

	@Override
	public void act()
	{
		// Calculates the aps and fps
		this.actions ++;
		
		if (System.currentTimeMillis() - this.lastmillis > 1000)
		{
			this.aps = this.actions;
			this.fps = this.frames;
			this.actions = 0;
			this.frames = 0;
			this.lastmillis = System.currentTimeMillis();
		}
	}
	
	@Override
	public int getDepth()
	{
		// The test is always drawn on top
		return DepthConstants.TOP;
	}

	@Override
	public boolean setDepth(int depth)
	{
		// The depth of the test can't be changed
		return false;
	}
}
