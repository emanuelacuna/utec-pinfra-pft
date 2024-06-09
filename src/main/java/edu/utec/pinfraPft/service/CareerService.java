package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.CareerDto;

import java.util.List;

public interface CareerService {

    CareerDto createCareer(CareerDto careerDto);

    List<CareerDto> getCareers();

    CareerDto getCareerByID(Long id);

    void updateCareer(CareerDto careerDto);

    void deleteCareer(Long id);

    List<CareerDto> filterCareer(String query);

}
