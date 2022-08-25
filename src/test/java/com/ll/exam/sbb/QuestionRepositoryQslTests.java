package com.ll.exam.sbb;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionDto;
import com.ll.exam.sbb.question.QuestionRepository;
import com.ll.exam.sbb.question.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class QuestionRepositoryQslTests {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("Qsl 질문 1개")
    void t1() {
        Question question = questionRepository.findQslQuestionById(1);
    }

    @Test
    @DisplayName("Qsl 질문 1개, 2")
    void t2() {
        Question question = questionRepository.findQslV2QuestionById(1);

        System.out.println(question);
    }

    @Test
    @DisplayName("Qsl 질문 1개, 3")
    void t3() {
        Question question = questionRepository.findQslV3QuestionById(1);

        System.out.println(question);
    }

    @Test
    @DisplayName("Qsl 질문 1개, 3")
    void t4() {
        QuestionDto question = questionRepository.findQslV4QuestionById(1);

        System.out.println(question);
        System.out.println(question.getAnswerList().get(0));
    }

    @Test
    void t5() {
        Page<Question> questionPage = questionService.getList(" ", 0, "OLD");
    }

    @Test
    void t6() {
        Page<Question> questionPage = questionService.getDslList(" ", 0, "OLD");
    }
}
