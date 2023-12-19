package cn.sustech.cs209backend.repo;

import cn.sustech.cs209backend.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BugRepo extends JpaRepository<Bug, String> {

    // category information

    @Query(value = "select count(*) " +
            "from bug_type bt " +
            "join bugs on bt.bug_name = bugs.bug_name and bt.type = :category " +
            "join questions_bugs qb on bugs.bug_name = qb.bug_name " +
            "group by bt.type; ", nativeQuery = true)
    int categoryQuestionCount(String category);

    @Query(value = "select avg(view_count) " +
            "from bug_type bt " +
            "join bugs on bt.bug_name = bugs.bug_name and bt.type = :category " +
            "join questions_bugs qb on bugs.bug_name = qb.bug_name " +
            "join questions q on qb.question_id = q.question_id " +
            "group by bt.type; ", nativeQuery = true)
    double categoryAvgViewCount(String category);

    @Query(value = "select avg(answer_count) " +
            "from bug_type bt " +
            "join bugs on bt.bug_name = bugs.bug_name and bt.type = :category " +
            "join questions_bugs qb on bugs.bug_name = qb.bug_name " +
            "join questions q on qb.question_id = q.question_id " +
            "group by bt.type; ", nativeQuery = true)
    double categoryAvgAnswerCount(String category);

    @Query(value = "select avg(score) " +
            "from bug_type bt " +
            "join bugs on bt.bug_name = bugs.bug_name and bt.type = :category " +
            "join questions_bugs qb on bugs.bug_name = qb.bug_name " +
            "join questions q on qb.question_id = q.question_id " +
            "group by bt.type; ", nativeQuery = true)
    double categoryAvgScore(String category);


}
