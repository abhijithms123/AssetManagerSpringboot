package com.bytestrone.assets.viewobjectDashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CountSummaryVO {

	private String type;
	long assignedCount=0;
	long unassignedCount=0;

}
