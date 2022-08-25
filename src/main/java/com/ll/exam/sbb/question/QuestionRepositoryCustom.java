package com.ll.exam.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {
    Question findQslQuestionById(long id);

    Question findQslV2QuestionById(long id);

    Question findQslV3QuestionById(long id);

    QuestionDto findQslV4QuestionById(long id);

    Page<Question> getDslList(String kw, Pageable pageable);
}
