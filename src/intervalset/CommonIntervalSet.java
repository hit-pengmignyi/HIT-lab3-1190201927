package intervalset;

import java.util.ArrayList;


public class CommonIntervalSet<L> implements IntervalSet<L> {
	private final ArrayList<period> periodlist = new ArrayList<period>();
	
	// Abstraction function:
    //   将<start,lable,end>映射到时间段上
    // Representation invariant:
    //   periodlist
    // Safety from rep exposure:
    //  LMap用private final修饰的，表示信息是受到保护的
    //   private 表示这个区域只能由同类进行访问；而final确保了该变量的索引不会被更改
    //   防止外界通过对内部引用导致表示泄露
	
	@Override
	/* 
	 * 参数：start表示lable对应时段起点，end表示lable对应时段终点，lable可以对应employee
	 * 约束：需要lable有意义，start与end这里不做要求，会在实现的app中检测。
	 * 函数功能：插入<start,Lable,end>三元组
	 * 返回值：成功加入返回true，否则返回false
	 */
	public boolean insert(long start, long end, L lable) {
		int i = 0;
		period A = new period(start,end,lable);
		/*初次插入*/
		if(periodlist.size() == 0) {
			periodlist.add(A);
			return true;
		}
		if(periodlist.size()==1 && periodlist.get(0).getstart() > end) {
			/*又插入了同样的标签*/
			if(periodlist.get(0).lableequals(lable))
				return false;
			periodlist.add(0, A);
			return true;
		}
		/*以事件发生顺序递增维护事件序列*/
		for(i = 0; i < periodlist.size();i++) {
			/*又插入了同样的标签*/
			if(periodlist.get(i).lableequals(lable)) {
				return false;
			}
			if(i == periodlist.size()-1 && periodlist.get(i).getend() < start) {
				periodlist.add(A);
				return true;
			}
				
			if(periodlist.get(i).getend() < start && periodlist.get(i+1).getstart() > end ){
				periodlist.add(i+1,A);
				return true;
			}
		}
		return false;
	}
	
	@Override
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：移除period表中以lable为标签的period
	 * 返回值：移除成功返回true，否则返回false
	 */
	public boolean remove(L lable) {
		for(period e : periodlist) {
			if(e.lableequals(lable)) {
				periodlist.remove(e);
				return true;
			}
		}
		return false;
	}
	
	@Override 
	/*
	 * 参数：无
	 * 约束：无
	 * 函数功能：返回所有period的标签
	 * 返回值：所有period的标签的序列
	 */
	public ArrayList<L> lables(){
		ArrayList<L> A = new ArrayList<L>();
		for(period e : periodlist) {
			A.add(e.getlable());
		}
		return A;
	}
	
	@Override
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：获得第一个以lable为标签阶段的起始时间
	 * 返回值：第一个以lable为标签阶段的起始时间
	 */
	public long start(L lable) {
		for(period e : periodlist) {
			if(e.lableequals(lable))
				return e.getstart();
		}	
		return -1;
	}
	
	@Override
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：获得第一个以lable为标签阶段的终止时间
	 * 返回值：第一个以lable为标签阶段的终止时间
	 */
	public long end(L lable) {
		for(period e : periodlist) {
			if(e.lableequals(lable))
				return e.getend();
		}	
		return -1;
	}
	

	@Override
	/*
	 * 参数：无
	 * 约束：无
	 * 函数功能：获得时间轴上所有period的字符串表示
	 * 返回值：时间轴上所有period的字符串表示
	 */
	public String toString() {
		StringBuilder A = new StringBuilder();
		for(period e : periodlist) {
			A.append(e.getstart()+" "+e.getend()+" "+e.getlable().toString()+"\n");
		}
		return A.toString();
	}
	

	class period{
		private final long start;
		private final long end;
		private final L lable;
		
		public period(long start, long end, L lable) {
			this.end = end;
			this.start = start;
			this.lable = lable;
		}
		
		/*
		 * 参数：无
		 * 约束：无
		 * 函数功能：返回当前阶段起始时间
		 * 返回值：当前阶段起始时间
		 */
		public long getstart() {
			return this.start;
		}
		/*
		 * 参数：无
		 * 约束：无
		 * 函数功能：返回当前阶段终止时间
		 * 返回值：当前阶段终止时间
		 */
		public long getend() {
			return this.end;
		}
		/*
		 * 参数：无
		 * 约束：无
		 * 函数功能：返回当前阶段标签
		 * 返回值：当前阶段标签
		 */
		public L getlable() {
			return this.lable;
		}
		/*
		 * 参数：L lable
		 * 约束：lable是一个L的有意义的实例
		 * 函数功能：判断两个lable的内容是否相同，而不根据是否索引相同判断
		 * 返回值：当两个lable内容相同时返回true，否则返回false
		 */
		public boolean lableequals(L lable) {
			if(this.lable.toString().equals(lable.toString())) {
				return true;
			}
			else {
				return false;
			}
		}
		
	}
}
