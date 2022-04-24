package com;

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
