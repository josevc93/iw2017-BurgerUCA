package com.proyecto;

//import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.User.User;
import com.proyecto.User.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest


public class VaadinWithJpaApplicationTests {
	
	@Autowired
	private UserService service;
	
	//@Autowired
	//private User usuario;
	
	//@SuppressWarnings("deprecation")
	@Test
	public void testFindAll(){
		System.out.println();
		long count = service.size();
		
		System.out.println("Inicialmente tengo "+ count + "usuarios");
		
		List<User> data = Arrays.asList( new User("pedro", "sanchez", "perico", "555", "789", "", "Gerente", "", null, ""));
		
		for(User u: data){
			service.save(u);
		}
		
		System.out.println("Añado "+ data.size() + "usuarios");
		System.out.println("Obtengo "+ service.size() + "usuarios");
		
		System.out.println();
		
		Assert.assertEquals(service.size(), count + data.size());
		
	}

}
