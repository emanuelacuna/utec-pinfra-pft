package edu.utec.loginPinfra;

import edu.utec.loginPinfra.model.Role;
import edu.utec.loginPinfra.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginPinfraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LoginPinfraApplication.class, args);
	}

	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

		Role admin = Role.builder().name("ROLE_ADMIN").build();
		Role user = Role.builder().name("ROLE_USER").build();

		if (roleRepository.findByName("ROLE_ADMIN") == null && roleRepository.findByName("ROLE_USER") == null) {
			roleRepository.save(admin);
			roleRepository.save(user);
		}

	}
}
