<%@ Page Title="PerfilModificar" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="PerfilModificar.aspx.cs" Inherits="PerfilModificar" %>

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
        <asp:Label runat="server" Text="<%$ Resources:Modificar %>"></asp:Label>
    </h1>
    <fieldset>
                    <legend>Formulario de inicio de sesión</legend>
                    <ol>
                        <li>
                            <label><asp:Label runat="server" Text="<%$ Resources:Nombre %>"></asp:Label></label>
                            <asp:TextBox runat="server" ID="nombre" ></asp:TextBox>
                        </li>
                        <li>
                            <label><asp:Label runat="server" Text="<%$ Resources:Apellidos %>"></asp:Label></label>
                            <asp:TextBox runat="server" ID="apellidos" ></asp:TextBox>
                        </li>
                        <li>
                            <label><asp:Label runat="server" Text="<%$ Resources:Contrasena %>"></asp:Label></label>
                            <asp:TextBox runat="server" ID="contrasena"></asp:TextBox>
                        </li>
                        <li>
                            <label><asp:Label runat="server" Text="<%$ Resources:Email %>"></asp:Label></label>
                            <asp:TextBox runat="server" ID="email"></asp:TextBox>
                        </li>
                        <li>
                            <label><asp:Label runat="server" Text="<%$ Resources:Iban %>"></asp:Label></label>
                            <asp:TextBox runat="server" ID="iban"></asp:TextBox>
                        </li>
                        <li>
                            <label><asp:Label runat="server" Text="<%$ Resources:Pan %>"></asp:Label></label>
                            <asp:TextBox runat="server" ID="pan"></asp:TextBox>
                        </li>
                    </ol>
                    <asp:Button ID="ButtonModificar" runat="server" Text="<%$ Resources:Guardar %>" onClick="ButtonModificar_Click"/>
                </fieldset>

</asp:Content>
