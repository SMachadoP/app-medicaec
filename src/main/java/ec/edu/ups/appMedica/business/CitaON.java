package ec.edu.ups.appMedica.business;

import java.util.List;

import ec.edu.ups.paw.appMedica.dao.CitaDAO;
import ec.edu.ups.paw.appMedica.dao.EspecialidadDAO;
import ec.edu.ups.paw.appMedica.dao.HorarioDAO;
import ec.edu.ups.paw.appMedica.dao.UsuarioDAO;
import ec.edu.ups.paw.appMedica.model.Cita;
import ec.edu.ups.paw.appMedica.model.Especialidad;
import ec.edu.ups.paw.appMedica.model.Horario;
import ec.edu.ups.paw.appMedica.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class CitaON {

	@Inject
	private CitaDAO daoCita;
	@Inject
	private HorarioDAO daoHorario;

	@Inject
	private UsuarioDAO daoUsuario;

	@Inject
	private EspecialidadDAO daoEspecialidad;

	
	
	/*public void guardarCita(Cita c) {
		 if (c.getId() == null) {
			 daoCita.insert(c);
		    } else {
		        Cita aux = daoCita.read(c.getId());
		        if (aux == null)
		        	daoCita.insert(c);
		        else
		        	daoCita.update(c);
		    }
	}*/
	
	public void guardarCita(Cita c) throws Exception {
	    if (c == null) {
	        throw new Exception("Cita nula");
	    }

	    if (c.getEspecialidad() == null || c.getEspecialidad().getId() == null ||
	        c.getHorario() == null || c.getHorario().getId() == null ||
	        c.getMedico() == null || c.getMedico().getId() == null ||
	        c.getPaciente() == null || c.getPaciente().getId() == null) {
	        throw new Exception("Faltan datos obligatorios para guardar la cita");
	    }

	    // Recuperar las entidades desde la base de datos
	    Especialidad especialidad = daoEspecialidad.read(c.getEspecialidad().getId());
	    Horario horario = daoHorario.read(c.getHorario().getId());
	    Usuario medico = daoUsuario.read(c.getMedico().getId());
	    Usuario paciente = daoUsuario.read(c.getPaciente().getId());

	    if (especialidad == null || horario == null || medico == null || paciente == null) {
	        throw new Exception("Una de las entidades relacionadas no existe en la base de datos");
	    }

	    c.setEspecialidad(especialidad);
	    c.setHorario(horario);
	    c.setMedico(medico);
	    c.setPaciente(paciente);

	    if (c.getEstado() == null) {
	        c.setEstado("pendiente");
	    }

	    if (c.getId() == null) {
	        daoCita.insert(c);
	    } else {
	        Cita aux = daoCita.read(c.getId());
	        if (aux == null) {
	            daoCita.insert(c);
	        } else {
	            daoCita.update(c);
	        }
	    }
	}




	public List<Cita> getCitas(){
		return daoCita.getAll();
	}
	
	
	public List<Cita> getCitaPorIdPaciente(Integer id) throws Exception {
		if (id == null || id <= 0) {
            throw new Exception("id paciente inválido");
        }
		return daoCita.getCitaPorIdPaciente(id);
	}

}
