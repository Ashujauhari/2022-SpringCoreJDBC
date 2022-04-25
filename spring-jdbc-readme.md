# 2022-SpringJDBCTemplate
## Problem Statement:
1. Lot of biolerplate code
2. Pay attention to Opening / Closing of connection
3. Handling SQL Exception 
4. Handle transactions


## Apraches to JDBC Template
1. JdbcTemplate
2. NamedParameterJdbcTemplate
3. SimpleJdbcTemplate
4. SimpleJdbcInsert and SimpleJdbcCall

##Example:
1. Create maven project

2. Update Pom.xml to add following dependencies:
a. spring-core
b. spring-context
c. mysql-connector-java
d. spring-jdbc

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

	<modelVersion>4.0.0</modelVersion>
  	<groupId>com.spring</groupId>
  	<artifactId>20-springcore-emp-jdbc</artifactId>
  	<version>1.0.0</version>
  	<packaging>jar</packaging>

  	<name>Spring4HelloWorldExample</name>

 	<properties>
		<springframework.version>4.0.6.RELEASE</springframework.version>
	</properties>

	 <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>${springframework.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>${springframework.version}</version>
		</dependency>

    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.11</version>
</dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.1.1.RELEASE</version>
</dependency>
    
  </dependencies>
  <build>
    <finalName>spring-emp-jdbc</finalName>
  </build>
</project>


3. Create Emp pojo in com.beans package
package com.beans;  
  
public class Emp {  
private int id;  
private String name;  
private float salary;  
private String designation;  
  
public int getId() {  
    return id;  
}  
public void setId(int id) {  
    this.id = id;  
}  
public String getName() {  
    return name;  
}  
public void setName(String name) {  
    this.name = name;  
}  
public float getSalary() {  
    return salary;  
}  
public void setSalary(float salary) {  
    this.salary = salary;  
}  
public String getDesignation() {  
    return designation;  
}  
public void setDesignation(String designation) {  
    this.designation = designation;  
}
@Override
public String toString() {
	return "Emp [id=" + id + ", name=" + name + ", salary=" + salary + ", designation=" + designation + "]";
}

  
}  
4. Create Benas.xml in src folder

<?xml version="1.0" encoding="UTF-8"?>  
<beans  
    xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
    xmlns:p="http://www.springframework.org/schema/p"  
    xsi:schemaLocation="http://www.springframework.org/schema/beans  
                http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">  
<bean id="ds" class="org.springframework.jdbc.datasource.DriverManagerDataSource">  
<property name="driverClassName" value="com.mysql.jdbc.Driver"></property> 
<property name="url" value="jdbc:mysql://localhost:3306/test?useSSL=false&amp;allowPublicKeyRetrieval=true"></property>  
<property name="username" value="root"></property>  
<property name="password" value="root"></property>  
</bean>  

<bean id="jt" class="org.springframework.jdbc.core.JdbcTemplate">
<property name="dataSource" ref="ds"></property>
</bean>

<bean id="daoBean" class="com.dao.EmpDao">
<property name="template" ref="jt"></property>
</bean>  
</beans>  

4. Create EmpDao Class for crud operation
package com.dao;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.util.List;  
import org.springframework.jdbc.core.BeanPropertyRowMapper;  
import org.springframework.jdbc.core.JdbcTemplate;  
import org.springframework.jdbc.core.RowMapper;

import com.beans.Emp;  
  
public class EmpDao {  
JdbcTemplate template;  
  
public void setTemplate(JdbcTemplate template) {  
    this.template = template;  
}  

//To Save the employee record;
public int save(Emp p){  
    String sql="insert into Emp(name,salary,designation) values('"+p.getName()+"',"+p.getSalary()+",'"+p.getDesignation()+"')";  
    return template.update(sql);  
}  
//To To update the employee details
public int update(Emp p){  
    String sql="update Emp set name='"+p.getName()+"', salary="+p.getSalary()+",designation='"+p.getDesignation()+"' where id="+p.getId()+"";  
    return template.update(sql);  
}  

//To delete the employee details
public int delete(int id){  
    String sql="delete from Emp where id="+id+"";  
    return template.update(sql);  
}  

// To get an employee by ID
public Emp getEmpById(int id){  
    String sql="select * from Emp where id=?";  
    return template.queryForObject(sql, new Object[]{id},new BeanPropertyRowMapper<Emp>(Emp.class));  
}  

//To get all employee details
public List<Emp> getEmployees(){  
    return template.query("select * from Emp",new RowMapper<Emp>(){  
        public Emp mapRow(ResultSet rs, int row) throws SQLException {  
            Emp e=new Emp();  
            e.setId(rs.getInt(1));  
            e.setName(rs.getString(2));  
            e.setSalary(rs.getFloat(3));  
            e.setDesignation(rs.getString(4));  
            return e;  
        }  
    });  
}  
}  

5. Create App.javapackage com;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.beans.Emp;
import com.dao.EmpDao;
public class AppMain {
	public static void main(String args[]) {
      ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		 EmpDao empObj = (EmpDao) context.getBean("daoBean");
		 
		 int ch=0;
		 while(ch!=3)
		 {
			System.out.println("1. Get all Employees");
			System.out.println(" 2. Get the first record");
			Scanner sc= new Scanner(System.in);
			ch= sc.nextInt();
			switch (ch)
			{
			case 1:
			{
				List<Emp> empList= empObj.getEmployees();
				System.out.println(empList.size());
				Iterator<Emp> it = empList.iterator();
				while(it.hasNext())
				{
					System.out.println(it.next());
				}
				
				
				break;
			}
		   }//switch
		}//while
	}
}





