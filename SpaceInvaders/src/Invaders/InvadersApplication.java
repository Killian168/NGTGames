package Invaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author killian
 * @version 13/02/2019
 */
public class InvadersApplication extends JFrame implements Runnable, KeyListener {

	// Members data
	private static String workingDirectory;
	private int currentScore = 0;
	private int bestScore = 0;
	private static boolean isInitialized = false;
	private static final Dimension WindowSize = new Dimension(800, 600);
	private BufferStrategy strategy;
	private Graphics offScreenGraphics;
	private static final int NUMALIENS = 30;
	private Alien[] AliensArray = new Alien[NUMALIENS];
	private Spaceship PlayerShip;
	private ArrayList<Sprite2D> bulletArray = new ArrayList<Sprite2D>();
	private boolean isGaming = false;
	private boolean gameOver = false;
	private int RoundNum = 0;
	private Menu gameOverScreen;
	private Menu homeScreen;

	/**
	 * Constructor for application class
	 */
	public InvadersApplication() {

		// Display the window, centered on the screen
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width / 2 - WindowSize.width / 2;
		int y = screensize.height / 2 - WindowSize.height / 2;
		setBounds(x, y, WindowSize.width, WindowSize.height);
		setVisible(true);
		this.setTitle("Space Invaders....Kinda");

		//DisplayMenu();
		initMenu();
		
		// Create and start a new thread
		Thread t = new Thread(this);
		t.start();

		// Send keyboard events arriving into this JFrame back to its own event handlers
		addKeyListener(this);

		// Initialize double-buffering
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		offScreenGraphics = strategy.getDrawGraphics();

		isInitialized = true;

	}// end constructor
	
	/**
	 * Initializes menu to display to user
	 */
	private void initMenu() {
		System.out.println("Initializing menu");
		ImageIcon icon = new ImageIcon(workingDirectory + "//enterToPlay.png");
		Image enterToPlay = icon.getImage();
		icon = new ImageIcon(workingDirectory + "//titlePage.png");
		Image homePage = icon.getImage();
		homeScreen = new Menu(homePage, enterToPlay);
		homeScreen.setPosition(((WindowSize.width/2)-(homePage.getWidth(null)/2)), 60);
	}
	
	/**
	 * Displays Game Over
	 */
	private void initGameOver() {
		ImageIcon icon = new ImageIcon(workingDirectory + "//gameOver.png");
		Image gameOverImage = icon.getImage();
		gameOverScreen = new Menu(gameOverImage,gameOverImage);
		gameOverScreen.setPosition(((WindowSize.width/2)-(gameOverImage.getWidth(null)/2)), 60);
		if(currentScore>bestScore) {
			bestScore = currentScore;
		}
	}
	
	/**
	 * Initialize game
	 */
	private void initGame() {
		// Load image from disk
		ImageIcon icon = new ImageIcon(workingDirectory + "//alien_ship_1.png");
		Image alienImage = icon.getImage();
		icon = new ImageIcon(workingDirectory + "//alien_ship_2.png");
		Image alienImage2 = icon.getImage();

		// Create and initialize some aliens, passing them each the image we have loaded
		for (int i = 0; i < NUMALIENS; i++) {

			AliensArray[i] = new Alien(alienImage, alienImage2);
			double xx = (i % 5) * 80 + 70;
			double yy = (i / 5) * 40 + 50;
			AliensArray[i].setPosition(xx, yy);

		} // end for
		Alien.setFleetXSpeed(2);

		// Create and initialize the players spaceship
		icon = new ImageIcon(workingDirectory + "//player_ship.png");
		Image shipImage = icon.getImage();
		PlayerShip = new Spaceship(shipImage);
		double xx = WindowSize.width / 2;
		double yy = WindowSize.height - shipImage.getHeight(null) - 20;
		PlayerShip.setPosition(xx, yy);

		// Tell all sprites the window width
		Sprite2D.setWinWidth(WindowSize.width);
		currentScore=0;
		
	}
	
