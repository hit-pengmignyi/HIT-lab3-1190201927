package DutyIntervalSetTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import intervalset.MultiIntervalSet;
import processschedule.process;

public class MultiIntervalSetTest {
	/*
	 * 测试策略
	 * 测试插入：插入有重叠时段的标签，插入相同内容的标签
	 * 测试删除：在空表中删除，在非空表中删除不存在的标签，在非空表中删除存在的标签
	 * 测试标签表：返回空标签表，返回非空标签表
	 * 测试标签所有时间段：一个标签有多个时间段，一个标签一个时间段
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
