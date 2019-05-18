package techinlabs.devtalk.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import techinlabs.devtalk.demo.entity.Article;
import techinlabs.devtalk.demo.repository.ArticleRepository;

@RunWith(SpringRunner.class)
public class ArticleServiceTest {

  @InjectMocks
  private ArticleService articleService;

  @Mock
  private ArticleRepository articleRepository;

  @Test
  public void findAllTest(){

    Article article1 = Article.builder()
        .id("1")
        .title("article 1")
        .build();

    Article article2 = Article.builder()
        .id("2")
        .title("article 2")
        .build();

    Mockito.when(articleRepository.findAll())
        .thenReturn(Flux.just(article1, article2));

    StepVerifier.create(articleService.findAll())
        .expectNext(article1)
        .expectNext(article2)
        .expectComplete()
        .verify();

  }

  @Test
  public void findOneTest(){
    Article article1 = Article.builder()
        .id("1")
        .title("article 1")
        .build();

    Mockito.when(articleRepository.findById(Mockito.anyString()))
        .thenReturn(Mono.just(article1));

    StepVerifier.create(articleService.findOne("1"))
        .expectNext(article1)
        .expectComplete()
        .verify();

  }

  @Test
  public void findOneFailedTest(){
    Mockito.when(articleRepository.findById(Mockito.anyString()))
        .thenReturn(Mono.empty());

    StepVerifier.create(articleService.findOne("somehing"))
        .expectError(NotFoundException.class)
        .verify();
  }

}
