package com.telemedicine.admin.controller;

import com.telemedicine.admin.apidefinitions.SlotTimingApiDefinition;
import com.telemedicine.admin.entity.SlotTiming;
import com.telemedicine.admin.service.SlotTimingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/slottimings")
public class SlotTimingController implements SlotTimingApiDefinition{

    @Autowired
    private SlotTimingService slotTimingService;

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<SlotTiming>> getAllSlotTimings() {
    	List<SlotTiming> slotTiming = slotTimingService.getAllSlotTimings();
    	if (!slotTiming.isEmpty()) {
            return new ResponseEntity<>(slotTiming, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<SlotTiming> getSlotTimingById(@PathVariable int id) {
        SlotTiming slotTiming = slotTimingService.getSlotTimingById(id);
        if (slotTiming != null) {
            return new ResponseEntity<>(slotTiming, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<SlotTiming> saveSlotTiming(@RequestBody SlotTiming slotTiming) {
        SlotTiming savedSlotTiming = slotTimingService.saveSlotTiming(slotTiming);
        return new ResponseEntity<>(savedSlotTiming, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<SlotTiming> updateSlotTiming(@PathVariable int id, @RequestBody SlotTiming updatedSlotTiming) {
        SlotTiming slotTiming = slotTimingService.updateSlotTiming(id, updatedSlotTiming);
        if (slotTiming != null) {
            return new ResponseEntity<>(slotTiming, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> deleteSlotTiming(@PathVariable int id) {
        slotTimingService.deleteSlotTiming(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
