package com.posada.santiago.postscommentseda.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class StorableEvent {

    @Id
    private String id;
    private String body;
    private String type;
}
