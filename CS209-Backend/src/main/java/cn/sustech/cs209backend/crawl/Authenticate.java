package cn.sustech.cs209backend.crawl;

import java.net.URI;
import java.net.URISyntaxException;

public class Authenticate {
    static String userId = "118900";
    


    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("https://stackoverflow.com/oauth");
        String query = uri.getQuery();
    }
}
