package backgrounds;

import java.awt.Graphics2D;
import java.util.ArrayList;

import graphic.Sprite;
import graphic.SpriteBank;
import graphic.SpriteDrawer;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import helpAndEnums.DepthConstants;
import drawnobjects.DrawnObject;

/**
 * Background is a simple surface that can be drawn under other objects
 *
 * @author Mikko Hilpinen.
 *         Created 1.7.2013.
 */
public class Background extends DrawnObject
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private SpriteDrawer texturedrawer;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new background to the given coordinates and adds it to the 
	 * given handlers.
	 *
	 * @param x The backround's top left x-coordinate
	 * @param y The backgound's top left y-coordinate
	 * @param drawer The drawablehandler that draws the background
	 * @param actorhandler The actorhandler that animates the background 
	 * (optional, for animated backgrounds)
	 * @param bank The spritebank that holds the textrure sprite
	 * @param texturename The name of the texture in the bank
	 */
	public Background(int x, int y, DrawableHandler drawer, 
			ActorHandler actorhandler, SpriteBank bank, String texturename)
	{
		super(x, y, DepthConstants.BOTTOM, drawer);

		// Initializes attributes
		this.texturedrawer = 
				new SpriteDrawer(bank.getSprite(texturename), actorhandler);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public int getOriginX()
	{
		// Background's origin is always in the middle
		if (this.texturedrawer != null)
			return this.texturedrawer.getSprite().getWidth() / 2;
		else
			return 0;
	}

	@Override
	public int getOriginY()
	{
		if (this.texturedrawer != null)
			return this.texturedrawer.getSprite().getHeight() / 2;
		else
			return 0;
	}

	@Override
	public void drawSelfBasic(Graphics2D g2d)
	{
		// Draws the sprite
		if (this.texturedrawer != null)
			this.texturedrawer.drawSprite(g2d);
	}
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * @return The spritedrawer used to drawing the texture of the background
	 */
	public SpriteDrawer getSpriteDrawer()
	{
		return this.texturedrawer;
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Changes the background's width and height
	 *
	 * @param width The new width of the background
	 * @param height The new height of the background
	 */
	public void setDimensions(int width, int height)
	{
		// Calculates the scaling
		double xscale = width / (double) this.texturedrawer.getSprite().getWidth();
		double yscale = height / (double) this.texturedrawer.getSprite().getHeight();
		
		// Scales the object
		scale(xscale, yscale);
	}
	
	/**
	 * Creates a list containing multiple backgrounds forming a larger surface.
	 *
	 * @param minx The left-top x-coordinate of the surface
	 * @param miny The left-top y-coordinate of the surface
	 * @param width The width of the surface (in pixels)
	 * @param height The height of the surface (in pixels)
	 * @param approximate Are the backgrounds scaled so that they all fit into the area. 
	 * If false, the backgrounds may be placed partly outside the given area.
	 * @param texturebank The spritebank that contains the texture used in the backgrounds
	 * @param texturename The name of the texture used in the background
	 * @param drawer The draweablehandler that will draw the backgrounds (optional)
	 * @param animator The actorhandler that will animate the backgrounds (optional)
	 * 
	 * @return A list containing all backgrounds used to create the surface
	 */
	public static ArrayList<Background> getRepeatedBackground(
			int minx, int miny, int width, int height, boolean approximate, 
			SpriteBank texturebank, String texturename, 
			DrawableHandler drawer, ActorHandler animator)
	{
		Sprite texture = texturebank.getSprite(texturename);
		
		// Calculates the number of backgrounds used horizontally and vertically
		int backbasewidth = texture.getWidth();
		int backbaseheight = texture.getHeight();
		int horbacks = 1 + width / backbasewidth;
		int verbacks = 1 + height / backbaseheight;
		
		double xscale = 1;
		double yscale = 1;
		
		// If approximation is on, scales the backgrounds a bit to fit the area
		if (approximate)
		{
			xscale = width / (horbacks * backbasewidth);
			yscale = height / (verbacks * backbaseheight);
		}
		
		// Initializes the returned list
		ArrayList<Background> backs = new ArrayList<Background>();
		
		// Creates all the backgrounds and adds them to the list
		for (int iy = 0; iy < verbacks; iy++)
		{
			for (int ix = 0; ix < horbacks; ix++)
			{
				Background newback = new Background(
						minx + (int) (ix * backbasewidth * xscale) + 
						(int) (xscale * backbasewidth/2), 
						miny + (int) (iy * backbaseheight * yscale) + 
						(int) (yscale * backbaseheight/2), 
						drawer, animator, texturebank, texturename);
				newback.scale(xscale, yscale);
				backs.add(newback);
			}
		}
		
		// Returns the complete list
		return backs;
	}
}
