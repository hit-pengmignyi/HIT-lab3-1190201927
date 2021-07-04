package dutyroster;

import java.util.ArrayList;

import intervalset.CommonIntervalSet;

public class DutyIntervalSet<L> extends CommonIntervalSet<L>{
	private final ArrayList<L> employeelist = new ArrayList<L>();
	
	/*
	 * 参数：无
	 * 约束：无
	 * 函数功能：获得员工表
	 * 返回值：员工的序列
	 */
	public ArrayList<L> lablelist(){
		return this.employeelist;
	}
	
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：将员工加入员工表
	 * 返回值：当加入成功时返回true，否则返回false
	 */
	public boolean insertemployee(L lable) {
		for(L e : employeelist) {
			if(e.toString().equals(lable.toString())){
				return false;
			}
		}
		employeelist.add(lable);
		return true;
	}
	
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：移除指定的员工
	 * 返回值：当移除成功时返回true，否则返回false
	 */
	public boolean removeemployee(L lable) {
		for(L e : employeelist) {
			/*先删除排班表*/
			if(e.toString().equals(lable.toString())){
				super.remove(e);
				employeelist.remove(e);
				return true;
			}
		}
		return false;
	}
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：调用父类的remove
	 * 返回值：与父类remove返回结果相同
	 */
	public boolean remove(L lable) {
		return super.remove(lable);
	}
	/*
	 * 参数：start表示lable对应时段起点，end表示lable对应时段终点，lable可以对应employee
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：调用父类insert
	 * 返回值：与父类insert结果相同
	 */
	public boolean insert(long start, long end, L lable) {
		return super.insert(start, end, lable);
	}
	/*
	 * 参数：无
	 * 约束：无
	 * 函数功能：调用父类toString
	 * 返回值：与父类toString相同
	 */
	public String toString() {
		return super.toString();
	}


	
}