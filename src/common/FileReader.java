package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Filereader is an abstract class that allows the subclasses to read files 
 * and react to their content.
 *
 * @author Mikko Hilpinen.
 *         Created 19.7.2013.
 */
public abstract class FileReader
{
	// ABSTRACT METHODS	--------------------------------------------------
	
	/**
	 * This method is called each time a line is read from a file with the 
	 * readFile method
	 *
	 * @param line The line read from the file
	 * @see readFile
	 */
	protected abstract void onLine(String line);
	
	
	// IMPLEMENTED METHODS	----------------------------------------------
	
	@Override
	public String toString()
	{
		return getClass().getName();
	}
	
	
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
	 * Reads a file and makes the object react to it somehow. Skips all the 
	 * empty lines in the file and calls the onLine method at each line read.<br>
	 * Doesn't read a file that doesn't exist and prints an error message if 
	 * no file was found
	 *
	 * @param filename The name of the file read (src/data/ is added 
	 * automatically to the beginning)
	 * @see onLine
	 */
	public void readFile(String filename)
	{
		// First checks if the file actually exists
		File file = new File("src/data/" + filename);
		Scanner scanner = null;
		
		// Tries to open the file
		try
		{
			scanner = new Scanner(file);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File src/data/" + filename + " does not exist!");
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
