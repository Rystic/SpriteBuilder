package org.rystic.tools.builders.spritebuilder;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.bushe.swing.event.EventSubscriber;
import org.rystic.tools.builders.spritebuilder.panels.DrawPanel;
import org.rystic.tools.builders.spritebuilder.panels.PreviewPanel;
import org.rystic.tools.builders.spritebuilder.panels.ToolsPanel;
import org.rystic.tools.builders.spritebuilder.panels.events.RepaintEvent;

public class SpriteBuilder implements EventSubscriber<RepaintEvent>
{
	public SpriteBuilder(int width, int height)
	{
		_model = new BuilderModel();
		_model.setColorGrid(new Color[BuilderConstants.DEFAULT_IMAGE_SIZE][BuilderConstants.DEFAULT_IMAGE_SIZE]);

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				createToolPanel();
				createDrawPanel();
				createPreviewPanel();
			}
		});
	}

	private void createToolPanel()
	{
		System.out.println(SwingUtilities.isEventDispatchThread());
		_toolsPanelFrame = new JFrame();
		_toolsPanel = new ToolsPanel(_model);

		_toolsPanelFrame.add(_toolsPanel);
		_toolsPanelFrame.setTitle("Tools Panel");
		_toolsPanelFrame.pack();

		_toolsPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		_toolsPanelFrame.setJMenuBar(_toolsPanel.getJMenuBar());
		_toolsPanelFrame.setVisible(true);
	}

	private void createDrawPanel()
	{
		_drawPanelFrame = new JFrame();
		_drawPanel = new DrawPanel(_model);

		_drawPanelFrame.add(_drawPanel);
		_drawPanelFrame.setTitle("Draw Panel");
		_drawPanelFrame.setSize(600, 600);

		_drawPanelFrame.setLocation(
				_toolsPanelFrame.getX() + _toolsPanelFrame.getWidth(),
				0);
		_drawPanelFrame.setVisible(true);
	}

	private void createPreviewPanel()
	{
		_previewPanelFrame = new JFrame();
		_previewPanel = new PreviewPanel(_model);

		_previewPanelFrame.add(_previewPanel);
		_previewPanelFrame.setTitle("Preview Panel");
		_previewPanelFrame.setSize(_toolsPanelFrame.getWidth(), 300);

		_previewPanelFrame.setLocation(0, _toolsPanelFrame.getY()
				+ _toolsPanelFrame.getHeight());
		_previewPanelFrame.setVisible(true);
	}

	@Override
	public void onEvent(RepaintEvent event_)
	{
		System.out.println("YOLO");
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("really?");
				_toolsPanelFrame.repaint();
				_drawPanelFrame.repaint();
				_previewPanelFrame.repaint();
			}
		});
	}

	public void calculateCursor()
	{
		boolean eraseOn = _toolsPanel.isErase();
		boolean fillOn = _toolsPanel.isFill();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image fillCursor = toolkit.getImage("art/fillTool.png");
		Image eraseCursor = toolkit.getImage("art/eraserIcon.png");
		Image fillPlusEraseCursor = toolkit
				.getImage("art/fillPlusEraseTool.png");
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
		_drawPanel.repaint();
	}

	public static void main(String[] args)
	{
		new SpriteBuilder(BuilderConstants.DEFAULT_IMAGE_SIZE,
				BuilderConstants.DEFAULT_IMAGE_SIZE);
	}

	private static BuilderModel _model;

	private ToolsPanel _toolsPanel;
	private DrawPanel _drawPanel;
	private PreviewPanel _previewPanel;

	private JFrame _toolsPanelFrame;
	private JFrame _drawPanelFrame;
	private JFrame _previewPanelFrame;

	private boolean _isEyedropper;
}
