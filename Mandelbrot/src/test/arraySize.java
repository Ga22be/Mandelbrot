package test;

public class arraySize {
	public static void main(String[] args) {
//		int res = 5;
//		int width = 700;
//		int height = 612;
//		int calcWidth;
//		int calcHeigth;
//
//		int diffX = 700 % res;
//		int diffY = 612 % res;
//		if(diffX != 0){
//			calcWidth = 700 + (res - diffX);
//		} else {calcWidth = 700;}
//		if(diffY != 0){
//			calcHeigth = 612 + (res - diffY);
//		} else {calcHeigth = 612;}
//
//		System.out.println(width + ":" + height);
//		System.out.println(diffX + ":" + diffY);
//		System.out.println(calcWidth + ":" + calcHeigth);
		int val = 612;
		int res = 9;
		
		int calc;
		int diff =  val % res;
		if(diff != 0){
			calc = val + (res - diff);
		} else {calc = val;}
		calc = calc/res;
		
		System.out.println(calc);
	}
}
