package ec.edu.ups.appMedica.business;

import java.util.List;

import ec.edu.ups.paw.appMedica.dao.EspecialidadDAO;
import ec.edu.ups.paw.appMedica.model.Especialidad;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class EspecialidadON {

	@Inject
	private EspecialidadDAO daoEspecialidad;
	
	
	public void guardarEspecialidad(Especialidad e) {
		 if (e.getId() == null) {
			 daoEspecialidad.insert(e);
		    } else {
		    	Especialidad aux = daoEspecialidad.read(e.getId());
		        if (aux == null)
		        	daoEspecialidad.insert(e);
		        else
		        	daoEspecialidad.update(e);
		    }
	}

	public List<Especialidad> getEspecialidades(){
		return daoEspecialidad.getAll();
	}
	
	
	/*public List<Especialidad> getCitaPorIdPaciente(Integer id) throws Exception {
		if (id == null || id <= 0) {
            throw new Exception("id paciente inválido");
        }
		return daoEspecialidad.getCitaPorIdPaciente(id);
	}*/
	
	
	
	
	
}
