package com.lperilla.projects.basfchallenge.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ConfigurationProperties(prefix = "basf")
public class BasfChallengeProperties {

    @NotNull
    private File directory;

    private List<String> partOfSpeech = Arrays.asList("NN", "NNS", "NNP", "NNPS");
}
