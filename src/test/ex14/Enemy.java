package test.ex14;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

enum EnemyState {
	LIVE, DEAD, BUBBLED;
}

@Getter
@Setter
public class Enemy extends JLabel implements Moveable {
	private int x;
	private int y;

	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private EnemyState enemyState;

	private EnemyDirection enemyDirection;
	
	private boolean leftCrash;
	private boolean rightCrash;

	private static final int SPEED = 3;
	private static final int JUMPSPEED = 1;

	private ImageIcon enemyR;
	private ImageIcon enemyL;

	public Enemy() {
		initObject();
		initSetting();
		initBackgroundEnemyService();
		start();
	}


	private void start() {
		enemyState = EnemyState.LIVE;
		enemyDirection = EnemyDirection.RIGHT;
		left();
	}
	
	

	private void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}

	private void initSetting() {
		x = 480;
		y = 178;

		left = false;
		right = false;
		up = false;
		down = false;

		leftCrash = false;
		rightCrash = false;

		setIcon(enemyR);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
	}

	@Override
	public void up() {
		System.out.println("UP");
		up = true;
		Thread t = new Thread(() -> {

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
		});
		
		t.start();

	}

	@Override
	public void down() {
		System.out.println("DOWN");
		down = true;
		Thread t = new Thread(() -> {
			while (down) {
				y = y + (JUMPSPEED);
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (Exception e) {
					System.out.println("아래쪽 이동중 인터럽트 발생 : " + e.getMessage());
				}
			}
			
		});
		
		t.start();

		
	}

	@Override
	public void left() {
		System.out.println("LEFT");
		enemyDirection = EnemyDirection.LEFT;
		setIcon(enemyL);
		left = true;
		

		Thread t = new Thread(() -> {
			while (left) {
				x = x - SPEED;
				setLocation(x, y);

				try {
					Thread.sleep(10);
				} catch (Exception e) {
					System.out.println("왼쪽 이동중 인터럽트 발생 : " + e.getMessage());
				}
			}

		});
		
		t.start();
		

	}

	@Override
	public void right() {
		System.out.println("RIGHT");
		enemyDirection = EnemyDirection.RIGHT;
		setIcon(enemyR);

		right = true;
		

		Thread t = new Thread(() -> {
			while (right) {
				x = x + SPEED;
				setLocation(x, y);

				try {
					Thread.sleep(10);
				} catch (Exception e) {
					System.out.println("오른쪽 이동중 인터럽트 발생 : " + e.getMessage());
				}
			}
			
		});
		
		t.start();


		
	}

}
