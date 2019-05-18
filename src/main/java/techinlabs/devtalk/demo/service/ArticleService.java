package techinlabs.devtalk.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import techinlabs.devtalk.demo.entity.Article;
import techinlabs.devtalk.demo.repository.ArticleRepository;

@Service
public class ArticleService {

  @Autowired
  ArticleRepository articleRepository;

  public Flux<Article> findAll(){
    return articleRepository.findAll();
  }

  public Mono<Article> findOne(String id){
    return articleRepository.findById(id)
        .switchIfEmpty(Mono.error(NotFoundException::new));
  }

  public Mono<Article> create(Article article){
    return articleRepository.save(article);
  }

}
