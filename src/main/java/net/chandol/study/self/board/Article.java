package net.chandol.study.self.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Article {
    private Long id;
    private String title;
    private String contents;
    private String author;
    private List<Comment> comments;
}
