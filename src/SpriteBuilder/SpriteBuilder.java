package SpriteBuilder;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class SpriteBuilder
{
	public SpriteBuilder(int width, int height)
	{

		_frameMenuBar.add(_frameMenu);
		_frameMenu.add(new JMenuItem("(A)dd Frame"));
		_frameMenu.add(new JMenuItem("(C)opy Frame to New Frame"));
		_frameMenu.add(new JMenuItem("(R)emove Frame"));
		_frameMenu.add(_frameMenuBar);
		_frameMenu.setVisible(true);

		_toolPanelFrame.add(_toolsPanel);
		_toolPanelFrame.setTitle("Tools Panel");
		_toolPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_toolPanelFrame.setVisible(true);
		_toolPanelFrame.pack();
		_toolPanelFrame.setSize(
				_toolPanelFrame.getWidth(),
				_toolPanelFrame.getHeight() + 100);

		_toolPanelFrame.setJMenuBar(_toolsPanel.getJMenuBar());

		_previewPanel = new PreviewPanel(width, height, this);

		_previewPanelFrame.setSize(_toolPanelFrame.getWidth(), 300);
		_previewPanelFrame.add(_previewPanel);
		_previewPanelFrame.setTitle("Preview Panel");
		_previewPanelFrame.setLocation(0, _toolPanelFrame.getY()
				+ _toolPanelFrame.getHeight());
		_previewPanelFrame.setVisible(true);

		createDrawPanel(width, height);
	}
	
	public void createDrawPanel(int width, int height)
	{
		_drawPanel = new DrawPanel(width, height, this);
		_drawPanelTabbedPane.add(_drawPanel, "frame" + _drawPanelTabbedPane.getTabCount());
		_drawPanelTabbedPane.addKeyListener(new HotkeyListener(this));

		_drawPanelFrame.setLocation(_toolPanelFrame.getX()
				+ _toolPanelFrame.getWidth(), 0);
		_drawPanelFrame.setJMenuBar(_frameMenuBar);
		_drawPanelFrame.setSize(600, 600);
		_drawPanelFrame.add(_drawPanelTabbedPane);
		_drawPanelFrame.setTitle("Draw Panel");
		_drawPanelFrame.setVisible(true);
		_drawPanel.repaint();
	}

	public void calculateCursor()
	{
		boolean eraseOn = _toolsPanel.isErase();
		boolean fillOn = _toolsPanel.isFill();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image fillCursor = toolkit.getImage("art/fillTool.png");
		Image eraseCursor = toolkit.getImage("art/eraseTool.png");
		Image fillPlusEraseCursor = toolkit.getImage("art/fillPlusEraseTool.png");
		Image eyeDroperCursor = toolkit.getImage("art/eyeDropperTool.png");

		Cursor newCursor;// = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		if (_isEyedropper)
		{
			newCursor = toolkit.createCustomCursor(eyeDroperCursor, new Point(
					0, 0), "eyedropper");
		}
		else if (eraseOn)
		{
			if (fillOn)
			{
				newCursor = toolkit.createCustomCursor(
						fillPlusEraseCursor,
						new Point(0, 0),
						"fillPlusErase");
			}
			else
			{
				newCursor = toolkit.createCustomCursor(eraseCursor, new Point(
						0, 0), "Erase");
			}
		}
		else if (fillOn)
		{
			newCursor = toolkit.createCustomCursor(
					fillCursor,
					new Point(0, 0),
					"Fill");
		}
		else
		{
			newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		}
		_drawPanel.setCursor(newCursor);
	}

	/* Menu Actions */

	public boolean isEyedropper()
	{
		return _isEyedropper;
	}

	public void setEyedropper(boolean eyedropperVal)
	{
		_isEyedropper = eyedropperVal;
	}

	/* Getters */

	public ToolsPanel getToolsPanel()
	{
		return _toolsPanel;
	}

	public DrawPanel getDrawPanel()
	{
		return _drawPanel;
	}

	public PreviewPanel getPreviewPanel()
	{
		return _previewPanel;
	}
	
	public JFrame getToolsFrame()
	{
		return _toolPanelFrame;
	}

	public JFrame getDrawFrame()
	{
		return _drawPanelFrame;
	}

	public JFrame getPreviewFrame()
	{
		return _previewPanelFrame;
	}

	private JMenuBar _frameMenuBar = new JMenuBar();
	private JMenu _frameMenu = new JMenu("Frame Actions");

	private JTabbedPane _drawPanelTabbedPane = new JTabbedPane();

	private JFrame _drawPanelFrame = new JFrame();
	private DrawPanel _drawPanel;

	private JFrame _toolPanelFrame = new JFrame();
	private ToolsPanel _toolsPanel = new ToolsPanel(this);

	private JFrame _previewPanelFrame = new JFrame();
	private PreviewPanel _previewPanel;

	private boolean _isEyedropper;
}
