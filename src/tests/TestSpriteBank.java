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
		createSprite("images/testitausta_strip6.png", 6, 48, 48, "background");
		createSprite("images/testitausta2.png", 1, 48, 48, "background2");
	}
}
