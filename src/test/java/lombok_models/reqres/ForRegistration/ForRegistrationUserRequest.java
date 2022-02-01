package lombok_models.reqres.ForRegistration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForRegistrationUserRequest {
    private String email;
    private String password;
}
