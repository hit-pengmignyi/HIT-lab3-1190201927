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
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ�����һ�����̵�δ��ɽ��̼�����
	 * ����ֵ������ɹ�����true�����򷵻�false
	 */
	public boolean insertprocess(L lable) {
		for(L e : pList.keySet()) {
			if(e.toString().equals(lable.toString())){
				return false;
			}
		}
		/*�������������ʱ����Ϊ0*/
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
	 * ������  L lable
	 * Լ����  lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ����ĳ�������Ѿ����е�ʱ��
	 * ����ֵ����lable����LMapʱ����-1�����򷵻ص�ǰ�Ѿ�����ʱ��
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
	 * ������L lable, �������ʱ�� maxtime
	 * Լ����Lable������L�б��У����Բ���LMap��
	 * �������ܣ�������ɴ˴ε��Ƚ��̵�����ʱ��
	 * ����ֵ������ʱ��
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
	 * ��������
	 * Լ������
	 * �������ܣ���ʾ�Ѿ��������̵���Ϣ
	 * ����ֵ����
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
		System.out.println("���������n"); 
		int n = 0  , i = 0, min = Integer.MAX_VALUE;
		long resttime = 0;
		long newtime = 0;
		n = in.nextInt();
		System.out.println("��ʽ��ID name mintime maxtime");
		for(i = 0 ; i < n ; i++) {
			process A = new process(in.nextInt(),in.next(),in.nextLong(),in.nextLong());
			ps.insertprocess(A);
		}
		System.out.println("����1��ѡ���������ģʽ������2��ѡ�����ģʽ");
		n = in.nextInt();
		if(n==1) {
		/*��̽�������*/
		while(!ps.pList.isEmpty()) {
			/*ĳ���̴˴ε�������ʱ��*/
			if(resttime != 0) {
				if(nowprocess != null)
				System.out.println("ʱ�̣�"+ps.time+"\n"+ps.Show()+"��ǰ���̣�"+nowprocess.toString());
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
		System.out.println("ʱ�̣�"+ps.time+"\n"+ps.Show());
		}
		else {
		/*���ģ��*/
		while(!ps.pList.isEmpty()) {
		/*ĳ���̴˴ε�������ʱ��*/
		if(resttime != 0) {
			if(nowprocess != null)
				System.out.println("ʱ�̣�"+ps.time+"\n"+ps.Show()+"��ǰ���̣�"+nowprocess.toString());
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
		System.out.println("ʱ�̣�"+ps.time+"\n"+ps.Show());
		}
	}
	
}
