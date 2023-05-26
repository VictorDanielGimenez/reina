$(function () {
    $('#form').submit(function(e) {
        e.preventDefault();
        if ($('#nombre').val() == "") {
            toastr.warning('Favor registrar una descripcion del cargo.');
        } else {
            datos = {bandera: 1, ciu_descri: $("#nombre").val()};
            $('#guardar').attr("disabled", "disabled");
            $.ajax({
                url: '/reina/CiudadCRTL',
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
        url = "/reina/CiudadCRTL";
        xhr.open(method, url, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                //convertimos el json a un array
                var json = JSON.parse(xhr.responseText);
               
                var dataSet = [];
                for (var i = 0; i < json.length; i++) {
                    dataSet.push([json[i].id_ciudad, json[i].ciu_descri,json[i].id_ciudad,]);                    
                }
                $(document).ready(function () {
                    var table = $('#listado').DataTable({
                        data: dataSet,                           
                        columns: [
                            {title: 'ID'},
                            {title: 'Ciudad'},
                            {title: 'Acciones'}
                        ],
                        columnDefs: [{
                            //GUARDAMOS EL VALOR DE PRIMERA COLUMNA EN UNA VARIABLE
                            targets: 2,
                            data: null,
                            render: function (data, type, row) {
                                return '<button type="button" class="btn btn-warning btn-sm" onclick="modificar(' + data[0] + ',\'' + data[1] + '\')"><i class="fas fa-edit"></i></button> '
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


                modificar = function(codigo, nombres) {
                    $('#modal-editar').modal('toggle');
                    $('#codigo').val(codigo);
                    $('#nombres').val(nombres); 
                };

$('#form_editar').submit(function(e) {
  e.preventDefault();
  if ($('#nombres').val() == "") {
    toastr.warning('Favor registrar una descripcion del cargo.');
  } else {
    $('#modificar').attr("disabled", "disabled");
    dato = {bandera: 2, id_ciudad: $("#codigo").val(), ciu_descri: $("#nombres").val()};
    $.ajax({
        url: '/reina/CiudadCRTL',
      method: 'POST',
      data: JSON.stringify(dato),
                success: function(data) {
        try {
          response = data;
          if (response.status == "success") {
            $('#modal-editar').modal('hide');
            toastr.success(response.message);
            setTimeout(function() {
                location.reload();
              $('#modificar').removeAttr("disabled");
            }, 3000);
          }  else {
                            toastr.error(response.message)
                            $('#guardar').removeAttr("disabled");
                        }
        } catch (error) {
          toastr.error('Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador del sistema');
          console.log(error);

        }
      },
      error: function(error) {
        toastr.error('Ocurrio un error intentado resolver la solicitud. Por favor contacte con el administrador de la red')
        console.log(error);
      }
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
    dat = {bandera: 3, id_ciudad: codigo};
      $.ajax({
        url: '/reina/CiudadCRTL',
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
  url = "/reina/CiudadCRTL";
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
