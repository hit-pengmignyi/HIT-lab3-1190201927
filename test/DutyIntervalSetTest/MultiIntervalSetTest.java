package DutyIntervalSetTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import intervalset.MultiIntervalSet;
import processschedule.process;

public class MultiIntervalSetTest {
	/*
	 * ���Բ���
	 * ���Բ��룺�������ص�ʱ�εı�ǩ��������ͬ���ݵı�ǩ
	 * ����ɾ�����ڿձ���ɾ�����ڷǿձ���ɾ�������ڵı�ǩ���ڷǿձ���ɾ�����ڵı�ǩ
	 * ���Ա�ǩ�����ؿձ�ǩ�����طǿձ�ǩ��
	 * ���Ա�ǩ����ʱ��Σ�һ����ǩ�ж��ʱ��Σ�һ����ǩһ��ʱ���
	 */
	@Test
	public void testinsert() {
		MultiIntervalSet<process> A = new MultiIntervalSet<process>();
		process a = new process(1,"a",1,2);
		process b = new process(1,"a",1,2);
		process c = new process(2,"c",3,4);
		assertEquals(A.insert(100, 200, a),true);
		assertEquals(A.insert(10, 20, b),true);
		assertEquals(A.insert(100, 200, c),true);
	}
	@Test
	public void testremove() {
		MultiIntervalSet<process> A = new MultiIntervalSet<process>();
		process a = new process(1,"a",1,2);
		process b = new process(2,"b",2,3);
		assertEquals(A.remove(a),false);
		A.insert(0, 10, a);
		assertEquals(A.remove(b),false);
		assertEquals(A.remove(a),true);
		
	}
	
	@Test
	public void testlables() {
		MultiIntervalSet<process> A = new MultiIntervalSet<process>();
		process a = new process(1,"a",1,2);
		assertEquals(A.lables().size(),0);
		A.insert(10, 20, a);
		for(process e : A.lables()) {
			assertEquals(e.toString().equals(a.toString()),true);
		}
		
	}
	
	@Test
	public void testperiod() {
		MultiIntervalSet<process> A = new MultiIntervalSet<process>();
		process a = new process(1,"a",1,2);
		process b = new process(2,"b",2,3);
		A.insert(0, 5, a);
		A.insert(6, 9, b);
		A.insert(10, 15, a);
		assertEquals(A.periods(a).size(),2);
		assertEquals(A.periods(b).size(),1);
	}
}
