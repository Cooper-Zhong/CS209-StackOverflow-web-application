package cn.sustech.cs209backend.crawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DataCollector {
    static String myApiKey = "L2JGrP5WXB*3kYD3S8iKVg((";
    static String apiKey = "gqjiH6ExBbic7NaMoFxC)w(("; // from maystern

    private int pageSize =5; // 分页获取数据时每页的大小 [1, 100]
    private int pageStep =100; // 分页获取数据时每次获取所隔的页数
    private final List<JSONObject> questionList = new ArrayList<>(); // 获取问题JSON列表
    private final List<JSONObject> answerList = new ArrayList<>();  // 获取回答JSON列表
    private final List<JSONObject> commentList = new ArrayList<>(); // 获取评论JSON列表

    private JSONArray questionJsonArray = new JSONArray(); // 获取问题JSON列表
    private JSONArray answerJsonArray = new JSONArray();  // 获取回答JSON列表
    private JSONArray commentJsonArray = new JSONArray(); // 获取评论JSON列表
    private int totalQuestions; // 当前StackOverflow上的问题总数（用来衡量数据爬取的普适性）
    private int NoAnsQuestionTotal; // 当前StackOverflow上的无回答问题总数（计算比例，用来衡量数据爬取的普适性）
    private final DatabaseService databaseService = new DatabaseService();
            ; // 数据库服务

    private Timestamp lastRefreshTime; // 上次刷新时间

    public Timestamp getLastRefreshTime() {  // 获取上次刷新时间
        return lastRefreshTime;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    } // 设置每页大小

    public void setPageStep(int pageStep) {
        this.pageStep = pageStep;
    } // 设置每次获取所隔的页数

    public DataCollector(DatabaseService databaseService, int pageSize, int pageStep) throws IOException {
        // 自定义每页大小和每次获取所隔的页数
        this.pageSize = pageSize;
        this.pageStep = pageStep;
//        refresh();
    }

    public void refresh() throws IOException {
        // 刷新数据，主要更新问题总数和无回答问题总数
        String url = "https://api.stackexchange.com/2.3/questions";
        String params = "filter=total&tagged=java&site=stackoverflow&key=gqjiH6ExBbic7NaMoFxC)w((";
        String apiURL = url + "?" + params;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(apiURL);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseBody = EntityUtils.toString(entity);
            JSONObject data = JSON.parseObject(responseBody);
            totalQuestions = data.getInteger("total");
        }
        response.close();
        httpClient.close();
        url = "https://api.stackexchange.com/2.3/questions/no-answers";
        params = "filter=total&tagged=java&site=stackoverflow&key=gqjiH6ExBbic7NaMoFxC)w((";
        apiURL = url + "?" + params;
        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(apiURL);
        response = httpClient.execute(httpGet);
        entity = response.getEntity();
        if (entity != null) {
            String responseBody = EntityUtils.toString(entity);
            JSONObject data = JSON.parseObject(responseBody);
            NoAnsQuestionTotal = data.getInteger("total");
        }
        response.close();
        httpClient.close();
        lastRefreshTime = new Timestamp(System.currentTimeMillis());
    }

    public int getTotalQuestions() throws IOException {
        // 获取stackOverflow上准确的问题总数
        return totalQuestions;
    }

    public int getNoAnsQuestionTotal() throws IOException {
        // 获取stackOverflow上准确的无回答问题总数
        return NoAnsQuestionTotal;
    }

    public double getNoAnsPercent() throws IOException {
        // 获取stackOverflow上准确的无回答问题比例
        totalQuestions = getTotalQuestions();
        NoAnsQuestionTotal = getNoAnsQuestionTotal();
        return (double) NoAnsQuestionTotal/totalQuestions;
    }

    public List<JSONObject> getCommentsFromAnswer(String ids) {
        // 获取每个回答的评论，ids为answer_id的字符串，以分号分隔
        List<JSONObject> jsonObjectList = new ArrayList<>();
        int page = 1;
        while (true) {
            String url = "https://api.stackexchange.com/2.3/answers/" + ids + "/comments";
            String params = String.format("page=%d&pagesize=%d&filter=withbody&order=desc&sort=creation&site=stackoverflow&key=gqjiH6ExBbic7NaMoFxC)w((", page, pageSize);
            String apiURL = url + "?" + params;
            info(apiURL);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiURL);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    JSONObject data = JSON.parseObject(responseBody);
                    jsonObjectList.add(data);
                    if (!data.getBoolean("has_more")) {
                        break; // 获取所有评论数据
                    }
                }
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            page++;
        }
        return jsonObjectList;
    }

    public List<JSONObject> getCommentsFromQuestion(String ids) {
        // 获取每个问题的评论，ids为question_id的字符串，以分号分隔
        List<JSONObject> jsonObjectList = new ArrayList<>();
        int page = 1;
        while (true) {
            String url = "https://api.stackexchange.com/2.3/questions/" + ids + "/comments";
            String params = String.format("page=%d&pagesize=%d&filter=withbody&order=desc&sort=creation&site=stackoverflow&key=gqjiH6ExBbic7NaMoFxC)w((", page, pageSize);
            String apiURL = url + "?" + params;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiURL);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    JSONObject data = JSON.parseObject(responseBody);
                    jsonObjectList.add(data);
                    if (!data.getBoolean("has_more")) {
                        break; // 获取所有评论数据
                    }
                }
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            page++;
        }
        return jsonObjectList;
    }

    public List<JSONObject> getAnswers(String ids) {
        // 获取每个问题的回答，ids为question_id的字符串，以分号分隔
        List<JSONObject> jsonObjectList = new ArrayList<>();
        int page = 1;
        while (true) {
            String url = "https://api.stackexchange.com/2.3/questions/" + ids + "/answers";
            String params = String.format("page=%d&pagesize=%d&filter=withbody&order=desc&sort=activity&site=stackoverflow&key=gqjiH6ExBbic7NaMoFxC)w((", page, pageSize);
            String apiURL = url + "?" + params;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiURL);
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseBody = EntityUtils.toString(entity);
                    JSONObject data = JSON.parseObject(responseBody);
                    jsonObjectList.add(data);
                    if (!data.getBoolean("has_more")) break; // 获取所有回答数据
                }
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            page++;
        }
        return jsonObjectList;
    }

    public void collectData() throws IOException, SQLException {
        // 数据爬取
        int pageTotal = 20;
        int step = 36;
        for (int i = 1; i<=pageTotal; i++) {
            step += pageStep;
            // 按照activity排序，获取每隔pageStep页的pageSize个问题
            String redColorCode = "\u001B[31m";
            String resetColorCode = "\u001B[0m";
//            info(redColorCode + "获取所有提问数据ing：" + (int) (100*((double) i/pageTotal)) + "%" + resetColorCode);
            info(redColorCode + "获取所有提问数据ing：" + (int) (100*((double) i/pageTotal)) + "%" + resetColorCode);

            //questions
            String url = "https://api.stackexchange.com/2.3/questions";
            String params = String.format("page=%d&pagesize=%d&order=desc&sort=activity&tagged=java&filter=withbody&site=stackoverflow&key=gqjiH6ExBbic7NaMoFxC)w((", step, pageSize);
            String apiURL = url + "?" + params;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiURL);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JSONObject data = JSON.parseObject(responseBody);
                if (data.getJSONArray("items") == null) continue; // 无数据
                JSONArray items = data.getJSONArray("items");
                for (int j = 0; j<items.size(); j++) {
                    JSONObject item = items.getJSONObject(j); //list of questions
                    questionList.add(item);
                    questionJsonArray.add(item);
                }
            }
            response.close();
            httpClient.close();
        }
        // answers to each question
        List<Integer> currentQuestionIdList = new ArrayList<>();
        List<Integer> answerIdList = new ArrayList<>();
        for (int i = 0; i<questionList.size(); i++) {
            if (i%100 == 0 && i>0) { // 每100个问题获取一次回答
                String redColorCode = "\u001B[31m";
                String resetColorCode = "\u001B[0m";
                info(redColorCode + "获取所有回答数据ing：" + (int) (100*((double) i/questionList.size())) + "%" + resetColorCode);
                StringBuilder ids = new StringBuilder();
                for (int j = 0; j<currentQuestionIdList.size(); j++) {
                    ids.append(currentQuestionIdList.get(j));
                    if (j != currentQuestionIdList.size() - 1) {
                        ids.append(";");
                    }
                }
                List<JSONObject> jsonObjectList = getAnswers(ids.toString());
                for (JSONObject jsonObject : jsonObjectList) {
                    JSONArray answers = jsonObject.getJSONArray("items");
                    for (int j = 0; j<answers.size(); j++) {
                        JSONObject item = answers.getJSONObject(j);
                        if (item != null) {
                            answerList.add(item);
                            answerJsonArray.add(item);
                            answerIdList.add(item.getInteger("answer_id"));
                        }
                    }
                }
                currentQuestionIdList.clear();
            }
            JSONObject item = questionList.get(i);
            int questionId = item.getInteger("question_id");
            currentQuestionIdList.add(questionId);
        }
        if (!currentQuestionIdList.isEmpty()) { // 获取剩余的回答
            StringBuilder ids = new StringBuilder();
            for (int j = 0; j<currentQuestionIdList.size(); j++) {
                ids.append(currentQuestionIdList.get(j));
                if (j != currentQuestionIdList.size() - 1) {
                    ids.append(";");
                }
            }
            List<JSONObject> jsonObjectList = getAnswers(ids.toString());
            for (JSONObject jsonObject : jsonObjectList) {
                JSONArray answers = jsonObject.getJSONArray("items");
                for (int j = 0; j<answers.size(); j++) {
                    JSONObject item = answers.getJSONObject(j);
                    if (item != null) {
                        answerList.add(item);
                        answerJsonArray.add(item);
                        answerIdList.add(item.getInteger("answer_id"));
                    }
                }
            }
            currentQuestionIdList.clear();
        }
        // 查询每个问题的评论
        for (int i = 0; i<questionList.size(); i++) {
            if (i%100 == 0 && i>0) {
                String redColorCode = "\u001B[31m";
                String resetColorCode = "\u001B[0m";
                info(redColorCode + "获取所有问题评论数据ing：" + (int) (100*((double) i/questionList.size())) + "%" + resetColorCode);
                StringBuilder ids = new StringBuilder();
                for (int j = 0; j<currentQuestionIdList.size(); j++) {
                    ids.append(currentQuestionIdList.get(j));
                    if (j != currentQuestionIdList.size() - 1) {
                        ids.append(";");
                    }
                }
                List<JSONObject> jsonObjectList = getCommentsFromQuestion(ids.toString());
                for (JSONObject jsonObject : jsonObjectList) {
                    JSONArray answers = jsonObject.getJSONArray("items");
                    for (int j = 0; j<answers.size(); j++) {
                        JSONObject item = answers.getJSONObject(j);
                        if (item != null) {
                            commentList.add(item);
                            commentJsonArray.add(item);
                        }
                    }
                }
                currentQuestionIdList.clear();
            }
            JSONObject item = questionList.get(i);
            int questionId = item.getInteger("question_id");
            currentQuestionIdList.add(questionId);
        }
        if (currentQuestionIdList.size()>0) { // 获取剩余的评论
            StringBuilder ids = new StringBuilder();
            for (int j = 0; j<currentQuestionIdList.size(); j++) {
                ids.append(currentQuestionIdList.get(j));
                if (j != currentQuestionIdList.size() - 1) {
                    ids.append(";");
                }
            }
            List<JSONObject> jsonObjectList = getCommentsFromQuestion(ids.toString());
            for (JSONObject jsonObject : jsonObjectList) {
                JSONArray answers = jsonObject.getJSONArray("items");
                for (int j = 0; j<answers.size(); j++) {
                    JSONObject item = answers.getJSONObject(j);
                    if (item != null) {
                        commentList.add(item);
                        commentJsonArray.add(item);
                    }
                }
            }
            currentQuestionIdList.clear();
        }

        // 获取每个回答的评论
        List<Integer> currentAnswerIdList = new ArrayList<>();
        for (int i = 0; i<answerIdList.size(); i++) {
            if (i%100 == 0 && i>0) {
                String redColorCode = "\u001B[31m";
                String resetColorCode = "\u001B[0m";
                info(redColorCode + "获取所有评论数据ing：" + (int) (100*((double) i/answerIdList.size())) + "%" + resetColorCode);
                StringBuilder ids = new StringBuilder();
                for (int j = 0; j<currentAnswerIdList.size(); j++) {
                    ids.append(currentAnswerIdList.get(j));
                    if (j != currentAnswerIdList.size() - 1) {
                        ids.append(";");
                    }
                }
                List<JSONObject> jsonObjectList = getCommentsFromAnswer(ids.toString());
                for (JSONObject jsonObject : jsonObjectList) {
                    JSONArray answers = jsonObject.getJSONArray("items");
                    for (int j = 0; j<answers.size(); j++) {
                        JSONObject item = answers.getJSONObject(j);
                        if (item != null){
                            commentList.add(item);
                            commentJsonArray.add(item);
                        }
                    }
                }
                currentAnswerIdList.clear();
            }
            JSONObject item = answerList.get(i);
            int answerId = item.getInteger("answer_id");
            currentAnswerIdList.add(answerId);
        }
        if (currentAnswerIdList.size()>0) {
            String ids = "";
            for (int j = 0; j<currentAnswerIdList.size(); j++) {
                ids += currentAnswerIdList.get(j);
                if (j != currentAnswerIdList.size() - 1) {
                    ids += ";";
                }
            }
            List<JSONObject> jsonObjectList = getCommentsFromAnswer(ids);
            for (JSONObject jsonObject : jsonObjectList) {
                JSONArray answers = jsonObject.getJSONArray("items");
                for (int j = 0; j<answers.size(); j++) {
                    JSONObject item = answers.getJSONObject(j);
                    if (item != null) {
                        commentList.add(item);
                        commentJsonArray.add(item);
                    }
                }
            }
            currentAnswerIdList.clear();
        }

        info("爬取Question总数：" + questionList.size());
        info("爬取Answer总数：" + answerList.size());
        info("爬取Comment总数：" + commentList.size());


        String questionJsonString = questionJsonArray.toJSONString();
        String answerJsonString = answerJsonArray.toJSONString();
        String commentJsonString = commentJsonArray.toJSONString();
        String QuestionPath = "/Users/cooperz/SUSTech/2023_Fall/CS209_Java2/Final-Project-repo/CS209-Backend/src/main/java/cn/sustech/cs209backend/crawl/questions1.json";
        String answerPath = "/Users/cooperz/SUSTech/2023_Fall/CS209_Java2/Final-Project-repo/CS209-Backend/src/main/java/cn/sustech/cs209backend/crawl/answers1.json";
        String commentPath = "/Users/cooperz/SUSTech/2023_Fall/CS209_Java2/Final-Project-repo/CS209-Backend/src/main/java/cn/sustech/cs209backend/crawl/comments1.json";
        try {
            // 创建 FileWriter 对象
            FileWriter fileWriter = new FileWriter(QuestionPath);
            // 将 JSON 字符串写入文件
            fileWriter.write(questionJsonString);
            // 关闭 FileWriter
            fileWriter.close();
            System.out.println("questionJSON saved!");
            fileWriter = new FileWriter(answerPath);
            fileWriter.write(answerJsonString);
            fileWriter.close();
            System.out.println("answerJSON saved!");
            fileWriter = new FileWriter(commentPath);
            fileWriter.write(commentJsonString);
            fileWriter.close();
            System.out.println("commentJSON saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }



        // 将数据插入数据库
        String redColorCode = "\u001B[31m";
        String resetColorCode = "\u001B[0m";
        info(redColorCode + "数据插入数据库" + resetColorCode);

        for (int i = 0; i<questionList.size(); i++) {
            JSONObject questionItem = questionList.get(i);
            databaseService.insertQuestionJson(questionItem);
        }
        info(redColorCode + "Question已经插入数据库!" + resetColorCode);
        for (int i = 0; i<answerList.size(); i++) {
            JSONObject answerItem = answerList.get(i);
            databaseService.insertAnswerJSON(answerItem);
        }
        info(redColorCode + "Answer已经插入数据库!" + resetColorCode);
        for (int i = 0; i<commentList.size(); i++) {
            JSONObject commentItem = commentList.get(i);
            databaseService.insertCommentJSON(commentItem);
        }
        info(redColorCode + "Comment已经插入数据库!" + resetColorCode);
        info(redColorCode + "数据插入数据库完成！" + resetColorCode);
//        databaseService.insertUpdateTime();
//        info(redColorCode + "更新时间已经插入数据库！" + resetColorCode);
    }

    private static void info(String message) {
        //System out message with formatted time prefix
        System.out.printf("[%s] %s%n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), message);
    }


}
