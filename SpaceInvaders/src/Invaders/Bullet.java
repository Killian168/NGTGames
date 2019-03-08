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
public class Bullet extends Sprite2D {

	private int YSpeed = -2;
	
	public Bullet(Image i) {
		super(i, i);
	}
	
	public void move() {
		this.y += YSpeed;
	}

}
