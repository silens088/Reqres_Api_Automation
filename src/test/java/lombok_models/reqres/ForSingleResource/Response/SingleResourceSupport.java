package lombok_models.reqres.ForSingleResource.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleResourceSupport {
    private String url;
    private String text;
}
