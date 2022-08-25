package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.Answer;
import com.ll.exam.sbb.user.SiteUser;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에 없다면, 자동으로 생성되어야 한다.
@ToString
public class Question {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    @Column(length = 200) // varchar(200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL})
    @ToString.Exclude
    private List<Answer> answerList = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private SiteUser author;

    @ManyToMany
    @ToString.Exclude
    Set<SiteUser> voter;

    private Integer hitCount = 0;

    @Transient
    private Integer likeCount = 0;

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        getAnswerList().add(answer);
    }

    @QueryProjection
    public Question(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    @QueryProjection
    public Question(Long id, String subject, Integer likeCount) {
        this.id = id;
        this.subject = subject;
        this.likeCount = likeCount;
    }



}
