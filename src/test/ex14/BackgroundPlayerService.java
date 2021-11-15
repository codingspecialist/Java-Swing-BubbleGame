package test.ex14;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackgroundPlayerService implements Runnable {
	private BufferedImage image;
	private Player player;

	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {

				Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
				Color rightColor = new Color(image.getRGB(player.getX() + 50 + 10, player.getY() + 25));

				// 바닥 색깔은 파랑도 있고 빨강도 있어서 int로 처리하는게 편함.
				int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5)
						+ image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5);

//				System.out.println("leftColor : " + leftColor);
//				System.out.println("rightColor " + rightColor);
//				System.out.println("topColor : " + topColor);
//				System.out.println("bottomColor : " + bottomColor);

				if (bottomColor != -2) {
					player.setDown(false);
				}else if (!player.isUp() && !player.isDown()) {
					player.down();
				}

				if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
					player.setLeft(false);
					player.setLeftCrash(true);
				} else if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
					player.setRight(false);
					player.setRightCrash(true);
				} else {
					player.setLeftCrash(false);
					player.setRightCrash(false);
				}

				Thread.sleep(10);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
		}

	}

}
