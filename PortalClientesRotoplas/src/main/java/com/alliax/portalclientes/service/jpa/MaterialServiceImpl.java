package com.alliax.portalclientes.service.jpa;

import com.alliax.portalclientes.domain.Material;
import com.alliax.portalclientes.repository.MaterialRepository;
import com.alliax.portalclientes.service.MaterialService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("materialService")
@Repository
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final static Logger logger = Logger.getLogger(MaterialServiceImpl.class);

    @Autowired
    public MaterialRepository materialRepository;

    @Override
    public List<Material> findAll() {
        return (List<Material>)materialRepository.findAll();
    }

    @Override
    public Material findById(String noMaterial) {
        return materialRepository.findOne(noMaterial);
    }

    @Override
    public Material save(Material material) {
        material = materialRepository.save(material);

        return material;
    }

    @Override
    public List<Material> findMaterialByNoMaterialAndDescripicon(String tipoMaterial, String noMaterial, String descipcion) {

        return materialRepository.findByTipoMaterialAndSkuContainsOrDescripcionContainsOrderByDescripcionAsc(tipoMaterial, noMaterial, descipcion);
    }

    @Override
    public List<Material> findByTipoMaterial(String tipoMaterial) {
        return materialRepository.findByTipoMaterialOrderByDescripcionAsc(tipoMaterial);
    }

}
