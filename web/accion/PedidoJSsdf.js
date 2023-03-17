function Fecha() {

    var someDateString = moment().format("YYYY-MM-DD");
//    var someDate = moment(someDateString, "YYYY/MM/DD");
    moment().format("YYYY/MM/DD");
//    (document.getElementById('fecha_solicitud').value === '' ? 0 : document.getElementById('fecha_solicitud').value)
    document.getElementById('fecha_solicitud').value = someDateString;
}



function agregarFilaMercaderia() {
    var tindex;
    var cod = document.getElementById('codigo_mercaderia').value;
    var med = document.getElementById('medida').value;
    var des = document.getElementById('mercaderia').value;
    var cant = document.getElementById('cantidad_mercaderia').value;
    var mar = document.getElementById('marca').value;
    tindex++;

    if (checkId(cod)) {
        var des = document.getElementById('mercaderia').value;
        return alert(des + ' ya fue cargado a la tabla detalle');
    }

    $('#detalleTablaMercaderia').append("<tr id=\'prod" + tindex + "\'>\n\
    <td for='cod' id='cod' style=' width: 10%;'>" + cod + "</td>\n\
    <td id='des' style=' width: 10%;'>" + med + "</td>\n\
    <td id='des' style=' width: 40%;'>" + des + "</td>\n\
    <td id='mar' style=' width: 20%;'>" + mar + "</td>\n\
    <td id='cant' style=' width: 10%;'>" + cant + "</td>\n\
    <td style=' width: 5%;'><img class='update' onclick=\"$(\'#prod" + tindex + "\')\" src='../Recursos/Img/update.png'/></td>\n\
    <td style=' width: 5%;'><img class='delete' onclick=\"$(\'#prod" + tindex + "\')\" src='../Recursos/Img/delete.png'/></td>\n\
    </tr>");
}

function checkId(cod) {
    let ids = document.querySelectorAll('#detalleTablaMercaderia td[for="cod"]');
    return [].filter.call(ids, td => td.textContent === cod).length === 1;
}

function limpiarInputMercaderia() {
    document.getElementById('codigo_mercaderia').value = '';
    document.getElementById('medida').value = '';
    document.getElementById('mercaderia').value = '';
    document.getElementById('marca').value = '';
    document.getElementById('cantidad_mercaderia').value = '';
    document.getElementById('cantidad_mercaderia').focus();
}

$(document).on('click', '.delete', function (event) {
    if (confirm('Confirmar la eliminación de la Fila')) {
        event.preventDefault();
        $(this).closest('tr').remove();
    } else {
        limpiarInputMercaderia();
    }
});

$(document).ready(function () {
    $("#detalleTablaMercaderia").on('click', '.update', function (e) {
        if (confirm('Desea Modificar la Fila')) {
            e.preventDefault();
            var currentRow = $(this).closest("tr");
            var col1 = currentRow.find("td:eq(0)").text();
            var col2 = currentRow.find("td:eq(1)").text();
            var col3 = currentRow.find("td:eq(2)").text();
            var col4 = currentRow.find("td:eq(3)").text();
            var col5 = currentRow.find("td:eq(4)").text();

            $("#codigo_mercaderia").val(col1);
            $("#medida").val(col2);
            $("#mercaderia").val(col3);
            $("#marca").val(col4);
            $("#cantidad_mercaderia").val(col5);
            $(this).closest('tr').remove();
        } else {
            return false;
        }
    });
});

function verificar(evt) {
    var cod = document.getElementById('codigo_mercaderia').value;
    var med = document.getElementById('medida').value;
    var des = document.getElementById('mercaderia').value;
    var mar = document.getElementById('marca').value;
    var can = document.getElementById('cantidad_mercaderia').value;

    if (cod.length === 0 || med.length === 0 || des.length === 0 || mar.length === 0 || des.length === 0) {
        if (confirm('Debe Ingresar una Mercaderia')) {
            evt.preventDefault();
            document.getElementById('cantidad_mercaderia').focus();
        } else {
            limpiarInputMercaderia();
            evt.preventDefault();
        }
    }
    if (can.length === 0) {
        if (confirm('Debe ingresar la Cantidad')) {
            evt.preventDefault();
            document.getElementById('cantidad_mercaderia').focus();
        } else {
            limpiarInputMercaderia();
            evt.preventDefault();
        }
    }
}

