package main;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class AirHockey {
	
	//Objects
	private JFrame frame;
	private GamePanel panel;
	private Player player;
	
	//System Variables
	int gameState = 0; //0 = main menu, 1 = difficulty menu, 2 = Game over/Score, 3 = game
	private final int WIDTH = 496;
	private final int HEIGHT = 759;
	private final int FPS = 60;
	private int mx = 0, my = 0;
	
	//Thread
	private Thread updateThread;
	private boolean running = false;
	
	AirHockey(){
		frame = new JFrame("Air Hockey");
		player = new Player(this, 200, 500, 40);
		panel = new GamePanel(this, WIDTH, HEIGHT);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
				
				for(MenuButton b : panel.getButtons())
					if(b.getBounds().contains(mx - 5, my - 20)) 
						b.setHovered(true);
					else
						b.setHovered(false);
			}
		});
		
		updateThread = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
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
			
		});
		running = true;
		updateThread.start();
		
		frame.add(panel);
		frame.setVisible(true);
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
}
