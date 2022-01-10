package test.org.conggroup.vizard.dataconsumer;

import org.conggroup.vizard.crypto.components.pedersen.Commitment;
import org.conggroup.vizard.crypto.components.pedersen.Pedersen;
import org.conggroup.vizard.crypto.components.secretsharing.ShamirSecretSharing;
import org.conggroup.vizard.crypto.utils.Converter;
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
public class RingerBenchmark {

    @Param({ "10", "20", "30" })
    public int shareNum;

    @Param({ "0.67" })
    public float shareRatio;

    private Pedersen pedersen;

    private ShamirSecretSharing shamirSecretSharing;

    private Commitment comm;

    private ArrayList<String> splitC, splitR;

    private final BigInteger m = BigInteger.ONE.shiftLeft(64);

    @Setup
    public void init() {
        pedersen = initPedersen();
        shamirSecretSharing = new ShamirSecretSharing(shareNum, shareRatio);
        comm = pedersen.commit(0, 0L, BigInteger.TWO);
        splitC = genSecretShares(comm.c);
        splitR = genSecretShares(comm.r);
    }

    private Pedersen initPedersen() {
        BigInteger p = new BigInteger("20461323899232065519");
        BigInteger q = new BigInteger("10230661949616032759");
        BigInteger g = new BigInteger("7167227173707595926");
        BigInteger y = new BigInteger("10939128848210874778");
        return new Pedersen(p,q,g,y);
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
    public void ringerBenchmark(Blackhole blackhole) {
        BigInteger c = recoverSecret(splitC);
        BigInteger r = recoverSecret(splitR);
        Commitment comm1 = new Commitment(0, 0L, c, r);
        blackhole.consume(comm.equals(comm1));
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("benchmark");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(RingerBenchmark.class.getSimpleName())
                .output("benchmark/rrc_ringer_benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
