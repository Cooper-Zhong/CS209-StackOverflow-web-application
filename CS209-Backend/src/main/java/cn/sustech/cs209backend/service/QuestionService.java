package cn.sustech.cs209backend.service;

import cn.sustech.cs209backend.dto.BugViewCount;
import cn.sustech.cs209backend.dto.TagViewCount;
import cn.sustech.cs209backend.entity.Question;
import cn.sustech.cs209backend.entity.Tag;
import cn.sustech.cs209backend.repo.QuestionRepo;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public List<Question> allQuestions() {
        return questionRepo.findAll();
    }

    public Question questionByQuestionID(int questionID) {
        return questionRepo.findById(questionID).orElse(null);
    }

    public List<Question> noAnswer(Date from, Date end) {
        return questionRepo.noAnswer(from, end);
    }


    // topic ------------------------------------------------------

    public List<Question> questionByTagName(String tagName) {
        return questionRepo.findByTagName(tagName);
    }

    public BigDecimal topicAvgViewCount(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        BigDecimal sum = BigDecimal.ZERO;
        for (Question question : questions) {
            sum = sum.add(BigDecimal.valueOf(question.getViewCount()));
        }
        if (sum.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return sum.divide(BigDecimal.valueOf(questions.size()), RoundingMode.HALF_UP);
    }

    public double topicAvgAnswerCount(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public int topicTotalAnswerCount(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        return sum;
    }

    public int topicQuestionCount(String topic) {
        return questionRepo.findByTagName(topic).size();
    }

    public int topicTotalScore(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        return sum;
    }

    public double topicAvgScore(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public List<TagViewCount> topKPopularTags(int k) {
        List<Map> result = questionRepo.topKPopularTags(k);
        List<TagViewCount> tagViewCounts = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("tag_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_view_count");
            tagViewCounts.add(new TagViewCount(tagName, average_view_count.intValue()));
        }
        return tagViewCounts;
    }

    // bug ------------------------------------------------------

    public List<Question> questionsByBugName(String bugName) {
        return questionRepo.findByBugName(bugName);
    }

    public BigDecimal bugNameAvgViewCount(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        BigDecimal sum = BigDecimal.ZERO;
        for (Question question : questions) {
            sum = sum.add(BigDecimal.valueOf(question.getViewCount()));
        }
        if (sum.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return sum.divide(BigDecimal.valueOf(questions.size()), RoundingMode.HALF_UP);
    }

    public double bugNameAvgAnswerCount(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public int bugNameTotalAnswerCount(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        return sum;
    }

    public int bugNameQuestionCount(String bugName) {
        return questionRepo.findByBugName(bugName).size();
    }

    public int bugNameTotalScore(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        return sum;
    }

    public double bugNameAvgScore(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public List<BugViewCount> topKPopularBugs(int k) {
        List<Map> result = questionRepo.topKPopularBugs(k);
        List<BugViewCount> bugViewCounts = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_view_count");
            bugViewCounts.add(new BugViewCount(tagName, average_view_count.intValue()));
        }
        return bugViewCounts;
    }


}
