package test2;

import java.util.Comparator;
import java.util.TreeSet;

public class compar {
	public static void main(String[] args) {
		TreeSet<Student> tree = new TreeSet<>(new Comparator<Student>() {

			public int compare(Student o1, Student o2) {		
				return o2.getAge() - o1.getAge();
			}
		});	
		tree.add(new Student("t1", 10));
		tree.add(new Student("t2", 5));
		tree.add(new Student("t3", 7));
		tree.add(new Student("t4", 9));
		tree.add(new Student("t5", 13));
		tree.add(new Student("t6", 1));
		for (Student student : tree) {
			System.out.println(student);
		}
	}
}
