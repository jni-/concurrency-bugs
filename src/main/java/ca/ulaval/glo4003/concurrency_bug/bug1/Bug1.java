package ca.ulaval.glo4003.concurrency_bug.bug1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Bug1 {
	
	private static final int ITERATIONS = 1000;
	private static final int INITIAL_BALANCE = 1000;
	private static final int THREAD_COUNT = 100;
	private static final Map<Integer, Account> accounts = new HashMap<>();
	private static Random random = new Random(65743L);
	
	private static int counter = 0;

	public static void main(String[] args) {
		createAccounts();
		transferSomeMoney();
		
		// All accounts initially have 1000$ in it. With 1000 threads, that's a total of 1 000 000$.
		// The transfers are a zero sum game, so the total should be the same.
		int totalMoney = accounts.values().stream().mapToInt((plane) -> plane.getBalance()).sum();
		System.out.println("We are supposed to have 1 000 000$ : " + totalMoney);
	}

	private static void createAccounts() {
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		for(int i = 0; i < ITERATIONS; i++) {
			executor.execute(() -> {
				int id = ++counter;
				Account plane = new Account(INITIAL_BALANCE);
				accounts.put(id, plane);
			});
		}
		
		shutdownAndWait(executor);
	}


	private static void transferSomeMoney() {
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		for(int i = 0; i < ITERATIONS; i++) {
			executor.execute(() -> {
				Account account1 = getRandomAccount();
				Account account2 = getRandomAccount();
				account1.sendMoney(account2, 10);
			});
		}
		
		shutdownAndWait(executor);
	}

	private static void shutdownAndWait(ExecutorService executor) {
		try {
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static Account getRandomAccount() {
		ArrayList<Account> accountsList = new ArrayList<Account>(accounts.values());
		return accountsList.get(random.nextInt(accountsList.size() - 1));
	}
}
