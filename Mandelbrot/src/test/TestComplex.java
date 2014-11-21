package test;

import mandelbrot.Complex;

public class TestComplex {

	public static void main(String[] args) {
		Complex z = new Complex(0, 0);
		Complex c = new Complex(-0.4, 0.4);
		
		for(int i=0; i<8; i++){
			z.mul(z);
			z.add(c);
			System.out.println(z.getRe() + " + " + z.getIm() + "i");
		}
	}

}
