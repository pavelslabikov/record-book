package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.CertificateInfo;

import java.util.UUID;

public interface CertificateInfoRepository extends JpaRepository<CertificateInfo, UUID> {
}