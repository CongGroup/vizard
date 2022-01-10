package test.org.conggroup.vizard.dataowner;

import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.dataowner.DataOwnerApplication;
import org.conggroup.vizard.dataowner.service.RawMessageProducer;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@Fork(value = 1)
@Warmup(iterations = 1)
@Measurement(iterations = 3, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class KafkaProduceThroughputBenchmark {

    private ConfigurableApplicationContext context;

    private RawMessageProducer rawMessageProducer;

    private VizardMessage vizardMessage;

    @Setup
    public void init() {
        context = SpringApplication.run(DataOwnerApplication.class);
        rawMessageProducer = context.getBean(RawMessageProducer.class);
        vizardMessage = new VizardMessage();
    }

    @TearDown
    public void down() {
        context.close();
    }

    @Benchmark
    public void singleMachineBenchmark() throws ExecutionException, InterruptedException {
        rawMessageProducer.kafkaProducerZero.send("test.kafka.produce.throughput",vizardMessage.ownerId.toString(),vizardMessage).get();
    }

    @Benchmark
    public void dualMachineBenchmark() throws ExecutionException, InterruptedException {
        rawMessageProducer.kafkaProducerZero.send("test.kafka.produce.throughput",vizardMessage.ownerId.toString(),vizardMessage).get();
        rawMessageProducer.kafkaProducerOne.send("test.kafka.produce.throughput",vizardMessage.ownerId.toString(),vizardMessage).get();
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("eval");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(KafkaProduceThroughputBenchmark.class.getSimpleName())
                .output("eval/kafka_produce_throughput_benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
