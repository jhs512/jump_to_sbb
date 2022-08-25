package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.Answer;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.ll.exam.sbb.answer.QAnswer.answer;
import static com.ll.exam.sbb.question.QQuestion.create;
import static com.ll.exam.sbb.question.QQuestion.question;
import static com.ll.exam.sbb.user.QSiteUser.siteUser;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Question findQslQuestionById(long id) {
        Question q = jpaQueryFactory.selectFrom(question)
                .where(question.id.eq(id))
                .fetchOne();

        return q;
    }

    @Override
    public Question findQslV2QuestionById(long id) {
        Question q1 = jpaQueryFactory
                .select(create(question.id, question.subject, Expressions.constant(10)))
                .from(question)
                .where(question.id.eq(id))
                .fetchOne();

        return q1;
    }

    @Override
    public Question findQslV3QuestionById(long id) {
        Question q1 = jpaQueryFactory
                .select(create(question.id, question.subject, question.voter.size()))
                .from(question)
                .where(question.id.eq(id))
                .fetchOne();

        return q1;
    }

    @Override
    public QuestionDto findQslV4QuestionById(long id) {
        QuestionDto q1 = jpaQueryFactory
                .select(new QQuestionDto(
                        question.id,
                        question.subject,
                        question.content,
                        question.createDate,
                        question.modifyDate
                ))
                .from(question)
                .where(question.id.eq(id))
                .fetchOne();

        List<Answer> fetch = jpaQueryFactory.select(answer)
                .from(answer)
                .where(answer.question.eq(question))
                .fetch();

        q1.setAnswerList(fetch);

        return q1;
    }

    @Override
    public Page<Question> getDslList(String kw, Pageable pageable) {

        JPAQuery<Question> query = jpaQueryFactory
                .select(question)
                .from(question)
                .leftJoin(question.author).fetchJoin()
                .leftJoin(question.answerList, answer)
                .leftJoin(answer.author, siteUser)
                .where(
                        question.subject.contains(kw)
                                .or(question.content.contains(kw))
                                .or(question.author.username.contains(kw))
                                .or(answer.content.contains(kw))
                                .or(answer.author.username.contains(kw))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(question);

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(question.getType(), question.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<Question> fetch = query.fetch();

        /**
         * count
         */
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(question.count())
                .leftJoin(question.author).fetchJoin()
                .leftJoin(question.answerList, answer)
                .leftJoin(answer.author, siteUser)
                .where(
                        question.subject.contains(kw)
                                .or(question.content.contains(kw))
                                .or(question.author.username.contains(kw))
                                .or(answer.content.contains(kw))
                                .or(answer.author.username.contains(kw))
                )
                .from(question);

        return PageableExecutionUtils.getPage(fetch, pageable, countQuery::fetchOne);
    }
}