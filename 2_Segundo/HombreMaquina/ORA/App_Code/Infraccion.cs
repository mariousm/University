using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Descripción breve de Infraccion
/// </summary>
public class Infraccion
{
    private String codigo;
    private bool pagado;
    private bool quitado;
    private String localidad;
    private Vehiculo vehiculo;
    private int causa;
    private float cuantia;
    private Ticket ticket;
    private bool queja;
    //Un vehiculo puede tener activa solo dos infracciones

	public Infraccion(String codigo, bool pagado, bool quitado, String localidad, int causa, float cuantia)
	{
        this.codigo = codigo;
        this.pagado = pagado;
        this.quitado = quitado;
        this.localidad = localidad;
        this.causa = causa;
        this.cuantia = cuantia;
	}

    public String getCodigo()
    {
        return codigo;
    }
    public bool getPagado()
    {
        return pagado;
    }
    public bool getQuitado()
    {
        return quitado;
    }
    public String getLocalidad()
    {
        return localidad;
    }
    public Vehiculo getVehiculo()
    {
        return vehiculo;
    }
    public int getCausa()
    {
        return causa;
    }
    public float getCuantia()
    {
        return cuantia;
    }
    public Ticket getTicket()
    {
        return ticket;
    }
    public bool getQueja()
    {
        return queja;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }
    public void setPagado(bool pagado)
    {
        this.pagado = pagado;
    }
    public void setQuitado(bool quitado)
    {
        this.quitado = quitado;
    }
    public void setVehiculo(Vehiculo vehiculo)
    {
        this.vehiculo = vehiculo;
    }
    public void setCuantia(float cuantia)
    {
        this.cuantia = cuantia;
    }
    public void setTicket(Ticket ticket)
    {
        this.ticket = ticket;
    }
    public void setQueja(bool queja)
    {
        this.queja = queja;
    }
}