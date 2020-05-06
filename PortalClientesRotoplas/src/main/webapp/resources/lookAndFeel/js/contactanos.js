var motivQueja = (function () {

    var init = function () {
        comboMotQueja.fill();
        comboPlanta.fill();
        comboTipoDoc.fill();
    };

    var comboMotQueja = {
       
        fill: function () {
        
            return this.data()
                .then(function (rs) {
                    if (!rs) {
                        showToastr('No existen tipos de documentos', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }
                   

                    if ($("#select_motivoQueja").data('select2')) {
                        $("#select_motivoQueja").select2("destroy");
                        $("#select_motivoQueja").empty();
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
                    $("#select_motivoQueja").append("<option></option>");
                    $("#select_motivoQueja").append(selectTemplate);
                    $("#select_motivoQueja").select2({
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
                    showToastr('No existen Motivos de queja', 'Aviso', {
                        type: typeNotification.warning
                    });
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model = [{
                    id: 1,
                    descripcion: 'Capturar domicilio equivocado'
                },
                {
                    id: 2,
                    descripcion: 'Faltante de entrega'
                },
                {
                    id: 3,
                    descripcion: 'Producto mal terminado'
                }
                ];
                resolve(model);
            });
        }
    }; 

    var comboPlanta = {
       
        fill: function () {
        
            return this.data()
                .then(function (rs) {
                    if (!rs) {
                        showToastr('No existen tipos de documentos', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }
                   

                    if ($("#select_planta").data('select2')) {
                        $("#select_planta").select2("destroy");
                        $("#select_planta").empty();
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
                    $("#select_planta").append("<option></option>");
                    $("#select_planta").append(selectTemplate);
                    $("#select_planta").select2({
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
                    showToastr('No existen Motivos de queja', 'Aviso', {
                        type: typeNotification.warning
                    });
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model = [{
                    id: 1,
                    descripcion: 'Anahuac'
                },
                {
                    id: 2,
                    descripcion: 'Golfo'
                },
                {
                    id: 3,
                    descripcion: 'Tuxtla'
                }
                ];
                resolve(model);
            });
        }
    }; 

    var comboTipoDoc = {
       
        fill: function () {
        
            return this.data()
                .then(function (rs) {
                    if (!rs) {
                        showToastr('No existen tipos de documentos', 'Aviso', {
                            type: typeNotification.warning
                        });
                        return false;
                    }
                   

                    if ($("#select_tipoDocumento").data('select2')) {
                        $("#select_tipoDocumento").select2("destroy");
                        $("#select_tipoDocumento").empty();
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
                    $("#select_tipoDocumento").append("<option></option>");
                    $("#select_tipoDocumento").append(selectTemplate);
                    $("#select_tipoDocumento").select2({
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
                    showToastr('No existen Motivos de queja', 'Aviso', {
                        type: typeNotification.warning
                    });
                    return false;
                });
        },
        data: function () {
            return new Promise(function (resolve, reject) {
                var model = [{
                    id: 1,
                    descripcion: 'Folio de Queja'
                },
                {
                    id: 2,
                    descripcion: 'No. de factura'
                },
                {
                    id: 3,
                    descripcion: 'No. de pedido'
                }
                ];
                resolve(model);
            });
        }
    }; 

    return {
        init: init
        
    };
})();
$(document).ready(function () {
    motivQueja.init();
});