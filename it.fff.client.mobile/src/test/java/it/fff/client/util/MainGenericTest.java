package it.fff.client.util;

public class MainGenericTest {

	public static void main(String[] args) {
		
		System.out.println("2.358183899999972 -> "+round(2.358183899999972,5));
		System.out.println("2.358187899999972 -> "+round(2.358187899999972,5));
		System.out.println("2.35818 -> "+round(2.35818,5));
		System.out.println("2.3581 -> "+round(2.3581,5));
		System.out.println("2.35 -> "+round(2.35,5));
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}	

}
