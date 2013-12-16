package org.rystic.tools.builders;

import org.rystic.tools.builders.spritebuilder.SpriteBuilder;

public class BuilderMain
{

	public static BuilderMain builder;

	public static void main(String[] args)
	{
		_main = new SpriteBuilder(IMAGE_SIZE, IMAGE_SIZE);
	}

	public static int IMAGE_SIZE = 25;

	public static SpriteBuilder _main;
}
