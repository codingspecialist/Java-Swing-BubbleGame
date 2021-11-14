package test.ex11;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author 겟인데어 FrontMap과 BackMap 테스트
 **/

public class BubbleGame extends JFrame {

	private BubbleGame mContext = this;
	private JLabel frontMap;
	private BackgroundPlayerService backgroundPlayerService;
	
	private Player player;

	public BubbleGame() {
		initObject();
		initSetting();
		initListener();
		initThread();
		setVisible(true);
	}

	private void initThread() {
		new Thread(backgroundPlayerService).start();
	}

	private void initObject() {
		player = new Player();
		
		backgroundPlayerService = new BackgroundPlayerService(player);
		frontMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		// new BGM();
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
					if (!player.isLeft())
						player.left();
					break;
				case KeyEvent.VK_RIGHT:
					if (!player.isRight())
						player.right();
					break;
				case KeyEvent.VK_UP:
					if (!player.isUp() && !player.isDown())
						player.up();
					break;
				case KeyEvent.VK_SPACE:
					if (player.getDirection() == Direction.LEFT) {
						Bubble bubble = new Bubble(player.getX() - 50, player.getY(), mContext);
						add(bubble);
						bubble.shootLeft();
						break;
					} else {
						Bubble bubble = new Bubble(player.getX() + 50, player.getY(), mContext);
						add(bubble);
						bubble.shootRight();
						break;
					}
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
