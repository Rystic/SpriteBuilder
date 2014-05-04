package org.rystic.tools.builders.spritebuilder;

import java.awt.Color;

import javax.swing.JColorChooser;

public class BuilderModel
{
	public BuilderModel()
	{
		_isEraser = false;
		_isEyedropper = false;
		_isFill = false;
	}
	
	public void setColorGrid(Color[][] colorGrid_)
	{
		_colorGrid = colorGrid_;
	}

	public void setColorChooser(JColorChooser chooser_)
	{
		_chooser = chooser_;
	}
	
	public void setSelectedColor(Color color_)
	{
		_chooser.setColor(color_);
	}

	public void setPixelSize(int pixelSize_)
	{
		_pixelSize = pixelSize_;
	}

	public void setEraser(boolean isEraser_)
	{
		_isEraser = isEraser_;
	}

	public void setEyedropper(boolean isEyedropper_)
	{
		_isEyedropper = isEyedropper_;
	}

	public void setFill(boolean isFill_)
	{
		_isFill = isFill_;
	}

	public void setGrid(boolean isGrid_)
	{
		_isGrid = isGrid_;
	}
	
	public Color[][] getColorGrid()
	{
		return _colorGrid;
	}

	public Color getSelectedColor()
	{
		return _chooser.getColor();
	}
	
	public int getPixelSize()
	{
		return _pixelSize;
	}

	public boolean isEraser()
	{
		return _isEraser;
	}

	public boolean isEyedropper()
	{
		return _isEyedropper;
	}

	public boolean isFill()
	{
		return _isFill;
	}

	public boolean isGrid()
	{
		return _isGrid;
	}
	
	public int getGridWidth()
	{
		return _colorGrid.length;
	}

	public int getGridHeight()
	{
		if (_colorGrid.length == 0)
			return 0;
		return _colorGrid[0].length;
	}
	
	public void setColorAt(int x_, int y_, Color color_)
	{
		_colorGrid[x_][y_] = color_;
	}
	
	private Color[][] _colorGrid;

	private int _pixelSize;

	private JColorChooser _chooser;
	
	private boolean _isEraser;
	private boolean _isEyedropper;
	private boolean _isFill;
	private boolean _isGrid;

}
