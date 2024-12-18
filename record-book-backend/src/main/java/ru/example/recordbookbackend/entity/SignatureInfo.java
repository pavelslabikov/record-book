package ru.example.recordbookbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "signature_info")
public class SignatureInfo {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = Integer.MAX_VALUE)
    private SignatureType type;

    @Column(name = "certificate")
    private UUID certificate;

    @Column(name = "signature_file")
    private byte[] signatureFile;


    @Column(name = "signed_at")
    private ZonedDateTime signedAt;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

}