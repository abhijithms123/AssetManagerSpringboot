package com.bytestrone.assets.serviceImpl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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


import com.bytestrone.assets.model.SoftwareRequest;
import com.bytestrone.assets.repository.SoftwareRequestRepo;
import com.bytestrone.assets.serviceImpl.SoftwareRequestServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SoftwareRequestServiceImplTest {
	
	@Mock
	private SoftwareRequestRepo requestRepo;
	
	@InjectMocks
	private SoftwareRequestServiceImpl softwareService;
  

	@Test
	void testSaveSoftwareRequestSuccess() {
		// Arrange
		SoftwareRequest request = new SoftwareRequest();
		request.setAssetId(115);
		request.setRequestId("12321");
		request.setSoftwareCategory("application software");

		// Act
		softwareService.saveSoftwareRequest(request);

		// Assert
		verify(requestRepo, times(1)).save(request);
	}

	@Test
	void testGetSoftwareRequestsSuccess() {
		// Arrange
		String requestStatus = "open";
		String requestedBy = "akhi";
		Integer pageNumber = 0;
		Integer pageSize = 10;
		SoftwareRequest request1 = new SoftwareRequest();

		request1.setRequestStatus(requestStatus);
		request1.setRequestedBy(requestedBy);



		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<SoftwareRequest> hadrwares = Mockito.mock(Page.class);

		when(requestRepo.findByRequestStatusAndRequestedBy(request1.getRequestStatus(), request1.getRequestedBy(),
				pageable)).thenReturn(hadrwares);

		// Act
		Page<SoftwareRequest> pages = softwareService.getSoftwareRequests(requestStatus, requestedBy,
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

		SoftwareRequest request1 = new SoftwareRequest();
		request1.setRequestId(requestId);
		request1.setRequestStatus(status);

		when(requestRepo.findByRequestId(requestId)).thenReturn(request1);

		// Act
		softwareService.updateRequestStatus(status, requestId, reason);

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

		List<Object[]> actualResults = softwareService.getHardwareCountByStatus(userName);

		verify(requestRepo,times(1)).countHardwareByStatus(userName);
		assertEquals(items, actualResults);
	}

	@Test
	void testGetRequestByStatusSuccess() {
		//Arrange
		String requestStatus = "open";
		Integer pageNumber = 0;
		Integer pageSize = 10;
		SoftwareRequest request1 = new SoftwareRequest();

		request1.setRequestStatus(requestStatus);
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<SoftwareRequest> requests = Mockito.mock(Page.class);
		
		when(requestRepo.findByRequestStatus(requestStatus, pageable)).thenReturn(requests);
		
		//Act
		Page<SoftwareRequest> pages = softwareService.getRequestByStatus(requestStatus, pageNumber, pageSize);
		 
		//Assert
		verify(requestRepo,times(1)).findByRequestStatus(requestStatus, pageable);
		assertThat(pages).isNotNull();
		
		
	}

	
}
