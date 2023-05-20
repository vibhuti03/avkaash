package com.vibhuti.avkaash.controller;

import com.vibhuti.avkaash.exception.ResourceNotFoundException;
import com.vibhuti.avkaash.models.EmployeeInfoEntity;
import com.vibhuti.avkaash.models.LeaveHistoryEntity;
import com.vibhuti.avkaash.repositories.EmployeeInfoRepo;
import com.vibhuti.avkaash.repositories.LeaveHistoryRepo;
import com.vibhuti.avkaash.request.EmployeeInfoRequest;
import com.vibhuti.avkaash.request.LeaveHistoryRequest;
import com.vibhuti.avkaash.response.EmployeeInfoResponse;
import com.vibhuti.avkaash.response.LeaveHistoryResponse;
import liquibase.repackaged.org.apache.commons.collections4.map.HashedMap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("api/v1")
public class AvkaashController {

    @Autowired
    private EmployeeInfoRepo employeeInfoRepo;

    @Autowired
    private LeaveHistoryRepo leaveHistoryRepo;

    //1. For employee/manager to get info on the employee's leave
    @GetMapping("/employee-leave-info/{id}")
    public ResponseEntity<List<EmployeeInfoResponse>> getEmployeeByManager(
            @PathVariable("id") Long employeeId
    ) {
        log.info("Get - Employee list by employee ID");

        List<EmployeeInfoEntity> list = (employeeInfoRepo.findById(employeeId)).stream().collect(Collectors.toList());
        List<EmployeeInfoResponse> responseList = new ArrayList<>();

        list.forEach(e -> {
            EmployeeInfoResponse employeeResponse = new EmployeeInfoResponse();
            employeeResponse.setId(e.getId());
            employeeResponse.setEmployeeMail(e.getEmployeeMail());
            employeeResponse.setManagerMail(e.getManagerMail());
            employeeResponse.setPrivilegeLeaveBalance(e.getPrivilegeLeaveBalance());
            employeeResponse.setCasualLeaveBalance(e.getCasualLeaveBalance());
            employeeResponse.setSickLeaveBalance(e.getSickLeaveBalance());
            List<LeaveHistoryResponse> leaveHistoryList = new ArrayList<>();
            e.getLeaveHistoryEntities().forEach(l -> {
                LeaveHistoryResponse leaves = new LeaveHistoryResponse();
                leaves.setLeaveId(l.getId());
                leaves.setLeaveType(l.getLeaveType());
                leaves.setLeaveStartDate(l.getLeaveStartDate());
                leaves.setLeaveEndDate(l.getLeaveEndDate());
                leaves.setLeaveStatus(l.getLeaveStatus());
                leaves.setTotalDays(l.getTotalDays());

                leaveHistoryList.add(leaves);
            });


            employeeResponse.setLeaveHistory(leaveHistoryList);
            responseList.add(employeeResponse);
        });

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    //2. For manager to get data on each of their employee's leaves
    @GetMapping("/employee-by-manager/{managerMail}")
    public ResponseEntity<List<EmployeeInfoResponse>> getEmployeeByManager(
            @PathVariable("managerMail") String managerMail
    ) {
        log.info("Get - Employee list by manager mail");

        List<EmployeeInfoEntity> list = employeeInfoRepo.findByManagerMail(managerMail);
        List<EmployeeInfoResponse> responseList = new ArrayList<>();

        list.forEach(e -> {
            EmployeeInfoResponse employeeResponse = new EmployeeInfoResponse();
            employeeResponse.setId(e.getId());
            employeeResponse.setEmployeeMail(e.getEmployeeMail());
            employeeResponse.setManagerMail(e.getManagerMail());
            employeeResponse.setPrivilegeLeaveBalance(e.getPrivilegeLeaveBalance());
            employeeResponse.setCasualLeaveBalance(e.getCasualLeaveBalance());
            employeeResponse.setSickLeaveBalance(e.getSickLeaveBalance());
            List<LeaveHistoryResponse> leaveHistoryList = new ArrayList<>();
            e.getLeaveHistoryEntities().forEach(l -> {
                LeaveHistoryResponse leaves = new LeaveHistoryResponse();
                leaves.setLeaveId(l.getId());
                leaves.setLeaveType(l.getLeaveType());
                leaves.setLeaveStartDate(l.getLeaveStartDate());
                leaves.setLeaveEndDate(l.getLeaveEndDate());
                leaves.setLeaveStatus(l.getLeaveStatus());
                leaves.setTotalDays(l.getTotalDays());

                leaveHistoryList.add(leaves);
            });


            employeeResponse.setLeaveHistory(leaveHistoryList);
            responseList.add(employeeResponse);
        });

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    //3. For manager to add employee info on DB
    @PostMapping("/add-employee-info")
    public ResponseEntity<String> saveEmployeeInfo(
            @RequestBody EmployeeInfoRequest employeeInfoRequest
    ) {
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

        return new ResponseEntity<>("Record saved successfully", HttpStatus.CREATED);
    }

    //4. For employee to apply leave
    @PostMapping("/apply-leave")
    public ResponseEntity<String> applyLeave(
            @RequestBody LeaveHistoryRequest leaveHistoryRequest
            ){

        EmployeeInfoEntity employee = employeeInfoRepo.findById(leaveHistoryRequest.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid employee ID"));

        leaveHistoryRequest.setLeaveStatus("Applied");

        LeaveHistoryEntity leaveApplication = new LeaveHistoryEntity(leaveHistoryRequest, employee);

        leaveHistoryRepo.save(leaveApplication);

        return new ResponseEntity<>("Leave Applied", HttpStatus.ACCEPTED);
    }

    //5. For manager to approve leave
    @PutMapping("/approve-leave/{leaveId}")
    public ResponseEntity<String> approveLeave(
            @PathVariable("leaveId") Long leaveId
    ){
        LeaveHistoryEntity leave = leaveHistoryRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("No such leave applied"));

        leave.setLeaveStatus("Approved");

        leaveHistoryRepo.save(leave);

        return new ResponseEntity<>("Leave " +leaveId+ " approved", HttpStatus.ACCEPTED);
    }

    //6. For employee/manager to get the leave status by leave id
    @GetMapping("/leave-status/{leaveId}")
    public ResponseEntity<LeaveHistoryResponse> getLeaveStatus(
            @PathVariable("leaveId") Long leaveId
    ) {
        LeaveHistoryEntity leaveHistoryEntity = leaveHistoryRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("No leave info found"));


        LeaveHistoryResponse leave = new LeaveHistoryResponse();
        leave.setLeaveId(leaveHistoryEntity.getId());
        leave.setLeaveType(leaveHistoryEntity.getLeaveType());
        leave.setLeaveStartDate(leaveHistoryEntity.getLeaveStartDate());
        leave.setLeaveEndDate(leaveHistoryEntity.getLeaveEndDate());
        leave.setTotalDays(leaveHistoryEntity.getTotalDays());
        leave.setLeaveStatus(leaveHistoryEntity.getLeaveStatus());
        leave.setEmployeeId(leaveHistoryEntity.getEmployee().getId());

        return new ResponseEntity<>(leave, HttpStatus.FOUND);
    }

    //7. For employee to delete a previously applied leave
    @DeleteMapping("/delete-leave/{leaveId}/{employeeId}")
    public ResponseEntity<Map<String, Boolean>> deleteLeave(
            @PathVariable("leaveId") long leaveId,
            @PathVariable("employeeId") long employeeId
    ) {

        LeaveHistoryEntity leave = leaveHistoryRepo.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("No leave found"));

        log.info(leave.getEmployee().getId());

        leaveHistoryRepo.deleteById(leaveId);

        Map<String, Boolean> deleted = new HashedMap<>();
        deleted.put("Deleted "+leave.getTotalDays()+ " days of "+leave.getLeaveType()+" leave for employee id: "+leave.getEmployee().getId(),
                Boolean.TRUE);

        return ResponseEntity.ok(deleted);
    }

}
