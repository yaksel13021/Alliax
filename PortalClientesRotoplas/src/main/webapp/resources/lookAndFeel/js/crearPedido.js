
function isUndefined(x) {
    return typeof x == "undefined";
}

var cfdiOptions = null
var materiales = null;
var materialesSel = null;

function setMateriales(valores){
    materiales = JSON.parse(valores);
}
function setMaterialesSel(valores){
    materialesSel = valores;
}

function setCFDI(values){
    cfdiOptions = values;
}

var crearPedido = (function () {
    var $dt = null,
        $dtSeleccionados = null,
        $dtResumenCuentaPartidas = null,
        $dtResumentCuentaFacturacion = null,
        $dtResumentCuentaComentarios = null,
        $dtComentarios = null,
        display = $.fn.dataTable.render.number(',','.',2,'$').display;

    var init = function () {
        $('.isResizable').matchHeight();
        $('#headingThree').prop('disabled', true);
        initEvents();
        cargarDireccionEntrega.fill();
    };

    var initEvents = function () {
        // BOTONES DE SIGUIENTE
        $("#btnContinuarColp1").off().on("click", function (e) {
            e.preventDefault();
            if (!validStepOne()) {
                return;
            }

            $("[id='crearPedido:filterStepOne:frm_destinatario']").val(($("#select_direccionEntrega").val()));
            $("[id='crearPedido:filterStepOne:frm_nroPedido']").val($("[id='crearPedido:filterStepOne:input_numeroPedido']").val());

            var flagUpdate = $("[id='crearPedido:filterStepOne:flagUpdate']").val();
            if(flagUpdate=='true'){
                $("[id='crearPedido:filterStepOne:clonarPedidoId']").trigger('click');
            }else{
                $("[id='crearPedido:filterStepOne:asignaDestNroPedido']").trigger('click');
            }

        });

        $('DIV.continuarDestinatarioNroPedido').off().on("click", function (e) {


            $('#headingOne').prop('disabled', true);
            $('#headingThree').prop('disabled', false).click();
            $('#cardDynamicHeaderTitle').html('Selección de Materiales');
            loadMustacheTemplate('seleccionMateriales_template', 'crearPedido:cardDynamicBody');
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [{
                        btnId: 'btn_cancelAll',
                        btnName: 'btn_cancelAll',
                        btnText: 'Cancelar'
                    }]
                }
            });

            //validar cual imagen mostrar
            var descDestinatario = $("[id='crearPedido:filterStepOne:descripcionDestinatario']").val();

            if(descDestinatario.toUpperCase().match("^AGR") || descDestinatario.toUpperCase().match("^IND")){
                $('div.AMC_DIV').hide();
                $('div.I_DIV').show();
            }
            if(descDestinatario.toUpperCase().match("^FIN")){
                $('div.I_DIV').hide();
                $('div.AMC_DIV').show();
            }


            $("[id='crearPedido:cardDynamicFooter']").show();
            setTimeout(function () {
                $('.isResizable').matchHeight();
            }, 100)
            initEvents();
        });

        $('div.deletePartida').off().on('click', function (e) {
            cargarDTListProductosSelected.fill();
        });

        $('#btn_AgregarProductsNext').off().on('click', function (e) {
            e.preventDefault();
            if ($dt) {

                var rowsCount = $dt.rows().count(),
                    tableResultArr = [],
                    count = 0;

                // $("[id='crearPedido:asignaSegmento']").trigger("click");

                while (count < rowsCount) {
                    var rowCurrent = $dt.row(count),
                        nodesCurrent = rowCurrent.nodes(),
                        inputCantidad = nodesCurrent.to$().find('input.input_searchProductCantidad'),
                        data = rowCurrent.data();

                    var model = {
                        cantidad: inputCantidad.val(),
                        data: data
                    }
                    tableResultArr.push(model);
                    count++;
                }

                var filterResult = tableResultArr.filter(function (a, e) {
                    return a.cantidad !== null && a.cantidad !== ''
                });

                if (filterResult.length === 0) {
                    showToastr('Agregue una cantidad al menos en un producto', 'Aviso', {
                        type: typeNotification.warning
                    })
                    return;
                }

                var totalCantidad = filterResult.reduce(function (a, e, i) {
                    return a + parseInt(e.cantidad);
                }, 0);

                if (totalCantidad === 0) {
                    showToastr('La suma de las cantidades debe ser diferente a 0', 'Aviso', {
                        type: typeNotification.warning
                    })
                    return;
                }

                RESS.setProductosSeleccionados(filterResult);

                createMaterialJSON();

                $("[id='crearPedido:filterStepOne:asignaMaterial']").trigger('click');
            }else{
                showToastr(mensajes().Generico01, 'Aviso', {
                    type: typeNotification.warning
                });
            }
        });

        $('div.material_seleccionado').off().on('click', function (e) {
            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("[id='crearPedido:filterStepOne:descripcionDestinatario']");


            loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val(), info: true, seguirComprando: true, productSelected: true });
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [{
                        btnId: 'btn_cancelAll',
                        btnName: 'btn_cancelAll',
                        btnText: 'Cancelar'
                    },
                        {
                            btnId: 'btn_ListaProductosSeleccionadosNext',
                            btnName: 'btn_adbtn_listaProductosSeleccionadosNextdProducts',
                            btnText: 'Continuar'
                        }
                    ]
                }
            });
            $('#cardDynamicHeaderTitle').html('Lista de Productos Seleccionados');
            cargarDTListProductosSelected.fill()
            initEvents();
        });

        $('div.material_seleccionado_clonar').off().on('click', function (e) {
            $('#headingOne').prop('disabled', true);
            $('#headingThree').prop('disabled', false).click();

            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("[id='crearPedido:filterStepOne:descripcionDestinatario']");

            loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val(), info: true, seguirComprando: true, productSelected: true });
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [{
                        btnId: 'btn_cancelAll',
                        btnName: 'btn_cancelAll',
                        btnText: 'Cancelar'
                    },
                        {
                            btnId: 'btn_ListaProductosSeleccionadosNext',
                            btnName: 'btn_adbtn_listaProductosSeleccionadosNextdProducts',
                            btnText: 'Continuar'
                        }
                    ]
                }
            });
            $('#cardDynamicHeaderTitle').html('Lista de Productos Seleccionados');
            $("[id='crearPedido:cardDynamicFooter']").show();
            setTimeout(function () {
                $('.isResizable').matchHeight();
            }, 100)
            cargarDTListProductosSelected.fill()
            initEvents();
        });
        $('#btn_ListaProductosSeleccionadosNext').off().on('click', function (e) {
            var rowsCount = $dtSeleccionados.rows().count();
            if (rowsCount === 0) {
                showToastr('Agregue al menos en un producto', 'Aviso', {
                    type: typeNotification.warning
                })
                return;
            }

            if($('.partidaError').length > 0){
                showToastr(mensajes().Generico01, 'Aviso', {
                    type: typeNotification.warning
                });
                return false;
            }


            //var valuesStepOne = $('#filterStepOne').serializeForm();
           // $("[id='crearPedido:filterStepOne:preparaFacturacion']").trigger('click');
/*
        });

        $('div.listaProductosSeleccionadosNext').off().on('click', function (e) {*/
            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("[id='crearPedido:filterStepOne:descripcionDestinatario']");

            loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val(), info: true, facturacion: true });
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [
                        {
                            btnId: 'btn_cancelAll',
                            btnName: 'btn_cancelAll',
                            btnText: 'Cancelar'
                        },
                        {
                            btnId: 'btn_FacturacionNext',
                            btnName: 'btn_FacturacionNext',
                            btnText: 'Siguiente'
                        }
                    ]
                }
            });
            $('#cardDynamicHeaderTitle').html('Facturación');
            return cargarCFDI.fill()
                .then(function () {
                    return cargarMetodosPago.fill()
                        .then(function () {
                            initEvents();
                        });
                });
        });


        $('#btn_FacturacionNext').off().on('click', function (e) {
            e.preventDefault();
            if (!validFacturacion()) {
                return;
            }
           // var values = $('#facturacionForm').serializeForm();
            var select_cfdi = $('#select_cfdi');
            var select_metodoPago = $('#select_metodoPago');

            $("[id='crearPedido:filterStepOne:frm_cfdi']").val($('#select_cfdi').val());
            $("[id='crearPedido:filterStepOne:frm_metodoPago']").val($('#select_metodoPago').val());
            $("[id='crearPedido:filterStepOne:frm_cfdi_desc']").val($( "#select_cfdi option:selected" ).text());
            $("[id='crearPedido:filterStepOne:frm_metodoPago_desc']").val($( "#select_metodoPago option:selected" ).text());

            $("[id='crearPedido:filterStepOne:asignaFacturacion']").trigger('click');

            RESS.setCFDISeleccionado(select_cfdi.val());
            RESS.setMetodoPagoSeleccionado(select_metodoPago.val());

            var ressValues = RESS.getRESSObject(),
                seleccionMateriales = ressValues.seleccionMateriales;

            if (seleccionMateriales === 'AMC') {
                //var valuesStepOne = $('#filterStepOne').serializeForm();

                var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
                var select_direccionEntrega = $("[id='crearPedido:filterStepOne:descripcionDestinatario']");

                var ivaPedido = $("[id='crearPedido:filterStepOne:ivaPedido']");
                var subtotalPedido = $("[id='crearPedido:filterStepOne:subtotalPedido']");
                var totalPedido = $("[id='crearPedido:filterStepOne:totalPedido']");

                loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { info: true, noPedido: input_numeroPedido.val() , subtotalPedido: currencyFormat(subtotalPedido.val()), ivaPedido: currencyFormat(ivaPedido.val()), totalPedido:currencyFormat(totalPedido.val()) , destino: select_direccionEntrega.val(), resumencuenta: true });
                loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                    isList: {
                        divClass: 'footerButtonsRigth',
                        btnList: [
                            {
                                btnId: 'btn_cancelAll',
                                btnName: 'btn_cancelAll',
                                btnText: 'Cancelar'
                            },
                            {
                                btnId: 'btn_ResumenCuentaPartidasOrdenar',
                                btnName: 'btn_ResumenCuentaPartidasOrdenar',
                                btnText: 'Ordenar'
                            }
                        ]
                    }
                });
                $('#cardDynamicHeaderTitle').html('Resumen cuenta');
                return cargarDTResumenCuentaPartidas.fill()
                    .then(function () {
                        return cargarDTResumenCuentaFacturacion.fill();
                    })
                    .then(function () {
                        initEvents();
                    });
            } else {
                var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
                var select_direccionEntrega = $("[id='crearPedido:filterStepOne:descripcionDestinatario']");

                loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { comentarios: true , noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val()});
                loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                    isList: {
                        divClass: 'footerButtonsRigth',
                        btnList: [
                            {
                                btnId: 'btn_cancelAll',
                                btnName: 'btn_cancelAll',
                                btnText: 'Cancelar'
                            },
                            {
                                btnId: 'btn_ComentatiosNext',
                                btnName: 'btn_ComentatiosNext',
                                btnText: 'Continuar'
                            }
                        ]
                    }
                });
                $('#cardDynamicHeaderTitle').html('Comentarios');
                return cargarDTComentarios.fill()
                    .then(function () {
                        initEvents();
                    });
            }
        });
        $('#btn_UsoMaterialNext').off().on('click', function (e) {
            e.preventDefault();
            if (!validUsoMaterial()) {
                return;
            }
            //var values = $('#usoMaterialForm').serializeForm();
            RESS.setUsoMaterialSeleccionado($('#select_usoMateriales').val());

            $("[id='crearPedido:filterStepOne:frm_segmento']").val($('#select_usoMateriales').val());

            // $("[id='crearPedido:asignaSegmento']").trigger('click');
            $("[id='crearPedido:filterStepOne:asignaSegmento']").trigger('click');
        });
        $('DIV.usoMaterialSecond').off().on('click', function (e) {
            e.preventDefault();
            loadMustacheTemplate('searchProducts_template', 'crearPedido:cardDynamicBody');
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [{
                        btnId: 'btn_cancelAll',
                        btnName: 'btn_cancelAll',
                        btnText: 'Cancelar'
                    },
                    {
                        btnId: 'btn_AgregarProductsNext',
                        btnName: 'btn_AgregarProductsNext',
                        btnText: 'Agregar'
                    }
                    ]
                }
            });
            $('#cardDynamicHeaderTitle').html('Buscar Productos');
            return cargarDTSearchProductos.fill()
                .then(function (rs) {
                    initEvents();
                    if (!rs) {
                        $('#btn_cancelAll').click();
                    }
                });
        });
        $('#btn_ComentatiosNext').off().on('click', function (e) {
            e.preventDefault();
            var rowsCount = $dtComentarios.rows().count(),
                tableResultArr = [],
                count = 0;

            while (count < rowsCount) {
                var rowCurrent = $dtComentarios.row(count),
                    nodesCurrent = rowCurrent.nodes(),
                    inputComentario = nodesCurrent.to$().find('input.input_comentario'),
                    data = rowCurrent.data();

                switch(count){
                    case 0:
                        $("[id='crearPedido:filterStepOne:nombreContacto']").val(inputComentario.val());
                        break;
                    case 1:
                        $("[id='crearPedido:filterStepOne:apellidoContacto']").val(inputComentario.val());
                        break;
                    case 2:
                        $("[id='crearPedido:filterStepOne:telefonoContacto']").val(inputComentario.val());
                        break;
                    case 3:
                        $("[id='crearPedido:filterStepOne:telefonoFijoContacto']").val(inputComentario.val());
                        break;
                    case 4:
                        $("[id='crearPedido:filterStepOne:horarioRecepcion']").val(inputComentario.val());
                        break;
                    case 5:
                        $("[id='crearPedido:filterStepOne:referenciaUbicacion']").val(inputComentario.val());
                        break;
                    case 6:
                        $("[id='crearPedido:filterStepOne:productoAlmacenar']").val(inputComentario.val());
                        break;
                    case 7:
                        $("[id='crearPedido:filterStepOne:capacidadesTransporte']").val(inputComentario.val());
                        break;
                    case 8:
                        $("[id='crearPedido:filterStepOne:equipoEspecial']").val(inputComentario.val());
                        break;
                }

                var model = {
                    comentario: inputComentario.val(),
                    data: data
                }
                tableResultArr.push(model);
                count++;
            }

            var filterResult = tableResultArr.filter(function (a, e) { return a.comentario !== null && a.comentario !== '' });
            if (filterResult.length === 0) {
                showToastr('Agregue comentarios', 'Aviso', {
                    type: typeNotification.warning
                })
                return;
            }

            var filterObligatorio = tableResultArr.filter(function (a, e) { return a.data.obligatorio == true && (a.comentario == null || a.comentario == '') });
            if (filterObligatorio.length > 0) {
                showToastr('Necesita llenar los campos obligatorios', 'Aviso', {
                    type: typeNotification.warning
                })
                return;
            }

            RESS.setComentarios(tableResultArr);
            $("[id='crearPedido:filterStepOne:saveComentarios']").trigger('click');
           //var valuesStepOne = $('#filterStepOne').serializeForm();
        });
        $('div.saveCoentarioAfter').off().on('click', function (e) {
            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("[id='crearPedido:filterStepOne:descripcionDestinatario']");

            var ivaPedido = $("[id='crearPedido:filterStepOne:ivaPedido']");
            var subtotalPedido = $("[id='crearPedido:filterStepOne:subtotalPedido']");
            var totalPedido = $("[id='crearPedido:filterStepOne:totalPedido']");

            loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { info: true, noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val(), subtotalPedido: currencyFormat(subtotalPedido.val()), ivaPedido: currencyFormat(ivaPedido.val()), totalPedido:currencyFormat(totalPedido.val()), resumencuenta: true, showComentarios: true, emailflete: true });
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [
                        {
                            btnId: 'btn_cancelAll',
                            btnName: 'btn_cancelAll',
                            btnText: 'Cancelar'
                        },
                        {
                            btnId: 'btn_CotizarFlete',
                            btnName: 'btn_CotizarFlete',
                            btnText: 'Cotizar Flete'
                        }
                    ]
                }
            });
            $('#cardDynamicHeaderTitle').html('Resumen Cuenta');
            return cargarDTResumenCuentaPartidas.fill()
                .then(function () {
                    return cargarDTResumenCuentaFacturacion.fill();
                })
                .then(function () {
                    return cargarDTResumenCuentaComentarios.fill();
                })
                .then(function () {
                    initEvents();
                });

        });

        $('#btn_CotizarFlete').off().on('click', function (e) {
            $("[id='crearPedido:filterStepOne:correoElectronico']").val($('#frm_emailFlete').val());
            $("[id='crearPedido:filterStepOne:cotizarFlete']").trigger('click');
        });

        $('div.continuarCotizador').off().on('click', function (e) {
            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("[id='crearPedido:filterStepOne:descripcionDestinatario']");
            var noCotizacion=$("[id='crearPedido:filterStepOne:noCotizacion']");

            loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { info: true, noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val(), noCotizacion: noCotizacion.val(), resumencuenta: true, showComentarios: true, confirmaCotizacion: true });
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [
                        {
                            btnId: 'btn_Finalizar',
                            btnName: 'btn_Finalizar',
                            btnText: 'Finalizar'
                        }/*,
                        {
                            btnId: 'btn_ResumenCuentaPartidasOrdenar',
                            btnName: 'btn_ResumenCuentaPartidasOrdenar',
                            btnText: 'Ordenar'
                        }*/
                    ]
                }
            });
            return cargarDTResumenCuentaPartidas.fill()
                .then(function () {
                    return cargarDTResumenCuentaFacturacion.fill();
                })
                .then(function () {
                    return cargarDTResumenCuentaComentarios.fill();
                })
                .then(function () {
                    initEvents();
                });
        });

        $('#btn_Finalizar').off().on('click', function (e) {
            $("[id='crearPedido:filterStepOne:finalizar']").trigger('click');
        });
        $('#btn_ResumenCuentaPartidasOrdenar').off().on('click', function (e) {
            $("[id='crearPedido:filterStepOne:generaPedido']").trigger('click');
        });
        //SELECCION DE MATERIALES
        $('div.AMC_content').off().on('click', function (e) {
            e.preventDefault();
            RESS.setSeleccionMateriales('AMC');


            $("[id='crearPedido:filterStepOne:frm_segmento']").val('10');
           // $("[id='crearPedido:asignaSegmento']").trigger('click');
            $("[id='crearPedido:filterStepOne:asignaSegmento']").trigger('click');
        });

        $('div.AMC_content_next').off().on('click', function (e) {
            e.preventDefault();
            $("[id='crearPedido:cardDynamicFooter']").show();
            loadMustacheTemplate('searchProducts_template', 'crearPedido:cardDynamicBody');
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [{
                        btnId: 'btn_cancelAll',
                        btnName: 'btn_cancelAll',
                        btnText: 'Cancelar'
                    },
                        {
                            btnId: 'btn_AgregarProductsNext',
                            btnName: 'btn_AgregarProductsNext',
                            btnText: 'Agregar'
                        }
                    ]
                }
            });
            $('#cardDynamicHeaderTitle').html('Buscar Productos');
            return cargarDTSearchProductos.fill()
                .then(function (rs) {
                    initEvents();
                    if (!rs) {
                        //$('#btn_cancelAll').click();
                    }
                });
        });

        $('div.I_content').off().on('click', function (e) {
            e.preventDefault();
            RESS.setSeleccionMateriales('IND');
            $("[id='crearPedido:cardDynamicFooter']").show();

            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("[id='crearPedido:filterStepOne:descripcionDestinatario']");
            loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', {  noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val(),usoMaterial: true });
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [{
                        btnId: 'btn_cancelAll',
                        btnName: 'btn_cancelAll',
                        btnText: 'Cancelar'
                    },
                    {
                        btnId: 'btn_UsoMaterialNext',
                        btnName: 'btn_UsoMaterialNext',
                        btnText: 'Siguiente'
                    }
                    ]
                }
            });
            $('#cardDynamicHeaderTitle').html('Uso Material');
            return cargarUsoMateriales.fill()
                .then(function (e) {
                    initEvents();
                })
        });
        // VALIDACIONES
        $('#input_numeroPedido, input.input_searchProductCantidad').off().on('keyup', function (e) {
            e.preventDefault();
            validNumber(e);
        });
        // CANCELAR TODO
        $('#btn_cancelAll').off().on('click', function (e) {
            e.preventDefault();
            RESS.removeRESSObjext();
            $("[id='crearPedido:cardDynamicFooter']").html(null);
            $("[id='crearPedido:cardDynamicBody']").html(null);

            $("[id='crearPedido:cardDynamicFooter']").hide();


            $('#headingOne').prop('disabled', false).click();
            $('#headingThree').prop('disabled', true);
            $('#cardDynamicHeaderTitle').html('Selección de Materiales');
        });
        // SEGUIR COMPRANDO EN LISTA DE PRODUCTOS SELECCIONADOS
        $('#btn_seguirComprando').off().on('click', function (e) {
            e.preventDefault();
            $("[id='crearPedido:filterStepOne:cotinuaCompra']").trigger('click');
        });
        $('div.showMaterial').off().on('click', function (e) {
            $("[id='crearPedido:cardDynamicFooter']").show();

            loadMustacheTemplate('searchProducts_template', 'crearPedido:cardDynamicBody');
            loadMustacheTemplate('cardDynamicFooter_template', 'crearPedido:cardDynamicFooter', {
                isList: {
                    divClass: 'footerButtonsRigth',
                    btnList: [{
                        btnId: 'btn_cancelAll',
                        btnName: 'btn_cancelAll',
                        btnText: 'Cancelar'
                    },
                    {
                        btnId: 'btn_AgregarProductsNext',
                        btnName: 'btn_AgregarProductsNext',
                        btnText: 'Agregar'
                    }
                    ]
                }
            });
            $('#cardDynamicHeaderTitle').html('Buscar Productos');
            return cargarDTSearchProductos.fill(true)
                .then(function () {
                    initEvents();
                });
        });
        $('a[data-toggle="tab"]').on('hidden.bs.tab', function (e) {
            ddt.util.columns_adjust_recalc();
        })
		$('#select_metodoPago').off().on('change', function (e) {
        	var select_metodoPago = $('#select_metodoPago');
        	var ticket = $('#ticket');
        	if('PUE' == select_metodoPago.val()) {
                $('#select_cfdi').val('G01');
                $('#select_cfdi').trigger('change');
                $('#select_cfdi').prop('disabled','disabled');
        		$('#containerTicket').show();

        	} else {
        		$('#containerTicket').hide();
                $('#select_cfdi').prop('disabled',false);
        	}
        });
        $(document).on('change','.btn-file :file',function() {
        	  var input = $(this);
        	  var numFiles = input.get(0).files ? input.get(0).files.length : 1;
        	  var label = input.val().replace(/\\/g,'/').replace(/.*\//,'');
        	  input.trigger('fileselect',[numFiles,label]);        	  
        	});
        $('.btn-file :file').on('fileselect',function(event,numFiles,label) {
            var input = $(this).parents('.input-group').find(':text');
            var log = numFiles > 1 ? numFiles + ' files selected' : label;
            if(input.length){ input.val(log); }else{ }
        });
    };


    var cargarDireccionEntrega = {
        fill: function () {


            return this.data()
                .then(function (rs) {
                    if (!rs) {
                        showToastr('No existen direcciones de entrega', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }
                    RESS.setCargarDireccionEntrega(rs);

                    if ($("#select_direccionEntrega").data('select2')) {
                        $("#select_direccionEntrega").select2("destroy");
                        $("#select_direccionEntrega").empty();
                    }

                    var result = [];
                    for (var i = 0; i < rs.length; i++) {
                        result.push({
                            value: rs[i].noDestinatario,
                            descripcion: rs[i].nombreSucursal + "/" + rs[i].calleNumero + " " + rs[i].colonia,
                            dataAtributos: 'data-id="' + rs[i].noDestinatario + '"'
                        });
                    }
                    var selectTemplate = renderMustacheTemplate('select2_template', result);

                    $("#select_direccionEntrega").append(selectTemplate);
                    $("#select_direccionEntrega").select2({
                        theme: "bootstrap",
                        allowClear: true,
                        placeholder: "Seleccione una opción",
                        language: Select2Languaje(),
                        multiple: false,
                        width: '100%'
                    });
                    return true;
                })
                .catch(function () {
                    showToastr('No existen direcciones de entrega', 'Aviso', {
                        type: typeNotification.warning
                    });
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model = loadDestinatarioMercancias();
                resolve(model);
            });
        }
    };

    var cargarDTSearchProductos = {
        fill: function (loadPreviusValues) {
            return this.data()
                .then(function (rs) {

                    if (!rs) {
                        showToastr(mensajes().Generico01, 'Aviso', {
                            type: typeNotification.warning
                        })
                        return false;
                    }
                    RESS.setProductos(rs);
                    if ($dt) {
                        $dt.clear().destroy();
                    }
                    $dt = document.querySelector('#dt_searchProducts').rssDataTable({
                        order: [0, 'asc'],
                        scrollX: true,
                        searching: true,
                        paging: true,
                        data: rs,
                        responsive: true,
                        free: function (data, type, row, meta) {
                            if (meta.col === 1) {
                                return renderMustacheTemplate('input_template', { class: 'input_searchProductCantidad', name: 'input_searchProductCantidad', placeholder: 'Cantidad', value: data.cantidad });
                            }
                        },
                        rowCallback: function (row, data, api) {
                            /*if (loadPreviusValues) {
                                var findData = RESS.getRESSObject().productosSeleccionados.find(function (a, e) {
                                    return a.data.id === data.id;
                                });
                                if (findData) {
                                    $(row).find('.input_searchProductCantidad').val(findData.cantidad);
                                }
                            }*/
                            $(row).find('.input_searchProductCantidad').val(data.cantidad);
                        }
                    });
                    return true;
                })
                .catch(function () {
                    showToastr(mensajes().Generico01, 'Aviso', {
                        type: typeNotification.warning
                    })
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {

                var model= materiales;
                     //   var model = materialLoad();

                resolve(model);
            });
        }
    };

    var cargarDTListProductosSelected = {
        fill: function () {
            this.data()
                .then(function (rs) {

                    if (!rs) {
                        showToastr('No existen productos seleccionados', 'Aviso', {
                            type: typeNotification.warning
                        })
                        return false;
                    }

                    if ($dtSeleccionados) {
                        $dtSeleccionados.clear().destroy();
                    }
                    $dtSeleccionados = document.querySelector('#dt_ProductsSelected').rssDataTable({
                        order: [0, 'asc'],
                        scrollX: true,
                        searching: true,
                        paging: true,
                        data: rs,
                        responsive: true,
                        actions: function (data, type, row, meta) {

                            //return renderMustacheTemplate('actions_template',{delete:true});
                            return renderMustacheTemplate('actions_template');
                        },
                        free: function (data, type, row, meta) {
                            if(meta.col === 7){
                                return display(parseFloat(data));
                            }
                            if(meta.col === 8){
                                return display(parseFloat(data));
                            }
                            if(meta.col === 6){
                                return display(parseFloat(data));
                            }
                        },
                        rowCallback: function (row, data, api) {
                            $(row).find('.eliminarProducto').off().on('click', function (e) {
                                var productosSeleccionados =  $('#dt_ProductsSelected tbody tr');
                                if (productosSeleccionados.length === 1) {
                                    showToastr('No se puede quedar sin partidas', 'Aviso', {
                                        type: typeNotification.warning
                                    })
                                    return;
                                }
                                confirmModal('Eliminar elemento',
                                '¿ Seguro que desea eliminar esta partida ?',
                                'Cancelar', 'Confirmar',
                                true, function (rs) {
                                    if (rs) {
                                        var dta = data;

                                        $("[id='crearPedido:filterStepOne:frm_skuMaterialEliminado']").val(data.sku);
                                        $("[id='crearPedido:filterStepOne:deletePartida']").trigger('click');

                                    }
                                });
                            });
                        }
                    });
/*
                    $('#dt_ProductsSelected tr').each(function(index, tr) {
                        var row = $dtSeleccionados.row( tr );
                        var tr = $(tr);
                        if(!(row.data().codigoError == '' || row.data().codigoError == '0')) {
                            tr.removeClass('odd').addClass('partidaError');
                        }
                    });
*/
                    // Add event listener for opening and closing details
                   /* $('#dt_ProductsSelected tbody').on('click', 'tr', function () {
                        var tr = $(this);
                        if (!tr.hasClass('formatRow')) {
                            var row = $dtSeleccionados.row(tr);
                            if (!(row.data().codigoError == '' || row.data().codigoError == '0')) {
                                if (row.child.isShown()) {
                                    // This row is already open - close it
                                    row.child.hide();
                                    tr.removeClass('shown');
                                } else {
                                    // Open this row
                                    row.child(format(row.data())).show();
                                    tr.addClass('shown');
                                }
                            }
                        }
                    } );*/

                    $('#dt_ProductsSelected tbody tr').each(function(index, tr) {
                            var row = $dtSeleccionados.row( $(tr) );
                            if(!(row.data().codigoError == '' || row.data().codigoError == '0')) {
                                $(tr).addClass('partidaError');
                            }
                        }
                    );


                    $('#dt_ProductsSelected tr').click(function () {
                        var tr = $(this);
                        if($(this).attr("role")=="row"){
                            var row = $dtSeleccionados.row(tr);
                            if (!(row.data().codigoError == '' || row.data().codigoError == '0')) {
                                if (row.child.isShown()) {
                                    // This row is already open - close it
                                    row.child.hide();
                                } else {
                                    // Open this row
                                    row.child(format(row.data())).show();
                                }
                            }
                        }
                    } );
                    return true
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                model = materialesSel;
                resolve(model);
            });
        }
    };

    var cargarCFDI = {
        fill: function () {
            return this.data()
                .then(function (rs) {
                    if (!rs) {
                        showToastr('No existen CFDI', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }

                    RESS.setcargarCFDI(rs);

                    if ($("#select_cfdi").data('select2')) {
                        $("#select_cfdi").select2("destroy");
                        $("#select_cfdi").empty();
                    }

                    var result = [];
                    for (var i = 0; i < rs.length; i++) {
                        result.push({
                            value: rs[i].claveUsoCFDI,
                            descripcion: rs[i].descripcionClaveUsoCFDI,
                            dataAtributos: 'data-id="' + rs[i].claveUsoCFDI + '"'
                        });
                    }
                    var selectTemplate = renderMustacheTemplate('select2_template', result);

                    $("#select_cfdi").append(selectTemplate);
                    $("#select_cfdi").select2({
                        theme: "bootstrap",
                        allowClear: true,
                        placeholder: "Seleccione una opción",
                        language: Select2Languaje(),
                        multiple: false,
                        width: '100%'
                    });
                    return true;
                })
                .catch(function () {
                    showToastr('No existen CFDI', 'Aviso', {
                        type: typeNotification.warning
                    });
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model =  cfdiOptions;/*[{
                    id: "G01",
                    descripcion: 'G01 Adquisición de mercancias'
                },
                {
                    id: "G02",
                    descripcion: 'G02 Devoluciones, descuentes o bonificaciones'
                },
                {
                    id: "G03",
                    descripcion: 'G03 Gastos en general'
                },
                {
                    id: "001",
                    descripcion: '001 Construcciones'
                },
                {
                    id: "002",
                    descripcion: '002 Mobiliario y equipo de oficina para inversiones'
                },
                {
                    id: "003",
                    descripcion: '003 Equipos de transporte'
                },
                {
                    id: "004",
                    descripcion: '004 Equipo de computo y accesorios'
                },
                {
                    id: "005",
                    descripcion: '005 Codos, troqueles, moldes, matrices y herramental'
                },
                {
                    id: "006",
                    descripcion: '006 Comunicaciones Telefónicas'
                },
                {
                    id: "007",
                    descripcion: '007 Comunicaciones Satelitales'
                },
                {
                    id: "008",
                    descripcion: '008 Otra Maquinaria y Equipo'
                }
                ];*/
                resolve(model);
            });
        }
    };

    var cargarMetodosPago = {
        fill: function () {
            return this.data()
                .then(function (rs) {
                    if (!rs) {
                        showToastr('No existen CFDI', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }

                    RESS.setcargarMetodosPago(rs);

                    if ($("#select_metodoPago").data('select2')) {
                        $("#select_metodoPago").select2("destroy");
                        $("#select_metodoPago").empty();
                    }

                    var result = [];
                    for (var i = 0; i < rs.length; i++) {
                        result.push({
                            value: rs[i].id,
                            descripcion: rs[i].descripcion,
                            dataAtributos: 'data-id="' + rs[i].id + '"'
                        });
                    }
                    var selectTemplate = renderMustacheTemplate('select2_template', result);

                    $("#select_metodoPago").append(selectTemplate);
                    $("#select_metodoPago").select2({
                        theme: "bootstrap",
                        allowClear: true,
                        placeholder: "Seleccione una opción",
                        language: Select2Languaje(),
                        multiple: false,
                        width: '100%'
                    });
                    return true;
                })
                .catch(function () {
                    showToastr('No existen CFDI', 'Aviso', {
                        type: typeNotification.warning
                    });
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model = [{
                    id: "PPD",
                    descripcion: 'Pago Parcialidades'
                },
                {
                    id: "PUE",
                    descripcion: 'Pago una sola exhibición'
                }
                ];
                resolve(model);
            });
        }
    };

    var cargarDTResumenCuentaPartidas = {
        fill: function () {
            return this.data()
                .then(function (rs) {

                    if (!rs) {
                        showToastr('No existen productos seleccionados', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }

                    if ($dtResumenCuentaPartidas) {
                        $dtResumenCuentaPartidas.clear().destroy();
                    }
                    $dtResumenCuentaPartidas = document.querySelector('#dt_ResumenCuentaPartidas').rssDataTable({
                        order: [0, 'asc'],
                        scrollX: true,
                        searching: true,
                        paging: true,
                        data: model,
                        responsive: true,
                        free: function (data, type, row, meta) {
                            if(meta.col === 6){
                                return display(parseFloat(data));
                            }
                            if(meta.col === 7){
                                return display(parseFloat(data));
                            }
                            if(meta.col === 8){
                                return display(parseFloat(data));
                            }
                            if(meta.col === 9){
                                return display(parseFloat(data));
                            }
                        },
                        rowCallback: function (row, data, api) {

                        }
                    });
                    return true;
                })
                .catch(function () {
                    showToastr('No existen productos seleccionados', 'Aviso', {
                        type: typeNotification.warning
                    });
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model = materialesSel;
                resolve(model);
            });
        }
    };

    var cargarUsoMateriales = {
        fill: function () {
            return this.data()
                .then(function (rs) {
                    if (!rs) {
                        showToastr('No existen uso para materiales', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }

                    if ($("#select_usoMateriales").data('select2')) {
                        $("#select_usoMateriales").select2("destroy");
                        $("#select_usoMateriales").empty();
                    }

                    var result = [];
                    for (var i = 0; i < rs.length; i++) {
                        result.push({
                            value: rs[i].id,
                            descripcion: rs[i].descripcion,
                            dataAtributos: 'data-id="' + rs[i].id + '"'
                        });
                    }
                    var selectTemplate = renderMustacheTemplate('select2_template', result);

                    $("#select_usoMateriales").append(selectTemplate);
                    $("#select_usoMateriales").select2({
                        theme: "bootstrap",
                        allowClear: true,
                        placeholder: "Seleccione una opción",
                        language: Select2Languaje(),
                        multiple: false,
                        width: '100%'
                    });
                    return true;
                })
                .catch(function () {
                    showToastr('No existen uso para materiales', 'Aviso', {
                        type: typeNotification.warning
                    });
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model = [{
                    id: 14,
                    descripcion: 'Industrial'
                },
                {
                    id: 13,
                    descripcion: 'Agricola'
                }
                ];
                resolve(model);
            });
        }
    };

    var cargarDTComentarios = {
        fill: function () {
            return this.data()
                .then(function (rs) {
                    if (!rs) {
                        showToastr(mensajes().Generico01, 'Aviso', {
                            type: typeNotification.warning
                        })
                        return false;
                    }

                    if ($dtComentarios) {
                        $dtComentarios.clear().destroy();
                    }
                    $dtComentarios = document.querySelector('#dt_comentarios').rssDataTable({
                        order: [0, 'asc'],
                        scrollX: true,
                        searching: false,
                        paging: false,
                        data: rs,
                        responsive: true,
                        free: function (data, type, row, meta) {
                            if (meta.col === 2) {
                                return renderMustacheTemplate('input_template', { class: 'input_comentario', name: 'input_comentario', placeholder: 'Descripción' });
                            }
                        },
                        rowCallback: function (row, data, api) {
                            if (data.obligatorio == true) {
                                $('td:eq(1)', row).html('<p style="display: flex;align-items: center;"><span style="color:red;font-size:20px;">*</span> ' + data.datosEntrega + '</p>');
                            }
                        }
                    });
                    return true;
                })
                .catch(function () {
                    showToastr(mensajes().Generico01, 'Aviso', {
                        type: typeNotification.warning
                    })
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model = [{
                    id: 1,
                    datosEntrega: 'Nombre del contacto',
                    obligatorio: true
                },
                {
                    id: 2,
                    datosEntrega: 'Apellido del contacto',
                    obligatorio: true
                },
                {
                    id: 3,
                    datosEntrega: 'Teléfono de contacto',
                    obligatorio: true
                },
                {
                    id: 4,
                    datosEntrega: 'Teléfono fijo de contacto',
                    obligatorio: false
                },
                {
                    id: 5,
                    datosEntrega: 'Horario de recepción',
                    obligatorio: false
                },
                {
                    id: 6,
                    datosEntrega: 'Referencia fisica de ubicación',
                    obligatorio: true
                },
                {
                    id: 7,
                    datosEntrega: 'Producto a almacenar. Espacio por contenido del producto concentración en %',
                    obligatorio: true
                },
                {
                    id: 8,
                    datosEntrega: 'Capacidades de transporte especiales',
                    obligatorio: false
                },
                {
                    id: 9,
                    datosEntrega: 'Equipo especial de protección personal (EPP)',
                    obligatorio: false
                }
                ];
                resolve(model);
            });
        }
    };

    var cargarDTResumenCuentaFacturacion = {
        fill: function () {
            var CFDISeleccionado =  $("[id='crearPedido:filterStepOne:frm_cfdi']").val();
            var metodoPagoSeleccionado = $("[id='crearPedido:filterStepOne:frm_metodoPago']").val();

            if (!CFDISeleccionado || !metodoPagoSeleccionado) {
                showToastr('No selecionó un CFDI o un método de pago', 'Aviso', {
                    type: typeNotification.warning
                });
                return false;
            }

            var model = [{
                cfdi: $("[id='crearPedido:filterStepOne:frm_cfdi_desc']").val(),
                metodoPago: $("[id='crearPedido:filterStepOne:frm_metodoPago_desc']").val()
            }];

            if ($dtResumentCuentaFacturacion) {
                $dtResumentCuentaFacturacion.clear().destroy();
            }
            $dtResumentCuentaFacturacion = document.querySelector('#dt_ResumenCuentaFacturacion').rssDataTable({
                order: [0, 'asc'],
                scrollX: true,
                searching: true,
                paging: true,
                data: model,
                responsive: true
            });
            return true;
        }
    };

    var cargarDTResumenCuentaComentarios = {
        fill: function () {
            var ressObj = RESS.getRESSObject(),
                comentarios = ressObj.comentarios;

            if (!comentarios) {
                showToastr('No existen comentarios', 'Aviso', {
                    type: typeNotification.warning
                });
                return false;
            }
            var model = comentarios.map(function (a, e) {
                var obj = {
                    datosEntrega: a.data.datosEntrega,
                    descripcion: a.comentario
                };
                return obj;
            });

            if ($dtResumentCuentaComentarios) {
                $dtResumentCuentaComentarios.clear().destroy();
            }
            $dtResumentCuentaComentarios = document.querySelector('#dt_ResumenCuentaComentarios').rssDataTable({
                order: [0, 'asc'],
                scrollX: true,
                searching: true,
                paging: true,
                data: model,
                responsive: true
            });
            return true;
        }
    };

    var validStepOne = function () {
        if (!($("#select_direccionEntrega").val())) {
            showToastr('Seleccione una dirección de entrega', 'Aviso', {
                type: typeNotification.warning
            })
            return false;
        }
        if (!($("[id='crearPedido:filterStepOne:input_numeroPedido']").val())) {
            showToastr('Ingrese un número de pedido', 'Aviso', {
                type: typeNotification.warning
            })
            return false;
        }
        return true;
    };

    var validFacturacion = function () {
        //var values = $('#facturacionForm').serializeForm();
        var select_cfdi = $('#select_cfdi');
        var select_metodoPago = $('#select_metodoPago');
        var comprobante = $('#tipoMessage');

        if (!select_cfdi.val()) {
            showToastr('Seleccione un CFDI', 'Aviso', {
                type: typeNotification.warning
            })
            return false;
        }
        if (!select_metodoPago.val()) {
            showToastr('Seleccione un método de pago', 'Aviso', {
                type: typeNotification.warning
            })
            return false;
        }

        if(select_metodoPago.val() == 'PUE' && comprobante.val() != '1'){
            showToastr('Proporcione un Comprobante Bancario', 'Aviso', {
                type: typeNotification.warning
            });
            return false;
        }

        return true;
    };

    var validUsoMaterial = function () {
       // var values = $('#usoMaterialForm').serializeForm();

        var select_usoMateriales = $('#select_usoMateriales');
        if (!select_usoMateriales.val()) {
            showToastr('Seleccione un uso para el material', 'Aviso', {
                type: typeNotification.warning
            })
            return false;
        }
        return true;
    };

    return {
        init: init,
        initEvents: initEvents
    };
})();

$(document).ready(function () {
    crearPedido.init();

});


function createMaterialJSON() {
    jsonObj = [];
    $("input[name=input_searchProductCantidad]").each(function() {
        var sku =  $(this).closest('tr').find("td:last").text();
        var val = $(this).val();
        item = {}
        item ["sku"] = sku;
        item ["cantidad"] = val;

        jsonObj.push(item);
    });
    $("[id='crearPedido:filterStepOne:frm_materialSeleccionado']").val(JSON.stringify(jsonObj));
}

function validateClasePedido(){
    var clasePedido =  $("[id='crearPedido:filterStepOne:clasePedido']");

    if(clasePedido.val() == '' || clasePedido.val().length == 0){
        showToastr(mensajes().Generico01, 'Aviso', {
            type: typeNotification.warning
        });
    }else{
        $('div.continuarDestinatarioNroPedido').trigger('click');
    }
}

function format ( d ) {
    var row = "";


        // `d` is the original data object for the row
        row ='<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
            '<tr class="formatRow">' +
            '<td>Codigo</td>' +
            '<td>' + d.codigoError + '</td>' +
            '</tr>' +
            '<tr class="formatRow">' +
            '<td>Mensaje:</td>' +
            '<td>' + d.mensajeError + '</td>' +
            '</tr>' +
            '</table>';

    return row;
}
function currencyFormat(texto) {

    var num = Number(texto);
   // var n = num.toFixed(2);

    return '$' + num.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
}
