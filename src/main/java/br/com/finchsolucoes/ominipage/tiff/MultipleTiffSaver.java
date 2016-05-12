package br.com.finchsolucoes.ominipage.tiff;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;

public class MultipleTiffSaver {

	public static void saveAsMultipageTIFF(List<BufferedImage> bimTab, String filename, int dpi) throws IOException {
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
		ImageWriter imageWriter = writers.next();

		ImageOutputStream ios = ImageIO.createImageOutputStream(new File(filename));
		imageWriter.setOutput(ios);
		imageWriter.prepareWriteSequence(null);
		for (BufferedImage image : bimTab) {
			ImageWriteParam param = imageWriter.getDefaultWriteParam();
			IIOMetadata metadata = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image), param);
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			TIFFUtil.setCompressionType(param, image);
			TIFFUtil.updateMetadata(metadata, image, dpi);
			imageWriter.writeToSequence(new IIOImage(image, null, metadata), param);
		}
		imageWriter.endWriteSequence();
		imageWriter.dispose();
		ios.flush();
		ios.close();
	}

}
