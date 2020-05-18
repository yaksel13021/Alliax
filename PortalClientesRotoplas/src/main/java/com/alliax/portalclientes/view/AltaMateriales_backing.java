package com.alliax.portalclientes.view;

import com.alliax.portalclientes.domain.Material;
import com.alliax.portalclientes.model.AltaMaterial;
import com.alliax.portalclientes.service.MaterialService;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import java.io.IOException;


@ManagedBean(name="altaMat")
@ViewScoped
public class AltaMateriales_backing extends AbstractBacking{

    private final static Logger logger = Logger.getLogger(AltaMateriales_backing.class);

    MaterialService mtlServ = this.getSpringContext().getBean("materialService",MaterialService.class);

    private AltaMaterial altaMaterial = new AltaMaterial();
    private String noMaterial;
    private Material mat;
    private int tipoMessage;
    private Part file;
    private String tipoMAterial;
    private String update;


    public String busquedaMaterial(){
        try {
            if (this.getNoMaterial() != null && this.getNoMaterial() != ""){
                this.setMat(this.mtlServ.findById(noMaterial));
                tipoMessage = 1;
            }
            if (mat == null || mat.getSku() ==""){
                tipoMessage = 9;
            }
        }
        catch (Exception e){
            logger.info("error :"+e.getMessage());
            tipoMessage = 2;
        }
        return "";
    }

    public String actualizarMaterial(){
        try{
            tipoMessage = 0;
            String [] a = this.getUpdate().split(",");
            Material material = mtlServ.findById(noMaterial);
            material.setDescripcion(a[0]);
            material.setUnidadMedida(a[1]);
            material.setUrlFoto(a[2]);
            mtlServ.save(material);
            tipoMessage = 20;
        }catch (Exception e ){
            tipoMessage = 22;
        }
        return "";
    }

    public String guardarMaterial(){
        try{
            tipoMessage = 0;
            Material material = mtlServ.findById(this.getAltaMaterial().getSku());
            if (material != null){
                tipoMessage = 5;
            }else{
                material = new Material();
                material.setSku(this.getAltaMaterial().getSku());
                material.setUnidadMedida(this.getAltaMaterial().getUnidadMedida());
                material.setTipoMaterial(this.getAltaMaterial().getTipoMaterial());
                material.setDescripcion(this.getAltaMaterial().getDescripcion());
                material.setUrlFoto(this.getAltaMaterial().getUrlFoto());
                mtlServ.save(material);
                tipoMessage = 6;
            }
        }catch (Exception e ){
            tipoMessage = 4;
        }
        return "";
    }

    public String uploadExcel() throws IOException {
        try{
            tipoMessage = 0;
            mtlServ.loadFromFile(file.getInputStream(),this.getTipoMAterial());
            tipoMessage = 11;
        }catch (Exception e){
            logger.info("Error :"+e.getMessage());
            tipoMessage = 12;
        }
        return "";
    }

    public String getTipoMAterial() {
        return tipoMAterial;
    }

    public void setTipoMAterial(String tipoMAterial) {
        this.tipoMAterial = tipoMAterial;
    }

    public int getTipoMessage() {
        return tipoMessage;
    }

    public void setTipoMessage(int tipoMessage) {
        this.tipoMessage = tipoMessage;
    }

    public AltaMaterial getAltaMaterial() {
        return altaMaterial;
    }

    public void setAltaMaterial(AltaMaterial altaMaterial) {
        this.altaMaterial = altaMaterial;
    }

    public String getNoMaterial() {
        return noMaterial;
    }

    public void setNoMaterial(String noMaterial) {
        this.noMaterial = noMaterial;
    }

    public Material getMat() {
        return mat;
    }

    public void setMat(Material mat) {
        this.mat = mat;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
