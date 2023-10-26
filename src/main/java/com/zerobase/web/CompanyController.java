package com.zerobase.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company") // 경로의 공통된 부분
public class CompanyController {

    @GetMapping("/company/autocomplete")
    public ResponseEntity<?> autocomplete(
            @RequestParam String keyword) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> searchCompany() {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addCompany() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<?> deleeteCompany() {
        return null;
    }

}
