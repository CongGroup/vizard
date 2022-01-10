package test.org.conggroup.vizard.dataowner;

import org.conggroup.vizard.crypto.components.dpf.DPFGenerator;
import org.conggroup.vizard.crypto.components.secretsharing.AdditiveSecretSharing;
import org.conggroup.vizard.crypto.utils.Digest;
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
@Warmup(iterations = 3)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class DataOwnerBenchmark {

    @Param({ "100", "1000", "10000", "100000" })
    public int iterations;

    public DPFGenerator dpfGenerator;

    public String description;

    @Setup
    public void setupDPF() {
        this.dpfGenerator = new DPFGenerator();
        this.description = "defaultPolicy";
    }

    @Benchmark
    public void secretSharingBenchmark_1(Blackhole blackhole) {
        for(int i=0; i<iterations; i++) {
            blackhole.consume(AdditiveSecretSharing.gen(BigInteger.ONE));
        }
    }

    @Benchmark
    public void secretSharingBenchmark_x(Blackhole blackhole) {
        for(int i=0; i<iterations; i++) {
            blackhole.consume(AdditiveSecretSharing.gen(BigInteger.valueOf(i)));
        }
    }

    @Benchmark
    public void secretSharingBenchmark_x2(Blackhole blackhole) {
        for(int i=0; i<iterations; i++) {
            blackhole.consume(AdditiveSecretSharing.gen(BigInteger.valueOf((long) i*i)));
        }
    }

    @Benchmark
    public void dpfBenchmark(Blackhole blackhole) {
        for(int i=0; i<iterations; i++) {
            long hash = Digest.crc32(description);
            blackhole.consume(dpfGenerator.gen(0, 0, hash, "single", false));
        }
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("eval");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(DataOwnerBenchmark.class.getSimpleName())
                .output("eval/data_provider_benchmark.txt")
                .build();
        new Runner(options).run();
    }
}
