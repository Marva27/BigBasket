package com.bigbasket.framework.lab;

public abstract class Abstraction {
	
	Abstraction(){
		System.out.println("Hi");
	}
	
	public abstract void add(int a, int b);
	
	public void sub(int a, int b){
		System.out.println("Result is: "+(a-b));
	}

}
