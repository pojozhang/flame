package io.bayberry.flame.core.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "branches")
@CompoundIndex(name = "unique_index", unique = true, def = "{'name':1,'documentId':1}")
public class Branch extends BaseEntity {

    private String name;
    private Header header;
    private Archive archive;
    private Long documentId;
    private Long originId;

    public boolean hasOrigin() {
        return this.originId != null;
    }
}
