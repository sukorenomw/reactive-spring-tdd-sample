package techinlabs.devtalk.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
  ArticleRepository articleRepository;

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

    Article article3 = Article.builder()
        .id("3")
        .title("article 3")
        .build();

    Mockito.when(articleRepository.findAll())
        .thenReturn(Flux.just(article1, article2, article3));

    client.get().uri("/articles")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$[0].id").isEqualTo("1")
        .jsonPath("$[0].title").isEqualTo("article 1")
        .jsonPath("$[1].id").isEqualTo("2")
        .jsonPath("$[1].title").isEqualTo("article 2")
        .jsonPath("$[2].id").isEqualTo("3")
        .jsonPath("$[2].title").isEqualTo("article 3");
  }

  @Test
  public void findOneTest(){
    Article article3 = Article.builder()
        .id("3")
        .title("article 3")
        .build();

    Mockito.when(articleRepository.findById(Mockito.anyString()))
        .thenReturn(Mono.just(article3));

    client.get().uri("/articles/3")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo("3")
        .jsonPath("$.title").isEqualTo("article 3");
  }

  @Test
  public void findOneNotFoundTest(){
    Mockito.when(articleRepository.findById(Mockito.anyString()))
        .thenReturn(Mono.empty());

    client.get().uri("/articles/something-not-found")
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  public void createTest(){
    Article article = Article.builder()
        .id("3")
        .title("article 3")
        .build();

    Mockito.when(articleRepository.save(Mockito.any(Article.class)))
        .thenReturn(Mono.just(article));

    client.post().uri("/articles")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(Mono.just(article), Article.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("$.id").isEqualTo("3")
        .jsonPath("$.title").isEqualTo("article 3");

  }


}
