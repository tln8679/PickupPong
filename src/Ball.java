import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Ball {
	int x, y,width,height ;
	public Ball (int x, int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	public void draw(Graphics g, int x, int y,int width,int height) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillOval(x, y, this.width, this.height);
	}
}
