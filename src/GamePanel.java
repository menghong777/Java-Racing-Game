import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements KeyListener, ActionListener {
	
	protected ImageIcon playerCar[];		// array of player car images
	protected ImageIcon opponentCar[];		// array of opponent car images
	private String file_name = "";			// initialise file name
	private String file_type = ".png";		// initialise file type
	int player_x = 450;						// initial player position
	int player_y = 500;						// initial player position
	int opponent_x = 450;					// initial opponent position
	int opponent_y = 550;					// initial opponent position
	int player_velX = 0;					// velocity of player
	int player_velY = 0;					// velocity of player
	int opponent_velX = 0;					// velocity of opponent
	int opponent_velY = 0;					// velocity of opponent
	int p_currentImage = 0;					// current image index
	private int o_currentImage = 0;			// current image index
	private final int ANIMATION_DELAY = 50;	// millisecond delay
	private int width = 850;				// image width
	private int height = 650;				// image height
	private Timer animationTimer;			// Timer
	
	public GamePanel() {
		loadPlayerCar();
		loadOpponentCar();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public void loadPlayerCar() {
		file_name = "images/car_red_";
		file_type = ".png";
//		String audio = "sounds/.wav";
		
		try {
			playerCar = new ImageIcon[16];
			
			for (int count = 0; count < 16; count++) {
				playerCar[count] = new ImageIcon(file_name + count + file_type);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadOpponentCar() {
		file_name = "images/car_blue_";
		file_type = ".png";
		
		try {
			opponentCar = new ImageIcon[16];
			
			for (int count = 0; count < 16; count++) {
				opponentCar[count] = new ImageIcon(file_name + count + file_type);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Race track
		Color c1 = Color.black;
		g.setColor(c1);
		g.fillRect(50, 100, 750, 500);	// track (black)
		g.drawRect(50, 100, 750, 500);	// outer edge
		
		Color c2 = Color.green;
		g.setColor(c2);
		g.fillRect(150, 200, 550, 300); // grass
		
		Color c3 = Color.yellow;
		g.setColor(c3);
		g.drawRect(100, 150, 650, 400);	// mid-line marker
		
		Color c4 = Color.white;
		g.setColor(c4);
		g.drawLine(425, 500, 425, 600);	// start line
		
		// player's car
		playerCar[p_currentImage].paintIcon(this, g, player_x, player_y);
//		System.out.println("Current X: " + player_x + ", Current Y: " + player_y );
		
		// opponent's car
		opponentCar[o_currentImage].paintIcon(this, g, opponent_x, opponent_y);

	}
	
	public void startAnimation() {
		if (animationTimer == null) {
			
			p_currentImage = 12;	// display first image
			o_currentImage = 12;	// display first image
			
			// create timer
			animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler());
			
			animationTimer.start();		// start timer
		} else {	// animationTimer already exists, restart animation
			if (!animationTimer.isRunning())
				animationTimer.restart();
		} // end else
	} // end method startAnimation
	
	public void stopAnimation() {
		animationTimer.stop();
	} // end method stopAnimation
	
	// return preferred size of animation
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	} // end method getPreferredSize
	
	// inner class to handle action events from Timer
	private class TimerHandler implements ActionListener {
		// respond to Timer's event
		public void actionPerformed(ActionEvent e) {
			outOfTrackCtrl("player");
			outOfTrackCtrl("opponent");
			player_x = player_x + player_velX;
			player_y = player_y + player_velY;
			opponent_x = opponent_x + opponent_velX;
			opponent_y = opponent_y + opponent_velY;
			repaint(); // repaint animator
		} // end actionPerformed
	} // end class TimerHandler

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_LEFT) {
			// Turn left
			turn_left("player", p_currentImage);
		}
		if (key == e.VK_UP) {
			// Accelerate
			acceleration("player", p_currentImage);
		}
		if (key == e.VK_RIGHT) {
			// Turn right
			turn_right("player", p_currentImage);
		}
		if (key == e.VK_DOWN) {
			// Reverse
			reverse("player", p_currentImage);
		}
		if (key == e.VK_A) {
			turn_left("opponent", o_currentImage);
		}
		if (key == e.VK_W) {
			acceleration("opponent", o_currentImage);
		}
		if (key == e.VK_D) {
			turn_right("opponent", o_currentImage);
		}
		if (key == e.VK_S) {
			reverse("opponent", o_currentImage);
		}
	}

	private void turn_left(String player_type, int left) {
		if (player_type == "player") {
			if (left == 0){
				p_currentImage = 15;
			} else {
				p_currentImage--;
			}
		}
		if (player_type == "opponent") {
			if (left == 0){
				o_currentImage = 15;
			} else {
				o_currentImage--;
			}
		}
	}

	private void turn_right(String player_type, int right) {
		if (player_type == "player") {
			if (right == 15){
				p_currentImage = 0;
			} else {
				p_currentImage++;
			}
		}
		if (player_type == "opponent") {
			if (right == 15){
				o_currentImage = 0;
			} else {
				o_currentImage++;
			}
		}
		
	}

	private void reverse(String player_type, int direction_of_car) {
		if (player_type == "player") {
			switch (direction_of_car){
			case 0:
				// UP
				player_velX = 0;
				player_velY = 10;
				break;
			case 1:
				player_velX = -3;
				player_velY = 7;
				break;
			case 2:
				player_velX = -5;
				player_velY = 5;
				break;
			case 3:
				player_velX = -7;
				player_velY = 3;
				break;
			case 4:
				// RIGHT
				player_velX = -10;
				player_velY = 0;
				break;
			case 5:
				player_velX = -7;
				player_velY = -3;
				break;
			case 6:
				player_velX = -5;
				player_velY = -5;
				break;
			case 7:
				player_velX = -3;
				player_velY = -7;
				break;
			case 8:
				// DOWN
				player_velX = 0;
				player_velY = -10;
				break;
			case 9:
				player_velX = 3;
				player_velY = -7;
				break;
			case 10:
				player_velX = 5;
				player_velY = -5;
				break;
			case 11:
				player_velX = 7;
				player_velY = -3;
				break;
			case 12:
				// LEFT
				player_velX = 10;
				player_velY = 0;
				break;
			case 13:
				player_velX = 7;
				player_velY = 3;
				break;
			case 14:
				player_velX = 5;
				player_velY = 5;
				break;
			case 15:
				player_velX = 3;
				player_velY = 7;
				break;
			default:
				break;
			}
		}
		if (player_type == "opponent") {
			switch (direction_of_car){
			case 0:
				// UP
				opponent_velX = 0;
				opponent_velY = 10;
				break;
			case 1:
				opponent_velX = -3;
				opponent_velY = 7;
				break;
			case 2:
				opponent_velX = -5;
				opponent_velY = 5;
				break;
			case 3:
				opponent_velX = -7;
				opponent_velY = 3;
				break;
			case 4:
				// RIGHT
				opponent_velX = -10;
				opponent_velY = 0;
				break;
			case 5:
				opponent_velX = -7;
				opponent_velY = -3;
				break;
			case 6:
				opponent_velX = -5;
				opponent_velY = -5;
				break;
			case 7:
				opponent_velX = -3;
				opponent_velY = -7;
				break;
			case 8:
				// DOWN
				opponent_velX = 0;
				opponent_velY = -10;
				break;
			case 9:
				opponent_velX = 3;
				opponent_velY = -7;
				break;
			case 10:
				opponent_velX = 5;
				opponent_velY = -5;
				break;
			case 11:
				opponent_velX = 7;
				opponent_velY = -3;
				break;
			case 12:
				// LEFT
				opponent_velX = 10;
				opponent_velY = 0;
				break;
			case 13:
				opponent_velX = 7;
				opponent_velY = 3;
				break;
			case 14:
				opponent_velX = 5;
				opponent_velY = 5;
				break;
			case 15:
				opponent_velX = 3;
				opponent_velY = 7;
				break;
			default:
				break;
			}
		}
		
	}

	private void acceleration(String player_type, int direction_of_car) {
		if (player_type == "player") {
			switch (direction_of_car){
			case 0:
				// UP
				player_velX = 0;
				player_velY = -10;
				break;
			case 1:
				player_velX = 3;
				player_velY = -7;
				break;
			case 2:
				player_velX = 5;
				player_velY = -5;
				break;
			case 3:
				player_velX = 7;
				player_velY = -3;
				break;
			case 4:
				// RIGHT
				player_velX = 10;
				player_velY = 0;
				break;
			case 5:
				player_velX = 7;
				player_velY = 3;
				break;
			case 6:
				player_velX = 5;
				player_velY = 5;
				break;
			case 7:
				player_velX = 3;
				player_velY = 7;
				break;
			case 8:
				// DOWN
				player_velX = 0;
				player_velY = 10;
				break;
			case 9:
				player_velX = -3;
				player_velY = 7;
				break;
			case 10:
				player_velX = -5;
				player_velY = 5;
				break;
			case 11:
				player_velX = -7;
				player_velY = 3;
				break;
			case 12:
				// LEFT
				player_velX = -10;
				player_velY = 0;
				break;
			case 13:
				player_velX = -7;
				player_velY = -3;
				break;
			case 14:
				player_velX = -5;
				player_velY = -5;
				break;
			case 15:
				player_velX = -3;
				player_velY = -7;
				break;
			default:
				break;
			}
		}
		if (player_type == "opponent") {
			switch (direction_of_car){
			case 0:
				// UP
				opponent_velX = 0;
				opponent_velY = -10;
				break;
			case 1:
				opponent_velX = 3;
				opponent_velY = -7;
				break;
			case 2:
				opponent_velX = 5;
				opponent_velY = -5;
				break;
			case 3:
				opponent_velX = 7;
				opponent_velY = -3;
				break;
			case 4:
				// RIGHT
				opponent_velX = 10;
				opponent_velY = 0;
				break;
			case 5:
				opponent_velX = 7;
				opponent_velY = 3;
				break;
			case 6:
				opponent_velX = 5;
				opponent_velY = 5;
				break;
			case 7:
				opponent_velX = 3;
				opponent_velY = 7;
				break;
			case 8:
				// DOWN
				opponent_velX = 0;
				opponent_velY = 10;
				break;
			case 9:
				opponent_velX = -3;
				opponent_velY = 7;
				break;
			case 10:
				opponent_velX = -5;
				opponent_velY = 5;
				break;
			case 11:
				opponent_velX = -7;
				opponent_velY = 3;
				break;
			case 12:
				// LEFT
				opponent_velX = -10;
				opponent_velY = 0;
				break;
			case 13:
				opponent_velX = -7;
				opponent_velY = -3;
				break;
			case 14:
				opponent_velX = -5;
				opponent_velY = -5;
				break;
			case 15:
				opponent_velX = -3;
				opponent_velY = -7;
				break;
			default:
				break;
			}
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_LEFT) {
//			player_velX = 0;
//			player_velY = 0;
			auto_accelerate("player", p_currentImage);
		}
		if (key == e.VK_UP) {
//			player_velX = 0;
//			player_velY = 0;
			auto_accelerate("player", p_currentImage);
		}
		if (key == e.VK_RIGHT) {
//			player_velX = 0;
//			player_velY = 0;
			auto_accelerate("player", p_currentImage);
		}
		if (key == e.VK_DOWN) {
			player_velX = 0;
			player_velY = 0;
		}
		if (key == e.VK_A) {
//			opponent_velX = 0;
//			opponent_velY = 0;
			auto_accelerate("opponent", o_currentImage);
		}
		if (key == e.VK_W) {
//			opponent_velX = 0;
//			opponent_velY = 0;
			auto_accelerate("opponent", o_currentImage);
		}
		if (key == e.VK_D) {
//			opponent_velX = 0;
//			opponent_velY = 0;
			auto_accelerate("opponent", o_currentImage);
		}
		if (key == e.VK_S) {
			opponent_velX = 0;
			opponent_velY = 0;
		}
	}

	private void auto_accelerate(String player_type, int direction_of_car) {
		if (player_type == "player") {
			switch (direction_of_car){
			case 0:
				// UP
				player_velX = 0;
				player_velY = -10;
				break;
			case 1:
				player_velX = 3;
				player_velY = -7;
				break;
			case 2:
				player_velX = 5;
				player_velY = -5;
				break;
			case 3:
				player_velX = 7;
				player_velY = -3;
				break;
			case 4:
				// RIGHT
				player_velX = 10;
				player_velY = 0;
				break;
			case 5:
				player_velX = 7;
				player_velY = 3;
				break;
			case 6:
				player_velX = 5;
				player_velY = 5;
				break;
			case 7:
				player_velX = 3;
				player_velY = 7;
				break;
			case 8:
				// DOWN
				player_velX = 0;
				player_velY = 10;
				break;
			case 9:
				player_velX = -3;
				player_velY = 7;
				break;
			case 10:
				player_velX = -5;
				player_velY = 5;
				break;
			case 11:
				player_velX = -7;
				player_velY = 3;
				break;
			case 12:
				// LEFT
				player_velX = -10;
				player_velY = 0;
				break;
			case 13:
				player_velX = -7;
				player_velY = -3;
				break;
			case 14:
				player_velX = -5;
				player_velY = -5;
				break;
			case 15:
				player_velX = -3;
				player_velY = -7;
				break;
			default:
				break;
			}
		}
		if (player_type == "opponent") {
			switch (direction_of_car){
			case 0:
				// UP
				opponent_velX = 0;
				opponent_velY = -10;
				break;
			case 1:
				opponent_velX = 3;
				opponent_velY = -7;
				break;
			case 2:
				opponent_velX = 5;
				opponent_velY = -5;
				break;
			case 3:
				opponent_velX = 7;
				opponent_velY = -3;
				break;
			case 4:
				// RIGHT
				opponent_velX = 10;
				opponent_velY = 0;
				break;
			case 5:
				opponent_velX = 7;
				opponent_velY = 3;
				break;
			case 6:
				opponent_velX = 5;
				opponent_velY = 5;
				break;
			case 7:
				opponent_velX = 3;
				opponent_velY = 7;
				break;
			case 8:
				// DOWN
				opponent_velX = 0;
				opponent_velY = 10;
				break;
			case 9:
				opponent_velX = -3;
				opponent_velY = 7;
				break;
			case 10:
				opponent_velX = -5;
				opponent_velY = 5;
				break;
			case 11:
				opponent_velX = -7;
				opponent_velY = 3;
				break;
			case 12:
				// LEFT
				opponent_velX = -10;
				opponent_velY = 0;
				break;
			case 13:
				opponent_velX = -7;
				opponent_velY = -3;
				break;
			case 14:
				opponent_velX = -5;
				opponent_velY = -5;
				break;
			case 15:
				opponent_velX = -3;
				opponent_velY = -7;
				break;
			default:
				break;
			}
		}
	}
	
	private void outOfTrackCtrl(String player_type) {
		// hitting track wall
		if (player_type == "player") {
			if (player_x >= 760){
				player_velX = 0;
				player_velY = 0;
				player_x = player_x - 1;
			}
			if (player_x <= 40){
				player_velX = 0;
				player_velY = 0;
				player_x = player_x + 1;
			}
			if (player_y >= 560){
				player_velX = 0;
				player_velY = 0;
				player_y = player_y - 1;
			}
			if (player_y <= 90){
				player_velX = 0;
				player_velY = 0;
				player_y = player_y + 1;
			}
			// left grass boundary
			if (player_y >= 200 && player_y <= 450) {
				if (player_x >= 50 && player_x <= 110) {
					if (player_x >= 100) {
						player_velX = 0;
						player_velY = 0;
						player_x = player_x - 1;
					}
				}
			}
			// right grass boundary
			if (player_y >= 200 && player_y <= 450) {
				if (player_x >= 650 && player_x <= 700) {
					if (player_x <= 700){
						player_velX = 0;
						player_velY = 0;
						player_x = player_x + 1;
					}
				}
			}
			// bottom grass boundary
			if (player_x >= 150 && player_x <= 650) {
				if (player_y >= 450 && player_y <= 550) {
					if (player_y <= 500){
						player_velX = 0;
						player_velY = 0;
						player_y = player_y + 1;
					}
				}
			}
			// top grass boundary
			if (player_x >= 150 && player_x <= 650) {
				if (player_y >= 100 && player_y <= 200) {
					if (player_y >= 150){
						player_velX = 0;
						player_velY = 0;
						player_y = player_y - 1;
					}
				}
			}
		}
		if (player_type == "opponent") {
			if (opponent_x >= 750){
				opponent_velX = 0;
				opponent_velY = 0;
				opponent_x = opponent_x - 1;
			}
			if (opponent_x <= 50){
				opponent_velX = 0;
				opponent_velY = 0;
				opponent_x = opponent_x + 1;
			}
			if (opponent_y >= 550){
				opponent_velX = 0;
				opponent_velY = 0;
				opponent_y = opponent_y - 1;
			}
			if (opponent_y <= 100){
				opponent_velX = 0;
				opponent_velY = 0;
				opponent_y = opponent_y + 1;
			}
			// left grass boundary
			if (opponent_y >= 200 && opponent_y <= 450) {
				if (opponent_x >= 50 && opponent_x <= 110) {
					if (opponent_x >= 100) {
						opponent_velX = 0;
						opponent_velY = 0;
						opponent_x = opponent_x - 1;
					}
				}
			}
			// right grass boundary
			if (opponent_y >= 200 && opponent_y <= 450) {
				if (opponent_x >= 650 && opponent_x <= 700) {
					if (opponent_x <= 700){
						opponent_velX = 0;
						opponent_velY = 0;
						opponent_x = opponent_x + 1;
					}
				}
			}
			// bottom grass boundary
			if (opponent_x >= 150 && opponent_x <= 650) {
				if (opponent_y >= 450 && opponent_y <= 550) {
					if (opponent_y <= 500){
						opponent_velX = 0;
						opponent_velY = 0;
						opponent_y = opponent_y + 1;
					}
				}
			}
			// top grass boundary
			if (opponent_x >= 150 && opponent_x <= 650) {
				if (opponent_y >= 100 && opponent_y <= 200) {
					if (opponent_y >= 150){
						opponent_velX = 0;
						opponent_velY = 0;
						opponent_y = opponent_y - 1;
					}
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
