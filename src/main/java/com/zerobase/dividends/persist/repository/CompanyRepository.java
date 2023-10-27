package com.zerobase.dividends.persist.repository;

import com.zerobase.dividends.persist.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker);
    Optional<CompanyEntity> findByName(String name);
    Optional<CompanyEntity> findByTicker(String ticker);
    // LIKE 연산자 사용
    Page<CompanyEntity> findByNameStartingWithIgnoreCase(String s, Pageable pageable);
}
