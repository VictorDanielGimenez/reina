var datos;
var miVentana;
function validarUsuario() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/reina/accesCrtl";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
//            alert(xhr.responseText);
//            alert('ssss');
            if (xhr.responseText === "") {
                alert('¡¡No existe el Usuario, Ó Error en la Contraseña!!');
                window.location = 'index.html';
            }
            document.getElementById("menu_principal").innerHTML = xhr.responseText;
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 1,
        usuario: document.getElementById('email').value,
        clave: document.getElementById('password').value}));
    document.getElementById('panelAcceso').style.display = "none";
//    document.getElementById('BotonAcceso').style.display = "none";
}
                 
//function doOpen(url){
//window.open(url, "C:\Users\user\Documents\UTIC\Lenguaje de Programación III\LPIII - 2018\JavaWebCompras\JavaWeb_Compras\src\java\Ayuda\help.chm", 
//"width=330,height=252,scrollbars=NO,statusbar=NO,left=500,top=250");
//}

function abrir() {
     miVentana = window.open( "", "C://Users//user//Documents\UTIC//Lenguaje de Programación III//LPIII - 2018//JavaWebCompras//JavaWeb_Compras//src//java//Ayuda//help.chm", 
     "height=200,width=700,left=300,location=yes,menubar=no,resizable=no,scrollbars=yes,status=no,titlebar=yes,top=300" );
}
function cerrar() {
     miVentana.close();
}

