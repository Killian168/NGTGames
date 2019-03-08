import java.awt.Color;
import java.awt.Graphics;

public class GridBox {
	private int x;
	public int y;
	private Color c;
	private boolean alive;

	public GridBox(int x, int y, Color c) {
		//System.out.println(y);
		this.x = x;
		this.y = y;
		this.c = c;
		this.alive = false;
	}

	public void checkClick(int ClickX, int ClickY) {
		if ((ClickX < this.x + 20) && (ClickX > this.x) && (ClickY < this.y + 20) && (ClickY > this.y)) {
			alive = true;
		}
	}
	
	public void paint(Graphics g) {
		if(alive==true) {
			g.setColor(this.c);
			g.fillRect(this.x, this.y, 20, 20);
		}
	}
	
}
