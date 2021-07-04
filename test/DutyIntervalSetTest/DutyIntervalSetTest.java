package DutyIntervalSetTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dutyroster.DutyIntervalSet;
import dutyroster.Employee;

/*
 * 测试策略
 * 测试插入职工：空职工表插入职工；非空值班表插入不在表中的职工；非空值班表插入在表中的职工
 * 测试删除职工：空职工表删除职工；非空值班表删除不在表中的职工；非空值班表删除在表中的职工
 * 测试加入排班：排班时间重叠加入排班；已加入的排班人再加入排班；未加入的排班人非重叠加入排班
 * 测试删除排班：空排班表删除某人的排班；
 * 				非空排班表删除某人的排班且此人实际不在排班表中；
 * 				非空排班表删除某人的排班且此人实际在排班表中
 */	



public class DutyIntervalSetTest {
	@Test
	public void testinsertemployee() {
		DutyIntervalSet<Employee> A = new DutyIntervalSet<Employee>();
		Employee worker1 = new Employee("赵一","主任","1212");
		Employee worker2 = new Employee("赵一","主任","1212");
		Employee worker3 = new Employee("赵二","副主任","1213");
		assertEquals(A.insertemployee(worker1),true);
		assertEquals(A.insertemployee(worker2),false);
		assertEquals(A.insertemployee(worker3),true);
	}
	
	@Test
	public void testremoveemployee() {
		DutyIntervalSet<Employee> A = new DutyIntervalSet<Employee>();
		Employee worker1 = new Employee("赵一","主任","1212");
		Employee worker2 = new Employee("赵二","副主任","1213");
		assertEquals(A.removeemployee(worker1),false);
		A.insertemployee(worker1);
		assertEquals(A.removeemployee(worker1),true);
		A.insertemployee(worker1);
		assertEquals(A.removeemployee(worker2),false);
	}
	
	@Test
	public void testinsert() {
		DutyIntervalSet<Employee> A = new DutyIntervalSet<Employee>();
		Employee worker1 = new Employee("赵一","主任","1212");
		Employee worker2 = new Employee("赵二","副主任","1213");
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
		Employee worker1 = new Employee("赵一","主任","1212");
		Employee worker2 = new Employee("赵二","副主任","1213");
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
		Employee worker1 = new Employee("赵一","主任","1212");
		A.insertemployee(worker1);
		A.insert(20201010, 20201020, worker1);
		assertEquals(A.toString(),"20201010 20201020 赵一 主任 1212\n");
	}
}
