package intervalset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiIntervalSet<L> implements IntervalSet<L>{
	private final Map<L,Map<Long,Long>> LMap = new HashMap<L,Map<Long,Long>>();

	// Abstraction function:
    //   将<L，<long,long>集合>映射到时间段上
    // Representation invariant:
    //   LMap
    // Safety from rep exposure:
    //  LMap用private final修饰的，表示信息是受到保护的
    //   private 表示这个区域只能由同类进行访问；而final确保了该变量的索引不会被更改
    //   防止外界通过对内部引用导致表示泄露
	
	@Override
	/*
	 * 参数：start表示lable对应时段起点，end表示lable对应时段终点，lable可以对应employee
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：插入一个lable对应的时间段<start,end>
	 * 返回值：插入成功返回true，否则返回false
	 */
	public boolean insert(long start, long end, L lable) {
		//new code for courseApp
		for(L e : this.LMap.keySet()) {
			for(long f : this.periods(e).keySet()) {
				if(f == start && end == this.periods(e).get(f))
					return false;
			}
		}
		// TODO Auto-generated method stub
		if(this.LMap.keySet()!=null) {
		for(L e : this.LMap.keySet()) {
			if(e.toString().equals(lable.toString())) {
				this.LMap.get(e).put(start, end);
				return true;
			}
		}
		}
		Map<Long,Long> A = new HashMap<Long,Long>();
		A.put(start, end);
		LMap.put(lable, A);
		return true;
	}

	@Override
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：删除时间轴上所有以lable为标签的时间片段
	 * 返回值：删除成功返回true，否则返回false
	 */
	public boolean remove(L lable) {
		// TODO Auto-generated method stub
		for(L e : this.LMap.keySet()) {
			if(e.toString().equals(lable.toString())) {
				this.LMap.remove(e);
				return true;
			}
		}
		return false;
	}

	/*
	 * 参数：无
	 * 约束：无
	 * 函数功能：显示时间轴上所有的标签
	 * 返回值：所有时间片段的标签序列
	 */
	@Override
	public ArrayList<L> lables() {
		// TODO Auto-generated method stub
		ArrayList<L> A = new ArrayList<L>();
		for(L e : LMap.keySet()) {
			A.add(e);
		}
		return A;
	}  
	
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：返回lable在实践中轴上所有时间段的<start,end>
	 * 返回值：所有以lable为标签的时间片段的起始终止时间图
	 */
	public Map<Long,Long> periods(L lable){ 
		for(L e : LMap.keySet()) {
			if(e.toString().equals(lable.toString()))
				return LMap.get(e);
		}
		return LMap.get(null);
	}

	@Override
	public long start(L lable) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long end(L lable) {
		// TODO Auto-generated method stub
		return 0;
	}
}
