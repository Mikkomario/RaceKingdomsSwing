package handlers;

import listeners.MouseListener;

/**
 * Informs multiple objects about mouse's movements and clicks
 *
 * @author Gandalf.
 *         Created 28.12.2012.
 */
public class MouseListenerHandler extends AbstractMouseListenerHandler 
	implements MouseListener
{
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new empty mouselistenerhandler
	 *
	 * @param autodeath Will the handler die when it runs out of living listeners
	 * @param actorhandler The actorhandler that will handle this handler (optional)
	 * @param superhandler The mouselistenerhandler that will handle this handler (optional)
	 */
	public MouseListenerHandler(boolean autodeath, 
			ActorHandler actorhandler, MouseListenerHandler superhandler)
	{
		super(autodeath, actorhandler);
		
		// Tries to add the object to the second handler
		if (superhandler != null)
			superhandler.addMouseListener(this);
	}
	
	
	// IMPLEMENTED METHODS	-------------------------------------------------

	@Override
	public void onLeftDown(int mouseX, int mouseY)
	{
		// Informs all listeners about left mouse
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (this.getListener(i).listensPosition(mouseX, mouseY))
				this.getListener(i).onLeftDown(mouseX, mouseY);
		}
	}

	@Override
	public void onRightDown(int mouseX, int mouseY)
	{
		// Informs all listeners about the right mouse
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (this.getListener(i).listensPosition(mouseX, mouseY))
				this.getListener(i).onRightDown(mouseX, mouseY);
		}
	}

	@Override
	public void onLeftPressed(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
		setLeftMouseDown(true);
		
		// Informs all listeners about the press
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (this.getListener(i).listensPosition(mouseX, mouseY))
				this.getListener(i).onLeftPressed(mouseX, mouseY);
		}
	}

	@Override
	public void onRightPressed(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
		setRightMouseDown(true);
		
		// Informs all listeners about the press
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (this.getListener(i).listensPosition(mouseX, mouseY))
				this.getListener(i).onRightPressed(mouseX, mouseY);
		}
	}

	@Override
	public boolean listensPosition(int x, int y)
	{
		// Handlers listen all positions
		return true;
	}

	@Override
	public boolean listensMouseEnterExit()
	{
		// Does not have a special area of interrest
		return false;
	}

	@Override
	public void onMouseEnter(int mouseX, int mouseY)
	{
		// Does nothing
	}

	@Override
	public void onMouseExit(int mouseX, int mouseY)
	{
		// Nothing
	}

	@Override
	public void onMouseMove(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
	}


	@Override
	public void onLeftReleased(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
		setLeftMouseDown(false);
		
		// Informs all listeners about the release
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (this.getListener(i).listensPosition(mouseX, mouseY))
				this.getListener(i).onLeftReleased(mouseX, mouseY);
		}
	}


	@Override
	public void onRightReleased(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
		setRightMouseDown(false);
		
		// Informs all listeners about the release
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (this.getListener(i).listensPosition(mouseX, mouseY))
				this.getListener(i).onRightReleased(mouseX, mouseY);
		}
	}


	@Override
	public void onMouseOver(int mouseX, int mouseY)
	{
		// Nothing
	}
}
