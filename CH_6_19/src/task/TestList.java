package task;

import java.util.*;

public class TestList {
	public static void main(String args[]) {
		List list = new ArrayList();
		list.add("Hello");
		list.add("Hello");
		list.add("Learn");
		list.add("Java");
		list.remove("Hello");
		printList(list);
		
//		new Thread(() -> System.out.println("-----")).start();
	}
	public static void printList(List list){
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}