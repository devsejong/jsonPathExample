package net.chandol.study.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import net.chandol.study.self.board.Article;
import net.chandol.study.self.board.Board;
import net.chandol.study.self.board.Comment;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class SimpleJsonPathTest {

    // 값은 아랫쪽에서 설정함 ㅇㅅㅇ
    private static final String JSON;

    @Test
    public void simpleTraverse() {
        //given, when
        List<Comment> comments = JsonPath.parse(JSON)
                .read("$.articles[2].comments", new TypeRef<List<Comment>>() {});

        //then
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

    @Test
    public void findByValue(){
        // when
        List<Article> articles = JsonPath.parse(JSON).read("$.articles[?(@.author == 'author1')]", new TypeRef<List<Article>>() {});

        //then
        for (Article article : articles) {
            System.out.println(article);
        }
        System.out.println(articles.size());
    }


    static {
        final List<Comment> comments = new ArrayList<Comment>() {{
            add(new Comment(1L, "test1", "aaa1"));
            add(new Comment(2L, "test2", "aaa2"));
            add(new Comment(3L, "test3", "aaa3"));
            add(new Comment(4L, "test4", "aaa4"));
            add(new Comment(5L, "test5", "aaa5"));
        }};

        List<Article> articles = new ArrayList<Article>() {{
            add(new Article(1L, "title1", "contents1", "author1", comments));
            add(new Article(2L, "title2", "contents2", "author2", new ArrayList<Comment>()));
            add(new Article(3L, "title3", "contents3", "author3", comments.subList(2, 4)));
            add(new Article(4L, "title4", "contents4", "author4", comments.subList(1, 3)));
            add(new Article(5L, "title5", "contents5", "author5", comments.subList(0, 2)));
        }};

        Board board = new Board(1L, articles, "test");

        Configuration.setDefaults(new Configuration.Defaults() {
            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JSON = objectMapper.writeValueAsString(board);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
