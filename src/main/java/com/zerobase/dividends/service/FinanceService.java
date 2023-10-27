package com.zerobase.dividends.service;

import com.zerobase.dividends.exception.impl.NoCompanyException;
import com.zerobase.dividends.model.Company;
import com.zerobase.dividends.model.Dividend;
import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.model.constants.CacheKey;
import com.zerobase.dividends.persist.entity.CompanyEntity;
import com.zerobase.dividends.persist.entity.DividendEntity;
import com.zerobase.dividends.persist.repository.CompanyRepository;
import com.zerobase.dividends.persist.repository.DividendRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    /**
     * exceptionhandler 작동함
     * @param companyName
     * @return
     */

    // 요청이 자주 들어오는가?
    // 자주 변경되는 데이터인가?

    // 여기서 key, value는 redis의 key, value와 의미가 다름
    // #을 붙여야 함
    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName) {

        log.info("search company -> " + companyName);

        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new NoCompanyException());

        // 2. 조회된 회사 ID로 배당금 정보 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> new Dividend(e.getDate(), e.getDividend()))
                .collect(Collectors.toList());

        return new ScrapedResult(new Company(
                company.getTicker(), company.getName()), dividends);

    }
}
