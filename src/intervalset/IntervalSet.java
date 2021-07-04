package intervalset;

import java.util.ArrayList;

public interface IntervalSet<L> {
	public static <L> IntervalSet<L> empty(){
		IntervalSet<L> IntervalSet = (IntervalSet<L>) new CommonIntervalSet<L>();
		return IntervalSet;
	}
	/*成功加入返回true*/
	public boolean insert(long start, long end, L lable);
	/*成功删除返回true*/
	public boolean remove(L lable);
	/*获得起始时间*/
	public long start(L lable);
	/*获得结束时间*/
	public long end(L lable);
	/*获得当前阶段集合*/
	public ArrayList<L> lables();
	
}