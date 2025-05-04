
<!-- ABOUT THE PROJECT -->
# **About The Project**
___
>This project is about creating a functional Patient Management System (PMS). There are many stakeholders to think of in this situation as well as a large amount of rules and regulations to follow such as HIPAA. Within this project there should be the management of prescriptions from both sides of either the patient and the doctor/care team. Patients are able to view their prescriptions, schedule, and cancel appointments. Doctors can cancel appointments, view patient medical history, and prescribe medication. Admins can create and delete doctors and patients, along with view activity logs, such as sheduling and cancelling appointments, login and logouts, etc.
____

# **Project Use Cases**
> 1. **User Authentication** - users can log into their accounts using credentials stored in the database
> 2. **Management of Patients and Doctors** - admins can create and delete patients and doctors (**Manage Patients**, **Manage Doctors**)
> 3. **Scheduling/Managing Appointments** - patients can schedule and cancel appointments with doctors on times and dates after the current time. Doctors are able to cancel the appointments (**Manage Appointments**)
> 4. **Prescribing Medication** - doctors can prescribe medication to patients. Patients are able to see their prescriptions, and doctors are able to view them in patient medical history, where they can also delete them (**Prescribe Medication**, **View Prescriptions**)
> 5. **View Activity Logs** - admins can view activity logs around the whole system. They can see when people login, logout, create and delete users, schedule and cancel appointments, when doctors edit users, and when medications are prescribed (**View Logs**)
> 6. **Patient Creation and Login Screen** - new patients can create an account on the login screen (**Create Account**)
> 7. **View/Manage Patient Medical History** - doctors can see patient medical history and information. It contains patient personal information, diseases/diagnosis, and their medication. Doctors are able to change the diagnosis and prescriptions here (**View Patient Records**)


# **Connecting to Database**

There are necessary drivers to download depending on what database you use.

## MySQL
    Download Platform Independent from here --> https://dev.mysql.com/downloads/connector/j/
    
    Once downloaded:

        1. Right-click the project, and open Open Module Settings
        2. Modules > Dependencies
        3. Click the + icon > JARs or Directories
        4. Select the .jar file, something like mysql-connector-j-8.0.33.jar
        5. Make sure it's set to Compile
        6. Apply > OK

## .accdb Database

    Download from here --> http://ucanaccess.sourceforge.net/site.html

    Once downloaded:

        1. Right-click the project, and open Open Module Settings
        2. Modules > Dependencies
        3. Click the + icon > JARs or Directories
        4. There are five .jar files to select:

            ucanaccess-x.x.x.jar
            jackcess-x.x.x.jar
            commons-lang-x.x.jar
            commons-logging-x.x.jar
            hsqldb.jar 

        5. Make sure they're set to Compile
        6. Apply > OK


<!-- TESTING LOGIN INFORMATION-->

# **Logins**
The login information for each of the important stake holders within this scenario.
## Doctors
### Test Doctor 1
    username: doctor1 
    password: pass123

## Patients
### Test Patient 1
	username: patient1
    password: pass123

## System Administrators
### Test System Admin 1
	username: admin1
    password: admin123

___
<!-- USAGE EXAMPLES -->
# **Usage**
___
## Doctors
>	View and Update the Medical Records of their patients, they are also able to prescribe their patients prescriptions, and cancel appointments.
## Patients
>	Schedule and cancel appointments and view their prescriptions
## System Administrators
>   Create and delete patient and doctor users and view activity logs around the system
___
<!-- CONTACT -->
# **Contact**
___
Aiden Wiktor,  
aaw5633@psu.edu  
   
Anthony Martinez,  
aem6293@psu.edu  
   
Gursidak Sodhi,  
gss5298@psu.edu  
  
John Levine,  
jel5817@psu.edu  
   
Thomas Kiedaisch,  
tjk5903@psu.edu   
