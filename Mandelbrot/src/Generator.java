import java.awt.Color;

import se.lth.cs.ptdc.fractal.MandelbrotGUI;


public class Generator {

	private Complex[][] mesh(
			double minRe, double maxRe,
			double minIm, double maxIm,
			int width, int height){
		
		Complex[][] complex = new Complex[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double widthPercent = j/(double)width;
				double re = minRe + widthPercent*(maxRe-minRe);
				double heightPercent = 1-(i/(double)height);
				double im = minIm + heightPercent*(maxIm-minIm);
				complex[i][j] = new Complex(re, im);
			}
		}
		
		return complex;
	};
	
	public void render(MandelbrotGUI gui){
		gui.disableInput();
		Complex complex[][] = mesh(
				gui.getMinimumReal(), gui.getMaximumReal(), 
				gui.getMinimumImag(), gui.getMaximumImag(), 
				gui.getWidth(), gui.getHeight());
		
		boolean debug = false;
		if (debug){
			System.out.println(complex[0][0].getIm() + " == " + gui.getMaximumImag());
			System.out.println(complex[0][0].getRe() + " == " + gui.getMinimumReal());
			System.out.println(complex[gui.getHeight()-1][gui.getWidth()-1].getIm() + " == " + gui.getMinimumImag());
			System.out.println(complex[gui.getHeight()-1][gui.getWidth()-1].getRe() + " == " + gui.getMaximumReal());
		}
		
		Color[][] picture = new Color[gui.getHeight()][gui.getWidth()];
		for (int i = 0; i < gui.getHeight(); i++) {
			for (int j = 0; j < gui.getWidth(); j++) {
				// TODO generate mandelbrot instead
				if (complex[i][j].getAbs2()<1){
					int r = 0;
					int b = 0;
					if (complex[i][j].getRe()>0){
						r = 255;
					}
					if(complex[i][j].getIm()>0){
						b = 255;
					}
					picture[i][j] = new Color(r, 0, b);
				}
				else{
					picture[i][j] = new Color(255, 255, 255);
				}
			}
		}
		gui.putData(picture, 1, 1);
		gui.enableInput();
	}
	
}
