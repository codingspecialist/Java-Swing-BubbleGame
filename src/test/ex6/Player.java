package test.ex6;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel implements Moveable {
	int x;
	int y;
	Direction direction;
	boolean left;
	boolean right;
	boolean up;
	boolean down;

	private final int SPEED = 3;
	private final int JUMPSPEED = 1;

	private ImageIcon playerR;
	private ImageIcon playerL;

	public Player() {
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
		if(up == false && down == false) {
			up = true;
			new Thread(() -> {

				for (int i = 0; i < 120; i++) {
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
	}

	@Override
	public void right() {
		if (right == false) {
			right = true;
			direction = Direction.RIGHT;
			setIcon(playerR);

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

}
