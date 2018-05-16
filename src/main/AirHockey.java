package main;

import javax.swing.JFrame;

public class AirHockey {
	
	private JFrame frame;
	
	AirHockey(){
		frame = new JFrame("Air Hockey");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(500, 800);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new AirHockey();
	}
}
