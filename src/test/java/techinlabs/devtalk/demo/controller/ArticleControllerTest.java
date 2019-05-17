package techinlabs.devtalk.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import techinlabs.devtalk.demo.entity.Article;
import techinlabs.devtalk.demo.repository.ArticleRepository;
import techinlabs.devtalk.demo.service.ArticleService;

@RunWith(SpringRunner.class)
@WebFluxTest({ArticleController.class, ArticleService.class})
public class ArticleControllerTest {

  @Autowired
  WebTestClient client;

  @MockBean
  private ArticleRepository articleRepository;

  private Article article1;
  private Article article2;

  @Before
  public void setUp() {
    article1 = Article.builder()
        .id("1")
        .title("article a")
        .build();

    article2 = Article.builder()
        .id("2")
        .title("article b")
        .build();

    Mockito.when(articleRepository.findAll())
        .thenReturn(Flux.just(article1, article2));

    Mockito.when(articleRepository.findById(article1.getId()))
        .thenReturn(Mono.just(article1));

  }

  @Test
  public void getAllTest() {

    client.get().uri("/articles")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$[0].id").isEqualTo("1")
        .jsonPath("$[0].title").isEqualTo("article a")
        .jsonPath("$[1].id").isEqualTo("2")
        .jsonPath("$[1].title").isEqualTo("article b");
  }

  @Test
  public void findOneTest() {
    client.get().uri("/articles/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo("1")
        .jsonPath("$.title").isEqualTo("article a");
  }

  @Test
  public void findOneNotExistsTest() {
    Mockito.when(articleRepository.findById(Mockito.anyString()))
        .thenReturn(Mono.empty());

    client.get().uri("/articles/2")
        .exchange()
        .expectStatus().isNotFound();
  }

}
