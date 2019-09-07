<%@ Page Title="Contacto" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Vehiculos.aspx.cs" Inherits="Contact" %>

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
<asp:Content runat="server" ID="BodyContent" ContentPlaceHolderID="MainContent">
<%if(Request.QueryString["idv"]==null){ %>
    <h1><asp:Label ID="Label3" runat="server" Text="<%$Resources:Tus Vehiculos %>"></asp:Label></h1>
    <ul>
        <%for (int i = 0; i < DBPruebas.getUsuario().getVehiculo().Count;i++)
          { %>
        <li><a href="./Vehiculos.aspx?idv=<%:i %>"><%:DBPruebas.getUsuario().getVehiculo()[i].getMatricula()%></a></li>
        <%} %>
    </ul>
<%}else{
      int idv = Int32.Parse(Request.QueryString["idv"]);%>
    <h1><asp:Label ID="Label1" runat="server" Text="<%$Resources:Tickets Actuales %>"></asp:Label></h1>
    <ul>
    <%for (int i = 0; i < DBPruebas.getUsuario().getVehiculo()[idv].getTickets().Count;i++)
          {
          if(DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[i].getFinalTicket()>DateTime.Now){ %>
        <li><a href="./Tickets.aspx?idt=<%:i %>&idv=<%:idv %>"><asp:Label ID="Label4" runat="server" Text="<%$Resources:Info %>"></asp:Label> <%:DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[i].getIdTicket() %></a></li>
        <%}
          } %>
        </ul>
    <h1><asp:Label ID="Label2" runat="server" Text="<%$Resources:Historial Tickets %>"></asp:Label></h1>
    <ul>
    <%for (int i = 0; i < DBPruebas.getUsuario().getVehiculo()[idv].getTickets().Count;i++)
          {
          if(DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[i].getFinalTicket()<DateTime.Now){ %>
        <li><a href="./TicketsPasados.aspx?idt=<%:i %>&idv=<%:idv %>"><asp:Label ID="Label5" runat="server" Text="<%$Resources:Revisar %>"></asp:Label><%:DBPruebas.getUsuario().getVehiculo()[idv].getTickets()[i].getIdTicket() %></a></li>
        <%}
          } %>
        </ul>
    <%} %>

</asp:Content>