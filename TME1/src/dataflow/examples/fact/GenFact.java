package dataflow.examples.fact;

import java.math.BigInteger;

import dataflow.core.Composite;
import dataflow.core.IntEventReceiverService;
import dataflow.core.RequireIntEventReceiverService;
import dataflow.examples.basics.GenInt;
import dataflow.generators.GenConst;
import dataflow.operators.Mul;


/*
 * 
 * Exercice 2 -- Question 3
 * 
 * A compléter  
 * 
 */

public class GenFact implements Composite,
			/* require */
			RequireIntEventReceiverService {

	// TODO
	public GenConst gen0;
	public GenInt gen1;
	public Mul mul;
	public GenFact() {
		gen0 = new GenConst(new BigInteger(String.valueOf(1)));
		gen1 = new GenInt(1);
		mul = new Mul();
		gen0.bindIntEventReceiverService(mul);
		gen1.bindIntEventReceiverService(mul);
		mul.bindIntEventReceiverService(mul);
		gen0.activate();
	}	
	
	@Override
	public void bindIntEventReceiverService(IntEventReceiverService serv) {
		mul.bindIntEventReceiverService(serv);
	}
		
	@Override
	public void activate() {
		gen1.activate();
		mul.activate();
	}

}
