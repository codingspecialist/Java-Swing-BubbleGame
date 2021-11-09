package test.ex7;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import lombok.Getter;

@Getter
public class FrontMap extends JPanel {

	private ImageIcon bgIcon;
	private Image bgImg;

	public FrontMap() {
		initObject();
		initSetting();
	}

	private void initObject() {
		bgIcon = new ImageIcon("image/backgroundMap.png");
		bgImg = bgIcon.getImage();
	}
	
	private void initSetting() {
		setLayout(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 배경이미지를 x=0, y=0 좌표에 그린다. 대상은 JPanel 즉 frontMap
		g.drawImage(bgImg, 0,0, this);
	}
}
