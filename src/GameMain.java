import javax.swing.JFrame;
import javax.swing.JTextField;

public class GameMain {

	private GamePanel gp;
	private JFrame f = new JFrame();
	
	public static void main(String[] args) {
		new GameMain();
	}
	
	public GameMain() {
		f.setTitle("Circuit#1");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gp = new GamePanel();
		f.add(gp);
		f.pack();
//		f.setResizable(false);
		f.setVisible(true);
		
		gp.startAnimation();
	}
}
