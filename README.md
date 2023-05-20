# avkaash
A Leave Management System

## Databases

There are two tables currently supporting the system, which are - 
1. ``employee_info`` - To store the basic employee details and associated manager information.
   
2. ``leave_history`` - To store the applied and approved leave status


## API

#### 1. Employee Leave Info - 
GET _http://localhost:8080/api/v1/employee-leave-info/201_ <br/>
To get the details of all the leaves by the Employee's ID.
![API](images/Screenshot%20(1924).png)

#### 2. Employees per Manager - 
GET _http://localhost:8080/api/v1/employee-by-manager/govind@tempmail.com_ <br/>
To get list of all employees, as well as the leaves taken by them per manager. This will help the managers in fetching a cumulative list of employee-leave data.
![API](images/Screenshot%20(1925).png)

#### 3. Add Employee
POST _http://localhost:8080/api/v1/add-employee-info_ <br/>
This is to ease the employee addition process to the DB.
![API](images/Screenshot%20(1926).png)

#### 4. Apply Leave
POST _http://localhost:8080/api/v1/apply-leave_ <br/>
Employees can easily apply leaves using this API.
![API](images/Screenshot%20(1927).png)

#### 5. Approve Leave
PUT _http://localhost:8080/api/v1/approve-leave/36_ <br/>
Managers can easily approve employee leaves by providing the leave id.
![API](images/Screenshot%20(1928).png)

#### 6. Leave Status - 
GET _http://localhost:8080/api/v1/leave-status/36_ <br/>
This gives the details of the leave inclusive of the leave type, number of days on leave, start and end date.
![API](images/Screenshot%20(1929).png)

#### 7. Delete Leave - 
DELETE _http://localhost:8080/api/v1/delete-leave/29_ <br/>
This will delete the leave by leave id.
![API](images/Screenshot%20(1930).png)
