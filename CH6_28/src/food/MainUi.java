package food;

import java.util.Scanner;

import log.Log;

public class MainUi {
	private Scanner sc;
	private String name;
	private double price;
	private int count;
	private Food food;
	private static Food[] foods;
	private static FoodManage fm;
	
	public static void main(String[] args) {
		MainUi m=new MainUi();
		fm=new FoodManage();
		m.start();
	}

	public void start() {
		System.out.println("欢迎使用食品管理系统");
		sc = new Scanner(System.in);
		while (true) {
			this.init();
			String open = sc.next();
			switch (open) {
			case "1":
				this.addFood();
				break;
			case "2":
				this.queryAll();
				break;
			case "3":
				this.findFoodByName();
				break;
			case "4":
				this.upDate();
				break;
			case "5":
				this.sumCount();
				break;
			case "6":
				this.priceMax();
				break;
			case "7":
				this.sort();
				break;
			case "8":
				System.out.println("谢谢您的使用");
				Log.cuntInfo();
				System.exit(0);
			default:
				System.out.println("输入有误");
				break;
			}

		}
	}
	/**
	 * 初始化方法
	 */
	private void init() {
		System.out.println("功能：\n1.添加食品 ");
		System.out.println("2.查询显示所有食品 ");
		System.out.println("3.根据食品名称查询食品信息");
		System.out.println("4.根据食品名称修改食品信息");
		System.out.println("5.计算所有食品总价");
		System.out.println("6.统计最贵食品的信息");
		System.out.println("7.将食品按价格进行升序排序");
		System.out.println("8.退出");
	}
	/**
	 * 前端 食品添加方法
	 */
	private void addFood(){
		System.out.println("请输入食品名称");
		name=sc.next();
		System.out.println("请输入食品价格");
		price=sc.nextDouble();
		System.out.println("请输入食品数量");
		count=sc.nextInt();
		food=new Food(name, price, count);
		boolean flag=fm.addFood(food);
		if(flag){
			System.out.println("添加成功");
		}else{
			System.out.println("添加失败");
		}
	}
	/**
	 *前端  查询所有食品信息
	 */
	private void queryAll(){
		foods=fm.queryAll();
		this.print(foods);
	}
	/**
	 * 前端 根据食品名称查询食品信息
	 */
	private void findFoodByName(){
		System.out.println("请输入食品名称");
		name=sc.next();
		food=fm.findFoodByName(name);
		this.print(foods);
	}
	/**
	 * 前端 根据食品名称修改食品信息
	 */
	private void upDate(){
		System.out.println("请输入食品名称");
		name=sc.next();
		if(null==fm.findFoodByName(name)){
			System.out.println("找不到该食品");
			return;
		}
		System.out.println("请输入食品价格");
		price=sc.nextDouble();
		System.out.println("请输入食品数量");
		count=sc.nextInt();
		food=new Food(name, price, count);
		boolean flag=fm.upDate(food);
		if(flag){
			System.out.println("修改成功");
		}else{
			System.out.println("修改失败");
		}
	}
	/**
	 * 前端 计算食品总价
	 */
	private void sumCount(){
		double $count=fm.sumCount();
		System.out.println("食品总价为："+$count);
	}
	/**
	 * 统计所有最贵的食品的信息
	 */
	private void priceMax(){
		foods=fm.priceMax();
		this.print(foods);
	}
	private void sort(){
		fm.sort();
		this.print(foods);
	}
	/**
	 * 打印方法
	 * @param foods
	 */
	private void print(Food...foods){
		if(null==foods || foods.length<=0){
			System.out.println("找不到食品");
		}else{
			System.out.println("食品名称\t食品单价\t食品库存");
			for(int i=0;i<foods.length;i++){
				System.out.print(foods[i].getName()+"\t");
				System.out.print(foods[i].getPrice()+"\t");
				System.out.println(foods[i].getCount());
			}
		}
	}
}
