package test;

public class testPixelJump {
	public static void main(String[] args) {
		int res = 5;
		int jump;
		
		for(int i = 0; i < 4; i++){
			jump = res/2 + i*res;
			System.out.println(jump);
		}
				
	}
}
