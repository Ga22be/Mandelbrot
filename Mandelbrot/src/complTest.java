
public class complTest {
	public static void main(String[] args) {
		Complex a = new Complex(0.4, -0.8);
		Complex b = new Complex(1, 2);
		b.add(a); // b = 1.4 + 1.2i
		System.out.println(b.getRe() + " + " + b.getIm());
		b.mul(a); // b = (1.4 + 1.2i) * (0.4 - 0.8i) = 1.52 - 0.64i
		System.out.println(b.getRe() + " + " + b.getIm());
	}
}
