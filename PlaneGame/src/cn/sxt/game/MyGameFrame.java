package cn.sxt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * 游戏主窗口类
 * @author 
 *
 */
public class MyGameFrame  extends  Frame {
	
	Image   planeImg  = GameUtil.getImage("images/plane.png");
	Image   bg  = GameUtil.getImage("images/bg.jpg");
	
	Plane   plane = new Plane(planeImg,250,250);
	Shell[]   shells = new Shell[50];/**子弹*/
	
	Explode   bao ;
	Date  startTime = new Date();
	Date  endTime;
	int period;   //移动距离
	
	@Override
	public void paint(Graphics g) {		//画笔，自动调用
		Color   c =  g.getColor();
		g.drawImage(bg, 0, 0, null);
		
		plane.drawSelf(g);  //绘制飞机
		
		//绘制子弹
		for(int i=0;i<shells.length;i++){
			shells[i].draw(g);
			
			//爆炸
			boolean  peng = shells[i].getRect().intersects(plane.getRect());
			if(peng){
				plane.live = false;
				if(bao ==null){
					bao  = new Explode(plane.x, plane.y);		
					endTime = new Date();
					period = (int)((endTime.getTime()-startTime.getTime())/1000);
				}
				bao.draw(g);
			}
			
			//字体设置
			if(!plane.live){
				g.setColor(Color.red);
				Font   f  =  new Font("微软雅黑", Font.BOLD, 50);
				g.setFont(f);
				g.drawString("用时"+period+"秒", (int)Constant.GAME_WIDTH/2-100, (int)Constant.GAME_HEIGHT/2);
			}
			
		}
		
		g.setColor(c);
	}
	
	
	//线程
	class  PaintThread  extends  Thread  {
		@Override
		public void run() {
			while(true){
				repaint();		//重新绘制
				
				try {
					Thread.sleep(40);   	//1s=1000ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
		}
		
	}
	
	//键盘的按键事件
	class   KeyMonitor extends  KeyAdapter  {

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
		
		
	}
	
	
	/**
	 * 绘制主窗口
	 */
	public  void  launchFrame(){
		this.setTitle("飞机小游戏");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(300, 300);//位置
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		new PaintThread().start();	//开启线程
		addKeyListener(new KeyMonitor());   //给窗口注册键盘监听事件
		
		
		//创建子弹对象
		for(int i=0;i<shells.length;i++){
			shells[i] = new Shell();
		}
		
	}
	
	public static void main(String[] args) {
		MyGameFrame  f = new MyGameFrame();
		f.launchFrame();
	}
	//防止Frame闪烁
	private Image offScreenImage = null;
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//������Ϸ���ڵĿ�Ⱥ͸߶�
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}
	
}
