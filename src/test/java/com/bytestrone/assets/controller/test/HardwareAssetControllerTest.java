package com.bytestrone.assets.controller.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bytestrone.assets.controller.HardwareAssetController;
import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.service.HardwareService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;



@RunWith(MockitoJUnitRunner.class) // just test the methods,not the web server so we run it with mockito
public class HardwareAssetControllerTest {

	private MockMvc mockMvc;

	// to convert json to string
	// for post methods
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private HardwareService hardwareService;

	@InjectMocks
	private HardwareAssetController hardwareAssetController;

	HardwareAsset hardware1 = new HardwareAsset();

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(hardwareAssetController).build();
	}
}
