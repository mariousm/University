<%@ Page Title="Iniciar sesión" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Login.aspx.cs" Inherits="Account_Login" %>
<%@ Register Src="~/Account/OpenAuthProviders.ascx" TagPrefix="uc" TagName="OpenAuthProviders" %>

<asp:Content runat="server" ID="BodyContent" ContentPlaceHolderID="MainContent">
     <% String idUsuario = Request.Form["ctl00$MainContent$ctl00$UserName"];
       String pass = Request.Form["ctl00$MainContent$ctl00$Password"];
       if (DBPruebas.login(idUsuario, pass))
       {
           Response.Redirect("/ORA/");
       }
     %>
    <hgroup class="title">
        <h1><%: Title %>.</h1>
    </hgroup>
    
    <section id="loginForm">
        <h2>Utilice su nombre de usuario para iniciar sesión.</h2>
        <asp:Login runat="server" ViewStateMode="Disabled" RenderOuterTable="false">
            <LayoutTemplate>
                <fieldset>
                    <legend>Inicio de sesión</legend>
                    <ol>
                        <li>
                            <label>Nombre de usuario</label>
                            <asp:TextBox runat="server" ID="UserName" ></asp:TextBox>
                        </li>
                        <li>
                            <label>Contraseña</label>
                            <asp:TextBox runat="server" ID="Password" TextMode="Password"></asp:TextBox>
                        </li>
                    </ol>
                    <asp:Button runat="server" Text="Iniciar sesión" />
                </fieldset>
            </LayoutTemplate>
        </asp:Login>
        </section>
</asp:Content>