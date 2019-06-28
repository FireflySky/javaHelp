package iotask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Task1 {

	/**
	 * 在指定的路径下新建一个 test.txt 文件
	 * 
	 * @param args
	 */
	static BufferedWriter out = null;

	public static void main(String[] args) {
		writer("src/message/test.txt");
	}
	public static void writer(String path){
		File f = new File(path);

		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			out = new BufferedWriter(new FileWriter(f));
			out.write("Java是一种可以撰写跨平台应用软件的面向对象的程序设计语言，是由Sun Microsystems公司于");
			out.newLine();
			out.write("1995年5月推出的Java程序设计语言和Java平台（即JavaSE, JavaEE, JavaME）的总称。Java 技术具有");
			out.newLine();
			out.write("卓越的通用性、高效性、平台移植性和安全性，广泛应用于个人PC、数据中心、游戏控制台、科学超级");
			out.newLine();
			out.write("计算机、移动电话和互联网，同时拥有全球最大的开发者专业社群。在全球云计算和移动互联网的产业");
			out.newLine();
			out.write("环境下，Java更具备了显著优势和广阔前景。");
			System.out.println("Assecc");
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
