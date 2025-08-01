package ec.edu.ups.paw.appMedica.model;

public class ReporteFila {
    public String medico;
    public String especialidad;
    public int totalCitas;
    public int totalHorarios;
    public double ocupacionPct;
    public ReporteFila(String m, String e, int c, int h, double p) {
        this.medico = m;
        this.especialidad = e;
        this.totalCitas = c;
        this.totalHorarios = h;
        this.ocupacionPct = p;
    }
}
