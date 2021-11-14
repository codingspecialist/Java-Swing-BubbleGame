package test.ex10;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackgroundBubbleService implements Runnable {
	private BufferedImage image;
	private Bubble bubble;

	private int leftColor;
	private int rightColor;
	private int topColor;
	private int bottomColor;

	public BackgroundBubbleService(Bubble bubble) {
		this.bubble = bubble;
		try {
			image = ImageIO.read(new File("image/backgroundService.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {

				leftColor = image.getRGB(bubble.getX() - 10, bubble.getY() + 25);
				rightColor = image.getRGB(bubble.getX() + 50 + 10, bubble.getY() + 25);
				topColor = image.getRGB(bubble.getX() + 25, bubble.getY() - 10);

				System.out.println(rightColor);
				if (leftColor != -1) {
					bubble.setLeft(false);
				} else if (rightColor != -1) {
					System.out.println("오른쪽에 걸렸어");
					bubble.setRight(false);
				} else if(bubble.getY() <50){
					if(topColor != -1) {
						bubble.setUp(false);
					}
				}

				Thread.sleep(10);
			} catch (Exception e) {

			}
		}

	}

}
