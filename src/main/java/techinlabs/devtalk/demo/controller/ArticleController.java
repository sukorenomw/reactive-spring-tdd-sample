package techinlabs.devtalk.demo.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import techinlabs.devtalk.demo.entity.Article;
import techinlabs.devtalk.demo.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @GetMapping
  public Flux<Article> findAll(){
    return articleService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Article> findOne(@PathVariable String id){
    System.out.println(id);
    return articleService.findOne(id);
  }

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public Mono<Article> create(@Valid  @RequestBody Article article){
    return articleService.create(article);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public Mono handleNotFound(){
    return Mono.empty();
  }
}
