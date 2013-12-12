package SpriteBuilder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel
{
	public DrawPanel(int width, int height, SpriteBuilder parentBuilder)
	{
		_width = width;
		_height = height;
		
		_parentBuilder = parentBuilder;
		_drawPanelFrame = _parentBuilder.getDrawFrame();
		_toolsPanel = _parentBuilder.getToolsPanel();
	
		_colorGrid = new Color[width][height];

		_incrementGeneration = false;
		
		addMouseMotionListener(new DrawListener());
		addMouseListener(new DrawListener());
	}

	protected void paintComponent(Graphics g)
	{

		g.setColor(getBackground());
		g.fillRect(0, 0, 2000, 2000);

		_tileDim.width = _drawPanelFrame.getWidth() / _width;
		_tileDim.height = _drawPanelFrame.getHeight() / _height;

		while (_tileDim.width * _width > _drawPanelFrame.getWidth() - 15)
		{
			_tileDim.width--;
		}

		while (_tileDim.height * _height > _drawPanelFrame
				.getHeight() - 100)
		{
			_tileDim.height--;
		}

		for (int w = 0; w < _width; w++)
		{
			for (int h = 0; h < _height; h++)
			{
				g.setColor(Color.BLACK);
				if (!_toolsPanel.isGrid())
					g.setColor(Color.WHITE);

				g.clearRect(
						w * _tileDim.width,
						h * _tileDim.height,
						_tileDim.width,
						_tileDim.height);
				g.drawRect(
						w * _tileDim.width,
						h * _tileDim.height,
						_tileDim.width,
						_tileDim.height);

				if (_colorGrid[w][h] != null)
				{
					g.setColor(_colorGrid[w][h]);
					g.fillRect(
							w * _tileDim.width,
							h * _tileDim.height,
							_tileDim.width,
							_tileDim.height);
				}
			}
		}
		_parentBuilder.getPreviewPanel().updatePreview(_colorGrid);
		_parentBuilder.calculateCursor();
	}

	public void undo()
	{
		if (_head != null)
		{
			_head.decrementGeneration();
			repaint();
		}
	}

	public Color[][] getColorGrid()
	{
		return _colorGrid;
	}
	
	public void
	setColorGrid(Color[][] newColorGrid)
	{
		_colorGrid = newColorGrid;
	}

	private class DrawListener implements MouseMotionListener,
			MouseListener
	{

		private Color _newColor;
		private Color _oldColor;

		@Override
		public void mouseDragged(MouseEvent e)
		{
			int x = e.getX() / _tileDim.width;
			int y = e.getY() / _tileDim.height;

			if (x >= 0 && y >= 0 && x < _width && y < _height)
			{
				if (SwingUtilities.isRightMouseButton(e))
				{
					colorGrab(x, y);
				}
				else
				{
					replaceColor(
						x,
							y,
							_toolsPanel.isErase() ? null : _toolsPanel
									.getColor());
				}
			}
			_toolsPanel.repaint();
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{

		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
			int x = arg0.getX() / _tileDim.width;
			int y = arg0.getY() / _tileDim.height;
			if (x >= 0 && y >= 0 && x < _width && y < _height)
			{
				if (arg0.getButton() == MouseEvent.BUTTON3)
				{
					colorGrab(x, y);
				}
				else
				{
					_oldColor = _colorGrid[x][y] == null ? null : new Color(
							_colorGrid[x][y].getRed(),
							_colorGrid[x][y].getGreen(),
							_colorGrid[x][y].getBlue(), 255);
					replaceColor(
							x,
							y,
							_toolsPanel.isErase() ? null : _toolsPanel
									.getColor());
					if (_toolsPanel.isFill())
					{
						_newColor = _colorGrid[x][y];
						fill(x - 1, y);
						fill(x + 1, y);
						fill(x, y - 1);
						fill(x, y + 1);
					}
				}
			}
			_toolsPanel.repaint();
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			_parentBuilder.setEyedropper(false);
			_parentBuilder.calculateCursor();
			_toolsPanel.repaint();
			repaint();
			if (_head != null && _incrementGeneration)
			{
				_head.incrementGeneration();
				_incrementGeneration = false;
			}
		}

		private void colorGrab(int x, int y)
		{
			_parentBuilder.setEyedropper(true);
			if (_colorGrid[x][y] == null)
			{
				_toolsPanel.setErase(true);
				return;
			}
			_toolsPanel.setErase(false);
			Color newColor = _colorGrid[x][y];
			_toolsPanel.setColor(newColor);
		}

		private void fill(int x, int y)
		{
			LinkedList<IntegerPair> tiles = new LinkedList<IntegerPair>();
			tiles.add(new IntegerPair(x, y));

			while (tiles.size() > 0)
			{
				IntegerPair tile = tiles.pop();
				int tileX = tile._a;
				int tileY = tile._b;
				if (tileY >= _height || tileY < 0 || tileX >= _width
						|| tileX < 0)
					continue;
				if ((_newColor == null && _oldColor == null)
						|| (_newColor != null && _oldColor != null && _newColor
								.equals(_oldColor)))
					continue;

				boolean isReplaced = _colorGrid[tileX][tileY] == null ? _oldColor == null : _colorGrid[tileX][tileY]
						.equals(_oldColor);

				if (isReplaced)
				{
					replaceColor(tileX, tileY, _newColor);

					tiles.add(new IntegerPair(tileX - 1, tileY));
					tiles.add(new IntegerPair(tileX + 1, tileY));
					tiles.add(new IntegerPair(tileX, tileY - 1));
					tiles.add(new IntegerPair(tileX, tileY + 1));
				}
			}
		}

		private void replaceColor(int x, int y, Color newColor)
		{
			if ((newColor == null && _colorGrid[x][y] == null)
					|| (newColor != null && newColor
							.equals(_colorGrid[x][y])))
				return;
			_incrementGeneration = true;
			PreviousDrawAction prev = new PreviousDrawAction(x, y,
					_colorGrid[x][y] == null ? null : new Color(
							_colorGrid[x][y].getRed(),
							_colorGrid[x][y].getGreen(),
							_colorGrid[x][y].getBlue()));
			if (_head == null)
				_head = prev;
			else
				_head.add(prev);
			_colorGrid[x][y] = newColor;
		}
	}

	private class PreviousDrawAction
	{
		private final Color _prevColor;
		private final IntegerPair _xy;

		private PreviousDrawAction _next;
		private int _undoGeneration;

		public PreviousDrawAction(int x, int y, Color prevColor)
		{
			_xy = new IntegerPair(x, y);
			_prevColor = prevColor;
			_undoGeneration = 0;
		}

		public void add(PreviousDrawAction newAction)
		{
			newAction._next = this;
			_head = newAction;

		}

		public void incrementGeneration()
		{
			_undoGeneration++;
			if (_next != null)
			{
				_next.incrementGeneration();
			}
		}

		public void decrementGeneration()
		{
			if (_undoGeneration <= 1)
			{
				_colorGrid[_xy._a][_xy._b] = _prevColor;
				if (_head.equals(this))
				{
					_head = _head._next;
					if (_head != null)
						_head.decrementGeneration();
				}
			}
			else
			{
				_undoGeneration--;
				if (_head == null)
				{
					_head = this;
				}
				if (_next != null)
				{
					_next.decrementGeneration();
				}
			}
		}
	}

	private class IntegerPair
	{
		int _a;
		int _b;

		public IntegerPair(int a_, int b_)
		{
			_a = a_;
			_b = b_;
		}
	}
	
	private Dimension _tileDim = new Dimension(20, 20);

	private Color[][] _colorGrid;

	private PreviousDrawAction _head;

	private boolean _incrementGeneration;

	private int _width;
	private int _height;
		
	private SpriteBuilder _parentBuilder;
	private JFrame _drawPanelFrame;
	private ToolsPanel _toolsPanel;
}
