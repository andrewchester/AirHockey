package main;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
/*
 * Main class for AirHockey, contains Frame, keyListener, mouseListener, and the update thread
 * 
 */
public class AirHockey implements Runnable{
	
	//Objects
	private JFrame frame;
	private GamePanel panel;
	private Player player;
	
	//Game Variables
	int gameState = 0; //0 = main menu, 1 = difficulty menu, 2 = Game over/Score, 3 = game, 4 = paused
	private int difficulty = 0; //0 = easy, 1 = hard
	private final int WIDTH = 496;
	private final int HEIGHT = 759;
	private final int FPS = 60;
	private int mx = 0, my = 0;
	//Thread
	private Thread updateThread;
	private boolean running = false;
	
	//Cursor
	Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
	
	AirHockey(){
		frame = new JFrame("Air Hockey");
		player = new Player(this, 200, 500, 40);
		panel = new GamePanel(this, WIDTH, HEIGHT);
		
		updateThread = new Thread(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		//Constantly gets the mouse x position and mouse y position
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
					mx = e.getX();
					my = e.getY();
					
					if(gameState == 0 || gameState == 4) {
						for(MenuButton b : panel.getButtons())
							if(b.getBounds().contains(mx - 5, my - 20)) 
								b.setHovered(true);
							else 
								b.setHovered(false);
					}
			}
		});
		//Finds out which button was pressed and passes it to the panel
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(gameState == 0) {
					for(MenuButton b : panel.getMenuButtons())
						if(b.getBounds().contains(mx - 5, my - 20))
							panel.clicked(b);
				}else if(gameState == 4) {
					for(MenuButton b : panel.getPauseButtons())
						if(b.getBounds().contains(mx - 5, my - 20))
							panel.clicked(b);
				}
			}
		});
		//Passes the keyEvent to the panel
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				panel.keyPressed(e);
			}
		});
		
		running = true;
		updateThread.start();//Starting the update thread
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	//The update thread, runs the update() method in panel every 16 milliseconds or so. The update() method also repaints the panel, drawing the objects to the screen
	public void run() {
		while(running) { 
			panel.update();
			try {
				updateThread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new AirHockey();
	}
	
	public Player getPlayer() {
		return player;
	}
	public int getGameState() {
		return gameState;
	}
	public int getMX() {
		return mx;
	}
	public int getMY() {
		return my;
	}
	public int getWidth() {
		return WIDTH;
	}
	public int getHeight() {
		return HEIGHT;
	}
	public GamePanel getPanel() {
		return panel;
	}
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public JFrame getFrame() {
		return frame;
	}
	//Hides the cursor
	public void hideCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.getContentPane().setCursor(blankCursor);
	}
	//Shows the cursor
	public void showCursor() {
		frame.getContentPane().setCursor(Cursor.getDefaultCursor());
	}
}
