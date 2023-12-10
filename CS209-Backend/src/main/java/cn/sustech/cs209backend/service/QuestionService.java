package cn.sustech.cs209backend.service;

import cn.sustech.cs209backend.entity.Question;
import cn.sustech.cs209backend.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

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

    public List<Question> questionByTagName(String tagName) {
        return questionRepo.findByTagName(tagName);
    }

    public List<Question> noAnswer(Date from, Date end) {
        return questionRepo.noAnswer(from, end);
    }

    public BigDecimal avgViewCount(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        BigDecimal sum = BigDecimal.ZERO;
        for (Question question : questions) {
            sum = sum.add(BigDecimal.valueOf(question.getViewCount()));
        }
        if (sum.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return sum.divide(BigDecimal.valueOf(questions.size()), RoundingMode.HALF_UP);
    }

    public double avgAnswerCount(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public int totalAnswerCount(String topic) {
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

    public int totalScore(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        return sum;
    }

    public double avgScore(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }


}
