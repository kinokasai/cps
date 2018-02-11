package dataflow.examples.primes;

import dataflow.core.Printer;

public class PrimeThread extends Thread{
	GenPrimes gen;
	Printer printer;
	int nb_gen;
	
	public PrimeThread(GenPrimes gen , Printer printer,int nb_gen) {
		this.gen= gen;
		this.printer = printer;
		this.nb_gen = nb_gen;
	}
	
	@Override
	public void run() {
		for(int i=0;i<this.nb_gen;i++) {
			gen.activate();
			printer.activate();
		}
	}

}
