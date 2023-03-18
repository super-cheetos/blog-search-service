package io.supercheetos.blogsearchservice.keyword;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/keywords")
@AllArgsConstructor
public class KeywordController {
    private final KeywordService service;

    @GetMapping("/top10")
    public KeywordDto.Top10Response getTop10() {
        return KeywordDto.Top10Response.ok(service.findTop10());
    }
}
