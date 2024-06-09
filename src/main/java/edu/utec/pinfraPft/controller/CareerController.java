package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.CareerDto;
import edu.utec.pinfraPft.model.Career;
import edu.utec.pinfraPft.service.CareerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/career")
public class CareerController {

    private CareerService careerService;

    @Autowired
    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping
    public String getCareers(Model model) {
        List<CareerDto> careers = careerService.getCareers();
        model.addAttribute("careers", careers);
        return "career-list";
    }

    @GetMapping("/create")
    public String createCareerForm(Model model) {
        Career career = new Career();
        model.addAttribute("career", career);
        return "career-create";
    }

    @PostMapping("/create")
    public String createCareer(@Valid @ModelAttribute("career") CareerDto careerDto,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("career", careerDto);
            return "career-create";
        }
        careerService.createCareer(careerDto);
        return "redirect:/career";
    }

    @GetMapping("/update/{id}")
    public String updateCareerForm(@PathVariable("id") Long id, Model model) {
        CareerDto careerDto = careerService.getCareerByID(id);
        model.addAttribute("career", careerDto);
        return "career-update";
    }

    @PostMapping("/update/{id}")
    public String updateCareer(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("career") CareerDto careerDto,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("career", careerDto);
            return "career-update";
        }
        careerDto.setId(id);
        careerService.updateCareer(careerDto);
        return "redirect:/career";
    }

    @GetMapping("/delete/{id}")
    public String deleteCareer(@PathVariable("id") Long id) {
        careerService.deleteCareer(id);
        return "redirect:/career";
    }

    @GetMapping("/filter")
    public String filterCareer(@RequestParam(value = "query") String query,
                               Model model) {
        List<CareerDto> careers = careerService.filterCareer(query);
        model.addAttribute("careers", careers);
        return "career-list";
    }

}
