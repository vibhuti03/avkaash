# avkaash
A Leave Management System

#### 1. Leave Status API - 
http://localhost:8080/api/v1/leave-status?leaveId=##
This gives the details of the leave inclusive of the leave type, number of days on leave, start and end date.
Output - 
``
{
    "id": 3,
    "leaveType": "Privilege",
    "leaveStartDate": "2023-01-11",
    "leaveEndDate": "2023-01-11",
    "totalDays": "1",
    "leaveStatus": "Approved"
}``


#### 2. Delete Leave API - 
http://localhost:8080/api/v1/delete-leave/##
This will delete the leave by leave id.
