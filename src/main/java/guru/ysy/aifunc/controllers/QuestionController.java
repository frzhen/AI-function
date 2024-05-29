package guru.ysy.aifunc.controllers;

import guru.ysy.aifunc.models.Answer;
import guru.ysy.aifunc.models.Question;
import guru.ysy.aifunc.services.AiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/26 15:17
 * @Email: fred.zhen@gmail.com
 */
@RequiredArgsConstructor
@RestController
@Tag(name = "Question Controller", description = "Endpoints for getting current weather with AI")
public class QuestionController {

    private final AiService aiService;

    @PostMapping("/weather")
    public Answer askQuestion(@RequestBody Question question) {
        return aiService.getAnswer(question);
    }
}
