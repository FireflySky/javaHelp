package task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 序列化
 * @author Administrator
 * 
 */
public class Task {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		writer();
//		raed();
	}

	static File f = new File("src/message/task2.dat");
	static FileInputStream put;
	static FileOutputStream out;
	static ObjectOutputStream objout;
	static ObjectInputStream objput;

	private static void writer() {
		ArrayList<Student> stu = new ArrayList<>();
		stu.add(new Student("张三", 14));
		stu.add(new Student("张1", 10));
		stu.add(new Student("张2", 16));
		stu.add(new Student("张3", 38));
		stu.add(new Student("张4", 65));
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			out = new FileOutputStream(f);
			objout = new ObjectOutputStream(out);
			objout.writeObject(stu);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (objout != null)
					objout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unchecked")
	private static void raed() {
		try {
			put = new FileInputStream(f);
			objput = new ObjectInputStream(put);
			ArrayList<Student> student = new ArrayList<>();
			student=(ArrayList<Student>)objput.readObject();
			for (Student s : student) {
				System.out.println(s.getName()+":"+s.getAge());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (put != null)
					put.close();
				if (objput != null)
					objput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
