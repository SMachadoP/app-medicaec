package ec.edu.ups.paw.appMedica.model;
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Cita implements Serializable {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="idCita")
	private Integer id;
	private Date fecha;
	private String estado;
	
	@ManyToOne
	@JoinColumn(name = "idEspecialidad")
	private Especialidad especialidad;
	
	@OneToOne(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "idHorario")
	private Horario horario;
	
	@ManyToOne
	@JoinColumn(name = "idMedico")
	private Usuario medico;
	
	@ManyToOne
	@JoinColumn(name = "idPaciente")
	private Usuario paciente;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Especialidad getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}
	public Horario getHorario() {
		return horario;
	}
	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	public Usuario getMedico() {
		return medico;
	}
	public void setMedico(Usuario medico) {
		this.medico = medico;
	}
	public Usuario getPaciente() {
		return paciente;
	}
	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}
	@Override
	public String toString() {
		return "Cita [id=" + id + ", fecha=" + fecha + ", estado=" + estado + ", especialidad=" + especialidad
				+ ", horario=" + horario + ", medico=" + medico + ", paciente=" + paciente + "]";
	}
	
	

}
