package ca.ulaval.glo4003.concurrency_bug.bug1;

public class Account {

	private int balance;

	public Account(int balance) {
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}

	public void sendMoney(Account recipient, int amount) {
		this.balance -= amount;
		recipient.balance +=amount;
	}

}