function procesarJSON(bandera) {

    var listaMercaderia = [];
    $("#detalleTablaMercaderia  tr").each(function () {

        //push => Agrega un nuevo elemento al Array [listaProductos]
        listaMercaderia.push({
//            nro_solicitud: $(this).find("td").eq(0).html(),
            cod_mercaderia: $(this).find("td").eq(0).html(),
            cantidad: $(this).find("td").eq(4).html()
        });
    });

    valores = {
        bandera: bandera,
        nro_solicitud: (document.getElementById('codigo_solicitud').value === '' ? 0 : document.getElementById('codigo_solicitud').value),
        fecha: document.getElementById('fecha_solicitud').value,
        cod_empleado: document.getElementById('codigo_empleado').value,
        cod_deposito: document.getElementById('codigo_deposito').value,
        cod_departamento: document.getElementById('codigo_departamento').value,
        prioridad: document.getElementById('menuPrioridad').value,
        descripcion: document.getElementById('observacion_solicitud').value,
        lista_mercaderia: listaMercaderia
    };
    envioCabDet();
}

///GenerarPedidosCTRL
function envioCabDet() {
    var xmlhttp = new XMLHttpRequest();   // objeto para peticion vía ajax 
    xmlhttp.open("POST", "/JavaWeb_Compras/Solicitud_CompraCTR");// tipo de envio -  destino de envio
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8"); // Es el formato de envio de datos  
    xmlhttp.send(JSON.stringify(valores));
}

function agregarSolicitud() {
    var listaMercaderia = [];
    var fecha = $('#fecha_solicitud').val();
    var cod_empleado = $('#codigo_empleado').val();
    var cod_departamento = $('#codigo_departamento').val();
    var cod_deposito = $('#codigo_deposito').val();
    var prioridad = $('#menuPrioridad').val();
    var descripcion = $('#observacion_solicitud').val();
    '#detalleTablaMercaderia' === listaMercaderia.length <= 0 ? "0" : listaMercaderia;

    if (fecha.length === 0 || cod_empleado.length === 0 || cod_departamento.length === 0
            || cod_deposito.length === 0 || prioridad.length === 0 || descripcion.length === 0
//            || lista_mercaderia.length === 0 ? "0" : lista_mercaderia
            ) {
        alert('Debe completar todos los campos');
        limpiarSolcitud();
    } else {
        if (confirm('Confirmar la inserción de Datos')) {
            procesarJSON(1);
        } else {
            //limpiar();
        }
    }
}

function eliminarSolicitud() {
    var verificar_codigo = $('#codigo_solicitud').val();
    if (verificar_codigo.length === 0) {
        alert('Debe Seleccionar el registro a ser Eliminado');
    } else {
        if (confirm('Confirmar la eliminación de Datos')) {
            procesarJSON(3);
        } else {
            //limpiar();
        }
    }
}

function mostrarSolicitud() {
    var xhr = new XMLHttpRequest(),
            method = "POST",
            url = "/JavaWeb_Compras/Solicitud_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
//            alert(xhr.responseText);
            var json = JSON.parse(xhr.responseText);
            var i;
            var d;
            var tindex;
            var filas = "";
            for (i in json) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].nro_solicitud + "</td>";
                filas += "<td>" + json[i].fecha + "</td>";
                filas += "<td>" + json[i].prioridad + "</td>";
                filas += "<td>" + json[i].empleado_descripcion + "</td>";
                filas += "<td>" + json[i].departamento_descripcion + "</td>";
                filas += "<td>" + json[i].descripcion + "</td>";
                filas += "<td> <img onclick=\"RecuperarDeBuscadorSolicitud(\n\
                    " + json[i].nro_solicitud + " ,\n\
//                    '" + json[i].fecha + "' ,\n\
//                    '" + json[i].cod_empleado + "',\n\
//                    '" + json[i].empleado_descripcion + "',\n\
//                    '" + json[i].cod_departamento + "',\n\
//                    '" + json[i].departamento_descripcion + "',\n\
//                    '" + json[i].cod_deposito + "',\n\
//                    '" + json[i].deposito_descripcion + "',\n\
//                    '" + json[i].sucursal_descripcion + "',\n\
//                    '" + json[i].prioridad + "' ,\n\
//                    '" + json[i].descripcion + "',\n\
////                    '" + json[i].lista_mercaderia + "',\n\
                    'codigo_solicitud' \n\
//                    'fecha_solicitud', \n\
//                    'codigo_empleado', \n\
//                    'empleado', \n\
//                    'codigo_departamento', \n\
//                    'departamento', \n\
//                    'codigo_deposito', \n\
//                    'deposito', \n\
//                    'sucursal', \n\
//                    'menuPrioridad', \n\
//                    'observacion_solicitud'); \n\
////                    'detalleTablaMercaderia'\n\
                    ); \n\
                    \" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
