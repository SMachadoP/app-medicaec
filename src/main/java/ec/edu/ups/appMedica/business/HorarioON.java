package ec.edu.ups.appMedica.business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

import ec.edu.ups.paw.appMedica.dao.HorarioDAO;
import ec.edu.ups.paw.appMedica.dao.UsuarioDAO;
import ec.edu.ups.paw.appMedica.model.Horario;
import ec.edu.ups.paw.appMedica.model.Usuario;
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
	
	@Inject
	private UsuarioDAO usuarioDAO; // Asegúrate de tener esto, o inyecta desde donde obtienes los médicos

	public void generarHorariosPorDefecto() {
	    List<Usuario> medicos = usuarioDAO.getMedicos(); // Asumo que tienes este método

	    LocalDate hoy = LocalDate.now();
	    DayOfWeek[] diasSemana = {
	        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
	        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
	    };

	    for (Usuario medico : medicos) {
	        for (DayOfWeek dia : diasSemana) {
	            LocalDate fechaDia = hoy.with(TemporalAdjusters.nextOrSame(dia));
	            for (int hora = 8; hora < 18; hora++) {
	                LocalDateTime fechaHora = fechaDia.atTime(hora, 0);
	                Date fechaFinal = Date.from(fechaHora.atZone(ZoneId.systemDefault()).toInstant());

	                // Verificar si ya existe ese horario (puedes mejorar con una consulta específica)
	                boolean yaExiste = daoHorario.getHorarioPorIdMedico(medico.getId()).stream()
	                    .anyMatch(h -> h.getFecha().equals(fechaFinal));

	                if (!yaExiste) {
	                    Horario h = new Horario();
	                    h.setFecha(fechaFinal);
	                    h.setDisponible(true);
	                    h.setMedico(medico);
	                    daoHorario.insert(h);
	                }
	            }
	        }
	    }
	}

	 
	
	public List<Horario> getHorarioPorIdMedico(Integer id) throws Exception {
		if (id == null || id <= 0) {
            throw new Exception("id paciente inválido");
        }
		return daoHorario.getHorarioPorIdMedico(id);
	}	
}
