package edu.utec.loginPinfra;

import edu.utec.loginPinfra.model.Career;
import edu.utec.loginPinfra.model.Role;
import edu.utec.loginPinfra.model.Student;
import edu.utec.loginPinfra.model.UserEntity;
import edu.utec.loginPinfra.repository.CareerRepository;
import edu.utec.loginPinfra.repository.RoleRepository;
import edu.utec.loginPinfra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class LoginPinfraApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public RoleRepository roleRepository;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Autowired
	public CareerRepository careerRepository;

	public static void main(String[] args) {
		SpringApplication.run(LoginPinfraApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		//Create roles if they don't exist
		Role adminRole = Role.builder().name("ROLE_ADMIN").build();
		Role userRole = Role.builder().name("ROLE_USER").build();
		Role studentRole = Role.builder().name("ROLE_STUDENT").build();

		if(roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
			roleRepository.save(adminRole);
		}
		if(roleRepository.findByName("ROLE_USER").isEmpty()) {
			roleRepository.save(userRole);
		}
		if(roleRepository.findByName("ROLE_STUDENT").isEmpty()) {
			roleRepository.save(studentRole);
		}

		//Create admin if it doesn't exist
		UserEntity admin = UserEntity.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.name("Administrador")
				.surname("UTEC")
				.email("admin@utec.edu.uy")
				.role(Arrays.asList(adminRole))
				.birthDate(LocalDate.of(2024, 5, 22))
				.address("Fray Bentos")
				.active(true)
				.build();

		userRepository.save(admin);

		//Create user if it doesn't exist
		UserEntity user = UserEntity.builder()
				.username("user")
				.password(passwordEncoder.encode("user"))
				.name("Usuario")
				.surname("UTEC")
				.email("user@utec.edu.uy")
				.role(Arrays.asList(userRole))
				.birthDate(LocalDate.of(2000, 2, 28))
				.address("Fray Bentos")
				.active(false)
				.build();

		userRepository.save(user);

		//Create career if it doesn't exist
		List<String> careers = Arrays.asList(
				"Technologist in Dairy Production Systems Management",
				"Technologist in Systems Analysis and Development",
				"Chemical Technologist",
				"Industrial Mechanical Technologist",
				"Information Technology Technologist",
				"Environmental Control Technologist",
				"Bachelor's Degree in Jazz and Creative Music",
				"Bachelor's Degree in Food Analysis",
				"Bachelor's Degree in Dairy Science and Technology",
				"Bachelor's Degree in Information Technologies",
				"Bachelor's Degree in Data Engineering and Artificial Intelligence",
				"Engineering in Renewable Energies",
				"Engineering in Water and Sustainable Development",
				"Engineering in Logistics",
				"Engineering in Mechatronics",
				"Engineering in Control and Automation",
				"Biomedical Engineering",
				"Agro-Environmental Engineering"
		);

		List<Career> careersList = careers.stream()
				.map(Career::new)
				.toList();

		careerRepository.saveAll(careersList);

		//Create student if it doesn't exist
		UserEntity student = Student.builder()
				.username("student")
				.password(passwordEncoder.encode("student"))
				.name("Estudiante")
				.surname("UTEC")
				.email("student@utec.edu.uy")
				.role(Arrays.asList(studentRole))
				.birthDate(LocalDate.of(2005, 3, 20))
				.address("Fray Bentos")
				.studentNumber(123456L)
				.career(careerRepository.findById(1L).get())
				.active(false)
				.build();

		userRepository.save(student);
	}
}
