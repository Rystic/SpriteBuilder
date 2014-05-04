package org.rystic.tools.builders.spritebuilder;

<<<<<<< HEAD
import java.awt.Color;

import javax.swing.JColorChooser;

=======
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7
public class BuilderModel
{
	public BuilderModel()
	{
		_isEraser = false;
		_isEyedropper = false;
		_isFill = false;
	}
	
<<<<<<< HEAD
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

=======
	public void setEraser(boolean _isEraser)
	{
		this._isEraser = _isEraser;
	}
	
	public void setEyedropper(boolean _isEyedropper)
	{
		this._isEyedropper = _isEyedropper;
	}
	
	public void setFill(boolean _isFill)
	{
		this._isFill = _isFill;
	}
	
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7
	public boolean isEraser()
	{
		return _isEraser;
	}
<<<<<<< HEAD

=======
	
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7
	public boolean isEyedropper()
	{
		return _isEyedropper;
	}
<<<<<<< HEAD

=======
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7
	public boolean isFill()
	{
		return _isFill;
	}
<<<<<<< HEAD

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
=======
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7
	
	private boolean _isEraser;
	private boolean _isEyedropper;
	private boolean _isFill;
<<<<<<< HEAD
	private boolean _isGrid;

=======
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7
}
