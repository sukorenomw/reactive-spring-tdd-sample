package techinlabs.devtalk.demo.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import techinlabs.devtalk.demo.entity.Article;
import techinlabs.devtalk.demo.repository.ArticleRepository;

@Service
public class ArticleService {

  private final ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public Flux<Article> getAll(){
    return articleRepository.findAll();
  }

  public Mono<Article> findOne(String id){
    return articleRepository.findById(id)
        .switchIfEmpty(Mono.error(NotFoundException::new));
  }
}
