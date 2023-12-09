package cn.sustech.cs209backend.crawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.json.Json;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Crawler {

    private static final String DB_HOST = "10.26.80.100";
    private static final String DB_USER = "cooper";
    private static final String DB_PASSWORD = "cooper";
    private static final String DB_DATABASE = "stackoverflow";
    private static final int DB_PORT = 5432;

    public static void crawl() throws SQLException, IOException {
        DatabaseService databaseService = new DatabaseService();
        DataCollector dataCollector = new DataCollector(databaseService, 50, 168);
//        databaseService.connect();
        databaseService.disableForeignKeyCheck();
        dataCollector.collectData();
        databaseService.enableForeignKeyCheck();
        databaseService.close();
    }

    public static void main(String[] args) throws SQLException, IOException {
//        crawl();
        save();

    }

    public static void save() throws SQLException, IOException {
        String questionJsonPath = "/Users/cooperz/SUSTech/2023_Fall/CS209_Java2/Final-Project-repo/CS209-Backend/src/main/java/cn/sustech/cs209backend/crawl/questions.json";
        String answerJsonPath = "/Users/cooperz/SUSTech/2023_Fall/CS209_Java2/Final-Project-repo/CS209-Backend/src/main/java/cn/sustech/cs209backend/crawl/answers.json";
        String commentJsonPath = "/Users/cooperz/SUSTech/2023_Fall/CS209_Java2/Final-Project-repo/CS209-Backend/src/main/java/cn/sustech/cs209backend/crawl/comments.json";
        DatabaseService databaseService = new DatabaseService();


        try {
            // question
            BufferedReader bufferedReader = new BufferedReader(new FileReader(questionJsonPath));
            StringBuilder content = new StringBuilder();
            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                content.append(line);
//            }
//            bufferedReader.close();
//            JSONArray jsonArray = JSONArray.parseArray(content.toString());
//            for (Object obj : jsonArray) {
//                JSONObject questionItem = (JSONObject) obj;
//                databaseService.insertQuestionJson(questionItem);
//            }
            JSONArray jsonArray;

            // answer
            bufferedReader = new BufferedReader(new FileReader(answerJsonPath));
            content = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            bufferedReader.close();
            jsonArray = JSONArray.parseArray(content.toString());
            for (Object obj : jsonArray) {
                JSONObject answerItem = (JSONObject) obj;
                databaseService.insertAnswerJSON(answerItem);
            }

            // comment
            bufferedReader = new BufferedReader(new FileReader(commentJsonPath));
            content = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            bufferedReader.close();
            jsonArray = JSONArray.parseArray(content.toString());
            for (Object obj : jsonArray) {
                JSONObject commentItem = (JSONObject) obj;
                databaseService.insertCommentJSON(commentItem);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void info(String message) {
        //System out message with formatted time prefix
        System.out.printf("[%s] %s%n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), message);
    }
}