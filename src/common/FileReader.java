package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Filereader is an abstract class that allows the subclasses to read files 
 * and react to them somehow.
 *
 * @author Gandalf.
 *         Created 19.7.2013.
 */
public abstract class FileReader
{
	// ABSTRACT METHODS	--------------------------------------------------
	
	/**
	 * This method is called each time a line is read from a file
	 *
	 * @param line The line read from the file
	 */
	protected abstract void onLine(String line);
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Checks if a certain file exists
	 *
	 * @param filename the name of the file
	 * @return Does the file exist
	 */
	public static boolean checkFile(String filename)
	{
		File test = new File(filename);
		return test.isFile();
	}
	
	/**
	 * Reads a file and makes the object react to it somehow
	 *
	 * @param filename
	 * @param applet
	 */
	protected void readFile(String filename)
	{
		// First checks if the file actually exists
		File file = new File(filename);
		Scanner scanner = null;
		
		// Tries to open the file
		try
		{
			scanner = new Scanner(file);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File " + filename + " does not exist!");
			return;
		}
		
		String line = "";
		
		// Reads the file
		// Loops until the file ends
		while (scanner.hasNextLine())
		{	
			line = scanner.nextLine();
			
			// Skips the empty lines
			if (line.length() == 0)
				continue;
			
			onLine(line);
		}
		// Closes the file in the end
		scanner.close();
	}
}
