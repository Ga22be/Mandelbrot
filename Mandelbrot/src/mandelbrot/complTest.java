package mandelbrot;

public class complTest {
	public static void main(String[] args) {
		Complex a = new Complex(0.4, -0.8);
		Complex b = new Complex(1, 2);
		b.add(a); // b = 1.4 + 1.2i
		System.out.println(b.getRe() + " + " + b.getIm());
		b.mul(a); // b = (1.4 + 1.2i) * (0.4 - 0.8i) = 1.52 - 0.64i
		System.out.println(b.getRe() + " + " + b.getIm());
		
		System.out.println("-------------------");
		Complex c = new Complex(1, 2);
		Complex d = new Complex(1, 2);
		
		d.mul(c);
		System.out.println(d.getRe() + " + " + d.getIm() + "i");

		d = new Complex(1, 2);
		d.mul(d);
		System.out.println(d.getRe() + " + " + d.getIm() + "i");
		
		//seems to work, maybe?
		
	}
}
