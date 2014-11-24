package test;

import java.awt.Color;

import se.lth.cs.window.SimpleWindow;

public class TestPrintGradient {
	public static void main(String[] args) {
		Color[] gradient = new Color[256];
		SimpleWindow w = new SimpleWindow(300, 300, "TestGradient");

		for (int i = 0; i < gradient.length; i++) {
			gradient[i] = new Color(46, (int)(204/(double)255*i), 113);
			w.setLineColor(gradient[i]);
			w.moveTo(i, 0);
			w.lineTo(i, 255);
			
//			rgb(46, 204, 113)
//			rgb(142, 68, 173)
			
			
		}

	}
}
