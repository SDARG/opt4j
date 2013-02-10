package org.opt4j.optimizer.sa;

import com.google.inject.Inject;

/**
 * The default cooling schedule is the {@link CoolingScheduleLinear}.
 * 
 * @author reimann
 */
public class CoolingScheduleDefault extends CoolingScheduleLinear {

	/**
	 * Constructs a default {@link CoolingSchedule}.
	 */
	@Inject
	public CoolingScheduleDefault() {
		super(10.0, 1.0);
	}
}
