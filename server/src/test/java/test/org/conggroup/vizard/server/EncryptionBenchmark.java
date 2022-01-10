package test.org.conggroup.vizard.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.server.ServerApplication;
import org.conggroup.vizard.server.service.message.RawMessageProcessor;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 1)
@Measurement(iterations = 3, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class EncryptionBenchmark {

    @Param({ "100", "1000", "10000", "100000" })
    public int messageNum;

    private ConfigurableApplicationContext context;

    private RawMessageProcessor rawMessageProcessor;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Setup
    public void init() {
        context = SpringApplication.run(ServerApplication.class);
        rawMessageProcessor = context.getBean(RawMessageProcessor.class);
    }

    @TearDown
    public void down() {
        context.close();
    }

    private VizardMessage jsonToMessage(String json) {
        VizardMessage message = null;
        try {
            message = objectMapper.readValue(json, VizardMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Benchmark
    public void encOnlyBenchmark(Blackhole blackhole) {
        String json = "{\"ownerId\":0, \"seqIndex\":0, \"c\":\"0\"}";
        for(int i = 0; i< messageNum; i++) {
            VizardMessage message = jsonToMessage(json);
            blackhole.consume(rawMessageProcessor.encOnlyBenchmark(message));
        }
    }

    @Benchmark
    public void encAndProduceBenchmark() {
        String json = "{\"ownerId\":0, \"seqIndex\":0, \"c\":\"0\"}";
        for(int i = 0; i< messageNum; i++) {
            VizardMessage message = jsonToMessage(json);
            rawMessageProcessor.process(message);
        }
    }

    public static void main(String[] args) throws RunnerException {
        File folder = new File("benchmark");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        Options options = new OptionsBuilder()
                .include(EncryptionBenchmark.class.getSimpleName())
                .output("benchmark/encryption_benchmark.txt")
                .build();
        new Runner(options).run();
    }

}
