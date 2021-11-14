package test.ex11;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

enum BubbleState {
	BUBBLE, BUBBLED, BOOM;
}

@Setter
@Getter
public class Bubble extends JLabel {

	private BufferedImage image;

	private int x;
	private int y;

	private boolean up;
	private boolean left;
	private boolean right;

	private ImageIcon bubble;
	private ImageIcon bubbled;
	private BubbleState bubbleState;
	
	private BubbleGame mContext;
	private ImageIcon bomb;

	public Bubble(int x, int y, BubbleGame mContext) {
		this.mContext = mContext;
		try {
			image = ImageIO.read(new File("image/backgroundService.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		initObject();
		initSetting();

	}

	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");

		bubbleState = bubbleState.BUBBLE;
	}

	private void initSetting() {
		up = false;
		left = false;
		right = false;
		setIcon(bubble);
		setSize(50, 50);
		setLocation(x, y);
	}

	public void shootRight() {
		right = true;
		new Thread(() -> {

			try {
				for (int i = 0; i < 300; i++) {

					setLocation(x, y);
					x++;

					Thread.sleep(3);
					int rightColor = image.getRGB(x + 50 + 10, y + 25);
					if (rightColor != -1) {
						break;
					}
				}

				shootUp();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}).start();

	}

	public void shootLeft() {
		left = true;
		new Thread(() -> {

			try {
				for (int i = 0; i < 300; i++) {

					setLocation(x, y);
					x--;

					Thread.sleep(3);
					int leftColor = image.getRGB(x - 10, y+ 25);
					if (leftColor != -1) {
						break;
					}
				}

				shootUp();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}).start();

	}

	public void shootUp() {
		left = false;
		right = false;
		up = true;
		while (up) {
			
			try {
				setLocation(x, y);
				y--;
				Thread.sleep(3);
				
				if(y < 30) {
					up = false;
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop();
		
	}
	
	public void stop() {
		System.out.println("스탑로직");
		try {
			Thread.sleep(3000);
			setIcon(bomb);
			Thread.sleep(500);
			mContext.remove(this);
			mContext.repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
