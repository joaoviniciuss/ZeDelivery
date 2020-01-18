package ze.delivery.commons.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

public interface EntityBase {
    @ApiModelProperty(hidden = true)
    Long getId();
    void setId(Long id);

    @JsonIgnore
    default String getIdAsString() {
        return getId().toString();
    }
}

