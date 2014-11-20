public class Complex {
	private double re;
	private double im;

	/** Skapar en komplex variabel med realdelen re och imaginärdelen im */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/** Tar reda på realdelen */
	public double getRe() {
		return re;
	}

	/** Tar reda på imaginärdelen */
	public double getIm() {
		return im;
	}

	/** Tar reda på talets absolutbelopp i kvadrat */
	public double getAbs2(){
		return Math.pow(re, 2) + Math.pow(im, 2);
	}
	
	/** Adderar det komplexa talet c till detta tal */
	public void add(Complex c){
		re += c.getRe();
		im += c.getIm();
	}
	
	/** Multiplicerar detta tal med det komplexa talet c */
	public void mul(Complex c){
		double tempRe = re*c.getRe() - im*c.getIm();
		im = im*c.getRe() + re*c.getIm();
		re = tempRe;
	}
	

}
