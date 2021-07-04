package processschedule;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import intervalset.MultiIntervalSet;

public class ProcessScheduleApp<L> extends MultiIntervalSet<L>{
	private final Map<L,Long> pList = new HashMap<L,Long>();
	private final Map<L,Long> pListfinish = new HashMap<L,Long>();
	private long time = 0;
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：插入一个进程到未完成进程集合中
	 * 返回值：插入成功返回true，否则返回false
	 */
	public boolean insertprocess(L lable) {
		for(L e : pList.keySet()) {
			if(e.toString().equals(lable.toString())){
				return false;
			}
		}
		/*待处理进程运行时间设为0*/
		pList.put(lable,0L);
		return true;
	}
	public double Similarity(ProcessScheduleApp<L> s1, ProcessScheduleApp<L> s2) {
		String [] a1 = new String[(int)s1.time+1];
		String [] a2 = new String[(int)s2.time+1];
		long mintime = s1.time < s2.time ? s1.time+1 : s2.time+1;
		long maxtime = s1.time > s2.time ? s1.time+1 : s2.time+1;
		int flag = 0;
		double similartime = 0;
		for(long i = 0; i <= s1.time ; i++) {
			for(L e : s1.lables())
				for(Long f : s1.periods(e).keySet())
				if(i == f) {
					for(long m = i; m <= s1.periods(e).get(f) ; m++)
						a1[(int)m] = e.toString();
					i = s1.periods(e).get(f);
					flag = 1;
				}	
			if(flag == 0)
				a1[(int)i] = "";
			else
				flag = 0;
		}
		for(long i = 0; i <= s2.time ; i++) {
			for(L e : s2.lables())
				for(Long f : s2.periods(e).keySet())
				if(i == f) {
					for(long m = i; m <= s2.periods(e).get(f) ; m++)
						a2[(int)m] = e.toString();
					i = s2.periods(e).get(f);
					flag = 1;
				}	
			if(flag == 0)
				a2[(int)i] = "";
			else
				flag = 0;
		}
		for(int i = 0; i < mintime;i++) {
			if(a1[i].equals(a2[i])&&!a1[i].equals(""))
				similartime++;			
		}
		return similartime / maxtime;
	}
	
