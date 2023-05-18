package com.vibhuti.avkaash.repositories;

import com.vibhuti.avkaash.models.LeaveHistoryEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface LeaveHistoryRepo extends Repository<LeaveHistoryEntity, Long> {

    LeaveHistoryEntity save(LeaveHistoryEntity leaveHistory);

    List<LeaveHistoryEntity> findAll();
    List<LeaveHistoryEntity> findByEmployeeId(long employeeId);
}
