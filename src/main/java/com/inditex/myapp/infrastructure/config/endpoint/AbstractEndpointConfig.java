package com.inditex.myapp.infrastructure.config.endpoint;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class AbstractEndpointConfig {

    @NotBlank
    private String url;
}
