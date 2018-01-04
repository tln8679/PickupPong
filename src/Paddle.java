
import java.awt.Color;
import java.awt.Graphics;


/**
 * 
 * @author tln86
 *
 */
public class Paddle {
	private int startX, startY, width, height;
	private Color team;
	public Paddle(int startX, int startY, Color team) {
		this.startX = startX;
		this.startY = startY;
		this.team = team;
		width = 30;
		height = 75;
	}
	public void draw(Graphics g) {
		g.setColor(team);
		g.fillRect(startX, startY, width, height);
	}
	
	public void changePos(int dy) {
		this.startY = startY+dy;
		
	}
	
	public void setPos(int y) {
		this.startY = y;
		
	}


	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	}

