$(function () {
  
  //creamos una funcion para traer un json
   function listar() {
    var xhr = new XMLHttpRequest(), //
    method = "POST",
    url = "/reina/PedidoCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //convertimos el json a un array
            var json = JSON.parse(xhr.responseText);
           
            var dataSet = [];
            for (var i = 0; i < json.length; i++) {
              //convertir la fecha a dd/mm/yyyy
              var fecha = json[i].ped_fecha;
              var fecha2 = fecha.split("-");             
              var fecha3 = fecha2[2] + "/" + fecha2[1] + "/" + fecha2[0];

              
                    
                dataSet.push([json[i].id_pedidocompra,
                  fecha3,
                              json[i].usu_nombre,
                              json[i].ped_monto,
                              json[i].est_descri,                              
                              json[i].id_pedidocompra,
                            ]);   //10                  
            }
           
            $(document).ready(function () {
                var table = $('#listado').DataTable({
                    data: dataSet,                           
                    columns: [
                        {title: 'ID'},
                        {title: 'Fecha'},
                        {title: 'Usuario'},
                        {title: 'Monto'},
                        {title: 'Estado'},
                        {title: 'Acciones'}
                    ],
                    columnDefs: [
                     
                      {
                        targets: 5,
                        data: null,
                        render: function (data, type, row) {
                          return data[4] == 'PENDIENTE' ? '<button type="button" class="btn btn-success btn-sm" onclick="anular(' + data[0] + ');"><i class="fas fa-plus"></i></button>' :
                          '<button class="btn btn-default btn-sm" ' +
                            'onclick="javascript:void(0);" style="background-color: gray;' +
                            'color: white; cursor:not-allowed"><i class="fas fa-check"></i></button>';
                        }


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

           anular = function(codigo) {
            swal({
              title: "Confirmar",
              text: "Está seguro que desea anular el pedido?",
              type: "warning",
              confirmButtonText: "SI",
              confirmButtonColor: "#5cb85c",
              showCancelButton: true,
              cancelButtonText: "NO",
            }, function(isConfirm) {
              if (isConfirm) {
              dat = {bandera: 3, id_pedidocompra: codigo};
                $.ajax({
                  url: '/reina/PedidoCRTL',
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

          

});
