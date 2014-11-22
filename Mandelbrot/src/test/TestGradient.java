package test;

import java.awt.Color;

public class TestGradient {
	public static void main(String[] args) {
		int iterations = 1000;
		// Ändras för att representera avklarade iterationer
		int itersCompleted = 500;
		Color[] colors = new Color[256];
		for(int i = 0; i < 256; i++){
			colors[i] = new Color(211, i, 0);
		}
		
		
		int index = 0;
		double step;
		step = iterations/255.0;
		System.out.println("Intervall mellan färgbyte: " + step);
		index = (int) Math.round(itersCompleted/step);
		System.out.println("Vilket index motsvarar detta: " + index);
	}
}
