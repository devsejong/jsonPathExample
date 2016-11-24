package net.chandol.study.self.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Board {
    private Long id;
    private List<Article> articles;
    private String name;
}
