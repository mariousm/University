using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Descripción breve de Usuario
/// </summary>
public class Usuario
{
    private String nick;
    private String pass;
    private String nombre;
    private String apellidos;
    private String nif;
    private String idioma;
    private String email;
    private String localidad;
    private String iban;
    private String pan;
    private List<Vehiculo> vehiculo;
    private List<Infraccion> infraccion;

	public Usuario(String nick, String pass, String nombre, String apellidos, String nif, String idioma, String email, String iban, String localidad, String pan)
	{
        this.nick = nick;
        this.pass = pass;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.idioma = idioma;
        this.email = email;
        this.iban = iban;
        this.pan = pan;
        this.localidad = localidad;
	}
    public String getNick()
    {
        return nick;
    }
    public String getPass()
    {
        return pass;
    }
    public String getNombre()
    {
        return nombre;
    }
    public String getApellidos()
    {
        return apellidos;
    }
    public String getNif()
    {
        return nif;
    }
    public String getIdioma()
    {
        return idioma;
    }
    public String getEmail()
    {
        return email;
    }
    public String getIban()
    {
        return iban;
    }
    public String getPan()
    {
        return pan;
    }
    public String getLocalidad()
    {
        return localidad;
    }
    public List<Vehiculo> getVehiculo()
    {
        return vehiculo; 
    }
    public Vehiculo getVehiculoUsuario()
    {
        return vehiculo[0];
    }
    public List<Infraccion> getInfraccion()
    {
        return infraccion;
    }


    public void setNick(String nick)
    {
        this.nick = nick;
    }
    public void setPass(String pass)
    {
        if (pass != "")
        {
            this.pass = pass;
        }
    }
    public void setNombre(String nombre)
    {
        if (nombre != "")
        {
            this.nombre = nombre;
        }
        
    }
    public void setApellidos(String apellidos)
    {
        if (apellidos != "")
        {
            this.apellidos = apellidos;
        }
    }
    public void setNif(String nif)
    {
        this.nif = nif;
    }
    public void setIdioma(String idioma)
    {
        this.idioma = idioma;
    }
    public void setEmail(String email)
    {
        if (email != "")
        {
            this.email = email;
        }
    }
    public void setLocalidad(String localidad)
    {
        if (localidad != "")
        {
            this.localidad = localidad;
        }
    }
    public void setIban(String iban)
    {
        if (iban != "")
        {
            this.iban = iban;
        }
    }
    public void setPan(String pan)
    {
        if (pan != "")
        {
            this.pan = pan;
        }
    }
    public void setVehiculo(List<Vehiculo> vehiculo)
    {
        this.vehiculo = vehiculo;
    }
    public void setInfraccion(List<Infraccion> infraccion)
    {
        this.infraccion = infraccion;
    }
}