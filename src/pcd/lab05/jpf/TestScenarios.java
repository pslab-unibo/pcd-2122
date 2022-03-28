package pcd.lab05.jpf;

import gov.nasa.jpf.vm.Verify;

public class TestScenarios {

	static class Worker extends Thread {
		protected void log(String msg) {
			synchronized (System.out) {
				System.out.println(msg);
			}
		}
	}

	static class MyWorkerA extends Worker {
		public void run() {
			Verify.beginAtomic();
			log("a1");
			log("a2");
			Verify.endAtomic();
		}
	}

	static class MyWorkerB extends Worker {
		public void run() {
			Verify.beginAtomic();
			log("b1");
			log("b2");
			Verify.endAtomic();
		}
	}

	public static void main(String[] args) throws Exception {
		Verify.beginAtomic();
		Thread th0 = new MyWorkerA();
		Thread th1 = new MyWorkerB();
		th0.start();
		th1.start();
		Verify.endAtomic();
		th0.join();
		th1.join();
	}

}
