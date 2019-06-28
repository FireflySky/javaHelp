package iotask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * 从控制台输入一些字符串，每次输入的字符串加上当前时间累加之前的内容到 日志文件”log.txt”中去。 输入后换行，直到用户输入exit才退出
 * 
 * @author Administrator
 * 
 */
public class Task6 {

	/**
	 * @param args
	 */
	static Scanner sc;
	static File f;
	static BufferedWriter out;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		f = new File("src/message/log.txt");

		while (true) {
			System.out.println("请输入一行字符，退出请输入exit");
			String str = sc.next();
			if(!str.equals("exit")){
				writer(str);
			}else{
				System.out.println("程序已停止");
				return;
			}
		}

	}

	private static void writer(String str) {
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),"GBK"));
			out.write(str);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
