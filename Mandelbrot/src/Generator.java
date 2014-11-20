import java.awt.Color;

import se.lth.cs.ptdc.fractal.MandelbrotGUI;

public class Generator {

	public Generator() {
		// TODO skapa Color[][] picture;
	}

	/** Renderar mandelbrot och ritar upp det i MandelbrotGUI */
	public void render(MandelbrotGUI gui) {
		gui.disableInput();
		int pixelWidth;
		int pixelHeight;
		switch (gui.getResolution()) {
		case MandelbrotGUI.RESOLUTION_VERY_HIGH:
			pixelHeight = 1;
			break;
		case MandelbrotGUI.RESOLUTION_HIGH:
			pixelHeight = 3;
			break;
		case MandelbrotGUI.RESOLUTION_MEDIUM:
			pixelHeight = 5;
			break;
		case MandelbrotGUI.RESOLUTION_LOW:
			pixelHeight = 7;
			break;
		case MandelbrotGUI.RESOLUTION_VERY_LOW:
			pixelHeight = 9;
			break;
		default:
			pixelHeight = 1;
		}
		pixelWidth = pixelHeight;

		Complex complex[][] = mesh(gui.getMinimumReal() / pixelWidth,
				gui.getMaximumReal() / pixelWidth, gui.getMinimumImag()
						/ pixelHeight, gui.getMaximumImag() / pixelHeight,
				(int) gui.getWidth() / pixelWidth, (int) gui.getHeight()
						/ pixelHeight);

		// boolean debug = false;
		// if (debug){
		// System.out.println(complex[0][0].getIm() + " == " +
		// gui.getMaximumImag());
		// System.out.println(complex[0][0].getRe() + " == " +
		// gui.getMinimumReal());
		// System.out.println(complex[gui.getHeight()-1][gui.getWidth()-1].getIm()
		// + " == " + gui.getMinimumImag());
		// System.out.println(complex[gui.getHeight()-1][gui.getWidth()-1].getRe()
		// + " == " + gui.getMaximumReal());
		// }

		/** Generate colorarray from complexarray */
		Color[][] picture = new Color[gui.getHeight()][gui.getWidth()];
		for (int i = 0; i < gui.getHeight() / pixelHeight; i += pixelHeight) {
			for (int j = 0; j < gui.getWidth() / pixelWidth; j += pixelWidth) {
				// TODO generate mandelbrot instead
				// TODO Color/BW
				
				// ritar en cirkel
				if (complex[i][j].getAbs2() < 2) {
					int r = 0;
					int b = 0;
					if (complex[i][j].getRe() > 0) {
						r = 255;
					}
					if (complex[i][j].getIm() > 0) {
						b = 255;
					}
					picture[i][j] = new Color(r, 0, b);
				} else {
					picture[i][j] = new Color(255, 255, 255);
				}
			}
		}
		// TODO extrarutan
		gui.putData(picture, pixelWidth, pixelHeight);
		gui.enableInput();
	}
	
	/** Skapar en matris av komplexa tal som motsvarar pixlarna i ritfönstret */
	private Complex[][] mesh(double minRe, double maxRe, double minIm,
			double maxIm, int width, int height) {
		//TODO ändra namnet på pixelArray (kanske)
		Complex[][] pixelArray = new Complex[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double widthPercent = j / (double) width;
				double re = minRe + widthPercent * (maxRe - minRe);
				double heightPercent = i / (double) height;
				double im = maxIm - heightPercent * (maxIm - minIm);
				pixelArray[i][j] = new Complex(re, im);
			}
		}
		return pixelArray;
	}

}
