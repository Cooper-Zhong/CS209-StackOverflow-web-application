import java.sql.Timestamp;

public class test {
    public static void main(String[] args) {
        long seconds = 1702009594L; // 这个数字代表的是秒数

        java.util.Date date = new java.util.Date(seconds * 1000L);
        Timestamp timestamp = new Timestamp(seconds * 1000L);
        System.out.println(timestamp);

        String s = "hello 11";
        String[] ss = s.split(" ");
        for (String sss : ss)
            System.out.println(sss);
        System.out.println(ss.length);
    }
}
