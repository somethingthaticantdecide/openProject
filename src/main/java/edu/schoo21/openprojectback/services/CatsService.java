package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.exceptions.NotFoundException;
import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.CatDto;
import edu.schoo21.openprojectback.repository.CatRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CatsService {
    private final CatRepository catRepository;

    public CatsService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public void save(Cat cat) {
        catRepository.saveAndFlush(cat);
    }

    public Cat findById(Long id) {
        return catRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Collection<Cat> findAll() {
        return catRepository.findAll();
    }

    public Cat addNewCat(CatDto catDto) {
        return catRepository.save(new Cat(catDto.getName(), catDto.getSex(), catDto.getBreed(),
                catDto.getAge(), catDto.getPrice(), catDto.getPassport(), catDto.getVaccination(),
                catDto.getCertificates(), catDto.getInfo(), catDto.getPhoto()));
    }

    public Cat update(CatDto catDto, String id) {
        Cat cat = catRepository.findById(Long.parseLong(id)).orElseThrow(NotFoundException::new);
        cat.setName(catDto.getName());
        cat.setSex(catDto.getSex());
        cat.setBreed(catDto.getBreed());
        cat.setAge(catDto.getAge());
        cat.setPrice(catDto.getPrice());
        cat.setPassport(catDto.getPassport());
        cat.setVaccination(catDto.getVaccination());
        cat.setCertificates(catDto.getCertificates());
        cat.setInfo(catDto.getInfo());
        cat.setPhoto(catDto.getPhoto());
        return catRepository.saveAndFlush(cat);
    }

    public void deleteById(Long id) {
        catRepository.deleteById(id);
    }
}
