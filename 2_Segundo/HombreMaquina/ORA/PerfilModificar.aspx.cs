using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class PerfilModificar : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void ButtonModificar_Click(object sender, EventArgs e)
    {
        String nombre = Request.Form["ctl00$MainContent$nombre"];
        String apellidos = Request.Form["ctl00$MainContent$apellidos"];
        String pass = Request.Form["ctl00$MainContent$contrasena"];
        String email = Request.Form["ctl00$MainContent$email"];
        String iban = Request.Form["ctl00$MainContent$iban"];
        String pan = Request.Form["ctl00$MainContent$pan"];
        Dictionary<String, String> datos = new Dictionary<string, string>();
        datos.Add("Nombre", nombre);
        datos.Add("Apellidos", apellidos);
        datos.Add("Pass", pass);
        datos.Add("Email", email);
        datos.Add("Iban", iban);
        datos.Add("Pan", pan);
        DBPruebas.ModifyUser(datos);
        Response.Redirect("~/Perfil.aspx");
    }
}