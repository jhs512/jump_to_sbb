package com.ll.exam.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {
    Page<Question> getQslList(String kw, Pageable pageable);
}
