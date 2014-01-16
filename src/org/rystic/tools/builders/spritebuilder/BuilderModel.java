package org.rystic.tools.builders.spritebuilder;

public class BuilderModel
{
	public BuilderModel()
	{
		_isEraser = false;
		_isEyedropper = false;
		_isFill = false;
	}
	
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
	
	private boolean _isEraser;
	private boolean _isEyedropper;
	private boolean _isFill;
}
