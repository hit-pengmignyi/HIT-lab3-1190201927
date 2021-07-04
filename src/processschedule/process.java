package processschedule;

public class process {
	private final int ID;
	private final String name;
	private final long minsumtime;
	private final long maxsuntime;
	
	public process(int ID,String name,long minsumtime,long maxsumtime){
		this.ID = ID;
		this.name = name;
		this.minsumtime = minsumtime;
		this.maxsuntime = maxsumtime;
	}
	
	public long getmintime() {
		return this.minsumtime;
	}
	
	public long getmaxtime() {
		return this.maxsuntime;
	}
	
	@Override 
	public String toString() {
		StringBuilder A = new StringBuilder();
		A.append("ID:"+this.ID+" "+"name:"+this.name+"\n");
		return A.toString();
	}
}
