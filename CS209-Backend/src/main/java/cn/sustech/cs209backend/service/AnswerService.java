package cn.sustech.cs209backend.service;

import cn.sustech.cs209backend.entity.Answer;
import cn.sustech.cs209backend.repo.AnswerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepo answerRepo;

    public List<Answer> answerByQuestionID(int questionID) {
        return answerRepo.findByQuestionID(questionID);
    }

    public List<Answer> answerByAnswerID(int answerID) {
        return answerRepo.findByAnswerID(answerID);
    }

    public List<Answer> answerByDate(Timestamp from, Timestamp end) {
        return answerRepo.findByDate(from, end);
    }




}
