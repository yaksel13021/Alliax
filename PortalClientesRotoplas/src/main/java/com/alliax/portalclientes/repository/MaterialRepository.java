package com.alliax.portalclientes.repository;

import com.alliax.portalclientes.domain.Material;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MaterialRepository extends CrudRepository<Material,String> {
	
	List<Material> findByTipoMaterialOrderByDescripcionAsc(String tipoMaterial);

	List<Material> findByTipoMaterialAndSkuContainsOrDescripcionContainsOrderByDescripcionAsc(String tipoMaterial, String sku, String descripcion);

}
