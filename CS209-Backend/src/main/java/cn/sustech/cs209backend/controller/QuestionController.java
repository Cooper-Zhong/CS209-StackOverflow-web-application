package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.entity.Question;
import cn.sustech.cs209backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public List<Question> allQuestions() {
        return questionService.allQuestions();
    }

    @GetMapping("/findById/{questionID}")
    public Question questionByQuestionID(@PathVariable int questionID) {
        return questionService.questionByQuestionID(questionID);
    }

    @GetMapping("/noAnswer")
    public List<Question> noAnswer(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date end) {
        return questionService.noAnswer(from, end);
    }

    @GetMapping("/findByTagName/{tagName}")
    public List<Question> questionByTagName(@PathVariable String tagName) {
        return questionService.questionByTagName(tagName);
    }

    @GetMapping("/avgViewCount/{topic}")
    public double avgViewCount(@PathVariable String topic) {
        return questionService.avgViewCount(topic).intValue();
    }

    @GetMapping("/avgAnswerCount/{topic}")
    public double avgAnswerCount(@PathVariable String topic) {
        return questionService.avgAnswerCount(topic);
    }

    @GetMapping("/totalAnswerCount/{topic}")
    public int totalAnswerCount(@PathVariable String topic) {
        return questionService.totalAnswerCount(topic);
    }

    @GetMapping("/topicQuestionCount/{topic}")
    public int topicQuestionCount(@PathVariable String topic) {
        return questionService.topicQuestionCount(topic);
    }

    @GetMapping("/totalScore/{topic}")
    public int totalScore(@PathVariable String topic) {
        return questionService.totalScore(topic);
    }

    @GetMapping("/avgScore/{topic}")
    public double avgScore(@PathVariable String topic) {
        return questionService.avgScore(topic);
    }
}
