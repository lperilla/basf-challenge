package com.lperilla.projects.basfchallenge.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "errors")
public class Error implements Serializable {

    @Id
    private UUID id;

    private String fileName;

    private LocalDateTime timestamp;

    private String failedMessage;

    private String cause;

}
