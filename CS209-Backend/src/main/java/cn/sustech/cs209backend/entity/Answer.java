package cn.sustech.cs209backend.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import cn.sustech.cs209backend.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "answers")
public class Answer {

    @Id
    @Column(name = "answer_id")
    private int answerId;

    @Column(nullable = false)
    private Date lastActivityDate;

    @Column(nullable = false)
    private Date lastEditDate;

    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private boolean isAccepted;

    private String contentLicense;

    @JoinColumn(name = "question_id", referencedColumnName = "question_id",
            foreignKey = @ForeignKey(name = "fk_answers_questions"))
    @JsonIdentityReference(alwaysAsId = true) //当序列化 Answer 实体时，只会包含 Question 的 questionId 属性
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "questionId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

//    private int questionId;


    @Column(nullable = false)
    private String body;

    @JoinColumn(name = "account_id", referencedColumnName = "account_id",
            foreignKey = @ForeignKey(name = "fk_answers_users"))
    @ManyToOne
    @JsonIgnore
//    private int accountId;
    private User user;


    @ManyToMany
    @JoinTable(name = "answers_bugs",
            joinColumns = @JoinColumn(name = "answer_id", referencedColumnName = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "bug_name", referencedColumnName = "bug_name"))
    private List<Bug> bugs;

    @ManyToMany
    @JoinTable(name = "answers_apis",
            joinColumns = @JoinColumn(name = "answer_id", referencedColumnName = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "api_name", referencedColumnName = "api_name"))
    private List<Api> apis;

}
