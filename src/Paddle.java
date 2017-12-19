import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Paddle {
	private int startX, startY, endX, endY;
	private Color team;
	public Paddle(int startX, int startY, int endX, int endY, Color team) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.team = team;
	}
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(35));
		g2.setColor(team);
		g.drawLine(startX, startY, endX, endY);
	}

	
	/**
	 * @param int x: the x coordinate.
	 * @param int y: the y coordinate.
	 * @return true if the point is contained in one of the rectangles edge and false otherwise.
	 */
	public boolean contains(int x, int y) {
		double distanceTo = Line2D.ptLineDist(startX, startY, endX, endY, x, y);
		if (distanceTo <= 5)
			return true;
		else
			return false;
	}
}
