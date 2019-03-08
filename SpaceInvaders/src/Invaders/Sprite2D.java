/**
 * 
 */
package Invaders;

import java.awt.Graphics;
import java.awt.Image;

/**
 * @author killian
 * @version 13/02/2019
 */
public class Sprite2D {

	// Member data
	private int framesDrawn = 0;
	protected double x,y;
	protected Image image1;
	protected Image image2;
	
	// Static members data
	protected static int winWidth;
	
	/**
	 * Class Constructor
	 */
	public Sprite2D(Image i, Image i2) {
		
		this.image1 = i;
		this.image2 = i2;
		
	}// end constructor

	public void setPosition(double xx, double yy) {
		x = xx;
		y = yy;
	}
	
	public void paint(Graphics g) {
		framesDrawn++;
		if(framesDrawn>1000)
			framesDrawn = 0;
		if(framesDrawn%100<50) {
			g.drawImage(image1, (int)x, (int)y, null);
		}
		else {
			g.drawImage(image2, (int)x, (int)y, null);
		}
	}
	
	public static void setWinWidth(int w) {
		winWidth = w;
	}
	
}// end class Sprite2D
