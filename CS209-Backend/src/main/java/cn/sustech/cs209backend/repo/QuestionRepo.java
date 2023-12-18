package cn.sustech.cs209backend.repo;

import cn.sustech.cs209backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {


    @Query(value = "SELECT q FROM Question q WHERE q.creationDate >= :from AND q.creationDate <= :end AND q.answerCount = 0")
    List<Question> noAnswer(Date from, Date end);

    // tag ------------------------------------------------------
    @Query(value = "SELECT q FROM Question q JOIN q.tags t WHERE t.tagName ilike %:tagName%")
//    @Query(value = "select q.question_id, account_id, answer_count, body, content_license, creation_date, " +
//            "is_answered, last_activity_date, last_edit_date, link, score, title, view_count,tag_name " +
//            "from questions q join questions_tags qt on q.question_id = qt.question_id " +
//            "where qt.tag_name like %:tagName%", nativeQuery = true)
    List<Question> findByTagName(String tagName);

    @Query(value = "select qt.tag_name, avg(q.view_count) as average_view_count " +
            "from questions_tags qt " +
            "join questions q on qt.question_id = q.question_id " +
            "group by qt.tag_name " +
            "order by average_view_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKTagsByViewCount(@Param("k") Integer k);


    @Query(value = "select qt.tag_name, avg(q.answer_count) as average_answer_count " +
            "from questions_tags qt " +
            "join questions q on qt.question_id = q.question_id " +
            "group by qt.tag_name " +
            "order by average_answer_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKTagsByAnswerCount(@Param("k") Integer k);

    @Query(value = "select qt.tag_name, avg(q.score) as average_score " +
            "from questions_tags qt " +
            "join questions q on qt.question_id = q.question_id " +
            "group by qt.tag_name " +
            "order by average_score desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKTagsByAvgScore(@Param("k") Integer k);

    @Query(value = "select qt.tag_name, count(*) as question_count " +
            "from questions_tags qt " +
            "group by qt.tag_name " +
            "order by question_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKTagsByQuestionCount(@Param("k") Integer k);

    // intimacy ------------------------------------------------------

    @Query(value = "select * from calculate_tag_similarity(:tagName,:k);", nativeQuery = true)
    List<Map> topKTagsByIntimacy(@Param("k") Integer k, @Param("tagName") String tagName);

    // 返回k个字面上相似的tag
    @Query(value = "select * from find_similar_tag_name(:tagName,:k);", nativeQuery = true)
    List<Map> KSimilarTags(@Param("k") Integer k, @Param("tagName") String tagName);


    // bug ------------------------------------------------------

    @Query(value = "select q from Question q join q.bugs b where b.bugName ilike %:bugName%")
    List<Question> findByBugName(String bugName);


    @Query(value = "select qb.bug_name, avg(q.answer_count) as average_answer_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "group by qb.bug_name " +
            "order by average_answer_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKBugsByAnswerCount(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, avg(q.view_count) as average_view_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "group by qb.bug_name " +
            "order by average_view_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKBugsByViewCount(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, avg(q.score) as average_score " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "group by qb.bug_name " +
            "order by average_score desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKBugsByAvgScore(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, count(*) as question_count " +
            "from questions_bugs qb " +
            "group by qb.bug_name " +
            "order by question_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKBugsByQuestionCount(@Param("k") Integer k);

    @Query(value = "select bug_name, sum(count) as total_count " +
            "from (select bug_name, count " +
            "from questions_bugs " +
            "union all " +
            "select bug_name, count " +
            "from answers_bugs) as combined_bugs " +
            "group by bug_name " +
            "order by total_count desc " +
            "limit :k", nativeQuery = true)
    List<Map> topKBugsByAppearanceCount(@Param("k") Integer k);



    // exception ------------------------------------------------------

    @Query(value = "select qb.bug_name, avg(q.answer_count) as average_answer_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'exception' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_answer_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKExceptionsByAnswerCount(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, avg(q.view_count) as average_view_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'exception' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_view_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKExceptionsByViewCount(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, avg(q.score) as average_score " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'exception' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_score desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKExceptionsByAvgScore(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, count(*) as question_count " +
            "from questions_bugs qb " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'exception' " + // 添加条件
            "group by qb.bug_name " +
            "order by question_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKExceptionsByQuestionCount(@Param("k") Integer k);




    // syntaxError ------------------------------------------------------

    @Query(value = "select qb.bug_name, avg(q.answer_count) as average_answer_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'SyntaxError' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_answer_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKSyntaxErrorByAnswerCount(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, avg(q.view_count) as average_view_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'SyntaxError' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_view_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKSyntaxErrorByViewCount(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, avg(q.score) as average_score " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'SyntaxError' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_score desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKSyntaxErrorByAvgScore(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, count(*) as question_count " +
            "from questions_bugs qb " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'SyntaxError' " + // 添加条件
            "group by qb.bug_name " +
            "order by question_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKSyntaxErrorByQuestionCount(@Param("k") Integer k);



    // fatalError ------------------------------------------------------

    @Query(value = "select qb.bug_name, avg(q.answer_count) as average_answer_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'FatalError' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_answer_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKFatalErrorByAnswerCount(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, avg(q.view_count) as average_view_count " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'FatalError' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_view_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKFatalErrorByViewCount(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, avg(q.score) as average_score " +
            "from questions_bugs qb " +
            "join questions q on qb.question_id = q.question_id " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'FatalError' " + // 添加条件
            "group by qb.bug_name " +
            "order by average_score desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKFatalErrorByAvgScore(@Param("k") Integer k);

    @Query(value = "select qb.bug_name, count(*) as question_count " +
            "from questions_bugs qb " +
            "join bug_type bt on bt.bug_name = qb.bug_name " + // 添加 join bug_type
            "where bt.type = 'FatalError' " + // 添加条件
            "group by qb.bug_name " +
            "order by question_count desc " +
            "LIMIT :k", nativeQuery = true)
    List<Map> topKFatalErrorByQuestionCount(@Param("k") Integer k);
}
