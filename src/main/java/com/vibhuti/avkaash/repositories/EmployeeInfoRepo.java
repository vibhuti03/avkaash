package com.vibhuti.avkaash.repositories;

import com.vibhuti.avkaash.models.EmployeeInfoEntity;
import liquibase.pro.packaged.L;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface EmployeeInfoRepo extends Repository<EmployeeInfoEntity, Long> {

    EmployeeInfoEntity save(EmployeeInfoEntity employeeInfoEntity);

    Optional<EmployeeInfoEntity> findAll();
    Optional<EmployeeInfoEntity> findById(long id);
    List<EmployeeInfoEntity> findByManagerMail(String managerMail);


}
