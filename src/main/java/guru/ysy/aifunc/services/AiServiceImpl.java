package guru.ysy.aifunc.services;

import guru.ysy.aifunc.functions.WeatherService;
import guru.ysy.aifunc.models.Answer;
import guru.ysy.aifunc.models.Question;
import guru.ysy.aifunc.models.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatClient;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/26 15:16
 * @Email: fred.zhen@gmail.com
 */
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("classpath:/templates/system-message.st")
    private Resource systemMessageTemplate;

    // Here we need specifically use  Mistral AI Chat Client
    final MistralAiChatClient mistralAiChatClient;

    @Override
    public Answer getAnswer(Question question) {

        MistralAiChatOptions promptOptions = MistralAiChatOptions.builder()
                .withFunctionCallbacks(
                    List.of(
                        FunctionCallbackWrapper.builder(new WeatherService(weatherApiKey))
                                .withName("CurrentWeather")
                                .withDescription("Get the current weather for a location")
                                .withResponseConverter( response -> {
                                    String schema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                                    String json = ModelOptionsUtils.toJsonString(response);
                                    return schema + "\n" + json;
                                })
                                .build()
                        )
                    )
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();

        Message systemMessage = new SystemPromptTemplate(systemMessageTemplate).createMessage();

        ChatResponse response = mistralAiChatClient.call(
                new Prompt(
                        List.of(userMessage, systemMessage),
                    promptOptions
                    )
                );
        return new Answer(response.getResult().getOutput().getContent());
    }
}
