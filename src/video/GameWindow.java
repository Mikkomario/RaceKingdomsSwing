package video;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Creates the main window frame.
 * 
 * @author Unto Created 8.8.2013
 * 
 */
public class GameWindow extends JFrame{
	
	// ATTRIBUTES ---------------------------------------------------------
	
	private int width;
	private int height;
	
	// CONSTRUCTOR ---------------------------------------------------------
	
	/**Creates a new window frame with given width and height.
	 * 
	 * @param width		Window's width.
	 * @param height	Window's height.
	 */
	public GameWindow(int width, int height){
		this.width = width;
		this.height = height;
		//Let's format our window
		this.formatWindow();
		//And make it visible
		this.setVisible(true);
	}
	
	/**Creates a new window frame with given width, height and name.
	 * 
	 * @param width		Window's width.
	 * @param height	Window's height.
	 * @param WindowTitle	Window's title.
	 */
	public GameWindow(int width, int height, String WindowTitle){
		this.width = width;
		this.height = height;
		//Let's format our window
		this.formatWindow();
		//Let's set our window's title
		this.setTitle(WindowTitle);
		//And make it visible
		this.setVisible(true);
	}
	
	// PRIVATE METHODS ---------------------------------------------------
	
	private void formatWindow(){
		//Let's set our window's layout
		this.setLayout(new BorderLayout());
		//Let's make sure our window closes properly
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Let's set our window's size
		this.setSize(this.width, this.height);
	}
	
	
	// METHODS ---------------------------------------------------
	/**Adds a new GamePanel to the given direction.
	 * 
	 * @param newPanel
	 * @param direction
	 */
	public void addGamePanel(GamePanel newPanel, String direction){
		this.add(newPanel, direction);
	}
	
	
	
	// MAIN METHOD ---------------------------------------------------
	
	public static void main(String [] args){
		GameWindow testi = new GameWindow(800, 600, "Testi");
		GamePanel paneeliTesti = new GamePanel(100, 100);
		GamePanel toinenPaneeli = new GamePanel(200, 200);
		toinenPaneeli.setBackgroundColor(123, 123, 0, 255);
		testi.addGamePanel(paneeliTesti, BorderLayout.WEST);
		testi.addGamePanel(toinenPaneeli, BorderLayout.EAST);
	}

}
