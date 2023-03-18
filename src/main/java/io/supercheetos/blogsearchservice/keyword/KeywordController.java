package io.supercheetos.blogsearchservice.keyword;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/keywords")
@AllArgsConstructor
public class KeywordController {
    private final KeywordService service;

    @GetMapping("/top10")
    public List<Responses.Keyword> getTop10() {
        return service.findTop10();
    }
}
