package cn.sustech.cs209backend.crawl;

import cn.sustech.cs209backend.entity.User;


import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.Map;

public class DatabaseService {

    private Connection connection;
    private static final String DB_HOST = "10.26.80.100";
    private static final String DB_USER = "cooper";
    private static final String DB_PASSWORD = "cooper";

//    private static final String DB_HOST = "localhost";
//    private static final String DB_USER = "postgres";
//    private static final String DB_PASSWORD = "Lhx13922727768";

    private static final String DB_DATABASE = "stackoverflow";
    private static final int DB_PORT = 5432;

    private PreparedStatement questionStatement;

    private PreparedStatement answerStatement;

    private PreparedStatement commentStatement;


    private PreparedStatement selectOwnerStatement; // 防止重复插入
    private PreparedStatement ownerStatement;


    private PreparedStatement selectTagStatement;

    private PreparedStatement tagStatement;

    private PreparedStatement selectApiStatement;

    private PreparedStatement apiStatement;

    private PreparedStatement selectBugStatement;

    private PreparedStatement bugStatement;

    private PreparedStatement connectionTagAndQuestionStatement;

    private PreparedStatement connectionQuestionAndApiStatement;

    private PreparedStatement connectionAnswerAndApiStatement;

    private PreparedStatement connectionCommentAndApiStatement;

    private PreparedStatement connectionQuestionAndBugStatement;

    private PreparedStatement connectionAnswerAndBugStatement;

    private PreparedStatement connectionCommentAndBugStatement;


//    @Autowired
//    private AnswerRepo answerRepo;
//
//    @Autowired
//    private QuestionRepo questionRepo;
//
//    @Autowired
//    private CommentRepo commentRepo;
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private TagRepo tagRepo;
//
//    @Autowired
//    private ApiRepo apiRepo;
//
//    @Autowired
//    private BugRepo bugRepo;

    private final StanfordCoreNLPService stanfordCoreNLPService;

    public DatabaseService() {

//        this.host = host;
//        this.port = port;
//        this.user = user;
//        this.password = password;
//        this.database = database;
        connection = null;
        stanfordCoreNLPService = new StanfordCoreNLPService();
        connect();
        initializeStatements();
    }

    public void initializeStatements() {
        questionStatement = prepareStatement(
                "insert into questions (question_id, account_id, answer_count, body, content_license, creation_date, is_answered,\n" +
                        "last_activity_date, last_edit_date, link, score, title, view_count) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT DO NOTHING;");

        answerStatement = prepareStatement(
                "insert into answers (answer_id, account_id, body, content_license, creation_date, is_accepted,\n" +
                        "last_activity_date, last_edit_date, question_id, score) " +
                        "values (?,?,?,?,?,?,?,?,?,?) ON CONFLICT DO NOTHING;");

        commentStatement = prepareStatement(
                "insert into comments (comment_id, account_id, post_id, body, content_license, creation_date, edited, score) " +
                        "values (?,?,?,?,?,?,?,?) ON CONFLICT DO NOTHING;");


        selectOwnerStatement = prepareStatement(
                "select count(*) from users where account_id = ?"); // 防止重复插入
        ownerStatement = prepareStatement(

                "insert into users (account_id, display_name, link, reputation) values (?,?,?,?) ON CONFLICT DO NOTHING;");


        selectTagStatement = prepareStatement(
                "select count(*) from tags where tag_name = ?");

        tagStatement = prepareStatement(
                "insert into tags (tag_name) values (?) ON CONFLICT DO NOTHING;");

        selectApiStatement = prepareStatement(
                "select count(*) from apis where api_name = ?");

        apiStatement = prepareStatement(
                "insert into apis (api_name) values (?) ON CONFLICT DO NOTHING;");

        selectBugStatement = prepareStatement(
                "select count(*) from bugs where bug_name = ?");

        bugStatement = prepareStatement(
                "insert into bugs (bug_name) values (?) ON CONFLICT DO NOTHING;");

        connectionTagAndQuestionStatement = prepareStatement(
                "insert into questions_tags values (?,?) ON CONFLICT DO NOTHING;");

        connectionQuestionAndApiStatement = prepareStatement(
                "insert into questions_apis values (?,?,?) ON CONFLICT DO NOTHING;");

        connectionAnswerAndApiStatement = prepareStatement(
                "insert into answers_apis values (?,?,?) ON CONFLICT DO NOTHING;");

        connectionCommentAndApiStatement = prepareStatement(

                "insert into connection_comment_and_api values (?,?,?) ON CONFLICT DO NOTHING;");

        connectionQuestionAndBugStatement = prepareStatement(
                "insert into questions_bugs values (?,?,?) ON CONFLICT DO NOTHING;");

        connectionAnswerAndBugStatement = prepareStatement(
                "insert into answers_bugs values (?,?,?) ON CONFLICT DO NOTHING;");

        connectionCommentAndBugStatement = prepareStatement(

                "insert into comments_bugs values (?,?,?) ON CONFLICT DO NOTHING;");
    }

