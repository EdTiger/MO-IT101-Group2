<div align="center">

# Basic Payroll System (Phase 1)

</div>

## Project Overview
This repository contains the Phase 1 implementation of the MotorPH Basic Payroll System. The initial requirement focuses on the presentation of employee details and the automatic calculation of salaries through code, utilizing the number of hours worked and basic deductions.

## Phase 1 Tasks and Objectives
* **Employee Information:** Present employee details in the prescribed format, specifically the employee number, name, and birthday.
* **Hours Worked Calculation:** Calculate the total weekly hours worked by an employee.
* **Gross Salary Calculation:** Compute the gross weekly salary based on hours worked.
* **Net Salary Calculation:** Compute the net weekly salary after applying generic deductions.

## Development Environment
This project is developed exclusively in Java. Ensure the local environment matches the following specifications to execute the source code correctly:

| Component | Specification |
| :--- | :--- |
| **Language** | Java |
| **Runtime** | `java 21.0.10 2026-01-20 LTS` |
| **JVM** | Java HotSpot(TM) 64-Bit Server VM (build 21.0.10+8-LTS-217, mixed mode, sharing) |
| **Compiler** | `javac 21.0.10` |

## Documentation
* **Project Management:** [View Project Plan on Google Sheets](https://docs.google.com/spreadsheets/d/1FF1jRVCnI0Zv32z_0VOuM7BB6sGdVNDUTtjyJr_Z7lg/edit?gid=2134013708#gid=2134013708)

## Development Team
This phase of the MotorPH Basic Payroll System was developed by **ByteBeans**.

***
/* Import clean developer font */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap');

body{
    font-family: "Inter", sans-serif;
    background:#f5f7fb;
    color:#2d2d2d;
    line-height:1.6;
    margin:40px;
}

/* Title */
h1{
    font-size:40px;
    font-weight:700;
    color:#1f3c88;
    margin-bottom:10px;
}

/* Section headers */
h2{
    font-size:24px;
    margin-top:35px;
    border-left:5px solid #4a6cf7;
    padding-left:12px;
}

/* Lists */
ul{
    padding-left:20px;
}

li{
    margin:8px 0;
}

/* Table styling */
table{
    width:100%;
    border-collapse:collapse;
    margin-top:15px;
    background:white;
    border-radius:8px;
    overflow:hidden;
    box-shadow:0 4px 10px rgba(0,0,0,0.05);
}

th{
    background:#4a6cf7;
    color:white;
    padding:12px;
    text-align:left;
}

td{
    padding:12px;
    border-bottom:1px solid #eee;
}

tr:hover{
    background:#f2f6ff;
}

/* Links */
a{
    color:#4a6cf7;
    text-decoration:none;
    font-weight:500;
}

a:hover{
    text-decoration:underline;
}

/* Centered title container */
div[align="center"]{
    margin-bottom:30px;
}

/* Footer team text */
strong{
    color:#1f3c88;
}
