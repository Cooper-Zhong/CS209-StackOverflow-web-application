package cn.sustech.cs209backend.controller;

import cn.sustech.cs209backend.entity.Question;
import cn.sustech.cs209backend.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
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

    @GetMapping("/findByBugName/{bugName}")
    public List<Question> questionByBugName(@PathVariable String bugName) {
        return questionService.questionsByBugName(bugName);
    }

}
