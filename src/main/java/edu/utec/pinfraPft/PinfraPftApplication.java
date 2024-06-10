package edu.utec.pinfraPft;

import edu.utec.pinfraPft.model.Department;
import edu.utec.pinfraPft.model.Itr;
import edu.utec.pinfraPft.model.Locality;
import edu.utec.pinfraPft.model.Role;
import edu.utec.pinfraPft.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor
public class PinfraPftApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final DepartmentRepository departmentRepository;

	private final LocalityRepository localityRepository;

	private final ItrRepository itrRepository;

	public static void main(String[] args) {
		SpringApplication.run(edu.utec.pinfraPft.PinfraPftApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		Role adminRole = Role.builder().name("ROLE_ADMIN").build();
		Role studentRole = Role.builder().name("ROLE_STUDENT").build();
		Role teacherRole = Role.builder().name("ROLE_TEACHER").build();

		if(roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
			roleRepository.save(adminRole);
		}
		if(roleRepository.findByName("ROLE_TEACHER").isEmpty()) {
			roleRepository.save(teacherRole);
		}
		if(roleRepository.findByName("ROLE_STUDENT").isEmpty()) {
			roleRepository.save(studentRole);
		}

		Department department = Department.builder().name("Departamento de prueba").build();
		departmentRepository.save(department);

		Locality locality = Locality.builder().name("Localidad de prueba").build();
		localityRepository.save(locality);

		Itr itr = Itr.builder().name("Itr de prueba").build();
		itrRepository.save(itr);

	}
}
