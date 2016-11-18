package ca.ulaval.glo4003.concurrency_bug.bug2;

// Example taken from Seven models of concurrency in seven weeks by Paul Butcher
public class Bug2 {
	
	static int answer = 0;
	static boolean answerReady = false;
	
	static Thread t1 = new Thread() {
		public void run() {
			answer = 42;
			answerReady = true;
		}
	};

	static Thread t2 = new Thread() {
		public void run() {
			if(answerReady) {
				System.out.println("The meaning of life is " + answer);
			} else {
				System.out.println("I don't know the answer (" + answer + ")");
			}
		}
	};
	
	public static void main(String[] args) throws InterruptedException {
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

}
