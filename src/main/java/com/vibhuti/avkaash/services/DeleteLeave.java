package com.vibhuti.avkaash.services;

import com.vibhuti.avkaash.exception.ResourceNotFoundException;
import com.vibhuti.avkaash.models.LeaveHistoryEntity;
import com.vibhuti.avkaash.repositories.LeaveHistoryRepo;
import liquibase.repackaged.org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class DeleteLeave {


    @Autowired
    private LeaveHistoryRepo leaveHistoryRepo;

    public ResponseEntity<Map<String, Boolean>> deleteLeaveByID(long leaveId) {

        LeaveHistoryEntity leave = leaveHistoryRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("No leave found"));

        leaveHistoryRepo.deleteById(leaveId);

        Map<String, Boolean> deleted = new HashedMap<>();
        deleted.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(deleted);
    }

}
