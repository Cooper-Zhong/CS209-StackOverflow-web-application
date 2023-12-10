package cn.sustech.cs209backend.repo;

import cn.sustech.cs209backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Integer> {

    @Query(value = "select * from answers where question_id = ?1", nativeQuery = true)
    List<Answer> findByQuestionID(int questionID);

    @Query(value = "select * from answers where answer_id = ?1", nativeQuery = true)
    List<Answer> findByAnswerID(int answerID);

    @Query(value = "select * from answers where creation_date between ?1 and ?2", nativeQuery = true)
    List<Answer> findByDate(Timestamp from, Timestamp end);
}
