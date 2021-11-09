package test.ex5;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author 겟인데어
 * 이미지 백그라운드 테스트
 *
 */

public class BackgroundMap extends JFrame{
	
	protected JLabel bgMap;
	
	public BackgroundMap() {		
		initObject();
		initSetting();
	}
	
	private void initObject() {
		bgMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
	}
	
	private void initSetting() {
		setSize(1000, 640);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(bgMap);
	}
}









