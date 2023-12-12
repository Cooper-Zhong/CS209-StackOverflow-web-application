package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.dto.TagViewCount;
import cn.sustech.cs209backend.service.QuestionService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }


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

    @GetMapping("/topKByViewCount/{k}")
    public List<TagViewCount> topKTagsByViewCount(@PathVariable int k) {
        return questionService.topKTagsByViewCount(k);
    }

    @GetMapping("/topKByAnswerCount/{k}")
    public List<JSONObject> topKTagsByAnswerCount(@PathVariable int k) {
        return questionService.topKTagsByAnswerCount(k);
    }

    @GetMapping("/topKByQuestionCount/{k}")
    public List<JSONObject> topKTagsByQuestionCount(@PathVariable int k) {
        return questionService.topKTagsByQuestionCount(k);
    }

    @GetMapping("/topKByAvgScore/{k}")
    public List<JSONObject> topKTagsByAvgScore(@PathVariable int k) {
        return questionService.topKTagsByAvgScore(k);
    }

    // intimacy ------------------------------------------------------

    @GetMapping("/intimacy")
    public List<JSONObject> intimacy(@RequestParam("k") Integer k, @RequestParam("topic") String topic) {
        return questionService.KIntimateTags(topic, k);
    }

    @GetMapping("/similar")
    public List<JSONObject> similar(@RequestParam("k") Integer k, @RequestParam("topic") String topic) {
        return questionService.KSimilarTags(topic, k);
    }


}
