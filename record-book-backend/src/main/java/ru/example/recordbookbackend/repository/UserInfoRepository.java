package ru.example.recordbookbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.recordbookbackend.entity.UserInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    List<UserInfo> findAllByDeleted(Boolean deleted);

    Optional<UserInfo> findByIdAndDeleted(UUID id, Boolean deleted);
}