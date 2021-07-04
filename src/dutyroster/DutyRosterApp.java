package dutyroster;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class DutyRosterApp<L> extends DutyIntervalSet<L>{
	private final long rosterstart;
	private final long rosterend;
	
	public DutyRosterApp(long rosterstart1, long rosterend1) {
		this.rosterstart = rosterstart1;
		this.rosterend = rosterend1;
	}
	/*
	 * 参数：start表示lable对应时段起点，end表示lable对应时段终点，lable可以对应employee
	 * 约束：lable需要是L的一个有意义实例
	 * 函数功能：插入一个period<start,Lable,end>三元组
	 * 返回值：成功插入返回true，否则返回false
	 */
	
	public boolean setperiod(long start,long end,L lable) {
		if(start<this.rosterstart||end>this.rosterend||end<this.rosterstart||start>this.rosterend) {
			System.out.println("时间格式错误,请换一个输入文件");
			System.exit(1);
		}
		super.remove(lable);
		if(super.insert(start, end, lable))
			return true;
		return false;
	}
	
	/*
	 * 参数：无
	 * 约束：无
	 * 函数功能：显示排班信息
	 * 返回值：无
	 */
	
	public void showroster() {
		data A = new data();
		long start = this.rosterstart;
		double sumday = A.datainterval(rosterstart, rosterend).length + 2;
		double freeday = 0;
		int i = 0;
		for(L e : super.lables()) {
			System.out.println(A.long2String(super.start(e))+" "
							  +A.long2String(super.end(e))+" "
							  +e.toString());
		}
		System.out.println("未安排值班的时间：");
		
		if(!super.lables().isEmpty()) {
		/*检查排班起始时间之前还有多少天没有排班*/
		L f = super.lables().get(0);
		String [] sstart = A.datainterval(start, super.start(f));
		if(start < super.start(f)) {
			System.out.println(A.long2String(this.rosterstart));
			for(i = 0; i < sstart.length ; i++)
				System.out.print(sstart[i]);
			if(sstart[0].equals(""))
				freeday += sstart.length;
			else
				freeday += sstart.length+1;
			start = super.end(f);
			
		}
		/*检查中间的排班空隙*/
		for(L e : super.lables()) {
			String [] s = A.datainterval(start, super.start(e));
			if(!s[0].equals("")) {
				for(i = 0 ; i < s.length; i++) {
					System.out.print(s[i]);
				}
				freeday += s.length;;
			}
			start = super.end(e);
		}
		/*检查排班终止时间之后还有多少天没有排班*/
		L e = super.lables().get(super.lables().size()-1);
		String [] send = A.datainterval(super.end(e), this.rosterend); 
		if(super.end(e) < this.rosterend) {
			for(i = 0; i < send.length ; i++)
				System.out.print(send[i]);
			System.out.println(A.long2String(this.rosterend));
			if(send[0].equals(""))
				freeday += send.length;
			else
				freeday += send.length+1;
		}
		}
		/*没有排班时的显示信息*/
		else {
			String [] s = A.datainterval(this.rosterstart, this.rosterend);
			for(i = 0; i < s.length ;i++)
				System.out.print(s[i]);
			System.out.print(A.long2String(this.rosterstart));
			System.out.print(A.long2String(this.rosterend));
			freeday = sumday;
		}
		System.out.println("总天数"+" "+sumday);
		System.out.println("空闲天数"+" "+freeday);
		System.out.println("未安排时间占比"+freeday/sumday);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String [] temp = new String[3]; 
		int command = -1, n = 0;
		data A = new data();
		System.out.println("输入1，手动创建排班表或模拟生成排班表；输入2，从文件读入排班表并显示排班信息；输入0：退出");
		while(true) {
			command = in.nextInt();
			switch(command){
				case 0:
					System.exit(0);
					break;
				case 1:
					System.out.println("设置任务起止时间");
						DutyRosterApp<Employee> manuapp = new DutyRosterApp<Employee>(A.String2long(in.next()),A.String2long(in.next()));	
					while(command!=0) {
						System.out.println("输入1：加入员工；输入2：删除员工；输入3：设置某一时段的安排；输入4：随机生成排班表;"
							+ "输入5：显示排班表；"+"输入0：退出");
					command = in.nextInt();
					switch(command) {
						case 0:
							System.exit(0);
							break;
						case 1:
							System.out.println("输入人数：");
							n = in.nextInt();
							in.nextLine();
							System.out.println("输入格式：名字{职务，电话号码}");
							while(n!=0) {
								String[] a = in.nextLine().split("\\{|,|\\}");
								Employee people = new Employee(a[0],a[1],a[2]);
								manuapp.insertemployee(people);
								n--;
							}
							break;
						case 2:
							System.out.println("输入人数：");
							n = in.nextInt();
							System.out.println("输入格式：名字");
							while(n!=0) {
								String a = in.next();
								for(Employee e : manuapp.lablelist()) {
									if(e.getname().equals(a)) {
										manuapp.removeemployee(e);
										break;
									}
								}
								n--;
							}
							break;
						case 3:
							System.out.println("输入show展示排班表，输入quit退出排班表设置");
							System.out.println("输入格式：名字{开始时间，截止时间}");
							while(true) {
								String[] a = in.next().split("\\{|\\}|,");
								if(a[0].equals("show")) 
									manuapp.showroster();
								else if(a[0].equals("quit"))
									break;
								else {
									for(Employee e : manuapp.lablelist()) {
										if(e.getname().equals(a[0])) {
											manuapp.setperiod(A.String2long(a[1]), A.String2long(a[2]), e);
										}
									}
								}
							}
						case 4:
							/*在有员工表的时候随机生成排班表*/
							if(manuapp.lablelist().size() == 0) {
								System.out.println("请先输入员工");
								break;
							}
					        int length = (A.datainterval(manuapp.rosterstart, manuapp.rosterend).length+2);
					        int i = 0;
					        long start = manuapp.rosterstart;
					        Random random = new Random();
					        for(i = 0; i < manuapp.lablelist().size()-1; i++) {
					        	if(length <= 1)
					        		break;
					        	int s = random.nextInt(length-2)+ 1;
					        	manuapp.setperiod(start, A.long2long(start, s-1), manuapp.lablelist().get(i));
					        	length -= s;
					        	start = A.long2long(start, s);
					        }
					        if(start <= manuapp.rosterend)
					        manuapp.setperiod(start, manuapp.rosterend, 
					        		manuapp.lablelist().get(manuapp.lablelist().size()-1));
							break;
						case 5:
							manuapp.showroster();
							break;
					}
					}
					break;
					
				case 2:
					StringBuilder cons = new StringBuilder();
					System.out.println("输入文件名称");
					cons.append("src\\test\\"+in.next()+".txt");
					long start = 0, end = 0;
					Scanner sc = new Scanner(new File(cons.toString()));
					ArrayList<Employee> p = new ArrayList<Employee>();
					int i = 2;
					/*根据读到的标签选择功能执行*/
					while(i!=0) {
					temp = sc.nextLine().split("\\{|,|\\}");
					if(!temp[0].equals("Employee")&&!temp[0].equals("Period")) {
						System.out.println("Iligal Text!");
						break;
					}
					if(temp[0].equals("Employee")) {
					while(temp.length > 0) {
						temp = sc.nextLine().split("\\{|,|\\}");
						if(temp.length == 0)
							break;
						Employee people = new Employee(temp[0],temp[1],temp[2]);
						p.add(people);
					}
					}
					else {
						start = A.String2long(temp[1]);
						end = A.String2long(temp[2]);
					}
					i--;
					}
					/*创建安排表*/
					DutyRosterApp<Employee> autoapp = new DutyRosterApp<Employee>(start,end);
					/*将职工加入排班表可选员工名单中*/
					for(Employee e : p) {
						autoapp.insertemployee(e);
					}
					temp = sc.next().split("\\{|,|\\}");
					if(!temp[0].equals("Roster")) {
						System.out.println("Iligal Text!");
						break;
					}
					while(temp.length > 0) {
						temp = sc.nextLine().split("\\{|,|\\}");
						if(temp.length == 0)
							break;
						for(Employee e : p) {
							if(e.getname().equals(temp[0])) {
								if(autoapp.setperiod(A.String2long(temp[1]),A.String2long(temp[2]),e)) {
								break;	
								}
								else {
									System.out.println("有错误的安排，已取消这次安排");
								}
							}
						}
					}
					autoapp.showroster();
					break;
			}
			
		}
		
	}
	

	
	
}