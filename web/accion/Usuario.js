//creamos una funcion para traer un json
function listar() {
  var xhr = new XMLHttpRequest(), //
    method = "POST",
    url = "/reina/UsuarioCRTL";
  xhr.open(method, url, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
      //convertimos el json a un array
      var json = JSON.parse(xhr.responseText);

      var dataSet = [];
      for (var i = 0; i < json.length; i++) {
        dataSet.push([
          json[i].id_usuario,
          json[i].usu_nombre,
          json[i].usuemail,
          json[i].estado,
          json[i].id_estado,
        ]);
      }
      $(document).ready(function () {
        var table = $("#listado").DataTable({
          data: dataSet,
          columns: [
            { title: "ID" },
            { title: "Nombre" },
            { title: "Email" },
            { title: "Estado" },
            { title: "Acciones" },
          ],
          columnDefs: [
            {
              //GUARDAMOS EL VALOR DE PRIMERA COLUMNA EN UNA VARIABLE
              targets: 4,
              data: null,
              render: function (data, type, row) {
                return (
                  '<button type="button" class="btn btn-warning btn-sm" onclick="modificar(' +
                  data[0] +
                  ",'" +
                  data[1] +
                  "','" +
                  data[2] +
                  '\')"><i class="fas fa-edit"></i></button> ' +
                  '<button type="button" class="btn btn-danger btn-sm" onclick="eliminar(' +
                  data[0] +
                  ')"><i class="fas fa-trash"></i></button>'
                );
              },
            },
          ],
          language: {
            decimal: "",
            emptyTable: "No hay registros en la tabla",
            info: "Se muestran _START_ a _END_ de _TOTAL_ registros",
            infoEmpty: "Se muestran 0 a 0 de 0 registros",
            infoFiltered: "(filtrado de _MAX_ registros totales)",
            infoPostFix: "",
            thousands: ",",
            lengthMenu: "Mostrar _MENU_ registros",
            loadingRecords: "Cargando...",
            processing: "Procesando...",
            search: "Search:",
            zeroRecords: "No se encontraron registros que coincidan",
            paginate: {
              first: "Primero",
              last: "Último",
              next: "Siguiente",
              previous: "Anterior",
            },
            aria: {
              sortAscending: ": activar para ordenar la columna ascendente",
              sortDescending: ": activar para ordenar la columna descendente",
            },
          },
        });
      });
    }
  };
  xhr.send(JSON.stringify((datos = { bandera: 5 })));
}
listar();
$("#form").submit(function (e) {
  e.preventDefault();
  if ($("#nombre").val() == "") {
    toastr.warning("Favor registrar una descripcion del cargo.");
  } else {
    datos = {
      bandera: 1,
      usu_nombre: $("#nombre").val(),
      usuemail: $("#email").val(),
      usu_clave: $("#pass").val(),
    };
    $("#guardar").attr("disabled", "disabled");
    $.ajax({
      url: "/reina/UsuarioCRTL",
      method: "POST",
      data: JSON.stringify(datos),
      success: function (data) {
        try {
          response = data;
          console.log(response);
          if (response.status == "success") {
            $("#modal-agregar").modal("hide");
            toastr.success(response.message);
            setTimeout(function () {
              $("#nombre").val("");
              $("#guardar").removeAttr("disabled");
              //recarga la pagina
              location.reload();
            }, 3000);
          } else {
            toastr.error(response.message);
            $("#guardar").removeAttr("disabled");
          }
        } catch (error) {
          toastr.error(
            "Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador del sistema"
          );
          console.log(error);
        }
      },
      error: function (error) {
        toastr.error(
          "Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador de la red"
        );
        console.log(error);
      },
    });
  }
});

function procesarJSON(bandera) {
  valores = {
    bandera: bandera,
    usu_nombre: document.getElementById("nombre").value,
    usuemail: document.getElementById("email").value,
    usu_clave: document.getElementById("password").value,
  };
  enviar(valores);
}

function enviar(valores) {
  var xmlhttp = new XMLHttpRequest(); // objeto para peticion vía ajax
  xmlhttp.open("POST", "/reina/UsuarioCRTL"); // tipo de envio -  destino de envio
  xmlhttp.setRequestHeader("Content-Type", "application/json"); // Es el formato de envio de datos
  xmlhttp.send(JSON.stringify(valores));
}

function agregarUsuario() {
  var nombrez = document.getElementById("nombre").value;
  var emailz = document.getElementById("email").value;
  var passwordz = document.getElementById("password").value;

  if (nombrez == "" || emailz == "" || passwordz == "") {
    alert("Debe completar todos los campos");
  } else {
    if (confirm("Confirmar la inserción de Datos")) {
      console.log("ddggg");
      procesarJSON(1);
    } else {
      //limpiar();
    }
  }
}

function limpiar() {
  document.getElementById("form").reset();
  document.getElementById("nombre").value();
  document.getElementById("email").value();
  document.getElementById("password").value();
}

modificar = function (codigo, nombres, email) {
  $("#modal-editar").modal("toggle");
  $("#codigo").val(codigo);
  $("#nombres").val(nombres);
  $("#emails").val(email);
};

$("#form_editar").submit(function (e) {
  e.preventDefault();
  if ($("#nombres").val() == "") {
    toastr.warning("Favor registrar una descripcion del cargo.");
  } else {
    $("#modificar").attr("disabled", "disabled");
    dato = {bandera: 2, id_usuario: $("#codigo").val(), usu_nombre: $("#nombres").val(), usuemail: $("#emails").val(), usu_clave: $("#passs").val()};
    $.ajax({
      url: "/reina/UsuarioCRTL",
      method: "POST",
      data: JSON.stringify(dato),
      success: function (data) {
        try {
          response = data;
          if (response.status == "success") {
            $("#modal-editar").modal("hide");
            toastr.success(response.message);
            setTimeout(function () {
              location.reload();
              $("#modificar").removeAttr("disabled");
            }, 3000);
          } else {
            toastr.error(response.message);
            $("#guardar").removeAttr("disabled");
          }
        } catch (error) {
          toastr.error(
            "Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador del sistema"
          );
          console.log(error);
        }
      },
      error: function (error) {
        toastr.error(
          "Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador de la red"
        );
        console.log(error);
      },
    });
  }
});

eliminar = function(codigo) {
  swal({
    title: "Confirmar",
    text: "Está seguro que desea eliminar el registro?",
    type: "warning",
    confirmButtonText: "SI",
    confirmButtonColor: "#5cb85c",
    showCancelButton: true,
    cancelButtonText: "NO",
  }, function(isConfirm) {
    if (isConfirm) {
    dat = {bandera: 3, id_usuario: codigo};
      $.ajax({
        url: '/reina/UsuarioCRTL',
        method: 'POST',
        data: JSON.stringify(dat),
        success: function(data) {
          try {
            response = data;
            if (response.status == "success") {
              toastr.success(response.message);
              setTimeout(function() {
                location.reload();
              }, 3000);
            }  else {
                            toastr.error(response.message)                               
                        }
          } catch (error) {
            toastr.error('Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador del sistema');
            console.log(error);
          }
        },
        error: function(data) {
          toastr.error('Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador de la red')
          console.log(error);
        }
      });
    }
  });


}   
