<%@ Page Title="Acerca de" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="About.aspx.cs" Inherits="About" %>
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
    <hgroup class="title">
        <h1><asp:Label runat="server" Text="<%$ Resources:Pagina %>"></asp:Label></h1>
    </hgroup>

    <article>
        <p>
            <asp:Label runat="server" Text="<%$ Resources:P1 %>"></asp:Label>
        </p>
        <p>
            <asp:Label runat="server" Text="<%$ Resources:P2 %>"></asp:Label>
        </p>

    </article>

</asp:Content>