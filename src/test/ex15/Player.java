package test.ex15;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

// Player <- 배경화면 의존관계
@Getter
@Setter
public class Player extends JLabel implements Moveable {

	private int x;
	private int y;
	
	private BubbleGame mContext;

	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private PlayerDirection playerDirection;

	private boolean leftCrash;
	private boolean rightCrash;

	private static final int SPEED = 4;
	private static final int JUMPSPEED = 2; 

	private ImageIcon playerR;
	private ImageIcon playerL;

	public Player(BubbleGame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundPlayerService();
	}
	
	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}

	private void initSetting() {
		x = 55;
		y = 535;

		left = false;
		right = false;
		up = false;
		down = false;
		
		playerDirection = PlayerDirection.RIGHT;

		leftCrash = false;
		rightCrash = false;

		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}
	
	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(mContext, this)).start();
	}
 

	@Override
	public void up() {
		up = true;
		new Thread(() -> {

			for (int i = 0; i < 130/JUMPSPEED; i++) {
				y = y - (JUMPSPEED);
				setLocation(x, y);
				try {
					Thread.sleep(5);
				} catch (Exception e) {
					System.out.println("위쪽 이동중 인터럽트 발생 : " + e.getMessage());
				}
			}

			up = false;
			down();
		}).start();

	}

	@Override
	public void down() {
		System.out.println("하강중");
		down = true;
		new Thread(() -> {
			while (down) {
				y = y + (JUMPSPEED);
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (Exception e) {
					System.out.println("아래쪽 이동중 인터럽트 발생 : " + e.getMessage());
				}
			}

		}).start();
	}

	@Override
	public void left() {
		setIcon(playerL);
		playerDirection = PlayerDirection.LEFT;
		left = leftCrash ? false : true;
		

		new Thread(() -> {
			while (left) {
				x = x - SPEED;
				setLocation(x, y);

				try {
					Thread.sleep(10);
				} catch (Exception e) {
					System.out.println("왼쪽 이동중 인터럽트 발생 : " + e.getMessage());
				}
			}
		}).start();

	}

	@Override
	public void right() {
		setIcon(playerR);
		playerDirection = PlayerDirection.RIGHT;
		right = rightCrash ? false : true;
		

		new Thread(() -> {
			while (right) {
				x = x + SPEED;
				setLocation(x, y);

				try {
					Thread.sleep(10);
				} catch (Exception e) {
					System.out.println("오른쪽 이동중 인터럽트 발생 : " + e.getMessage());
				}
			}
		}).start();

	}

}
