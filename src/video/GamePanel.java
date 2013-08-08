package video;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Creates a panel for the game.
 * 
 * @author Unto Created 8.8.2013
 * 
 */
public class GamePanel extends JPanel{
	
	// ATTRIBUTES ---------------------------------------------------------
	
	private int width;
	private int height;
	
	// CONSTRUCTOR ---------------------------------------------------------
	
	/**Creates a new panel with default color being
	 * 
	 * @param width		Panel's width
	 * @param height	Panel's height
	 */
	public GamePanel(int width, int height){
		this.width = width;
		this.height = height;
		
		//Let's format our panel
		this.formatPanel();
		
		//And make it visible
		this.setVisible(true);
	}
	
	// IMPLEMENTED METHODS	----------------------------------------------
	
	@Override
	public void paintComponent(Graphics g)
	{
		// The panel draws the background and all stuff inside it
	}
	
	
	// PRIVATE METHODS ---------------------------------------------------
	
	private void formatPanel()
	{
		//Let's set the panel's size...
		this.setSizes(this.width, this.height);
		//...And color
		this.setBackground(new Color(0,0,0));
	}
	
	// OTHER METHODS ---------------------------------------------------
	
	/**
	 * Changes the size of the game panel, for real.
	 * 
	 * @param width		Panel's new width
	 * @param height	Panel's new height
	 */
	public void setSizes(int width, int height)
	{
		this.setSize(width, height);
		Dimension preferred = new Dimension(width, height);
		this.setPreferredSize(preferred);
		this.setMinimumSize(preferred);
		this.setMaximumSize(preferred);
	}
	
	/**Changes the panel's background color.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	public void setBackgroundColor(int red, int green, int blue, int alpha)
	{
		this.setBackground(new Color(red, green, blue, alpha));
	}
	
	/**
	 * Makes the panel visible.
	 */
	public void makeVisible(){
		this.setVisible(true);
	}
	
	/**
	 * Makes the panel invisible.
	 */
	public void makeInvisible(){
		this.setVisible(false);
	}
}
