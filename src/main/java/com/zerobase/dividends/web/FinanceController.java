package com.zerobase.dividends.web;

import com.zerobase.dividends.model.ScrapedResult;
import com.zerobase.dividends.service.FinanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    @GetMapping("/dividend/{companyName}")
    public ResponseEntity<?> searchFinance(
            @PathVariable String companyName) {
        ScrapedResult scrapResult = this.financeService.getDividendByCompanyName(companyName);
        return ResponseEntity.ok(scrapResult);
    }
}
