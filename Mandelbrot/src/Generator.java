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

		/**
		 * Anropar mesh för att få en matris med representationer av komplexa
		 * tal för var enskilld pixel på ritytan.
		 */
		Complex complex[][] = mesh(
				gui.getMinimumReal(), gui.getMaximumReal(), 
				gui.getMinimumImag(), gui.getMaximumImag(), 
				gui.getWidth(), gui.getHeight());

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

		int heightSize = getMaxArrayIndex(gui.getHeight(),pixelHeight); 
		int widthSize = getMaxArrayIndex(gui.getWidth(),pixelHeight);
		System.out.println(heightSize + ":" + widthSize);
		
		/** Generate colorarray from complexarray */
		Color[][] picture = new Color[heightSize][widthSize];
		for (int i = 0; i < heightSize; i++) {
			int jumpY = (pixelHeight/2)+(i*pixelHeight);
			if(jumpY>=heightSize){jumpY=heightSize-1;}
			for (int j = 0; j < widthSize; j++) {
				// TODO generate mandelbrot instead
				// TODO Color/BW
				int jumpX = (pixelHeight/2)+(j*pixelHeight);
				if(jumpX>=widthSize){jumpX=widthSize-1;}
				
				// ritar en cirkel
				if (complex[jumpY][jumpX].getAbs2() < 2) {
					int r = 0;
					int b = 0;
					if (complex[jumpY][jumpX].getRe() > 0) {
						r = 255;
					}
					if (complex[jumpY][jumpX].getIm() > 0) {
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

	/** Skapar en matris av komplexa tal som motsvarar pixlarna i ritfÃ¶nstret */
	private Complex[][] mesh(double minRe, double maxRe, double minIm,
			double maxIm, int width, int height) {
		// TODO Ã¤ndra namnet pÃ¥ pixelArray (kanske)
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

	/**
	 * Beräknar hur många rader/kolumner du bör skapa i färgmatrisen för en
	 * viss upplösning
	 */
	private int getMaxArrayIndex(int val, int res) {
		int calc;
		int diff = val % res;
		if (diff != 0) {
			calc = val + (res - diff);
		} else {
			calc = val;
		}
		calc = calc / res;
		
		System.out.println(diff);
		return calc;
	}

}
