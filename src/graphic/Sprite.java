package graphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import common.BankObject;

/**
 * This object represents a drawn image that can be animated. Sprites are
 * meant to be used in multiple objects and those objects should handle the
 * animation (this class merely loads and provides all the neccessary images)
 *
 * @author Gandalf.
 *         Created 27.11.2012.
 */
public class Sprite implements BankObject
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private BufferedImage[] images;
	
	private int origX, origY;
	private String spritename;
	private boolean dead;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * This method creates a new sprite based on the information provided by 
	 * the caller. The images are loaded from a strip that contains one or more 
	 * images.
	 *
	 * @param filename The location of the loaded image (data/...)
	 * @param numberOfImages How many separate images does the strip contain?
	 * @param originX the x-coordinate of the sprite's origin (Pxl)
	 * @param originY the y-coordinate of the sprite's origin (Pxl)
	 * @param spritename The name of the sprite. This is used in finding the sprite later
	 * @throws FileNotFoundException If an image can't be loaded with the given 
	 * filename, the constructor will throw this exception. It is advised to 
	 * not use the sprite since the 
	 * object can't function properly if this happens
	 */
	public Sprite(String filename, int numberOfImages, int originX, int originY,
			String spritename)
	{
		// Checks the variables
		if (filename == null || numberOfImages <= 0)
			throw new IllegalArgumentException();
		
		// Initializes attributes
		this.origX = originX;
		this.origY = originY;
		this.spritename = spritename;
		this.dead = false;
		
		// Loads the image
		File img = new File("src/data/" + filename);
		BufferedImage strip = null;
		
		try
		{
			strip = ImageIO.read(img);
		}
		catch (IOException ioe)
		{
			System.err.println("Failed to load the image " + filename);
			return;
		}
		
		/*
		this.strip = new BufferedImage(in.getWidth(), 
				in.getHeight(), BufferedImage.TYPE_INT_ARGB);
		*/
		//this.strip = in;
		
		// Creates the subimages
		this.images = new BufferedImage[numberOfImages];
		
		// Calculates the subimage width
		int sw = strip.getWidth() / numberOfImages;
		
		for (int i = 0; i < numberOfImages; i++)
		{
			// Calculates the needed variables
			int sx;
			sx = i*sw;
			
			this.images[i] = strip.getSubimage(sx, 0, sw, strip.getHeight());
		}
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------
	
	@Override
	public boolean kill()
	{
		// Kills the object (clears the image data)
		// TODO: This might cause problems in the other threads trying to draw 
		// the sprite
		this.images = null;
		this.dead = true;
		return true;
	}
	
	@Override
	public boolean isDead()
	{
		return this.dead;
	}
	
	
	// GETTERS & SETTERS	------------------------------------------------
	
	/**
	 * @return returns how many subimages exist within this sprite
	 */
	public int getImageNumber()
	{
		return this.images.length;
	}
	
	/**
	 * @return The x-coordinate of the origin of the sprite (Pxl)
	 */
	public int getOriginX()
	{
		return this.origX;
	}
	
	/**
	 * @return The y-coordinate of the origin of the sprite (Pxl)
	 */
	public int getOriginY()
	{
		return this.origY;
	}
	
	/**
	 * @return How wide a single subimage is (pxl)
	 */
	public int getWidth()
	{
		return getSubImage(0).getWidth();
	}
	
	/**
	 * @return How tall a single subimage is (pxl)
	 */
	public int getHeight()
	{
		//System.out.println(this.strip.height);
		return getSubImage(0).getHeight();
	}
	
	/**
	 * @return The unique index / name of the sprite
	 */
	public String getName()
	{
		return this.spritename;
	}
	
	
	// METHODS	------------------------------------------------------------
	
	/**
	 * This method returns a single subimage from the sprite.
	 *
	 * @param imageIndex The index of the image to be drawn [0, numberOfImages]
	 * @return The subimage from the given index
	 */
	public BufferedImage getSubImage(int imageIndex)
	{
		// Checks the given index and adjusts it if needed
		if (imageIndex < 0 || imageIndex >= this.images.length)
			imageIndex = Math.abs(imageIndex % this.images.length);
		
		return this.images[imageIndex];
	}
	
	// TODO: If you get bored, try to implement filters into the project
	// check: http://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
	
	/*
	public BufferedImage getStrip()
	{
		return this.strip;
	}
	*/
}
