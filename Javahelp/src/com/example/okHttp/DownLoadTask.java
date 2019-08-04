package com.example.okHttp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.AsyncTask;

public class DownLoadTask extends AsyncTask<String, Integer, Integer> {

	public static final int TYPE_SUCCESS = 0;
	public static final int TYPE_FAILED = 1;
	public static final int TYPE_PAUSED = 2;
	public static final int TYPE_CANCELED = 3;

	private DownLoadDao downLoadDao;

	private boolean isCanceled = false;
	private boolean isPaused = false;
	private int lastProgress = 0;

	public DownLoadTask(DownLoadDao dao){
		this.downLoadDao=dao;
	}
	
	
	/**
	 * 后台执行下载逻辑
	 */
	@Override
	protected Integer doInBackground(String... params) {
		InputStream put = null;
		RandomAccessFile savedFile = null;
		File file = null;

		try {
			long length = 0;// 记录已下载的文件长度
			String downLoadUrl = params[0];
			file = new File("下载的图片的地址");
			if (file.exists()) {
				length = file.length();
			}

			long countLenght = getCountLength(downLoadUrl);// 获取文件总长度
			if (countLenght == 0) {
				return TYPE_FAILED;
			} else if (countLenght == length) {// 文件下载完成
				return TYPE_SUCCESS;
			}

			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.addHeader("RANGE", "bytes=" + length + "-")// 断点下载，指定从哪开始下载
					.url(downLoadUrl).build();

			Response response = client.newCall(request).execute();
			if (response != null && response.isSuccessful()) {
				put = response.body().byteStream();
				savedFile = new RandomAccessFile(file, "re");
				savedFile.seek(length);// 跳过已下载字节

				byte[] b = new byte[1024];
				int total = 0;// 下载进度
				int len;

				while ((len = put.read(b)) != -1) {
					if (isCanceled) {// 判断是否已经取消下载
						return TYPE_CANCELED;
					} else if (isPaused) {// 判断是否暂停了下载
						return TYPE_PAUSED;
					} else {
						total += len;
						savedFile.write(b, 0, len);
						// 计算下载的百分比
						int progress = (int) ((total + length) * 100 / countLenght);
					}
				}
				response.close();
				return TYPE_SUCCESS;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (put != null)
					put.close();
				if (savedFile != null)
					savedFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return TYPE_FAILED;
	}

	/**
	 * 更新下载进度
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		int progress = values[0];
		if (progress > lastProgress) {
			downLoadDao.onProgress(progress);
			lastProgress = progress;
		}
	}

	/**
	 * 通知最终的下载结果
	 */
	@Override
	protected void onPostExecute(Integer result) {
		switch (result) {
		case TYPE_SUCCESS:
				downLoadDao.onSunccess();
			break;
		case TYPE_FAILED:
				downLoadDao.onFailed();
			break;
		case TYPE_PAUSED:
				downLoadDao.onPaused();
			break;
		case TYPE_CANCELED:
				downLoadDao.onCanceled();
			break;
		default:
			break;
		}
	}
	//暂停下载
	public void puasedDownLoad(){
		isPaused=true;
	}
	//取消下载
	public void canceDwomLoad(){
		isCanceled=true;
	}
	
	// 获取下载文件的大小
	private long getCountLength(String url) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();
		if (response != null && response.isSuccessful()) {
			long countLength = response.body().contentLength();
			response.body().close();
			return countLength;
		}
		return 0;

	}
}
