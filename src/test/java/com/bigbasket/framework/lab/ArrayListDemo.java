package com.bigbasket.framework.lab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArrayListDemo {

	public static void main(String[] args) {
		List myList = new ArrayList(3);
		myList.add("Srini");
		myList.add(null);
		myList.add(3);
		System.out.println(myList);
		myList.add(3,4.75);
		System.out.println(myList.size());
		System.out.println(myList);
		myList.remove(1);
		System.out.println(myList);
		Collection c = new ArrayList(4);
		c.add("Hema");
		c.add(9);
		myList.addAll(c);
		System.out.println(myList);
		Iterator iterator = myList.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		myList.retainAll(c);
		System.out.println(myList);
	}

}
