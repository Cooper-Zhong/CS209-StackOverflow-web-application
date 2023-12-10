package cn.sustech.cs209backend.repo;

import cn.sustech.cs209backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

    @Query(value = "SELECT q FROM Question q JOIN q.tags t WHERE t.tagName ilike %:tagName%")
//    @Query(value = "select q.question_id, account_id, answer_count, body, content_license, creation_date, " +
//            "is_answered, last_activity_date, last_edit_date, link, score, title, view_count,tag_name " +
//            "from questions q join questions_tags qt on q.question_id = qt.question_id " +
//            "where qt.tag_name like %:tagName%", nativeQuery = true)
    List<Question> findByTagName(String tagName);

    @Query(value = "SELECT q FROM Question q WHERE q.creationDate >= :from AND q.creationDate <= :end AND q.answerCount = 0")
    List<Question> noAnswer(Date from, Date end);


}
