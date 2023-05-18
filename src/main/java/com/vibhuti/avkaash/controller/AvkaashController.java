package com.vibhuti.avkaash.controller;

import com.vibhuti.avkaash.models.EmployeeInfoEntity;
import com.vibhuti.avkaash.repositories.EmployeeInfoRepo;
import com.vibhuti.avkaash.repositories.LeaveHistoryRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1")
public class AvkaashController {

    @Autowired
    private EmployeeInfoRepo employeeInfoRepo;

    @Autowired
    private LeaveHistoryRepo leaveHistoryRepo;

    //get all Employees
    @GetMapping("/employee-by-manager")
    public List<EmployeeInfoEntity> getEmployeeByManager(
            @RequestParam("managerMail")  String managerMail
    ){
        log.info("Get - Employee list by manager mail");
        return employeeInfoRepo.findByManagerMail(managerMail);
    }

}
