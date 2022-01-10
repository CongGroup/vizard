package test.org.conggroup.vizard.server;

import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.server.ServerApplication;
import org.conggroup.vizard.server.service.message.EncryptedMessageProcessor;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 0)
@Measurement(iterations = 3, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class AddStoreBenchmark {

    @Param({ "100", "1000", "10000", "100000" })
    public int messageNum;

    private ConfigurableApplicationContext context;

    private EncryptedMessageProcessor encryptedMessageProcessor;

    @Setup
    public void init() {
        context = SpringApplication.run(ServerApplication.class);
        encryptedMessageProcessor = context.getBean(EncryptedMessageProcessor.class);
    }

    @Setup(Level.Invocation)
    public void initMessage() {
        for(int i = 0; i<messageNum; i++) {
            VizardMessage vizardMessage = new VizardMessage();
            encryptedMessageProcessor.addToQueue(vizardMessage);
        }
    }

    @TearDown
    public void down() {
        context.close();
    }

    @Benchmark
    public void addBenchmark(Blackhole blackhole) {
        for(int i = 0; i< messageNum; i++) {
            VizardMessage message = new VizardMessage();
            blackhole.consume(encryptedMessageProcessor.processMessageBenchmark(message));
        }
    }

    @Benchmark
    public void addStoreBenchmark() {
        for(int i = 0; i< messageNum; i++) {
            VizardMessage message = new VizardMessage();
            encryptedMessageProcessor.processAnotherServerMessage(message);
        }
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("benchmark");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(AddStoreBenchmark.class.getSimpleName())
                .output("benchmark/add_store_benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
