package test.org.conggroup.vizard.server;

import org.conggroup.vizard.crypto.components.dpf.DPFEvaluator;
import org.conggroup.vizard.crypto.components.dpf.DPFKey;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.server.ServerApplication;
import org.conggroup.vizard.server.service.dpf.DPFProcessor;
import org.conggroup.vizard.server.service.persistence.DPFKeyPersistenceUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 1)
@Measurement(iterations = 3, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class DPFEvalBenchmark {

    @Param({ "999", "9999", "99999" })
    public int maxOwnerId;

    @Param({ "100000" })
    public int linearOROwnerId;

    @Param({ "100001" })
    public int hashOROwnerId;

    private ConfigurableApplicationContext context;

    private Operator operator;

    private DPFKeyPersistenceUtil dpfKeyPersistenceUtil;

    private DPFEvaluator dpfEvaluator;

    private DPFProcessor dpfProcessor;

    @Setup
    public void init() {
        context = SpringApplication.run(ServerApplication.class);
        operator = context.getBean(Operator.class);
        dpfKeyPersistenceUtil = context.getBean(DPFKeyPersistenceUtil.class);
        dpfEvaluator = context.getBean(DPFEvaluator.class);
        dpfProcessor = context.getBean(DPFProcessor.class);
    }

    @TearDown
    public void down() {
        context.close();
    }

    @Benchmark
    public void dpfEvalBenchmark(Blackhole blackhole) {
        String desc = "defaultPolicy";
        HashMap<Integer, ArrayList<DPFKey>> map = dpfProcessor.loadDPFKeys();
        for(int ownerId=0; ownerId<=maxOwnerId; ownerId++) {
            ArrayList<DPFKey> keys = map.get(ownerId);
            blackhole.consume(dpfProcessor.eval(desc, maxOwnerId, keys));
        }
    }

    @Benchmark
    public void dpfEvalAndSumBenchmark(Blackhole blackhole) {
        String desc = "defaultPolicy";
        HashMap<Integer, ArrayList<DPFKey>> map = dpfProcessor.loadDPFKeys();
        BigInteger sum = BigInteger.ZERO;
        for(int ownerId=0; ownerId<=maxOwnerId; ownerId++) {
            ArrayList<DPFKey> keys = map.get(ownerId);
            sum = sum.add(dpfProcessor.eval(desc, maxOwnerId, keys));
        }
        BigInteger M = BigInteger.ONE.shiftLeft(64);
        blackhole.consume(sum.mod(M));
    }

    @Benchmark
    public void linearORBenchmark(Blackhole blackhole) {
        String description = "defaultPolicy";
        ArrayList<DPFKey> keys = dpfKeyPersistenceUtil.load(linearOROwnerId);
        ArrayList<BigInteger> Ts = new ArrayList<BigInteger>(keys.size());
        for(DPFKey key: keys) {
            Ts.add(dpfEvaluator.eval(description, key, operator));
        }
        blackhole.consume(Ts);
    }

    @Benchmark
    public void hashORBenchmark(Blackhole blackhole) {
        String description = "defaultPolicy";
        ArrayList<Integer> indexes = new ArrayList<Integer>(3);
        for(int i=0; i<3; i++) {
            indexes.add(dpfProcessor.calcIndex(description, i, 75));
        }
        ArrayList<DPFKey> dpfKeys = dpfKeyPersistenceUtil.loadWithIndexes(hashOROwnerId, indexes);
        ArrayList<BigInteger> Ts = new ArrayList<BigInteger>(dpfKeys.size());
        for(DPFKey key: dpfKeys) {
            Ts.add(dpfEvaluator.eval(description, key, operator));
        }
        blackhole.consume(Ts);
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("benchmark");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(DPFEvalBenchmark.class.getSimpleName())
                .output("benchmark/dpf-eval-benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
