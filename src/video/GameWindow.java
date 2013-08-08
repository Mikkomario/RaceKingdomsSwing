package video;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import handlers.MainKeyListenerHandler;
import handlers.MainMouseListenerHandler;
import handlers.MouseListenerHandler;
import handlers.StepHandler;
import helpAndEnums.DepthConstants;

import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	
	private MainKeyListenerHandler mainkeyhandler;
	private MainMouseListenerHandler mainmousehandler;
	private StepHandler stephandler;
	private DrawableHandler drawer;
	private KeyListenerHandler keylistenerhandler;
	private MouseListenerHandler mouselistenerhandler;
	private ActorHandler listeneractorhandler;
	
	private ActorHandler testactorhandler;
	private KeyListenerHandler testkeylistenerhandler;
	private MouseListenerHandler testmouselistenerhandler;

	private boolean needsUpdating; 
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
		
		
		//Let's implement some earlier features
		this.stephandler = new StepHandler(15, this);
		
		this.listeneractorhandler = new ActorHandler(false, this.stephandler);
		this.mainkeyhandler = new MainKeyListenerHandler(this.listeneractorhandler);
		this.mainmousehandler = new MainMouseListenerHandler(this.listeneractorhandler);
		
		this.drawer = new DrawableHandler(false, true, DepthConstants.NORMAL, null);
		this.keylistenerhandler = new KeyListenerHandler(false, null);
		this.mouselistenerhandler = new MouseListenerHandler(false, 
				this.listeneractorhandler, null);
		
		this.testactorhandler = new ActorHandler(true, this.stephandler);
		this.testkeylistenerhandler = new KeyListenerHandler(false, this.keylistenerhandler);
		this.testmouselistenerhandler = new MouseListenerHandler(false, 
				this.testactorhandler, this.mouselistenerhandler);
		
		// Updates missing handling information
		this.mainkeyhandler.addListener(this.keylistenerhandler);
		this.mainmousehandler.addMouseListener(this.mouselistenerhandler);
		
		// Initializes other attributes
		this.needsUpdating = true;
		
		// Inactivates the testhandlers
		this.testactorhandler.inactivate();
		this.testkeylistenerhandler.inactivate();
		this.testmouselistenerhandler.inactivate();
		
		// Starts the game
		new Thread(this.stephandler).start();
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
	 * @param newPanel	The GamePanel you want to add to the window.
	 * @param direction	The direction where you want to place the panel. (For
	 * example Borderlayout.NORTH)
	 */
	public void addGamePanel(GamePanel newPanel, String direction){
		this.add(newPanel, direction);
	}
	
	/**Updates mouse's position in the game
	 * 
	 */
	public void callMousePositionUpdate(){
		Point mousePosition = MouseInfo.getPointerInfo().getLocation();
		//this.mainmousehandler.setMousePosition(mousePosition.x, mousePosition.y);
	}
	
	
	// MAIN METHOD ---------------------------------------------------
	/**Starts the program.
	 * 
	 * @param args
	 */
	public static void main(String [] args){
		GameWindow testi = new GameWindow(800, 600, "Testi");
		GamePanel paneeliTesti = new GamePanel(100, 100);
		GamePanel toinenPaneeli = new GamePanel(200, 200);
		toinenPaneeli.setBackgroundColor(123, 123, 0, 255);
		testi.addGamePanel(paneeliTesti, BorderLayout.WEST);
		testi.addGamePanel(toinenPaneeli, BorderLayout.EAST);
	}
	
	/**Main window's helper class, which listens to what the mouse does.
	 * 
	 * @author Unto	Created 8.8.2013
	 *
	 */
	private class BasicMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// Not needed
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// Not needed
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// Not needed
		}

		@Override
		public void mousePressed(MouseEvent e) {
			//this.mainmousehandler.setMouseStatus(e.getX(), e.getY(), true, this.mouseButton);
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//this.mainmousehandler.setMouseStatus(e.getX(), e.getY(), false, this.mouseButton);
			
		}
		
	}
	
	/**Main window's helper class, which listens to what the keyboard does.
	 * 
	 * @author Unto	Created 8.8.2013
	 *
	 */
	private class BasicKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// Not needed
		}
		
	}

}
