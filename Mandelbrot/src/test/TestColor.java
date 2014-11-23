package test;

import java.awt.Color;

import se.lth.cs.ptdc.fractal.MandelbrotGUI;

public class TestColor {
	public static void main(String[] args) {
		MandelbrotGUI gui = new MandelbrotGUI();
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
		Color a = new Color(0, 0, 255);
		Color b = new Color(255, 0, 0);
		
		Color[][] picture = new Color[100][200];
		for(int i=0; i<200; i++){
			for(int j=0; j<100; j++){
				
				//picture[j][i] = Color.getHSBColor(i/(float)200, 1, 1);
				picture[j][i] = colors[(int)(i/(float)10)];
			}
		}
		gui.putData(picture, 3, 3);
	}

}
