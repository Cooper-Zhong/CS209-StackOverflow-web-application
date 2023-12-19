package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.dto.BugViewCount;
import cn.sustech.cs209backend.service.BugService;
import cn.sustech.cs209backend.service.QuestionService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/bug")
public class BugController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private BugService bugService;

    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @GetMapping("/avgViewCount/{bug}")
    public double avgViewCount(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: avgViewCount, input: " + bug);
            return questionService.bugNameAvgViewCount(bug).intValue();
        } catch (Exception e) {
            log.error("BUG - Request for function: avgViewCount, input: " + bug + " failed");
        }
        return 0;
    }

    @GetMapping("/avgAnswerCount/{bug}")
    public double avgAnswerCount(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: avgAnswerCount, input: " + bug);
            return questionService.bugNameAvgAnswerCount(bug);
        } catch (Exception e) {
            log.error("BUG - Request for function: avgAnswerCount, input: " + bug + " failed");
        }
        return 0;
    }

    @GetMapping("/totalAnswerCount/{bug}")
    public int totalAnswerCount(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: totalAnswerCount, input: " + bug);
            return questionService.bugNameTotalAnswerCount(bug);
        } catch (Exception e) {
            log.error("BUG - Request for function: totalAnswerCount, input: " + bug + " failed");
        }
        return 0;
    }

    @GetMapping("/bugQuestionCount/{bug}")
    public int bugQuestionCount(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: bugQuestionCount, input: " + bug);
            return questionService.bugNameQuestionCount(bug);
        } catch (Exception e) {
            log.error("BUG - Request for function: bugQuestionCount, input: " + bug + " failed");
        }
        return 0;
    }

    @GetMapping("/totalScore/{bug}")
    public int bugNameTotalScore(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: bugNameTotalScore, input: " + bug);
            return questionService.bugNameTotalScore(bug);
        } catch (Exception e) {
            log.error("BUG - Request for function: bugNameTotalScore, input: " + bug + " failed");
        }
        return 0;
    }

    @GetMapping("/avgScore/{bug}")
    public double bugNameAvgScore(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: avgScore, input: " + bug);
            return questionService.bugNameAvgScore(bug);
        } catch (Exception e) {
            log.error("BUG - Request for function: avgScore, input: " + bug + " failed");
        }
        return 0;
    }

    @GetMapping("/topKByViewCount/{k}")
    public List<BugViewCount> topKBugsByViewCount(@PathVariable int k) {
        try {
            log.info("BUG - Request for function: topKBugsByViewCount, input size: " + k);
            return questionService.topKBugsByViewCount(k);
        } catch (Exception e) {
            log.error("BUG - Request for function: topKBugsByViewCount, input size: " + k + " failed");
        }
        return null;
    }

    @GetMapping("/topKByAnswerCount/{k}")
    public List<JSONObject> topKBugsByAnswerCount(@PathVariable int k) {
        try {
            log.info("BUG - Request for function: topKBugsByAnswerCount, input size: " + k);
            return questionService.topKBugsByAnswerCount(k);
        } catch (Exception e) {
            log.error("BUG - Request for function: topKBugsByAnswerCount, input size: " + k + " failed");
        }
        return null;
    }

    @GetMapping("/topKByAvgScore/{k}")
    public List<JSONObject> topKBugsByAvgScore(@PathVariable int k) {
        try {
            log.info("BUG - Request for function: topKBugsByAvgScore, input size: " + k);
            return questionService.topKBugsByAvgScore(k);
        } catch (Exception e) {
            log.error("BUG - Request for function: topKBugsByAvgScore, input size: " + k + " failed");
        }
        return null;
    }

    @GetMapping("/topKByQuestionCount/{k}")
    public List<JSONObject> topKBugsByQuestionCount(@PathVariable int k) {
        try {
            log.info("BUG - Request for function: topKBugsByQuestionCount, input size: " + k);
            return questionService.topKBugsByQuestionCount(k);
        } catch (Exception e) {
            log.error("BUG - Request for function: topKBugsByQuestionCount, input size: " + k + " failed");
        }
        return null;
    }

    @GetMapping("/topKByAppearanceCount/{k}")
    public List<JSONObject> topKBugsByAppearanceCount(@PathVariable int k) {
        try {
            log.info("BUG - Request for function: topKBugsByAppearanceCount, input size: " + k);
            return questionService.topKBugsByAppearanceCount(k);
        } catch (Exception e) {
            log.error("BUG - Request for function: topKBugsByAppearanceCount, input size: " + k + " failed");
        }
        return null;
    }

    // category ----------------------------------------------------------

    @GetMapping("/categoryQuestionCount/{category}")
    public double categoryQuestionCount(@PathVariable String category) {
        try {
            log.info("BUG - Request for function: categoryQuestionCount, input: " + category);
            return bugService.categoryQuestionCount(category);
        } catch (Exception e) {
            log.error("BUG - Request for function: categoryQuestionCount, input: " + category + " failed");
        }
        return 0;
    }

    @GetMapping("/categoryAvgViewCount/{category}")
    public double categoryAvgViewCount(@PathVariable String category) {
        try {
            log.info("BUG - Request for function: categoryAvgViewCount, input: " + category);
            return bugService.categoryAvgViewCount(category);
        } catch (Exception e) {
            log.error("BUG - Request for function: categoryAvgViewCount, input: " + category + " failed");
        }
        return 0;
    }

    @GetMapping("/categoryAvgAnswerCount/{category}")
    public double categoryAvgAnswerCount(@PathVariable String category) {
        try {
            log.info("BUG - Request for function: categoryAvgAnswerCount, input: " + category);
            return bugService.categoryAvgAnswerCount(category);
        } catch (Exception e) {
            log.error("BUG - Request for function: categoryAvgAnswerCount, input: " + category + " failed");
        }
        return 0;
    }

    @GetMapping("/categoryAvgScore/{category}")
    public double categoryAvgScore(@PathVariable String category) {
        try {
            log.info("BUG - Request for function: categoryAvgScore, input: " + category);
            return bugService.categoryAvgScore(category);
        } catch (Exception e) {
            log.error("BUG - Request for function: categoryAvgScore, input: " + category + " failed");
        }
        return 0;
    }


}

