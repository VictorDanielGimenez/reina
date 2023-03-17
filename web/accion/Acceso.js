var datos;
var miVentana;


function verificarUsuario() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/reina/AccesoCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) { 
            //imprimir el resultado
            console.log(xhr.responseText);
            if (xhr.responseText == 1) {
                window.location.href = "/reina/vistas/home.html";
            } else {
                alert("Usuario o contraseña incorrectos");
            }
        }
    };

    xhr.send(JSON.stringify(datos = {bandera: 2,
        email: document.getElementById('email').value,
        clave: document.getElementById('password').value}));
}

function CerrarSession() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/reina/AccesoCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) { 
            //imprimir el resultado
            console.log(xhr.responseText);
            if (xhr.responseText == 1) {
                window.location.href = "/reina/";
            } else {
                alert("Ocurrio Un Error");
            }
        }
    };

    xhr.send(JSON.stringify(datos = {bandera: 1}));
}

function UserLog() {
  var xhr = new XMLHttpRequest(), //
  method = "POST",
  url = "/reina/UsuarioCRTL";
  xhr.open(method, url, true);
  xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
          //convertimos el json a un array
          var json = JSON.parse(xhr.responseText);  
          //imprimimos el valor dentro de un label

          document.getElementById('UserNombre').innerHTML = json[0].primernombre +" "+json[0].primerapellido;
    
        } 
  };
  xhr.send(JSON.stringify(datos = {bandera: 6}));
} 
UserLog();




function showpassword() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}
    