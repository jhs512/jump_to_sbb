package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.Answer;
import com.ll.exam.sbb.user.SiteUser;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private String subject;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private List<Answer> answerList;
    private SiteUser author;
    private Integer hitCount;
    private Integer likeCount;

    @QueryProjection
    public QuestionDto(Long id, String subject, String content, LocalDateTime createDate, LocalDateTime modifyDate, List<Answer> answerList, SiteUser author, Integer hitCount, Integer likeCount) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.answerList = answerList;
        this.author = author;
        this.hitCount = hitCount;
        this.likeCount = likeCount;
    }

    @QueryProjection
    public QuestionDto(Long id, String subject, String content, LocalDateTime createDate, LocalDateTime modifyDate) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    @QueryProjection
    public QuestionDto(Long id, String subject, String content, LocalDateTime createDate, LocalDateTime modifyDate, List<Answer> answerList) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.answerList = answerList;
    }
}
