import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 * @version 4/March/2018
 * @author Killian O'Dálaigh
 * Description: 
 */

/**
 * Main class for the game
 */
public class Main extends JFrame implements Runnable, MouseListener {

	// member data
	private static final Dimension WindowSize = new Dimension(800, 800);
	private final int BOXESSIZE = WindowSize.width / 20;
	private boolean isInitialized = false;
	private BufferStrategy strategy;
	private Graphics offScreenGraphics;
	private boolean mouseEntered = false;
	GridBox[][] GridBoxes = new GridBox[BOXESSIZE][BOXESSIZE];

	public Main() {

		// Created 40x40 2D Array, i = Row, j = Col
		for (int i = 0; i < BOXESSIZE; i++) {
			for (int j = 0; j < BOXESSIZE; j++) {
				GridBoxes[i][j] = new GridBox(j*20, i*20, new Color(255,255,255));
			}
		}
		
		// Display the window, centered on the screen
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width / 2 - WindowSize.width / 2;
		int y = screensize.height / 2 - WindowSize.height / 2;
		setBounds(x, y, WindowSize.width, WindowSize.height);
		setVisible(true);
		System.out.print("Display screen");
		this.setTitle("Game of Life");

		// Create and start a new thread
		Thread t = new Thread(this);
		t.start();

		// Send keyboard events arriving into this JFrame back to its own event handlers
		addMouseListener(this);

		// Initialize double-buffering
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		offScreenGraphics = strategy.getDrawGraphics();

		isInitialized = true;

	}// end main

	public void paint(Graphics g) {

		g = offScreenGraphics;

		// Clear the canvas with a big black rectangle
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WindowSize.width, WindowSize.height);

		// Draw rectangles
		for (int i = 0; i < BOXESSIZE; i++) {
			for (int j = 0; j < BOXESSIZE; j++) {
				// System.out.println(BoxesGrid.get(i).get(j).paint(g));
				GridBoxes[i][j].paint(g);
			}
		}

		// Flip the buffers offScreen <--> onScreen
		strategy.show();
	}

	// Entry point for application
	public static void main(String[] args) {
		Main m = new Main();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		boolean clicked = false;
		int x = e.getX();
		int y = e.getY();
		System.out.println("\nMouse Clicked");
		
		
		if(x<400 && y<400) {
			// Q1
			for(int i=0; i<BOXESSIZE/2; i++) {
				for(int j=0; j<BOXESSIZE/2; j++) {
					GridBoxes[i][j].checkClick(x, y);
				}
			}
		}
		else if(x>400 && y<400) {
			// Q2
			for(int i=0; i<BOXESSIZE/2; i++) {
				for(int j=20; j<BOXESSIZE; j++) {
					GridBoxes[i][j].checkClick(x, y);
				}
			}
		}
		else if(x<400 && y>400) {
			// Q3
			for(int i=20; i<BOXESSIZE; i++) {
				for(int j=0; j<BOXESSIZE/2; j++) {
					GridBoxes[i][j].checkClick(x, y);
				}
			}
		}
		else if(x>400 && y> 400) {
			// Q4
			for(int i=20; i<BOXESSIZE; i++) {
				for(int j=20; j<BOXESSIZE; j++) {
					GridBoxes[i][j].checkClick(x, y);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("\nMouse Pressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("\nMouse Released");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("\nMouse Entered");
		mouseEntered = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("\nMouse Exited");
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}

}
