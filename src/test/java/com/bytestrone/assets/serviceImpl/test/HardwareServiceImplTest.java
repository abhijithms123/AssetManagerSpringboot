package com.bytestrone.assets.serviceImpl.test;



import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.modelmapper.ModelMapper;
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
import com.bytestrone.assets.repository.HardwareRepository;
import com.bytestrone.assets.serviceImpl.HardwarServiceImpl;
import com.bytestrone.assets.viewobjects.HardwareRequestModel;
import com.bytestrone.assets.viewobjects.UpdateHardwareRequestModel;



@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class HardwareServiceImplTest {
	@Mock
	private HardwareRepository hardwareRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private HardwarServiceImpl hardwareServiceImpl;

	@Test
	 void testSaveHardwareSuccess() {
		
        //Arrange
		
		HardwareRequestModel hardwareRequestModel = new HardwareRequestModel();
		hardwareRequestModel.setAssetNumber("ASSET001");

		HardwareAsset hardwareAsset = new HardwareAsset();
		hardwareAsset.setAssetNumber("ASSET001");
		hardwareAsset.setAssetType("Hardware");
		hardwareAsset.setStatus("Unallocated");

		when(hardwareRepository.existsByAssetNumber(hardwareRequestModel.getAssetNumber())).thenReturn(false);
		when(modelMapper.map(hardwareRequestModel, HardwareAsset.class)).thenReturn(hardwareAsset);

		// Act
		
		boolean result = hardwareServiceImpl.saveHardware(hardwareRequestModel);

		// Assert
		
		verify(hardwareRepository, times(1)).existsByAssetNumber(hardwareRequestModel.getAssetNumber());
		assertTrue(result);

	}
	
	
	@Test
	 void testUpdateHardwareSucces() {
		//Arrange
		int id = 115;
		UpdateHardwareRequestModel assetBody = new UpdateHardwareRequestModel();
		HardwareAsset hardwareAsset = new HardwareAsset();
		
		when(hardwareRepository.findById(id)).thenReturn(hardwareAsset);
		
		//Act
		
		boolean result = hardwareServiceImpl.updateHardwareAsset(id, assetBody);
		
		//Assert
		assertTrue(result);
	}
	
	@Test
	 void testGeAlltHardwareAssetsSuccess() {
		//Arrange
		
		int page = 0;
		int size = 10;
		Page<HardwareAsset> hardwares = Mockito.mock(Page.class);
		when(hardwareRepository.findByStatusOrStatus(eq("Unallocated"),eq("Allocated"),Mockito.any(Pageable.class))).thenReturn(hardwares);
		
		//Act
		Page<HardwareAsset> pages = hardwareServiceImpl.getAllHardwareAssets(page, size);
		
		//Assert
		Assertions.assertThat(pages).isNotNull();
		
		
	}
	
	@Test
	 void testSearchHardwareItemsSuccess() {
		//Arrange
		int page = 0;
		int size = 10;
		String searchTerm = "Lenovo";
		Page<HardwareAsset> hardwares = Mockito.mock(Page.class);
		when(hardwareRepository.searchHardware(eq(searchTerm),Mockito.any(Pageable.class))).thenReturn(hardwares);
		
		//Act
		Page<HardwareAsset> pages = hardwareServiceImpl.searchHardwareItems(page, size, searchTerm);
		
		//Assert
		
		verify(hardwareRepository).searchHardware(eq(searchTerm),Mockito.any(Pageable.class));
		Assertions.assertThat(pages).isNotNull();
	}
	
	
	
	@Test
	 void testGetHardwareAssetSuccess() {
		//Arrange
		
		int page = 0;
		int size = 10;
		int searchId = 115;
		Page<HardwareAsset> hardwares = Mockito.mock(Page.class);
		when(hardwareRepository.findById(Mockito.anyInt(),Mockito.any(Pageable.class))).thenReturn(hardwares);
		
		//Act
		Page<HardwareAsset> pages = hardwareServiceImpl.getHardwareAsset(page, size, searchId);
		
		//Assert
		Assertions.assertThat(pages).isNotNull();
		
		
		
	}
	
	@Test
	 void testChangeStatusSuccess() {
		//Arrange
        HardwareAsset asset1 = new HardwareAsset();
        asset1.setId(1);
        asset1.setStatus("active");

        HardwareAsset asset2 = new HardwareAsset();
        asset2.setId(2);
        asset2.setStatus("active");

        List<Integer> ids = Arrays.asList(1, 2);

     
        when(hardwareRepository.findById(1)).thenReturn(asset1);
        when(hardwareRepository.findById(2)).thenReturn(asset2);

        // Act
        boolean result = hardwareServiceImpl.changeStatus(ids);
        
        //Assert
        
        // Verify that the repository was called with the correct parameters
        verify(hardwareRepository, times(1)).findById(1);
        verify(hardwareRepository, times(1)).findById(2);
        verify(hardwareRepository, times(1)).save(asset1);
        verify(hardwareRepository, times(1)).save(asset2);

        // Verify that the method returned true
        assertTrue(result);

        // Verify that the assets were updated correctly
        assertEquals("inactive", asset1.getStatus());
        assertEquals("inactive", asset2.getStatus());
    
	}
	
	@Test
	void testGetHardwareAssetPaginationSuccess() {
       //Arrange
		int pageNumber = 0;
		int pageSize = 10;
		Page<HardwareAsset> hardwares = Mockito.mock(Page.class);
		
		HardwareAsset hardware1 = new HardwareAsset();
	    hardware1.setStatus("Unallocated");
	    
	    Pageable pageable= PageRequest.of(pageNumber,pageSize);
	    
	    
	    when(hardwareRepository.findByStatus(hardware1.getStatus(),pageable)).thenReturn(hardwares);
	   
	    //Act
	    Page<HardwareAsset> pages = hardwareServiceImpl.getHardwareAssetPagination(pageNumber,pageSize);
	   
	    //Assert
	    verify(hardwareRepository, times(1)).findByStatus(hardware1.getStatus(),pageable);
	    Assertions.assertThat(pages).isNotNull();
	}
	
	@Test
	void testUpdateHardwareAssetSuccess() {
		
		//Arrange
		String status = "Unallocated";
		 HardwareAsset asset1 = new HardwareAsset();
	        asset1.setId(1);
	        asset1.setStatus("Unallocated");
	        
	        when(hardwareRepository.findById(1)).thenReturn(asset1);
		//Act
	        hardwareServiceImpl.updateHardwareAsset(asset1.getId(), status);
	        
	     //Assert
	      verify(hardwareRepository,times(1)).findById(asset1.getId());  
	      verify(hardwareRepository,times(1)).save(asset1);
		    
	}

	@Test
	 void testGetAssetBySearchTerm() {
		String searchTerm = "Test";
		HardwareAsset asset1 = new HardwareAsset();
		HardwareAsset asset2 = new HardwareAsset();
		HardwareAsset asset3 = new HardwareAsset();
		asset1.setHardwareType(searchTerm);
	
		
		List<HardwareAsset> assets = Arrays.asList(asset1,asset2,asset3);
     
		List<HardwareAsset> actual = hardwareServiceImpl.getAssetBySearchTerm(searchTerm);
        
	
		verify(hardwareRepository).findHardwareBySearchTerm(searchTerm);

	}

}
