package iotask;

public class Task3 {

	/**利用程序复制 test.txt 为 test1.txt
	 * @param args
	 */
	public static void main(String[] args) {
		String txt=Task2.read();
		Task1.writer("src/message/test1.txt");
	}

}
