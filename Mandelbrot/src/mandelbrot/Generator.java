package mandelbrot;
import java.awt.Color;

import se.lth.cs.ptdc.fractal.MandelbrotGUI;

public class Generator {

	public Generator() {
		// TODO skapa Color[][] picture;
		// går detta ens nu när vi ändrar storlek på den?
	}

	/** Renderar mandelbrot och ritar upp det i MandelbrotGUI */
	public void render(MandelbrotGUI gui) {
		gui.disableInput();
		//tolka input: (resolution)
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
		
		//tolka input: (svartvitt)
		boolean blackAndWhite;
		switch(gui.getMode()){
		case MandelbrotGUI.MODE_BW:
			blackAndWhite = true;
			break;
		case MandelbrotGUI.MODE_COLOR:
			blackAndWhite = false;
			break;
		default:
			blackAndWhite = true;	
		}
		// TODO extrarutan
		// tolka input: (extrarutan)
		/** delar up texten i extrarutan i olika kommandon med olika värden 
		 * 	till exempel: "-itt 300" -> command itt=300
		 * 	"-itt 400 -col red" -> command itt=400
		 * 							command col=red
		 * (kommandon ges som argument till terminalprogram)
		 * programm -in fil.txt -out fil2.txt
		 * fast utan programm (obviously)
		 * */
		String extra = gui.getExtraText();
		String extras[] = extra.split("-");
		for(int i=0; i<extras.length; i++){
			String current[] = extras[i].split(" ");
			if(current.length>1){
				String command = current[0];
				String value = current[1];
				
				//System.out.println("Command:" + command + "value" + value);
				//TODO if command = itt{ itterations = value} etc
				
			}else{
				System.out.println("Texten i extrarutan innehåller felaktiga komandon");
			}
		}
		// klar med att tolka input

		/**
		 * Anropar mesh f�r att f� en matris med representationer av komplexa
		 * tal f�r var enskilld pixel p� ritytan.
		 */
		Complex complex[][] = mesh(
				gui.getMinimumReal(), gui.getMaximumReal(), 
				gui.getMinimumImag(), gui.getMaximumImag(), 
				gui.getWidth(), gui.getHeight());
		System.out.println("done with mesh");

		/** Skapar en f�rgmatris med "r�tt" storlek i f�rh�llande till uppl�sningen */
		int heightSize = getMaxArrayIndex(gui.getHeight(),pixelHeight); 
		int widthSize = getMaxArrayIndex(gui.getWidth(),pixelHeight);
		Color[][] picture = new Color[heightSize][widthSize];
		//System.out.println(heightSize + ":" + widthSize);
		System.out.println("done with picture[][]");
		
		/** Generate colorarray from complexarray */
		for (int y = 0; y < heightSize; y++) {
			int jumpY = (pixelHeight/2)+(y*pixelHeight); //y-v�rdet f�r punkten i mitten av pixeln i f�rgmatrisen
			//Fixar out of bounds exception f�r h�jd
			if(jumpY >= gui.getHeight()){
				jumpY = gui.getHeight()-1;
			}
			for (int x = 0; x < widthSize; x++) {
				int jumpX = (pixelWidth/2)+(x*pixelWidth); //x-v�rdet f�r punkten i mitten av pixeln i f�rgmatrisen
				//Fixar out of bounds exception f�r bredd
				if(jumpX >= gui.getWidth()){
					jumpX = gui.getWidth()-1;
				}
				picture[y][x] = generateMandelColor(complex[jumpY][jumpX], blackAndWhite);
			}
			
		}
		System.out.println("done with rendering");
		gui.putData(picture, pixelWidth, pixelHeight);
		System.out.println("done with putData");
		gui.enableInput();
	}

	/** Skapar en matris av komplexa tal som motsvarar pixlarna i ritfönstret */
	private Complex[][] mesh(double minRe, double maxRe, double minIm,
			double maxIm, int width, int height) {
		// TODO ändra namnet på pixelArray (kanske)
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
	 * Ber�knar hur m�nga rader/kolumner du b�r skapa i f�rgmatrisen f�r en
	 * viss uppl�sning
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
	/** Best�mmer f�rgen f�r en pixel beroende p� ett givet komplext tal */
	private Color generateMandelColor(Complex c, boolean blackAndWhite) {
		Color color = Color.BLACK;
		//flatuicolors:
		Color[] colors = {
				new Color(236, 240, 241),
				new Color(189, 195, 199),
				new Color(149, 165, 166),
				new Color(127, 140, 141),
				new Color(241, 196, 15),
				new Color(243, 156, 18),
				new Color(230, 126, 34),
				new Color(211, 84, 0),
				new Color(231, 76, 60),
				new Color(192, 57, 43),
				new Color(46, 204, 113),
				new Color(39, 174, 96),
				new Color(26, 188, 156),
				new Color(22, 160, 133),
				new Color(52, 152, 219),
				new Color(41, 128, 185),
				new Color(155, 89, 182),
				new Color(142, 68, 173),
				new Color(52, 73, 94),
				new Color(44, 62, 80),
		};
		int iterations = 200;
		Complex z = new Complex(0, 0);
		
		for(int i = 0; i<iterations; i++){
			z.mul(z);
			z.add(c);
			if(z.getAbs2()>2){
				if(blackAndWhite){
					color = Color.WHITE;
				}else{
					//skapar en gradient
					color = Color.getHSBColor(i/(float)iterations, 1, 1);
					// om man vill ha flatuicolors
					// color = colors[i/10];
				}
				break;
			}
		}
		return color;
	}

}