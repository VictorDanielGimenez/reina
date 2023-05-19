$(function () {
  //cargar el ultimo id
  function LastId() {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/CompraCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        //convertimos el json a un array
        var json = JSON.parse(xhr.responseText);
        document.getElementById("id_compra").value = json[0].id_compra;
        console.log(json[0].id_compra);
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 6 })));
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
        document.getElementById("id_usuario").value = json[0].id_usuario;
        document.getElementById("usu_nombre").value = json[0].usu_nombre;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 6 })));
  }
  LogUser();
  //
  

  function listarDocumento() {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/CompraCRTL";
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
            json[i].id_tipodoc +
            ">" +
            json[i].tipo_decri +
            "</option>";
        }
        document.getElementById("id_doc").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 8 })));
  }

  listarDocumento(); //
  
  //
  function listarOrden() {
    var xhr = new XMLHttpRequest(), //
      method = "POST",
      url = "/reina/OrdenCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        var json = JSON.parse(xhr.responseText);
        var i;
        var valorOption = "";
        valorOption += "<option value=0>--- Seleccionar ---</option>";
        for (i = 0; i < json.length; i++) {
          //convertir la fecha a formato dd/mm/yyyy
          var fecha = json[i].orden_fecha;
          var fecha2 = fecha.split("-");
          var fecha3 = fecha2[2] + "/" + fecha2[1] + "/" + fecha2[0];

       
         valorOption +=
            "<option value=" +
            json[i].id_orden +
            ">" +
            json[i].id_orden +
            " - " +
            json[i].usu_nombre +
            " - " +
            fecha3 +
            " - " +
            json[i].orden_monto +
            "</option>";
        }
        document.getElementById("id_orden").innerHTML = valorOption;
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 9})));
  }

  listarOrden();

 


  function checkId(cod) {
    console.log(cod);
    let ids = document.querySelectorAll('#listadodetalle td[id="cod"]');
    return [].filter.call(ids, (td) => td.textContent === cod).length === 1;
  }
  function limpiarInputMercaderia(cod) {
    $("#cantidad_" + cod).val("");
    $("#precio_" + cod).val("");
  }

  $(document).on("click", ".delete", function (event) {
    if (confirm("Confirmar la eliminación de la Fila")) {
      event.preventDefault();
      $(this).closest("tr").remove();
      //actualizamos el total
      var total = 0;
      var iva5 = 0;
      var iva10 = 0;
      for (var i = 0; i < $("#listadodetalle tr").length; i++) {
        var subtotal =
          $("#listadodetalle tr:eq(" + i + ") td:eq(2)").text() *
          $("#listadodetalle tr:eq(" + i + ") td:eq(3)").text();
        total += subtotal;
        iva5 += $("#listadodetalle tr:eq(" + i + ") td:eq(6)").text();
        iva10 += $("#listadodetalle tr:eq(" + i + ") td:eq(7)").text();
        console.log(total);
        console.log(iva5);
        console.log(iva10);
      }
      $("#total").val(total);
      $("#iva5").val(iva5);
      $("#iva10").val(iva10);
    } else {
      limpiarInputMercaderia();
    }
  });

  function mostrarSolicitud_Compra(codigo) {
    var xhr = new XMLHttpRequest(),
      method = "POST",
      url = "/reina/OrdenCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        //            alert(xhr.responseText);
        var json = JSON.parse(xhr.responseText);
        console.log(json);

        //hacemos un recorrido para separar los datos
        for (i = 0; i < json.length; i++) {
          for (j = 0; j < json[i].length; j++) {
     
            var tindex;
            var cod = json[i][j].id_articulo;
            var art = json[i][j].art_descri;
            var cant = json[i][j].det_cant;
            var prec = json[i][j].det_precio;
            var exenta = json[i][j].exenta;
            var iva5 = json[i][j].iva5;     
            var iva10 = json[i][j].iva10;     
            var sub = json[i][j].det_precio * json[i][j].det_cant;
            var total = 0; 
         
            tindex++;

            
            

           

            $("#listadodetalle").append(
              "<tr id='prod" +
                tindex +
                "'>\n\
            <td for='cod' id='cod' style=' width: 10%;'>" +
                cod +
                "</td>\n\
            <td id='art' style=' width: 40%;'>" +
                art +
                "</td>\n\
            <td id='cant' style=' width: 10%;'>" +
                cant +
                "</td>\n\
            <td id='prec' style=' width: 20%;'>" +
                prec +
                "</td>\n\
                <td id='subtotal' style=' width: 20%;'>" +
                sub +
                "</td>\n\
                <td id='exenta' style=' width: 20%;'>" +
                exenta +
                "</td>\n\
                <td id='iva5' style=' width: 20%;'>" +
                iva5 +
                "</td>\n\
                <td id='iva10' style=' width: 20%;'>" +
                iva10 +
                "</td>\n\
            <td style=' width: 5%;'><img class='delete' onclick=\"$('#prod" +
                tindex +
                "')\" src='../public/img/delete.png'/></td>\n\
            </tr>"
            );         
           
          }
        }
        //calculamos el subtotal de cada fila
      for (var i = 0; i < $("#listadodetalle tr").length; i++) {
        var subtotal =
          $("#listadodetalle tr:eq(" + i + ") td:eq(2)").text() *
          $("#listadodetalle tr:eq(" + i + ") td:eq(3)").text();
        total += subtotal;  
            
      }
      $("#total").val(total);
  

      
      
      
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 8, id_orden: codigo })));
  }

  function mostrarProveedor(codigo) {
    var xhr = new XMLHttpRequest(),
      method = "POST",
      url = "/reina/OrdenCRTL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        $("#id_proveedor").val();
        $("#proveedor").val();
        var json = JSON.parse(xhr.responseText);


        //hacemos un recorrido para separar los datos
        for (i = 0; i < json.length; i++) {
          for (j = 0; j < json[i].length; j++) {
     
            var id_proveedor = json[i][j].id_proveedor;
            var proveedor = json[i][j].proveedor;

            $("#id_proveedor").val(id_proveedor);
            $("#proveedor").val(proveedor);
                      
          }
        }
      
      
      
      }
    };
    xhr.send(JSON.stringify((datos = { bandera: 4, id_orden: codigo })));
  }

  $("#id_orden").on("change", function () {
    var id = $(this).val();   
    mostrarProveedor(id);
    mostrarSolicitud_Compra(id);
    

  });
  
  $("#id_doc").on("change", function () {
    var id = $(this).val();    
    if (id == 2) {
      $("#div_cuota").show();
      $("#div_intervalo").show();
       $("#cuota").val();
      $("#intervalo").val();
    } else {
      $("#div_cuota").hide();
      $("#div_intervalo").hide();
      $("#cuota").val(0);
      $("#intervalo").val(0);
    }
   
  });

  procesarJSON = function () {
    var listaMercaderia = [];
    //recorremos la tabla para obtener los datos

    $("#listadodetalle  tr").each(function () {
      listaMercaderia.push({
        id_articulo: $(this).find("td").eq(0).html()
          ? $(this).find("td").eq(0).html()
          : 0,
        det_cant: $(this).find("td").eq(2).html()
          ? $(this).find("td").eq(2).html()
          : 0,
        det_precio: $(this).find("td").eq(3).html()
          ? $(this).find("td").eq(3).html()
          : 0,
      });
    });

    valores = {
      bandera: 1,
      id_usuario:	document.getElementById("id_usuario").value,
      id_pedidocompra: document.getElementById("id_pedido").value,
      id_proveedor: document.getElementById("id_proveedor").value,
      orden_fecha: document.getElementById("fecha_solicitud").value,
      orden_monto : document.getElementById("total").value,
      lista_articulo: listaMercaderia,
    };
    console.log(valores);
    $.ajax({
      url: "/reina/OrdenCRTL",
      method: "POST",
      data: JSON.stringify(valores),
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
              window.location.href = "/reina/vistas/orden.html";
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
  };
});
