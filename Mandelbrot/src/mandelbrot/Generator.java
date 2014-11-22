package mandelbrot;

import java.awt.Color;

import se.lth.cs.ptdc.fractal.MandelbrotGUI;

public class Generator {
	Color[] colors = new Color[256];
	int iterations;

	public Generator() {
		// TODO skapa Color[][] picture;
		// gÃ¥r detta ens nu nÃ¤r vi Ã¤ndrar storlek pÃ¥ den?
		// svar nej, den ändras var gång vi ändrar resolution alltså kan den
		// inte skapas direkt
		for (int i = 0; i < 256; i++) {
			colors[i] = new Color(211, i, 0);
		}
	}

	/** Renderar mandelbrot och ritar upp det i MandelbrotGUI */
	public void render(MandelbrotGUI gui) {
		// Avaktiverar möjligheterna till input i gui uför att förhindra att en
		// ändring får renderingen att bugga ur
		gui.disableInput();

		int pixelWidth;
		int pixelHeight;
		// Tolkar input: Vilken upplösning önskas?
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
		 * delar up texten i extrarutan i olika kommandon med olika vÃ¤rden till
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
						.println("Texten i extrarutan innehÃ¥ller felaktiga komandon");
			}
		}
		// klar med att tolka input

		/**
		 * Anropar mesh fï¿½r att fï¿½ en matris med representationer av
		 * komplexa tal fï¿½r var enskilld pixel pï¿½ ritytan.
		 */
		Complex complex[][] = mesh(gui.getMinimumReal(), gui.getMaximumReal(),
				gui.getMinimumImag(), gui.getMaximumImag(), gui.getWidth(),
				gui.getHeight());
		System.out.println("done with mesh");

		/**
		 * Skapar en fï¿½rgmatris som representerar pixlarna som ska ritas i
		 * gui. Pixlarnas storlek sätts i fï¿½rhï¿½llande till upplï¿½sningen.
		 */
		int heightSize = getMaxArrayIndex(gui.getHeight(), pixelHeight);
		int widthSize = getMaxArrayIndex(gui.getWidth(), pixelHeight);
		Color[][] picture = new Color[heightSize][widthSize];
		// System.out.println(heightSize + ":" + widthSize);
		System.out.println("done with picture[][]");

		/** Generate colorarray from complexarray */
		/** Ger var och en av pixlarna i färgmatrisen "picture" en färg */
		for (int y = 0; y < heightSize; y++) {
			// y-vï¿½rdet fï¿½r den komplexa punkten i mitten av pixeln i
			// fï¿½rgmatrisen
			int jumpY = (pixelHeight / 2) + (y * pixelHeight);

			// Fixar out of bounds exception fï¿½r hï¿½jd
			if (jumpY >= gui.getHeight()) {
				jumpY = gui.getHeight() - 1;
			}

			for (int x = 0; x < widthSize; x++) {
				// x-vï¿½rdet fï¿½r den komplexa punkten i mitten av pixeln i
				// fï¿½rgmatrisen
				int jumpX = (pixelWidth / 2) + (x * pixelWidth);

				// Fixar out of bounds exception fï¿½r bredd
				if (jumpX >= gui.getWidth()) {
					jumpX = gui.getWidth() - 1;
				}

				// Ger pixeln picture sin färg beroende på mandelbrotsmängden
				picture[y][x] = generateMandelColor(complex[jumpY][jumpX],
						blackAndWhite);
			}

		}
		System.out.println("done with rendering");
		// Sänder färgmatrisen och dess pixelstorlek till gui för rendering i
		// fönstret
		gui.putData(picture, pixelWidth, pixelHeight);
		System.out.println("done with putData");

		// Återaktiverar input
		gui.enableInput();
	}

	/** Skapar en matris av komplexa tal som motsvarar pixlarna i ritfÃ¶nstret */
	private Complex[][] mesh(double minRe, double maxRe, double minIm,
			double maxIm, int width, int height) {
		// TODO Ã¤ndra namnet pÃ¥ pixelArray (kanske)
		Complex[][] pixelArray = new Complex[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Beräkna pixeln i frågas relativa avstånd till vänstra kanten
				// av fönstret
				double widthPercent = j / (double) width;
				// Ger re värdet av: minRe (vilket är vänstra kanten) och
				// adderar med avståndet mellan minRe och maxRe multiplicerat
				// med det relativa avståndet till vänstra kanten
				double re = minRe + widthPercent * (maxRe - minRe);

				// Samma procedur fas för den imaginära delen och höjden
				double heightPercent = i / (double) height;
				double im = maxIm - heightPercent * (maxIm - minIm);
				pixelArray[i][j] = new Complex(re, im);
			}
		}
		return pixelArray;
	}

	/**
	 * Berï¿½knar hur mï¿½nga rader/kolumner du bï¿½r skapa i fï¿½rgmatrisen
	 * fï¿½r en viss upplï¿½sning
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

	/** Bestï¿½mmer fï¿½rgen fï¿½r en pixel beroende pï¿½ ett givet komplext tal */
	private Color generateMandelColor(Complex c, boolean blackAndWhite) {
		Complex z = new Complex(0, 0);
		Color color = Color.BLACK;
		iterations = 200;
		int index;
		double indexStep = iterations / 255.0;

		// Beräknar mandelbrot för givet komplext tal med givet antal
		// iterationer
		for (int i = 0; i < iterations; i++) {
			z.mul(z);
			z.add(c);
			if (z.getAbs2() > 2) {
				if (blackAndWhite) {
					color = Color.WHITE;
				} else {
					// Beräknar vilken av färgerna i vektorn "colors" vi vill
					// använda
					index = (int) Math.round(i / indexStep);
					// Använder den färgen
					color = colors[index];
				}
				break;
			}
		}
		return color;
	}
}