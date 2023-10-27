package com.zerobase.dividends.service;

import com.zerobase.dividends.model.Company;
import com.zerobase.dividends.model.Dividend;
import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.persist.entity.CompanyEntity;
import com.zerobase.dividends.persist.entity.DividendEntity;
import com.zerobase.dividends.persist.repository.CompanyRepository;
import com.zerobase.dividends.persist.repository.DividendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public ScrapedResult getDividendByCompanyName(String companyName) {

        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));

        // 2. 조회된 회사 ID로 배당금 정보 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> Dividend.builder().date(e.getDate())
                        .dividend(e.getDividend())
                        .build())
                .collect(Collectors.toList());

        return new ScrapedResult(Company
                .builder()
                .ticker(company.getTicker())
                .name(company.getName())
                .build(), dividends);

    }
}
