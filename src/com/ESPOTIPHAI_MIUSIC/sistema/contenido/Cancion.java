package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;

public class Cancion {
	private EstadoCancion estado;
	private Boolean reproducible;
	private Integer num_reprod;
	
	public Cancion(EstadoCancion estado,Boolean reproducible ) {
		this.estado = estado;
		this.reproducible = reproducible;
	}
	
	Status reproducir() {
		return Status.OK;
	}


	Status reportarPlagio() {
		return Status.OK;
	}
	
	
	Status validarCancion() {
		return Status.OK;
	}
	
	
	Status cqancionRechazada() {
		return Status.OK;
	}
	
	
	Status cancionCorregida() {
		return Status.OK;
	}
	
	
	

}
