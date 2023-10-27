package com.zerobase.dividends.scheduler;

import com.zerobase.dividends.model.Company;
import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.model.constants.CacheKey;
import com.zerobase.dividends.persist.entity.CompanyEntity;
import com.zerobase.dividends.persist.entity.DividendEntity;
import com.zerobase.dividends.persist.repository.CompanyRepository;
import com.zerobase.dividends.persist.repository.DividendRepository;
import com.zerobase.dividends.scraper.Scraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Scheduled
 * - 기본적으로 scheduler 스레드는 1개만 형성
 *
 * test1에서 10초 정지하기 때문에
 * test2의 작업이 10초 안에 수행되는 것을 기대하였으나
 * 스레드가 1개이기 때문에 test1의 작업이 완료되어야
 * test2의 작업이 수행된다
 */
@Slf4j
@Component
@AllArgsConstructor
@EnableCaching
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scraper yahooFinanceScraper;

    // 일정 주기마다 수행
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true) // redis에서 finance로 엮인 것은 전부 지운다
    public void yahooFinanceScheduling() {
        log.info("scraping scheduler is started");

        // 저장된 회사 목록을 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();

        // 회사마다 배당금 정보를 새로 스크래핑
        for (var company : companies) {
            log.info("scraping scheduler is started -> " + company.getName());

            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(
                            new Company(company.getTicker(), company.getName()));

            // 스크래핑한 배당금 정보 중 데이터베이스에 없는 정보 저장
            scrapedResult.getDividends().stream()
                    // 디비든 모델을 디비든 엔티티로 매핑
                    .map(e -> new DividendEntity(company.getId(), e))
                    // 엘리먼트를 하나씩 디비든 레파지토리에 삽입(존재하지 않는 경우)
                    .forEach(e -> {
                        boolean exists = this.dividendRepository.
                                existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());

                        if (!exists) {
                            this.dividendRepository.save(e);
                        }
                    });
            // 연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

    }

//    @Scheduled(fixedDelay = 1000)
//    public void test1() throws InterruptedException {
//        Thread.sleep(10000); // 10초간 일시 정지
//
//
//        System.out.println(Thread.currentThread().getName() + " -> test 1 : " + LocalDateTime.now());
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void test2() throws InterruptedException {
//        System.out.println(Thread.currentThread().getName() + " -> test 2 : " + LocalDateTime.now());
//    }
}
