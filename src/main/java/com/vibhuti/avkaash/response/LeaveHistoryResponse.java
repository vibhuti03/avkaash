package com.vibhuti.avkaash.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveHistoryResponse {

    private Long leaveId;

    private String leaveType;

    private LocalDate leaveStartDate;

    private LocalDate leaveEndDate;

    private String totalDays;

    private String leaveStatus;

    private Long employeeId;
}
