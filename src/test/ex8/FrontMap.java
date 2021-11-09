package test.ex8;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

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

    public Color getColorAtRelocation(final Component comp, final Point p) {
        final Point loc = comp.getLocation();
        final BufferedImage bimg = comp.getGraphicsConfiguration().createCompatibleImage(1, 1);
        comp.setLocation(loc.x - p.x, loc.y - p.y);
        final Graphics2D g2d = (Graphics2D) bimg.createGraphics();
        //g2d.setClip(0, 0, 1, 1);
        comp.getParent().printAll(g2d);
        comp.setLocation(loc);
        g2d.dispose();
        final Color c = new Color(bimg.getRGB(0, 0), true);
        bimg.flush();
        return c;
    }
}
