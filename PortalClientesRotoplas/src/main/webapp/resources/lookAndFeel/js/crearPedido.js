var crearPedido = (function () {
    var $dt = null,
        $dtSeleccionados = null,
        $dtResumenCuentaPartidas = null,
        $dtResumentCuentaFacturacion = null,
        $dtResumentCuentaComentarios = null,
        $dtComentarios = null;

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
            $("[id='crearPedido:cardDynamicFooter']").show();
            setTimeout(function () {
                $('.isResizable').matchHeight();
            }, 100)
            initEvents();
        });
        $('#btn_AgregarProductsNext').off().on('click', function (e) {
            e.preventDefault();
            var rowsCount = $dt.rows().count(),
                tableResultArr = [],
                count = 0;

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

            var filterResult = tableResultArr.filter(function (a, e) { return a.cantidad !== null && a.cantidad !== '' });

            if (filterResult.length === 0) {
                showToastr('Agregue una cantidad al menos en un producto', 'Aviso', {
                    type: typeNotification.warning
                })
                return;
            }

            var totalCantidad = filterResult.reduce(function (a, e, i) { return a + parseInt(e.cantidad); }, 0);

            if (totalCantidad === 0) {
                showToastr('La suma de las cantidades debe ser diferente a 0', 'Aviso', {
                    type: typeNotification.warning
                })
                return;
            }

            RESS.setProductosSeleccionados(filterResult);

            //var valuesStepOne = $('#filterStepOne').serializeForm();

            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("#select_direccionEntrega");

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
        $('#btn_ListaProductosSeleccionadosNext').off().on('click', function (e) {
            var rowsCount = $dtSeleccionados.rows().count();
            if (rowsCount === 0) {
                showToastr('Agregue al menos en un producto', 'Aviso', {
                    type: typeNotification.warning
                })
                return;
            }
            //var valuesStepOne = $('#filterStepOne').serializeForm();

            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("#select_direccionEntrega");

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

            RESS.setCFDISeleccionado(select_cfdi.val());
            RESS.setMetodoPagoSeleccionado(select_metodoPago.val());

            var ressValues = RESS.getRESSObject(),
                seleccionMateriales = ressValues.seleccionMateriales;

            if (seleccionMateriales === 'AMC') {
                //var valuesStepOne = $('#filterStepOne').serializeForm();

                var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
                var select_direccionEntrega = $("#select_direccionEntrega");

                loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { info: true, noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val(), resumencuenta: true });
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
                loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { comentarios: true });
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
           //var valuesStepOne = $('#filterStepOne').serializeForm();

            var input_numeroPedido = $("[id='crearPedido:filterStepOne:input_numeroPedido']");
            var select_direccionEntrega = $("#select_direccionEntrega");

            loadMustacheTemplate('selectedProducts_template', 'crearPedido:cardDynamicBody', { info: true, noPedido: input_numeroPedido.val(), destino: select_direccionEntrega.val(), resumencuenta: true, showComentarios: true });
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
        //SELECCION DE MATERIALES
        $('div.AMC_content').off().on('click', function (e) {
            e.preventDefault();
            RESS.setSeleccionMateriales('AMC');

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
            var select_direccionEntrega = $("#select_direccionEntrega");
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
                            value: rs[i].id,
                            descripcion: rs[i].descripcion,
                            dataAtributos: 'data-id="' + rs[i].id + '"'
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
                var model = [{
                    id: 1,
                    descripcion: 'SUC. Tecnica Tectonica Aplicada/Av. Adolfo Lopez Mateos 18 Colel Toreo'
                },
                {
                    id: 2,
                    descripcion: 'Fis. Todo Gas Plomeria / Universidad 3214 Col Centro'
                },
                {
                    id: 3,
                    descripcion: 'Agr. Polimar Narvarte/ Carr. a San benito 902 Col El Barrial'
                },
                {
                    id: 4,
                    descripcion: 'Ind. Total Home/ Miguel de la Madrid 7643 Col Unidad Modelo'
                }
                ];
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
                    alert("RS Antes DT " + rs);
                    if ($dt) {
                        $dt.clear().destroy();
                    }
                    alert("Query selector " + document.querySelector('#dt_searchProducts'));
                    $dt = document.querySelector('#dt_searchProducts').rssDataTable({
                        order: [0, 'asc'],
                        scrollX: true,
                        searching: true,
                        paging: true,
                        data: rs,
                        responsive: true,
                        free: function (data, type, row, meta) {
                            if (meta.col === 1) {
                                return renderMustacheTemplate('input_template', { class: 'input_searchProductCantidad', name: 'input_searchProductCantidad', placeholder: 'Cantidad' });
                            }
                        },
                        rowCallback: function (row, data, api) {
                            if (loadPreviusValues) {
                                var findData = RESS.getRESSObject().productosSeleccionados.find(function (a, e) {
                                    return a.data.id === data.id;
                                });
                                if (findData) {
                                    $(row).find('.input_searchProductCantidad').val(findData.cantidad);
                                }
                            }
                        }
                    });
                    alert("RS DESPUES DT " + rs);
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
                    descripcion: 'Valvula de esfera desmontable 63 x 63 mm',
                    um: 'PZA',
                    noSku: '12456748'
                },
                {
                    id: 2,
                    descripcion: 'Conector hembra 90 x 3',
                    um: 'PZA',
                    noSku: '487686'
                },
                {
                    id: 3,
                    descripcion: 'Conector macho 75 x 2 1/2',
                    um: 'PZA',
                    noSku: '3547687'
                },
                {
                    id: 4,
                    descripcion: 'Valvula de esfera desmontable  32 x 32 mm',
                    um: 'PZA',
                    noSku: '57468435'
                },
                {
                    id: 5,
                    descripcion: 'Codo 90º de 110 mm',
                    um: 'PZA',
                    noSku: '3545678'
                },
                {
                    id: 6,
                    descripcion: 'Tee de 110 mm',
                    um: 'PZA',
                    noSku: '214567687'
                },
                {
                    id: 7,
                    descripcion: 'Reduccion 110 x 90 mm',
                    um: 'PZA',
                    noSku: '74656894'
                }
                ];

                resolve(model);
            });
        }
    };

    var cargarDTListProductosSelected = {
        fill: function () {
            this.data()
                .then(function () {
                    var ressObj = RESS.getRESSObject(),
                        rs = ressObj.productosSeleccionados;

                    if (!rs) {
                        showToastr('No existen productos seleccionados', 'Aviso', {
                            type: typeNotification.warning
                        })
                        return false;
                    }

                    var model = rs.map(function (a, e) {
                        var obj = {
                            id: a.data.id,
                            pos: e + 1,
                            noMaterial: a.data.noSku,
                            descripcion: a.data.descripcion,
                            cantidad: a.cantidad,
                            um: a.data.um,
                            monto: 356,
                            precioNeto: a.cantidad * 3,
                            mon: 'MXN',
                            fecha: moment().utc().format('DD/MM/YYYY')
                        };
                        return obj;
                    });

                    if ($dtSeleccionados) {
                        $dtSeleccionados.clear().destroy();
                    }
                    $dtSeleccionados = document.querySelector('#dt_ProductsSelected').rssDataTable({
                        order: [0, 'asc'],
                        scrollX: true,
                        searching: true,
                        paging: true,
                        data: model,
                        responsive: true,
                        actions: function (data, type, row, meta) {
                            return renderMustacheTemplate('actions_template');
                        },
                        rowCallback: function (row, data, api) {
                            $(row).find('.eliminarProducto').off().on('click', function (e) {
                                var productosSeleccionados = RESS.getRESSObject().productosSeleccionados;
                                if (productosSeleccionados.length === 1) {
                                    showToastr('No se puede quedar sin partidas', 'Aviso', {
                                        type: typeNotification.warning
                                    })
                                    return;
                                }
                            confirmModal('Eliminar elemento', '¿ Seguro que desea eliminar esta partida ?', 'Cancelar', 'Confirmar', true, function (rs) {
                                    if (rs) {
                                        var dta = data,
                                            findIndex = productosSeleccionados.findIndex(function (a, e, i) { return a.data.id === data.id; });

                                        productosSeleccionados.splice(findIndex, 1);
                                        RESS.setProductosSeleccionados(productosSeleccionados);
                                        cargarDTListProductosSelected.fill();
                                    }
                                });
                            });
                        }
                    });
                    return true
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                resolve();
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
                            value: rs[i].id,
                            descripcion: rs[i].descripcion,
                            dataAtributos: 'data-id="' + rs[i].id + '"'
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
                var model = [{
                    id: 1,
                    descripcion: 'G01 Adquisición de mercancias'
                },
                {
                    id: 2,
                    descripcion: 'G02 Devoluciones, descuentes o bonificaciones'
                },
                {
                    id: 3,
                    descripcion: 'G03 Gastos en general'
                },
                {
                    id: 4,
                    descripcion: '001 Construcciones'
                },
                {
                    id: 5,
                    descripcion: '002 Mobiliario y equipo de oficina para inversiones'
                },
                {
                    id: 6,
                    descripcion: '003 Equipos de transporte'
                },
                {
                    id: 7,
                    descripcion: '004 Equipo de computo y accesorios'
                },
                {
                    id: 8,
                    descripcion: '005 Codos, troqueles, moldes, matrices y herramental'
                },
                {
                    id: 9,
                    descripcion: '006 Comunicaciones Telefónicas'
                },
                {
                    id: 10,
                    descripcion: '007 Comunicaciones Satelitales'
                },
                {
                    id: 11,
                    descripcion: '008 Otra Maquinaria y Equipo'
                }
                ];
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
                    id: 1,
                    descripcion: 'PPD Pago Parcialidades'
                },
                {
                    id: 2,
                    descripcion: 'PUE Pago una sola exhibición'
                }
                ];
                resolve(model);
            });
        }
    };

    var cargarDTResumenCuentaPartidas = {
        fill: function () {
            return this.data()
                .then(function () {
                    var ressObj = RESS.getRESSObject(),
                        rs = ressObj.productosSeleccionados;

                    if (!rs) {
                        showToastr('No existen productos seleccionados', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }

                    var model = rs.map(function (a, e) {
                        var obj = {
                            id: a.data.id,
                            pos: e + 1,
                            noMaterial: a.data.noSku,
                            descripcion: a.data.descripcion,
                            cantidad: a.cantidad,
                            cantidadEnt: 000,
                            um: a.data.um,
                            monto: 356,
                            precioNeto: a.cantidad * 3,
                            fechaEnt: moment().utc().format('DD/MM/YYYY'),
                            estatus: 'NO SE'
                        };
                        return obj;
                    });

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
                resolve();
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
                    id: 1,
                    descripcion: 'Industrial'
                },
                {
                    id: 2,
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
                        searching: true,
                        paging: true,
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
                    datosEntrega: 'Nombre del contrato',
                    obligatorio: true
                },
                {
                    id: 2,
                    datosEntrega: 'Apellido del contrato',
                    obligatorio: true
                },
                {
                    id: 3,
                    datosEntrega: 'Teléfono del contrato',
                    obligatorio: true
                },
                {
                    id: 4,
                    datosEntrega: 'Horario de recepción',
                    obligatorio: false
                },
                {
                    id: 5,
                    datosEntrega: 'Referencia fisica de ubicación',
                    obligatorio: true
                },
                {
                    id: 6,
                    datosEntrega: 'Producto a almacenar. Espacio por contenido del producto concentración en %',
                    obligatorio: true
                },
                {
                    id: 7,
                    datosEntrega: 'Capacidades de transporte especiales',
                    obligatorio: false
                },
                {
                    id: 8,
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
            var ressObj = RESS.getRESSObject(),
                CFDISeleccionado = ressObj.cargarCFDI.find(function (a, e) { return a.id == ressObj.CFDISeleccionado }),
                metodoPagoSeleccionado = ressObj.cargarMetodosPago.find(function (a, e) { return a.id == ressObj.metodoPagoSeleccionado });

            if (!CFDISeleccionado || !metodoPagoSeleccionado) {
                showToastr('No selecionó un CFDI o un método de pago', 'Aviso', {
                    type: typeNotification.warning
                });
                return false;
            }

            var model = [{
                cfdi: CFDISeleccionado.descripcion,
                metodoPago: metodoPagoSeleccionado.descripcion
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