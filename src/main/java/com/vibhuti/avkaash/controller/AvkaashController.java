package com.vibhuti.avkaash.controller;

import com.vibhuti.avkaash.exception.ResourceNotFoundException;
import com.vibhuti.avkaash.models.EmployeeInfoEntity;
import com.vibhuti.avkaash.models.LeaveHistoryEntity;
import com.vibhuti.avkaash.repositories.EmployeeInfoRepo;
import com.vibhuti.avkaash.repositories.LeaveHistoryRepo;
import com.vibhuti.avkaash.request.EmployeeInfoRequest;
import com.vibhuti.avkaash.response.EmployeeInfoResponse;
import liquibase.repackaged.org.apache.commons.collections4.map.HashedMap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("api/v1")
public class AvkaashController {

    @Autowired
    private EmployeeInfoRepo employeeInfoRepo;

    @Autowired
    private LeaveHistoryRepo leaveHistoryRepo;

    //get all Employees
    @GetMapping("/employee-by-manager/{managerMail}")
    public ResponseEntity<List<EmployeeInfoResponse>> getEmployeeByManager(
            @PathVariable("managerMail")  String managerMail
    ){
        log.info("Get - Employee list by manager mail");

        List<EmployeeInfoEntity> list =employeeInfoRepo.findByManagerMail(managerMail);
        List<EmployeeInfoResponse> responseList = new ArrayList<>();

        list.forEach(e -> {
            EmployeeInfoResponse employeeResponse = new EmployeeInfoResponse();
            employeeResponse.setId(e.getId());
            employeeResponse.setEmployeeMail(e.getEmployeeMail());
            employeeResponse.setManagerMail(e.getManagerMail());
            employeeResponse.setPrivilegeLeaveBalance(e.getPrivilegeLeaveBalance());
            employeeResponse.setCasualLeaveBalance(e.getCasualLeaveBalance());
            employeeResponse.setSickLeaveBalance(e.getSickLeaveBalance());
            List<String> leaves = new ArrayList<>();
            for(LeaveHistoryEntity l : e.getLeaveHistoryEntities()) {
                leaves.add(l.getLeaveType() + ":" + l.getTotalDays());
            }
            employeeResponse.setLeaveHistory(leaves);
            responseList.add(employeeResponse);
        });

        return new ResponseEntity<>(responseList, HttpStatus.OK);
//                .orElseThrow(() -> new ResourceNotFoundException("No employee found"));
    }

    @PostMapping("/add-employee-info")
    public ResponseEntity<String> saveEmployeeInfo(
             @RequestBody EmployeeInfoRequest employeeInfoRequest
            ){
        EmployeeInfoEntity employeeInfo = new EmployeeInfoEntity(employeeInfoRequest);
        employeeInfoRepo.save(employeeInfo);

        employeeInfoRequest.getLeaveHistory().forEach(e -> {
            LeaveHistoryEntity l = new LeaveHistoryEntity();
            l.setLeaveType(e.getLeaveType());
            l.setLeaveStartDate(e.getLeaveStartDate());
            l.setLeaveEndDate(e.getLeaveEndDate());
            l.setLeaveStatus(e.getLeaveStatus());
            l.setEmployee(e.getEmployee());

            leaveHistoryRepo.save(l);
        });

        return new ResponseEntity<String>("Record saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/leave-status")
    public LeaveHistoryEntity getLeaveStatus(
            @RequestParam("leaveId") Long leaveId
    ){
        return leaveHistoryRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("No leave found"));
    }

    @DeleteMapping("/delete-leave/{leaveId}/{employeeId}")
    public ResponseEntity<Map<String, Boolean>> deleteLeave(
        @PathVariable("leaveId") long leaveId,
        @PathVariable("employeeId") long employeeId
    ){

        LeaveHistoryEntity leave = leaveHistoryRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("No leave found"));

//        log.info(employeeInfoRepo.findById(employeeId).get().getEmployeeLeave().toString());

        leaveHistoryRepo.deleteById(leaveId);

        Map<String, Boolean> deleted = new HashedMap<>();
        deleted.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(deleted);
    }

}
