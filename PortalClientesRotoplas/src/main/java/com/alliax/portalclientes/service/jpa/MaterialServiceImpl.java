package com.alliax.portalclientes.service.jpa;

import com.alliax.portalclientes.domain.Material;
import com.alliax.portalclientes.repository.MaterialRepository;
import com.alliax.portalclientes.service.MaterialService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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

    @Override
    public void loadFromFile(InputStream inputStream, String tipoMaterial) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        Material material = null;
        String[] materialData = null;

        List<Material> records = new ArrayList<>();

        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
            	material = new Material();
              
                materialData = line.split(cvsSplitBy);
                material.setSku(materialData[0]);

                if(materialData.length>1) {
                    material.setDescripcion(materialData[1]);
                }

                if(materialData.length>2) {
                    material.setUnidadMedida(materialData[2]);
                }

                if(materialData.length>3) {
                    material.setUrlFoto(materialData[3]);
                }
                material.setTipoMaterial(tipoMaterial);

                records.add(material);
            }

            if(records.size() > 0) {
                records.remove(0);
                for (Material mat :records) {
                    materialRepository.save(mat);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
