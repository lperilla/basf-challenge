package com.lperilla.projects.basfchallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Patent")
public class Patent {

    @Id
    private String documentId;

    private String title;

    private String abstractText;

    private String date;

    private String country;

    private String docNumber;

    private String kind;

    private List<NERObject> ner;
}
