package test;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author 겟인데어
 * 이미지 백그라운드 테스트
 *
 */

public class BgMapTest extends JFrame{
	
	private JLabel bgMap;
	
	public BgMapTest() {		
		initObject();
		initSetting();
		
		setVisible(true);
	}
	
	private void initObject() {
		bgMap = new JLabel(new ImageIcon("image/bgMap.png"));
	}
	
	private void initSetting() {
		setSize(1000, 640);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(bgMap);
	}
	
	public static void main(String[] args) {
		new BgMapTest();
	}
}









