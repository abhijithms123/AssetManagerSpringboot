package com.bytestrone.assets.serviceImpl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.model.HardwareRequest;
import com.bytestrone.assets.repository.HardwareRequestRepo;
import com.bytestrone.assets.serviceImpl.HardwareRequestServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class HardwareRequestServiceImplTest {

	@Mock
	private HardwareRequestRepo requestRepo;

	@InjectMocks
	private HardwareRequestServiceImpl hardwareService;

	@Test
	void testSaveHardwareRequestSuccess() {
		// Arrange
		HardwareRequest request = new HardwareRequest();
		request.setAssetId(115);
		request.setRequestId("12321");
		request.setHardwareType("Laptop");

		// Act
		hardwareService.saveHardwareRequest(request);

		// Assert
		verify(requestRepo, times(1)).save(request);
	}

	@Test
	void testFindByRequestStatusAndRequestedBySuccess() {
		// Arrange
		String requestStatus = "open";
		String requestedBy = "akhi";
		Integer pageNumber = 0;
		Integer pageSize = 10;
		HardwareRequest request1 = new HardwareRequest();
		HardwareRequest request2 = new HardwareRequest();
		HardwareRequest request3 = new HardwareRequest();

		request1.setRequestStatus(requestStatus);
		request1.setRequestedBy(requestedBy);

//        request2.setRequestStatus("open");
//        request2.setRequestedBy("akhi");
//        
//        request3.setRequestStatus("open");
//        request3.setRequestedBy("akhi");

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<HardwareRequest> hadrwares = Mockito.mock(Page.class);

		when(requestRepo.findByRequestStatusAndRequestedBy(request1.getRequestStatus(), request1.getRequestedBy(),
				pageable)).thenReturn(hadrwares);

		// Act
		Page<HardwareRequest> pages = hardwareService.findByRequestStatusAndRequestedBy(requestStatus, requestedBy,
				pageNumber, pageSize);

		// Assert
		verify(requestRepo, times(1)).findByRequestStatusAndRequestedBy(request1.getRequestStatus(),
				request1.getRequestedBy(), pageable);
		assertThat(pages).isNotNull();
	}

	@Test
	void testUpdateRequestStatus() {
		// Arrange
		String status = "testStatus";
		String requestId = "testId";
		String reason = "testReason";

		HardwareRequest request1 = new HardwareRequest();
		request1.setRequestId(requestId);
		request1.setRequestStatus(status);

		when(requestRepo.findByRequestId(requestId)).thenReturn(null);

		// Act
		hardwareService.updateRequestStatus(status, requestId, reason);

		// Assert
		verify(requestRepo, times(1)).findByRequestId(requestId);
		verify(requestRepo, times(1)).save(request1);

	}
	

	@Test
	void testGetHardwareCountByStatusSuccess() {
		String userName = "Akhi";
		Object[] obj1 = new Object[] {userName};
		Object[] obj2 = new Object[] {userName};
		List<Object[]> items = new ArrayList<Object[]>();
		items.add(obj1);
		items.add(obj2);

		when(requestRepo.countHardwareByStatus(userName)).thenReturn(items);

		List<Object[]> actualResults = hardwareService.getHardwareCountByStatus(userName);

		verify(requestRepo,times(1)).countHardwareByStatus(userName);
		assertEquals(items, actualResults);
	}

	@Test
	void testGetRequestByStatusSuccess() {
		//Arrange
		String requestStatus = "open";
		Integer pageNumber = 0;
		Integer pageSize = 10;
		HardwareRequest request1 = new HardwareRequest();

		request1.setRequestStatus(requestStatus);
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<HardwareRequest> requests = Mockito.mock(Page.class);
		
		when(requestRepo.findByRequestStatus(requestStatus, pageable)).thenReturn(requests);
		
		//Act
		Page<HardwareRequest> pages = hardwareService.getRequestByStatus(requestStatus, pageNumber, pageSize);
		 
		//Assert
		verify(requestRepo,times(1)).findByRequestStatus(requestStatus, pageable);
		assertThat(pages).isNotNull();
		
		
	}

}
