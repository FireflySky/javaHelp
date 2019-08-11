package com.example.okHttp;

import java.io.File;

import com.example.sufimage.MainActivity;
import com.example.util.MyAppLication;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class DownLoadService extends Service{
	
	private DownLoadTask doTask=null;
	private String downLoadUrl=null;
	
	private DownLoadDao dLoadDao=new DownLoadDao() {
		
		
		@Override
		public int onProgress(int progress) {
			doTask=null;
			getNotificationManager().notify(1,getNotification("资源下载", progress));
			return 0;
		}
		
		@Override
		public void onSunccess() {
			doTask=null;
			//下载成功时将前台服务通知关闭，并创建一个下载成功的通知
			stopForeground(true);
			getNotificationManager().notify(1,getNotification("资源下载", -1));
			Toast.makeText(DownLoadService.this, "DownLoad Success", Toast.LENGTH_LONG).show();
		}
		
		@Override
		public void onFailed() {
			doTask=null;
			
		}
		
		@Override
		public void onPaused() {
			doTask=null;
			
		}
		
		@Override
		public void onCanceled() {
			// TODO Auto-generated method stub
			
		}
	};

	
	private DownLoadBinder mBinder=new DownLoadBinder();
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	//通知栏管理对象
	private NotificationManager getNotificationManager(){
		return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	//通知栏对象
	private Notification getNotification(String title,int progress){
		Intent intent=new Intent(this,MainActivity.class);
		PendingIntent pi=PendingIntent.getActivity(this, 0, intent, 0);
		NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
		builder.setContentTitle(title) //设置通知标题
        .setSmallIcon(R.mipmap.sym_def_app_icon)//通知图标
        .setLargeIcon(BitmapFactory.decodeResource(MyAppLication.getContext().getResources(), R.mipmap.sym_def_app_icon)) //设置通知的大图标
        .setContentIntent(pi);
//        .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
//        .setPriority(NotificationCompat.PRIORITY_MAX); //设置通知的优先级：最大
//        .setAutoCancel(false)//设置通知被点击一次是否自动取消
        if(progress>0){
        	builder.setContentText(progress + "%")
        	.setProgress(100, progress, false);       	
        }
		return builder.build();//返回构建的通知对象
	}
	
	public class DownLoadBinder extends Binder{
		public void startDownload(String url){
			if(doTask==null){
				downLoadUrl=url;
				doTask=new DownLoadTask(dLoadDao);
				doTask.execute(downLoadUrl);
				startForeground(1, getNotification("DownLaoding...", 0));
				Toast.makeText(DownLoadService.this, "DownLaoding...", Toast.LENGTH_LONG).show();
			}
		}
		
		public void puaseDownload(){
			if(doTask != null){
				doTask.puasedDownLoad();
			}
		}
		
		public void canceDownlaod(){
			if(doTask != null){
				doTask.canceDwomLoad();
			}else if(downLoadUrl != null){
				//取消下载将下载的文件删除，关闭通知
				File file=new File("/storage/emulated/0/aJavaHelp/day1.png");
				if(file.exists()){
					file.delete();
				}
				getNotificationManager().cancel(1);
				stopForeground(true);
				Toast.makeText(DownLoadService.this, "Canceled...", Toast.LENGTH_LONG).show();
			}
		}
	}
}
