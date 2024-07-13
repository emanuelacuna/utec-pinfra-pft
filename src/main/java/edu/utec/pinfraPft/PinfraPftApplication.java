package edu.utec.pinfraPft;

import edu.utec.pinfraPft.model.*;
import edu.utec.pinfraPft.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PinfraPftApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final DepartmentRepository departmentRepository;

	private final LocalityRepository localityRepository;

	private final ItrRepository itrRepository;

	private final ClaimRepository claimRepository;

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

		Department department1 = Department.builder().name("Departamento de prueba 1").build();
		Department department2 = Department.builder().name("Departamento de prueba 1").build();
		List<Department> departments = Arrays.asList(department1, department2);
		departmentRepository.saveAll(departments);

		Locality locality1 = Locality.builder().name("Localidad de prueba 1").build();
		Locality locality2 = Locality.builder().name("Localidad de prueba 2").build();
		List<Locality> localities = Arrays.asList(locality1, locality2);
		localityRepository.saveAll(localities);

		Itr itr1 = Itr.builder().name("Itr de prueba 1").build();
		Itr itr2 = Itr.builder().name("Itr de prueba 2").build();
		List<Itr> itrs = Arrays.asList(itr1, itr2);
		itrRepository.saveAll(itrs);

		UserEntity admin = UserEntity.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.firstName("admin")
				.secondName("admin")
				.firstSurname("admin")
				.secondSurname("admin")
				.document(12345678)
				.birthDate(LocalDate.of(2001, 3, 30))
				.personalEmail("admin@gmail.com")
				.phone(123456789)
				.department(department1)
				.locality(locality1)
				.institutionalEmail("admin@utec.edu.uy")
				.itr(itr1)
				.role(Arrays.asList(adminRole))
				.active(true)
				.build();

		userRepository.save(admin);

	}
}
