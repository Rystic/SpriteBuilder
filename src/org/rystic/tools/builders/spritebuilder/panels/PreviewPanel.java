package org.rystic.tools.builders.spritebuilder.panels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.rystic.tools.builders.spritebuilder.SpriteBuilder;

public class PreviewPanel extends JPanel
{

	public PreviewPanel(int width_, int height_, SpriteBuilder parentBuilder_)
	{
		_gridWidth = width_;
		_gridHeight = height_;
		_parentBuilder = parentBuilder_;

		_pixelSize = 8;
	}

	public void updatePreview(Color[][] colorGrid_)
	{
		_colorGrid = colorGrid_;
		_gridWidth = colorGrid_[0].length;
		_gridHeight = colorGrid_[1].length;
		repaint();
	}

	protected void paintComponent(Graphics g_)
	{
		_pixelSize = _parentBuilder.getToolsPanel().getPixelSize();
		if (_colorGrid == null)
			return;
		g_.setColor(getBackground());
		g_.fillRect(0, 0, 2000, 2000);

		int widthStart = 0;
		int heightStart = 0;

		_parentBuilder.getPreviewFrame().setSize(
				_pixelSize * _gridWidth + 15,
				_pixelSize * _gridHeight + 38);

		for (int w = 0; w < _gridWidth; w++)
		{
			for (int h = 0; h < _gridHeight; h++)
			{
				if (_colorGrid[w][h] == null)
					continue;
				g_.setColor(_colorGrid[w][h]);
				g_.fillRect(widthStart + (w * _pixelSize), heightStart
						+ (h * _pixelSize), _pixelSize, _pixelSize);
			}
		}
	}

	public int getPixelSize()
	{
		return _pixelSize;
	}
	
	public void setPixelSize(int pixelSize_)
	{
		_pixelSize = pixelSize_;
	}

	private static final long serialVersionUID = 1L;

	private Color[][] _colorGrid;

	private int _gridWidth;
	private int _gridHeight;
	private int _pixelSize;

	private SpriteBuilder _parentBuilder;

}