	public double calcFreeTimeRatio(MultiIntervalSet<L> set) {
		int flag = 0;
		double freetime = 0;
		for(long i = 0; i <= this.time ; i++) {
			for(L e : super.lables())
				for(Long f : super.periods(e).keySet())
				if(i == f) {
					i = super.periods(e).get(f);
					flag = 1;
				}
			if(flag == 0) 
				freetime++;
			else
				flag = 0;	
		}
		return freetime / (this.time+1);
	}
	/*
	 * 参数：  L lable
	 * 约束：  lable需要是L的一个有意义实例
	 * 函数功能：获得某个进程已经运行的时间
	 * 返回值：当lable不在LMap时返回-1，否则返回当前已经运行时间
	 */
	public long runtime(L lable) {
		long sum = 0;
		Map<Long,Long> A = this.periods(lable);
		if(A == null)
			return -1;
		for(Long e : A.keySet()) {
			sum += A.get(e) - e + 1;
		}
		return sum;
	}
	/*
	 * 参数：L lable, 最大运行时间 maxtime
	 * 约束：Lable必须在L列表中，可以不在LMap中
	 * 函数功能：随机生成此次调度进程的运行时间
	 * 返回值：运行时间
	 */
	public long lableperiodtime(L lable, long maxtime) {
		long alreadyruntime = this.runtime(lable);
		if(alreadyruntime == -1) 
			alreadyruntime = 0;
		Random random = new Random();
		if(maxtime - this.runtime(lable) == 1)
			return 1;
		return (long)random.nextInt((int)(maxtime - this.runtime(lable)))+ 1;
	}
	/*
	 * 参数：无
	 * 约束：无
	 * 函数功能：显示已经结束进程的信息
	 * 返回值：无
	 */
	public String Show() {
		StringBuilder A = new StringBuilder();
		for(long i = 0 ; i < this.time ; i++) {
			for(L e : super.lables())
				for(Long f : super.periods(e).keySet())
				if(i == f && super.periods(e).get(f) < this.time) {
					A.append(f+" "+super.periods(e).get(f)+" "+e.toString());
					i = super.periods(e).get(f); 
					continue;
				}
		}
		return A.toString();
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ProcessScheduleApp<process> ps = new ProcessScheduleApp<process>();
		Scanner in = new Scanner(System.in);
		process nowprocess = null;
		System.out.println("输入进程数n"); 
		int n = 0  , i = 0, min = Integer.MAX_VALUE;
		long resttime = 0;
		long newtime = 0;
		n = in.nextInt();
		System.out.println("格式：ID name mintime maxtime");
		for(i = 0 ; i < n ; i++) {
			process A = new process(in.nextInt(),in.next(),in.nextLong(),in.nextLong());
			ps.insertprocess(A);
		}
		System.out.println("输入1；选择最近进程模式；输入2：选择随机模式");
		n = in.nextInt();
		if(n==1) {
		/*最短进程优先*/
		while(!ps.pList.isEmpty()) {
			/*某进程此次调度余下时间*/
			if(resttime != 0) {
				if(nowprocess != null)
				System.out.println("时刻："+ps.time+"\n"+ps.Show()+"当前进程："+nowprocess.toString());
				Thread.sleep(1000);
				ps.time++;
				resttime--;
				continue;
			}
			else {
				if(nowprocess!=null) {
					if(ps.pList.get(nowprocess) >= nowprocess.getmintime()) {
					ps.pList.remove(nowprocess);
					ps.pListfinish.put(nowprocess, ps.pList.get(nowprocess));
					min = Integer.MAX_VALUE;
					if(ps.pList.size() == 0)
						break;
				}
				}
			}
			for(process e : ps.pList.keySet()) {
				if(e.getmaxtime() - ps.pList.get(e) < min) {
					min = (int)(e.getmaxtime() - ps.pList.get(e));
					nowprocess = e;
				}
			}
			newtime = ps.lableperiodtime(nowprocess, nowprocess.getmaxtime());
			ps.insert(ps.time, ps.time+newtime-1, nowprocess);
			ps.pList.put(nowprocess, newtime+ps.pList.get(nowprocess));
			resttime = newtime;			
		}
		System.out.println("时刻："+ps.time+"\n"+ps.Show());
		}
		else {
		/*随机模拟*/
		while(!ps.pList.isEmpty()) {
		/*某进程此次调度余下时间*/
		if(resttime != 0) {
			if(nowprocess != null)
				System.out.println("时刻："+ps.time+"\n"+ps.Show()+"当前进程："+nowprocess.toString());
			Thread.sleep(1000);
			ps.time++;
			resttime--;
			continue;
		}
		else {
			if(nowprocess!=null) {
				if(ps.pList.get(nowprocess) >= nowprocess.getmintime()) {
				ps.pList.remove(nowprocess);
				ps.pListfinish.put(nowprocess, ps.pList.get(nowprocess));
			}
			}
		}
		Random random = new Random();
		n = random.nextInt(ps.pList.size()+1);
		if(n == 0) {
			resttime = 1;
			nowprocess = null;
		}
		else {
			for(process e : ps.pList.keySet()) {
				n--;
				if(n == 0) {
					if(nowprocess!=null && nowprocess.toString().equals(e.toString()))
						break;
					newtime = ps.lableperiodtime(e, e.getmaxtime());
					ps.insert(ps.time, ps.time+newtime-1, e);
					ps.pList.put(e, newtime+ps.pList.get(e));
					resttime = newtime;
					nowprocess = e; 
					break;
				}
			}
		}
		}
		System.out.println("时刻："+ps.time+"\n"+ps.Show());
		}
	}
	
}
