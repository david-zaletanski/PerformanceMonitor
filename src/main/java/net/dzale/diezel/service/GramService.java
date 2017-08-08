package net.dzale.diezel.service;

import net.dzale.diezel.model.database.GramEntity;
import net.dzale.diezel.model.grams.Gram;
import net.dzale.diezel.repository.GramRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service methods for performing common operations on Grams.
 *
 * @drzale
 */
@Service
public class GramService {

    @Autowired
    private GramRepository gramRepository;
    //@Autowired
    //private ModelMapper modelMapper;

    public GramEntity addGram(String body) {
        ModelMapper modelMapper = new ModelMapper();
        Gram gram = new Gram(body);
        GramEntity gramEntity = modelMapper.map(gram, GramEntity.class);
        gramEntity = gramRepository.save(gramEntity);
        return gramEntity;
    }

    public Gram getGramById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        GramEntity gramEntity = gramRepository.findOne(id);
        Gram gram = modelMapper.map(gramEntity, Gram.class);
        return gram;
    }
}
