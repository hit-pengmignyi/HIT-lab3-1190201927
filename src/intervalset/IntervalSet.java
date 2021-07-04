package intervalset;

import java.util.ArrayList;

public interface IntervalSet<L> {
	public static <L> IntervalSet<L> empty(){
		IntervalSet<L> IntervalSet = (IntervalSet<L>) new CommonIntervalSet<L>();
		return IntervalSet;
	}
	/*�ɹ����뷵��true*/
	public boolean insert(long start, long end, L lable);
	/*�ɹ�ɾ������true*/
	public boolean remove(L lable);
	/*�����ʼʱ��*/
	public long start(L lable);
	/*��ý���ʱ��*/
	public long end(L lable);
	/*��õ�ǰ�׶μ���*/
	public ArrayList<L> lables();
	
}