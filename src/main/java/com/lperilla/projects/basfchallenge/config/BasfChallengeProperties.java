package com.lperilla.projects.basfchallenge.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.io.File;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ConfigurationProperties(prefix = "basf")
public class BasfChallengeProperties {

    @NotNull
    private File directory;

}
