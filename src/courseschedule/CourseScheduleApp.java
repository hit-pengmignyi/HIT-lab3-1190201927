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
	 * ������L lable��String time
	 * Լ����lable��Ҫ��L��һ��������ʵ����time��һ���������ʱ���ʽ��yyyy-mm-dd��
	 * �������ܣ���һ�ſγ�ӳ�䵽�γ̱��ĳһ��ʱ�����
	 * ����ֵ����
	 */
	public void setschedule(L lable,String time) {
		String [] s =time.split("\\{|:|\\}");
		data tc = new data();
		int i = 0;
		switch(s[0]) {
		case "����һ":
			i = 0;
			break;
		case "���ڶ�":
			i = 1;
			break;
		case "������":
			i = 2;
			break;
		case "������":
			i = 3;
			break;
		case "������":
			i = 4;
			break;
		case "������":
			i = 5;
			break;
		case "������":
			i = 6;
			break;
		default:
			System.out.println("����ʧ�ܣ�����ȷ��������");
	}
		super.insert(tc.long2long(starttime, i), Long.parseLong(s[1]), lable);
	}
	/*
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ�����ĳһ�ſγ̻���Ҫ���ŵ�ѧʱ��
	 * ����ֵ��ĳ�ſγ���Ҫ���ŵ�ѧʱ��
	 */
	/*�����ſΣ����ؿ�ȱѧʱ����û�����ſη���-1*/
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
	 * ������MultiIntervalSet<L> set
	 * Լ������
	 * �������ܣ���ʾ�γ̱���Ϣ
	 * ����ֵ����
	 */
	public void Show(MultiIntervalSet<L> set) {
		System.out.println("�γ̰��������");
		for(L e : this.courselist.keySet()) {
			System.out.println(e.toString()+"����"+this.needlearningtime(e)+"ѧʱ");
		}
		System.out.println("����ʱ��ռ��:"+this.calcFreeTimeRatio(set));
		System.out.println("�ظ�ʱ��ռ��:"+this.calcConflictRatio(set));
	}
	
	public static void main(String[] args) {
		data timecontrol = new data();
		long start = 0, end = 0, intervaldays;
		int id = 0;
		int weeknum = 0;
		Scanner in = new Scanner(System.in);
		System.out.println("����ѧ����ʼʱ�䣬����");
		start = timecontrol.String2long(in.next());
		weeknum = in.nextInt();
		end = timecontrol.long2long(start, weeknum*7);
		CourseScheduleApp<course> schedule = new CourseScheduleApp<course>(start,end,weeknum);
		
		System.out.println("����Ҫ����Ŀγ���");
		int n = in.nextInt();
		System.out.println("��ʽ��ID name teacher position sumlearningtime");
		for(int i = 0; i < n; i++) {
			course E = new course(in.nextInt(),in.next(),in.next(),in.next(),in.nextInt());
			schedule.courselist.put(E, E.getsumtime());
		}
		
		System.out.println("���ÿγ̣�����show��ʾ��ǰ�ſ����������stopֹͣ����");
		System.out.println("��ʽ������һ{10:00-12:00} ID");
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
		
		System.out.println("�������ڣ��鿴����α�");
		end = timecontrol.String2long(in.next());
		intervaldays = timecontrol.datainterval(start, end).length % 7;
		if(end!=start){
			end = timecontrol.long2long(start, (int)intervaldays+1);
		}
		for(course e : schedule.lables()) {
			for(Long f : schedule.periods(e).keySet()) {
				if(f == end)
					System.out.println(e.toString()+"�Ͽ�ʱ��: "+schedule.periods(e).get(f)+":00"+" �¿�ʱ��: "+(schedule.periods(e).get(f)+2L)+":00");
			}
		}
	}
}
