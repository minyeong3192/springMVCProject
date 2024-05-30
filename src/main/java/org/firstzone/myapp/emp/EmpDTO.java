package org.firstzone.myapp.emp;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


// VO(Value Object)
// 데이터를 전달하는 역할
// DTO(Data Transfer Object)
// JavaBeans기술에서 이용(JSP, Spring, Mybatis)

@Getter@Setter//java beans기술에서 필수
@ToString
@AllArgsConstructor //java beans기술에서 필수
@NoArgsConstructor 
public class EmpDTO {

	private int employee_id;
	private String first_name;
	private String last_name;
	private String email;
	private String phone_number;
	private Date hire_date;
	private String job_id;
	private Integer salary;
	private Double commission_pct;
	private Integer manager_id;
	private Integer department_id;
}