    public boolean connect() {
        // 连接数据库
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_DATABASE,
                    DB_USER,
                    DB_PASSWORD
            );
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot connect to the database.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Cannot close the connection.");
            e.printStackTrace();
        }
    }

    private PreparedStatement prepareStatement(String sql) {
        // 准备 SQL 语句
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("Cannot prepare the statement.");
            e.printStackTrace();
            return null;
        }
    }

    public void disableForeignKeyCheck() throws SQLException {
        // 关闭外键约束
        Statement statement = connection.createStatement();
        statement.execute("SET session_replication_role = replica;");
        statement.close();
    }

    public void enableForeignKeyCheck() throws SQLException {
        // 开启外键约束
        Statement statement = connection.createStatement();
        statement.execute("SET session_replication_role = DEFAULT;");
        statement.close();
    }

    public void insertQuestion(JSONObject questionJSON) throws SQLException {
        // 在Question表中插入一条记录

        int question_id = questionJSON.getInteger("question_id");
        Timestamp last_activity_date = convertTime(questionJSON.getInteger("last_activity_date"));
        Timestamp last_edit_date = convertTime(questionJSON.getInteger("last_edit_date"));
        Timestamp creation_date = convertTime(questionJSON.getInteger("creation_date"));
        int score = questionJSON.getInteger("score");
        String link = questionJSON.getString("link");
        int answer_count = questionJSON.getInteger("answer_count");
        int view_count = questionJSON.getInteger("view_count");
        String content_license = questionJSON.getString("content_license");
        String title = questionJSON.getString("title");
        String body = questionJSON.getString("body");
        boolean isAnswered = questionJSON.getBoolean("is_answered");
        int account_id = -1;
        try {
            account_id = questionJSON.getJSONObject("owner").getInteger("account_id");
        } catch (Exception e) { // 有的问题没有owner
        }

        // 在Question表中插入一条记录

        this.questionStatement.setInt(1, question_id);
        this.questionStatement.setInt(2, account_id);
        this.questionStatement.setInt(3, answer_count);
        this.questionStatement.setString(4, body);
        this.questionStatement.setString(5, content_license);
        this.questionStatement.setTimestamp(6, creation_date);
        this.questionStatement.setBoolean(7, isAnswered);
        this.questionStatement.setTimestamp(8, last_activity_date);
        this.questionStatement.setTimestamp(9, last_edit_date);
        this.questionStatement.setString(10, link);
        this.questionStatement.setInt(11, score);
        this.questionStatement.setString(12, title);
        this.questionStatement.setInt(13, view_count);
        try {
            this.questionStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void insertTag(String tag_name) throws SQLException {
        // 在Tag表中插入一条记录
//        selectTagStatement.setString(1, tag_name);
//        ResultSet resultSet = selectTagStatement.executeQuery();
//        resultSet.next();
//        if (resultSet.getInt(1)>0) {
//            return;
//        }
        tagStatement.setString(1, tag_name);
        try {
            tagStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertApi(String api_name) throws SQLException {
        // 在Api表中插入一条记录
//        selectApiStatement.setString(1, api_name);
//        ResultSet resultSet = selectApiStatement.executeQuery();
//        resultSet.next();
//        if (resultSet.getInt(1)>0) {
//            return;
//        }

        apiStatement.setString(1, api_name);
        try {
            apiStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertBug(String bug_name) throws SQLException {
        // 在Bug表中插入一条记录


//        selectBugStatement.setString(1, bug_name);
//        ResultSet resultSet = selectBugStatement.executeQuery();
//        resultSet.next();
//        if (resultSet.getInt(1)>0) {
//            return;
//        }

        bugStatement.setString(1, bug_name);
        try {
            bugStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }


    public void insertAnswer(JSONObject answerJSON) throws SQLException {
        // 在Answer表中插入一条记录
        int answer_id = answerJSON.getInteger("answer_id");
        Timestamp last_activity_date = convertTime(answerJSON.getInteger("last_activity_date"));
        Timestamp last_edit_date = convertTime(answerJSON.getInteger("last_edit_date"));
        Timestamp creation_date = convertTime(answerJSON.getInteger("creation_date"));
        int score = answerJSON.getInteger("score");
        boolean is_accepted = answerJSON.getBoolean("is_accepted");
        String content_license = answerJSON.getString("content_license");
        int question_id = answerJSON.getInteger("question_id");
        String body = answerJSON.getString("body");

        int account_id = -1;
        try {
            account_id = answerJSON.getJSONObject("owner").getInteger("account_id");
        } catch (Exception e) {
        }

//        PreparedStatement statement = this.prepareStatement(
//                "insert into answers (answer_id, account_id, body, content_license, creation_date, is_accepted,\n" +
//                        "last_activity_date, last_edit_date, question_id, score) " +
//                        "values (?,?,?,?,?,?,?,?,?,?);");

        answerStatement.setInt(1, answer_id);
        answerStatement.setInt(2, account_id);
        answerStatement.setString(3, body);
        answerStatement.setString(4, content_license);
        answerStatement.setTimestamp(5, creation_date);
        answerStatement.setBoolean(6, is_accepted);
        answerStatement.setTimestamp(7, last_activity_date);
        answerStatement.setTimestamp(8, last_edit_date);
        answerStatement.setInt(9, question_id);
        answerStatement.setInt(10, score);

        try {
            answerStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertOwner(int account_id, String link, String display_name, int reputation) throws SQLException {
        // 在Owner表中插入一条记录
//        selectOwnerStatement.setInt(1, account_id);
//        ResultSet resultSet = selectOwnerStatement.executeQuery();
//        resultSet.next();
//        if (resultSet.getInt(1)>0) {
//            return;
//        }

        ownerStatement.setInt(1, account_id);
        ownerStatement.setString(2, display_name);
        ownerStatement.setString(3, link);
        ownerStatement.setInt(4, reputation);

        try {
            ownerStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertComment(JSONObject commentJSON)
            throws SQLException {
        // 在Comment表中插入一条记录
        int comment_id = commentJSON.getInteger("comment_id");
        boolean edited = commentJSON.getBoolean("edited");
        int post_id = commentJSON.getInteger("post_id");
        String body = commentJSON.getString("body");
        Timestamp creation_date = convertTime(commentJSON.getInteger("creation_date"));
        int score = commentJSON.getInteger("score");
        String content_license = commentJSON.getString("content_license");

        int account_id = -1;
        try {
            account_id = commentJSON.getJSONObject("owner").getInteger("account_id");
        } catch (Exception e) {
        }
//
//        PreparedStatement statement = this.prepareStatement(
//                "insert into comments (comment_id, account_id, post_id, body, content_license, creation_date, edited, score) " +
//                        "values (?,?,?,?,?,?,?,?);");

        commentStatement.setInt(1, comment_id);
        commentStatement.setInt(2, account_id);
        commentStatement.setInt(3, post_id); // 问题或回答的id,没有加外键
        commentStatement.setString(4, body);
        commentStatement.setString(5, content_license);
        commentStatement.setTimestamp(6, creation_date);
        commentStatement.setBoolean(7, edited);
        commentStatement.setInt(8, score);

        try {
            commentStatement.executeUpdate();
        } catch (SQLException e) {
        }

    }

    // relationships ----------------------------------------------------------
    // apis

    public void insertConnectionTagAndQuestion(String tag_name, int question_id)
            throws SQLException {
        // many to many
        connectionTagAndQuestionStatement.setInt(1, question_id);
        connectionTagAndQuestionStatement.setString(2, tag_name);
        try {
            connectionTagAndQuestionStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertConnectionQuestionAndApi(int question_id, String api_name, int count)
            throws SQLException {
        connectionQuestionAndApiStatement.setInt(1, question_id);
        connectionQuestionAndApiStatement.setString(2, api_name);
        connectionQuestionAndApiStatement.setInt(3, count);
        try {
            connectionQuestionAndApiStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertConnectionAnswerAndApi(int answer_id, String api_name, int count)
            throws SQLException {
        connectionAnswerAndApiStatement.setInt(1, answer_id);
        connectionAnswerAndApiStatement.setString(2, api_name);
        connectionAnswerAndApiStatement.setInt(3, count);
        try {
            connectionAnswerAndApiStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertConnectionCommentAndApi(int comment_id, String api_name, int count)
            throws SQLException {
        connectionCommentAndApiStatement.setInt(1, comment_id);
        connectionCommentAndApiStatement.setString(2, api_name);
        connectionCommentAndApiStatement.setInt(3, count);
        try {
            connectionCommentAndApiStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }


    // bugs ----------------------------------------------------------
    public void insertConnectionQuestionAndBug(int question_id, String bug_name, int count)
            throws SQLException {
        connectionQuestionAndBugStatement.setInt(1, question_id);
        connectionQuestionAndBugStatement.setString(2, bug_name);
        connectionQuestionAndBugStatement.setInt(3, count);
        try {
            connectionQuestionAndBugStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertConnectionAnswerAndBug(int answer_id, String bug_name, int count)
            throws SQLException {
        connectionAnswerAndBugStatement.setInt(1, answer_id);
        connectionAnswerAndBugStatement.setString(2, bug_name);
        connectionAnswerAndBugStatement.setInt(3, count);
        try {
            connectionAnswerAndBugStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void insertConnectionCommentAndBug(int comment_id, String bug_name, int count)
            throws SQLException {
        connectionCommentAndBugStatement.setInt(1, comment_id);
        connectionCommentAndBugStatement.setString(2, bug_name);
        connectionCommentAndBugStatement.setInt(3, count);
        try {
            connectionCommentAndBugStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    // ----------------------------------------------------------

    private Timestamp convertTime(Integer date) {
        // 将Unix时间戳转换为Timestamp
        if (date == null) {
            return null;
        }
        return new Timestamp(date*1000L);
    }

    public void insertUpdateTime() throws SQLException {
        PreparedStatement statement = this.prepareStatement(
                "insert into last_update values (?);");
        statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        statement.executeUpdate();
    }

    public void insertQuestionJson(JSONObject questionJSON) throws SQLException {
        // 将一个问题的JSON数据插入到数据库中
        JSONObject ownerJson = questionJSON.getJSONObject("owner");
        User owner = new User(
                ownerJson.getInteger("account_id") == null ? -1 : ownerJson.getInteger("account_id"),
                ownerJson.getString("link") == null ? "" : ownerJson.getString("link"),
                ownerJson.getString("display_name"),
                ownerJson.getInteger("reputation") == null ? -1 : ownerJson.getInteger("reputation")
        );
        insertOwner(
                owner.getAccountId(),
                owner.getLink(),
                owner.getDisplayName(),
                owner.getReputation());

        for (Object tag : questionJSON.getJSONArray("tags")) {
            insertTag((String) tag);
        }

        insertQuestion(questionJSON);

        for (Object tag : questionJSON.getJSONArray("tags")) {
            insertConnectionTagAndQuestion((String) tag, questionJSON.getInteger("question_id"));
        }

//        insertOwner(
//                owner.getAccountId(),
//                owner.getLink(),
//                owner.getDisplayName(),
//                owner.getReputation()
//        );

        Map<String, Integer> apiCount = stanfordCoreNLPService.getAllJavaAPI(questionJSON.getString("title") + " " +
                questionJSON.getString("body"));

        String[] bugs = {"Error", "Exception"};
        Map<String, Integer> bugCount = stanfordCoreNLPService.getAllKeywords(questionJSON.getString("title") + " " +
                questionJSON.getString("body"), bugs);

        for (Map.Entry<String, Integer> entry : apiCount.entrySet()) {
            insertApi(entry.getKey());
            insertConnectionQuestionAndApi(questionJSON.getInteger("question_id"), entry.getKey(),
                    entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : bugCount.entrySet()) {
            insertBug(entry.getKey());
            insertConnectionQuestionAndBug(questionJSON.getInteger("question_id"), entry.getKey(),
                    entry.getValue());
        }


    }

    public void insertAnswerJSON(JSONObject answerJSON) throws SQLException {
        // 将一个回答的JSON数据插入到数据库中
        JSONObject ownerJson = answerJSON.getJSONObject("owner");
        User owner = new User(
                ownerJson.getInteger("account_id") == null ? -1 : ownerJson.getInteger("account_id"),
                ownerJson.getString("link") == null ? "" : ownerJson.getString("link"),
                ownerJson.getString("display_name"),
                ownerJson.getInteger("reputation") == null ? -1 : ownerJson.getInteger("reputation")
        );
        insertOwner(
                owner.getAccountId(),
                owner.getLink(),
                owner.getDisplayName(),
                owner.getReputation()
        );
        insertAnswer(answerJSON);

        Map<String, Integer> apiCount = stanfordCoreNLPService.getAllJavaAPI(
                answerJSON.getString("body"));

        Map<String, Integer> bugCount = stanfordCoreNLPService.getAllKeywords(
                answerJSON.getString("body"), new String[]{"Error", "Exception"});

        for (Map.Entry<String, Integer> entry : apiCount.entrySet()) {
            insertApi(entry.getKey());
            insertConnectionAnswerAndApi(answerJSON.getInteger("answer_id"), entry.getKey(),
                    entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : bugCount.entrySet()) {
            insertBug(entry.getKey());
            insertConnectionAnswerAndBug(answerJSON.getInteger("answer_id"), entry.getKey(),
                    entry.getValue());
        }
    }

    void insertCommentJSON(JSONObject commentJSON) throws SQLException {
        // 将一个评论的JSON数据插入到数据库中
        JSONObject ownerJson = commentJSON.getJSONObject("owner");
        User owner = new User(
                ownerJson.getInteger("account_id") == null ? -1 : ownerJson.getInteger("account_id"),
                ownerJson.getString("link") == null ? "" : ownerJson.getString("link"),
                ownerJson.getString("display_name"),
                ownerJson.getInteger("reputation") == null ? -1 : ownerJson.getInteger("reputation")
        );
        // public void insertComment(int comment_id, boolean edited, int post_id, String body, Timestamp creation_date, int score, String content_license, int account_id) throws SQLException {
        insertOwner(
                owner.getAccountId(),
                owner.getLink(),
                owner.getDisplayName(),
                owner.getReputation()
        );

        insertComment(commentJSON);

        Map<String, Integer> apiCount = stanfordCoreNLPService.getAllJavaAPI(
                commentJSON.getString("body"));

        Map<String, Integer> bugCount = stanfordCoreNLPService.getAllKeywords(
                commentJSON.getString("body"), new String[]{"Error", "Exception"});

        for (Map.Entry<String, Integer> entry : apiCount.entrySet()) {
            insertApi(entry.getKey());
            insertConnectionCommentAndApi(commentJSON.getInteger("comment_id"), entry.getKey(),
                    entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : bugCount.entrySet()) {
            insertBug(entry.getKey());
            insertConnectionCommentAndBug(commentJSON.getInteger("comment_id"), entry.getKey(),
                    entry.getValue());
        }
    }
}
