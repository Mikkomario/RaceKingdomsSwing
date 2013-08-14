package handlers;

import java.awt.event.MouseEvent;

/**
 * This class takes input straight from the applet and informs all mouselisteners 
 * 'below' it. There should be only one mainmouselistenerhandler created and / or used
 *
 * @author Gandalf.
 *         Created 29.12.2012.
 */
public class MainMouseListenerHandler extends AbstractMouseListenerHandler
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new empty mouselistenerhandler. The handler won't die 
	 * automatically
	 * @param actorhandler The handler that will handle this handler (optional)
	 */
	public MainMouseListenerHandler(ActorHandler actorhandler)
	{
		super(false, actorhandler);
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Informs the handler about the mouse's current position and button status
	 *
	 * @param mouseX Mouse's current x-coordinate
	 * @param mouseY Mouse's current y-coordinate
	 * @param mousePressed Is a mouse button pressed
	 * @param mouseButton Which mouse button is pressed
	 */
	public void setMouseStatus(int mouseX, int mouseY, boolean mousePressed, 
			int mouseButton)
	{
		setMousePosition(mouseX, mouseY);
		
		if (mousePressed)
		{
			if (mouseButton == MouseEvent.BUTTON1)
				setLeftMouseDown(true);
			else if (mouseButton == MouseEvent.BUTTON3)
				setRightMouseDown(true);
		}
		else
		{
			setLeftMouseDown(false);
			setRightMouseDown(false);
		}
	}
}
