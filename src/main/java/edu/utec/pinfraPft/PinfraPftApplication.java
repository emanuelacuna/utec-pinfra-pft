package edu.utec.pinfraPft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.utec.pinfraPft.model.*;
import edu.utec.pinfraPft.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

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
	private final EventRepository eventRepository;
	private final AttendanceRepository attendanceRepository;

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

		try {
			// Leer el archivo JSON generado
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, String>> localidades = mapper.readValue(
					new File("resultado_ordenado.json"),
					new TypeReference<List<Map<String, String>>>() {}
			);

			// Map para agrupar localidades por departamento
			Map<String, List<String>> departamentos = new LinkedHashMap<>();

			for (Map<String, String> localidad : localidades) {
				String departamento = localidad.get("Departamento");
				String localidadNombre = localidad.get("Localidad");

				// Agrupar localidades por departamento
				departamentos.computeIfAbsent(departamento, k -> new ArrayList<>()).add(localidadNombre);
			}

			// Crear departamentos y localidades en base a los datos del JSON
			List<Department> departments = new ArrayList<>();
			List<Locality> localities = new ArrayList<>();

			departamentos.forEach((departamento, localidadesList) -> {
				Department department = Department.builder().name(departamento).build();
				departments.add(department);

				localidadesList.forEach(localidad -> {
					Locality locality = Locality.builder()
							.name(localidad)
							.department(department)
							.build();
					localities.add(locality);
				});
			});

			// Guardar los datos en la base de datos
			departmentRepository.saveAll(departments);
			localityRepository.saveAll(localities);

			System.out.println("Datos cargados exitosamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
//
		try {
			// Leer el archivo JSON desde la carpeta resources
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("departamentos_localidades.json");

			if (inputStream == null) {
				throw new FileNotFoundException("El archivo departamentos_localidades.json no se encuentra en resources.");
			}

			// Leer los datos del JSON
			List<Map<String, String>> localidades = mapper.readValue(
					inputStream,
					new TypeReference<List<Map<String, String>>>() {}
			);

			// Map para agrupar localidades por departamento
			Map<String, List<String>> departamentos = new LinkedHashMap<>();

			for (Map<String, String> localidad : localidades) {
				String departamento = localidad.get("Departamento");
				String localidadNombre = localidad.get("Localidad");

				// Agrupar localidades por departamento
				departamentos.computeIfAbsent(departamento, k -> new ArrayList<>()).add(localidadNombre);
			}

			// Crear departamentos y localidades en base a los datos del JSON
			List<Department> departments = new ArrayList<>();
			List<Locality> localities = new ArrayList<>();

			departamentos.forEach((departamento, localidadesList) -> {
				Department department = Department.builder().name(departamento).build();
				departments.add(department);

				localidadesList.forEach(localidad -> {
					Locality locality = Locality.builder()
							.name(localidad)
							.department(department)
							.build();
					localities.add(locality);
				});
			});

			// Guardar los datos en la base de datos
			departmentRepository.saveAll(departments);
			localityRepository.saveAll(localities);

			System.out.println("Datos cargados exitosamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Itr itrLaPaz = Itr.builder().name("ITR Sur Oeste - La Paz").locality(localityRepository.getById(271l)).build();
		Itr itrFrayBentos = Itr.builder().name("ITR Sur Oeste - Fray Bentos").locality(localityRepository.getById(907l)).build();
		Itr itrMercedes = Itr.builder().name("ITR Sur Oeste - Mercedes").locality(localityRepository.getById(1289l)).build();
		Itr itrNuevaHelvecia = Itr.builder().name("ITR Sur Oeste - Nueva Helvecia").locality(localityRepository.getById(285l)).build();
		Itr itrPaysandu = Itr.builder().name("ITR Sur Oeste - Paysandú").locality(localityRepository.getById(815l)).build();

		Itr itrMinas = Itr.builder().name("ITR Este - Minas").locality(localityRepository.getById(647l)).build();
		Itr itrMaldonado = Itr.builder().name("ITR Este - Maldonado").locality(localityRepository.getById(769l)).build();

		Itr itrSanJose = Itr.builder().name("ITR Centro-Sur - San José").locality(localityRepository.getById(1188l)).build();
		Itr itrDurazno = Itr.builder().name("ITR Centro-Sur - Durazno").locality(localityRepository.getById(403l)).build();

		Itr itrMelo = Itr.builder().name("ITR Norte - Melo").locality(localityRepository.getById(230l)).build();
		Itr itrRivera = Itr.builder().name("ITR Norte - Rivera").locality(localityRepository.getById(954l)).build();

		List<Itr> itrs = Arrays.asList(itrLaPaz, itrFrayBentos, itrMercedes, itrNuevaHelvecia, itrPaysandu, itrMinas, itrMelo, itrRivera, itrMaldonado, itrSanJose, itrDurazno);
		itrRepository.saveAll(itrs);
//
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
				.department(departmentRepository.getById(12l))
				.locality(localityRepository.getById(907l))
				.institutionalEmail("admin@utec.edu.uy")
				.itr(itrFrayBentos)
				.role(Arrays.asList(adminRole))
				.active(true)
				.build();

		userRepository.save(admin);

		Student student = Student.builder()
				.username("student")
				.password(passwordEncoder.encode("student"))
				.firstName("Jorge")
				.secondName("Luis")
				.firstSurname("Costa")
				.secondSurname("Perez")
				.document(41235517)
				.birthDate(LocalDate.of(2000, 2, 10))
				.personalEmail("jorge.costa@gmail.com")
				.phone(994321221)
				.department(departmentRepository.getById(12l))
				.locality(localityRepository.getById(907l))
				.itr(itrFrayBentos)
				.role(Arrays.asList(studentRole))
				.active(true)
				.institutionalEmail("jorge.costa@estudiantes.utec.edu.uy")
				.gender("Masculino")
				.generation(2023)
				.build();
		userRepository.save(student);

		Teacher teacher = Teacher.builder()
				.username("teacher")
				.password(passwordEncoder.encode("teacher"))
				.firstName("Maria")
				.secondName("Claudia")
				.firstSurname("Silva")
				.secondSurname("Lopez")
				.document(41245514)
				.birthDate(LocalDate.of(1990, 7, 12))
				.personalEmail("maria.silva@gmail.com")
				.phone(994441221)
				.department(departmentRepository.getById(12l))
				.locality(localityRepository.getById(907l))
				.itr(itrFrayBentos)
				.role(Arrays.asList(teacherRole))
				.active(true)
				.institutionalEmail("maria.silva@utec.edu.uy")
				.gender("Femenino")
				.teacherRole("Teacher")
				.area("LTI")
				.build();
		userRepository.save(teacher);


		List<UserEntity> teachers = new ArrayList<>();
		teachers.add(teacher);

		Event bantotal = Event.builder().eventType("Convocatoria VME").itr(itrFrayBentos).mode("Presencial").location("Sala de Conferencias").endingDate(LocalDateTime.of(2024, Month.DECEMBER, 17, 12, 0)).startingDate(LocalDateTime.of(2024, Month.DECEMBER, 17, 9, 0)).status("Futuro").title("Bantotal").teachers(teachers).build();
		Event cigras =	Event.builder().eventType("Convocatoria VME").itr(itrFrayBentos).mode("Virtual").location("Virtual").endingDate(LocalDateTime.of(2024, Month.DECEMBER, 25, 12, 0)).startingDate(LocalDateTime.of(2024, Month.DECEMBER, 17, 9, 0)).status("Futuro").title("CIGRAS").teachers(teachers).build();

		List<Event> events = Arrays.asList(bantotal,cigras);
		eventRepository.saveAll(events);

		Attendance convocatoria = Attendance.builder().event(bantotal).status("Asistencia").student(student).qualification(4).build();
		attendanceRepository.save(convocatoria);
	}
}
