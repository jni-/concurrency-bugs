package ca.ulaval.glo4003.concurrency_bug.bug3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// Example taken from Seven models of concurrency in seven weeks by Paul Butcher
public class Bug3 {
	
	private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private Date parse(String date) {
		try {
			return format.parse(date);
		} catch(Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Bug3 instance = new Bug3();

		List<Thread> threads = new LinkedList<>();
		for(int i = 0; i < 100; i++) {
			Thread thread = new Thread(() -> instance.parse("2012-12-11"));
			threads.add(thread);
			thread.start();
		}
		
		for(Thread thread: threads) {
			thread.join();
		}
		
		System.out.println("done");
	}

}
