package ru.example.recordbookbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class VersionedId implements Serializable {
    private static final long serialVersionUID = -8749840325401432685L;
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version", nullable = false)
    @JsonIgnore
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VersionedId entity = (VersionedId) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.version, entity.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

}