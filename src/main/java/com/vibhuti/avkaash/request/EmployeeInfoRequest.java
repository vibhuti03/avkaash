package com.vibhuti.avkaash.request;


import com.vibhuti.avkaash.models.LeaveHistoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeInfoRequest {

    private Long id;

    private String employeeMail;

    private String managerMail;

    private int privilegeLeaveBalance;

    private int sickLeaveBalance;

    private int casualLeaveBalance;

    private List<LeaveHistoryEntity> leaveHistory;
}
