package br.com.aexo.nimbleway.client;

public class SpinLock {

	private boolean isLocked = false;

	public synchronized void lock() {
		while (isLocked) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		isLocked = true;
	}

	public synchronized void unlock() {
		isLocked = false;
		notify();
	}
}