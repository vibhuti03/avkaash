package com.vibhuti.avkaash.repositories;

import com.vibhuti.avkaash.models.LeaveHistoryEntity;
import liquibase.pro.packaged.Q;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface LeaveHistoryRepo extends Repository<LeaveHistoryEntity, Long> {

    LeaveHistoryEntity save(LeaveHistoryEntity leaveHistory);

    List<LeaveHistoryEntity> findAll();
    Optional<LeaveHistoryEntity> findById(long leaveId);

    void deleteById(long leaveId);

    boolean existsById(long leaveID);
}
