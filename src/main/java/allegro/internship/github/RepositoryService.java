package allegro.internship.github;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
class RepositoryService {

    private final RestTemplate restTemplate;
    private static final String REPOS_URL = "https://api.github.com/users/allegro/repos";

    Repository getLastModifiedRepository() {
        Repository[] repositories = Optional.ofNullable(restTemplate.getForObject(REPOS_URL + "?sort=pushed&per_page=1", Repository[].class))
                .orElseThrow(GitHubInternalError::new);

        return Arrays.stream(repositories)
                .findFirst()
                .orElseThrow(RepositoryNotFoundException::new);
    }
}
