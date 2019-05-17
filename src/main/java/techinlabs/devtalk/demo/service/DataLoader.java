package techinlabs.devtalk.demo.service;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import techinlabs.devtalk.demo.entity.Article;
import techinlabs.devtalk.demo.repository.ArticleRepository;

@Component
public class DataLoader {
    private final ArticleRepository repo;

    public DataLoader(ArticleRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    private void load() {
        repo.deleteAll().thenMany(
            Flux.just(
                Article.builder().id("1").title("article 1").build(),
                Article.builder().id("2").title("article 2").build())
                .map(repo::save))
            .thenMany(repo.findAll())
            .subscribeOn(Schedulers.immediate());
    }
}
