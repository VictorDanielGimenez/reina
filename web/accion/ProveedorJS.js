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
    if ($("#raz").val() == "") {
      toastr.warning("Favor registrar una Razon Social.");
    }else if ($("#ruc").val() == "") {
      toastr.warning("Favor registrar un ruc.");
    }else if ($("#tel").val() == "") {
      toastr.warning("Favor registrar un numero de telefono.");
    }else if ($("#dire").val() == "") {
      toastr.warning("Favor registrar una direccion.");
    } else if ($("#correo").val() == "") {
      toastr.warning("Favor registrar un correo.");
    } else if ($("#id_ciudad").val() == 0) {
      toastr.warning("Favor registrar una ciudad.");
    } else {
      datos = { bandera: 1, prov_razons: $("#raz").val(), prov_ruc: $("#ruc").val(), prov_te: $("#tel").val(), prov_direc: $("#dire").val(), prov_correo: $("#correo").val(), id_ciudad: $("#id_ciudad").val()};
      $("#guardar").attr("disabled", "disabled");
      $.ajax({
        url: "/reina/ProveedorCRTL",
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
      url = "/reina/ProveedorCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        //convertimos el json a un array
        var json = JSON.parse(xhr.responseText);
        var dataSet = [];
        for (var i = 0; i < json.length; i++) {
          dataSet.push([
            json[i].id_proveedor,
            json[i].prov_razons,
            json[i].prov_ruc,
            json[i].prov_te,
            json[i].prov_direc,
            json[i].prov_correo,
            json[i].ciu_descri,
            json[i].id_ciudad,
          ]);
        }
        $(document).ready(function () {
          var table = $("#listado").DataTable({
            data: dataSet,
            columns: [
              { title: "ID" },
              { title: "Razon Social" },
              { title: "RUC" },
              { title: "Telefono" },
              { title: "Direccion" },
              { title: "Correo" },
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

  modificar = function (codigo, razon, ruc, telefono, direccion, correo, ciudad) {
    $("#modal-editar").modal("toggle");
    $("#codigo").val(codigo);
    $("#ra").val(razon);
    $("#ru").val(ruc);
    $("#te").val(telefono);
    $("#dir").val(direccion);
    $("#corre").val(correo);
    $('#id_ciuda').val(ciudad);
      $('#id_ciuda').trigger('change');
  
  };

  $("#form_editar").submit(function (e) {
    e.preventDefault();
    if ($("#ra").val() == "") {
      toastr.warning("Favor registrar una Razon Social.");
    }else if ($("#te").val() == "") {
      toastr.warning("Favor registrar un numero de telefono.");
    }else if ($("#ru").val() == "") {
      toastr.warning("Favor registrar un ruc.");
    } else if ($("#dir").val() == "") {
      toastr.warning("Favor registrar una direccion.");
    } else if ($("#corre").val() == "") {
      toastr.warning("Favor registrar un correo.");
    } else if ($("#id_ciuda").val() == 0) {
      toastr.warning("Favor registrar una ciudad.");
    } else {
      $("#modificar").attr("disabled", "disabled");
      dato = {bandera: 2, id_proveedor: $("#codigo").val(), prov_razons: $("#ra").val(), prov_ruc: $("#ru").val(), prov_te: $("#te").val(), prov_direc: $("#dir").val(), prov_correo: $("#corre").val(), id_ciudad: $("#id_ciuda").val()};
      $.ajax({
        url: "/reina/ProveedorCRTL",
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
          dat = { bandera: 3, id_proveedor: codigo };
          $.ajax({
            url: "/reina/ProveedorCRTL",
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
