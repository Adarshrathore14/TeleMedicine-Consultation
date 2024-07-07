package com.telemedicine.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telemedicine.admin.entity.SlotTiming;

public interface SlotTimingRepository extends JpaRepository<SlotTiming, Integer> {
}
