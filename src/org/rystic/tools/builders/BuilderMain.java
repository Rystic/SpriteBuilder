package org.rystic.tools.builders;

import org.rystic.tools.builders.spritebuilder.SpriteBuilder;

public class BuilderMain
{

	public static BuilderMain builder;

	public static void main(String[] args)
	{
		_main = new SpriteBuilder(_imageSize, _imageSize);
	}

	public static int _imageSize = 35;

	public static SpriteBuilder _main;
}
