package models.singleResource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

//тут мы сделали класс с классами моделей, для кусков Data q Support
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleResourceMain {
    private SingleResourceData data;
    private SingleResourceSupport support;
}
