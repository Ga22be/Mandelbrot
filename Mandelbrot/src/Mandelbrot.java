import java.awt.Color;

import se.lth.cs.ptdc.fractal.MandelbrotGUI;



public class Mandelbrot {

	public static void main(String[] args) {
		// TODO main metoden, skapar objekt och hanterar rendrering
		MandelbrotGUI gui = new MandelbrotGUI();
		
		Generator generator = new Generator();
		
		while (true) {
			switch (gui.getCommand()) {
			case MandelbrotGUI.RENDER:
				//Todo render
				generator.render(gui);
				break;
			case MandelbrotGUI.RESET: 
				gui.resetPlane();
				generator.render(gui);
				break;
			case MandelbrotGUI.QUIT:
				//TODO
				break;
			case MandelbrotGUI.ZOOM:
				//TODO zoom;
				generator.render(gui);
				break;
			}
		}
	}
}
