package handlers;

import handleds.Handled;
import listeners.CameraListener;

/**
 * This class informs all its sublisteners about changes in camera's position
 *
 * @author Gandalf.
 *         Created 7.12.2012.
 */
public class CameraListenerHandler extends LogicalHandler implements CameraListener
{	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new listenerhandler that will inform all sublisteners about
	 * camera's changes. Listeners must be added manually later.
	 * 
	 * @param autodeath Will the handler stop functioning when it runs out of 
	 * handled listeners
	 * @param superhandler The cameralistenerhandler that informs this handler (optional)
	 */
	public CameraListenerHandler(boolean autodeath, CameraListenerHandler superhandler)
	{
		super(autodeath, superhandler);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void informCameraPosition(int posx, int posy, int w, int h, int angle)
	{
		// Informs all sublisteners about the change
		for (int i = 0; i < getHandledNumber(); i++)
		{
			getListener(i).informCameraPosition(posx, posy, w, h, angle);
		}	
	}
	
	@Override
	protected Class<?> getSupportedClass()
	{
		return CameraListener.class;
	}
	
	
	// OTHER METHODS	---------------------------------------------------
	
	/**
	 * Adds a new cameralistener to the informed cameralisteners
	 * @param c The listener to be addded
	 */
	public void addListener(CameraListener c)
	{
		super.addHandled(c);
	}
	
	// Casts the handled to listener
	private CameraListener getListener(int index)
	{
		Handled maybeListener = getHandled(index);
		if (maybeListener instanceof CameraListener)
			return (CameraListener) maybeListener;
		else
			return null;
	}
}
