package listeners;

import handleds.LogicalHandled;

/**
 * Cameralisteners are interested in camera's position and movement and should 
 * be informed when changes in the former happen
 *
 * @author Gandalf.
 *         Created 7.12.2012.
 */
public interface CameraListener extends LogicalHandled
{	
	/**
	 * 
	 * This method should be called when the camera's position changes and is used 
	 * to keep the object in time with the camera's movement and/or position.
	 *
	 * @param posx The camera's current x-coordinate
	 * @param posy The camera's current y-coordinate
	 * @param w The width of the camera's area (ingame pxl)
	 * @param h The height of the camera's area (ingamepxl)
	 * @param angle The angle of the camera (degrees)
	 */
	public void informCameraPosition(int posx, int posy, int w, int h, int angle);
}
