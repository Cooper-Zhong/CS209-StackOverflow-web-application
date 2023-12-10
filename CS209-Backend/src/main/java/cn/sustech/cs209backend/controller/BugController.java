package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.dto.BugViewCount;
import cn.sustech.cs209backend.dto.TagViewCount;
import cn.sustech.cs209backend.entity.Bug;
import cn.sustech.cs209backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bug")
public class BugController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/avgViewCount/{bug}")
    public double avgViewCount(@PathVariable String bug) {
        return questionService.bugNameAvgViewCount(bug).intValue();
    }

    @GetMapping("/avgAnswerCount/{bug}")
    public double avgAnswerCount(@PathVariable String bug) {
        return questionService.bugNameAvgAnswerCount(bug);
    }

    @GetMapping("/totalAnswerCount/{bug}")
    public int totalAnswerCount(@PathVariable String bug) {
        return questionService.bugNameTotalAnswerCount(bug);
    }

    @GetMapping("/bugQuestionCount/{bug}")
    public int bugQuestionCount(@PathVariable String bug) {
        return questionService.bugNameQuestionCount(bug);
    }

    @GetMapping("/totalScore/{bug}")
    public int bugNameTotalScore(@PathVariable String bug) {
        return questionService.bugNameTotalScore(bug);
    }

    @GetMapping("/avgScore/{bug}")
    public double bugNameAvgScore(@PathVariable String bug) {
        return questionService.bugNameAvgScore(bug);
    }

    @GetMapping("/topKTagsByViewCount/{k}")
    public List<BugViewCount> topKTagsByViewCount(@PathVariable int k) {
        return questionService.topKPopularBugs(k);
    }


}

