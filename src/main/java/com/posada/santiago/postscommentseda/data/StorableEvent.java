package com.posada.santiago.postscommentseda.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class StorableEvent {

    @Id
    private String id;
    private String parentId;
    private String body;
    private String type;


}
