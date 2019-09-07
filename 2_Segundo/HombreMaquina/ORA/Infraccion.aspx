<%@ Page Title="Infraccion" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Infraccion.aspx.cs" Inherits="Prueba" %>
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
    <h1><asp:Label runat="server" Text="<%$ Resources:Titulo  %>"></asp:Label> </h1>
    <% if (Request.QueryString["idi"] != null)
       { %>
    <%int idi = Int32.Parse(Request.QueryString["idi"]);%>
    <% if (Request.Form["ctl00$MainContent$PagarInfraccion"] != null)
       {%>
    <%DBPruebas.PayTressPassing(idi); %>
    <%}
       else if (Request.Form["ctl00$MainContent$RecurrirInfraccion"] != null)
       {%>
    <%DBPruebas.RequestTressPassing(idi); %>
    <%} %>
       
    <h1><asp:Label ID="Label1" runat="server" Text="<%$ Resources:Infraccion  %>"></asp:Label> <%:DBPruebas.getUsuario().getInfraccion()[idi].getCodigo()%></h1>
 <ul>
     <li><strong><asp:Label ID="Label2" runat="server" Text="<%$ Resources:Vehiculo  %>"></asp:Label></strong>: <%:DBPruebas.getUsuario().getInfraccion()[idi].getVehiculo().getMatricula()%></li>
     <li><strong><asp:Label ID="Label3" runat="server" Text="<%$ Resources:Localidad  %>"></asp:Label></strong>: <%:DBPruebas.getUsuario().getInfraccion()[idi].getLocalidad()%></li>
     <li><strong><asp:Label ID="Label4" runat="server" Text="<%$ Resources:Precio  %>"></asp:Label></strong>: <%:(DBPruebas.getUsuario().getInfraccion()[idi].getCuantia()).ToString("c")%></li>
     <li><strong><asp:Label ID="Label5" runat="server" Text="<%$ Resources:Causa  %>"></asp:Label></strong>: <%:DBPruebas.getUsuario().getInfraccion()[idi].getCausa()%></li>
 </ul>
    <% if(DBPruebas.getUsuario().getInfraccion()[idi].getQueja()){ %>
    <h3><asp:Label ID="Label6" runat="server" Text="<%$ Resources:Queja  %>"></asp:Label></h3>
    <%} else if (DBPruebas.getUsuario().getInfraccion()[idi].getPagado()) { %>
    <asp:Button ID="RecurrirInfraccion" runat="server" Text="<%$ Resources:Recurrir %>"/>
    <%}else { %>
    <asp:Button ID="PagarInfraccion" runat="server" Text="<%$ Resources:Pagar %>" />
    <% } %>
   <% } else {%>
      <ul>
        <%for (int i = 0; i < DBPruebas.getUsuario().getInfraccion().Count;i++)
          { %>
        <li><a href="./Infraccion.aspx?idi=<%:i %>"><asp:Label runat="server" Text="<%$ Resources:Ver %>"></asp:Label><%:DBPruebas.getUsuario().getInfraccion()[i].getCausa()%></a></li>
        <%} %>
    </ul>
    <%} %>
 
</asp:Content>
