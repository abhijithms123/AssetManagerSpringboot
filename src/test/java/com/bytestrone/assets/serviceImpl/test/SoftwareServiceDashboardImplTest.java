package com.bytestrone.assets.serviceImpl.test;

import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.bytestrone.assets.converter.AssetConverter;
import com.bytestrone.assets.model.Asset;
import com.bytestrone.assets.model.SoftwareAsset;
import com.bytestrone.assets.repository.SoftwareDashboardRepository;
import com.bytestrone.assets.serviceImpl.SoftwareServiceDashboardImpl;
import com.bytestrone.assets.viewobjectDashboard.AboutToExpireVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseGraphVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseListVO;
import com.bytestrone.assets.viewobjectDashboard.ResponseWidgetVO;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SoftwareServiceDashboardImplTest {
	
	@Mock
	private SoftwareDashboardRepository softwareRepository;
	
	@Mock
	private AssetConverter assetConverter;
	
	@InjectMocks
	private SoftwareServiceDashboardImpl softwareService;
    
	@Test
	void testGetAssetSuccess() {
		//Arrange
		SoftwareAsset asset = new SoftwareAsset();
		asset.setId(1);
		asset.setAssetNumber("345");
		asset.setAssetType("Software");;
		
		ResponseListVO responseListVO = new ResponseListVO();
		responseListVO.setAssetNumber("345");
		responseListVO.setAssetType("Software");

		
		when(softwareRepository.findById(1)).thenReturn(asset);
		when(assetConverter.convertAssetModelToResponseListVo(asset)).thenReturn(responseListVO);
		
		
		//Act
		
		  List<ResponseListVO> result = softwareService.getAsset(1);
		  
	    //Assert
		  assertEquals(1, result.size());
	      assertEquals(responseListVO, result.get(0));
		
	}
	
//	@Test
//	void testGetTotalSoftwarePurchasedSuccess() {
//		 // Configure the mock software repository to return a total of 10 purchased software
//        when(softwareRepository.selectTotalPurchased()).thenReturn(10);
//
//        // Call the method being tested
//        int result = softwareService.getTotalSoftwarePurchased();
//
//        // Verify that the method returns the expected value
//        Assert.assertEquals(10, result);
//	}
	
	@Test
	void testGetGraphDataSuccess() {
	    //Arrange
        when(softwareRepository.selectTotalPurchased()).thenReturn(10);
        when(softwareRepository.selectTotalInstalled()).thenReturn(20);
        when(softwareRepository.selectTotalAvailable()).thenReturn(30);

        //Act
        ResponseGraphVO result = softwareService.getGraphData();

        //Assert
        Assertions.assertEquals(10, result.getTotalPurchased());
        Assertions.assertEquals(20, result.getTotalInstalled());
        Assertions.assertEquals(30, result.getTotalAvailable());
	}
	@Test
	void testGetWidgetDataSuccess() {
		  // Arrange
        List<Object[]> mockResultList = new ArrayList<>();
        Object[] mockData1 = new Object[]{"Software 1", BigInteger.valueOf(10), BigInteger.valueOf(20), BigInteger.valueOf(30)};
        Object[] mockData2 = new Object[]{"Software 2", BigInteger.valueOf(40), BigInteger.valueOf(50), BigInteger.valueOf(60)};
        mockResultList.add(mockData1);
        mockResultList.add(mockData2);
        when(softwareRepository.findBySoftwareCategory()).thenReturn(mockResultList);

        // Call the method being tested
        List<ResponseWidgetVO> result = softwareService.getWidgetData();

        // Verify that the expected ResponseWidgetVO objects were created and returned
        assertEquals(2, result.size());

        ResponseWidgetVO expected1 = new ResponseWidgetVO("Software 1", BigInteger.valueOf(10), BigInteger.valueOf(20), BigInteger.valueOf(30));
        assertEquals(expected1, result.get(0));

        ResponseWidgetVO expected2 = new ResponseWidgetVO("Software 2", BigInteger.valueOf(40), BigInteger.valueOf(50), BigInteger.valueOf(60));
        assertEquals(expected2, result.get(1));
	}
	
	@Test
	void testGetBySoftwareCategorySuccess() {
		// Arrange
        List<Asset> mockAssetList = new ArrayList<>();
        SoftwareAsset softwareAsset1 = new SoftwareAsset();
        softwareAsset1.setSoftwareCategory("Category 1");
        softwareAsset1.setStatus("Active");
        mockAssetList.add(softwareAsset1);
        
        SoftwareAsset softwareAsset2 = new SoftwareAsset();
        softwareAsset2.setSoftwareCategory("Category 2");
        softwareAsset2.setStatus("Inactive");
        mockAssetList.add(softwareAsset2);
        when(softwareRepository.findSoftwareByStatus("Active")).thenReturn(mockAssetList);

    
        ResponseListVO mockResponseListVO1 = new ResponseListVO();
        ResponseListVO mockResponseListVO2 = new ResponseListVO();
        when(assetConverter.convertAssetModelToResponseListVo(softwareAsset1)).thenReturn(mockResponseListVO1);
        when(assetConverter.convertAssetModelToResponseListVo(softwareAsset2)).thenReturn(mockResponseListVO2);

        // Act
        List<ResponseListVO> result = softwareService.getBySoftwareCategory("all", "Active");

        // Assert
        assertEquals(2, result.size());
        assertEquals(mockResponseListVO1, result.get(0));
        assertEquals(mockResponseListVO2, result.get(1));
	}
	
	@Test
	void testGetAboutToExpireAssetsSuccess() {
		  //Arrange
        List<Object[]> mockObjectList = new ArrayList<>();
        Object[] mockObject1 = new Object[]{1, "Software 1", new Date(), 10};
        mockObjectList.add(mockObject1);
        Object[] mockObject2 = new Object[]{2, "Software 2", new Date(), 20};
        mockObjectList.add(mockObject2);
        when(softwareRepository.findAboutToExpireAssets()).thenReturn(mockObjectList);

        // Act
        List<AboutToExpireVO> result = softwareService.getAboutToExpireAssets();

        // Assert
        assertEquals(2, result.size());
        assertEquals(mockObject1[0], result.get(0).getId());
        assertEquals(mockObject1[1], result.get(0).getSoftwareName());
        assertEquals(mockObject1[2], result.get(0).getLicenseExpiryDate());
        assertEquals(mockObject1[3], result.get(0).getExpiresIn());
 
        assertEquals(mockObject2[0], result.get(1).getId());
        assertEquals(mockObject2[1], result.get(1).getSoftwareName());
        assertEquals(mockObject2[2], result.get(1).getLicenseExpiryDate());
        assertEquals(mockObject2[3], result.get(1).getExpiresIn());
	}
	
	
}
