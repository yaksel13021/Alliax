package com.alliax.portalclientes.service;

import java.util.List;

import com.alliax.portalclientes.domain.Usuario;


public interface UsuarioService {
			
	public Usuario findByUserName(String usuario);
	
	public Usuario findByUsrPwd(String usuario, String password);
	
	public List<Usuario> findByClientNumber(String noCliente);
	
	public Usuario findById(int idUsuario);
	
	public Usuario save(Usuario usuario);
	
	public List<Usuario> findAll();
			
}
