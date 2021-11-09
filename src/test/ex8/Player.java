package test.ex8;

import java.awt.Color;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

// Player <- 배경화면 의존관계
@Getter
@Setter
public class Player extends JLabel implements Moveable {

	// 의존성 콤포지션
	private FrontMap frontMap;

	private int x;
	private int y;
	private Direction direction;
	private boolean crash; // 충돌 감지

	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	private int xLeftEnd = 75;
	private int xRightEnd = 890;

	private final int SPEED = 3;
	private final int JUMPSPEED = 1;

	private ImageIcon playerR;
	private ImageIcon playerL;

	public Player(FrontMap frontMap) {
		this.frontMap = frontMap;
		initObject();
		initSetting();
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}

	private void initSetting() {
		x = 55;
		y = 535;

		direction = Direction.RIGHT; // 1
		crash = false;

		left = false;
		right = false;
		up = false;
		down = false;

		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	@Override
	public void up() {
		if (up == false && down == false) {
			up = true;
			new Thread(() -> {

				for (int i = 0; i < 120; i++) {
					y = y - (JUMPSPEED);
					setLocation(x, y);
					// System.out.println("x, y 좌표 : "+x+","+y);
					// System.out.println("FrontMap : "+frontMap.getColorAtRelocation(frontMap, new
					// Point(x+50,y+50)));
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
	}

	@Override
	public void down() {
		if (down == false) {
			down = true;
			new Thread(() -> {
				if (down) {
					for (int i = 0; i < 120; i++) {
						y = y + (JUMPSPEED);
						setLocation(x, y);
						// System.out.println("x, y 좌표 : "+x+","+y);
						// System.out.println("FrontMap : "+frontMap.getColorAtRelocation(frontMap, new
						// Point(x+50,y+50)));
						try {
							Thread.sleep(5);
						} catch (Exception e) {
							System.out.println("아래쪽 이동중 인터럽트 발생 : " + e.getMessage());
						}
					}
					down = false;
				}
			}).start();
		}
	}

	@Override
	public void left() {
		if (left == false) {
			left = true;
			direction = Direction.LEFT;
			setIcon(playerL);

			new Thread(() -> {
				while (left) {
					System.out.println("x, y 좌표 : " + x + "," + y);
					if (x > xLeftEnd) {
						x = x - SPEED;
						setLocation(x, y);
					}

					try {
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println("왼쪽 이동중 인터럽트 발생 : " + e.getMessage());
					}
				}
			}).start();
		}
	}

	@Override
	public void right() {
		if (right == false) {
			right = true;
			direction = Direction.RIGHT;
			setIcon(playerR);

			new Thread(() -> {
				while (right) {
					System.out.println("x, y 좌표 : " + x + "," + y);
					if (x < xRightEnd) {
						x = x + SPEED;
						setLocation(x, y);
					}

					try {
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println("오른쪽 이동중 인터럽트 발생 : " + e.getMessage());
					}
				}
			}).start();

		}
	}

	// 방향에 따른 제약 주기
	private void constraintDownPlayer() {
	
	}

}
