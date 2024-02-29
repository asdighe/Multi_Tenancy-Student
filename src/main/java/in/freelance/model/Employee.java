package in.freelance.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @Column(name = "empid")
    private int empId;

    @Column(name = "empfullname")
    private String empFullName;

    @Column(name = "empdepartment")
    private String empDepartment;

    @Column(name = "empmanager")
    private String empManager;

    @Column(name = "empcity")
    private String empCity;


}
