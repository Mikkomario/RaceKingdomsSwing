package racing;

import java.io.FileNotFoundException;

import graphic.SpriteBank;

/**
 * Holds all the sprites for the game's cars
 *
 * @author Gandalf.
 *         Created 15.6.2013.
 */
public class CarSpriteBank extends SpriteBank
{
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void createSprites() throws FileNotFoundException
	{
		createSprite("images/dragon_head.png", 1, 91, 52, "test");
		createSprite("images/dragon_head_mask.png", 1, 70, 52, "testcarmask");
	}

}
