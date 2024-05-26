package guru.ysy.aifunc.services;

import guru.ysy.aifunc.models.Answer;
import guru.ysy.aifunc.models.Question;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/26 15:16
 * @Email: fred.zhen@gmail.com
 */
public interface AiService {
    Answer getAnswer(Question question);
}
