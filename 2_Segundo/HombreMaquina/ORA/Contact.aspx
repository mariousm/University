<%@ Page Title="Contacto" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Contact.aspx.cs" Inherits="Contact" %>

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
        <h1><asp:Label runat="server" Text="<%$ Resources:Contacto %>"></asp:Label>.</h1>
    </hgroup>

    <section class="contact">
        <header>
            <h3><asp:Label runat="server" Text="<%$ Resources:Telefono %>"></asp:Label></h3>
        </header>
        <p>
            <span class="label"><asp:Label runat="server" Text="<%$ Resources:Movil %>"></asp:Label></span>
            <span>+34 686-00-11-22</span>
        </p>
    </section>

    <section class="contact">
        <header>
            <h3><asp:Label runat="server" Text="<%$ Resources:Correo %>"></asp:Label></h3>
        </header>
        <p>
            <span class="label"><asp:Label runat="server" Text="<%$ Resources:Soporte %>"></asp:Label></span>
            <span><a href="mailto:Support@example.com">Support@example.com</a></span>
        </p>
    </section>

</asp:Content>