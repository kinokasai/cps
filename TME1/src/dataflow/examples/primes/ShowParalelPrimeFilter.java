package dataflow.examples.primes;

import java.util.ArrayList;

import dataflow.core.Printer;

public class ShowParalelPrimeFilter {
	public static void main(String[] args) throws InterruptedException {
		int nb_gen = 30;
		int nb_thread = 4;
		if (args.length > 0) {
			try {
				nb_gen = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) { /* ok use default */ }
		}

		GenPrimes gen = new GenPrimes();
		Printer printer = new Printer("primes() -> ");
		
		gen.bindIntEventReceiverService(printer);
		ArrayList<PrimeThread> tab_thread = new ArrayList<>(nb_thread);
		for(int j = 0;j<nb_thread;j++) {
			tab_thread.add(j,new PrimeThread(gen, printer,nb_gen));
		}
		
		for(int k = 0;k<nb_thread;k++) {
			tab_thread.get(k).start();
			tab_thread.get(k).join();
		}
		
	}
}
