package test.ex9;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

enum BubbleState {
	BUBBLE, BUBBLED, BOOM;
}

@Setter
@Getter
public class Bubble extends JLabel{

	private int x;
	private int y;
	
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
		setIcon(bubble);
		setSize(50, 50);
		setLocation(x, y);
	}
	
	public void shoot(Direction direction) {
		
		new Thread(()->{
			
			try {
				if(direction == Direction.LEFT) {
					for(int i = 0; i< 300;i++) {
						setLocation(x, y);
						x--;
						Thread.sleep(3);
					}
				}else {
					for(int i = 0; i< 300;i++) {
						setLocation(x, y);
						x++;
						Thread.sleep(3);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
		}).start();
		

	}
}
