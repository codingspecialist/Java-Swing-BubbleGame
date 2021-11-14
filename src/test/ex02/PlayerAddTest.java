package test.ex02;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author 겟인데어
 * 백그라운드 이미지에 플레이어 추가하기 테스트
 *
 */

enum Direction {
	// 0, 1, 2, 3
	LEFT, RIGHT, UP, DOWN;
}

class Player extends JLabel{
	
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
}

public class PlayerAddTest extends BackgroundMap{

	private Player player;
	
	public PlayerAddTest() {
		initObject();
		initSetting();
		
		setVisible(true);
	}
	
	private void initObject() {
		player = new Player();
	}
	private void initSetting() {
		add(player);
	}
	
	public static void main(String[] args) {
		new PlayerAddTest();
	}
}









