package test.ex8;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

/**
 * 
 * @author 겟인데어
 * FrontMap과 BackMap 테스트
 */

public class BubbleGame extends JFrame {

	private FrontMap frontMap;
	private Player player;

	public BubbleGame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);
	}

	private void initObject() {
		frontMap = new FrontMap();
		player = new Player(frontMap);
	}

	private void initSetting() {
		setSize(1000, 640);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(frontMap);
		add(player);
	}

	private void initListener() {
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
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
					player.setRight(false);
					break;
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				}
			}
		});
	}

	public static void main(String[] args) {
		new BubbleGame();
	}
}
