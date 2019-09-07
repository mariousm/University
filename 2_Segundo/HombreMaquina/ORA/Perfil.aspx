<%@ Page Title="Perfil" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Perfil.aspx.cs" Inherits="Contact" %>

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
    <h1>
        <%if (DBPruebas.getCIA() != null) {%>
        <%: DBPruebas.getUsuario().getApellidos()+ "," + "  " + DBPruebas.getUsuario().getNombre()%>
        <%} %>
    </h1>
    <div class="left">
    <img src="Images/fotopersona.png" class="foto" alt="" /><br /><br />
    <a runat="server" href="~/PerfilModificar.aspx"><asp:Label runat="server" Text="<%$ Resources:Editar %>"></asp:Label></a>
    </div>
    <div class="right">
     <%if (DBPruebas.getCIA() != null) {%>
     <p class="datos_persona">
        <span class="izq"><strong><asp:Label ID="Label1" runat="server" Text="<%$ Resources:DP %>"></asp:Label></strong></span><br />
        <span class="izq"><strong><asp:Label ID="Label2" runat="server" Text="<%$ Resources:Nombre %>"></asp:Label></strong></span><span class="dch"><%:" "+DBPruebas.getUsuario().getNombre()%></span><br/>
        
        <span class="izq"><strong><asp:Label ID="Label3" runat="server" Text="<%$ Resources:Apellidos %>"></asp:Label></strong></span><span class="dch"><%:" "+DBPruebas.getUsuario().getApellidos()%></span><br/>
        <span class="izq"><strong><asp:Label ID="Label4" runat="server" Text="<%$ Resources:Nif %>"></asp:Label></strong></span><span class="dch"><%:" "+DBPruebas.getUsuario().getNif()%></span><br/>
        <span class="izq"><strong><asp:Label ID="Label5" runat="server" Text="<%$ Resources:email %>"></asp:Label></strong></span><span class="dch"><%:" "+DBPruebas.getUsuario().getEmail()%></span><br/>
        <span class="izq"><strong><asp:Label ID="Label6" runat="server" Text="<%$ Resources:Localidad %>"></asp:Label></strong></span><span class="dch"><%:" "+DBPruebas.getUsuario().getLocalidad()%></span><br/><br />

        <span class="izq"><strong><asp:Label ID="Label7" runat="server" Text="<%$ Resources:FP %>"></asp:Label></strong></span><br />
        <span class="izq"><strong><asp:Label ID="Label8" runat="server" Text="<%$ Resources:Iban %>"></asp:Label></strong></span><span class="dch"><%:" "+DBPruebas.getUsuario().getIban()%></span><br/>
        <span class="izq"><strong><asp:Label ID="Label9" runat="server" Text="<%$ Resources:Pan %>"></asp:Label></strong></span><span class="dch"><%:" "+DBPruebas.getUsuario().getPan()%></span><br/><br />

        <span class="izq"><strong><asp:Label ID="Label10" runat="server" Text="<%$ Resources:DV %>"></asp:Label></strong></span><br />
        <span class="izq"><strong><asp:Label ID="Label11" runat="server" Text="<%$ Resources:Vehiculo %>"></asp:Label></strong></span><span class="dch"><%for(int i=0;i<DBPruebas.getUsuario().getVehiculo().Count;i++){ %><%:DBPruebas.getUsuario().getVehiculo()[i].getMarca() + " "%><%} %></span><br/><br />

    </p>
    <% } %> 
    </div>
    

</asp:Content>