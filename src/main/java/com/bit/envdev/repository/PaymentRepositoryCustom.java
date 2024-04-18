package com.bit.envdev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PaymentRepositoryCustom {
    Page<Map<String, Object>> getPurchaseList(Pageable pageable, long memberId);
}
