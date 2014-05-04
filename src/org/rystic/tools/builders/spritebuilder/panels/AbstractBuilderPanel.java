package org.rystic.tools.builders.spritebuilder.panels;

import javax.swing.JPanel;

import org.rystic.tools.builders.spritebuilder.BuilderModel;

@SuppressWarnings("serial")
public abstract class AbstractBuilderPanel extends JPanel
{
	public AbstractBuilderPanel(BuilderModel model_)
	{
		_model = model_;
		addListeners();
	}
	
	public abstract void addListeners();
	
	protected BuilderModel _model;
}
