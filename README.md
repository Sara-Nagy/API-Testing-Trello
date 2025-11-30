# API Testing Projects – Manual & Automation

This repository contains two API testing projects built using **Trello REST API**, showcasing both **manual API testing** (Postman + Newman) and **automation API testing** (Java + Rest Assured + TestNG + Allure Reporting**).

---

## 📌 **1. Manual API Testing Project (Postman + Newman)**

### **Overview**

Performed manual API testing on Trello’s REST APIs using **Postman**, with JavaScript test scripts for validations. Executed automated collections using **Newman** and generated HTML reports.

### **Tools & Technologies**

* Postman
* JavaScript (for Postman test scripts)
* Newman (CLI runner)
* HTML report generation

### **Test Coverage**

A total of **25 requests** divided into organized folders:

* **Board Management**
* **Member & Label Management**
* **Card & List Management**
* **Negative Tests** (validation for errors & invalid inputs)

### **What Was Done**

* Wrote detailed tests using Postman test scripts
* Used environment variables for maintainability
* Ran the collection automatically using Newman
* Generated HTML reports through Newman reporters

---

## 📌 **2. Automation API Testing Project (Java + Rest Assured)**

### **Overview**

Automated the same Trello API test scenarios using **Java** in **IntelliJ IDEA** applying OOP principles and a clean project architecture.

### **Tech Stack**

* **Java**
* **Rest Assured** (API automation)
* **TestNG** (test execution)
* **Maven** (dependency management)
* **Hamcrest** (assertions)
* **Log4j & SLF4J2** (logging)
* **Allure Reports** (reporting)

### **Features Implemented**

* Designed using **OOP principles** and reusable components
* Parallel execution of **4 test classes** using TestNG
* Logging integrated using **Log4j + SLF4J2**
* Allure reporting with detailed request & response logs
* Clean folder structure for maintainability

### **Test Suites Included**

* **Board Tests**
* **List & Card Tests**
* **Member & Label Tests**
* **Negative Tests**

### **How to Run**

```bash
mvn clean test
```

Generate Allure report:

```bash
allure serve allure-results
```

---

## 📁 **Project Structure (Automation Project)**

```
project
├── src
│   ├── main
│   │   └── java (core + utilities + request builders)
│   └── test
│       ├── tests (board, card/list, member/label, negative)
│       ├── testng.xml
│       └── resources
├── pom.xml
└── README.md
```

---



---

## 🤝 **Contributions**

Pull requests are welcome!

---

If you like this project, don’t forget to ⭐ star the repo!

