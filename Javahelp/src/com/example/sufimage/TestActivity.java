package com.example.sufimage;

import com.example.okHttp.DownLoadService;
import com.example.okHttp.DownLoadService.DownLoadBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class TestActivity extends Activity {

	Button bnt1,bnt2,bnt3;
	private DownLoadService.DownLoadBinder down;
	private ServiceConnection connection=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			down=(DownLoadBinder) arg1;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		bnt1=(Button) findViewById(R.id.button1);
		bnt2=(Button) findViewById(R.id.button2);
		bnt3=(Button) findViewById(R.id.button3);
		
		Intent intent=new Intent(TestActivity.this,DownLoadService.class);
		startService(intent);
		bindService(intent, connection, BIND_AUTO_CREATE);
		
		bnt1.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				if(down==null)return;
				String url="http://225b8u4018.iask.in/JavaHelps/day1.png";
				
				down.startDownload(url);
			}
		});
		bnt2.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				if(down==null)return;
				down.puaseDownload();
			}
		});
		bnt3.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				if(down==null)return;
				down.canceDownlaod();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(connection);
	}
}
