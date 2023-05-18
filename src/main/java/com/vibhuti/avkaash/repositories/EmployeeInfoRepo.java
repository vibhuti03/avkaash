package com.vibhuti.avkaash.repositories;

import com.vibhuti.avkaash.models.EmployeeInfoEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface EmployeeInfoRepo extends Repository<EmployeeInfoEntity, Long> {

    EmployeeInfoEntity save(EmployeeInfoEntity employeeInfoEntity);

    List<EmployeeInfoEntity> findAll();
    List<EmployeeInfoEntity> findByManagerMail(String managerMail);
}
