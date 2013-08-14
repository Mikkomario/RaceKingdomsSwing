package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import handlers.MouseListenerHandler;

/**
 * This class tests whether the mouse is working or not
 * 
 * @author: Unto 
 * 			Created 8.8.2013
 * */
public class MouseTest extends AbstractTest implements listeners.AdvancedMouseListener
{
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
	public MouseTest(ActorHandler actorhandler, DrawableHandler drawer, 
			KeyListenerHandler keylistenerhandler, 
			MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);
		mouselistenerhandler.addMouseListener(this);
		this.active = false;
		this.isDead = false;
	}

	@Override
	public void test()
	{
		activate();
	}
	
	
	// ---IMPLEMENTED NON-IMPORTANT METHODS--------------
	
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

	
	// --------------------------------------------------------

	@Override
	public void onLeftDown(int mouseX, int mouseY) {
		System.out.println("Vasen hiiren nappula pohjassa koordinaateissa X:"+mouseX+" Y:"+mouseY);

	}

	@Override
	public void onRightDown(int mouseX, int mouseY) {
		System.out.println("Oikea hiiren nappula pohjassa koordinaateissa X:"+mouseX+" Y:"+mouseY);

	}

	@Override
	public void onLeftPressed(int mouseX, int mouseY) {
		System.out.println("Vasenta hiiren nappulaa painettiin koordinaateissa X:"+mouseX+" Y:"+mouseY);

	}

	@Override
	public void onRightPressed(int mouseX, int mouseY) {
		System.out.println("Oikeaa hiiren nappulaa painettiin koordinaateissa X:"+mouseX+" Y:"+mouseY);

	}

	@Override
	public void onLeftReleased(int mouseX, int mouseY) {
		System.out.println("Vasen hiiren nappula vapautettiin koordinaateissa X:"+mouseX+" Y:"+mouseY);

	}

	@Override
	public void onRightReleased(int mouseX, int mouseY) {
		System.out.println("Oikea hiiren nappula vapautettiin koordinaateissa X:"+mouseX+" Y:"+mouseY);
		
	}

	@Override
	public boolean listensPosition(int x, int y) {
		//Checks if the mouse is on the left side of the screen
		return x<500;
	}

	@Override
	public boolean listensMouseEnterExit() {
		// Mouse is interested whether it enters or leaves an interesting zone
		return true;
	}

	@Override
	public void onMouseEnter(int mouseX, int mouseY) {
		System.out.println("Hiiri tuli kivalle alueelle! X:"+mouseX+" Y:"+mouseY);

	}

	@Override
	public void onMouseOver(int mouseX, int mouseY) {
		System.out.println("Hiiri on kivalla alueella! X:"+mouseX+" Y:"+mouseY);

	}

	@Override
	public void onMouseExit(int mouseX, int mouseY) {
		System.out.println("Hiiri poistui kivalta alueelta :( X:"+mouseX+" Y:"+mouseY);

	}

	@Override
	public void onMouseMove(int mouseX, int mouseY) {
		// Reacts to mouses movements
		
	}
	
}
