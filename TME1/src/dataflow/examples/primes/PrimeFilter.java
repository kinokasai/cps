package dataflow.examples.primes;

import dataflow.core.IntEvent;
import dataflow.core.IntEventReceiverService;
import dataflow.generators.SimpleGenerator;

import java.math.BigInteger;
import java.util.Optional;

/*
 * 
 * Exercice 3 -- Question 5
 * 
 * A compléter
 * 
 */

public class PrimeFilter extends SimpleGenerator 
		implements /* provide */
			       IntEventReceiverService {

	public Optional<BigInteger> divisor;
	public Optional<BigInteger> test;

    public Optional<PrimeFilter> next;

	
	public PrimeFilter() {
		next = Optional.empty();
		divisor = Optional.empty();
		test = Optional.empty();
	}

	@Override
	public void onIntEvent(IntEvent event) {
        if (!divisor.isPresent()) {
            divisor = Optional.of(event.getValue());
            send(event);
        } else {
            test = Optional.of(event.getValue());
        }
	}

	@Override
	public void activate() {
	    if (!divisor.isPresent()) { return;}
	    if (!test.isPresent()) { return;}

	    if (test.get().mod(divisor.get()).equals(BigInteger.ZERO)) {
	    	return;
	    }
	    else {
	        if (next.isPresent()) {
                next.get().onIntEvent(new IntEvent(test.get()));
                next.get().activate();
            } else {
            	PrimeFilter tmp = new PrimeFilter();
            	tmp.receivers = this.receivers;
	            next = Optional.of(tmp);
	            IntEvent try_test = new IntEvent(test.get());
	            next.get().onIntEvent(try_test);
            }
        }
	}
	
}
