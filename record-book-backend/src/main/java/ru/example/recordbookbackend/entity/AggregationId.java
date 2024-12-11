package ru.example.recordbookbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class AggregationId implements Serializable {
    private static final long serialVersionUID = -8749840325401432685L;
    @Column(name = "id", nullable = false)
    private Long aggregationId;

    @Column(name = "version", nullable = false)
    private Long aggregationVersion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AggregationId entity = (AggregationId) o;
        return Objects.equals(this.aggregationId, entity.aggregationId) &&
                Objects.equals(this.aggregationVersion, entity.aggregationVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregationId, aggregationVersion);
    }

}