package cn.sustech.cs209backend.crawl;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class StanfordCoreNLPService {
    private final Properties props;
    private final StanfordCoreNLP pipeline;

    public StanfordCoreNLPService() {
        props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        props.setProperty("ner.useSUTime", "false");
        pipeline = new StanfordCoreNLP(props);
    }

    //  方法就是对于输入的字符串，返回一个 Jave API 的词频 Map
    public Map<String, Integer> getAllJavaAPI(String htmlText) {
        Map<String, Integer> javaAPIs = new HashMap<>();
        Annotation annotation = new Annotation(htmlText);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
            for (CoreLabel token : tokens) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // word 以 java. 开头
                if (word.startsWith("java.")) {
                    if (javaAPIs.containsKey(word)) {
                        javaAPIs.put(word, javaAPIs.get(word) + 1);
                    } else {
                        javaAPIs.put(word, 1);
                    }
                }
            }
        }
        return javaAPIs;
    }

    public Map<String, Integer> getAllKeywords(String htmlText, String[] keywords) {
        Map<String, Integer> map = new HashMap<>();
        Annotation annotation = new Annotation(htmlText);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
            for (CoreLabel token : tokens) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // word 以 keywords 中的任意一个开头
                for (String keyword : keywords) {
                    // java.lang.nullPointerException, i don't want a simple "Exception" string
                    if (word.contains(keyword) && !word.equals(keyword)) {
                        if (map.containsKey(word)) {
                            map.put(word, map.get(word) + 1);
                        } else {
                            map.put(word, 1);
                        }
                    }
                }
            }
        }
        return map;
    }


}
