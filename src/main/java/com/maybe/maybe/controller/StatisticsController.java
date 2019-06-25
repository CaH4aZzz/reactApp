package com.maybe.maybe.controller;

import com.maybe.maybe.dto.ComponentReportDTO;
import com.maybe.maybe.dto.SummaryDTO;
import com.maybe.maybe.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/summary")
    public List<SummaryDTO> getSummaryReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        return statisticsService.getSummaryReport(dateAt, dateTo);
    }

    @GetMapping("/component-report")
    public List<ComponentReportDTO> getComponentReportDTO(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {

        return statisticsService.getComponentReport(dateFrom, dateTo);
    }
}
