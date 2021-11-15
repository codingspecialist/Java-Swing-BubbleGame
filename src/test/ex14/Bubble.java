package test.ex14;

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

	private BubbleState bubbleState;
	
	private ImageIcon bubble;
	private ImageIcon bubbled;
	

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
//			System.out.println("절대값 : "+Math.abs(y - mContext.getEnemy().getY()));
			if(Math.abs(x - mContext.getEnemy().getX()) < 10 && 
					Math.abs(y - mContext.getEnemy().getY()) < 50
				) {
				attack();
				break;
			}
			
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
			
			
			
//			System.out.println("버블 x좌표 : "+x);
//			System.out.println("적군 x좌표 : "+mContext.getEnemy().getX());
//			System.out.println("버블 y좌표 : "+y);
//			System.out.println("적군 y좌표 : "+mContext.getEnemy().getY());
//			System.out.println("절대값 : "+Math.abs(y - mContext.getEnemy().getY()));
			if(Math.abs(x - mContext.getEnemy().getX()) < 10 && 
					Math.abs(y - mContext.getEnemy().getY()) < 50
				) {
				attack();
				break;
			}

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
				Color topColor = new Color(image.getRGB(x + 25, y - 10));
				if(bubbleState == bubbleState.BUBBLED) {
					if (topColor.getRGB() != -1) {
						up = false;
					}
					Thread.sleep(20);
				}else {
					if (topColor.getRed() == 255 && topColor.getBlue() == 0 && topColor.getGreen() == 0) {
						up = false;
					}
					Thread.sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up = false;
		if(bubbleState == BubbleState.BUBBLE) bubbleStop();

	}

	public void attack() {
		if(mContext.getEnemy().getEnemyState() == EnemyState.LIVE) {
			System.out.println("적군 공 맞음");
			bubbleState = bubbleState.BUBBLED;
			mContext.getEnemy().setEnemyState(EnemyState.BUBBLED);
			setIcon(bubbled);
			mContext.remove(mContext.getEnemy());
			
			mContext.repaint();
		}

	}
	
	public void bubbleStop() {
		try {
			Thread.sleep(3000);
			
			bubbleState = BubbleState.BOMB;
			setIcon(bomb);
			Thread.sleep(500);
			
			mContext.remove(this);
			mContext.repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
