package org.firstzone.myapp.emp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
// VO(Value Object)
// 데이터를 전달하는 역할
// DTO(Data Transfer Object)
// JavaBeans기술에서 이용(JSP, Spring, Mybatis)

@Getter@Setter@ToString @NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

	private String job_id;
	private String job_title;
	private int min_salary;
	private int max_salary;
	
}