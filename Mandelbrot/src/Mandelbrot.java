import se.lth.cs.ptdc.fractal.MandelbrotGUI;



public class Mandelbrot {

	public static void main(String[] args) {
		// TODO main metoden, skapar objekt och hanterar rendrering
		MandelbrotGUI gui = new MandelbrotGUI();
		
		Generator generator = new Generator();
		
		boolean rendered = false;
		
		while (true) {
			switch (gui.getCommand()) {
			case MandelbrotGUI.RENDER:
				//Todo render
				generator.render(gui);
				rendered = true;
				break;
			case MandelbrotGUI.RESET:
				gui.resetPlane();
				gui.clearPlane();
				rendered = false;
				break;
			case MandelbrotGUI.QUIT:
				System.exit(0);
				break;
			case MandelbrotGUI.ZOOM:
				if(rendered){
					generator.render(gui);
				}
				break;
			}
		}
	}
}
