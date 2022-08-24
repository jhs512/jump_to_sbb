package com.ll.exam.sbb.question;

import com.ll.exam.sbb.user.QSiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ll.exam.sbb.answer.QAnswer.answer;
import static com.ll.exam.sbb.question.QQuestion.question;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Question> getQslList(String kw, Pageable pageable) {
        QSiteUser questionAuthor = new QSiteUser("questionAuthor");
        QSiteUser answerAuthor = new QSiteUser("answerAuthor");

        List<Question> questions = jpaQueryFactory
                .selectFrom(question)
                .leftJoin(questionAuthor).fetchJoin()
                .on(
                        question.author.eq(questionAuthor)
                )
                .leftJoin(answer).fetchJoin()
                .on(
                        question.eq(answer.question)
                )
                .leftJoin(answerAuthor).fetchJoin()
                .on(
                        answer.author.eq(answerAuthor)
                )
                .where(
                        question.subject.contains(kw)
                                .or(question.content.contains(kw))
                                .or(questionAuthor.username.contains(kw))
                                .or(answer.content.contains(kw))
                                .or(answerAuthor.username.contains(kw))
                )
                .fetchAll().fetch();

        return null;
    }
}
