package cn.sustech.cs209backend.repo;

import cn.sustech.cs209backend.dto.TagViewCount;
import cn.sustech.cs209backend.entity.Question;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {


    @Query(value = "SELECT q FROM Question q JOIN q.tags t WHERE t.tagName ilike %:tagName%")
//    @Query(value = "select q.question_id, account_id, answer_count, body, content_license, creation_date, " +
//            "is_answered, last_activity_date, last_edit_date, link, score, title, view_count,tag_name " +
//            "from questions q join questions_tags qt on q.question_id = qt.question_id " +
//            "where qt.tag_name like %:tagName%", nativeQuery = true)
    List<Question> findByTagName(String tagName);

    @Query(value = "select q from Question q join q.bugs b where b.bugName ilike %:bugName%")
    List<Question> findByBugName(String bugName);

//    @Query(value = "select qt.tag_name, avg(q.view_count) as average_view_count\n" +
//            "from questions_tags qt\n" +
//            "         join questions q on qt.question_id = q.question_id\n" +
//            "group by qt.tag_name\n" +
//            "order by average_view_count desc limit ?1;", nativeQuery = true)
//    List<TagViewCount> topKPopularTags(int k);


    @Query(value = "select qt.tag_name, avg(q.view_count) as average_view_count " +
            "from questions_tags qt " +
            "join questions q on qt.question_id = q.question_id " +
            "group by qt.tag_name " +
            "order by average_view_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKPopularTags(@Param("k") Integer k);


    @Query(value = "select qb.bug_name, avg(q.view_count) as average_view_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "group by qb.bug_name " +
            "order by average_view_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKPopularBugs(@Param("k") Integer k);


    @Query(value = "SELECT q FROM Question q WHERE q.creationDate >= :from AND q.creationDate <= :end AND q.answerCount = 0")
    List<Question> noAnswer(Date from, Date end);


}
