package test.ex3;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author 겟인데어
 * 백그라운드 이미지에서 플레이어 움직이기 테스트
 *
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
class Player extends JLabel implements Moveable{
	
	// Checking
	// Player는 BackgroundMap에 의존해야 한다. Map을 모르면 움직일 수 없다. (상속 or 콤포지션 머쓸지 고민....)
	
	private int x;
	private int y;
	private Direction direction;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private ImageIcon playerR;
	private ImageIcon playerL;
	
	public Player() {
		initObject();
		initSetting();
	}
	
	private void initObject() {	
		playerR =  new ImageIcon("image/playerR.png");
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

	}

	@Override
	public void down() {
		
	}

	@Override
	public void left() {
		left = true;
		direction = Direction.LEFT;
		setIcon(playerL);
		
	}

	// 키보드를 press할 때 마다 right() 메서드를 계속 호출하는 것은 성능상 좋지 않다.
	@Override
	public void right() {
		right = true;
		direction = Direction.RIGHT;
		setIcon(playerR);
		
		System.out.println("right 메서드 호출됨 : x :"+x);
		x = x + 3;
		setLocation(x, y);
	}
}

public class PlayerMoveTest extends BackgroundMap{

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
		
		// 키보드 누르고 있는 이벤트
		// right = 39, left = 37, up = 38, down = 40
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: // 16진수 0x25 -> 10진수 37
						player.left();
						break;
					case KeyEvent.VK_RIGHT:
						player.right();
						break;
					case KeyEvent.VK_UP:
						player.up();
						break;
					case KeyEvent.VK_DOWN:
						player.down();
						break;
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new PlayerMoveTest();
	}
}









