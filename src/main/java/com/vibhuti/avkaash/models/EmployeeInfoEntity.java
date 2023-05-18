package com.vibhuti.avkaash.models;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee_info")
public class EmployeeInfoEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name="employee_mail")
    private String employeeMail;

    @Column(name="manager_mail")
    private String managerMail;

    @Column(name="privilege_leave_balance")
    private int privilegeLeaveBalance;

    @Column(name="sick_leave_balance")
    private int sickLeaveBalance;

    @Column(name="casual_leave_balance")
    private int casualLeaveBalance;

    @OneToMany
    @JoinColumn(name="employee_id")
    private List<LeaveHistoryEntity> employeeLeave;
}
