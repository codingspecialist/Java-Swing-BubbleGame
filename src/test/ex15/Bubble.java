package test.ex15;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

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
	private Enemy enemy;
	private Player player;
	private ImageIcon bomb;

	public Bubble(BubbleGame mContext, Enemy enemy, Player player) {
		this.mContext = mContext;
		this.enemy = enemy;
		this.player = player;
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
	}

	private void initSetting() {
		up = false;
		left = false;
		right = false;

		this.x = player.getX();
		this.y = player.getY();

		setIcon(bubble);
		setSize(50, 50);
		
		bubbleState = BubbleState.BUBBLE;
	}

	private void initThread() {
		new Thread(() -> {
			if (player.getPlayerDirection() == PlayerDirection.LEFT) {
				shootLeft();
			} else {
				shootRight();
			}

		}).start();
	}

	public void shootLeft() {
		for (int i = 0; i < 400; i++) {
			try {
				setLocation(x, y);

				setLeft(true);
				x--;
				Color leftColor = new Color(image.getRGB(x - 10, y + 25));
				if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
					break;
				}

				if (Math.abs(x - enemy.getX()) < 10 && Math.abs(y - enemy.getY()) < 50) {
					
					if (enemy.getEnemyState() == EnemyState.LIVE) {
						attack();
						break;
					}
				}
				Thread.sleep(1);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}

		}
		setLeft(false);
		shootUp();
	}

	public void shootRight() {
		for (int i = 0; i < 400; i++) {
			try {
				setLocation(x, y);

				setRight(true);
				x++;
				Color rightColor = new Color(image.getRGB(x + 50 + 10, y + 25));
				if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
					break;
				}

				if (Math.abs(x - enemy.getX()) < 10 && Math.abs(y - enemy.getY()) < 50) {
					if (enemy.getEnemyState() == EnemyState.LIVE) {
						attack();
						break;
					}
				}
				Thread.sleep(1);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}

		}
		setRight(false);
		shootUp();
	}

	public void shootUp() {
		up = true;
		while (up) {

			try {
				setLocation(x, y);
				y--;
				Color topColor = new Color(image.getRGB(x + 25, y - 10));
				if (bubbleState == BubbleState.BUBBLED) {
					if (topColor.getRGB() != -1) {
						setUp(false);
					}
					Thread.sleep(20);
				} else {
					if (topColor.getRed() == 255 && topColor.getBlue() == 0 && topColor.getGreen() == 0) {
						setUp(false);
						bubbleClear();
					}
					Thread.sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void attack() {
			bubbleState = BubbleState.BUBBLED;
			enemy.setEnemyState(EnemyState.BUBBLED);
			setIcon(bubbled);
			mContext.remove(enemy);
			mContext.repaint();
	}

	public void bubbleClear() {
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
	
	public void bubbledClear() {
		try {
			setUp(false);
			setIcon(bomb);
			Thread.sleep(1000);
			mContext.remove(this);
			mContext.repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
