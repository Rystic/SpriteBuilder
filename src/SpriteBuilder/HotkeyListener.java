package SpriteBuilder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import builders.PNGDrawer;

public class HotkeyListener implements KeyListener
{
	public HotkeyListener(SpriteBuilder parentBuilder)
	{
		_parentBuilder = parentBuilder;
	}

	@Override
	public void keyPressed(KeyEvent e_)
	{
		_toolsPanel = _parentBuilder.getToolsPanel();
		_drawPanel = _parentBuilder.getDrawPanel();
		if (e_.getKeyCode() == KeyEvent.VK_E)
		{
			_toolsPanel.setErase(!_toolsPanel.isErase());
		}
		else if (e_.getKeyCode() == KeyEvent.VK_F)
		{
			_toolsPanel.setFill(!_toolsPanel.isFill());
		}
		else if (e_.getKeyCode() == KeyEvent.VK_G)
		{
			_toolsPanel.setGrid(!_toolsPanel.isGrid());
			_toolsPanel.repaint();
			_drawPanel.repaint();
		}
		else if (e_.getKeyCode() == KeyEvent.VK_Z && e_.isControlDown())
		{
			_drawPanel.undo();
		}
		else if (e_.getKeyCode() == KeyEvent.VK_C)
		{
			PNGDrawer.drawPNG(_drawPanel.getColorGrid(), 5);
		}
		_parentBuilder.calculateCursor();
		_toolsPanel.repaint();
		_drawPanel.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		keyPressed(e);
	}

	private SpriteBuilder _parentBuilder;

	private ToolsPanel _toolsPanel;
	private DrawPanel _drawPanel;
}
