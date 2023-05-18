package com.vibhuti.avkaash.models;


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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private EmployeeInfoEntity employee;

    @Column(name= "leave_type")
    private String productName;

    @Column(name = "leave_start_date")
    private LocalDate leaveStartDate;

    @Column(name = "leave_end_date")
    private LocalDate leaveEndDate;

    @Column(name="total_days")
    private String totalDays;

    @Column(name="leave_status")
    private String leaveStatus;


}
