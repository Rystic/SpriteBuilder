package org.rystic.tools.builders.spritebuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.rystic.tools.builders.PNGDrawer;


@SuppressWarnings("serial")
public class ToolsPanel extends JPanel
{
	public ToolsPanel(SpriteBuilder parentBuilder)
	{

		_parentBuilder = parentBuilder;

		// _fillButton.setIcon(new ImageIcon("fillTool.png"));
		_fillButton.addKeyListener(new HotkeyListener(_parentBuilder));

		// _eraseButton.setIcon(new ImageIcon("eraseTool.png"));
		_eraseButton.addKeyListener(new HotkeyListener(_parentBuilder));

		_gridButton.setSelected(true);
		_gridButton.addActionListener(new GridListener());
		_gridButton.addKeyListener(new HotkeyListener(_parentBuilder));

		_convertToPng.addActionListener(new PNGConverstionListener());
		_convertToPng.addKeyListener(new HotkeyListener(_parentBuilder));

		_colorChooser = new JColorChooser();

		// menu bar

		_menuBar = new JMenuBar();
		_fileMenu = new JMenu("File");
		_newSpriteOption = new JMenuItem("New Sprite", new ImageIcon(
				"art/newFileIcon.png"));
		_newSpriteOption.addActionListener(new NewFileListener());
		_saveOption = new JMenuItem("Save", new ImageIcon("art/saveIcon.png"));
		_saveOption.addActionListener(new SaveFileListener());
		_loadOption = new JMenuItem("Load", new ImageIcon("art/loadIcon.png"));
		_loadOption.addActionListener(new LoadFileListener());
		_exitOption = new JMenuItem("Exit");
		_exitOption.addActionListener(new ExitListener());

		_fileMenu.add(_newSpriteOption);
		_fileMenu.add(_saveOption);
		_fileMenu.add(_loadOption);
		_fileMenu.addSeparator();
		_fileMenu.add(_exitOption);

		_viewMenu = new JMenu("View");
		_previewMenuOption = new JMenuItem("Preview Panel");
		_previewMenuOption.addActionListener(new PanelOptionListener(
				PanelOptionListener.PANEL_PREVIEW));
		_drawMenuOption = new JMenuItem("Draw Panel");
		_drawMenuOption.addActionListener(new PanelOptionListener(
				PanelOptionListener.PANEL_DRAW));

		_pixelSizeField = new JSlider();
		_pixelSizeField.addChangeListener(new PixelSizeListener());
		_pixelSizeLabel = new JLabel();

		_viewMenu.add(_previewMenuOption);
		_viewMenu.add(_drawMenuOption);

		_menuBar.add(_fileMenu);
		_menuBar.add(_viewMenu);

		drawComponents();
	}

	private void drawComponents()
	{
		setLayout(new BorderLayout());
		add(_colorChooser, BorderLayout.NORTH);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(0, 2));

		JPanel pixelAdjust = new JPanel();

		pixelAdjust.setLayout(new BoxLayout(pixelAdjust, BoxLayout.Y_AXIS));

		_pixelSizeLabel.setText("Block Size: " + _pixelSizeField.getValue() + " px");
		_pixelSizeLabel.setAlignmentY(200f);

