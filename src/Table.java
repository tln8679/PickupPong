import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class Table extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private final int B_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
	private final int B_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
	private final int INITIAL_X = 1;
	private final int INITIAL_Y = B_HEIGHT / 2;
	private final int DELAY = 25;
	private double angle = Math.random()*100;
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
		while (angle > 40 || angle <15) {
			angle = Math.random()*100;
		}
		System.out.println(angle);
		initBoard();
	}

	private void initBoard() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setDoubleBuffered(true);
		b = new Ball(INITIAL_X, INITIAL_Y, 25, 25);
		p = new Paddle(INITIAL_X, INITIAL_Y, INITIAL_X,INITIAL_Y+35 , Color.RED);
		p2 = new Paddle(B_WIDTH, INITIAL_Y, B_WIDTH,INITIAL_Y+35 , Color.CYAN);
		x = INITIAL_X;
		y = INITIAL_Y;
	}


	@Override
	public void addNotify() {
		super.addNotify();

		animator = new Thread(this);
		animator.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		float dash[] = { 10.0f };
		g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
		        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g.setColor(Color.WHITE);
		g.drawLine(B_WIDTH/2, B_HEIGHT, B_WIDTH/2, 0);
		b.draw(g,x,y,b.width,b.height);
		p.draw(g2);
		p2.draw(g2);
	}

	private void cycle() {

		// Ball Rebounds
		if (x > B_WIDTH - b.width) {
			//angle = 90 - angle;
			if (currentDir == Direction.SOUTHEAST) {
				currentDir = Direction.SOUTHWEST;
			} else
				currentDir = Direction.NORTHWEST;
		}

	
		if (x < 0 && (currentDir == Direction.SOUTHWEST ||currentDir == Direction.NORTHWEST )) {
			if (currentDir == Direction.SOUTHWEST) {
				currentDir = Direction.SOUTHEAST;
			}
			else if (currentDir == Direction.NORTHWEST) {
				currentDir = Direction.NORTHEAST;
			}
			else if (currentDir == Direction.SOUTHEAST) {
				currentDir = Direction.SOUTHWEST;
			}
			else if (currentDir == Direction.NORTHEAST) {
				currentDir = Direction.NORTHWEST;
			}
			}
		
		if (y > B_HEIGHT - b.height) {
			if (currentDir == Direction.SOUTHWEST) {	
				currentDir = Direction.NORTHWEST;
			}
			else if (currentDir == Direction.SOUTHEAST) {
				currentDir = Direction.NORTHEAST;
			}
		}
		
		if (y < 0) {
			if (currentDir == Direction.NORTHWEST) {	
				currentDir = Direction.SOUTHWEST;
			}
			else if (currentDir == Direction.NORTHEAST) {
				currentDir = Direction.SOUTHEAST;
			}
		}
		
		if (currentDir == Direction.SOUTHEAST) {
			//System.out.println("SE");
			x += speed;
			y += Math.PI * Math.toRadians(angle);
		}
		if (currentDir == Direction.SOUTHWEST) {
			//System.out.println("SW");
			x -= speed;
			y += Math.PI * Math.toRadians(angle);
		}

		if (currentDir == Direction.NORTHEAST) {
			//System.out.println("NE");
			x += speed;
			y -= Math.PI * Math.toRadians(angle);
		}
		if (currentDir == Direction.NORTHWEST) {
			//System.out.println("NW");
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

}
