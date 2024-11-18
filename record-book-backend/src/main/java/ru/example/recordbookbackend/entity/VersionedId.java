package ru.example.recordbookbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@ToString
@RequiredArgsConstructor
public class VersionedId implements Serializable {
    private static final long serialVersionUID = -8749840325401432685L;
    @Column(name = "id", nullable = false)
    private Long sheetId;

    @Column(name = "version", nullable = false)
    private Long sheetVersion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VersionedId entity = (VersionedId) o;
        return Objects.equals(this.sheetId, entity.sheetId) &&
                Objects.equals(this.sheetVersion, entity.sheetVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sheetId, sheetVersion);
    }

}