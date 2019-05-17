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
  public void getAllTest() {
    Article article1 = Article.builder()
        .id("1")
        .title("article a")
        .build();

    Article article2 = Article.builder()
        .id("2")
        .title("article b")
        .build();

    Mockito.when(articleRepository.findAll())
        .thenReturn(Flux.just(article1, article2));

    StepVerifier.create(articleService.getAll())
        .expectNext(article1)
        .expectNext(article2)
        .verifyComplete();
  }

  @Test
  public void findOneTest() {
    Article article = Article.builder()
        .id("2")
        .title("article b")
        .build();

    Mockito.when(articleRepository.findById("2"))
        .thenReturn(Mono.just(article));

    StepVerifier.create(articleService.findOne("2"))
        .expectNext(article)
        .verifyComplete();
  }

  @Test
  public void findOneFailedTest() {
    Mockito.when(articleRepository.findById(Mockito.anyString()))
        .thenReturn(Mono.empty());

    StepVerifier.create(articleService.findOne("-1"))
        .expectError(NotFoundException.class)
        .verify();
  }
}
