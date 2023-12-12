package cn.sustech.cs209backend.service;

import cn.sustech.cs209backend.config.MyException;
import cn.sustech.cs209backend.dto.BugViewCount;
import cn.sustech.cs209backend.dto.TagViewCount;
import cn.sustech.cs209backend.entity.Question;
import cn.sustech.cs209backend.repo.QuestionRepo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public List<Question> allQuestions() {
        return questionRepo.findAll();
    }

    public Question questionByQuestionID(int questionID) {
        return questionRepo.findById(questionID).orElse(null);
    }

    public List<Question> noAnswer(Date from, Date end) {
        return questionRepo.noAnswer(from, end);
    }


    // topic ------------------------------------------------------

    public List<Question> questionByTagName(String tagName) {
        return questionRepo.findByTagName(tagName);
    }

    public BigDecimal topicAvgViewCount(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        BigDecimal sum = BigDecimal.ZERO;
        for (Question question : questions) {
            sum = sum.add(BigDecimal.valueOf(question.getViewCount()));
        }
        if (sum.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return sum.divide(BigDecimal.valueOf(questions.size()), RoundingMode.HALF_UP);
    }

    public double topicAvgAnswerCount(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public int topicTotalAnswerCount(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        return sum;
    }

    public int topicQuestionCount(String topic) {
        return questionRepo.findByTagName(topic).size();
    }

    public int topicTotalScore(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        return sum;
    }

    public double topicAvgScore(String topic) {
        List<Question> questions = questionRepo.findByTagName(topic);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public List<TagViewCount> topKTagsByViewCount(int k) {
        List<Map> result = questionRepo.topKTagsByViewCount(k);
        List<TagViewCount> tagViewCounts = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("tag_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_view_count");
            tagViewCounts.add(new TagViewCount(tagName, average_view_count.intValue()));
        }
        return tagViewCounts;
    }

    public List<JSONObject> topKTagsByAnswerCount(int k) {
        List<Map> result = questionRepo.topKTagsByAnswerCount(k);
        List<JSONObject> resultList = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("tag_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_answer_count");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagName", tagName);
            jsonObject.put("averageAnswerCount", average_view_count.intValue());
            resultList.add(jsonObject);
        }
        return resultList;
    }

    public List<JSONObject> topKTagsByAvgScore(int k) {
        List<Map> result = questionRepo.topKTagsByAvgScore(k);
        List<JSONObject> resultList = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("tag_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_score");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagName", tagName);
            jsonObject.put("averageScore", average_view_count.intValue());
            resultList.add(jsonObject);
        }
        return resultList;
    }

    public List<JSONObject> topKTagsByQuestionCount(int k) {
        List<Map> result = questionRepo.topKTagsByQuestionCount(k);
        List<JSONObject> resultList = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("tag_name");
            long average_view_count = (long) map.get("question_count");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagName", tagName);
            jsonObject.put("questionCount", average_view_count);
            resultList.add(jsonObject);
        }
        return resultList;
    }

    public List<JSONObject> KIntimateTags(String topic, int k) {
        if (k<=0) throw new MyException(4, "k must be positive");
        List<JSONObject> similarTags = KSimilarTags(topic, 1);
        if (similarTags.isEmpty()) return new ArrayList<>();
        String queryTagName = (String) similarTags.get(0).get("tagName");
        // intimacy
        List<Map> result = questionRepo.topKTagsByIntimacy(k, queryTagName);
        List<JSONObject> resultList = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("intimate_tag");
            long intimacy = (long) map.get("intimacy");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("intimate_tag", tagName);
            jsonObject.put("intimacy", intimacy);
            resultList.add(jsonObject);
        }
        return resultList;
    }

    public List<JSONObject> KSimilarTags(String tagName, int k) {
        List<Map> result = questionRepo.KSimilarTags(k, tagName);
        List<JSONObject> resultList = new ArrayList<>();
        for (Map map : result) {
            String similarTagName = (String) map.get("similar_tag");
            float similarity = (float) map.get("similarity_score");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagName", similarTagName);
            resultList.add(jsonObject);
        }
        return resultList;
    }


    // bug ------------------------------------------------------

    public List<Question> questionsByBugName(String bugName) {
        return questionRepo.findByBugName(bugName);
    }

    public BigDecimal bugNameAvgViewCount(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        BigDecimal sum = BigDecimal.ZERO;
        for (Question question : questions) {
            sum = sum.add(BigDecimal.valueOf(question.getViewCount()));
        }
        if (sum.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return sum.divide(BigDecimal.valueOf(questions.size()), RoundingMode.HALF_UP);
    }

    public double bugNameAvgAnswerCount(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public int bugNameTotalAnswerCount(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        return sum;
    }

    public int bugNameQuestionCount(String bugName) {
        return questionRepo.findByBugName(bugName).size();
    }

    public int bugNameTotalScore(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        return sum;
    }

    public double bugNameAvgScore(String bugName) {
        List<Question> questions = questionRepo.findByBugName(bugName);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public List<BugViewCount> topKBugsByViewCount(int k) {
        List<Map> result = questionRepo.topKBugsByViewCount(k);
        List<BugViewCount> bugViewCounts = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_view_count");
            bugViewCounts.add(new BugViewCount(tagName, average_view_count.intValue()));
        }
        return bugViewCounts;
    }

    public List<JSONObject> topKBugsByAnswerCount(int k) {
        List<Map> result = questionRepo.topKBugsByAnswerCount(k);
        List<JSONObject> tempList = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_answer_count");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bugName", tagName);
            jsonObject.put("averageAnswerCount", average_view_count.intValue());
            tempList.add(jsonObject);
        }
        return tempList;
    }

    public List<JSONObject> topKBugsByAvgScore(int k) {
        List<Map> result = questionRepo.topKBugsByAvgScore(k);
        List<JSONObject> tempList = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_score");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bugName", tagName);
            jsonObject.put("averageScore", average_view_count.intValue());
            tempList.add(jsonObject);
        }
        return tempList;
    }

    public List<JSONObject> topKBugsByQuestionCount(int k) {
        List<Map> result = questionRepo.topKBugsByQuestionCount(k);
        List<JSONObject> tempList = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            long average_view_count = (long) map.get("question_count");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bugName", tagName);
            jsonObject.put("questionCount", average_view_count);
            tempList.add(jsonObject);
        }
        return tempList;
    }


}
