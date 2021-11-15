package test.ex12;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

enum BubbleState {
	BUBBLE, BUBBLED, BOMB;
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

	public Bubble(BubbleGame mContext) {
		this.mContext = mContext;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		initObject();
		initSetting();
		initThread();

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
		
		this.x = mContext.getPlayer().getX();
		this.y = mContext.getPlayer().getY();

		setIcon(bubble);
		setSize(50, 50);
	}

	private void initThread() {
		new Thread(() -> {

			if (mContext.getPlayer().getPlayerDirection() == PlayerDirection.LEFT) {
				shootLeft();
			} else if (mContext.getPlayer().getPlayerDirection() == PlayerDirection.RIGHT) {
				shootRight();
			}

		}).start();
	}

	public void shootLeft() {
		left = true;
		for (int i = 0; i < 400; i++) {

			setLocation(x, y);
			x--;

			try {
				Thread.sleep(1);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}

			Color leftColor = new Color(image.getRGB(x - 10, y + 25));
			if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
				break;
			}
		}
		left = false;
		shootUp();

	}

	public void shootRight() {
		right = true;
		for (int i = 0; i < 400; i++) {

			setLocation(x, y);
			x++;

			try {
				Thread.sleep(1);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}

			Color rightColor = new Color(image.getRGB(x + 50 + 10, y + 25));
			if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
				break;
			}
		}
		right = false;
		shootUp();
	}

	public void shootUp() {
		up = true;
		while (up) {

			try {
				setLocation(x, y);
				y--;
				Thread.sleep(1);

				Color topColor = new Color(image.getRGB(x + 25, y - 10));
				System.out.println("topColor : " + topColor);
				if (topColor.getRed() == 255 && topColor.getBlue() == 0 && topColor.getGreen() == 0) {
					up = false;
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up = false;
		stop();

	}

	public void stop() {
		try {
			Thread.sleep(3000);
			setIcon(bomb);
			bubbleState = BubbleState.BOMB;
			Thread.sleep(500);
			mContext.remove(this);
			mContext.repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
