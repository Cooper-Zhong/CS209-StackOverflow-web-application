package cn.sustech.cs209backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
    private int questionId;

    //    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_questions_users"))
    private int accountId;

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
    private Timestamp lastActivityDate;

    @Column(nullable = false)
    private Timestamp lastEditDate;

    @Column(nullable = false)
    private Timestamp creationDate;

    @Column(nullable = false)
    private boolean isAnswered;

//    @ManyToMany
//    @JoinTable(name = "question_tag",
//            joinColumns = @JoinColumn(name = "question_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_name"))
//    private List<Tag> tags;

}
