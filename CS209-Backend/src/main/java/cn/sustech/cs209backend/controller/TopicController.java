package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.dto.TagViewCount;
import cn.sustech.cs209backend.service.QuestionService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
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
        log.info("TOPIC - Request for function: avgViewCount, input: " + topic);
        return questionService.topicAvgViewCount(topic).intValue();
    }

    @GetMapping("/avgAnswerCount/{topic}")
    public double avgAnswerCount(@PathVariable String topic) {
        log.info("TOPIC - Request for function: avgAnswerCount, input: " + topic);
        return questionService.topicAvgAnswerCount(topic);
    }

    @GetMapping("/totalAnswerCount/{topic}")
    public int totalAnswerCount(@PathVariable String topic) {
        log.info("TOPIC - Request for function: totalAnswerCount, input: " + topic);
        return questionService.topicTotalAnswerCount(topic);
    }

    @GetMapping("/topicQuestionCount/{topic}")
    public int topicQuestionCount(@PathVariable String topic) {
        log.info("TOPIC - Request for function: topicQuestionCount, input: " + topic);
        return questionService.topicQuestionCount(topic);
    }

    @GetMapping("/totalScore/{topic}")
    public int totalScore(@PathVariable String topic) {
        log.info("TOPIC - Request for function: totalScore, input: " + topic);
        return questionService.topicTotalScore(topic);
    }

    @GetMapping("/avgScore/{topic}")
    public double avgScore(@PathVariable String topic) {
        log.info("TOPIC - Request for function: avgScore, input: " + topic);
        return questionService.topicAvgScore(topic);
    }

    @GetMapping("/topKByViewCount/{k}")
    public List<TagViewCount> topKTagsByViewCount(@PathVariable int k) {
        log.info("TOPIC - Request for function: topKTagsByViewCount, input size: " + k);
        return questionService.topKTagsByViewCount(k);
    }

    @GetMapping("/topKByAnswerCount/{k}")
    public List<JSONObject> topKTagsByAnswerCount(@PathVariable int k) {
        log.info("TOPIC - Request for function: topKTagsByAnswerCount, input size: " + k);
        return questionService.topKTagsByAnswerCount(k);
    }

    @GetMapping("/topKByQuestionCount/{k}")
    public List<JSONObject> topKTagsByQuestionCount(@PathVariable int k) {
        log.info("TOPIC - Request for function: topKTagsByQuestionCount, input size: " + k);
        return questionService.topKTagsByQuestionCount(k);
    }

    @GetMapping("/topKByAvgScore/{k}")
    public List<JSONObject> topKTagsByAvgScore(@PathVariable int k) {
        log.info("TOPIC - Request for function: topKTagsByAvgScore, input size: " + k);
        return questionService.topKTagsByAvgScore(k);
    }

    // intimacy ------------------------------------------------------

    @GetMapping("/intimacy")
    public List<JSONObject> intimacy(@RequestParam("k") Integer k, @RequestParam("topic") String topic) {
        log.info("RELATIVE TOPIC - Standard: intimacy, input topic: "+ topic +", input size: " + k);
        return questionService.KIntimateTags(topic, k);
    }

    @GetMapping("/similar")
    public List<JSONObject> similar(@RequestParam("k") Integer k, @RequestParam("topic") String topic) {
        log.info("RELATIVE TOPIC - Standard: similar, input topic: "+ topic +", input size: " + k);
        return questionService.KSimilarTags(topic, k);
    }


}
