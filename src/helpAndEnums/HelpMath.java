package helpAndEnums;

import java.awt.Point;

/**
 * This class only handles some mathematical problems
 *
 * @author Gandalf.
 *         Created 28.11.2012.
 */
public class HelpMath
{
	/**
	 * 
	 * Calculates the direction from one point to another (in degrees)
	 *
	 * @param x1 the first point's x coordinate
	 * @param y1 the first point's y coordinate
	 * @param x2 the second point's x coordinate
	 * @param y2 the second point's y coordinate
	 * @return the direction from point 1 to point 2 in degrees
	 */
	public static double pointDirection(double x1, double y1, double x2, double y2)
	{
		double xdist = x2 - x1;
		double ydist = y2 - y1;
		return checkDirection(-Math.toDegrees(Math.atan2(ydist, xdist)));
	}
	
	/**
	 * 
	 * Calculates the direction from one point to another around the x-axis (in degrees)
	 *
	 * @param z1 the first point's z coordinate
	 * @param y1 the first point's y coordinate
	 * @param z2 the second point's z coordinate
	 * @param y2 the second point's y coordinate
	 * @return the direction from point 1 to point 2 in degrees around the x-axis
	 * **/
	public static double pointXDirection(int z1, int y1, int z2, int y2)
	{
		return HelpMath.pointDirection(z1, y1, z2, y2);
	}
	
	/**
	 * 
	 * Calculates the direction from one point to another around the Y-axis (in degrees)
	 *
	 * @param x1 the first point's z coordinate
	 * @param z1 the first point's x coordinate
	 * @param x2 the second point's z coordinate
	 * @param z2 the second point's x coordinate
	 * @return the direction from point 1 to point 2 in degrees around the y-axis
	**/
	public static double PointYDirection(int x1, int z1, int x2, int z2)
	{
		return HelpMath.pointDirection(x1, z1, x2, z2);
	}
	
	/**
	 * 
	 * Calculates the direction from one point to another (in degrees) around z-axis
	 *
	 * @param x1 the first point's x coordinate
	 * @param y1 the first point's y coordinate
	 * @param x2 the second point's x coordinate
	 * @param y2 the second point's y coordinate
	 * @return the direction from point 1 to point 2 in degrees around z-axis
	**/
	public static double PointZDirection(int x1, int y1, int x2, int y2)
	{
		return HelpMath.pointDirection(x1, y1, x2, y2);
	}
	
	/**
	 * 
	 * Calculates a distance between two points.
	 *
	 * @param x1 First point's x coordinate
	 * @param y1 First point's y coordinate
	 * @param x2 Second point's x coordinate
	 * @param y2 Second point's y coordinate
	 * @return Distance between points in pixels
	 */
	public static double pointDistance(double x1, double y1, double x2, double y2)
	{
		double a = x1 - x2;
		double b = y1 - y2;
		
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}
	
