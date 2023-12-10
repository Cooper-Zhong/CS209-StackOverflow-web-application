package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.dto.TagViewCount;
import cn.sustech.cs209backend.entity.Question;
import cn.sustech.cs209backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/avgViewCount/{topic}")
    public double avgViewCount(@PathVariable String topic) {
        return questionService.topicAvgViewCount(topic).intValue();
    }

    @GetMapping("/avgAnswerCount/{topic}")
    public double avgAnswerCount(@PathVariable String topic) {
        return questionService.topicAvgAnswerCount(topic);
    }

    @GetMapping("/totalAnswerCount/{topic}")
    public int totalAnswerCount(@PathVariable String topic) {
        return questionService.topicTotalAnswerCount(topic);
    }

    @GetMapping("/topicQuestionCount/{topic}")
    public int topicQuestionCount(@PathVariable String topic) {
        return questionService.topicQuestionCount(topic);
    }

    @GetMapping("/totalScore/{topic}")
    public int totalScore(@PathVariable String topic) {
        return questionService.topicTotalScore(topic);
    }

    @GetMapping("/avgScore/{topic}")
    public double avgScore(@PathVariable String topic) {
        return questionService.topicAvgScore(topic);
    }

    @GetMapping("/topKTagsByViewCount/{k}")
    public List<TagViewCount> topKTagsByViewCount(@PathVariable int k) {
        return questionService.topKPopularTags(k);
    }
}
