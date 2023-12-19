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
@RequestMapping("/bugCompare")
public class BugCompareController {

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
    @GetMapping("/{type}")
    public double[] getData(@PathVariable String type){
        double[] ans = new double[6];
        try {
            log.info("BUG COMPARE - Request for function: bugTypeAvgViewCount, input: " + type);
            ans[0] = questionService.bugTypeAvgViewCount(type).intValue();
        } catch (Exception e) {
            log.error("BUG COMPARE - Request for function: bugTypeAvgViewCount, input: " + type + " failed");
        }

        try {
            log.info("BUG COMPARE - Request for function: bugTypeAvgViewCount, input: " + type);
            ans[1] = questionService.bugTypeAvgAnswerCount(type);
        } catch (Exception e) {
            log.error("BUG COMPARE - Request for function: bugTypeAvgViewCount, input: " + type + " failed");
        }
        try {
            log.info("BUG COMPARE - Request for function: bugTypeAvgViewCount, input: " + type);
            ans[2] = questionService.bugTypeTotalAnswerCount(type);
        } catch (Exception e) {
            log.error("BUG COMPARE - Request for function: bugTypeTotalAnswerCount, input: " + type + " failed");
        }
        try {
            log.info("BUG COMPARE - Request for function: bugTypeAvgViewCount, input: " + type);
            ans[3] = questionService.bugTypeQuestionCount(type);
        } catch (Exception e) {
            log.error("BUG COMPARE - Request for function: bugTypeQuestionCount, input: " + type + " failed");
        }
        try {
            log.info("BUG COMPARE - Request for function: bugTypeAvgViewCount, input: " + type);
            ans[4] = questionService.bugTypeTotalScore(type);
        } catch (Exception e) {
            log.error("BUG COMPARE - Request for function: bugTypeTotalScore, input: " + type + " failed");
        }
        try {
            log.info("BUG COMPARE - Request for function: bugTypeAvgViewCount, input: " + type);
            ans[5] = questionService.bugTypeAvgScore(type);
        } catch (Exception e) {
            log.error("BUG COMPARE - Request for function: bugTypeAvgScore, input: " + type + " failed");
        }

        log.info("BUG COMPARE - Request for function: getData, input: " + type);
        return ans;
    }
}

