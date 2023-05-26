$(function () {
  //cargamos el combo ciudad
  function listarMarca() {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/MarcaCRTL";
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
            json[i].id_marca +
            ">" +
            json[i].marca_descri +
            "</option>";
        }
        document.getElementById("id_marca").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 5 })));
  }

  listarMarca();

  //cargamos el combo ciudad
  function listarDeposito () {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/DepositoCRTL";
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
            json[i].id_deposito +
            ">" +
            json[i].dep_descri +
            "</option>";
        }
        document.getElementById("id_deposito").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 5 })));
  }

  listarDeposito();

   //cargamos el combo ciudad
   function listarImpuesto () {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/ImpuestoCRTL";
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
            json[i].id_impuesto +
            ">" +
            json[i].imp_descri +
            "</option>";
        }
        document.getElementById("id_imp").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 5 })));
  }

  listarImpuesto();


  function listarMarca2() {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/MarcaCRTL";
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
            json[i].id_marca +
            ">" +
            json[i].marca_descri +
            "</option>";
        }
        document.getElementById("id_marc").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 5 })));
  }

  listarMarca2();

  //cargamos el combo ciudad
  function listarDeposito2 () {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/DepositoCRTL";
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
            json[i].id_deposito +
            ">" +
            json[i].dep_descri +
            "</option>";
        }
        document.getElementById("id_deposit").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 5 })));
  }

  listarDeposito2();

   //cargamos el combo ciudad
   function listarImpuesto2 () {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/ImpuestoCRTL";
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
            json[i].id_impuesto +
            ">" +
            json[i].imp_descri +
            "</option>";
        }
        document.getElementById("id_im").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 5 })));
  }

  listarImpuesto2();


  $('#form').submit(function(e) {
    e.preventDefault();
    if ($('#nombre').val() == "") {
      toastr.warning('Favor registrar una descripcion.');
  }else if ($('#id_marca').val() == 0) {
    toastr.warning('Favor registrar una marca.');
}else if ($('#precmp').val() == "") {
  toastr.warning('Favor registrar un precio de compra.');
}else if ($('#preventa').val() == "") {
  toastr.warning('Favor registrar un precio de compra.');
}else if ($('#cantidad').val() == "") {
  toastr.warning('Favor registrar una cantidad.');
}else if ($('#id_imp').val() == 0) {
    toastr.warning('Favor registrar un impuesto.');
}else if ($('#id_deposito').val() == 0) {
  toastr.warning('Favor registrar un deposito.');
} else {
        datos = {bandera: 1, id_impuesto: $('#id_imp').val(), id_marca: $('#id_marca').val(), art_descri: $('#nombre').val(), art_precioc: $('#precmp').val(), art_preciov: $('#preventa').val(), id_deposito: $('#id_deposito').val(), stock_cant: $("#cantidad").val()};
        $('#guardar').attr("disabled", "disabled");
        $.ajax({
            url: '/reina/ArticuloCRTL',
            method: 'POST',
            data:JSON.stringify(datos),
            success: function(data) {
                try {
                    response = data;
                    console.log(response);
                    if (response.status == "success") {
                        $('#modal-agregar').modal('hide');
                        toastr.success(response.message);
                        setTimeout(function() {
                            $('#nombre').val('');
                            $('#guardar').removeAttr("disabled");
                            //recarga la pagina
                            location.reload();
                            }, 3000);
                    } else {
                        toastr.error(response.message)
                        $('#guardar').removeAttr("disabled");
                    }
                } catch (error) {
                    toastr.error('Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador del sistema');
                    console.log(error);
                }
            },
            error: function(error) {
                toastr.error('Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador de la red');
                console.log(error);
            }
        });
    }
});

   //creamos una funcion para traer un json
   function listar() {
    var xhr = new XMLHttpRequest(), //
    method = "POST",
    url = "/reina/ArticuloCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //convertimos el json a un array
            var json = JSON.parse(xhr.responseText);
           
            var dataSet = [];
            for (var i = 0; i < json.length; i++) {
                dataSet.push([json[i].id_articulo,  //0
                              json[i].art_descri,  //1
                              json[i].mar_descri,  //2
                              json[i].art_precioc,  //3
                              json[i].art_preciov,  //4
                              json[i].imp_descri,  //5
                              json[i].stock_cant,  //6
                              json[i].dep_descri, //7
                              json[i].id_marca,  //8
                              json[i].id_impuesto,  //9
                              json[i].id_deposito,]);   //10                  
            }
            $(document).ready(function () {
                var table = $('#listado').DataTable({
                    data: dataSet,                           
                    columns: [
                        {title: 'ID'},
                        {title: 'Articulo'},
                        {title: 'Marca'},
                        {title: 'Precio Compra'},
                        {title: 'Precio Venta'},
                        {title: 'Impuesto'},
                        {title: 'Cantidad'},
                        {title: 'Deposito'},
                        {title: 'Acciones'}
                    ],
                    columnDefs: [{
                        //GUARDAMOS EL VALOR DE PRIMERA COLUMNA EN UNA VARIABLE
                        targets: 8,
                        data: null,
                        render: function (data, type, row) {
                            return '<button type="button" class="btn btn-warning btn-sm" onclick="modificar(' + data[0] + ',\'' + data[1] +'\',\'' + data[3]+'\',\'' + data[4]+'\',' + data[6] +',' + data[8]+',' + data[9]+',' + data[10]+ ')"><i class="fas fa-edit"></i></button> '
                            +'<button type="button" class="btn btn-danger btn-sm" onclick="eliminar(' + data[0] + ')"><i class="fas fa-trash"></i></button>';                          }


                                  }],
                        "language": {
                            "decimal": "",
                            "emptyTable": "No hay registros en la tabla",
                            "info": "Se muestran _START_ a _END_ de _TOTAL_ registros",
                            "infoEmpty": "Se muestran 0 a 0 de 0 registros",
                            "infoFiltered": "(filtrado de _MAX_ registros totales)",
                            "infoPostFix": "",
                            "thousands": ",",
                            "lengthMenu": "Mostrar _MENU_ registros",
                            "loadingRecords": "Cargando...",
                            "processing": "Procesando...",
                            "search": "Search:",
                            "zeroRecords": "No se encontraron registros que coincidan",
                            "paginate": {
                              "first": "Primero",
                              "last": "Último",
                              "next": "Siguiente",
                              "previous": "Anterior"
                            },
                            "aria": {
                              "sortAscending": ": activar para ordenar la columna ascendente",
                              "sortDescending": ": activar para ordenar la columna descendente"
                            }
                        }                              
                    });
                })
                
            } 
    }
    xhr.send(JSON.stringify(datos = {bandera: 5}));
} 
           listar();

           modificar = function(codigo, articulo, compra, venta, cantidad, marca, impuesto, deposito) {
            $('#modal-editar').modal('toggle');
            $('#codigo').val(codigo);
            $('#nombr').val(articulo); 
            $('#precm').val(compra);
            $('#prevent').val(venta);
            $('#cantida').val(cantidad);
            $('#id_marc').val(marca);
            $('#id_marc').trigger('change');
            $('#id_im').val(impuesto);
            $('#id_im').trigger('change');
            $('#id_deposit').val(deposito);
            $('#id_deposit').trigger('change');

        };

        $("#form_editar").submit(function (e) {
          e.preventDefault();
          if ($('#nombr').val() == "") {
            toastr.warning('Favor registrar una descripcion.');
        }else if ($('#id_marc').val() == 0) {
          toastr.warning('Favor registrar una marca.');
      }else if ($('#precm').val() == "") {
        toastr.warning('Favor registrar un precio de compra.');
      }else if ($('#prevent').val() == "") {
        toastr.warning('Favor registrar un precio de compra.');
      }else if ($('#cantida').val() == "") {
        toastr.warning('Favor registrar una cantidad.');
      }else if ($('#id_im').val() == 0) {
          toastr.warning('Favor registrar un impuesto.');
      }else if ($('#id_deposit').val() == 0) {
        toastr.warning('Favor registrar un deposito.');
      } else {
            $("#modificar").attr("disabled", "disabled");
            dato = {bandera: 2, id_articulo: $("#codigo").val(), id_impuesto: $('#id_im').val(), id_marca: $('#id_marc').val(), art_descri: $('#nombr').val(), art_precioc: $('#precm').val(), art_preciov: $('#prevent').val(), id_deposito: $('#id_deposit').val(), stock_cant: $("#cantida").val()}; 
                       $.ajax({
              url: "/reina/ArticuloCRTL",
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
            dat = {bandera: 3, id_articulo: codigo};
              $.ajax({
                url: '/reina/ArticuloCRTL',
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
        
        
        };
        
          generarPDF = function() {
    var xhr = new XMLHttpRequest(), //
    method = "POST",
    url = "/reina/ArticuloCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      //la funcion devuelve una url
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        var url = xhr.responseText;
        //redireccionamos con javascript
        window.open('../'+url, '_blank');
        console.log(url);
      }
       
    };
    xhr.send(JSON.stringify(datos = {bandera: 6}));
  };
  
});
