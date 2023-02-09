package com.lperilla.projects.basfchallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NERObject implements Serializable {

    private String word;

    private String pos;

    private int beginPosition;

    private int endPosition;

}
