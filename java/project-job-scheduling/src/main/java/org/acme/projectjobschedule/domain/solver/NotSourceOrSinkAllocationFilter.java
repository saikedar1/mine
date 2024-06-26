package org.acme.projectjobschedule.domain.solver;

import ai.timefold.solver.core.api.domain.entity.PinningFilter;

import org.acme.projectjobschedule.domain.Allocation;
import org.acme.projectjobschedule.domain.JobType;
import org.acme.projectjobschedule.domain.ProjectJobSchedule;

public class NotSourceOrSinkAllocationFilter implements PinningFilter<ProjectJobSchedule, Allocation> {

    @Override
    public boolean accept(ProjectJobSchedule projectJobSchedule, Allocation allocation) {
        JobType jobType = allocation.getJob().getJobType();
        return jobType == JobType.SOURCE || jobType == JobType.SINK;
    }

}
