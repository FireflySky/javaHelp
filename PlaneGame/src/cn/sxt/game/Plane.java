package cn.sxt.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Plane  extends GameObject {
	boolean  left,up,right,down,shell;
	
	boolean  live = true; 
	Graphics  gr;
	
	//初始化
		public  Plane(Image  img, double x, double y){
			this.img = img;
			this.x = x;
			this.y = y;
			this.speed = 4;
			this.width = img.getWidth(null) ;
			this.height = img.getHeight(null);
			
		}
		public Plane(){}
	//飞机移动
	public  void  drawSelf(Graphics  g){
		if(live){//判断飞机是否还活着
				g.drawImage(img, (int)x,(int) y, null);
				
				if(left){
					x -=speed;
				}
				if(right){
					x += speed;
				}
				if(up){
					y -=speed;    //y = y-speed;
				}
				if(down){
					y += speed;
				}
				
		}
		
	}
	
	
	//按键按下事件
	public  void   addDirection(KeyEvent  e){
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		case KeyEvent.VK_Q:
			shell=true;
			break;
		}
	}
	
	//按键抬起事件
		public  void   minusDirection(KeyEvent  e){
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				left = false;
				break;
			case KeyEvent.VK_W:
				up = false;
				break;
			case KeyEvent.VK_D:
				right = false;
				break;
			case KeyEvent.VK_S:
				down = false;
				break;
			case KeyEvent.VK_Q:
				shell=false;
				break;
			}
		}
	
}
