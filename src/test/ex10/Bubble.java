package test.ex10;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

enum BubbleState {
	BUBBLE, BUBBLED, BOOM;
}

@Setter
@Getter
public class Bubble extends JLabel {

	private int x;
	private int y;

	private boolean up;
	private boolean left;
	private boolean right;
	
	private ImageIcon bubble;
	private ImageIcon bubbled;
	private BubbleState bubbleState;

	public Bubble(int x, int y) {
		this.x = x;
		this.y = y;
		initObject();
		initSetting();
	}

	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");

		bubbleState = bubbleState.BUBBLE;
	}

	private void initSetting() {
		up = false;
		left = false;
		right = false;
		setIcon(bubble);
		setSize(50, 50);
		setLocation(x, y);
	}
	
	public void shootRight() {
		right = true;
		new Thread(() -> {

			try {
				for (int i = 0; i < 300; i++) {
					if(right) {
						setLocation(x, y);
						x++;

						Thread.sleep(3);
					}

				}

				shootUp();
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}).start();

	}

	public void shootLeft() {
		left = true;
		new Thread(() -> {
			
			try {
				for (int i = 0; i < 300; i++) {
					if(left) {
						setLocation(x, y);
						x--;

						Thread.sleep(3);
					}

				}

				shootUp();
				
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}).start();

	}
	
	public void shootUp() {
		left = false;
		right = false;
		up = true;
		while(up) {
			setLocation(x, y);
			y--;
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
