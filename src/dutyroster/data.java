package dutyroster;



public class data{	
	/*long转化为字符串输出*/
	public String long2String(long data) {
		StringBuilder A = new StringBuilder();
		A.append(data/10000+"-"+(data/100)%100+"-"+data%100+" ");
		return A.toString();
	}
	
	public long String2long(String data) {
		String [] A = new String[3];
		A = data.split("-");
		long m = Long.parseLong(A[0]) * 10000 ;
		if(A[1].charAt(0) == '0') 
			A[1] = A[1].substring(1);
		if(A[2].charAt(0) == '0')
			A[2] = A[2].substring(1);
		m += Long.parseLong(A[1]) * 100;
		m += Long.parseLong(A[2]);
		return m;
	}
	
	public long long2long(long start,int length) {
		if(length <= 0)
			return start;
		data A = new data();
		long maxday = 0;
		long aidday = 0;
		long year = start / 10000,month = (start / 100) % 100,day = start % 100;
		while(length!=0) {
			if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12)
				maxday = 31;
			else
				maxday = 30;
			if(month == 2)
				maxday = A.SecMonthofYear(year);
			day++;
			if(day > maxday) {
				month++;
				day = 1;
				if(month > 12) {
					year++;
					month = 1;
				}
			}
			length--;
		}
		aidday = year * 10000 + month * 100 + day;
		return aidday;
	}
	
	
	
	public int SecMonthofYear(long year) {
		if((year%4==0 && year%100!=0)||year%400==0)
			return 29;
		else
			return 28;
	}
	/*计算两个日期之间的日期序列，不含这两端*/
	public String[] datainterval(long start, long end) {
		long year0 = start / 10000, month0 = (start/100)%100 , day0 = start % 100,
			 year1 = end / 10000, month1 = (end/100)%100 , day1 = end % 100;
		
		long i , j , k ,startday, endday, startmonth, endmonth;
		StringBuilder A = new StringBuilder();
		data time = new data();
	
		for(i = year0 ; i <= year1 ; i++) {
			if(i == year0)
				startmonth = month0;
			else
				startmonth = 1;
			if(i != year1)
				endmonth = 12;
			else
				endmonth = month1;
			for(j = startmonth ; j <= endmonth ; j++) {
				if(j == startmonth)
					startday = day0;
				else
					startday = 1;
				if(j == endmonth)
					endday = day1;
				else {
					if(j==1||j==3||j==5||j==7||j==8||j==10||j==12)
						endday = 31;
					else
						endday = 30;
					if(j == 2)
						endday = time.SecMonthofYear(i);
				}
				for(k = startday; k <= endday; k++) {
					if((i*10000+j*100+k)!=start && (i*10000+j*100+k)!=end) {
						A.append(i+"-"+j+"-"+k+"\n"+"/");
					}
				}
			}
		}
			
		String [] B = A.toString().split("/");
		return B;
	}
	
	
	 
}