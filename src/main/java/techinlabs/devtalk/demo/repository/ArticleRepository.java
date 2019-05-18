package techinlabs.devtalk.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import techinlabs.devtalk.demo.entity.Article;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {

}
