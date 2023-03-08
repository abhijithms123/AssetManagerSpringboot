package com.bytestrone.assets.serviceImpl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.assertj.core.api.Assertions;
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
import com.bytestrone.assets.repository.HardwareDashboardRepository;
import com.bytestrone.assets.serviceImpl.HardwareDashboardServiceImpl;
import com.bytestrone.assets.viewobjectDashboard.CountSummaryVO;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class HardwareDashboardServiceImplTest {
  
	@Mock
	private HardwareDashboardRepository hardwareDashboardRepository;
	
	@InjectMocks
	private HardwareDashboardServiceImpl hardwareDashboardServiceImpl;
	
	@Test
	void testFetchAssetByHardwareSuccess() {
		String AssetType = "Hardware";
		String status = "Unallocated";
		int page = 0;
		int size = 10;
		Pageable paging = PageRequest.of(page, size);
        //Arrange
		Page<HardwareAsset> hardwares = Mockito.mock(Page.class);
		
		when(hardwareDashboardRepository.findByHardwareTypeAndStatus(eq("Hardware"),eq("Unallocated"),Mockito.any(Pageable.class))).thenReturn(hardwares);
		
		//Act
		Page<HardwareAsset> pages = hardwareDashboardServiceImpl.fetchAssetByHardware(AssetType, status, page, size);
		
		verify(hardwareDashboardRepository,times(1)).findByHardwareTypeAndStatus(AssetType, status,paging );
		Assertions.assertThat(pages).isNotNull();
		
	}
	
	@Test
	void testGetCountsByHardwareTypeSuccess() {
		//Arrange
		Object[] obj1 = new Object[] {"Laptop",BigInteger.valueOf(3), BigInteger.valueOf(1)};
		Object[] obj2 = new Object[] {"mouse",BigInteger.valueOf(5), BigInteger.valueOf(2) };
		List<Object[]> items = new ArrayList<Object[]>();
		items.add(obj1);
		items.add(obj2);
		when(hardwareDashboardRepository.getCountDistinctHardwareByAssetType()).thenReturn(items);
		
		List<CountSummaryVO> response = new ArrayList<CountSummaryVO>();
		response.add(new CountSummaryVO("Laptop",3,1));
		response.add(new CountSummaryVO("mouse",5,2));
		
		//Act
		List<CountSummaryVO> newResponse = hardwareDashboardServiceImpl.getCountsByHardwareType();
		
		//Assert
		
		assertEquals(newResponse.size(),response.size());
		
		Iterator<CountSummaryVO> expectedIter = response.iterator();
		Iterator<CountSummaryVO> actualIter = newResponse.iterator();
		while (expectedIter.hasNext() && actualIter.hasNext()) {
			CountSummaryVO expectedVO = expectedIter.next();
			CountSummaryVO actualVO = actualIter.next();
			assertEquals(expectedVO.getType(), actualVO.getType());
			assertEquals(expectedVO.getAssignedCount(), actualVO.getAssignedCount());
			assertEquals(expectedVO.getUnassignedCount(), actualVO.getUnassignedCount());
		
	}
}
	
     



}
