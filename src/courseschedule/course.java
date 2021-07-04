package courseschedule;

public class course {
	private final int ID;
	private final String name;
	private final String teachname;
	private final String pos;
	private final int sumtime;
	
	public course(int ID,String name,String teachname,String pos,int sumtime){
		this.ID = ID;
		this.name = name;
		this.teachname = teachname;
		this.pos = pos;
		this.sumtime = sumtime;
	}
	
	public int getsumtime() {
		return this.sumtime;
	}
	
	public int getID() {
		return this.ID;
	}
	
	@Override
	public String toString() {
		StringBuilder A = new StringBuilder();
		A.append(this.ID+" "+this.name+" "+this.teachname+" "+this.pos+" "+this.sumtime+"\n");
		return A.toString();
	}
}
