package ec.edu.ups.appMedica.business;

import java.util.List;

import ec.edu.ups.paw.appMedica.dao.HorarioDAO;
import ec.edu.ups.paw.appMedica.model.Horario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class HorarioON {

	@Inject
	private HorarioDAO daoHorario;
	
	
	public void guardarHorario(Horario h) {
		 if (h.getId() == null) {
			 daoHorario.insert(h);
		    } else {
		    	Horario aux = daoHorario.read(h.getId());
		        if (aux == null)
		        	daoHorario.insert(h);
		        else
		        	daoHorario.update(h);
		    }
	}

	public List<Horario> getHorarios(){
		return daoHorario.getAll();
	}
	
	
	public List<Horario> getHorarioPorIdMedico(Integer id) throws Exception {
		if (id == null || id <= 0) {
            throw new Exception("id paciente inválido");
        }
		return daoHorario.getHorarioPorIdMedico(id);
	}
	
	
	
	
	
}
