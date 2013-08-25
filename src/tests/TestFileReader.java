package tests;

import common.FileReader;

/**
 * This filereader reads a file and prints it to the system
 *
 * @author Mikko Hilpinen.
 *         Created 25.8.2013.
 */
public class TestFileReader extends FileReader
{
	@Override
	protected void onLine(String line)
	{
		System.out.println(line);
	}
}
