package com.alliax.portalclientes.service;

import com.alliax.portalclientes.domain.Material;

import java.util.List;

public interface MaterialService {
    public List<Material> findAll();

    public Material findById(String noMaterial);

    public List<Material> findMaterialByNoMaterialAndDescripicon(String tipoMaterial, String noMaterial, String descipcion);

    public List<Material> findByTipoMaterial(String tipoMaterial);

    public Material save(Material material);
}
