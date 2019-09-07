using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Descripción breve de Ticket
/// </summary>
public class Ticket
{
    private DateTime inicioticket;
    private DateTime finalticket;
    private float importe;
    private Parquimetro parquimetro;
    private String idticket;
    private String Localidad;

	public Ticket(DateTime inicioticket, DateTime finalticket, float importe, String idticket, String Localidad)
	{
        this.inicioticket = inicioticket;
        this.finalticket = finalticket;
        this.importe = importe;
        this.idticket = idticket;
        this.Localidad = Localidad;
	}
    public DateTime getInicioTicket()
    {
        return inicioticket;
    }
    public DateTime getFinalTicket()
    {
        return finalticket;
    }
    public float getImporte()
    {
        return importe;
    }
    public Parquimetro getParquimetro()
    {
        return parquimetro;
    }
    public String getIdTicket()
    {
        return idticket;
    }
    public String getLocalidad()
    {
        return Localidad;
    }

    public void setInicioTicket(int year, int month, int day, int hour, int min, int sec)
    {
        this.inicioticket = new DateTime(year,month,day,hour,min,sec);
    }
    public void setFinalTicket(int year, int month, int day, int hour, int min, int sec)
    {
        this.finalticket = new DateTime(year, month, day, hour, min, sec);
    }
    public void setIdImporte(float importe)
    {
        this.importe = importe;
    }
    public void setIdTicket(String idticket)
    {
        this.idticket = idticket;
    }
    public void setParquimetro(Parquimetro parquimetro)
    {
        this.parquimetro=parquimetro;
    }
    public void setLocalidad(String Localidad)
    {
        this.Localidad = Localidad;
    }
}