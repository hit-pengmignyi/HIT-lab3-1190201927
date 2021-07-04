package intervalset;

import java.util.ArrayList;


public class CommonIntervalSet<L> implements IntervalSet<L> {
	private final ArrayList<period> periodlist = new ArrayList<period>();
	
	// Abstraction function:
    //   ��<start,lable,end>ӳ�䵽ʱ�����
    // Representation invariant:
    //   periodlist
    // Safety from rep exposure:
    //  LMap��private final���εģ���ʾ��Ϣ���ܵ�������
    //   private ��ʾ�������ֻ����ͬ����з��ʣ���finalȷ���˸ñ������������ᱻ����
    //   ��ֹ���ͨ�����ڲ����õ��±�ʾй¶
	
	@Override
	/* 
	 * ������start��ʾlable��Ӧʱ����㣬end��ʾlable��Ӧʱ���յ㣬lable���Զ�Ӧemployee
	 * Լ������Ҫlable�����壬start��end���ﲻ��Ҫ�󣬻���ʵ�ֵ�app�м�⡣
	 * �������ܣ�����<start,Lable,end>��Ԫ��
	 * ����ֵ���ɹ����뷵��true�����򷵻�false
	 */
	public boolean insert(long start, long end, L lable) {
		int i = 0;
		period A = new period(start,end,lable);
		/*���β���*/
		if(periodlist.size() == 0) {
			periodlist.add(A);
			return true;
		}
		if(periodlist.size()==1 && periodlist.get(0).getstart() > end) {
			/*�ֲ�����ͬ���ı�ǩ*/
			if(periodlist.get(0).lableequals(lable))
				return false;
			periodlist.add(0, A);
			return true;
		}
		/*���¼�����˳�����ά���¼�����*/
		for(i = 0; i < periodlist.size();i++) {
			/*�ֲ�����ͬ���ı�ǩ*/
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
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ��Ƴ�period������lableΪ��ǩ��period
	 * ����ֵ���Ƴ��ɹ�����true�����򷵻�false
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
	 * ��������
	 * Լ������
	 * �������ܣ���������period�ı�ǩ
	 * ����ֵ������period�ı�ǩ������
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
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ���õ�һ����lableΪ��ǩ�׶ε���ʼʱ��
	 * ����ֵ����һ����lableΪ��ǩ�׶ε���ʼʱ��
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
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ���õ�һ����lableΪ��ǩ�׶ε���ֹʱ��
	 * ����ֵ����һ����lableΪ��ǩ�׶ε���ֹʱ��
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
	 * ��������
	 * Լ������
	 * �������ܣ����ʱ����������period���ַ�����ʾ
	 * ����ֵ��ʱ����������period���ַ�����ʾ
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
		 * ��������
		 * Լ������
		 * �������ܣ����ص�ǰ�׶���ʼʱ��
		 * ����ֵ����ǰ�׶���ʼʱ��
		 */
		public long getstart() {
			return this.start;
		}
		/*
		 * ��������
		 * Լ������
		 * �������ܣ����ص�ǰ�׶���ֹʱ��
		 * ����ֵ����ǰ�׶���ֹʱ��
		 */
		public long getend() {
			return this.end;
		}
		/*
		 * ��������
		 * Լ������
		 * �������ܣ����ص�ǰ�׶α�ǩ
		 * ����ֵ����ǰ�׶α�ǩ
		 */
		public L getlable() {
			return this.lable;
		}
		/*
		 * ������L lable
		 * Լ����lable��һ��L���������ʵ��
		 * �������ܣ��ж�����lable�������Ƿ���ͬ�����������Ƿ�������ͬ�ж�
		 * ����ֵ��������lable������ͬʱ����true�����򷵻�false
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
