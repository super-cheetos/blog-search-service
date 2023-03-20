package io.supercheetos.blogsearchservice.blog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/blog/documents")
@Validated
public class BlogController {
    private final BlogService service;

    @GetMapping("/search")
    public BlogDto.SearchResponse searchBlogDocuments(
            @RequestParam @NotEmpty String query,
            @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) int size,
            @RequestParam(required = false, defaultValue = "ACCURACY") BlogSort sort
    ) {
        return BlogDto.SearchResponse.ok(service.search(query, page, size, sort));
    }
}