package lombok_models.reqres.ForUsers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForUsersCreateResponse {
    private String name;
    private String job;
    private String id;
    private String createdAt;
}
