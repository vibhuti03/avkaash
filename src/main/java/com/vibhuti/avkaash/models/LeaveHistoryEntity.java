package com.vibhuti.avkaash.models;


import com.vibhuti.avkaash.request.EmployeeInfoRequest;
import com.vibhuti.avkaash.request.LeaveHistoryRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "leave_history")
public class LeaveHistoryEntity {

    @Id
    @Column(name = "leave_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "leave_type")
    private String leaveType;

    @Column(name = "leave_start_date")
    private LocalDate leaveStartDate;

    @Column(name = "leave_end_date")
    private LocalDate leaveEndDate;

    @Column(name="total_days")
    private String totalDays;

    @Column(name="leave_status")
    private String leaveStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeInfoEntity employee;

    public LeaveHistoryEntity(LeaveHistoryRequest leaveHistoryRequest, EmployeeInfoEntity employee) {
//        this.id = leaveHistoryRequest.getId();
        this.leaveType = leaveHistoryRequest.getLeaveType();
        this.leaveStartDate = leaveHistoryRequest.getLeaveStartDate();
        this.leaveEndDate = leaveHistoryRequest.getLeaveEndDate();
        this.totalDays = leaveHistoryRequest.getTotalDays();
        this.leaveStatus = leaveHistoryRequest.getLeaveStatus();
        this.employee = employee;
    }

}
