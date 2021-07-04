package courseschedule;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dutyroster.data;
import intervalset.MultiIntervalSet;

public class CourseScheduleApp<L> extends MultiIntervalSet<L>{
	private final long starttime;
	private final long endtime;
	private final long weeknum;
	private Map<L,Integer> courselist = new HashMap<L,Integer>();
	
	public double calcConflictRatio(MultiIntervalSet<L> set) {
		int i = 0 , j = 0, multiblock = 0;
		long day = this.starttime,time;
		data tc = new data();
		int []a = new int[5];
		while(i!=7) {
			day = tc.long2long(starttime, i);
			for(L e : super.lables())
				for(Long f : super.periods(e).keySet()) {
					if(f == day) {
						time = super.periods(e).get(f);
						switch((int)time) {
						case 8:
							a[0]++;
							break;
						case 10:
							a[1]++;
							break;
						case 13:
							a[2]++;
							break;
						case 15:
							a[3]++;
							break;
						case 19:
							a[4]++;
							break;
						default:						
						}
						
					}
				}
			for(j = 0 ; j < 5; j++) {
				 if(a[j]>1)
					multiblock++;
				a[j] = 0;
			}
			i++;
			}
		return multiblock/35.0;
	}
	
	public double calcFreeTimeRatio(MultiIntervalSet<L> set) {
		int i = 0 , j = 0, freeblock = 0;
		long day = this.starttime,time;
		data tc = new data();
		int []a = new int[5];
		while(i!=7) {
			day = tc.long2long(starttime, i);
			for(L e : super.lables())
				for(Long f : super.periods(e).keySet()) {
					if(f == day) {
						time = super.periods(e).get(f);
						switch((int)time) {
						case 8:
							a[0]++;
							break;
						case 10:
							a[1]++;
							break;
						case 13:
							a[2]++;
							break;
						case 15:
							a[3]++;
							break;
						case 19:
							a[4]++;
							break;
						default:						
						}
						
					}
				}
			for(j = 0 ; j < 5; j++) {
				 if(a[j]==0)
					freeblock++;
				a[j] = 0;
			}
			i++;
			}
		return freeblock/35.0;
	}
	
	public CourseScheduleApp(long starttime , long endtime , long weeknum) {
		this.starttime = starttime;
		this.endtime = endtime;
		this.weeknum = weeknum;
	}
	
	/*
	 * 参数：L lable，String time
	 * 约束：lable需要是L的一个有意义实例，time是一个有意义的时间格式（yyyy-mm-dd）
	 * 函数功能：将一门课程映射到课程表的某一个时间段上
	 * 返回值：无
	 */
	public void setschedule(L lable,String time) {
		String [] s =time.split("\\{|:|\\}");
		data tc = new data();
		int i = 0;
		switch(s[0]) {
		case "星期一":
			i = 0;
			break;
		case "星期二":
			i = 1;
			break;
		case "星期三":
			i = 2;
			break;
		case "星期四":
			i = 3;
			break;
		case "星期五":
			i = 4;
			break;
		case "星期六":
			i = 5;
			break;
		case "星期日":
			i = 6;
			break;
		default:
			System.out.println("加入失败，不正确的星期数");
	}
		super.insert(tc.long2long(starttime, i), Long.parseLong(s[1]), lable);
	}
	/*
	 * 参数：L lable
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：计算某一门课程还需要安排的学时数
	 * 返回值：某门课程需要安排的学时数
	 */
	/*有这门课，返回空缺学时数，没有这门课返回-1*/
	public int needlearningtime(L lable) {
		int alreadytime = 0;
		for(L e : this.courselist.keySet()) {
			if(e.toString().equals(lable.toString())) {
				if(super.periods(e) == null)
					alreadytime = 0;
				else
					alreadytime = super.periods(e).size();
				return this.courselist.get(e) - alreadytime * 2;
			}
		}
		return -1;
	}
	/*
	 * 参数：MultiIntervalSet<L> set
	 * 约束：无
	 * 函数功能：显示课程表信息
	 * 返回值：无
	 */
	public void Show(MultiIntervalSet<L> set) {
		System.out.println("课程安排情况：");
		for(L e : this.courselist.keySet()) {
			System.out.println(e.toString()+"还差"+this.needlearningtime(e)+"学时");
		}
		System.out.println("空闲时间占比:"+this.calcFreeTimeRatio(set));
		System.out.println("重复时间占比:"+this.calcConflictRatio(set));
	}
	
	public static void main(String[] args) {
		data timecontrol = new data();
		long start = 0, end = 0, intervaldays;
		int id = 0;
		int weeknum = 0;
		Scanner in = new Scanner(System.in);
		System.out.println("输入学期起始时间，周数");
		start = timecontrol.String2long(in.next());
		weeknum = in.nextInt();
		end = timecontrol.long2long(start, weeknum*7);
		CourseScheduleApp<course> schedule = new CourseScheduleApp<course>(start,end,weeknum);
		
		System.out.println("输入要加入的课程数");
		int n = in.nextInt();
		System.out.println("格式：ID name teacher position sumlearningtime");
		for(int i = 0; i < n; i++) {
			course E = new course(in.nextInt(),in.next(),in.next(),in.next(),in.nextInt());
			schedule.courselist.put(E, E.getsumtime());
		}
		
		System.out.println("设置课程，输入show显示当前排课情况，输入stop停止输入");
		System.out.println("格式：星期一{10:00-12:00} ID");
		while(true) {
			String s =in.next();
			if(s.equals("show")) 
				schedule.Show(schedule);
			else if(s.equals("stop"))
				break;
			else {
				id = in.nextInt();
				for(course e : schedule.courselist.keySet()) {
					if(e.getID() == id)
						schedule.setschedule(e, s);
				}
			}
		}
		
		System.out.println("输入日期，查看该天课表");
		end = timecontrol.String2long(in.next());
		intervaldays = timecontrol.datainterval(start, end).length % 7;
		if(end!=start){
			end = timecontrol.long2long(start, (int)intervaldays+1);
		}
		for(course e : schedule.lables()) {
			for(Long f : schedule.periods(e).keySet()) {
				if(f == end)
					System.out.println(e.toString()+"上课时间: "+schedule.periods(e).get(f)+":00"+" 下课时间: "+(schedule.periods(e).get(f)+2L)+":00");
			}
		}
	}
}
