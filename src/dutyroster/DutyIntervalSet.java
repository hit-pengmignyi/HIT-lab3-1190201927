package dutyroster;

import java.util.ArrayList;

import intervalset.CommonIntervalSet;

public class DutyIntervalSet<L> extends CommonIntervalSet<L>{
	private final ArrayList<L> employeelist = new ArrayList<L>();
	
	/*
	 * ��������
	 * Լ������
	 * �������ܣ����Ա����
	 * ����ֵ��Ա��������
	 */
	public ArrayList<L> lablelist(){
		return this.employeelist;
	}
	
	/*
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ���Ա������Ա����
	 * ����ֵ��������ɹ�ʱ����true�����򷵻�false
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
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ��Ƴ�ָ����Ա��
	 * ����ֵ�����Ƴ��ɹ�ʱ����true�����򷵻�false
	 */
	public boolean removeemployee(L lable) {
		for(L e : employeelist) {
			/*��ɾ���Ű��*/
			if(e.toString().equals(lable.toString())){
				super.remove(e);
				employeelist.remove(e);
				return true;
			}
		}
		return false;
	}
	/*
	 * ������L lable
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ����ø����remove
	 * ����ֵ���븸��remove���ؽ����ͬ
	 */
	public boolean remove(L lable) {
		return super.remove(lable);
	}
	/*
	 * ������start��ʾlable��Ӧʱ����㣬end��ʾlable��Ӧʱ���յ㣬lable���Զ�Ӧemployee
	 * Լ����lable��Ҫ��L��һ��������ʵ��
	 * �������ܣ����ø���insert
	 * ����ֵ���븸��insert�����ͬ
	 */
	public boolean insert(long start, long end, L lable) {
		return super.insert(start, end, lable);
	}
	/*
	 * ��������
	 * Լ������
	 * �������ܣ����ø���toString
	 * ����ֵ���븸��toString��ͬ
	 */
	public String toString() {
		return super.toString();
	}


	
}