package cn.sustech.cs209backend.service;

import cn.sustech.cs209backend.config.MyException;
import cn.sustech.cs209backend.dto.BugViewCount;
import cn.sustech.cs209backend.dto.TagViewCount;
import cn.sustech.cs209backend.entity.Question;
import cn.sustech.cs209backend.repo.QuestionRepo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
        // topic can be phrases
        String[] topicArray = topic.split(" ");
        if (k<=0) throw new MyException(4, "k must be positive");
        List<JSONObject> resultList = new ArrayList<>();
        List<JSONObject> queryTagList = new ArrayList<>(); //存查询的tag，最后要加入到resultList中
        Set<String> tagNames = new HashSet<>();
        int totalOccurrence = 0;
        int minCoOccurrence = Integer.MAX_VALUE;
        int maxCoOccurrence = Integer.MIN_VALUE;
        for (String s : topicArray) {
            List<JSONObject> similarTags = KSimilarTags(s, 1);
            if (similarTags.isEmpty()) return new ArrayList<>();
            String queryTagName = (String) similarTags.get(0).get("tagName");
            queryTagList.add(similarTags.get(0));
            // intimacy
            tagNames.add(queryTagName);
            List<Map> result = questionRepo.topKTagsByIntimacy(k, queryTagName);
            for (Map map : result) {
                String tagName = (String) map.get("intimate_tag");
                if (tagNames.contains(tagName)) continue; // avoid duplicate
                tagNames.add(tagName);
                long intimacy = (long) map.get("intimacy");
                // update
                totalOccurrence += intimacy;
                minCoOccurrence = Math.min(minCoOccurrence, (int) intimacy);
                maxCoOccurrence = Math.max(maxCoOccurrence, (int) intimacy);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("intimate_tag", tagName);
                jsonObject.put("intimacy", intimacy);
                resultList.add(jsonObject);
            }
        }
        totalOccurrence += (maxCoOccurrence + 1)*queryTagList.size(); //用maxCoOccurrence+1作为queryTag的occurrence

        // normalize
        for (JSONObject jsonObject : resultList) {
            long intimacy = (long) jsonObject.get("intimacy");
            double normalizedIntimacy = normalize(intimacy, totalOccurrence);
            jsonObject.put("intimacy", normalizedIntimacy);
        }
        // add query tag
        for (JSONObject jsonObject : queryTagList) {
            String queryTagName = (String) jsonObject.get("tagName");
            double normalizedIntimacy = normalize(maxCoOccurrence + 1, totalOccurrence); // 把queryTag scale 到最大的co-occurrence
            JSONObject queryTag = new JSONObject();
            queryTag.put("intimate_tag", queryTagName);
            queryTag.put("intimacy", normalizedIntimacy);
            resultList.add(queryTag);
        }
        resultList.sort((o1, o2) -> {
            double intimacy1 = (double) o1.get("intimacy");
            double intimacy2 = (double) o2.get("intimacy");
            if (intimacy1>intimacy2) return -1;
            else if (intimacy1<intimacy2) return 1;
            else return 0;
        });
        return resultList.subList(0, Math.min(k, resultList.size())); // 取前k个
    }

    private double normalize(double intimacy, double totalOccurrence) {
        return intimacy/totalOccurrence;
    }

    public List<JSONObject> KSimilarTags(String tagName, int k) {
        List<Map> result = questionRepo.KSimilarTags(k, tagName);
        List<JSONObject> resultList = new ArrayList<>();
        for (Map map : result) {
            String similarTagName = (String) map.get("similar_tag");
            float similarity = (float) map.get("similarity_score");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagName", similarTagName);
            jsonObject.put("similarity", similarity);
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

    public List<JSONObject> topKBugsByAppearanceCount(int k) {
        List<Map> result = questionRepo.topKBugsByAppearanceCount(k);
        List<JSONObject> tempList = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            long average_view_count = (long) map.get("total_count");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bugName", tagName);
            jsonObject.put("totalCount", average_view_count);
            tempList.add(jsonObject);
        }
        return tempList;
    }


    // exception ------------------------------------------------------
    public List<BugViewCount> topKExceptionsByViewCount(int k) {
        List<Map> result = questionRepo.topKExceptionsByViewCount(k);
        List<BugViewCount> bugViewCounts = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_view_count");
            bugViewCounts.add(new BugViewCount(tagName, average_view_count.intValue()));
        }
        return bugViewCounts;
    }

    public List<JSONObject> topKExceptionsByAnswerCount(int k) {
        List<Map> result = questionRepo.topKExceptionsByAnswerCount(k);
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

    public List<JSONObject> topKExceptionsByAvgScore(int k) {
        List<Map> result = questionRepo.topKExceptionsByAvgScore(k);
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

    public List<JSONObject> topKExceptionsByQuestionCount(int k) {
        List<Map> result = questionRepo.topKExceptionsByQuestionCount(k);
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



    // fatal error ------------------------------------------------------

    public List<BugViewCount> topKFatalErrorByViewCount(int k) {
        List<Map> result = questionRepo.topKFatalErrorByViewCount(k);
        List<BugViewCount> bugViewCounts = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_view_count");
            bugViewCounts.add(new BugViewCount(tagName, average_view_count.intValue()));
        }
        return bugViewCounts;
    }

    public List<JSONObject> topKFatalErrorByAnswerCount(int k) {
        List<Map> result = questionRepo.topKFatalErrorByAnswerCount(k);
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

    public List<JSONObject> topKFatalErrorByAvgScore(int k) {
        List<Map> result = questionRepo.topKFatalErrorByAvgScore(k);
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

    public List<JSONObject> topKFatalErrorByQuestionCount(int k) {
        List<Map> result = questionRepo.topKFatalErrorByQuestionCount(k);
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



    // syntax error ------------------------------------------------------

    public List<BugViewCount> topKSyntaxErrorByViewCount(int k) {
        List<Map> result = questionRepo.topKSyntaxErrorByViewCount(k);
        List<BugViewCount> bugViewCounts = new ArrayList<>();
        for (Map map : result) {
            String tagName = (String) map.get("bug_name");
            BigDecimal average_view_count = (BigDecimal) map.get("average_view_count");
            bugViewCounts.add(new BugViewCount(tagName, average_view_count.intValue()));
        }
        return bugViewCounts;
    }

    public List<JSONObject> topKSyntaxErrorByAnswerCount(int k) {
        List<Map> result = questionRepo.topKSyntaxErrorByAnswerCount(k);
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

    public List<JSONObject> topKSyntaxErrorByAvgScore(int k) {
        List<Map> result = questionRepo.topKSyntaxErrorByAvgScore(k);
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

    public List<JSONObject> topKSyntaxErrorByQuestionCount(int k) {
        List<Map> result = questionRepo.topKSyntaxErrorByQuestionCount(k);
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



    // bug type ------------------------------------------------------

    public List<Question> questionsByBugType(String type) {
        return questionRepo.findByBugType(type);
    }

    public BigDecimal bugTypeAvgViewCount(String type) {
        List<Question> questions = questionRepo.findByBugType(type);
        BigDecimal sum = BigDecimal.ZERO;
        for (Question question : questions) {
            sum = sum.add(BigDecimal.valueOf(question.getViewCount()));
        }
        if (sum.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return sum.divide(BigDecimal.valueOf(questions.size()), RoundingMode.HALF_UP);
    }

    public double bugTypeAvgAnswerCount(String type) {
        List<Question> questions = questionRepo.findByBugType(type);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }

    public int bugTypeTotalAnswerCount(String type) {
        List<Question> questions = questionRepo.findByBugType(type);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getAnswerCount();
        }
        return sum;
    }

    public int bugTypeQuestionCount(String type) {
        return questionRepo.findByBugType(type).size();
    }

    public int bugTypeTotalScore(String type) {
        List<Question> questions = questionRepo.findByBugType(type);
        int sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        return sum;
    }

    public double bugTypeAvgScore(String type) {
        List<Question> questions = questionRepo.findByBugType(type);
        double sum = 0;
        for (Question question : questions) {
            sum += question.getScore();
        }
        if (sum == 0) return 0;
        return sum/questions.size();
    }



}
