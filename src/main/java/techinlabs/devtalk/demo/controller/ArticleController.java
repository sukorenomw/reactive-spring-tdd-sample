package techinlabs.devtalk.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import techinlabs.devtalk.demo.entity.Article;
import techinlabs.devtalk.demo.entity.ErrorResponse;
import techinlabs.devtalk.demo.repository.ArticleRepository;
import techinlabs.devtalk.demo.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

  private final ArticleService articleService;
  private final ArticleRepository repo;

  public ArticleController(ArticleService articleService, ArticleRepository repo) {
    this.articleService = articleService;
    this.repo = repo;
  }

  @GetMapping
  public Flux<Article> getAll() {
    return articleService.getAll();
  }

  @GetMapping("/{id}")
  public Mono<Article> findOne(@PathVariable String id){
    return articleService.findOne(id);
  }

  @PostMapping
  public Flux generateArticle() {
    return repo.deleteAll().thenMany(
        Flux.just(
            Article.builder().id("1").title("article 1").build(),
            Article.builder().id("2").title("article 2").build())
            .flatMap(repo::save))
        .subscribeOn(Schedulers.immediate());
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public Mono<ErrorResponse> notFoundExceptionHandler() { //
    return Mono.just(ErrorResponse.builder()
        .code("NOT_FOUND")
        .message("Article not found")
        .build());
  }

}
