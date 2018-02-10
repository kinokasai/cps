package dataflow.examples.fibo;

import java.math.BigInteger;

import dataflow.core.Composite;
import dataflow.core.IntEvent;
import dataflow.core.IntEventReceiverService;
import dataflow.core.Relay;
import dataflow.core.RequireIntEventReceiverService;
import dataflow.operators.Add;

/*
 * 
 * Exercice 2 -- Question 4
 * 
 * A compléter  
 * 
 */


public class GenFibo implements Composite,
			/* require */
			RequireIntEventReceiverService {

	public Relay first;
	public Relay second;
	public Add add;
	
	public GenFibo() {
		first = new Relay();
		second = new Relay();
		add = new Add();

//		Bindings
		first.bindIntEventReceiverService(add);
		first.bindIntEventReceiverService(second);
		second.bindIntEventReceiverService(add);
		add.bindIntEventReceiverService(first);

		/* Initialisation */

		add.onIntEvent(new IntEvent(new BigInteger(String.valueOf(0))));
		add.onIntEvent(new IntEvent(new BigInteger(String.valueOf(1))));
		second.onIntEvent(new IntEvent(new BigInteger(String.valueOf(0))));
	}
	
	@Override
	public void bindIntEventReceiverService(IntEventReceiverService serv) {
		add.bindIntEventReceiverService(serv);
	}
		
	@Override
	public void activate() {
        add.activate();
        second.activate();
        first.activate();
	}

}
