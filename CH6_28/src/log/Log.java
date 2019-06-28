package log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;

/**
 * 打印日志类
 * 
 * @author Administrator
 * 
 */
public class Log {
	static BufferedWriter out;
	static BufferedReader put;
	static File f=new File("src/message/ThreadLog.txt");
	public static void writerLog(String log) {
		try {
			if(!f.exists()){
				f.createNewFile();
			}
			out = new BufferedWriter(new FileWriter(f, true));
			
			out.write(log);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 获取时间
	private static Date date = new Date();
	private static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String date() {
		return sd.format(date);
	}

	// 分析统计
	private static ArrayList<String> name=null;
	private static ArrayList<Long> time=null;
	public static void cuntInfo() {
		try {
			put = new BufferedReader(new FileReader(f));
			String log=null;
			name=new ArrayList<>();
			time=new ArrayList<>();
			while ((log=put.readLine())!=null) {
				if(!(log.startsWith("统计"))){
					name.add(log.split(" ")[3]);
					time.add(Long.valueOf(log.split(" ")[4]));
				}
			}
			String[] s=(String[])name.toArray(new String[name.size()]);
			Long[] lon=(Long[]) time.toArray(new Long[name.size()]);
			String n=countSum(s);
			String l=countTime(lon);
			writerLog("统计：调用次数最多的方法 "+n+" 耗时最多的方法 "+l);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (put != null)
					put.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/*
	 * 
	 * 信息类型 调用时间 方法名 耗时
	 */
	//统计方法次数
	private static String countSum(String[] name){
		TreeSet<Cout> sum=new TreeSet<>(new Comparator<Cout>() {
			@Override
			public int compare(Cout o1, Cout o2) {
				// TODO Auto-generated method stub
				return o1.getCount()-o2.getCount();
			}
		});	
		for (int i = 0; i < name.length; i++) {
			int count=1;//方法调用次数
			for (int j= i+1;  j< name.length; j++) {
				if(name[i]!=null && name[i].equals(name[j])){
					name[j]=null;
					count++;
				}
			}
			sum.add(new Log().new Cout(name[i],count));
		}
		return sum.last().getNam();
	}

	//统计耗时最多的方法
	private static String countTime(Long[] l){
		Arrays.sort(l);
		int index=0;
		for (Long L:(Long[])time.toArray(new Long[time.size()])) {
			if(L==l[l.length-1]){
				break;
			}
			index++;
		}
		return name.get(index);
	}
	//用于排序执行最多次数的方法
	private class Cout{
		private String nam;
		private int count;
		
		public String getNam() {
			return nam;
		}

		public int getCount() {
			return count;
		}

		public Cout(String nam, int count) {
			super();
			this.nam = nam;
			this.count = count;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + count;
			result = prime * result + ((nam == null) ? 0 : nam.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cout other = (Cout) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (count != other.count)
				return false;
			if (nam == null) {
				if (other.nam != null)
					return false;
			} else if (!nam.equals(other.nam))
				return false;
			return true;
		}

		private Log getOuterType() {
			return Log.this;
		}		
	}
}

