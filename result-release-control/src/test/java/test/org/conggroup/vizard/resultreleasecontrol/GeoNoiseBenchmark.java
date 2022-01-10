package test.org.conggroup.vizard.resultreleasecontrol;

import org.conggroup.vizard.crypto.utils.RandomGenerator;
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
public class GeoNoiseBenchmark {

    @Param({ "10", "20", "30" })
    public int noiseNum;

    @Benchmark
    public void noiseGenerationBenchmark(Blackhole blackhole) {
        for(int i=0; i<noiseNum; i++) {
            blackhole.consume(RandomGenerator.randomGeometricDist(50));
        }
    }

    @Benchmark
    public void noiseAdditionBenchmark(Blackhole blackhole) {
        for(int i=0; i<noiseNum; i++) {
            blackhole.consume(BigInteger.ONE.add(BigInteger.ONE));
        }
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("benchmark");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(GeoNoiseBenchmark.class.getSimpleName())
                .output("benchmark/geo_noise_benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
