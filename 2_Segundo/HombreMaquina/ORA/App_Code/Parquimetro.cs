using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Descripción breve de Parquimetro
/// </summary>
public class Parquimetro
{
    private String localidad;
    private String idparquimetro;
    private String descripcion;
	public Parquimetro(String localidad, String idparquimetro, String descripcion)
	{
        this.localidad = localidad;
        this.idparquimetro = idparquimetro;
        this.descripcion = descripcion;
	}
    public String getLocalidad()
    {
        return localidad;
    }
    public String getIdParquimetro()
    {
        return idparquimetro;
    }
    public String getDescripcion()
    {
        return descripcion;
    }
    public void setLocalidad(String localidad)
    {
        this.localidad = localidad;
    }
    public void setIdParquimetro(String idparquimetro)
    {
        this.idparquimetro = idparquimetro;
    }
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
}