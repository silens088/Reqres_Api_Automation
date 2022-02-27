package models.singleResource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleResourceData {
    private Long id;
    private String name;
    private Long year;
    private String color;
    @JsonProperty("pantone_value") //спец аннотация которая переводит название, в имя без ниж.подчеркивания
    private String pantoneValue;
}
