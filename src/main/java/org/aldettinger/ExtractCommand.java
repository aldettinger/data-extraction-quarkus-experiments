package org.aldettinger;

import static org.apache.commons.io.IOUtils.resourceToString;

import java.io.IOException;
import java.time.Duration;

import com.github.dockerjava.zerodep.shaded.org.apache.commons.codec.Charsets;

import dev.langchain4j.model.chat.ChatLanguageModel;
import io.quarkiverse.langchain4j.ollama.OllamaChatLanguageModel;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import picocli.CommandLine;

@CommandLine.Command
public class ExtractCommand implements Runnable {

    @Inject
    CustomPojoExtractionService customPojoExtractionService;

    @Named
    ChatLanguageModel chatLanguageModel() {
        String modelServingUrl = String.format("http://%s:%d", "localhost", 11434);

        ChatLanguageModel model = OllamaChatLanguageModel.builder()
                .baseUrl(modelServingUrl)
                .model("codellama")
                //.temperature(0.0) TODO, add to quarkus-langchain4j ?
                .format("json")
                .timeout(Duration.ofMinutes(1L))
                .build();
        
        return model;
    }

    public ExtractCommand() {
    }

    @Override
    public void run() {
        String[] conversationResourceNames = {
                "01_sarah-london-10-07-1986-satisfied.txt", "02_john-doe-01-11-2001-unsatisfied.txt",
                "03_kate-boss-13-08-1999-satisfied.txt" };

        for (String conversationResourceName : conversationResourceNames) {
            try {
                String conversation = resourceToString(String.format("/texts/%s", conversationResourceName), Charsets.UTF_8);
                long begin = System.currentTimeMillis();
                CustomPojoExtractionService.CustomPojo answer = customPojoExtractionService.extractFromText(conversation);
                long duration = System.currentTimeMillis() - begin;

                System.out.println(toPrettyFormat(answer));
                System.out.println(
                        String.format("----- Inference lasted %.1fs ------------------------------", duration / 1000.0));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hello extraction world !");
    }

    private final static String FORMAT = "****************************************\n"
                                         + "customerSatisfied: %s\n"
                                         + "customerName: %s\n"
                                         + "customerBirthday: %td %tB %tY\n"
                                         + "summary: %s\n"
                                         + "****************************************\n";

    public static String toPrettyFormat(CustomPojoExtractionService.CustomPojo extract) {
        return String.format(FORMAT, extract.customerSatisfied, extract.customerName, extract.customerBirthday,
                extract.customerBirthday, extract.customerBirthday, extract.summary);
    }
}
