var datos;
var miVentana;
function validarUsuario() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/reina/AccesoCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

//            alert(xhr.responseText);
//            alert('ssss');
            if (xhr.responseText === "") {
                alert('¡¡No existe el Usuario, Ó Error en la Contraseña!!');
                window.location = 'index.html';
            }
            
           
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 1,
        usuario: document.getElementById('email').value,
        clave: document.getElementById('password').value}));
    document.getElementById('form').style.display = "none";
//    document.getElementById('BotonAcceso').style.display = "none";
}


function showpassword() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}
    