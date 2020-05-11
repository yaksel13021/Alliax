package com.alliax.portalclientes.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PedidoPartidasPK implements Serializable {
    @Column(name = "idPedido")
    private long idPedido;
    @Column(name = "sku")
    private String sku;

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoPartidasPK)) return false;
        PedidoPartidasPK that = (PedidoPartidasPK) o;
        return Objects.equals(getIdPedido(), that.getIdPedido()) &&
                Objects.equals(getSku(), that.getSku());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPedido(), getSku());
    }
}
