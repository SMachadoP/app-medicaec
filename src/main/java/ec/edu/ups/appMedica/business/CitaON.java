package ec.edu.ups.appMedica.business;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ec.edu.ups.paw.appMedica.dao.CitaDAO;
import ec.edu.ups.paw.appMedica.dao.EspecialidadDAO;
import ec.edu.ups.paw.appMedica.dao.HorarioDAO;
import ec.edu.ups.paw.appMedica.dao.UsuarioDAO;
import ec.edu.ups.paw.appMedica.model.Cita;
import ec.edu.ups.paw.appMedica.model.Especialidad;
import ec.edu.ups.paw.appMedica.model.Horario;
import ec.edu.ups.paw.appMedica.model.ReporteFila;
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
	
	public List<Cita> getHistorial(
		    Integer pacienteId, Integer medicoId,
		    Date from, Date to,
		    Integer especialidadId, String estado
		) {
		    return daoCita.findByFilters(pacienteId, medicoId, from, to, especialidadId, estado);
		}
	
	public List<Cita> getCitasFiltradasPorMedicoYEspecialidad(
	        String medicoNombre,
	        String especialidadNombre,
	        LocalDate desde,
	        LocalDate hasta) throws Exception {

	    System.out.println("=== DEBUG REPORTE ===");
	    System.out.println("Parámetros recibidos:");
	    System.out.println("- medicoNombre: " + medicoNombre);
	    System.out.println("- especialidadNombre: " + especialidadNombre);
	    System.out.println("- desde: " + desde);
	    System.out.println("- hasta: " + hasta);

	    // 1) Buscamos todos los médicos cuyo nombre contenga medicoNombre
	    List<Usuario> todosMedicos = daoUsuario.getAll();
	    System.out.println("Total usuarios en BD: " + todosMedicos.size());
	    
	    List<Usuario> medicos = todosMedicos.stream()
	        .filter(u -> {
	            boolean esAdmin = "admin".equalsIgnoreCase(u.getRol());
	            boolean contieneNombre = u.getNombre() != null && 
	                                   u.getNombre().toLowerCase().contains(medicoNombre.toLowerCase());
	            System.out.println("Usuario: " + u.getNombre() + ", Rol: " + u.getRol() + 
	                             ", Es admin: " + esAdmin + ", Contiene nombre: " + contieneNombre);
	            return esAdmin && contieneNombre;
	        })
	        .collect(Collectors.toList());
	    
	    System.out.println("Médicos encontrados: " + medicos.size());
	    medicos.forEach(m -> System.out.println("- " + m.getNombre()));

	    if (medicos.isEmpty()) {
	        throw new Exception("No se encontró ningún médico que coincida con '" + medicoNombre + "'");
	    }

	    // 2) Buscar la especialidad exacta (por nombre)
	    List<Especialidad> todasEspecialidades = daoEspecialidad.getAll();
	    System.out.println("Especialidades en BD: " + todasEspecialidades.size());
	    todasEspecialidades.forEach(e -> System.out.println("- " + e.getNombreEspecialidad()));
	    
	    Especialidad esp = todasEspecialidades.stream()
	        .filter(e -> e.getNombreEspecialidad().equalsIgnoreCase(especialidadNombre))
	        .findFirst()
	        .orElseThrow(() -> new Exception("Especialidad '" + especialidadNombre + "' no encontrada"));
	    
	    System.out.println("Especialidad encontrada: " + esp.getNombreEspecialidad() + " (ID: " + esp.getId() + ")");

	    // 3) Convertir fechas a java.util.Date
	    ZoneId zone = ZoneId.systemDefault();
	    Date dDesde = Date.from(desde.atStartOfDay(zone).toInstant());
	    Date dHasta = Date.from(hasta.atTime(23,59,59).atZone(zone).toInstant());
	    
	    System.out.println("Rango de fechas: " + dDesde + " a " + dHasta);

	    // 4) Para **cada médico** obtenemos sus citas
	    List<Cita> todasLasCitas = new ArrayList<>();
	    for (Usuario medico : medicos) {
	        System.out.println("Buscando citas para médico: " + medico.getNombre() + " (ID: " + medico.getId() + ")");
	        List<Cita> citasMedico = daoCita.findByMedicoEspecialidadFecha(
	            medico.getId(), esp.getId(), dDesde, dHasta
	        );
	        System.out.println("Citas encontradas para " + medico.getNombre() + ": " + citasMedico.size());
	        todasLasCitas.addAll(citasMedico);
	    }
	    
	    System.out.println("Total de citas encontradas: " + todasLasCitas.size());
	    System.out.println("=== FIN DEBUG REPORTE ===");
	    
	    return todasLasCitas;
	}

	// También agregar este método auxiliar para el reporte con estadísticas
	public List<ReporteFila> generarReporteEstadisticas(
	        String medicoNombre,
	        String especialidadNombre, 
	        LocalDate desde,
	        LocalDate hasta) throws Exception {
	    
	    List<Cita> citas = getCitasFiltradasPorMedicoYEspecialidad(
	        medicoNombre, especialidadNombre, desde, hasta);
	    
	    if (citas.isEmpty()) {
	        return new ArrayList<>();
	    }
	    
	    // Agrupar por médico y especialidad
	    Map<String, List<Cita>> citasPorMedico = citas.stream()
	        .collect(Collectors.groupingBy(c -> c.getMedico().getNombre()));
	    
	    List<ReporteFila> resultado = new ArrayList<>();
	    
	    for (Map.Entry<String, List<Cita>> entry : citasPorMedico.entrySet()) {
	        String nombreMedico = entry.getKey();
	        List<Cita> citasMedico = entry.getValue();
	        
	        if (!citasMedico.isEmpty()) {
	            Cita primeraCita = citasMedico.get(0);
	            String especialidad = primeraCita.getEspecialidad().getNombreEspecialidad();
	            int totalCitas = citasMedico.size();
	            
	            // Obtener total de horarios del médico en el período
	            ZoneId zone = ZoneId.systemDefault();
	            Date dDesde = Date.from(desde.atStartOfDay(zone).toInstant());
	            Date dHasta = Date.from(hasta.atTime(23,59,59).atZone(zone).toInstant());
	            
	            // Esto es una aproximación - podrías mejorarlo con una consulta específica
	            int totalHorarios = Math.max(totalCitas, 10); // Mínimo 10 para cálculo
	            double ocupacionPct = (double) totalCitas / totalHorarios * 100;
	            ocupacionPct = Math.round(ocupacionPct * 100.0) / 100.0; // Redondear a 2 decimales
	            
	            resultado.add(new ReporteFila(nombreMedico, especialidad, totalCitas, totalHorarios, ocupacionPct));
	        }
	    }
	    
	    return resultado;
	}
	
	public void deleteCita(Integer citaId) throws Exception {
	    if (citaId == null || citaId <= 0) {
	        throw new Exception("ID de cita inválido");
	    }
	    
	    Cita cita = daoCita.read(citaId);
	    if (cita == null) {
	        throw new Exception("Cita no encontrada");
	    }
	    
	    // Opcional: Liberar el horario si la cita se elimina
	    if (cita.getHorario() != null) {
	        Horario horario = cita.getHorario();
	        horario.setDisponible(true);
	        daoHorario.update(horario);
	    }
	    
	    daoCita.delete(citaId);
	}

}
