package com.bigbasket.framework.lab;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {

	public static void main(String[] args) {
		List myLinkedList = new LinkedList();
		List myArrayList = new ArrayList();
		myArrayList.add("Srini");
		myArrayList.add(30);
		myLinkedList.addAll(myArrayList);
		System.out.println(myLinkedList.get(0));
		System.out.println(myLinkedList);

	}

}
