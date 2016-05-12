package br.com.finchsolucoes.ominipage.tiff;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Pdf2TiffConverter {
    public static void main(String[] args) {
        String pdf = "/Dados/public/Download.pdf";
        String out = "/Dados/public/Download.jpg";
        try {
        	List<BufferedImage> imgs = savePdfAsImage(pdf);
        	
        	BufferedImage grande = null;
        	if (imgs.size() > 1){
        		grande = imgs.get(0);
	        	for (int i = 1; i < imgs.size(); i++) {
	        		grande = ImageCombiner.attachImages(grande, imgs.get(i));
				}
        	}
        	/*imgs.clear();
        	imgs.add(grande);
        	MultipleTiffSaver.saveAsMultipageTIFF(imgs, out, 800);*/
        	File outFile = new File(out);
        	
        	ImageIO.write(grande, "jpg", outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private static List<BufferedImage> savePdfAsImage(String pdf) throws IOException {
		List<BufferedImage> ret = new ArrayList<>();
		PDDocument document = PDDocument.load(new File(pdf));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
			//BufferedImage bim = pdfRenderer.renderImage(page);
			//File outputfile = new File("/Dados/public/image.jpg");
			//ImageIO.write(bim, "jpg", outputfile);
			ret.add(bim);
			// suffix in filename will be used as the file format
			// ImageIOUtil.writeImage(bim, pdfFilename + "-" + (page+1) +
			// ".png", 300);
		}
		document.close();
		return ret;
	}
}