package dutyroster;


public class Employee{
	private final String name;
	private final String job;
	private final String phone;

	
	public Employee(String name,String job,String phone) {
		this.name = name;
		this.job = job;
		this.phone = phone;
	}
	
	public String getname(){
		return this.name;
	}

	@Override
	public String toString() {
		StringBuilder A = new StringBuilder();
		A.append(this.name+" "+this.job+" "+this.phone);
		return A.toString();
	}
}
