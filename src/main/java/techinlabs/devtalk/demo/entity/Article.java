package techinlabs.devtalk.demo.entity;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
  private String id;

  @NotEmpty(message = "title should not be empty")
  private String title;

}
