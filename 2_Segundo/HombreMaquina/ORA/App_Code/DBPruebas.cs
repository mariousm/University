using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Descripción breve de DBPruebas
/// </summary>
public class DBPruebas
{
    private static String CIA;
    private static Dictionary<String, Usuario> users;
    private static Boolean bdIniciada = false;

    public DBPruebas()
    {
    }
    public static String getCIA()
    {
        return CIA;
    }
    public static Usuario getUsuario()
    {
        if (users[CIA] != null)
        {
            return users[CIA];
        }
        return null;
    }
    
    public static float CalcularPrecioTicket(DateTime ini, DateTime fin, string localidad)
    {
        int tiempo = (int)(fin-ini).TotalMinutes;
        float precio = 0;
        if (localidad=="Burgos")
        {
            if (tiempo <= 18)
            {
                precio = 0.20f;
            }
            else if (tiempo <= 35)
            {
                precio = 0.40f;
            }
            else if (tiempo <= 54)
            {
                precio = 0.60f;
            }
            else if (tiempo <= 72)
            {
                precio = 0.80f;
            }
            else if (tiempo <= 86)
            {
                precio = 1;
            }
            else if (tiempo <= 103)
            {
                precio = 1.20f;
            }
            else if (tiempo <= 120)
            {
                precio = 1.40f;
            }
            else
            {
                precio = 1.40f + (tiempo - 120) / 60 * 1.1f;
            }
        }
        else
        {
            if (tiempo <= 30)
            {
                precio = 1;
            }
            else if (tiempo <= 60)
            {
                precio = 2;
            }
            else if (tiempo <= 90)
            {
                precio = 3;
            }
            else if (tiempo <= 120)
            {
                precio = 4;
            }
            else if (tiempo <= 240)
            {
                precio = 5;
            }
            else
            {
                precio = 5 + (tiempo - 240) / 60 * 1.1f;
            }
        }
        return precio;
    }

    public static bool login(String cia, String contrasena)
    {
        Usuario loginUser;
        try
        {
            loginUser = users[cia];
        }
        catch (Exception e) {
            return false;
        }
        if (loginUser.getPass() == contrasena)
        {
            CIA = cia;
            return true;
        }
        return false;

    }
    public static void Init()
    {
        if (bdIniciada == false)
        {
        //USUARIO NUMERO 1
        users = new Dictionary<String, Usuario>();
        Usuario usuario1 = new Usuario("usuario1", "1234", "Jorge", "Ubierna", "13254896X", "es-ES", "jorgeubi@jub.com", "ES00 2222 2222 2222 2222 2222", "Burgos", "1111111122222222");
        List<Vehiculo> vehiculos = new List<Vehiculo>();
        vehiculos.Add(new Vehiculo("1111WWW", "Ford", "Mondeo", "Negro"));
        usuario1.setVehiculo(vehiculos);
        List<Ticket> tickets = new List<Ticket>();
        tickets.Add(new Ticket(DateTime.Now, DateTime.Now.AddMinutes(30), CalcularPrecioTicket(DateTime.Now, DateTime.Now.AddMinutes(30), "Burgos"), "1", "Burgos"));
        vehiculos[0].setTickets(tickets);
        Parquimetro parquimetro1 = new Parquimetro("Burgos","00001","parquimetro1");
        tickets[0].setParquimetro(parquimetro1);
        List<Infraccion> infracciones= new List<Infraccion>();
        infracciones.Add(new Infraccion("1",false,false,"Burgos",2,40.00f));
        usuario1.setInfraccion(infracciones);
        infracciones[0].setVehiculo(vehiculos[0]);
        infracciones[0].setTicket(tickets[0]);
        users.Add("usuario1", usuario1);

        //USUARIO NUMERO 2
        Usuario usuario2 = new Usuario("usuario2", "4321", "Mario", "Navarro", "17630210K", "en-GB", "marionav@mna.com", "EN00 1122 1122 2211 2211 2211", "Liverpool", "2222222211111111");
        List<Vehiculo> vehiculos2 = new List<Vehiculo>();
        vehiculos2.Add(new Vehiculo("1080GTX", "Ferrari", "Enzo", "Rojo"));
        vehiculos2.Add(new Vehiculo("0801XTG", "Bugatti", "Veyron", "Negro"));
        usuario2.setVehiculo(vehiculos2);
        List<Ticket> tickets2 = new List<Ticket>();
        tickets2.Add(new Ticket(DateTime.Now, DateTime.Now.AddMinutes(120), CalcularPrecioTicket(DateTime.Now, DateTime.Now.AddMinutes(120), "Liverpool"), "2", "Liverpool"));
        List<Ticket> tickets3 = new List<Ticket>();
        tickets3.Add(new Ticket(DateTime.Now, DateTime.Now.AddMinutes(40), CalcularPrecioTicket(DateTime.Now, DateTime.Now.AddMinutes(40), "Liverpool"), "3", "Liverpool"));
        tickets3.Add(new Ticket(new DateTime(2017, 2, 5, 13, 00, 00), new DateTime(2017, 2, 5, 15, 00, 00), CalcularPrecioTicket(new DateTime(2017, 2, 5, 13, 00, 00), new DateTime(2017, 2, 5, 15, 00, 00), "Liverpool"), "4", "Liverpool"));
        vehiculos2[0].setTickets(tickets2);
        vehiculos2[1].setTickets(tickets3);
        Parquimetro parquimetro2 = new Parquimetro("Liverpool","00002","parquimetro2");
        tickets3[0].setParquimetro(parquimetro2);
        tickets3[1].setParquimetro(parquimetro2);
        tickets2[0].setParquimetro(parquimetro2);
        List<Infraccion> infracciones2= new List<Infraccion>();
        infracciones2.Add(new Infraccion("2",false,false,"Liverpool",1,70.00f));
        usuario2.setInfraccion(infracciones2);
        infracciones2[0].setVehiculo(vehiculos2[0]);
        infracciones2[0].setTicket(tickets2[0]);
        users.Add("usuario2", usuario2);
        bdIniciada = true;
        }
    }
    public static void End()
    {
        CIA = null;
    }
    public static void ModifyUser(Dictionary<String,String> datos)
    {
        foreach (String clave in datos.Keys)
        {
            switch (clave)
            {
                case "Nombre":
                    getUsuario().setNombre(datos[clave]);
                    break;
                case "Apellidos":
                    getUsuario().setApellidos(datos[clave]);
                    break;
                case "Pass":
                    getUsuario().setPass(datos[clave]);
                    break;
                case "Email":
                    getUsuario().setEmail(datos[clave]);
                    break;
                case "Iban":
                    getUsuario().setIban(datos[clave]);
                    break;
                case "Pan":
                    getUsuario().setPan(datos[clave]);
                    break;
            }
        }
    }
    public static void AlterParkingTime(DateTime tiempo , int ticket, int vehiculo)
    {
        users[CIA].getVehiculo()[vehiculo].getTickets()[ticket].setFinalTicket(tiempo.Year,tiempo.Month,tiempo.Day,tiempo.Hour,tiempo.Minute,tiempo.Second);
    }
    public static List<Infraccion> ShowTressPassing()
    {
        return users[CIA].getInfraccion();
    }
    public static void PayTressPassing(int infraccion)
    {
        users[CIA].getInfraccion()[infraccion].setPagado(true);
    }
    public static bool RequestTressPassing(int infraccion)
    {
        if (users[CIA].getInfraccion()[infraccion].getPagado())
        {
            users[CIA].getInfraccion()[infraccion].setQueja(true);
            return true;
        }
        else
        {
            return false;
        }
    }
}