package org.nkd;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author Michal Nikodim (michal.nikodim@topmonks.com)
 */
public class Counter {

    final LongAdder requests = new LongAdder();
    final LongAdder reads = new LongAdder();
    final LongAdder writes = new LongAdder();

    void reset() {
        requests.reset();
        reads.reset();
        writes.reset();
    }
}
