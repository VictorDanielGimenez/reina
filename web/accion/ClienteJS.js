$(function () {
  //cargamos el combo ciudad
  function listarCiudad() {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/CiudadCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        var json = JSON.parse(xhr.responseText);
        var i;
        var valorOption = "";
        valorOption += "<option value=0>--- Seleccionar ---</option>";
        for (i = 0; i < json.length; i++) {
          valorOption +=
            "<option value=" +
            json[i].id_ciudad +
            ">" +
            json[i].ciu_descri +
            "</option>";
        }
        document.getElementById("id_ciudad").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 5 })));
  }

  listarCiudad();

  //cargamos el combo ciudad
  function listarCiudad2() {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/CiudadCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        var json = JSON.parse(xhr.responseText);
        var i;
        var valorOption = "";
        valorOption += "<option value=0>--- Seleccionar ---</option>";
        for (i = 0; i < json.length; i++) {
          valorOption +=
            "<option value=" +
            json[i].id_ciudad +
            ">" +
            json[i].ciu_descri +
            "</option>";
        }
        document.getElementById("id_ciuda").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 5 })));
  }

  listarCiudad2();

  $("#form").submit(function (e) {
    e.preventDefault();
    if ($("#cedula").val() == "") {
      toastr.warning("Favor registrar una cedula.");
    }else if ($("#raz").val() == "") {
      toastr.warning("Favor registrar un Nombre y Apellido.");
    }else if ($("#tel").val() == "") {
      toastr.warning("Favor registrar un numero de telefono.");
    }else if ($("#ruc").val() == "") {
      toastr.warning("Favor registrar un ruc.");
    } else if ($("#dir").val() == "") {
      toastr.warning("Favor registrar una direccion.");
    } else if ($("#id_ciudad").val() == 0) {
      toastr.warning("Favor registrar una ciudad.");
    } else {
      datos = { bandera: 1, cli_nombre: $("#raz").val(), cli_ci: $("#cedula").val(), cli_ruc: $("#ruc").val(), cli_direc: $("#dir").val(), cli_telef: $("#tel").val(), id_ciudad: $("#id_ciudad").val()};
      $("#guardar").attr("disabled", "disabled");
      $.ajax({
        url: "/reina/ClienteCRTL",
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

  //creamos una funcion para traer un json
  function listar() {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/ClienteCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        //convertimos el json a un array
        var json = JSON.parse(xhr.responseText);
        var dataSet = [];
        for (var i = 0; i < json.length; i++) {
          dataSet.push([
            json[i].id_cliente,
            json[i].cli_nombre,
            json[i].cli_ruc,
            json[i].cli_ci,
            json[i].cli_direc,
            json[i].cli_telef,
            json[i].ciu_descri,
            json[i].id_ciudad,
          ]);
        }
        $(document).ready(function () {
          var table = $("#listado").DataTable({
            data: dataSet,
            columns: [
              { title: "ID" },
              { title: "Nombre y Apellido" },
              { title: "RUC" },
              { title: "C.I" },
              { title: "Direccion" },
              { title: "Telefono" },
              { title: "Ciudad" },
              { title: "Acciones" },
            ],
            columnDefs: [
              {
                //GUARDAMOS EL VALOR DE PRIMERA COLUMNA EN UNA VARIABLE
                targets: 7,
                data: null,
                render: function (data, type, row) {
                  return (
                    '<button type="button" class="btn btn-warning btn-sm" onclick="modificar(' + data[0] + ',\'' + data[1] + '\',\'' + data[2] + '\',\'' + data[3] + '\',\'' + data[4] + '\',\'' + data[5]+ '\',' + data[7] +')"><i class="fas fa-edit"></i></button> ' +
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

  modificar = function (codigo, nombres, ruc, ci, direccion, telefono, ciudad) {
    $("#modal-editar").modal("toggle");
    $("#codigo").val(codigo);
    $("#ra").val(nombres);
    $("#cedul").val(ci);
    $("#tele").val(telefono);
    $("#ru").val(ruc);
    $("#dire").val(direccion);
    $('#id_ciuda').val(ciudad);
      $('#id_ciuda').trigger('change');
  
  };

  $("#form_editar").submit(function (e) {
    e.preventDefault();
    if ($("#cedul").val() == "") {
      toastr.warning("Favor registrar una cedula.");
    }else if ($("#ra").val() == "") {
      toastr.warning("Favor registrar un Nombre y Apellido.");
    }else if ($("#tele").val() == "") {
      toastr.warning("Favor registrar un numero de telefono.");
    }else if ($("#ru").val() == "") {
      toastr.warning("Favor registrar un ruc.");
    } else if ($("#dire").val() == "") {
      toastr.warning("Favor registrar una direccion.");
    } else if ($("#id_ciuda").val() == 0) {
      toastr.warning("Favor registrar una ciudad.");
    } else {
      $("#modificar").attr("disabled", "disabled");
      dato = {bandera: 2, id_cliente: $("#codigo").val(),cli_nombre: $("#ra").val(), cli_ci: $("#cedul").val(), cli_ruc: $("#ru").val(), cli_direc: $("#dire").val(), cli_telef: $("#tele").val(), id_ciudad: $("#id_ciuda").val()};
      $.ajax({
        url: "/reina/ClienteCRTL",
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

  eliminar = function (codigo) {
    swal(
      {
        title: "Confirmar",
        text: "Está seguro que desea eliminar el registro?",
        type: "warning",
        confirmButtonText: "SI",
        confirmButtonColor: "#5cb85c",
        showCancelButton: true,
        cancelButtonText: "NO",
      },
      function (isConfirm) {
        if (isConfirm) {
          dat = { bandera: 3, id_cliente: codigo };
          $.ajax({
            url: "/reina/ClienteCRTL",
            method: "POST",
            data: JSON.stringify(dat),
            success: function (data) {
              try {
                response = data;
                if (response.status == "success") {
                  toastr.success(response.message);
                  setTimeout(function () {
                    location.reload();
                  }, 3000);
                } else {
                  toastr.error(response.message);
                }
              } catch (error) {
                toastr.error("Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador del sistema"
                );
                console.log(error);
              }
            },
            error: function (data) {
              toastr.error(
                "Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador de la red"
              );
              console.log(error);
            },
          });
        }
      }
    );
  };
});
