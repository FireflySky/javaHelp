package com.example.sufimage;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

/**
 * 获取网络请求
 * @author Administrator
 *
 */
public class HttpReques {
	public static final String TAG="MyTag";

	/**
	 * 获取图片
	 * @return String
	 */
	public void getImageList(RequestQueue queues,final SharedPreferences shar){
		final SharedPreferences.Editor editor=shar.edit();
	      
	    	StringRequest request=new StringRequest(Method.POST,"http://225b8u4018.iask.in/JavaHelps/servlet/HttpReques",new
	    			Response.Listener<String>(){

				@Override
				public void onResponse(String response) {				
					   editor.putString("RequesTest",response);	
					   editor.commit();
					   editor.clear();
				}
	    		}, new Response.ErrorListener() {//请求失败    				
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				}){
	    	};
	    	//设置tag标签
	    	request.setTag(TAG);
	    	//加入请求队列
	    	queues.add(request);
	    	
	}
		
	/**
	 * 获取图片
	 * @return bitmap
	 */
	private void getImage(RequestQueue queues,final String imgName,final SharedPreferences shar){
		ImageRequest imageRequest = new ImageRequest(  
    	        "http://225b8u4018.iask.in/JavaHelps/"+imgName+".png", 
    	        new Response.Listener<Bitmap>() {  
    	            @Override  
    	            public void onResponse(Bitmap response) {   
    	            	DataStorage datas=new DataStorage(0);
    	            	datas.dataImg(response, imgName, shar);
    	            } 
    	        }, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
    	            @Override  
    	            
    	            public void onErrorResponse(VolleyError error) {            	
    	            }  
    	        });  
       queues.add(imageRequest);	
	}
	/**
	 * 将图片保存
	 * @return
	 */
	public void getImageArr(RequestQueue queues,SharedPreferences shar){	
		String str=shar.getString("RequesTest", "");
		try {
			String[] imgName=str.substring(0,str.indexOf("*")).split(",");

			for(int i=0;i<imgName.length;i++){
				
				this.getImage(queues,imgName[i],shar);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
