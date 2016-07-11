package com.bigbasket.framework.lab;

public class AbstractionTest extends Abstraction{

	AbstractionTest(){
		super();
		System.out.println("Hello");
	}
	
	public static void main(String[] args) {
		AbstractionTest at = new AbstractionTest();
		at.sub(15, 6);
		at.add(9, 8);
	}

	@Override
	public void add(int a, int b) {
		System.out.println("Result is: "+(a+b));
		
	}

}
