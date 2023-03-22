package io.supercheetos.blogsearchservice.keyword;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KeywordEventListener {
    private final KeywordService keywordService;

    @EventListener(KeywordQueryEvent.class)
    public void handleKeywordQueryEvent(KeywordQueryEvent event) {
        var keywordNames = event.query().split(" +");
        for (var keywordName : keywordNames) {
            keywordService.increment(keywordName);
        }
    }
}
