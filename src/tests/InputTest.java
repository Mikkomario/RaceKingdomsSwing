package tests;

import listeners.AdvancedKeyListener;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import handlers.MouseListenerHandler;

/**
 * This class is used to test input from the user.
 * 
 * @author Unto
 * 			Created 14.6.2013
 */
public class InputTest extends AbstractTest implements AdvancedKeyListener{

	private boolean active;
	private boolean isDead;
	
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 */
	public InputTest(ActorHandler actorhandler, DrawableHandler drawer, 
			KeyListenerHandler keylistenerhandler, 
			MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);
		keylistenerhandler.addKeyListener(this);
		this.active = false;
		this.isDead = false;
	}

	@Override
	public void test() {
		this.activate();
	}

	//---IMPLEMENTED NON-IMPORTANT METHODS--------------
	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public boolean activate() {
		this.active = true;
		return true;
	}

	@Override
	public boolean inactivate() {
		this.active = false;
		return true;
	}

	@Override
	public boolean isDead() {
		return this.isDead;
	}

	@Override
	public boolean kill() {
		this.isDead = true;
		return true;
	}

	//--------------------------------------------------------
	
	@Override
	public void onKeyDown(int key, int keyCode, boolean coded) {
		//Worked fine
		//System.out.println("Pidit pohjassa keyta: "+key+"ja keyCodea: "+keyCode+", coded oli '"+coded+"'.");
		
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded) {
		//Worked fine
		System.out.println("Painoit keyta: "+key+" ja keyCodea: "+keyCode+", coded oli '"+coded+"'.");
		
		if (key == 'a')
			System.out.println("Bytheway, the key pressed was 'a'");
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded) {
		//Worked fine
		System.out.println("Paastit irti keysta: "+key+" ja keyCodesta: "+keyCode+", coded oli '"+coded+"'.");
		
	}
}
