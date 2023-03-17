$(function () {
  
  //creamos una funcion para traer un json
   function listar() {
    var xhr = new XMLHttpRequest(), //
    method = "POST",
    url = "/reina/OrdenCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //convertimos el json a un array
            var json = JSON.parse(xhr.responseText);
           
            var dataSet = [];
            for (var i = 0; i < json.length; i++) {
              //convertir la fecha a dd/mm/yyyy
              var fecha = json[i].orden_fecha;
              var fecha2 = fecha.split("-");             
              var fecha3 = fecha2[2] + "/" + fecha2[1] + "/" + fecha2[0];

              
                    
                dataSet.push([json[i].id_orden,                             
                              fecha3,
                              json[i].usu_nombre,
                              json[i].id_pedidocompra,
                              json[i].razon,
                              json[i].orden_monto,
                              json[i].est_descri,                              
                              json[i].id_orden,
                            ]);   //10                  
            }
            console.log(dataSet);
            $(document).ready(function () {
                var table = $('#listado').DataTable({
                    data: dataSet,                           
                    columns: [
                        {title: 'ID'},
                        {title: 'Fecha'},
                        {title: 'Usuario'},
                        {title: 'Pedido No.'},
                        {title: 'Proveedor'},
                        {title: 'Monto'},
                        {title: 'Estado'},
                        {title: 'Acciones'}
                    ],
                    columnDefs: [
                     
                      {
                        targets: 7,
                        data: null,
                        render: function (data, type, row) {
                          return data[6] == 'PENDIENTE' ? '<button type="button" class="btn btn-success btn-sm" onclick="anular(' + data[0] + ');"><i class="fas fa-plus"></i></button>' :
                          '<button class="btn btn-default btn-sm" ' +
                            'onclick="javascript:void(0);" style="background-color: gray;' +
                            'color: white; cursor:not-allowed"><i class="fas fa-check"></i></button>';
                        }


                                  }]
                                                    
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
              text: "Está seguro que desea anular la Orden de Compra?",
              type: "warning",
              confirmButtonText: "SI",
              confirmButtonColor: "#5cb85c",
              showCancelButton: true,
              cancelButtonText: "NO",
            }, function(isConfirm) {
              if (isConfirm) {
              dat = {bandera: 3, id_orden: codigo};
                $.ajax({
                  url: '/reina/OrdenCRTL',
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
