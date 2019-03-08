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
public class Alien extends Sprite2D {

	// Member data
	private static int XSpeed = 0;
	public boolean isAlive = true;
	
	public Alien(Image i, Image i2) {
		super(i, i2);
	}
	
	public void paint(Graphics g) {
		if(isAlive) {
			super.paint(g);
		}
		else {
			
		}
	}
	
	// public interface
	public boolean move() {
		
		x+=XSpeed;
		
		// Direction reversal needed
		if(isAlive) {
			return ((x<=0)||(x>=winWidth-image1.getWidth(null)));
		}
		else {
			return false;
		}
	}
	
	public boolean checkCollision(Alien a, Bullet b) {
		if(
				((a.x<b.x && a.x+a.image1.getWidth(null)>b.x)||(b.x<a.x && b.x+b.image1.getWidth(null)>a.x))
				&&
				((a.y<b.y && a.y+a.image1.getHeight(null)>b.y)||(b.y<a.y && b.y+b.image1.getHeight(null)>a.y))
				&&
				(isAlive)
			) {
			isAlive = false;
			return true;
		}
		return false;
	}
	
	public static void setFleetXSpeed(int dx) {
		XSpeed = dx;
	}
	
	public static int getFleetXSpeed() {
		return XSpeed;
	}
	
	public static void reverseDirection() {
		XSpeed *= -1;
	}
	
	public void jumpDown() {
		y+=20;
	}

	public void setIsAlive(boolean t) {
		this.isAlive = t;
	}

	public boolean checkCollision(Alien a, Spaceship playerShip) {
		if(
				((a.x<playerShip.x && a.x+a.image1.getWidth(null)>playerShip.x)||(playerShip.x<a.x && playerShip.x+playerShip.image1.getWidth(null)>a.x))
				&&
				((a.y<playerShip.y && a.y+a.image1.getHeight(null)>playerShip.y)||(playerShip.y<a.y && playerShip.y+playerShip.image1.getHeight(null)>a.y))
				&&
				(isAlive)
			) {
			return true;
		}
		return false;
	}
}
