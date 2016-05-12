package br.com.finchsolucoes.ominipage.tiff;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageCombiner {

	public static BufferedImage attachImages(BufferedImage img1, BufferedImage img2) {
		BufferedImage resultImage = new BufferedImage(img1.getWidth() + img2.getWidth(),
				img1.getHeight() + img2.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = resultImage.getGraphics();
		g.drawImage(img1, 0, 0, null);
		g.drawImage(img2, 0, img1.getHeight(), null);
		return resultImage;
	}
}
