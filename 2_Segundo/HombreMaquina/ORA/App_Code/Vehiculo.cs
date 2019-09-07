using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Descripción breve de Vehiculo
/// </summary>
public class Vehiculo
{
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private List<Ticket> tickets;

	public Vehiculo(String matricula, String marca, String modelo, String color)
	{
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
	}
    public String getMatricula()
    {
        return matricula;
    }
    public String getMarca()
    {
        return marca;
    }
    public String getModelo()
    {
        return modelo;
    }
    public String getColor()
    {
        return color;
    }
    public List<Ticket> getTickets()
    {
        return tickets;
    }


    public void setMatricula(String matricula)
    {
        this.matricula = matricula;
    }
    public void setMarca(String marca)
    {
        this.marca = marca;
    }
    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }
    public void setColor(String color)
    {
        this.color = color;
    }
    public void setTickets(List<Ticket> tickets)
    {
        this.tickets = tickets;
    }
}