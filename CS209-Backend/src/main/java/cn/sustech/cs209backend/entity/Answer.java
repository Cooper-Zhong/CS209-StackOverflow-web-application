package cn.sustech.cs209backend.entity;


import jakarta.persistence.*;
import cn.sustech.cs209backend.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "answers")
public class Answer {

    @Id
    private int answerId;

    @Column(nullable = false)

    private Timestamp lastActivityDate;

    @Column(nullable = false)
    private Timestamp lastEditDate;

    @Column(nullable = false)
    private Timestamp creationDate;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private boolean isAccepted;

    private String contentLicense;

    @JoinColumn(name = "question_id", referencedColumnName = "question_id",
            foreignKey = @ForeignKey(name = "fk_answers_questions"))
//    @ManyToOne
    private int questionId;

    @Column(nullable = false)
    private String body;

    @JoinColumn(name = "account_id", referencedColumnName = "account_id",
            foreignKey = @ForeignKey(name = "fk_answers_users"))
//    @ManyToOne
    private int accountId;


}
