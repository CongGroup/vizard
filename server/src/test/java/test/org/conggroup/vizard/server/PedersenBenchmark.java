package test.org.conggroup.vizard.server;

import org.conggroup.vizard.crypto.components.pedersen.Commitment;
import org.conggroup.vizard.crypto.components.pedersen.Pedersen;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 1, time = 3)
@Measurement(iterations = 3, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class PedersenBenchmark {

    private Pedersen pedersen;

    private BigInteger testInteger;

    private Commitment lhs;

    private Commitment rhs;

    @Setup
    public void init() {
        testInteger = BigInteger.ONE;
        pedersen = initPedersen();
        lhs = pedersen.commit(0, 0L, testInteger);
        rhs = pedersen.commit(0, 0L, testInteger);
    }

    private Pedersen initPedersen() {
        BigInteger p = new BigInteger("20461323899232065519");
        BigInteger q = new BigInteger("10230661949616032759");
        BigInteger g = new BigInteger("7167227173707595926");
        BigInteger y = new BigInteger("10939128848210874778");
        return new Pedersen(p,q,g,y);
    }

    @Benchmark
    public void commitBenchmark(Blackhole blackhole) {
        blackhole.consume(pedersen.commit(0, 0L, testInteger));
    }

    @Benchmark
    public void compareBenchmark(Blackhole blackhole) {
        blackhole.consume(lhs.equals(rhs));
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("benchmark");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(PedersenBenchmark.class.getSimpleName())
                .output("benchmark/pedersen_benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
