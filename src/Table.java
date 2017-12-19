import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * 
 * @author tln86
 *
 */
public class Table extends JPanel implements Runnable, KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private final int T_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
	private final int T_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
	private final int INITIAL_X = 1;
	private final int INITIAL_Y = T_HEIGHT / 2;
	private final int DELAY = 25;
	private double angle = Math.random() * 100;
	private Ball b;

	private enum Direction {
		NORTHWEST, SOUTHWEST, NORTHEAST, SOUTHEAST
	};

	private Direction currentDir = Direction.SOUTHEAST;
	private Thread animator;
	private int x, y;
	private int speed = 5;
	private Paddle p, p2;

	public Table() {
		while (angle > 40 || angle < 15) {
			angle = Math.random() * 100;
		}
		initBoard();

	}

	private void initBoard() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(T_WIDTH , T_HEIGHT));
		setDoubleBuffered(true);
		b = new Ball(INITIAL_X + 35, INITIAL_Y, 25, 25);
		p = new Paddle(INITIAL_X, INITIAL_Y, Color.RED);
		// Subtracting the width of a paddle from the width of the table
		p2 = new Paddle(T_WIDTH -p.getWidth(), INITIAL_Y,  Color.CYAN);
		x = INITIAL_X;
		y = INITIAL_Y;
		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);
	}

	@Override
	public void addNotify() {
		super.addNotify();

		animator = new Thread(this);
		// animator.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// Dashed line in the middle of the board
		float dash[] = { 10.0f };
		g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g.setColor(Color.WHITE);
		g.drawLine(T_WIDTH / 2, T_HEIGHT, T_WIDTH / 2, 0);
		// Draw paddles and ball
		b.draw(g, x+p.getWidth(), y, b.width, b.height);
		p.draw(g2);
		p2.draw(g2);
		// Telling the user to mouse click to start the game
		Font f = new Font("sans-serif", Font.PLAIN, 40);
		g2.setStroke(new BasicStroke(10));
		g2.setFont(f);
		g2.setColor(Color.ORANGE);
		if (!animator.isAlive()) {
			g.drawString("Left or Right Click to Start", T_WIDTH / 3, T_HEIGHT / 2);
		}
	}

	private void cycle() {

		// Ball Rebounds
		if (x > T_WIDTH - b.width) {
			// angle = 90 - angle;
			if (currentDir == Direction.SOUTHEAST) {
				currentDir = Direction.SOUTHWEST;
			} else
				currentDir = Direction.NORTHWEST;
		}

		if (x <= p.getWidth() && (currentDir == Direction.SOUTHWEST || currentDir == Direction.NORTHWEST)) {
			//System.out.println(p.collides(x, y)+ " " + x+ "  "+y);
			System.out.println(p);
			if (currentDir == Direction.SOUTHWEST) {
				currentDir = Direction.SOUTHEAST;
			} else if (currentDir == Direction.NORTHWEST) {
				currentDir = Direction.NORTHEAST;
			} else if (currentDir == Direction.SOUTHEAST) {
				currentDir = Direction.SOUTHWEST;
			} else if (currentDir == Direction.NORTHEAST) {
				currentDir = Direction.NORTHWEST;
			}
		}

		if (y > T_HEIGHT - b.height) {
			if (currentDir == Direction.SOUTHWEST) {
				currentDir = Direction.NORTHWEST;
			} else if (currentDir == Direction.SOUTHEAST) {
				currentDir = Direction.NORTHEAST;
			}
		}

		if (y < 0) {
			if (currentDir == Direction.NORTHWEST) {
				currentDir = Direction.SOUTHWEST;
			} else if (currentDir == Direction.NORTHEAST) {
				currentDir = Direction.SOUTHEAST;
			}
		}

		if (currentDir == Direction.SOUTHEAST) {
			// System.out.println("SE");
			x += speed;
			y += Math.PI * Math.toRadians(angle);
		}
		if (currentDir == Direction.SOUTHWEST) {
			// System.out.println("SW");
			x -= speed;
			y += Math.PI * Math.toRadians(angle);
		}

		if (currentDir == Direction.NORTHEAST) {
			// System.out.println("NE");
			x += speed;
			y -= Math.PI * Math.toRadians(angle);
		}
		if (currentDir == Direction.NORTHWEST) {
			// System.out.println("NW");
			x -= speed;
			y -= Math.PI * Math.toRadians(angle);
		}

	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {
			cycle();
			repaint();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
			beforeTime = System.currentTimeMillis();
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			if (p.getStartY() > 35) {
				p.changePos(-10);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			if (p.getStartY() < T_HEIGHT - 35) {
				p.changePos(10);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// System.out.println("up");

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// System.out.println("Test");

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		animator.start();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
