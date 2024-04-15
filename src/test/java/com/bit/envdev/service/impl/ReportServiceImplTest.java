package com.bit.envdev.service.impl;

import com.bit.envdev.entity.Report;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class ReportServiceImplTest {

    @Test
    void regReport() {
        String refType = "";
        Long refId = 1L;

        Report report = Report.builder()
                .text("")
                .build();
    }

    @Test
    void updateState() {
    }
}