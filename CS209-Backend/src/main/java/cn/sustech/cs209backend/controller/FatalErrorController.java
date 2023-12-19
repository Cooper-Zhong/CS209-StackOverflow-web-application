package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.dto.BugViewCount;
import cn.sustech.cs209backend.service.QuestionService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/fatalError")
public class FatalErrorController {

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

    @GetMapping("/topKByViewCount/{k}")
    public List<BugViewCount> topKBugsByViewCount(@PathVariable int k) {
        try {
            log.info("FATAL ERROR - Request for function: topKBugsByViewCount, input size: " + k);
            return questionService.topKFatalErrorByViewCount(k);
        } catch (Exception e) {
            log.error("FATAL ERROR - Request for function: topKBugsByViewCount, input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/topKByAnswerCount/{k}")
    public List<JSONObject> topKBugsByAnswerCount(@PathVariable int k) {
        try {
            log.info("FATAL ERROR - Request for function: topKBugsByAnswerCount, input size: " + k);
            return questionService.topKFatalErrorByAnswerCount(k);
        } catch (Exception e) {
            log.error("FATAL ERROR - Request for function: topKBugsByAnswerCount, input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/topKByAvgScore/{k}")
    public List<JSONObject> topKBugsByAvgScore(@PathVariable int k) {
        try {

            log.info("FATAL ERROR - Request for function: topKBugsByAvgScore, input size: " + k);
            return questionService.topKFatalErrorByAvgScore(k);
        } catch (Exception e) {
            log.error("FATAL ERROR - Request for function: topKBugsByAvgScore, input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/topKByQuestionCount/{k}")
    public List<JSONObject> topKBugsByQuestionCount(@PathVariable int k) {
        try {
            log.info("FATAL ERROR - Request for function: topKBugsByQuestionCount, input size: " + k);
            return questionService.topKFatalErrorByQuestionCount(k);
        } catch (Exception e) {
            log.error("FATAL ERROR - Request for function: topKBugsByQuestionCount, input size: " + k);
            log.error(e.getMessage());
            return null;
        }
    }

}

