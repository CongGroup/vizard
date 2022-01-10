package test.org.conggroup.vizard.resultreleasecontrol;

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

    @Param({ "0.67" })
    public float shareRatio;

    private ShamirSecretSharing shamirSecretSharing;

    private ArrayList<String> testSecrets;

    private final BigInteger m = BigInteger.ONE.shiftLeft(64);

    @Setup
    public void init() {
        shamirSecretSharing = new ShamirSecretSharing(shareNum, shareRatio);
        testSecrets = genSecretShares(BigInteger.ONE);
    }

    private ArrayList<String> genSecretShares(BigInteger res) {
        byte[] bytes = Converter.bigIntegerToBytes(res);
        return shamirSecretSharing.split(bytes);
    }

    private BigInteger recoverSecret(ArrayList<String> shares) {
        byte[] bytes1 = shamirSecretSharing.join(shares);
        byte[] bytes2 = shamirSecretSharing.join(shares);
        BigInteger b1 = Converter.bytesToBigInteger(bytes1);
        BigInteger b2 = Converter.bytesToBigInteger(bytes2);
        return b1.add(b2).mod(m);
    }

    @Benchmark
    public void sssJoinBenchmark(Blackhole blackhole) {
        blackhole.consume(recoverSecret(testSecrets));
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("benchmark");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(SSSBenchmark.class.getSimpleName())
                .output("benchmark/consumer_sss_benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
