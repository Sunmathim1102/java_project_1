package com.skillmatrix.config;

import com.skillmatrix.entity.*;
import com.skillmatrix.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private final ProjectRepository projectRepository;
    private final ProjectSkillRequirementRepository projectSkillRequirementRepository;
    private final AllocationRepository allocationRepository;

    public DataInitializer(EmployeeRepository employeeRepository,
                           SkillRepository skillRepository,
                           EmployeeSkillRepository employeeSkillRepository,
                           ProjectRepository projectRepository,
                           ProjectSkillRequirementRepository projectSkillRequirementRepository,
                           AllocationRepository allocationRepository) {
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
        this.employeeSkillRepository = employeeSkillRepository;
        this.projectRepository = projectRepository;
        this.projectSkillRequirementRepository = projectSkillRequirementRepository;
        this.allocationRepository = allocationRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (employeeRepository.count() > 0) {
            return; // Data already initialized
        }

        // 1. Create Skills
        Skill java = skillRepository.save(new Skill("Java", "Core Java, JVM, Collections, Concurrency & Stream API", "Backend"));
        Skill springBoot = skillRepository.save(new Skill("Spring Boot", "Spring MVC, Spring Data JPA, Spring Security & Actuator", "Backend"));
        Skill mysql = skillRepository.save(new Skill("MySQL", "Relational database design, indexing, queries and optimization", "Database"));
        Skill restApi = skillRepository.save(new Skill("REST API", "RESTful web services, OpenAPI/Swagger, HTTP status codes", "Architecture"));
        Skill react = skillRepository.save(new Skill("React", "React.js, Hooks, State Management & Modern Frontend", "Frontend"));
        Skill javascript = skillRepository.save(new Skill("JavaScript", "Modern ES6+, Async programming, DOM manipulation", "Frontend"));
        Skill python = skillRepository.save(new Skill("Python", "Python 3 scripting, pandas, data processing", "Backend"));
        Skill git = skillRepository.save(new Skill("Git", "Version control, branching, merge conflict resolution & CI/CD", "DevOps"));

        // 2. Create Employees
        Employee john = employeeRepository.save(new Employee("EMP101", "John Doe", "john.doe@company.com", "Engineering", "Senior Backend Engineer", 100));
        Employee alice = employeeRepository.save(new Employee("EMP102", "Alice Smith", "alice.smith@company.com", "Engineering", "Full Stack Developer", 100));
        Employee bob = employeeRepository.save(new Employee("EMP103", "Bob Johnson", "bob.johnson@company.com", "Engineering", "Lead Java Architect", 100));
        Employee carol = employeeRepository.save(new Employee("EMP104", "Carol White", "carol.white@company.com", "Engineering", "Frontend Specialist", 100));
        Employee david = employeeRepository.save(new Employee("EMP105", "David Lee", "david.lee@company.com", "Engineering", "Junior Software Engineer", 100));

        // 3. Assign Skills to Employees (Proficiency: 1=Beginner, 2=Basic, 3=Intermediate, 4=Advanced, 5=Expert)
        // John: Strong Backend (Java 5, Spring Boot 4, MySQL 4, REST API 4, React 2)
        employeeSkillRepository.save(new EmployeeSkill(john, java, 5));
        employeeSkillRepository.save(new EmployeeSkill(john, springBoot, 4));
        employeeSkillRepository.save(new EmployeeSkill(john, mysql, 4));
        employeeSkillRepository.save(new EmployeeSkill(john, restApi, 4));
        employeeSkillRepository.save(new EmployeeSkill(john, react, 2));

        // Alice: Balanced Full Stack (Java 4, Spring Boot 4, MySQL 3, React 4, JavaScript 5, Git 4)
        employeeSkillRepository.save(new EmployeeSkill(alice, java, 4));
        employeeSkillRepository.save(new EmployeeSkill(alice, springBoot, 4));
        employeeSkillRepository.save(new EmployeeSkill(alice, mysql, 3));
        employeeSkillRepository.save(new EmployeeSkill(alice, react, 4));
        employeeSkillRepository.save(new EmployeeSkill(alice, javascript, 5));
        employeeSkillRepository.save(new EmployeeSkill(alice, git, 4));

        // Bob: Java & Architecture Expert (Java 5, Spring Boot 5, MySQL 5, REST API 5, Git 5)
        employeeSkillRepository.save(new EmployeeSkill(bob, java, 5));
        employeeSkillRepository.save(new EmployeeSkill(bob, springBoot, 5));
        employeeSkillRepository.save(new EmployeeSkill(bob, mysql, 5));
        employeeSkillRepository.save(new EmployeeSkill(bob, restApi, 5));
        employeeSkillRepository.save(new EmployeeSkill(bob, git, 5));

        // Carol: Frontend & Python (React 5, JavaScript 5, Python 3, Git 4, Java 2)
        employeeSkillRepository.save(new EmployeeSkill(carol, react, 5));
        employeeSkillRepository.save(new EmployeeSkill(carol, javascript, 5));
        employeeSkillRepository.save(new EmployeeSkill(carol, python, 3));
        employeeSkillRepository.save(new EmployeeSkill(carol, git, 4));
        employeeSkillRepository.save(new EmployeeSkill(carol, java, 2));

        // David: Junior (Java 2, Spring Boot 2, MySQL 2, Git 3)
        employeeSkillRepository.save(new EmployeeSkill(david, java, 2));
        employeeSkillRepository.save(new EmployeeSkill(david, springBoot, 2));
        employeeSkillRepository.save(new EmployeeSkill(david, mysql, 2));
        employeeSkillRepository.save(new EmployeeSkill(david, git, 3));

        // 4. Create Projects
        Project p1 = projectRepository.save(new Project(
                "PRJ-001",
                "Banking Application",
                "Core online banking and payments platform with microservices integration",
                "Global Apex Bank",
                ProjectStatus.ACTIVE,
                4
        ));

        Project p2 = projectRepository.save(new Project(
                "PRJ-002",
                "E-Commerce Web Portal",
                "High-volume storefront with reactive web UI and inventory management",
                "Nordic Retail Group",
                ProjectStatus.PLANNED,
                3
        ));

        Project p3 = projectRepository.save(new Project(
                "PRJ-003",
                "Cloud Analytics Pipeline",
                "Real-time event processing and telemetry data aggregator",
                "DataStream Inc",
                ProjectStatus.PLANNED,
                2
        ));

        // 5. Define Project Skill Requirements
        // Project 1 (Banking Application): Java 4, Spring Boot 4, MySQL 3, REST API 4
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p1, java, 4, 100));
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p1, springBoot, 4, 100));
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p1, mysql, 3, 90));
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p1, restApi, 4, 90));

        // Project 2 (E-Commerce Web Portal): React 4, JavaScript 4, Spring Boot 3, MySQL 3
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p2, react, 4, 100));
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p2, javascript, 4, 100));
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p2, springBoot, 3, 80));
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p2, mysql, 3, 80));

        // Project 3 (Cloud Analytics Pipeline): Python 4, MySQL 3, Git 4
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p3, python, 4, 100));
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p3, mysql, 3, 80));
        projectSkillRequirementRepository.save(new ProjectSkillRequirement(p3, git, 4, 80));

        // 6. Create Initial Allocations
        // John: allocated 60% on PRJ-001 -> available 40%
        allocationRepository.save(new Allocation(john, p1, 60, LocalDate.now().minusDays(30), LocalDate.now().plusDays(60), AllocationStatus.ACTIVE));
        john.setAllocatedCapacity(60);
        john.setAvailableCapacity(40);
        employeeRepository.save(john);

        // Alice: allocated 25% on PRJ-001 -> available 75%
        allocationRepository.save(new Allocation(alice, p1, 25, LocalDate.now().minusDays(15), LocalDate.now().plusDays(75), AllocationStatus.ACTIVE));
        alice.setAllocatedCapacity(25);
        alice.setAvailableCapacity(75);
        employeeRepository.save(alice);

        // Carol: allocated 50% on PRJ-001 -> available 50%
        allocationRepository.save(new Allocation(carol, p1, 50, LocalDate.now().minusDays(10), LocalDate.now().plusDays(80), AllocationStatus.ACTIVE));
        carol.setAllocatedCapacity(50);
        carol.setAvailableCapacity(50);
        employeeRepository.save(carol);
    }
}
