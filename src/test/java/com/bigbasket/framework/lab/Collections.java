package com.bigbasket.framework.lab;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Collections {

	public static void main(String[] args) {
		//ArrayList
		List names = new ArrayList();
		names.add("Srini");
		names.add(30);
		names.add(57250.23);
		System.out.println("ArrayList contains are: "+names);
		
		//LinkedList
		List animals = new LinkedList();
		animals.add("Dog");
		animals.add("Cat");
		animals.add("Tiger");
		System.out.println("LinkedList contains are: "+animals);

	}

}
