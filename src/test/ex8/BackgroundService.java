package test.ex8;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackgroundService implements Runnable{
	private BufferedImage image;
	private Player player;
	
	private int leftColor;
	private int rightColor;
	private int topColor;
	private int bottomColor;
	
	public BackgroundService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundService.png"));	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while(true) {
			try {
				// 하얀색은 -1
				// 빨간색은 -1237980
				// 노란색은 -3584
				// 파랑색은 -12629812
				
				leftColor = image.getRGB(player.getX()-5, player.getY()+25);
				rightColor = image.getRGB(player.getX()+50+5, player.getY()+25);
				topColor = image.getRGB(player.getX()+25, player.getY()-5);
				bottomColor = image.getRGB(player.getX()+25, player.getY()+50+5);
				
				
				if(bottomColor != -1) {
					System.out.println("바텀에 먼가 있어");
					player.setDown(false);
				}
				System.out.println("플레이어 왼쪽 색상 : "+image.getRGB(player.getX()-5, player.getY()+25));
				System.out.println("플레이어 오른쪽 색상 : "+image.getRGB(player.getX()+50+5, player.getY()+25));
				System.out.println("플레이어 탑 색상 : "+image.getRGB(player.getX()+25, player.getY()-5));
				System.out.println("플레이어 바텀 색상 : "+image.getRGB(player.getX()+25, player.getY()+50+5));
				System.out.println("===================");
				Thread.sleep(10);
			} catch (Exception e) {
				
			}
		}
		
	}
	
	
}
