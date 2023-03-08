package com.bytestrone.assets;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bytestrone.assets.model.HardwareAsset;
import com.bytestrone.assets.repository.HardwareRepository;


@SpringBootTest
@AutoConfigureJdbc
public class HadwareRepositoryTest {

	@Autowired
	private HardwareRepository hardwareRepository;

	@Test
	public void hardwareRepo_FindById_ReturnHardware() {
		// Arrange
		HardwareAsset hardware = new HardwareAsset();
		hardware.setAssetNumber("q233");
		hardware.setAssetType("Hardware");
		hardware = hardwareRepository.save(hardware);

		// Act

		HardwareAsset hardware1 = hardwareRepository.findById(hardware.getId());
		// Assert

		Assertions.assertThat(hardware1).isNotNull();
	}
}
