<%@ Page Title="<%$ Resources:titulo %>" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" %>

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
<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
    <section class="featured">
        <div class="content-wrapper">
            <hgroup class="title">
                <h1>
                    <%if (DBPruebas.getCIA() != null) {%>
                    <a runat="server" href="~/Perfil.aspx"><img src="Images/fotopersona.png" class="fotoperfil" width="100" height="100" alt=""/></a>
                    <asp:Label ID="Label4" runat="server" Text="<%$ Resources:Saludo %>"></asp:Label>
                    <% }else { %> 
                    <asp:Label ID="Label1" runat="server" Text="<%$ Resources:SaludoNoLogin %>"></asp:Label>
                    <%} %>
                </h1>
                <h2> 
                    <%if (DBPruebas.getCIA() != null) {%>
                    <%: DBPruebas.getUsuario().getNombre()%>
                    <% } %> 
                </h2>
                
            </hgroup>
            <p>
               
            </p>
        </div>
    </section>
</asp:Content>

<asp:Content runat="server" ID="BodyContent" ContentPlaceHolderID="MainContent">
    <h3><asp:Label runat="server" Text="<%$ Resources:Recomendacion %>"></asp:Label></h3>
    <%if (DBPruebas.getCIA() != null) {%>
      <ol class="round">
        <li class="one">
            <h5><asp:Label runat="server" Text="<%$ Resources:Modificar %>"></asp:Label></h5>
            <asp:Label runat="server" Text="<%$ Resources:Informacion %>"></asp:Label>
        </li>
    </ol>
    <% }else { %> 
      <ol class="round">
        <li class="one">
            <h5>Resgistro</h5>
            Para poder registrarse en nuestra web, usted tiene que ir al ayuntamiento de su localidad.
        </li>
        <li class="two">
            <h5>Entrar en la web</h5>
            Para poder ingresar en la web introduzca su identificar de usuario y su contraseña
        </li>
    </ol>
    <%} %>
</asp:Content>