		pixelAdjust.add(_pixelSizeLabel);
		pixelAdjust.add(_pixelSizeField);
		add(pixelAdjust, BorderLayout.SOUTH);
		buttonsPanel.add(_eraseButton);
		buttonsPanel.add(_fillButton);
		buttonsPanel.add(_gridButton);
		buttonsPanel.add(_convertToPng);
		add(buttonsPanel, BorderLayout.CENTER);

	}

	public JMenuBar getJMenuBar()
	{
		return _menuBar;
	}

	public boolean isErase()
	{
		return _eraseButton.isSelected();
	}

	public void setErase(boolean eraseVal)
	{
		_eraseButton.setSelected(eraseVal);
	}

	public boolean isFill()
	{
		return _fillButton.isSelected();
	}

	public void setFill(boolean fillVal)
	{
		_fillButton.setSelected(fillVal);
	}

	public boolean isGrid()
	{
		return _gridButton.isSelected();
	}

	public void setGrid(boolean gridVal)
	{
		_gridButton.setSelected(gridVal);
	}

	public Color getColor()
	{
		return _colorChooser.getColor();
	}

	public void setColor(Color color)
	{
		_colorChooser.setColor(color);
	}

	public int getPixelSize()
	{
		return _pixelSizeField.getValue() > 0 ? _pixelSizeField.getValue() : 1;
	}

	private class PNGConverstionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			PNGDrawer.drawPNG(
					_parentBuilder.getDrawPanel().getColorGrid(),
					_parentBuilder.getPreviewPanel().getPixelSize());
		}

	}

	private class GridListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			_parentBuilder.getDrawPanel().repaint();
		}

	}

	private class NewFileListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			_parentBuilder.createDrawPanel(20, 10);
		}

	}

	private class SaveFileListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showSaveDialog(ToolsPanel.this);
			File file = fileChooser.getSelectedFile();
			if (file == null)
				return;
			PrimitiveSprite sprite = new PrimitiveSprite(_parentBuilder
					.getDrawPanel().getColorGrid());
			try
			{
				FileOutputStream fileOut = new FileOutputStream(
						file.getAbsolutePath() + ".ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(sprite);
				out.close();
			} catch (Exception e1)
			{
				System.out.println("Save failed. file=" + file + " exception="
						+ e1.getMessage());
			}
		}

	}

	private class LoadFileListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(ToolsPanel.this);
			File file = fileChooser.getSelectedFile();
			if (file == null)
				return;
			try
			{
				FileInputStream fileIn = new FileInputStream(
						file.getAbsoluteFile());
				ObjectInputStream in = new ObjectInputStream(fileIn);
				PrimitiveSprite sprite = (PrimitiveSprite) in.readObject();
				in.close();
				fileIn.close();
				_parentBuilder.getDrawPanel().setColorGrid(
						sprite.getColorGrid());
				_parentBuilder.getDrawPanel().repaint();
			} catch (Exception e1)
			{
				System.out.println("Load failed. file=" + file + " exception="
						+ e1.getMessage());
			}
		}

	}

	private class ExitListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}

	private class PanelOptionListener implements ActionListener
	{
		public PanelOptionListener(String panelType_)
		{
			_panelType = panelType_;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (_panelType.equals(PANEL_PREVIEW))
				_parentBuilder.getPreviewFrame().setVisible(true);
			else if (_panelType.equals(PANEL_DRAW))
				_parentBuilder.getDrawFrame().setVisible(true);
			else
				System.out.println("Unknown panel type.");

		}

		static final String PANEL_PREVIEW = "PREVIEW";
		static final String PANEL_DRAW = "DRAW";

		private String _panelType;
	}
	
	private class PixelSizeListener implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e)
		{
			_pixelSizeLabel.setText("Block Size: " + _pixelSizeField.getValue() + " px");
			_parentBuilder.getPreviewPanel().repaint();
		}
		
	}

	private JMenuBar _menuBar;

	private JMenu _fileMenu;
	private JMenuItem _newSpriteOption;
	private JMenuItem _saveOption;
	private JMenuItem _loadOption;
	private JMenuItem _exitOption;

	private JMenu _viewMenu;
	private JMenuItem _previewMenuOption;
	private JMenuItem _drawMenuOption;
	
	private JLabel _pixelSizeLabel;

	private JSlider _pixelSizeField;

	private JColorChooser _colorChooser;
	private JToggleButton _eraseButton = new JToggleButton("(E)rase");
	private JToggleButton _fillButton = new JToggleButton("(F)ill");
	private JToggleButton _gridButton = new JToggleButton("(G)rid");

	private JButton _convertToPng = new JButton("(C)onvert to .png");

	private final SpriteBuilder _parentBuilder;

}
