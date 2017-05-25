package org.nkd;

import java.io.Serializable;

/**
 * @author Michal Nikodim (michal.nikodim@topmonks.com)
 */
class Config implements Serializable {

    int numThreads = 50;
    int numKeys = 100000;
    int timeSecs = 20;
    int valueSize = 1000;
    double readRatio = 0.5;

    @Override
    public String toString() {
        return "numThreads=" + numThreads +
                ", numKeys=" + numKeys +
                ", timeSecs=" + timeSecs +
                ", valueSize=" + valueSize +
                ", readRatio=" + readRatio;
    }
}
