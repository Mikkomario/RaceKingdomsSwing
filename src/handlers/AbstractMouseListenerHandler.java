package handlers;

import java.util.ArrayList;

import handleds.Actor;
import handleds.Handled;
import listeners.MouseListener;

/**
 * This class handles the informing of mouselsteners, it does not actively find 
 * any new information though so it can't work on its own
 *
 * @author Gandalf.
 *         Created 28.12.2012.
 */
public abstract class AbstractMouseListenerHandler extends LogicalHandler 
implements Actor
{
	// ATTRIBUTES	-------------------------------------------------------
	
	private int mouseX, mouseY;
	private boolean ldown, rdown, lpressed, rpressed, lreleased, rreleased;
	
	private ArrayList<MouseListener> entered;
	private ArrayList<MouseListener> over;
	private ArrayList<MouseListener> exited;
	
	
	
	// CONSTRUCTOR	-------------------------------------------------------

	/**
	 * Creates a new empty mouselistenerhandler
	 *
	 * @param autodeath Will the handler die when it runs out of living listeners
	 * @param actorhandler The ActorHandler that will call the act-event (optional)
	 */
	public AbstractMouseListenerHandler(boolean autodeath, ActorHandler actorhandler)
	{
		super(autodeath, actorhandler);
		
		// Initializes attributes
		this.mouseX = 0;
		this.mouseY = 0;
		this.entered = new ArrayList<MouseListener>();
		this.over = new ArrayList<MouseListener>();
		this.exited = new ArrayList<MouseListener>();
		this.lpressed = false;
		this.rpressed = false;
		this.lreleased = false;
		this.rreleased = false;
	}

	
	// IMPLEMENTED METHODS	-----------------------------------------------
	
	@Override
	public void act()
	{
		//System.out.println(this.entered.size());
		//System.out.println("Left " + this.ldown + " Right " + this.rdown);
		
		// Informs the listeners about the mouse's movements and buttons
		for (int i = 0; i < getHandledNumber(); i++)
		{
			MouseListener l = getListener(i);
			
			// Checks if informing is needed
			if (!l.isActive())
				continue;
			
			// Only if the object cares about mouse movement
			if (l.listensMouseEnterExit())
			{
				// Exiting
				if (this.exited.contains(l))
					l.onMouseExit(getMouseX(), getMouseY());
				// Mouseover
				else if (this.over.contains(l))
					l.onMouseOver(getMouseX(), getMouseY());
				// Entering
				else if (this.entered.contains(l))
					l.onMouseEnter(getMouseX(), getMouseY());
			}
			
			// Informs about mouse buttons
			if (l.listensPosition(getMouseX(), getMouseY()))
			{
				if (leftIsDown())
					l.onLeftDown(getMouseX(), getMouseY());
				if (rightIsDown())
					l.onRightDown(getMouseX(), getMouseY());
				if (this.lpressed)
					l.onLeftPressed(getMouseX(), getMouseY());
				if (this.rpressed)
					l.onRightPressed(getMouseX(), getMouseY());
				if (this.lreleased)
					l.onLeftReleased(getMouseX(), getMouseY());
				if (this.rreleased)
					l.onRightReleased(getMouseX(), getMouseY());
			}
		}
		
		// Refreshes memory
		if (this.entered.size() > 0)
		{
			this.over.addAll(this.entered);
			this.entered.clear();
		}
		
		this.exited.clear();
		this.lpressed = false;
		this.rpressed = false;
		this.lreleased = false;
		this.rreleased = false;
	}
	
	@Override
	protected Class<?> getSupportedClass()
	{
		return MouseListener.class;
	}
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * @return The mouse's current x-coordinate
	 */
	public int getMouseX()
	{
		return this.mouseX;
	}
	
	/**
	 * @return The mouse's current y-coordinate
	 */
	public int getMouseY()
	{
		return this.mouseY;
	}
	
	/**
	 * @return Is the left mouse button currently down
	 */
	public boolean leftIsDown()
	{
		return this.ldown;
	}
	
	/**
	 * @return Is the right mouse button currently down
	 */
	public boolean rightIsDown()
	{
		return this.rdown;
	}
	
	/**
	 * Informs the object about the mouse's current position
	 *
	 * @param x The mouse's current x-coordinate
	 * @param y The mouse's current y-coordinate
	 */
	public void setMousePosition(int x, int y)
	{		
		if (getMouseX() != x || getMouseY() != y)
		{
			/*
			int enterkokoennen = this.entered.size();
			if (enterkokoennen > 0)
				System.out.println("Populaa!");
			*/
			//System.out.println("Mousemoved!");
			
			this.mouseX = x;
			this.mouseY = y;
			
			for (int i = 0; i < getHandledNumber(); i++)
			{
				MouseListener l = getListener(i);
				
				// Informs about the mouse's movement (if needed)
				if (l.isActive())
					l.onMouseMove(x, y);
				
				if (l.listensMouseEnterExit())
				{		
					// Checks if entered
					if (l.listensPosition(x, y) && !this.over.contains(l) 
							&& !this.entered.contains(l))
					{
						this.entered.add(l);
						//System.out.println(this.entered.size());
						continue;
					}

					// Checks if exited
					if (!l.listensPosition(x, y) && this.over.contains(l) && 
							!this.exited.contains(l))
					{
						this.over.remove(l);
						this.exited.add(l);
					}
				}
			}
		}
	}
	
	/**
	 * Informs the object about the mouse's left button's status
	 *
	 * @param leftmousedown Is the mouse's left button down
	 */
	public void setLeftMouseDown(boolean leftmousedown)
	{
		if (this.ldown != leftmousedown)
		{
			this.ldown = leftmousedown;
			
			if (leftmousedown)
				this.lpressed = true;
			else
				this.lreleased = true;
		}
	}
	
	/**
	 * Informs the object about the mouse's right button's status
	 *
	 * @param rightmousedown Is the mouse's right button down
	 */
	public void setRightMouseDown(boolean rightmousedown)
	{
		if (this.rdown != rightmousedown)
		{
			this.rdown = rightmousedown;
			
			if (rightmousedown)
				this.rpressed = true;
			else
				this.rreleased = true;
		}
	}
	
	
	// OTHER METHODS	---------------------------------------------------
	
	/**
	 * Returns a mouselistener from the list of listeners
	 *
	 * @param index The index of the listener in the list
	 * @return The listener from the given index
	 */
	protected MouseListener getListener(int index)
	{
		Handled maybeListener = getHandled(index);
		
		if (maybeListener instanceof MouseListener)
			return (MouseListener) maybeListener;
		else
			return null;
	}
	
	/**
	 * Adds a new listener to the informed listeners
	 *
	 * @param m The MouseListener added
	 */
	public void addMouseListener(MouseListener m)
	{
		super.addHandled(m);
	}
}
