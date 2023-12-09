package cn.sustech.cs209backend.entity;


import jakarta.persistence.*;
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
@Table(name = "comments")
@Builder
public class Comment {

    @Id
    private int commentId;

    @Column(nullable = false)
    private boolean edited;

    //    @ManyToOne
//    @JoinColumn(name = "post_id", referencedColumnName = "answer_id",
//            foreignKey = @ForeignKey(name = "fk_comments_answers"))
    @Column(nullable = false)
    private int postId;


    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private Timestamp creationDate;

    @Column(nullable = false)
    private int score;

    private String contentLicense;

//    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "account_id",
            foreignKey = @ForeignKey(name = "fk_comments_users"))
    private int accountId;

}
