package com.alliax.portalclientes.repository;

import org.springframework.data.repository.CrudRepository;

import com.alliax.portalclientes.domain.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario,Integer> {

}
