package com.example.okHttp;
/**
 * 监听下载状态的回调接口
 * @author Administrator
 *
 */
public interface DownLoadDao {
	/**
	 * 通知下载进度
	 * @param progress
	 * @return
	 */
	int onProgress(int progress);
	/**
	 * 通知下载成功事件
	 */
	void onSunccess();
	/**
	 * 通知下载失败事件
	 */
	void onFailed();
	/**
	 * 通知下载暂停事件
	 */
	void onPaused();
	/**
	 * 通知下载取消事件
	 */
	void onCanceled();
}
