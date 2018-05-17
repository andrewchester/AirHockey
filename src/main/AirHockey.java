package main;

import javax.swing.JFrame;

public class AirHockey {
	
	private JFrame frame;
	private GameBoard board;
	
	int gameState = 0; //0 = main menu, 1 = difficulty menu, 
	private final int WIDTH = 500;
	private final int HEIGHT = 900;
	
	AirHockey(){
		frame = new JFrame("Air Hockey");
		board = new GameBoard(gameState, WIDTH, HEIGHT);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(500, 800);
		
		frame.add(board);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new AirHockey();
	}
}
