package com.bytestrone.assets.serviceImpl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bytestrone.assets.model.SoftwareAsset;
import com.bytestrone.assets.repository.SoftwareRepository;
import com.bytestrone.assets.serviceImpl.SoftwareServiceImpl;
import com.bytestrone.assets.viewobjects.SoftwareRequestModel;
import com.bytestrone.assets.viewobjects.UpdateSoftwareRequestModel;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SoftwareServiceImplTest {
	@Mock
	private SoftwareRepository softwareRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private SoftwareServiceImpl softwareServiceImpl;

	@Test
	void testSaveSoftwareSuccess() {

		// Arrange

		SoftwareRequestModel softwareRequestModel = new SoftwareRequestModel();
		softwareRequestModel.setAssetNumber("ASSET001");

		SoftwareAsset softwareAsset = new SoftwareAsset();
		softwareAsset.setAssetNumber("ASSET001");
		softwareAsset.setAssetType("Hardware");
		softwareAsset.setStatus("Unallocated");

		when(softwareRepository.existsByAssetNumber(softwareRequestModel.getAssetNumber())).thenReturn(false);
		when(modelMapper.map(softwareRequestModel, SoftwareAsset.class)).thenReturn(softwareAsset);

		// Act

		boolean result = softwareServiceImpl.saveSoftware(softwareRequestModel);

		// Assert

		verify(softwareRepository, times(1)).existsByAssetNumber(softwareRequestModel.getAssetNumber());
		assertTrue(result);

	}

	@Test
	void testGetSoftwareAssetSuccess() {
		// Arrange

		int page = 0;
		int size = 10;
		int softwareId = 115;
		Page<SoftwareAsset> softwares = Mockito.mock(Page.class);
		when(softwareRepository.findById(Mockito.anyInt(), Mockito.any(Pageable.class))).thenReturn(softwares);

		// Act
		Page<SoftwareAsset> pages = softwareServiceImpl.getSoftwareAsset(page, size, softwareId);

		// Assert
		assertThat(pages).isNotNull();

	}

	@Test
	void testUpdateAssetSucces() {
		// Arrange
		int id = 115;
		UpdateSoftwareRequestModel assetBody = new UpdateSoftwareRequestModel();
		SoftwareAsset softwareAsset = new SoftwareAsset();

		when(softwareRepository.findById(id)).thenReturn(softwareAsset);

		// Act

		boolean result = softwareServiceImpl.updateAsset(id, assetBody);

		// Assert
		assertTrue(result);
	}

	@Test
	void testChangeStatusSuccess() {
		// Arrange
		SoftwareAsset asset1 = new SoftwareAsset();
		asset1.setId(1);
		asset1.setStatus("active");

		SoftwareAsset asset2 = new SoftwareAsset();
		asset2.setId(2);
		asset2.setStatus("active");

		List<Integer> ids = Arrays.asList(1, 2);

		when(softwareRepository.findById(1)).thenReturn(asset1);
		when(softwareRepository.findById(2)).thenReturn(asset2);

		// Act
		boolean result = softwareServiceImpl.changeStatus(ids);

		// Assert

		// Verify that the repository was called with the correct parameters
		verify(softwareRepository, times(1)).findById(1);
//       verify(softwareRepository, times(1)).save(asset1);
		verify(softwareRepository, times(1)).findById(2);

		verify(softwareRepository, times(2)).save(asset2);

		// Verify that the method returned true
		assertTrue(result);

		// Verify that the assets were updated correctly
		assertEquals("inactive", asset1.getStatus());
		assertEquals("inactive", asset2.getStatus());

	}

	@Test
	void testGeAlltSoftwareAssetsSuccess() {
		// Arrange

		int page = 0;
		int size = 10;
		Page<SoftwareAsset> hardwares = Mockito.mock(Page.class);
		when(softwareRepository.findByStatusOrStatus(eq("Unallocated"), eq("Allocated"), Mockito.any(Pageable.class)))
				.thenReturn(hardwares);

		// Act
		Page<SoftwareAsset> pages = softwareServiceImpl.getAllSoftwareAssets(page, size);

		// Assert
		Assertions.assertThat(pages).isNotNull();

	}

	@Test
	void testSearchSoftwareItemsSuccess() {
		// Arrange
		int page = 0;
		int size = 10;
		String searchTerm = "Lenovo";
		Page<SoftwareAsset> softwares = Mockito.mock(Page.class);
		when(softwareRepository.searchSoftware(eq(searchTerm), Mockito.any(Pageable.class))).thenReturn(softwares);

		// Act
		Page<SoftwareAsset> pages = softwareServiceImpl.searchSoftwareItems(page, size, searchTerm);

		// Assert

		verify(softwareRepository, times(1)).searchSoftware(eq(searchTerm), Mockito.any(Pageable.class));
		Assertions.assertThat(pages).isNotNull();
	}

	@Test
	void testGetHardwareAssetPaginationSuccess() {
		// Arrange
		int pageNumber = 0;
		int pageSize = 10;
		Page<SoftwareAsset> hardwares = Mockito.mock(Page.class);

		SoftwareAsset hardware1 = new SoftwareAsset();
		hardware1.setStatus("Unallocated");

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		when(softwareRepository.findByStatus(hardware1.getStatus(), pageable)).thenReturn(hardwares);

		// Act
		Page<SoftwareAsset> pages = softwareServiceImpl.getSoftwareAssetPagination(pageNumber, pageSize);

		// Assert
		verify(softwareRepository, times(1)).findByStatus(hardware1.getStatus(), pageable);
		Assertions.assertThat(pages).isNotNull();
	}

	@Test
	void testUpdateSoftwareAssetSuccess() {
		
		//Arrange
		String status = "Unallocated";
		 SoftwareAsset asset1 = new SoftwareAsset();
	        asset1.setId(1);
	        asset1.setStatus("Unallocated");
	        asset1.setPurchased(3);
	        
	        when(softwareRepository.findById(1)).thenReturn(asset1);
		//Act
	        softwareServiceImpl.updateSoftwareAsset(asset1.getId(), status);
	        
	     //Assert
	      verify(softwareRepository,times(1)).findById(asset1.getId());  
	      verify(softwareRepository,times(1)).save(asset1);
		    
	}
}
