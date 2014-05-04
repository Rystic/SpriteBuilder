package org.rystic.tools.builders.spritebuilder.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.bushe.swing.event.EventBus;
import org.rystic.tools.builders.spritebuilder.BuilderModel;
import org.rystic.tools.builders.spritebuilder.panels.events.RepaintEvent;

@SuppressWarnings("serial")
public class DrawPanel extends AbstractBuilderPanel
{
	public DrawPanel(BuilderModel model_)
	{
		super(model_);
		_incrementGeneration = false;
	}

	//
	@Override
	public void addListeners()
	{
		addMouseMotionListener(new DrawListener());
		addMouseListener(new DrawListener());
	}

	protected void paintComponent(Graphics g)
	{

		g.setColor(getBackground());
		g.fillRect(0, 0, 2000, 2000);

		JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);

		_tileDim.width = parent.getWidth() / _model.getGridWidth();
		_tileDim.height = parent.getHeight() / _model.getGridHeight();

		Color[][] colorGrid = _model.getColorGrid();

		while (_tileDim.width * _model.getGridWidth() > parent.getWidth() - 15)
		{
			_tileDim.width--;
		}

		while (_tileDim.height * _model.getGridHeight() > parent.getHeight() - 100)
		{
			_tileDim.height--;
		}

		for (int w = 0; w < _model.getGridWidth(); w++)
		{
			for (int h = 0; h < _model.getGridHeight(); h++)
			{
				g.setColor(Color.BLACK);
				if (!_model.isGrid())
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

				if (colorGrid[w][h] != null)
				{
					g.setColor(colorGrid[w][h]);
					g.fillRect(
							w * _tileDim.width,
							h * _tileDim.height,
							_tileDim.width,
							_tileDim.height);
				}
			}
		}
	}

	public void undo()
	{
		if (_head != null)
		{
			_head.decrementGeneration();
			repaint();
		}
	}

	private class DrawListener implements MouseMotionListener, MouseListener
	{

		private Color _newColor;
		private Color _oldColor;

		@Override
		public void mouseDragged(MouseEvent e)
		{
			int x = e.getX() / _tileDim.width;
			int y = e.getY() / _tileDim.height;

			if (x >= 0 && y >= 0 && x < _model.getGridWidth()
					&& y < _model.getGridHeight())
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
							_model.isEraser() ? null : _model
									.getSelectedColor());
				}
			}
			EventBus.publish(new RepaintEvent());
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
			Point click = new Point(arg0.getX(), arg0.getY());

			Color[][] colorGrid = _model.getColorGrid();

			int x = arg0.getX() / _tileDim.width;
			int y = arg0.getY() / _tileDim.height;
			if (x >= 0 && y >= 0 && x < _model.getGridWidth()
					&& y < _model.getGridHeight())
			{
				if (arg0.getButton() == MouseEvent.BUTTON3)
				{
					colorGrab(x, y);
				}
				else
				{
					_oldColor = colorGrid[x][y] == null ? null : colorGrid[x][y];
					replaceColor(
							x,
							y,
							_model.isEraser() ? null : _model
									.getSelectedColor());
					if (_model.isFill())
					{
						_newColor = colorGrid[x][y];
						fill(x - 1, y);
						fill(x + 1, y);
						fill(x, y - 1);
						fill(x, y + 1);
					}
				}
			}
			EventBus.publish(new RepaintEvent());
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			_model.setEyedropper(false);
			EventBus.publish(new RepaintEvent());
			if (_head != null && _incrementGeneration)
			{
				_head.incrementGeneration();
				_incrementGeneration = false;
			}
		}

		private void colorGrab(int x, int y)
		{
			_model.setEyedropper(true);
			Color[][] _colorGrid = _model.getColorGrid();

			if (_colorGrid[x][y] == null)
			{
				_model.setEraser(true);
				return;
			}
			_model.setEraser(false);
			Color newColor = _colorGrid[x][y];
			_model.setSelectedColor(newColor);
		}

		private void fill(int x, int y)
		{
			LinkedList<Point> tiles = new LinkedList<Point>();
			tiles.add(new Point(x, y));
<<<<<<< HEAD

			Color[][] colorGrid = _model.getColorGrid();
=======
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7

			while (tiles.size() > 0)
			{
				Point tile = tiles.pop();
				int tileX = tile.x;
				int tileY = tile.y;
<<<<<<< HEAD
				if (tileY >= _model.getGridHeight() || tileY < 0
						|| tileX >= _model.getGridWidth() || tileX < 0)
=======
				if (tileY >= _height || tileY < 0 || tileX >= _width
						|| tileX < 0)
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7
					continue;
				if ((_newColor == null && _oldColor == null)
						|| (_newColor != null && _oldColor != null && _newColor
								.equals(_oldColor)))
					continue;

				boolean isReplaced = colorGrid[tileX][tileY] == null ? _oldColor == null : colorGrid[tileX][tileY]
						.equals(_oldColor);

				if (isReplaced)
				{
					replaceColor(tileX, tileY, _newColor);

					tiles.add(new Point(tileX - 1, tileY));
					tiles.add(new Point(tileX + 1, tileY));
					tiles.add(new Point(tileX, tileY - 1));
					tiles.add(new Point(tileX, tileY + 1));
				}
			}
		}

		private void replaceColor(int x_, int y_, Color newColor_)
		{
			Color[][] colorGrid = _model.getColorGrid();
			if ((newColor_ == null && colorGrid[x_][y_] == null)
					|| (newColor_ != null && newColor_
							.equals(colorGrid[x_][y_])))
				return;
			_incrementGeneration = true;
			PreviousDrawAction prev = new PreviousDrawAction(x_, y_,
					colorGrid[x_][y_] == null ? null : colorGrid[x_][y_]);
			if (_head == null)
				_head = prev;
			else
				_head.add(prev);
			_model.setColorAt(x_, y_, newColor_);
		}
	}

	private class PreviousDrawAction
	{
		private final Color _prevColor;
		private final Point _xy;

		private PreviousDrawAction _next;
		private int _undoGeneration;

		public PreviousDrawAction(int x, int y, Color prevColor)
		{
			_xy = new Point(x, y);
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
			Color[][] colorGrid = _model.getColorGrid();
			if (_undoGeneration <= 1)
			{
<<<<<<< HEAD
				colorGrid[_xy.x][_xy.y] = _prevColor;
=======
				_colorGrid[_xy.x][_xy.y] = _prevColor;
>>>>>>> ce82932371f90217c71ca44ca1ba0dc0849cdbe7
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

	private Dimension _tileDim = new Dimension(20, 20);

	private PreviousDrawAction _head;

	private boolean _incrementGeneration;

}
