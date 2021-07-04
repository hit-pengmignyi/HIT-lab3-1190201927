package intervalset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiIntervalSet<L> implements IntervalSet<L>{
	private final Map<L,Map<Long,Long>> LMap = new HashMap<L,Map<Long,Long>>();

	// Abstraction function:
    //   ��<L��<long,long>����>ӳ�䵽ʱ�����
    // Representation invariant:
    //   LMap
    // Safety from rep exposure:
    //  LMap��private final���εģ���ʾ��Ϣ���ܵ�������
    //   private ��ʾ�������ֻ����ͬ����з��ʣ���finalȷ���˸ñ������������ᱻ����
    //   ��ֹ���ͨ�����ڲ����õ��±�ʾй¶
	
	@Override
	/*
	 * ������start��ʾlable��Ӧʱ����㣬end��ʾlable��Ӧʱ���յ㣬lable���Զ�Ӧemployee
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ�����һ��lable��Ӧ��ʱ���<start,end>
	 * ����ֵ������ɹ�����true�����򷵻�false
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
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ�ɾ��ʱ������������lableΪ��ǩ��ʱ��Ƭ��
	 * ����ֵ��ɾ���ɹ�����true�����򷵻�false
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
	 * ��������
	 * Լ������
	 * �������ܣ���ʾʱ���������еı�ǩ
	 * ����ֵ������ʱ��Ƭ�εı�ǩ����
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
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ�����lable��ʵ������������ʱ��ε�<start,end>
	 * ����ֵ��������lableΪ��ǩ��ʱ��Ƭ�ε���ʼ��ֹʱ��ͼ
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
