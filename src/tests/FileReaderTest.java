package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * This test reads a file and prints it to the system
 *
 * @author Mikko Hilpinen.
 *         Created 25.8.2013.
 */
public class FileReaderTest extends AbstractTest
{
	// ATTRIBUTES	------------------------------------------------------
	
	private TestFileReader reader;
	private static String filename = "filetest.txt";
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 */
	public FileReaderTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);
		// Initializes attributes
		this.reader = new TestFileReader();
	}

	@Override
	public void test()
	{
		// Reads the file
		this.reader.readFile(filename);
		System.out.println("Test complete");
	}
}
