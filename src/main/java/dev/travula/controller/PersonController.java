package dev.travula.controller;

import dev.travula.model.Person;
import dev.travula.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping
    public String people(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "people";
    }

    @GetMapping("/people")
    public String newPerson(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "person_add";
    }

    @PostMapping("/people")
    public String addPerson(@ModelAttribute("person") Person person) {
        personRepository.save(person);
        return "redirect:/";
    }

    @GetMapping("/people/edit/{id}")
    public String editPerson(@PathVariable Long id,Model model ) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        model.addAttribute("person", person);
        return "person_edit";
    }

    @PostMapping("/people/edit/{id}")
    public String editPerson(@PathVariable Long id,@ModelAttribute("person") Person person) {
        personRepository.save(person);
        return "redirect:/";
    }

    @GetMapping("/people/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return "redirect:/";
    }
}
