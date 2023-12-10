package cn.sustech.cs209backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "questions")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Question {

    @Id
    @Column(name = "question_id")
    private int questionId;

    @JsonIgnoreProperties("user") // 忽略user字段
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_questions_users"))
//    private int accountId;
    private User user;


    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int answerCount;

    @Column(nullable = false)
    private int viewCount;

    private String contentLicense;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Date lastActivityDate;

    @Column(nullable = false)
    private Date lastEditDate;

    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private boolean isAnswered;

    @ManyToMany
    @JoinTable(name = "questions_tags",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name"))
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(name = "questions_bugs",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "bug_name"))
    private List<Bug> bugs;

    @ManyToMany
    @JoinTable(name = "questions_apis",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "api_name"))
    private List<Api> apis;


    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Answer> answers;

}
