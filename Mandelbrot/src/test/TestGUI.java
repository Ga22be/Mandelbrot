package test;

import java.awt.Color;

import se.lth.cs.ptdc.fractal.MandelbrotGUI;

public class TestGUI {
	public static void main(String[] args) {
		MandelbrotGUI gui = new MandelbrotGUI();
		
		Color[][] picture = new Color[gui.getHeight()-20][gui.getWidth()-20];
		
		for(int x=0; x<gui.getHeight()-20; x++){
			for(int y=0; y<gui.getWidth()-20; y++){
				picture[x][y] = Color.BLUE;
			}
		}
		gui.putData(picture, 1, 1);
		
	}
}
