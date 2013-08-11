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

import tests.MouseTest;

/**
 * Creates the main window frame.
 * 
 * @author Unto Created 8.8.2013
 * 
 */
public class GameWindow extends JFrame
{	
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

	// TODO: Change this to a real update feature
	private boolean needsUpdating;
	
	
	// CONSTRUCTOR ---------------------------------------------------------
	
	/**Creates a new window frame with given width and height.
	 * 
	 * @param width		Window's width.
	 * @param height	Window's height.
	 * @param title The title shown in the window's border
	 */
	public GameWindow(int width, int height, String title)
	{
		System.out.println("Constructor starts");
		
		this.width = width;
		this.height = height;
		this.setTitle(title);
		
		//Let's format our window
		this.formatWindow();
		//And make it visible
		this.setVisible(true);
		
		// Adds listener(s) to the window
		addMouseListener(new BasicMouseListener());
		addKeyListener(new BasicKeyListener());
		
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
		
		System.out.println("CREATES STUFF");
		
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
	
	// PRIVATE METHODS ---------------------------------------------------
	
	private void formatWindow()
	{
		//Let's set our window's layout
		this.setLayout(new BorderLayout());
		//Let's make sure our window closes properly
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Let's set our window's size
		this.setSize(this.width, this.height);
	}
	
	private void test()
	{
		// Activates the handlers
		//System.out.println(this.testactorhandler);
		this.testactorhandler.activate();
		this.testkeylistenerhandler.activate();
		this.testmouselistenerhandler.activate();
		
		// Runs tests
		new MouseTest(this.stephandler, this.drawer, 
		this.testkeylistenerhandler, this.testmouselistenerhandler).test();
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
		this.mainmousehandler.setMousePosition(mousePosition.x, mousePosition.y);
	}
	
	/**
	 * This method should be called when the screen needs redrawing
	 */
	public void callScreenUpdate()
	{
		this.needsUpdating = true;
	}
	
	// MAIN METHOD ---------------------------------------------------
	
	/**Starts the program.
	 * 
	 * @param args
	 */
	public static void main(String [] args)
	{
		System.out.println("Tries to create gamewindow");
		GameWindow testi = new GameWindow(800, 600, "Testi");
		System.out.println("Creates panels");
		GamePanel paneeliTesti = new GamePanel(100, 100);
		GamePanel toinenPaneeli = new GamePanel(200, 200);
		
		toinenPaneeli.setBackgroundColor(123, 123, 0, 255);
		testi.addGamePanel(paneeliTesti, BorderLayout.WEST);
		testi.addGamePanel(toinenPaneeli, BorderLayout.EAST);
		
		testi.test();
	}
	
	
	// SUBCLASSES	----------------------------------------------------
	
	/**
	 * Main window's helper class, which listens to what the mouse does.
	 * 
	 * @author Unto	Created 8.8.2013
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
		public void mousePressed(MouseEvent e)
		{
			GameWindow.this.mainmousehandler.setMouseStatus(e.getX(), e.getY(), 
					true, e.getButton());
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			GameWindow.this.mainmousehandler.setMouseStatus(e.getX(), e.getY(),
					false, e.getButton());
		}
	}
	
	/**Main window's helper class, which listens to what the keyboard does.
	 * 
	 * @author Unto	Created 8.8.2013
	 *
	 */
	private class BasicKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent ke)
		{
			GameWindow.this.mainkeyhandler.onKeyPressed(ke.getKeyChar(), 
					ke.getKeyCode(), ke.getKeyChar() == KeyEvent.CHAR_UNDEFINED);
		}

		@Override
		public void keyReleased(KeyEvent ke)
		{
			GameWindow.this.mainkeyhandler.onKeyReleased(ke.getKeyChar(), 
					ke.getKeyCode(), ke.getKeyChar() == KeyEvent.CHAR_UNDEFINED);
		}

		@Override
		public void keyTyped(KeyEvent arg0)
		{
			// Not needed
		}
	}
}
