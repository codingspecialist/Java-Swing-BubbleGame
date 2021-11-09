package test.ex5;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author 겟인데어 
 * 플레이어 점프(위아래) 이동 테스트
 */

interface Moveable {
	public abstract void up();

	public abstract void down();

	public abstract void left();

	public abstract void right();
}

enum Direction {
	// 0, 1, 2, 3
	LEFT, RIGHT, UP, DOWN;
}

// 플레이어는 현재위치(x,y), 방향(direction) 이라는 상태를 가진다.
// 플레이어는 방향을 변경하고 현재위치를 변경할 수 있는 상태를 가진다.
// 상태에 대한 제약을 주자 (위, 아래, 왼쪽, 오른쪽) - 인터페이스 생성하기!!
class Player extends JLabel implements Moveable {

	// Checking
	// Player는 BackgroundMap에 의존해야 한다. Map을 모르면 움직일 수 없다. (상속 or 콤포지션 머쓸지 고민....)

	int x;
	int y;
	Direction direction;
	boolean left;
	boolean right;
	boolean up;
	boolean down;

	// 기본 속도 상수로 처리 (변경안됨)
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
		// 버그 : 다운 혹은 점프 중에 점프막기 (up==false && down==false) 조건 주기
		if(up == false && down == false) {
			up = true;
			new Thread(() -> {
				
				// 점프 거리를 120정도 잡자
				for (int i = 0; i < 120; i++) {
					y = y - (JUMPSPEED); // 왼쪽상단 좌표가 0,0 이다. 위로 이동은 y값이 -가 되어야 한다.
					setLocation(x, y);
					try {
						Thread.sleep(5);
					} catch (Exception e) {
						System.out.println("위쪽 이동중 인터럽트 발생 : " + e.getMessage());
					}
				} // end of for
					// 점프가 끝났을 때 자동으로 아래로 내려와야 한다.
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
				if (down) { // 점프 후 다운은 현 위치에 따라서 어디까지 내려가야 할지 알 수 없다.
					
					// 우선은 점프한 만큼만 내려가게 테스트하기
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

	// 키보드를 press할 때 마다 right() 메서드를 계속 호출하는 것은 성능상 좋지 않다.
	@Override
	public void right() {
		if (right == false) {
			right = true;
			direction = Direction.RIGHT;
			setIcon(playerR);

			// right가 true일 때까지 while문 반복하기
			// 언제 멈춰? - 키를 release 할 때나, left 키를 press 할 때 멈추면 된다. (right를 false로 주면서)
			new Thread(() -> {
				// while 문을 적용하게 되면 setLocation 이벤트가 루프에 쌓여서 메인쓰레드가 repaint를 할 수 없다.
				// 새로운 스레드를 적용해줘야 한다.
				while (right) {

					//System.out.println("right 반복됨 : x :" + x);
					x = x + SPEED;

					setLocation(x, y);

					// 너무 빠르게 반복되기 때문에 sleep 적용
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

public class PlayerMoveTest extends BackgroundMap {

	private Player player;

	public PlayerMoveTest() {
		initObject();
		initSetting();
		initListener();

		setVisible(true);
	}

	private void initObject() {
		player = new Player();
	}

	private void initSetting() {
		add(player);
	}

	// 키보드 이벤트 주기
	private void initListener() {

		addKeyListener(new KeyAdapter() {

			// right = 39, left = 37, up = 38, down = 40
			@Override
			public void keyPressed(KeyEvent e) {
				// System.out.println(e.getKeyCode());
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT: // 16진수 0x25 -> 10진수 37
					player.left();
					break;
				case KeyEvent.VK_RIGHT:
					player.right();
					break;
				case KeyEvent.VK_UP:
					player.up();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					// while문 동작 멈추기
					player.right = false;
					break;
				case KeyEvent.VK_LEFT:
					player.left = false;
					break;
//				case KeyEvent.VK_UP:
//					player.up = false;
//					break;
				}
			}
		});

	}

	public static void main(String[] args) {
		new PlayerMoveTest();
	}
}
