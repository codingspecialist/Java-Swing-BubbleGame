package test.ex15;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackgroundEnemyService implements Runnable {
	private BufferedImage image;
	private Enemy enemy;

	public BackgroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (enemy.getEnemyState() == EnemyState.LIVE) {

			try {

				Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25));
				Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 10, enemy.getY() + 25));

				int bottomColor = image.getRGB(enemy.getX() + 10, enemy.getY() + 50 + 5)
						+ image.getRGB(enemy.getX() + 50 - 10, enemy.getY() + 50 + 5);

				if (bottomColor != -2) {
					enemy.setDown(false);
				} else if (!enemy.isUp() && !enemy.isDown()) {
					enemy.down();
				}

				if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
					System.out.println("왼쪽충돌");
					enemy.setLeft(false);
					if (!enemy.isRight()) {
						enemy.right();
					}

				} else if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
					System.out.println("오른쪽충돌");
					enemy.setRight(false);
					if (!enemy.isLeft()) {
						enemy.left();
					}
				}

				Thread.sleep(10);
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
		}

	}

}
