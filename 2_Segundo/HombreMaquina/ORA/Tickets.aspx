<%@ Page Title="Tickets" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Tickets.aspx.cs" Inherits="Prueba" %>
<script runat="server">
    protected override void InitializeCulture()
    {
        if (DBPruebas.getCIA() == null)
        {
            UICulture = "es-ES";
            Culture = "es";
        }
        else
        {
            UICulture = DBPruebas.getUsuario().getIdioma();
            Culture = DBPruebas.getUsuario().getIdioma().Split(new Char[] { '-' })[0];
        }
    }
</script>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <%int idv = Int32.Parse(Request.QueryString["idv"]); %>
    <%int idt = Int32.Parse(Request.QueryString["idt"]);
      if (Request.Form["ctl00$MainContent$ctl00"] != null) {
          int minutos = Int32.Parse(Request.Form["ctl00$MainContent$ctl00"]);
          if (minutos >= 0)
          {
              DBPruebas.AlterParkingTime(DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[idt].getFinalTicket().AddMinutes(minutos), idt, idv);
          }
          if(minutos<0)
          {%>
            <asp:Label ID="Label8" runat="server" Text="<%$ Resources:Mensaje  %>"></asp:Label>
    <%}
      }%>
    <h1><asp:Label ID="Label1" runat="server" Text="<%$ Resources:Ticket Actual:  %>"></asp:Label> <%:DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[idt].getIdTicket() %></h1>
 <ul>
     <li><strong><asp:Label ID="Label2" runat="server" Text="<%$ Resources:Vehiculo  %>"></asp:Label></strong>: <%:DBPruebas.getUsuario().getVehiculo()[idv].getMatricula()%></li>
     <li><strong><asp:Label ID="Label3" runat="server" Text="<%$ Resources:Localidad  %>"></asp:Label></strong>: <%:DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[idt].getLocalidad()%></li>
     <li><strong><asp:Label ID="Label4" runat="server" Text="<%$ Resources:Precio  %>"></asp:Label></strong>: <%:DBPruebas.CalcularPrecioTicket(DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[idt].getInicioTicket(),DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[idt].getFinalTicket(),DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[idt].getLocalidad()).ToString("c")%></li>
     <li><strong><asp:Label ID="Label5" runat="server" Text="<%$ Resources:FechaInicio  %>"></asp:Label></strong>: <%:DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[idt].getInicioTicket()%></li>
     <li><strong><asp:Label ID="Label6" runat="server" Text="<%$ Resources:FechaFinal  %>"></asp:Label></strong>: <%:DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[idt].getFinalTicket()%></li>
 </ul>
    <asp:Label ID="Label7" runat="server" Text="<%$Resources:Minutos %>"></asp:Label> <asp:TextBox runat="server" TextMode="Number"></asp:TextBox>
    <asp:Button Text="<%$ Resources:Añadir %>" runat="server"/>
</asp:Content>
