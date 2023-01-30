package com.lperilla.projects.basfchallenge.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "NERObject")
public class NERObject {

    @Id
    private UUID id;

    private String word;

    private String pos;

    private int beginPosition;

    private int endPosition;

}
