package com.vibhuti.avkaash.response;

import com.vibhuti.avkaash.models.LeaveHistoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EmployeeInfoResponse {

    private Long id;

    private String employeeMail;

    private String managerMail;

    private int privilegeLeaveBalance;

    private int sickLeaveBalance;

    private int casualLeaveBalance;

    private List<LeaveHistoryResponse> leaveHistory;
}