//                alert(filas);
                for (d in json[i].lista_mercaderia) {
//                        alert(json[x].lista_mercaderia[d].cod_mercaderia);
//                        alert(json[x].lista_mercaderia[d].descripcion);
                    alert(json[i].lista_mercaderia[d].descripcion_marca);
//                        alert(json[x].lista_mercaderia[d].cantidad);
                    $('#detalleTablaMercaderia').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td for='cod' id='cod' style=' width: 10%;'>" + json[i].lista_mercaderia[d].cod_mercaderia + "</td>\n\
                                <td id='med' style=' width: 10%;'>" + json[i].lista_mercaderia[d].descripcion_medida + "</td>\n\
                                <td id='des' style=' width: 40%;'>" + json[i].lista_mercaderia[d].articulo + "</td>\n\
                                <td id='mar' style=' width: 20%;'>" + json[i].lista_mercaderia[d].descripcion_marca + "</td>\n\
                                <td id='cant' style=' width: 10%;'>" + json[i].lista_mercaderia[d].cantidad + "</td>\n\
                                <td style=' width: 5%;'><img class='update' onclick=\"$(\'#prod" + tindex + "\')\" src='../Recursos/Img/update.png'/></td>\n\
                                <td style=' width: 5%;'><img class='delete' onclick=\"$(\'#prod" + tindex + "\')\" src='../Recursos/Img/delete.png'/></td>\n\
                          </tr>");
                    alert(json[i].lista_mercaderia);
                }
            }
            document.getElementById("Tabla_Solicitud").innerHTML = filas;
            document.getElementById('datos_Abuscar_Solicitud').style.display = 'block';
            document.getElementById("filtro_buscador_Solicitud").focus();
            document.getElementById("imprimir").disabled=false;
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 4}));
}

function RecuperarDeBuscadorSolicitud(nro_solicitud, codigo_solicitud) {

    document.getElementById(codigo_solicitud).value = nro_solicitud;
    RecuperarSolicitud();
//    document.getElementById(fecha_solicitud).value = fecha;
//    document.getElementById(codigo_empleado).value = cod_empleado;
//    document.getElementById(empleado).value = empleado_descripcion;
//    document.getElementById(codigo_departamento).value = cod_departamento;
//    document.getElementById(departamento).value = departamento_descripcion;
//    document.getElementById(codigo_sucursal).value = cod_sucursal;
//    document.getElementById(sucursal).value = sucursal_descripcion;
//    document.getElementById(empresa).value = empresa_descripcion;
//    document.getElementById(menuPrioridad).value = prioridad;
//    document.getElementById(observacion_solicitud).value = descripcion;


//    document.getElementById(detalleTablaMercaderia).value = lista_mercaderia;

//    document.getElementById(observacion_solicitud).focus();
    document.getElementById('datos_Abuscar_Solicitud').style.display = 'none';
}


function RecuperarSolicitud() {
    // alert("Llegamos...");
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");

    xhr.onreadystatechange = function () {
        // alert(xhr.responseText);
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var x;
                var d;
                var tindex;

                for (x in json) {
                    document.getElementById('codigo_solicitud').value = json[x].nro_solicitud;
                    document.getElementById('fecha_solicitud').value = json[x].fecha;
                    document.getElementById('codigo_empleado').value = json[x].cod_empleado;
                    document.getElementById('empleado').value = json[x].empleado_descripcion;
                    document.getElementById('codigo_departamento').value = json[x].cod_departamento;
                    document.getElementById('departamento').value = json[x].departamento_descripcion;
                    document.getElementById('codigo_deposito').value = json[x].cod_deposito;
                    document.getElementById('deposito').value = json[x].deposito_descripcion;
                    document.getElementById('sucursal').value = json[x].sucursal_descripcion;
                    document.getElementById('menuPrioridad').value = json[x].prioridad;
                    document.getElementById('observacion_solicitud').value = json[x].descripcion;

                        $('#detalleTablaMercaderia').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td for='cod' id='cod' style=' width: 10%;'>" + json[x].cod_mercaderia + "</td>\n\
                                <td id='des' style=' width: 10%;'>" + json[x].descripcion_medida + "</td>\n\
                                <td id='med' style=' width: 40%;'>" + json[x].articulo + "</td>\n\
                                <td id='mar' style=' width: 20%;'>" + json[x].marca_descripcion + "</td>\n\
                                <td id='cant' style=' width: 10%;'>" + json[x].cantidad + "</td>\n\
                                <td style=' width: 5%;'><img class='update' onclick=\"$(\'#prod" + tindex + "\')\" src='../Recursos/Img/update.png'/></td>\n\
                                <td style=' width: 5%;'><img class='delete' onclick=\"$(\'#prod" + tindex + "\')\" src='../Recursos/Img/delete.png'/></td>\n\
                          </tr>");
                    
                }
            }
        }
    }
    ;
    xhr.open('POST', '/JavaWeb_Compras/Solicitud_CompraCTR');
    xhr.send(JSON.stringify(datos = {bandera: 2, nro_solicitud: $('#codigo_solicitud').val()}));
    $('#codigo_solicitud').disabled;
