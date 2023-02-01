package com.epam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record CheckListEntity(String id, String name, String idBoard, String idCard) {
}
