/**
 * 
 */
package Invaders;

import java.awt.Image;

/**
 * @author killian
 * @version 13/02/2019
 */
public class Spaceship extends Sprite2D {

	// Member Data
	private double xSpeed = 0;
	
	/**
	 * Constructor for class Spaceship
	 * @param i
	 */
	public Spaceship(Image i) {
		super(i,i);
	}// end constructor

	public void setXSpeed(int x) {
		this.xSpeed = x;
	}
	
	public void move() {
		
		// Apply current movement
		x += this.xSpeed;
		
		// Stop movement at screen edge
		if(x<=0) {
			x=0;
			xSpeed = 0;
		}
		else if (x>=winWidth-image1.getWidth(null)) {
			x = winWidth-image1.getWidth(null);
			xSpeed = 0;
		}
	}
}
