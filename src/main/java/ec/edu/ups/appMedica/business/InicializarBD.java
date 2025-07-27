package ec.edu.ups.appMedica.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ec.edu.ups.paw.appMedica.dao.CitaDAO;
import ec.edu.ups.paw.appMedica.dao.EspecialidadDAO;
import ec.edu.ups.paw.appMedica.dao.HorarioDAO;
import ec.edu.ups.paw.appMedica.dao.UsuarioDAO;
import ec.edu.ups.paw.appMedica.model.Cita;
import ec.edu.ups.paw.appMedica.model.Especialidad;
import ec.edu.ups.paw.appMedica.model.Horario;
import ec.edu.ups.paw.appMedica.model.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class InicializarBD {
	
	@Inject
	private UsuarioDAO daoUsuario;
	@Inject
	private EspecialidadDAO daoEspecialidad;
	@Inject
	private HorarioDAO daoHorario;
	@Inject
	private CitaDAO daoCita;
	
	
	@PostConstruct
	public void init() {
		System.out.println("Inicializando BD");
		
		Especialidad especialidad = new Especialidad();
		especialidad.setNombreEspecialidad("Pediatría");
		
		daoEspecialidad.insert(especialidad);
		
		Especialidad especialidad2 = new Especialidad();
		especialidad2.setNombreEspecialidad("Cardiologia");
		
		daoEspecialidad.insert(especialidad2);
		
		List<Especialidad> especialidades = daoEspecialidad.getAll();
		for(Especialidad e : especialidades){
			System.out.println(e.toString() + "| ID generado: " + e.getId());
		}
		
		Usuario medico = new Usuario();
		medico.setNombre("Alejandro Machado");
		medico.setCorreo("alejo@gmail.com");
		medico.setTelefono("0984756847");
		medico.setCedula("1724683125");
		medico.setRol("admin");
		medico.setEspecialidad(especialidad);
		
		Usuario medico2 = new Usuario();
		medico.setNombre("Sebastian Machado");
		medico.setCorreo("salejomac1210@gmail.com");
		medico.setTelefono("0982285740");
		medico.setCedula("0605483080");
		medico.setRol("paciente");
		//medico.setEspecialidad(especialidad2);
		
		Usuario paciente = new Usuario();
		paciente.setNombre("Jordy Espinoza");
		paciente.setCorreo("jordy@gmail.com");
		paciente.setTelefono("0984756421");
		paciente.setCedula("0301108312");
		paciente.setRol("paciente");
		paciente.setDireccion("Av. de las Américas");
		paciente.setGenero("Masculino");
		
		daoUsuario.insert(medico);
		daoUsuario.insert(medico2);
		daoUsuario.insert(paciente);
		
		List<Usuario> usuarios = daoUsuario.getAll();
		for(Usuario u : usuarios){
			System.out.println(u.toString() + "| ID generado: " + u.getId());
		}
		
		Horario horario = new Horario();
		try {
		    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		    Date fecha = sdf.parse("29-09-2025 17:00");
		    horario.setFecha(fecha);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		horario.setDisponible(true);
		horario.setMedico(medico);
		
		daoHorario.insert(horario);
		
		List<Horario> horarios = daoHorario.getAll();
		for(Horario h : horarios){
			System.out.println(h.toString() + "| ID generado: " + h.getId());
		}
		
		Cita cita = new Cita();
		try {
		    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		    Date fecha = sdf.parse("29-09-2025 17:00");
		    cita.setFecha(fecha);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		cita.setEstado("pendiente");
		cita.setEspecialidad(especialidad);
		cita.setHorario(horario);
		cita.setMedico(medico);
		cita.setPaciente(paciente);
		
		daoCita.insert(cita);
		
		List<Cita> citas = daoCita.getAll();
		for(Cita c : citas){
			System.out.println(c.toString() + "| ID generado: " + c.getId() + " horario:" + c.getHorario().toString());
		}
	}
}
