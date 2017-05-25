package org.nkd;

import org.HdrHistogram.Histogram;
import org.jgroups.util.Streamable;

import java.io.DataInput;
import java.io.DataOutput;
import java.nio.ByteBuffer;

/**
 * @author Michal Nikodim (michal.nikodim@topmonks.com)
 */
public class MemberResult implements Streamable {

    long numGets, numPuts;
    long time; // ms
    Histogram histogramGet, histogramPut;

    public MemberResult() {
        //empty constructor for deserialize
    }

    MemberResult(long numGets, long numPuts, long time, Histogram histogramGet, Histogram histogramPut) {
        this.numGets = numGets;
        this.numPuts = numPuts;
        this.time = time;
        this.histogramGet = histogramGet;
        this.histogramPut = histogramPut;
    }

    public void writeTo(DataOutput out) throws Exception {
        out.writeLong(numGets);
        out.writeLong(numPuts);
        out.writeLong(time);
        writeHistogram(histogramGet, out);
        writeHistogram(histogramPut, out);
    }

    public void readFrom(DataInput in) throws Exception {
        numGets = in.readLong();
        numPuts = in.readLong();
        time = in.readLong();
        histogramGet = readHistogram(in);
        histogramPut = readHistogram(in);
    }

    public String toString() {
        long totalRequests = numGets + numPuts;
        double totalReqsPerSec = totalRequests / (time / 1000.0);
        return String.format("%.2f reqs/sec (%d GETs, %d PUTs), avg RTT (us) = %.2f get, %.2f put",
                totalReqsPerSec, numGets, numPuts, histogramGet.getMean(), histogramPut.getMean());
    }

    private static void writeHistogram(Histogram histogram, DataOutput out) throws Exception {
        int size = histogram.getEstimatedFootprintInBytes();
        ByteBuffer buf = ByteBuffer.allocate(size);
        histogram.encodeIntoCompressedByteBuffer(buf, 9);
        out.writeInt(buf.position());
        out.write(buf.array(), 0, buf.position());
    }

    private static Histogram readHistogram(DataInput in) throws Exception {
        int len = in.readInt();
        byte[] array = new byte[len];
        in.readFully(array);
        ByteBuffer buf = ByteBuffer.wrap(array);
        return Histogram.decodeFromCompressedByteBuffer(buf, 0);
    }
}