	/**
	 * 
	 * Calculates a distance between two points in three dimensions
	 *
	 * @param x1 First point's x coordinate
	 * @param y1 First point's y coordinate
	 * @param z1 First point's z coordinate
	 * @param x2 Second point's x coordinate
	 * @param y2 Second point's y coordinate
	 * @param z2 Second point's z coordinate
	 * @return Distance between points in pixels
	 */
	public static int pointDistance(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		double deltax = x1 - x2;
		double deltay = y1 - y2;
		double deltaz = z1 - z2;
		
		double xydist = Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));
		double xyzdist = Math.sqrt(Math.pow(xydist, 2) + Math.pow(deltaz, 2));
		
		return (int) xyzdist;
	}
	
	/**
	 * 
	 * Returns the x-coordinate of a point that is length pixels away to direction 
	 * angle from the origin
	 *
	 * @param length How far from the origin the point is (pxl)
	 * @param direction To what direction from the origin the point is (degrees)
	 * @return The point's x-coordinate
	 */
	public static double lendirX(double length, double direction)
	{
		return Math.cos(Math.toRadians(direction))*length;
	}
	
	/**
	 * 
	 * Returns the y-coordinate of a point that is length pixels away to direction 
	 * angle from the origin
	 *
	 * @param length How far from the origin the point is (pxl)
	 * @param direction To what direction from the origin the point is (degrees)
	 * @return The point's y-coordinate
	 */
	public static double lendirY(double length, double direction)
	{
		return -Math.sin(Math.toRadians(direction))*length;
	}
	
	/**
	 * 
	 * Changes the direction to a value between 0 and 360. For example -10 becomes 350.
	 *
	 * @param direction The direction to be adjusted
	 * @return The adjusted direction
	 */
	public static double checkDirection(double direction)
	{
		double tmpdir = direction % 360;
		
		if (tmpdir < 0)
			tmpdir += 360;
		
		return tmpdir;
	}
	
	/**
	 * 
	 * Tells whether a point is between the given values
	 *
	 * @param point The point tested
	 * @param minx The smallest possible x
	 * @param maxx The largest possible x
	 * @param miny The smallest possible y
	 * @param maxy The largest possible y
	 * @return Is the point between the values
	 */
	public static boolean pointIsInRange(Point point, int minx, int maxx, int miny, int maxy)
	{
		return (point.x > minx && point.y > miny && point.x < maxx && point.y < maxy);
	}
	
	/**
	 * Calculates a force vector that has been created by projecting a force 
	 * vector to a certain direction
	 *
	 * @param basicdirection The direction of the force to be projected (angles)
	 * @param basicforce The length of the force vector to be projected
	 * @param newdirection The new direction to which the vector is projected
	 * @return The length of the new projected force vector
	 */
	public static double getDirectionalForce(double basicdirection, 
			double basicforce, double newdirection)
	{
		double projectdir = newdirection - basicdirection;
		
		return lendirX(basicforce, projectdir);
	}
	
	/**
	 * Rotates a point around the origin and returns the new position
	 *
	 * @param originx The x-coordinate of the rotation origin
	 * @param originy The y-coordinate of the rotation origin
	 * @param p The point which will be rotated
	 * @param rotation How many degrees the point is rotated around the origin
	 * @return The new position after the rotation
	 */
	public static DoublePoint getRotatedPosition(double originx, double originy, 
			DoublePoint p, double rotation)
	{
		// Calculates the old and the new directions (from the origin to the point)
		double prevdir = pointDirection(originx, originy, p.getX(), p.getY());
		double newdir = checkDirection(prevdir + rotation);
		// Also calculates the distance between the object and the point 
		// (which stays the same during the process)
		double dist = pointDistance(originx, originy, p.getX(), p.getY());
		// Returns the new position after the rotation
		return new DoublePoint(originx + lendirX(dist, newdir), 
				originy + lendirY(dist, newdir));
	}
	
	/**
	 * Calculates a direction of a sum of an x- and y-vector
	 *
	 * @param xvector The vector's x-component
	 * @param yvector The vector's y-component
	 * @return The vector's direction in degrees
	 */
	public static double getVectorDirection(double xvector, double yvector)
	{
		return checkDirection(-(Math.toDegrees(Math.atan2(yvector, xvector))));
	}
	
	/**
	 * Calculates the directional difference between the two angles. The 
	 * difference is somewhere between 0 and 180 degrees.
	 *
	 * @param angle1 The first angle
	 * @param angle2 The second angle
	 * @return The difference between the two angles in degrees (0-180)
	 */
	public static double getAngleDifference180(double angle1, double angle2)
	{
		double angledifference = Math.abs(checkDirection(angle1) - 
				checkDirection(angle2));
		
		// > 180 = < 180
		if (angledifference > 180)
			angledifference = 360 - angledifference;
		
		return angledifference;
	}
	
	/**
	 * Calculates the directional difference between the two angles. The 
	 * difference is somewhere between 0 and 90 degrees.
	 *
	 * @param angle1 The first angle
	 * @param angle2 The second angle
	 * @return The difference between the two angles in degrees (0-90)
	 */
	public static double getAngleDifference90(double angle1, double angle2)
	{
		double angledifference = getAngleDifference180(angle1, angle2);
		
		// > 90 < 90
		if (angledifference > 90)
			angledifference = 180 - angledifference;
		
		return angledifference;
	}
}
