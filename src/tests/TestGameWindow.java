package tests;

import java.awt.BorderLayout;

import handlers.ActorHandler;
import handlers.KeyListenerHandler;
import handlers.MouseListenerHandler;
import video.GamePanel;
import video.GameWindow;

/**
 * This game window is meant solely for testing purposes
 *
 * @author Gandalf.
 *         Created 14.8.2013.
 */
public class TestGameWindow extends GameWindow
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private MouseListenerHandler testmouselistenerhandler;
	private KeyListenerHandler testkeylistenerhandler;
	private ActorHandler testactorhandler;
	private GamePanel testpanel;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new testgamewindow
	 *
	 * @param width The width of the window
	 * @param height The height of the window
	 * @param testpanel The panel to which the test will be drawn
	 */
	public TestGameWindow(int width, int height, GamePanel testpanel)
	{
		super(width, height, "Test");
		
		// Initializes attributes
		this.testactorhandler = new ActorHandler(false, null);
		this.testmouselistenerhandler = new MouseListenerHandler(false, 
				this.testactorhandler, null);
		this.testkeylistenerhandler = new KeyListenerHandler(false, null);
		this.testpanel = testpanel;
		
		// Adds them to the superhandlers
		addActor(this.testactorhandler);
		addMouseListener(this.testmouselistenerhandler);
		addKeyListener(this.testkeylistenerhandler);
		
		// Inactivates the testhandlers
		this.testactorhandler.inactivate();
		this.testkeylistenerhandler.inactivate();
		this.testmouselistenerhandler.inactivate();
		
		// Adds the panel to the window
		addGamePanel(this.testpanel, BorderLayout.CENTER);
	}

	
	// OTHER METHODS	-------------------------------------------------
	
	private void test()
	{
		// Activates the handlers
		this.testactorhandler.activate();
		this.testkeylistenerhandler.activate();
		this.testmouselistenerhandler.activate();
		
		// Runs tests
		new FpsApsTest(this.testactorhandler, this.testpanel.getDrawer(), 
				this.testkeylistenerhandler, this.testmouselistenerhandler).test();
		/*
		new MouseTest(this.testactorhandler, this.testpanel.getDrawer(), 
				this.testkeylistenerhandler, this.testmouselistenerhandler).test();
		new InputTest(this.testactorhandler, this.testpanel.getDrawer(), 
				this.testkeylistenerhandler, this.testmouselistenerhandler).test();
		new CollisionTest(this.testactorhandler, this.testpanel.getDrawer(), 
				this.testkeylistenerhandler, this.testmouselistenerhandler).test();
		*/
		/*
		new WavSoundTest(this.testactorhandler, this.testpanel.getDrawer(), 
				this.testkeylistenerhandler, this.testmouselistenerhandler).test();
		*/
		/*
		new MidiTest(this.testactorhandler, this.testpanel.getDrawer(), 
				this.testkeylistenerhandler, this.testmouselistenerhandler).test();
		*/
		/*
		new WavSoundTrackTest(this.testactorhandler, this.testpanel.getDrawer(), 
				this.testkeylistenerhandler, this.testmouselistenerhandler).test();
		*/
		new CollisionTest(this.testactorhandler, this.testpanel.getDrawer(), 
				this.testkeylistenerhandler, this.testmouselistenerhandler).test();
	}
	
	
	// MAIN METHOD ---------------------------------------------------
	
	/**
	 * Starts the tests
	 * 
	 * @param args not used
	 */
	public static void main(String [] args)
	{
		GamePanel testpanel = new GamePanel(1000, 600);
		TestGameWindow test = new TestGameWindow(1000, 600, testpanel);
		
		test.test();
	}
}
