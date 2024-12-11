package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.SignatureInfo;

import java.util.UUID;

public interface SignatureInfoRepository extends JpaRepository<SignatureInfo, UUID> {
}