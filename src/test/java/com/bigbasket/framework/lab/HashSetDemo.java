package com.bigbasket.framework.lab;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class HashSetDemo {

	public static void main(String[] args) {
		HashSet<String> myHashSet = new HashSet<String>();
		myHashSet.add("Srini");
		myHashSet.add("Hema");
		myHashSet.add("Srini");
		myHashSet.add("XXX");
		myHashSet.add("YYY");
		System.out.println(myHashSet.size());
		Iterator itr = myHashSet.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
		myHashSet.clear();
		System.out.println(myHashSet.size());
		LinkedHashSet<String> myLinkedHashSet = new LinkedHashSet<String>();
		myLinkedHashSet.add("X");
		myLinkedHashSet.add("Y");
		myLinkedHashSet.add("Z");
		Iterator itr1 = myLinkedHashSet.iterator();
		while(itr1.hasNext()){
			System.out.println(itr1.next());
		}
		
		Set<String> myTreeSet = new TreeSet<String>();
		myTreeSet.add("Z");
		myTreeSet.add("A");
		myTreeSet.add("B");
		System.out.println(myTreeSet);
		
		SortedSet<String> mySortedSet = new TreeSet<String>();
		mySortedSet.add("C");
		mySortedSet.add("B");
		mySortedSet.add("D");
		System.out.println(mySortedSet);
	}

}