	/**
	 * Creates a new Wave
	 */
	private void newWave() {
		//RoundNum += 1;
		for (int i = 0; i < NUMALIENS; i++) {
			AliensArray[i].setIsAlive(true);
			double xx = (i % 5) * 80 + 70;
			double yy = (i / 5) * 40 + 50;
			AliensArray[i].setPosition(xx, yy);
		} // end for
		Alien.setFleetXSpeed(Alien.getFleetXSpeed()*2);
	}
	
	/**
	 * Entry point for application thread
	 */
	@Override
	public void run() {

		while (true) {

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (isGaming) {
				
				// Display Menu or Game Over
				if(gameOver) {
					System.out.println("Game Over");
					isGaming=false;
					initGameOver();
				}
				
				// Iterate Game
				boolean alienDirectionChangeNeeded = false;
				for (int i = 0; i < NUMALIENS; i++) {
					if (AliensArray[i].move()) {
						alienDirectionChangeNeeded = true;
					} // end if
				} // end for

				if (alienDirectionChangeNeeded) {
					Alien.reverseDirection();
					for (int i = 0; i < NUMALIENS; i++) {
						AliensArray[i].jumpDown();
					}
				}

				Iterator<Sprite2D> iterator = bulletArray.iterator();
				boolean collided = false;
				boolean playerCollided = false;
				while (iterator.hasNext()) {
					Bullet b = (Bullet) iterator.next();
					b.move();
					collided = false;
					for (int i = 0; i < NUMALIENS; i++) {
						collided = AliensArray[i].checkCollision(AliensArray[i], b);
						if (collided == true) {
							iterator.remove();
							currentScore += 20;
						}
					}
				}

				for (int i = 0; i < NUMALIENS; i++) {
					playerCollided = AliensArray[i].checkCollision(AliensArray[i], PlayerShip);
					if(playerCollided == true) {
						System.out.println("GameOver");
						gameOver=true;
					}
				}
				
				int deadAliens = 0;
				for(Alien a: AliensArray) {
					if(!a.isAlive) {
						deadAliens++;
						if(deadAliens==NUMALIENS) {
							newWave();
							try {
								Thread.sleep(20);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				PlayerShip.move();
			}

			this.repaint();
			
		} // end while

	}// end run()

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			PlayerShip.setXSpeed(-4);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			PlayerShip.setXSpeed(4);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (bulletArray.size() < 10) {
				ImageIcon icon = new ImageIcon(workingDirectory + "//bullet.png");
				Image i = icon.getImage();
				Bullet b = new Bullet(i);
				b.setPosition(PlayerShip.x + (PlayerShip.image1.getWidth(null) / 2) - 3, PlayerShip.y);
				bulletArray.add(b);
			}
		}
		if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (isGaming == false)) {
			initGame();
			isGaming = true;
			gameOver = false;
		}
	}// end keyPressed()

	@Override
	public void keyReleased(KeyEvent e) {

		if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			PlayerShip.setXSpeed(0);
		}

	}// end keyReleased()

	public void paint(Graphics g) {

		if (!isInitialized) {
			return;
		}

		g = offScreenGraphics;

		// Clear the canvas with a big black rectangle
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WindowSize.width, WindowSize.height);
		g.setColor(new Color(255, 255, 255));
		g.drawString("Current Score: "+ currentScore,WindowSize.width/2-150,50);
		g.drawString("Best Score: "+ currentScore,WindowSize.width/2+50,50);
		
		if(isGaming) {
			// Redraw all game objects
			for (int i = 0; i < NUMALIENS; i++) {
				AliensArray[i].paint(g);
			}

			Iterator<Sprite2D> iterator = bulletArray.iterator();
			while (iterator.hasNext()) {
				Bullet b = (Bullet) iterator.next();
				if (b.y <= 0) {
					iterator.remove();
				}
				b.paint(g);
			}

			PlayerShip.paint(g);
		}
		else {
			if(gameOver) {
				gameOverScreen.paint(g);
			}
			else {
				homeScreen.paint(g);
			}
		}

		// Flip the buffers offScreen <--> onScreen
		strategy.show();

	}// end paint()

	/**
	 * @param args
	 *            Entry point for application
	 */
	public static void main(String[] args) {

		workingDirectory = System.getProperty("user.dir");
		System.out.println(workingDirectory);
		InvadersApplication a = new InvadersApplication();

	}// end main()

}// end class InvadersApplication
