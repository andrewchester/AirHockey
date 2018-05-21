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

public class AirHockey implements Runnable{
	
	//Objects
	private JFrame frame;
	private GamePanel panel;
	private Player player;
	private Physics p;
	
	//System Variables
	int gameState = 0; //0 = main menu, 1 = difficulty menu, 2 = Game over/Score, 3 = game, 4 = paused
	private final int WIDTH = 496;
	private final int HEIGHT = 759;
	private final int FPS = 60;
	private int mx = 0, my = 0;
	private boolean showingCursor = true;
	
	//Thread
	private Thread updateThread;
	private boolean running = false;
	
	//Cursor
	Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
	
	AirHockey(){
		frame = new JFrame("Air Hockey");
		player = new Player(this, 200, 500, 40);
		panel = new GamePanel(this, WIDTH, HEIGHT);
		p = new Physics();
		
		updateThread = new Thread(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
					mx = e.getX();
					
					my = e.getY();
					
					if(gameState == 0)
						for(MenuButton b : panel.getButtons())
							if(b.getBounds().contains(mx - 5, my - 20)) { 
								b.setHovered(true);
								frame.setCursor(Cursor.HAND_CURSOR);
							}else {
								b.setHovered(false);
								frame.setCursor(Cursor.DEFAULT_CURSOR);
							}
			}
		});
		
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(gameState == 0)
					for(MenuButton b : panel.getButtons())
						if(b.getBounds().contains(mx - 5, my - 20))
							panel.clicked(b);
			}
		});
		
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ESCAPE) {
					if(gameState == 3)
						gameState = 4;
					else if(gameState == 4)
						gameState = 3;
				}
			}
		});
		
		running = true;
		updateThread.start();
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
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
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	public Physics getPhysics() {
		return p;
	}
	public void setShowingCursor(boolean showingCursor) {
		this.showingCursor = showingCursor;
		if(!showingCursor) {
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
			frame.getContentPane().setCursor(blankCursor);
		}else {
			frame.setCursor(Cursor.getDefaultCursor());
		}
	}
}
