package cn.sustech.cs209backend.entity;


import jakarta.persistence.*;
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
@Table(name = "comments")
@Builder
public class Comment {

    @Id
    @Column(name = "comment_id")
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
    private Date creationDate;

    @Column(nullable = false)
    private int score;

    private String contentLicense;

//    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id",
            foreignKey = @ForeignKey(name = "fk_comments_users"))
    private int accountId;
//    private User user;

    @ManyToMany
    @JoinTable(name = "comments_bugs",
            joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "bug_name", referencedColumnName = "bug_name"))
    private List<Bug> bugs;

    @ManyToMany
    @JoinTable(name = "comments_apis",
            joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "api_name", referencedColumnName = "api_name"))
    private List<Api> apis;


}
