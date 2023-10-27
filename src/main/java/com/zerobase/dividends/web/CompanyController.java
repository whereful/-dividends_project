package com.zerobase.dividends.web;

import com.zerobase.dividends.model.Company;
import com.zerobase.dividends.model.constants.CacheKey;
import com.zerobase.dividends.persist.entity.CompanyEntity;
import com.zerobase.dividends.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ---------------------------------------------------------------------------------------------
 * @PreAuthorize is not working when authorizeHttpRequests method is used in SecurityConfiguration Class
 *
 * To be working, use authorizeRequests method instead of authorizeHttpRequests
 *
 * https://github.com/spring-projects/spring-security/issues/12861
 * ---------------------------------------------------------------------------------------------
 */
@RestController
@RequestMapping("/company") // 경로의 공통된 부분
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    private final CacheManager redisCacheManager;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(
            @RequestParam String keyword) {
        // List<String> result = this.companyService.autocomplete(keyword);
        List<String> result = this.companyService.getCompanyNamesByKeyword(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @PreAuthorize("hasRole('READ')") // 읽기 권한이 있는 유저만 해당 api 호출
    public ResponseEntity<?> searchCompany(final Pageable pageable) {
        Page<CompanyEntity> companyEntityList = companyService.getAllCompany(pageable);
        return ResponseEntity.ok(companyEntityList);
    }

    /**
     * 회사 및 배당금 정보 추가
     * @param request
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('WRITE')") // 쓰기 권한이 있는 유저만 해당 api 호출
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty(ticker)) {
            throw new RuntimeException("ticker is empty");
        }
        Company company = companyService.save(ticker);
        this.companyService.addAutocompleteKeyword(company.getName());

        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{ticker}")
    @PreAuthorize("hasRole('WRITE')") // 쓰기 권한이 있는 유저만 해당 api 호출
    public ResponseEntity<?> deleteCompany(@PathVariable String ticker) {
        String companyName = this.companyService.deleteCompany(ticker);
        this.clearFinanceCache(companyName);
        return ResponseEntity.ok(companyName);
    }

    public void clearFinanceCache(String companyName) {
        this.redisCacheManager.getCache(CacheKey.KEY_FINANCE).evict(companyName);
    }

}
