package com.vibhuti.avkaash.request;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveHistoryRequest {
    private Long id;

    private String leaveType;

    private LocalDate leaveStartDate;

    private LocalDate leaveEndDate;

    private String totalDays;

    private String leaveStatus;

}
