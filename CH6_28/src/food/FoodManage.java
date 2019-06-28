package food;

import java.util.Arrays;

import log.Log;

public class FoodManage {
	private Food food;
	private Food[] foods=new Food[500];
	private int count = 0;
	
	/**
	 * 后端 添加食品
	 * 
	 * @param food
	 * @return
	 */
	public boolean addFood(Food food) {
		long startTime=System.currentTimeMillis();
		if (null == food) {
			return false;
		}
		if (count + 1 >= foods.length) {
			return false;
		}
		for (int i = 0; i < count; i++) {
			if (food.getName().equals(foods[i].getName())) {
				return false;
			}
		}
		foods[count++] = food;
		long endTime=System.currentTimeMillis();
		Log.writerLog("INIF "+Log.date()+" "+Thread.currentThread() .getStackTrace()[1].getMethodName()+" "+(endTime-startTime));
		return true;
	}

	/**
	 * 后端 查询所有食品信息
	 */
	public Food[] queryAll() {
		long startTime=System.currentTimeMillis();
		Food[] f = new Food[count];
		f = (Food[]) Arrays.copyOf(foods, count);
		long endTime=System.currentTimeMillis();
		Log.writerLog("INIF "+Log.date()+" "+Thread.currentThread() .getStackTrace()[1].getMethodName()+" "+(endTime-startTime));
		return f;
	}

	/**
	 * 后端 根据食品名称查询食品信息
	 */
	public Food findFoodByName(String name) {
		long startTime=System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			if (foods[i].getName().equals(name)) {
				food = foods[i];
				return food;
			}
		}
		long endTime=System.currentTimeMillis();
		Log.writerLog("INIF "+Log.date()+" "+Thread.currentThread() .getStackTrace()[1].getMethodName()+" "+(endTime-startTime));
		return null;
	}

	/**
	 * 后端 根据食品名称修改食品信息
	 */
	public boolean upDate(Food food) {
		long startTime=System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			if (foods[i].getName().equals(food.getName())) {
				foods[i].setCount(food.getCount());
				foods[i].setPrice(food.getPrice());
				return true;
			}
		}
		long endTime=System.currentTimeMillis();
		Log.writerLog("INIF "+Log.date()+" "+Thread.currentThread() .getStackTrace()[1].getMethodName()+" "+(endTime-startTime));
		return false;
	}

	/**
	 * 后端 计算所有食品总价
	 */
	public double sumCount() {
		double $count = 0;
		for (int i = 0; i < count; i++) {
			$count += (foods[i].getPrice() * foods[i].getCount());
		}
		return $count;
	}

	/**
	 * 后端 统计所有最贵食品的信息
	 */
	public Food[] priceMax() {
		double Max = null == foods[0] ? 0 : foods[0].getPrice();
		for (int i = 1; i < count; i++) {
			if (Max < foods[i].getPrice()) {
				Max = foods[i].getPrice();
			}
		}

		int length = 0;
		for (int i = 0; i < count; i++) {
			if (foods[i].getPrice() == Max) {
				length++;
			}
		}

		Food[] f = new Food[length];
		int index = 0;
		for (int i = 0; i < count; i++) {
			if (foods[i].getPrice() == Max) {
				f[index++] = foods[i];
			}
		}
		return f;
	}

	/**
	 * 后端 将食品排序
	 */
	public void sort(){
		for (int i = 0; i < count-1; i++) {
			for (int j = 0; j < count-1-i; j++) {
				if(foods[j].getPrice()>foods[j+1].getPrice()){
					Food f=foods[j];
					foods[j]=foods[j+1];
					foods[j+1]=f;					
				}else if(foods[j].getPrice()==foods[j+1].getPrice()){
					if(foods[j].getCount()<foods[j+1].getCount()){
						Food f=foods[j];
						foods[j]=foods[j+1];
						foods[j+1]=f;
					}							
				}
			}
		}
	}
}
