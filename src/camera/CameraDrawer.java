package camera;

import java.awt.Graphics2D;

import drawnobjects.DrawnObject;
import handlers.DrawnObjectHandler;
import helpAndEnums.DepthConstants;

/**
 * This class follows the camera and draws objects. It only draws objects that 
 * will be shown on screen. The later only works with CollidingDrawnObjects 
 * since they can be checked.
 *
 * @author Gandalf.
 *         Created 16.6.2013.
 */
public class CameraDrawer extends DrawnObjectHandler
{
	// ATTRIBUTES	----------------------------------------------------
	
	private BasicCamera camera;
	
	
	// CONSTRUCTOR	----------------------------------------------------
	
	/**
	 * Creates a new cameradrawer. The drawer is not added to any handler 
	 * and must be drawn manually with the drawSelf() -method. By default, 
	 *
	 * @param autodeath Will the drawer die when it doesn't have anything to 
	 * draw anymore
	 * @param camera The camera that draws the drawer
	 */
	public CameraDrawer(boolean autodeath, BasicCamera camera)
	{
		super(autodeath, true, DepthConstants.BACK, null);
		
		// Initializes attributes
		this.camera = camera;
	}
	
	
	// IMPLEMENTED METHODS	--------------------------------------------
	
	@Override
	public void drawSelf(Graphics2D g2d)
	{
		// Only draws objects that are within the camera's range
		for (int i = 0; i < getHandledNumber(); i++)
		{
			DrawnObject d = getDrawnObject(i);
			// Only draws object inside the camera's vision
			if (this.camera.objectShouldBeDrawn(d))
				d.drawSelf(g2d);
		}
	}
}
