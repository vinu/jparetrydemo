package rocks.vinu.springjpa.jpaupdatedemo.jpa.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class DataRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String col1;

    private String col2;

    private boolean verified = false;

    private LocalDateTime updated;

    private LocalDateTime created;


    @PrePersist
    public void postCreate() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void postUpdate() {
        this.updated = LocalDateTime.now();
    }
}
