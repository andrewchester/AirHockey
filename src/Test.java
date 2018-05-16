import javax.swing.JFrame;

public class Test {
	
	Test(){
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Test();
	}
}
