package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.service.BugService;
import cn.sustech.cs209backend.service.QuestionService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/json")
@Slf4j
public class JsonController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private BugService bugService;

    @GetMapping("/question/avgViewCount/{topic}")
    public JSONObject questionAvgViewCount(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: avgViewCount, input: " + topic);
            int res = questionService.topicAvgViewCount(topic).intValue();
            JSONObject temp = new JSONObject();
            temp.put("topic", topic);
            temp.put("avgViewCount",    res);
            return temp;
        } catch (Exception e) {
            log.error("TOPIC - Request for function: avgViewCount, input: " + topic);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/question/avgAnswerCount/{topic}")
    public JSONObject questionAvgAnswerCount(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: avgAnswerCount, input: " + topic);
            double res = questionService.topicAvgAnswerCount(topic);
            JSONObject temp = new JSONObject();
            temp.put("topic", topic);
            temp.put("avgAnswerCount",    res);
            return temp;
        } catch (Exception e) {
            log.error("TOPIC - Request for function: avgAnswerCount, input: " + topic);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/question/totalAnswerCount/{topic}")
    public JSONObject questionTotalAnswerCount(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: totalAnswerCount, input: " + topic);
            int res = questionService.topicTotalAnswerCount(topic);
            JSONObject temp = new JSONObject();
            temp.put("topic", topic);
            temp.put("totalAnswerCount",    res);
            return temp;
        } catch (Exception e) {
            log.error("TOPIC - Request for function: totalAnswerCount, input: " + topic);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/question/topicQuestionCount/{topic}")
    public JSONObject questionTopicQuestionCount(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: topicQuestionCount, input: " + topic);
            int res = questionService.topicQuestionCount(topic);
            JSONObject temp = new JSONObject();
            temp.put("topic", topic);
            temp.put("topicQuestionCount",    res);
            return temp;
        } catch (Exception e) {
            log.error("TOPIC - Request for function: topicQuestionCount, input: " + topic);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/question/totalScore/{topic}")
    public JSONObject questionTotalScore(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: totalScore, input: " + topic);
            int res = questionService.topicTotalScore(topic);
            JSONObject temp = new JSONObject();
            temp.put("topic", topic);
            temp.put("totalScore",    res);
            return temp;
        } catch (Exception e) {
            log.error("TOPIC - Request for function: totalScore, input: " + topic);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/question/avgScore/{topic}")
    public JSONObject questionAvgScore(@PathVariable String topic) {
        try {
            log.info("TOPIC - Request for function: avgScore, input: " + topic);
            double res = questionService.topicAvgScore(topic);
            JSONObject temp = new JSONObject();
            temp.put("topic", topic);
            temp.put("avgScore",    res);
            return temp;
        } catch (Exception e) {
            log.error("TOPIC - Request for function: avgScore, input: " + topic);
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/bug/avgViewCount/{bug}")
    public JSONObject bugAvgViewCount(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: avgViewCount, input: " + bug);
            int res = questionService.bugNameAvgViewCount(bug).intValue();
            JSONObject temp = new JSONObject();
            temp.put("bug", bug);
            temp.put("avgViewCount",    res);
            return temp;
        } catch (Exception e) {
            log.error("BUG - Request for function: avgViewCount, input: " + bug + " failed");
            return null;
        }
    }

    @GetMapping("/bug/avgAnswerCount/{bug}")
    public JSONObject bugAvgAnswerCount(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: avgAnswerCount, input: " + bug);
            double res = questionService.bugNameAvgAnswerCount(bug);
            JSONObject temp = new JSONObject();
            temp.put("bug", bug);
            temp.put("avgAnswerCount",    res);
            return temp;
        } catch (Exception e) {
            log.error("BUG - Request for function: avgAnswerCount, input: " + bug + " failed");
            return null;
        }
    }

    @GetMapping("/bug/totalAnswerCount/{bug}")
    public JSONObject bugTotalAnswerCount(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: totalAnswerCount, input: " + bug);
            int res = questionService.bugNameTotalAnswerCount(bug);
            JSONObject temp = new JSONObject();
            temp.put("bug", bug);
            temp.put("totalAnswerCount",    res);
            return temp;
        } catch (Exception e) {
            log.error("BUG - Request for function: totalAnswerCount, input: " + bug + " failed");
            return null;
        }
    }

    @GetMapping("/bug/totalQuestionCount/{bug}")
    public JSONObject bugQuestionCount(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: bugQuestionCount, input: " + bug);
            int res = questionService.bugNameQuestionCount(bug);
            JSONObject temp = new JSONObject();
            temp.put("bug", bug);
            temp.put("bugQuestionCount",    res);
            return temp;
        } catch (Exception e) {
            log.error("BUG - Request for function: bugQuestionCount, input: " + bug + " failed");
            return null;
        }
    }

    @GetMapping("/bug/totalScore/{bug}")
    public JSONObject bugTotalScore(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: bugTotalScore, input: " + bug);
            int res = questionService.bugNameTotalScore(bug);
            JSONObject temp = new JSONObject();
            temp.put("bug", bug);
            temp.put("totalScore",    res);
            return temp;
        } catch (Exception e) {
            log.error("BUG - Request for function: bugTotalScore, input: " + bug + " failed");
            return null;
        }
    }

    @GetMapping("/bug/avgScore/{bug}")
    public JSONObject bugAvgScore(@PathVariable String bug) {
        try {
            log.info("BUG - Request for function: avgScore, input: " + bug);
            double res = questionService.bugNameAvgScore(bug);
            JSONObject temp = new JSONObject();
            temp.put("bug", bug);
            temp.put("avgScore",    res);
            return temp;
        } catch (Exception e) {
            log.error("BUG - Request for function: avgScore, input: " + bug + " failed");
            return null;
        }
    }


}
