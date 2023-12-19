package cn.sustech.cs209backend.service;

import cn.sustech.cs209backend.repo.BugRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class BugService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private BugRepo bugRepo;

    public double categoryQuestionCount(String category) {
        return bugRepo.categoryQuestionCount(category);
    }

    public double categoryAvgViewCount(String category) {
        return bugRepo.categoryAvgViewCount(category);
    }

    public double categoryAvgAnswerCount(String category) {
        return bugRepo.categoryAvgAnswerCount(category);
    }

    public double categoryAvgScore(String category) {
        return bugRepo.categoryAvgScore(category);
    }

}
