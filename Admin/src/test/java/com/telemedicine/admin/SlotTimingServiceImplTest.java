package com.telemedicine.admin;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.telemedicine.admin.entity.SlotTiming;
import com.telemedicine.admin.exception.ResourceNotFoundException;
import com.telemedicine.admin.repository.SlotTimingRepository;
import com.telemedicine.admin.serviceimpl.SlotTimingServiceImpl;

public class SlotTimingServiceImplTest {

	@Mock
	private SlotTimingRepository slotTimingRepository;

	@InjectMocks
	private SlotTimingServiceImpl slotTimingService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/*@Test
	public void testGetAllSlotTimings_PositiveCase() {
		
		List<SlotTiming> mockSlotTimings = new ArrayList<>();
		mockSlotTimings.add(new SlotTiming());

		when(slotTimingRepository.findAll()).thenReturn(mockSlotTimings);

		
		List<SlotTiming> result = slotTimingService.getAllSlotTimings();

		
		assertEquals(mockSlotTimings, result);
	}

	@Test
    public void testGetAllSlotTimings_NegativeCase() {
        
        when(slotTimingRepository.findAll()).thenReturn(new ArrayList<>());

        
        assertThrows(ResourceNotFoundException.class, () -> slotTimingService.getAllSlotTimings());
    }

	@Test
	public void testGetSlotTimingById_PositiveCase() {
		
		int id = 1;
		SlotTiming mockSlotTiming = new SlotTiming();

		when(slotTimingRepository.findById(id)).thenReturn(Optional.of(mockSlotTiming));

		
		SlotTiming result = slotTimingService.getSlotTimingById(id);

		
		assertEquals(mockSlotTiming, result);
	}

	@Test
	public void testGetSlotTimingById_NegativeCase() {
		
		int id = 1;

		when(slotTimingRepository.findById(id)).thenReturn(Optional.empty());

		
		assertThrows(ResourceNotFoundException.class, () -> slotTimingService.getSlotTimingById(id));
	}

	@Test
	public void testSaveSlotTiming_PositiveCase() {
		
		SlotTiming mockSlotTiming = new SlotTiming();
		when(slotTimingRepository.save(mockSlotTiming)).thenReturn(mockSlotTiming);

		
		SlotTiming result = slotTimingService.saveSlotTiming(mockSlotTiming);

		
		assertEquals(mockSlotTiming, result);
	}

	@Test
	public void testUpdateSlotTiming_PositiveCase() {
		
		int id = 1;
		SlotTiming existingSlotTiming = new SlotTiming();
		SlotTiming updatedSlotTiming = new SlotTiming();

		when(slotTimingRepository.existsById(id)).thenReturn(true);
		when(slotTimingRepository.save(updatedSlotTiming)).thenReturn(updatedSlotTiming);

		
		SlotTiming result = slotTimingService.updateSlotTiming(id, updatedSlotTiming);

		
		assertEquals(updatedSlotTiming, result);
	}

	@Test
	public void testDeleteSlotTiming_PositiveCase() {
		
		int id = 1;

		
		slotTimingService.deleteSlotTiming(id);

		
		verify(slotTimingRepository, times(1)).deleteById(id);
	}

	@Test
	public void testUpdateSlotTiming_NegativeCase() {
		
		int id = 1;
		SlotTiming updatedSlotTiming = new SlotTiming();

		when(slotTimingRepository.existsById(id)).thenReturn(false);

		
		assertThrows(ResourceNotFoundException.class, () -> slotTimingService.updateSlotTiming(id, updatedSlotTiming));
	}*/

	
	
}
