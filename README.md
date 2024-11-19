# SC2002 Hospital Management System (HMS)

![UML Class Diagram](https://img.shields.io/badge/UML%20Class%20Diagram-1976D2?style=for-the-badge&logoColor=white)
![Solid Design Principles](https://img.shields.io/badge/SOLID%20Design%20Principles-C71A36?style=for-the-badge&logoColor=white)
![OOP Concepts](https://img.shields.io/badge/OOP%20Concepts-C71A36?style=for-the-badge&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)

**Team:** [Lim Jing Rong](https://github.com/JingRonggg) |
[Loo Ping Wee](https://github.com/lpwee) |
[Ng Jing En](https://github.com/negnij) |
[Ong Wee Kiat,Ryan](https://github.com/RyanOngWK)|
[Ong Yao Sheng](https://github.com/kuehlapis)|

**Docs:** [Report](https://github.com/JingRonggg/SC2002-HMS/blob/main/report/HMSReport.pdf) | 
[UML Class Diagram](https://github.com/JingRonggg/SC2002-HMS/blob/main/UML/SC2002-HMS.pdf) |
[Java Docs](https://github.com/JingRonggg/SC2002-HMS/blob/main/doc/index.html)
## 📋 Project Overview

The **SC2002 Hospital Management System (HMS)** is a Java-based application developed as part of the SC2002 Object-Oriented Design and Programming module. The system leverages OOP principles and adheres to SOLID design principles to provide a robust and scalable solution for managing hospital operations, including:

- Patient records
- Appointment scheduling
- Staff management

This README file provides instructions on how to clone, compile, and run the project.

---
## Table of Contents

- [SC2002 Hospital Management System (HMS)](#sc2002-hospital-management-system-hms)
  - [📋 Project Overview](#-project-overview)
  - [Table of Contents](#table-of-contents)
  - [📂 Project Structure](#-project-structure)
- [HMS setup instructions](#hms-setup-instructions)
  - [Compiling and Running the project](#compiling-and-running-the-project)
    - [Using the terminal](#using-the-terminal)
    - [Using Eclipse](#using-eclipse)
  - [Generating JavaDocs](#generating-javadocs)
    - [Using the terminal](#using-the-terminal-1)
    - [Using Eclipse](#using-eclipse-1)
- [Usage](#usage)
  - [Login Credentials](#login-credentials)


---


## 📂 Project Structure

```
SC2002-HMS/
├── controller/
├── interfaces/
├── model/
├── repository/
├── utils/ 
├── view/
├── Main.java # Entry point of the application
```

---
# HMS setup instructions

## Compiling and Running the project

### Using the terminal

1. Clone the repository:
   ```bash
   git clone https://github.com/JingRonggg/SC2002-HMS.git
   cd SC2002-HMS
   ```

2. Compile the source code:
   ```bash
   javac -d bin src/**/*.java
   ```

3. Run the application:
   ```bash
   java -cp bin Main
   ```
Congratulations, you have successfully cloned, compiled, and ran the HMS project!

### Using Eclipse

If you prefer to use Eclipse as your IDE, you can also set up the project there. Here are the steps you need to follow:

1. Open Eclipse
2. Click on `File` > `Import` > `Git` > `Projects from Git` > `Clone URI`
3. In the `Clone URI` window, paste the following URL:

   ```bash
   https://github.com/JingRonggg/SC2002-HMS.git
   ```
4. Click `Next` and follow the prompts to finish the cloning process
5. Once the project is cloned, right-click on the project folder and select `Properties`
6. In the `Properties` window, click on `Java Build Path` > `Source` > `Add Folder`
7. Select the `src` folder from the project directory and click `OK`
8. Now you can run the project by right-clicking on `Main.java` in the `src` folder and selecting `Run As` > `Java Application`

That's it! You should now have the project up and running in Eclipse.

## Generating JavaDocs

### Using the terminal

Follow the steps below to generate JavaDocs using the terminal:

1. Open you terminal.
2. Navigate to the root directory of the project.
3. Run the following command in the terminal:

   ```bash
    javadoc -d docs -sourcepath src -subpackages controllers:interfaces:model:repository:utils:view -private
   ```

   This command will generate the JavaDocs and save them in the docs directory in HTML format.

4. Navigate to the `docs` directory using the following command:

   ```bash
   cd docs
   ```

5. Open the `index.html` file in a web browser to view the generated JavaDocs.

Congratulations, you have successfully created and viewed the JavaDocs.

### Using Eclipse

1. Open the Eclipse IDE and open your Java project.

2. Select the package or class for which you want to generate JavaDocs.

3. Go to the "Project" menu and select "Generate Javadoc".

4. In the "Generate Javadoc" dialog box, select the "Private" option to generate JavaDocs for private classes and members.

5. Choose the destination folder where you want to save the generated JavaDocs.

6. In the "Javadoc command line arguments" field, add any additional arguments that you want to include, such as `-classpath`.

7. Click the "Finish" button to start the JavaDocs generation process.

8. Once the JavaDocs have been generated, you can view them by opening the `index.html` file in your web browser.

Congratulations, you have successfully created and viewed the JavaDocs.

# Usage

## Login Credentials

This section contains some login credentials for users with different roles. The full list is available in `data/Staff_List.csv` & `data/Patient_List.csv` files.

**Patients:**
```bash
#Alice Brown
USERID: P1001
PASSWORD: password

#Bob Stone
USERID: P1002
PASSWORD: password

#Charlie White
USERID: P1003
PASSWORD: password
```

**Staff:**

```bash
#Doctor
USERID: D002
PASSWORD: password

#Pharmacist
USERID: P001
PASSWORD: password

#Administrator
USERID: A001
PASSWORD: password
```
