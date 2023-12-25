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
        try {

            log.info("TOPIC - Request for function: avgViewCount, input: " + topic);
            return questionService.topicAvgViewCount(topic).intValue();
        } catch (Exception e) {
            log.error("TOPIC - Request for function: avgViewCount, input: " + topic);
            log.error(e.getMessage());
            return 0;
        }
    }

    @GetMapping("/avgAnswerCount/{topic}")
    public double avgAnswerCount(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: avgAnswerCount, input: " + topic);
            return questionService.topicAvgAnswerCount(topic);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: avgAnswerCount, input: " + topic);
            log.error(e.getMessage());
            return 0;
        }
    }

    @GetMapping("/totalAnswerCount/{topic}")
    public int totalAnswerCount(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: totalAnswerCount, input: " + topic);
            return questionService.topicTotalAnswerCount(topic);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: totalAnswerCount, input: " + topic);
            log.error(e.getMessage());
            return 0;
        }
    }

    @GetMapping("/topicQuestionCount/{topic}")
    public int topicQuestionCount(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: topicQuestionCount, input: " + topic);
            return questionService.topicQuestionCount(topic);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: topicQuestionCount, input: " + topic);
            log.error(e.getMessage());
            return 0;
        }
    }

    @GetMapping("/totalScore/{topic}")
    public int totalScore(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: totalScore, input: " + topic);
            return questionService.topicTotalScore(topic);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: totalScore, input: " + topic);
            log.error(e.getMessage());
            return 0;
        }
    }

    @GetMapping("/avgScore/{topic}")
    public double avgScore(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: avgScore, input: " + topic);
            return questionService.topicAvgScore(topic);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: avgScore, input: " + topic);
            log.error(e.getMessage());
            return 0;
        }
    }

    // top k -------

    @GetMapping("/topKByViewCount/{k}")
    public List<TagViewCount> topKTagsByViewCount(@PathVariable int k) {
        try {
            log.info("TOPIC - Request for function: topKTagsByViewCount, input size: " + k);
            return questionService.topKTagsByViewCount(k);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: topKTagsByViewCount, input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/topKByAnswerCount/{k}")
    public List<JSONObject> topKTagsByAnswerCount(@PathVariable int k) {
        try {
            log.info("TOPIC - Request for function: topKTagsByAnswerCount, input size: " + k);
            return questionService.topKTagsByAnswerCount(k);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: topKTagsByAnswerCount, input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/topKByQuestionCount/{k}")
    public List<JSONObject> topKTagsByQuestionCount(@PathVariable int k) {
        try {
            log.info("TOPIC - Request for function: topKTagsByQuestionCount, input size: " + k);
            return questionService.topKTagsByQuestionCount(k);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: topKTagsByQuestionCount, input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/topKByAvgScore/{k}")
    public List<JSONObject> topKTagsByAvgScore(@PathVariable int k) {
        try {
            log.info("TOPIC - Request for function: topKTagsByAvgScore, input size: " + k);
            return questionService.topKTagsByAvgScore(k);
        } catch (Exception e) {
            log.error("TOPIC - Request for function: topKTagsByAvgScore, input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

    // intimacy ------------------------------------------------------

    @GetMapping("/intimacy")
    public List<JSONObject> intimacy(@RequestParam("k") Integer k, @RequestParam("topic") String topic) {
        try {
            log.info("RELATIVE TOPIC - Standard: intimacy, input topic: " + topic + ", input size: " + k);
            return questionService.KIntimateTags(topic, k);
        } catch (Exception e) {
            log.error("RELATIVE TOPIC - Standard: intimacy, input topic: " + topic + ", input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/similar")
    public List<JSONObject> similar(@RequestParam("k") Integer k, @RequestParam("topic") String topic) {
        try {
            log.info("RELATIVE TOPIC - Standard: similar, input topic: " + topic + ", input size: " + k);
            return questionService.KSimilarTags(topic, k);
        } catch (Exception e) {
            log.error("RELATIVE TOPIC - Standard: similar, input topic: " + topic + ", input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }


}
