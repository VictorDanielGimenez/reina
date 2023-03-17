$(function () {

  //cargar el ultimo id
  function LastId() {
    var xhr = new XMLHttpRequest(), //
    method = "POST",
    url = "/reina/PedidoCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //convertimos el json a un array
            var json = JSON.parse(xhr.responseText);
            document.getElementById('id_pedido').value = json[0].id_pedidocompra;
          } 
    }
    xhr.send(JSON.stringify(datos = {bandera: 6}));
} 
LastId();


function LogUser() {
  var xhr = new XMLHttpRequest(), //
  method = "POST",
  url = "/reina/UsuarioCRTL";
  xhr.open(method, url, true);
  xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
          //convertimos el json a un array
          var json = JSON.parse(xhr.responseText);
          document.getElementById('id_usuario').value = json[0].id_usuario;
          document.getElementById('usu_nombre').value = json[0].usu_nombre;
        } 
  }
  xhr.send(JSON.stringify(datos = {bandera: 6}));
} 
LogUser()
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
                              json[i].art_descri,
                              json[i].id_articulo,
                              json[i].id_articulo,
                              json[i].id_articulo,
                            ]);   //10                  
            }
            $(document).ready(function () {
                var table = $('#listadoArticulos').DataTable({
                    data: dataSet,                           
                    columns: [
                        {title: 'ID'},
                        {title: 'Articulo'},
                        {title: 'Cantidad'},
                        {title: 'Precio'},
                        {title: 'Acciones'}
                    ],
                    columnDefs: [
                      {
                        targets: 1,
                        data: null,
                        render: function(data, type, row) {
                            return '<input id="articulo_' + data[0] + '" value="'+ data[1] + '"  class="form-control" type="text" readonly />';
                        }
                    }, {
                        targets: 2,
                        data: null,
                        render: function(data, type, row) {
                            return '<input id="cantidad_' + data[0] + '"  class="form-control" type="number" />';
                        }
                    } ,{
                      targets: 3,
                      data: null,
                      render: function(data, type, row) {
                          return '<input id="precio_' + data[0] + '" class="form-control" type="number" />';
                      }
                  } ,
                      {
                        targets: 4,
                        data: null,
                        render: function (data, type, row) {
                            return '<button type="button" class="btn btn-success btn-sm" onclick="agregar(' + data[0] + ');"><i class="fas fa-plus"></i></button>'; 
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

           agregar = function(id){
            if($('#precio_'+id).val() == "" || $('#cantidad_'+id).val() == "" ){
              toastr.warning('Favor cargar cantidad y precio.');
            }else{
            var tindex;
            var cod = id;
            var art = document.getElementById('articulo_'+id).value;          
            var cant = document.getElementById('cantidad_'+id).value;
            var prec = document.getElementById('precio_'+id).value;
            var total = 0;
          
            tindex++;

            //comprobar si ya existe codigo en la tabla para agrupar
            var existe = false;
            var cantAnterior = 0;            

            $('#listadodetalle tr').each(function () {
                var codTabla = $(this).find("td").eq(0).html();
                if (codTabla == cod) {
                  //actualizar cantidad anterior mas la nueva cantidad
                    cantAnterior = $(this).find("td").eq(2).html();                   ;
                    cant = parseInt(cantAnterior) + parseInt(cant);
                    prec = parseInt(prec);
                    //borramos el anterior para no tener duplicados
                    $(this).remove();
                     //actualizamos el total
                   
                    existe = true;               
                }                
            });

           


            

         
               
              
          
        
            $('#listadodetalle').append("<tr id=\'prod" + tindex + "\'>\n\
            <td for='cod' id='cod' style=' width: 10%;'>" + cod + "</td>\n\
            <td id='art' style=' width: 40%;'>" + art + "</td>\n\
            <td id='cant' style=' width: 10%;'>" + cant + "</td>\n\
            <td id='prec' style=' width: 20%;'>" + prec + "</td>\n\
            <td style=' width: 5%;'><img class='delete' onclick=\"$(\'#prod" + tindex + "\')\" src='../public/img/delete.png'/></td>\n\
            </tr>");
            limpiarInputMercaderia(cod);
            //calculamos el subtotal de cada fila  
            for (var i = 0; i < $('#listadodetalle tr').length; i++) {
              var subtotal = $('#listadodetalle tr:eq(' + i + ') td:eq(2)').text() * $('#listadodetalle tr:eq(' + i + ') td:eq(3)').text();             
              total += subtotal;
            }
            $("#total").val(total);
            

          }
        } 
        function checkId(cod) {
          console.log(cod);
          let ids = document.querySelectorAll('#listadodetalle td[id="cod"]');
          return [].filter.call(ids, td => td.textContent === cod).length === 1;
          
      }
      function limpiarInputMercaderia(cod) {
        $('#cantidad_'+cod).val("");
        $('#precio_'+cod).val("");    
      
    }  

    $(document).on('click', '.delete', function (event) {
      if (confirm('Confirmar la eliminación de la Fila')) {
          event.preventDefault();
          $(this).closest('tr').remove();
          //actualizamos el total
          var total = 0;
          for (var i = 0; i < $('#listadodetalle tr').length; i++) {
            var subtotal = $('#listadodetalle tr:eq(' + i + ') td:eq(2)').text() * $('#listadodetalle tr:eq(' + i + ') td:eq(3)').text();             
            total += subtotal;
          }
          $("#total").val(total);
      } else {
          limpiarInputMercaderia();
      }
  });




  


  procesarJSON = function() {

      var listaMercaderia = [];
      //recorremos la tabla para obtener los datos    
      
      $('#listadodetalle tr').each(function() {
        var id_articulo = $(this).find("td").eq(0).html() ? $(this).find("td").eq(0).html() : 0;
        var det_cant = $(this).find("td").eq(2).html() ? $(this).find("td").eq(2).html() : 0;
        var det_precio = $(this).find("td").eq(3).html() ? $(this).find("td").eq(3).html()  : 0;    
        if (id_articulo != 0 && det_cant != 0 && det_precio != 0) {
          listaMercaderia.push({
              "id_articulo": id_articulo,
              "det_cant": det_cant, 
              "det_precio": det_precio
          });
        }       
      });
  
      valores = {bandera: 1, ped_fecha: document.getElementById('fecha_solicitud').value, ped_monto: document.getElementById('total').value, id_usuario: document.getElementById('id_usuario').value, lista_articulo: listaMercaderia};
     console.log(valores);
    $.ajax({
                url: '/reina/PedidoCRTL',
                method: 'POST',
                data:JSON.stringify(valores),
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
                                window.location.href = "/reina/vistas/pedido.html";
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
    
  };
});
