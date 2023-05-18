package com.vibhuti.avkaash.controller;

import com.vibhuti.avkaash.exception.ResourceNotFoundException;
import com.vibhuti.avkaash.models.EmployeeInfoEntity;
import com.vibhuti.avkaash.models.LeaveHistoryEntity;
import com.vibhuti.avkaash.repositories.EmployeeInfoRepo;
import com.vibhuti.avkaash.repositories.LeaveHistoryRepo;
import com.vibhuti.avkaash.services.DeleteLeave;
import liquibase.repackaged.org.apache.commons.collections4.map.HashedMap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("api/v1")
public class AvkaashController {

    @Autowired
    private EmployeeInfoRepo employeeInfoRepo;

    @Autowired
    private LeaveHistoryRepo leaveHistoryRepo;

    private DeleteLeave deleteLeave;

    //get all Employees
    @GetMapping("/employee-by-manager")
    public List<EmployeeInfoEntity> getEmployeeByManager(
            @RequestParam("managerMail")  String managerMail
    ){
        log.info("Get - Employee list by manager mail");
        return employeeInfoRepo.findByManagerMail(managerMail);
    }

    @GetMapping("/leave-status")
    public LeaveHistoryEntity getLeaveStatus(
            @RequestParam("leaveId") Long leaveId
    ){
        return leaveHistoryRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("No leave found"));
    }

    @DeleteMapping("/delete-leave/{leaveId}")
    public ResponseEntity<Map<String, Boolean>> deleteLeave(
        @PathVariable long leaveId
    ){

        return deleteLeave.deleteLeaveByID(leaveId);
    }

}
