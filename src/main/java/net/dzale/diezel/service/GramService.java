package net.dzale.diezel.service;

import net.dzale.diezel.model.entity.GramEntity;
import net.dzale.diezel.model.grams.Gram;
import net.dzale.diezel.repository.GramRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Service methods for performing common operations on Grams.
 *
 * @drzale
 */
@Service
public class GramService {
    public static final int DEFAULT_GRAMS_PER_PAGE = 15;

    @Autowired
    private GramRepository gramRepository;

    private ModelMapper modelMapper;

    public GramService() {
        modelMapper = new ModelMapper();
    }

    public GramEntity addGram(String body) {
        Gram gram = new Gram(body);
        GramEntity gramEntity = modelMapper.map(gram, GramEntity.class);
        gramEntity = gramRepository.save(gramEntity);
        return gramEntity;
    }

    public Gram getGramById(Long id) {
        GramEntity gramEntity = gramRepository.findOne(id);
        Gram gram = modelMapper.map(gramEntity, Gram.class);
        return gram;
    }

    public List<Gram> getGrams() {
        List<GramEntity> gramEntities = gramRepository.findAll();
        Type listType = new TypeToken<List<Gram>>() {
        }.getType();
        List<Gram> grams = modelMapper.map(gramEntities, listType);
        return grams;
        //return getGrams(0, DEFAULT_GRAMS_PER_PAGE);
    }

    /*public List<Gram> getGrams(int page, int gramsPerPage) {
        // Query repository for GramEntities.
        Pageable pageable = new PageRequest(page, gramsPerPage, Sort.Direction.DESC, "created", "modified");
        List<GramEntity> gramEntities = gramRepository.findAll(pageable);

        // Convert from GramEntity list to Gram list...
        // Using Type definition so that ModelMapper knows it's a list of Gram and not Object at runtime.
        Type listType = new TypeToken<List<Gram>>() {}.getType();
        List<Gram> grams = modelMapper.map(gramEntities, listType);

        return  grams;
    }*/
}
