package org.rystic.tools.builders.spritebuilder.panels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.rystic.tools.builders.spritebuilder.BuilderModel;

@SuppressWarnings("serial")
public class PreviewPanel extends AbstractBuilderPanel
{
	public PreviewPanel(BuilderModel model_)
	{
		super(model_);
		_pixelSize = 8;
	}
	
	@Override
	public void addListeners()
	{
		
	}

	protected void paintComponent(Graphics g_)
	{
		_pixelSize = _model.getPixelSize();
		JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);

		Color[][] colorGrid = _model.getColorGrid();
		
		g_.setColor(getBackground());
		g_.fillRect(0, 0, 2000, 2000);

		int widthStart = 0;
		int heightStart = 0;

		parent.setSize(
				_pixelSize * _model.getGridWidth() + 15,
				_pixelSize * _model.getGridHeight() + 38);

		for (int width = 0; width < _model.getGridWidth(); width++)
		{
			for (int height = 0; height < _model.getGridHeight(); height++)
			{
				if (colorGrid[width][height] == null)
					continue;
				g_.setColor(_model.getColorGrid()[width][height]);
				g_.fillRect(widthStart + (width * _pixelSize), heightStart
						+ (height * _pixelSize), _pixelSize, _pixelSize);
			}
		}
	}

	private int _pixelSize;
}