// 3. Specify your action, location and Send to the server - End
}

//}
//            document.getElementById("Tabla_Solicitud").innerHTML = filas;
//            document.getElementById('datos_Abuscar_Solicitud').style.display = 'block';
//            document.getElementById("filtro_buscador_Solicitud").focus();
//        }
//    };
//    xhr.send(JSON.stringify(datos = {bandera: 4}));
//}

function buscador(filtro, tabla) {
    var input, filter, table, tr, td, i;
    input = document.getElementById(filtro);
    filter = input.value.toUpperCase();
    table = document.getElementById(tabla);
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        visible = false;
        td = tr[i].getElementsByTagName("td");
        for (j = 0; j < td.length; j++) {
            if (td[j] && td[j].innerHTML.toUpperCase().indexOf(filter) > -1) {
                visible = true;
            }
        }
        if (visible === true) {
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";
        }
    }
}

function mostrarEmpleadoSoli() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/JavaWeb_Compras/Solicitud_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
//            alert(xhr.responseText);
            var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
            var i;
            var filas = "";
            for (i = 0; i < json.length; i++) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].cod_empleado + "</td>";
                filas += "<td>" + json[i].emp_ci + "</td>";
                filas += "<td>" + json[i].nombre + " " + json[i].apellido + "</td>";
                filas += "<td> <img onclick=\"recuperarEmpleado(" + json[i].cod_empleado + " ,\n\
'" + json[i].nombre + ' ' + json[i].apellido + "', \n\
'codigo_empleado' , \n\
'empleado') \n\
\" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
            }
            document.getElementById("Tabla_Empleado").innerHTML = filas;
            document.getElementById('datos_Abuscar_Empleado').style.display = 'block';
            document.getElementById("filtro_buscador_Empleado").focus();
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 7}));
}

function recuperarEmpleado(cod_empleado, empleado_descripcion,
        codigo_empleado, empleado) {
    document.getElementById(codigo_empleado).value = cod_empleado;
    document.getElementById(empleado).value = empleado_descripcion;
    document.getElementById('empleado').focus();
    document.getElementById('datos_Abuscar_Empleado').style.display = 'none';
}

function mostrarDepartamento() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/JavaWeb_Compras/Solicitud_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
            var i;
            var filas = "";
            for (i = 0; i < json.length; i++) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].cod_departamento + "</td>";
                filas += "<td>" + json[i].descripcion + "</td>";
                filas += "<td> <img onclick=\"recuperarDepartamento(" + json[i].cod_departamento + " ,\n\
'" + json[i].descripcion + "', \n\
'codigo_departamento' , \n\
'departamento') \n\
\" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
            }
            document.getElementById("Tabla_Departamento").innerHTML = filas;
            document.getElementById('datos_Abuscar_Departamento').style.display = 'block';
            document.getElementById("filtro_buscador_Departamento").focus();
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 5}));
}

function recuperarDepartamento(cod_departamento, departamento_descripcion,
        codigo_departamento, departamento) {
    document.getElementById(codigo_departamento).value = cod_departamento;
    document.getElementById(departamento).value = departamento_descripcion;
    document.getElementById('departamento').focus();
    document.getElementById('datos_Abuscar_Departamento').style.display = 'none';
}

function mostrarDeposito() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/JavaWeb_Compras/Solicitud_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
            var i;
            var filas = "";
            for (i = 0; i < json.length; i++) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].cod_deposito + "</td>";
                filas += "<td>" + json[i].descripcion + "</td>";
                filas += "<td>" + json[i].sucursal_descripcion + "</td>";
                filas += "<td> <img onclick=\"recuperarDeposito(" + json[i].cod_deposito + " ,\n\
'" + json[i].descripcion + "', '" + json[i].sucursal_descripcion + "', \n\
'codigo_deposito' , 'deposito', 'sucursal') \n\
\" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
            }
            document.getElementById("Tabla_Deposito").innerHTML = filas;
            document.getElementById('datos_Abuscar_Deposito').style.display = 'block';
            document.getElementById("filtro_buscador_Deposito").focus();
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 8}));
}

