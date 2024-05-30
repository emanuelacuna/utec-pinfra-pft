package edu.utec.loginPinfra.service;

import edu.utec.loginPinfra.dto.CareerDto;
import edu.utec.loginPinfra.model.Career;
import edu.utec.loginPinfra.repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerServiceImpl implements CareerService {

    private CareerRepository careerRepository;

    @Autowired
    public CareerServiceImpl(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @Override
    public CareerDto createCareer(CareerDto careerDto) {

        Career career = new Career();
        career.setName(careerDto.getName());

        Career saveCareer = careerRepository.save(career);

        CareerDto careerResponse = new CareerDto();
        careerResponse.setId(saveCareer.getId());
        careerResponse.setName(saveCareer.getName());

        return careerResponse;
    }

    @Override
    public List<CareerDto> getCareers() {

        List<Career> careers = careerRepository.findAll();

        return careers.stream().map((career) -> mapToDTO(career)).collect(Collectors.toList());
    }

    @Override
    public CareerDto getCareerByID(Long id) {

        Career career = careerRepository.findById(id).get();

        return mapToDTO(career);
    }

    @Override
    public void updateCareer(CareerDto careerDto) {

        Career career = mapToEntity(careerDto);

        careerRepository.save(career);
    }

    @Override
    public void deleteCareer(Long id) {
        careerRepository.deleteById(id);
    }

    @Override
    public List<CareerDto> filterCareer(String query) {

        List<Career> careers = careerRepository.filterCareer(query);

        return careers.stream().map(career -> mapToDTO(career)).collect(Collectors.toList());
    }

    //Mapper Career to DTO
    private CareerDto mapToDTO(Career career) {

        CareerDto careerDto = new CareerDto();
        careerDto.setId(career.getId());
        careerDto.setName(career.getName());

        return careerDto;
    }

    //Mapper DTO to Entity
    private Career mapToEntity(CareerDto careerDto) {

        Career career = new Career();
        career.setId(careerDto.getId());
        career.setName(careerDto.getName());

        return career;
    }

}
