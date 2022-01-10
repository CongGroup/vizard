package test.org.conggroup.vizard.server;

import org.conggroup.vizard.crypto.utils.Converter;
import org.conggroup.vizard.crypto.components.secretsharing.ShamirSecretSharing;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 1, time = 3)
@Measurement(iterations = 3, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SSSBenchmark {

    @Param({ "10", "20", "30" })
    public int shareNum;

    @Param({ "100", "1000", "10000"})
    public int loopNum;

    @Param({ "0.67" })
    public float shareRatio;

    private ShamirSecretSharing shamirSecretSharing;

    private BigInteger testInteger;

    private ArrayList<String> testSecrets;

    @Setup
    public void init() {
        testInteger = BigInteger.ONE;
        shamirSecretSharing = new ShamirSecretSharing(shareNum, shareRatio);
        testSecrets = genSecretShares(testInteger);
    }

    private ArrayList<String> genSecretShares(BigInteger res) {
        byte[] bytes = Converter.bigIntegerToBytes(res);
        return shamirSecretSharing.split(bytes);
    }

    private BigInteger recoverSecret(ArrayList<String> shares) {
        byte[] bytes = shamirSecretSharing.join(shares);
        return Converter.bytesToBigInteger(bytes);
    }

    @Benchmark
    public void sssSplitBenchmark(Blackhole blackhole) {
        for(int i = 0; i< loopNum; i++) {
            blackhole.consume(genSecretShares(testInteger));
        }
    }

    @Benchmark
    public void sssJoinBenchmark(Blackhole blackhole) {
        for(int i = 0; i< loopNum; i++) {
            blackhole.consume(recoverSecret(testSecrets));
        }
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("benchmark");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(SSSBenchmark.class.getSimpleName())
                .output("benchmark/sss_benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
