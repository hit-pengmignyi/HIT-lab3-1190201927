package DutyIntervalSetTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dutyroster.DutyIntervalSet;
import dutyroster.Employee;

/*
 * ���Բ���
 * ���Բ���ְ������ְ�������ְ�����ǿ�ֵ�����벻�ڱ��е�ְ�����ǿ�ֵ�������ڱ��е�ְ��
 * ����ɾ��ְ������ְ����ɾ��ְ�����ǿ�ֵ���ɾ�����ڱ��е�ְ�����ǿ�ֵ���ɾ���ڱ��е�ְ��
 * ���Լ����Űࣺ�Ű�ʱ���ص������Űࣻ�Ѽ�����Ű����ټ����Űࣻδ������Ű��˷��ص������Ű�
 * ����ɾ���Űࣺ���Ű��ɾ��ĳ�˵��Űࣻ
 * 				�ǿ��Ű��ɾ��ĳ�˵��Ű��Ҵ���ʵ�ʲ����Ű���У�
 * 				�ǿ��Ű��ɾ��ĳ�˵��Ű��Ҵ���ʵ�����Ű����
 */	



public class DutyIntervalSetTest {
	@Test
	public void testinsertemployee() {
		DutyIntervalSet<Employee> A = new DutyIntervalSet<Employee>();
		Employee worker1 = new Employee("��һ","����","1212");
		Employee worker2 = new Employee("��һ","����","1212");
		Employee worker3 = new Employee("�Զ�","������","1213");
		assertEquals(A.insertemployee(worker1),true);
		assertEquals(A.insertemployee(worker2),false);
		assertEquals(A.insertemployee(worker3),true);
	}
	
	@Test
	public void testremoveemployee() {
		DutyIntervalSet<Employee> A = new DutyIntervalSet<Employee>();
		Employee worker1 = new Employee("��һ","����","1212");
		Employee worker2 = new Employee("�Զ�","������","1213");
		assertEquals(A.removeemployee(worker1),false);
		A.insertemployee(worker1);
		assertEquals(A.removeemployee(worker1),true);
		A.insertemployee(worker1);
		assertEquals(A.removeemployee(worker2),false);
	}
	
	@Test
	public void testinsert() {
		DutyIntervalSet<Employee> A = new DutyIntervalSet<Employee>();
		Employee worker1 = new Employee("��һ","����","1212");
		Employee worker2 = new Employee("�Զ�","������","1213");
		A.insertemployee(worker1);
		A.insertemployee(worker2);
		assertEquals(A.insert(20201010, 20201015, worker1),true);
		assertEquals(A.insert(20201015, 20201030, worker2),false);
		assertEquals(A.insert(20201016, 20201101, worker2),true);
		assertEquals(A.insert(20201102, 20201110, worker1),false);
	}
	
	@Test
	public void testremove() {
		DutyIntervalSet<Employee> A = new DutyIntervalSet<Employee>();
		Employee worker1 = new Employee("��һ","����","1212");
		Employee worker2 = new Employee("�Զ�","������","1213");
		A.insertemployee(worker1);
		A.insertemployee(worker2);
		assertEquals(A.remove(worker1),false);
		A.insert(20201010, 20201110, worker1);
		assertEquals(A.remove(worker2),false);
		assertEquals(A.remove(worker1),true);
	}
	
	@Test
	public void testtoString() {
		DutyIntervalSet<Employee> A = new DutyIntervalSet<Employee>();
		Employee worker1 = new Employee("��һ","����","1212");
		A.insertemployee(worker1);
		A.insert(20201010, 20201020, worker1);
		assertEquals(A.toString(),"20201010 20201020 ��һ ���� 1212\n");
	}
}
