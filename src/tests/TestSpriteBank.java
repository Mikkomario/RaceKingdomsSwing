package tests;

import java.io.FileNotFoundException;

import graphic.SpriteBank;

/**
 * This class loads all the sprites used in the tests
 *
 * @author Gandalf.
 *         Created 15.8.2013.
 */
public class TestSpriteBank extends SpriteBank
{
	@Override
	public void createSprites() throws FileNotFoundException
	{
		createSprite("images/crystal_mushroom_death_strip_4.png", 4, 62, 68, "mushroom");
	}
}
