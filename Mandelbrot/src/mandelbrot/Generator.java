package mandelbrot;

import java.awt.Color;

import se.lth.cs.ptdc.fractal.MandelbrotGUI;

public class Generator {
	Color[] colors = new Color[256];
	int iterations;

	public Generator() {
		// TODO skapa Color[][] picture;
		// går detta ens nu när vi ändrar storlek på den?
		// svar nej, den �ndras var g�ng vi �ndrar resolution allts� kan den
		// inte skapas direkt
		for (int i = 0; i < 256; i++) {
			colors[i] = new Color(211, i, 0);
		}
	}

	/** Renderar mandelbrot och ritar upp det i MandelbrotGUI */
	public void render(MandelbrotGUI gui) {
		// Avaktiverar m�jligheterna till input i gui uf�r att f�rhindra att en
		// �ndring f�r renderingen att bugga ur
		gui.disableInput();

		int pixelWidth;
		int pixelHeight;
		// Tolkar input: Vilken uppl�sning �nskas?
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

		// Tolkar input: Svartvit eller gradient?
		boolean blackAndWhite;
		switch (gui.getMode()) {
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
		// Tolkar input: (extrarutan)
		/**
		 * delar up texten i extrarutan i olika kommandon med olika värden till
		 * exempel: "-itt 300" -> command itt=300 "-itt 400 -col red" -> command
		 * itt=400 command col=red (kommandon ges som argument till
		 * terminalprogram) programm -in fil.txt -out fil2.txt fast utan
		 * programm (obviously)
		 * */
		String extra = gui.getExtraText();
		String extras[] = extra.split("-");
		for (int i = 0; i < extras.length; i++) {
			String current[] = extras[i].split(" ");
			if (current.length > 1) {
				String command = current[0];
				String value = current[1];

				// System.out.println("Command:" + command + "value" + value);
				// TODO if command = itt{ itterations = value} etc

			} else {
				System.out
						.println("Texten i extrarutan innehåller felaktiga komandon");
			}
		}
		// klar med att tolka input

		/**
		 * Anropar mesh f�r att f� en matris med representationer av
		 * komplexa tal f�r var enskilld pixel p� ritytan.
		 */
		Complex complex[][] = mesh(gui.getMinimumReal(), gui.getMaximumReal(),
				gui.getMinimumImag(), gui.getMaximumImag(), gui.getWidth(),
				gui.getHeight());
		System.out.println("done with mesh");

		/**
		 * Skapar en f�rgmatris som representerar pixlarna som ska ritas i
		 * gui. Pixlarnas storlek s�tts i f�rh�llande till uppl�sningen.
		 */
		int heightSize = getMaxArrayIndex(gui.getHeight(), pixelHeight);
		int widthSize = getMaxArrayIndex(gui.getWidth(), pixelHeight);
		Color[][] picture = new Color[heightSize][widthSize];
		// System.out.println(heightSize + ":" + widthSize);
		System.out.println("done with picture[][]");

		/** Generate colorarray from complexarray */
		/** Ger var och en av pixlarna i f�rgmatrisen "picture" en f�rg */
		for (int y = 0; y < heightSize; y++) {
			// y-v�rdet f�r den komplexa punkten i mitten av pixeln i
			// f�rgmatrisen
			int jumpY = (pixelHeight / 2) + (y * pixelHeight);

			// Fixar out of bounds exception f�r h�jd
			if (jumpY >= gui.getHeight()) {
				jumpY = gui.getHeight() - 1;
			}

			for (int x = 0; x < widthSize; x++) {
				// x-v�rdet f�r den komplexa punkten i mitten av pixeln i
				// f�rgmatrisen
				int jumpX = (pixelWidth / 2) + (x * pixelWidth);

				// Fixar out of bounds exception f�r bredd
				if (jumpX >= gui.getWidth()) {
					jumpX = gui.getWidth() - 1;
				}

				// Ger pixeln picture sin f�rg beroende p� mandelbrotsm�ngden
				picture[y][x] = generateMandelColor(complex[jumpY][jumpX],
						blackAndWhite);
			}

		}
		System.out.println("done with rendering");
		// S�nder f�rgmatrisen och dess pixelstorlek till gui f�r rendering i
		// f�nstret
		gui.putData(picture, pixelWidth, pixelHeight);
		System.out.println("done with putData");

		// �teraktiverar input
		gui.enableInput();
	}

	/** Skapar en matris av komplexa tal som motsvarar pixlarna i ritfönstret */
	private Complex[][] mesh(double minRe, double maxRe, double minIm,
			double maxIm, int width, int height) {
		// TODO ändra namnet på pixelArray (kanske)
		Complex[][] pixelArray = new Complex[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Ber�kna pixeln i fr�gas relativa avst�nd till v�nstra kanten
				// av f�nstret
				double widthPercent = j / (double) width;
				// Ger re v�rdet av: minRe (vilket �r v�nstra kanten) och
				// adderar med avst�ndet mellan minRe och maxRe multiplicerat
				// med det relativa avst�ndet till v�nstra kanten
				double re = minRe + widthPercent * (maxRe - minRe);

				// Samma procedur fas f�r den imagin�ra delen och h�jden
				double heightPercent = i / (double) height;
				double im = maxIm - heightPercent * (maxIm - minIm);
				pixelArray[i][j] = new Complex(re, im);
			}
		}
		return pixelArray;
	}

	/**
	 * Ber�knar hur m�nga rader/kolumner du b�r skapa i f�rgmatrisen
	 * f�r en viss uppl�sning
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
		Complex z = new Complex(0, 0);
		Color color = Color.BLACK;
		iterations = 200;
		int index;
		double indexStep = iterations / 255.0;

		// Ber�knar mandelbrot f�r givet komplext tal med givet antal
		// iterationer
		for (int i = 0; i < iterations; i++) {
			z.mul(z);
			z.add(c);
			if (z.getAbs2() > 2) {
				if (blackAndWhite) {
					color = Color.WHITE;
				} else {
					// Ber�knar vilken av f�rgerna i vektorn "colors" vi vill
					// anv�nda
					index = (int) Math.round(i / indexStep);
					// Anv�nder den f�rgen
					color = colors[index];
				}
				break;
			}
		}
		return color;
	}
}