function recuperarDeposito(cod_deposito, deposito_descripcion, sucursal_descripcion,
        codigo_deposito, deposito, sucursal) {
    document.getElementById(codigo_deposito).value = cod_deposito;
    document.getElementById(deposito).value = deposito_descripcion;
    document.getElementById(sucursal).value = sucursal_descripcion;
    document.getElementById('deposito').focus();
    document.getElementById('datos_Abuscar_Deposito').style.display = 'none';
}

function mostrarMercaderia() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/JavaWeb_Compras/Solicitud_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
//            alert(xhr.responseText);
            var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
            var i;
            var filas = "";
            for (i = 0; i < json.length; i++) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].cod_mercaderia + "</td>";
                filas += "<td>" + json[i].descripcion_medida + "</td>";
                filas += "<td>" + json[i].articulo + "</td>";
                filas += "<td>" + json[i].descripcion_marca + "</td>";
                filas += "<td> <img onclick=\"recuperarMercaderia(" + json[i].cod_mercaderia + " , '" + json[i].descripcion_medida + "', '" + json[i].descripcion_marca + "',\n\
'" + json[i].articulo + "',\n\
'codigo_mercaderia' , 'medida', 'marca', 'mercaderia') \n\
\" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
            }
            document.getElementById("Tabla_Mercaderia").innerHTML = filas;
            document.getElementById('datos_Abuscar_Mercaderia').style.display = 'block';
            document.getElementById("filtro_buscador_Mercaderia").focus();
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 6}));
}

function recuperarMercaderia(cod_mercaderia, descripcion_medida, articulo, descripcion_marca,
        codigo_mercaderia, medida, mercaderia, marca) {
    document.getElementById(codigo_mercaderia).value = cod_mercaderia;
    document.getElementById(medida).value = descripcion_medida;
    document.getElementById(mercaderia).value = articulo;
    document.getElementById(marca).value = descripcion_marca;
    document.getElementById('cantidad_mercaderia').focus();
    document.getElementById('datos_Abuscar_Mercaderia').style.display = 'none';
}

function limpiarSolcitud() {
    document.getElementById('codigo_solicitud').disabled = true;
    document.getElementById('fecha_solicitud').disabled = true;
    document.getElementById('codigo_empleado').disabled = true;
    document.getElementById('codigo_departamento').disabled = true;
    document.getElementById('codigo_deposito').disabled = true;
    document.getElementById('menuPrioridad').disabled = true;
    document.getElementById('observacion_solicitud').disabled = true;
    document.getElementById('codigo_mercaderia').disabled = true;
    document.getElementById('mercaderia').disabled = true;
    document.getElementById('cantidad_mercaderia').disabled = true;
//    document.getElementById("detalleTablaMercaderia").value = 0;
    $('#detalleTablaMercaderia').empty();

    document.getElementById('form_solicitud_compra').reset();
}

function habilitaInputSolicitud(codigo_empleado, codigo_departamento, codigo_deposito, menuPrioridad, observacion_solicitud, codigo_mercaderia, cantidad_mercaderia)
{
    var cod_empleado = document.getElementById(codigo_empleado);
    var departamento = document.getElementById(codigo_departamento);
    var deposito = document.getElementById(codigo_deposito);
    var prioridad = document.getElementById(menuPrioridad);
    var observacion = document.getElementById(observacion_solicitud);
    var mercaderia = document.getElementById(codigo_mercaderia);
    var cantidad = document.getElementById(cantidad_mercaderia);

    cod_empleado.disabled = !cod_empleado.disabled;
    departamento.disabled = !departamento.disabled;
    deposito.disabled = !deposito.disabled;
    prioridad.disabled = !prioridad.disabled;
    observacion.disabled = !observacion.disabled;
    mercaderia.disabled = !mercaderia.disabled;
    cantidad.disabled = !cantidad.disabled;


    document.getElementById(codigo_empleado).focus();
}

function format(input) {
    var num = input.value.replace(/\./g, '');
    if (!isNaN(num)) {
        num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g, '$1.');
        num = num.split('').reverse().join('').replace(/^[\.]/, '');
        input.value = num;
    } else {
        alert('Solo se permiten numeros');
        input.value = input.value.replace(/[^\d\.]*/g, '');
    }
}