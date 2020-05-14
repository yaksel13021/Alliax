var creacionUsuario = (function () {
    var $dt = null;

    var init = function () {
        initEvents();
        cargarDTAddUsaers.fill();
    };

    var initEvents = function () {
    };

    var cargarDTAddUsaers = {
        fill: function (rs) {
            if ($dt) {
                $dt.clear().destroy();
            }
            debugger
            $dt = document.querySelector('#dt_AddUsers').rssDataTable({
                order: [0, 'asc'],
                scrollX: true,
                searching: true,
                paging: false,
                // data: rs,
                responsive: true,
                free: function (data, type, row, meta) {
                    if (meta.col === 1) {
                        return '<input class="addUser_checkbox" type="checkbox" checked data-toggle="toggle" data-on="SI" data-off="NO" data-onstyle="success" data-offstyle="danger">'
                    }
                    if (meta.col === 3) {
                        return '<input type="text" class="form-control addusers_email" name="addusers_email" placeholder="Correo electrónico">'
                    }
                },
                actions: function (data, type, row, meta) {
                    return '<h:commandButton type="button" name="addusers_btn_grabar" class="btn btn-primary addusers_btn_grabar" role="button">Grabar</h:commandButton>'
                },
                rowCallback: function (row, data, api) {
                    $(row).find('.addusers_btn_grabar').off().on('click', function (e) {
                        var checkValue = $(row).find('.addUser_checkbox').prop('checked'),
                            emailValue = $(row).find('.addusers_email').val();

                        if(!emailValue){
                            showToastr('Ingrese un correo', 'Aviso', {
                                type: typeNotification.warning
                            });
                            return;
                        }
                        showToastr('Se registro correctamente', '', {
                            type: typeNotification.success
                        });
                    });
                },
                initComplete: function (settings, json, api) {
                    $('.addUser_checkbox').bootstrapToggle();
                }
            });
            return true;
        }
    };

    return {
        init: init
    };
})();
$(document).ready(function () {
    $('.addUser_checkbox').bootstrapToggle({
    on: 'SI',
    off: 'NO',
    offstyle : 'danger',
    onstyle : 'success'
    });

    var actividad = "RC";
    document.getElementById("form1:actividad1").value = actividad;
    var actividad2 = "RS";
    document.getElementById("form2:actividad2").value = actividad2;
    var actividad3 = "RE";
    document.getElementById("form3:actividad3").value = actividad3;
    $("#dt_AddUsers").show();
});

function validaCola1() {
    var input1 = document.getElementById("form1:emailCola").value;
    var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var onerror = false;
    if (input1 == "") {
        showToastr('completa el campo : Correo', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    } else if (!regex.test(input1)) {
        showToastr('incluye un signo "@" en dirección de correo. La dirección "' + input1 + '" no incluye el signo "@"', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (!onerror) {
        var activo = document.getElementById('form1:activo').checked;
        document.getElementById("form1:activoHidden").value = activo;
        document.getElementById('form1:butt').click();
    }
}
function validaCola2() {
    var input1 = document.getElementById("form2:emailCola2").value;
    var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var onerror = false;
    if (input1 == "") {
        showToastr('completa el campo : Correo', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    } else if (!regex.test(input1)) {
        showToastr('incluye un signo "@" en dirección de correo. La dirección "' + input1 + '" no incluye el signo "@"', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (!onerror) {
        var activo = document.getElementById('form1:activo').checked;
        document.getElementById("form2:activoHidden2").value = activo;
        document.getElementById('form2:butt2').click();
    }
}
function validaCola3() {
    var input1 = document.getElementById("form3:emailCola3").value;
    var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var onerror = false;
    if (input1 == "") {
        showToastr('completa el campo : Correo', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    } else if (!regex.test(input1)) {
        showToastr('incluye un signo "@" en dirección de correo. La dirección "' + input1 + '" no incluye el signo "@"', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (!onerror) {
        var activo = document.getElementById('form3:activo3').checked;
        document.getElementById("form3:activoHidden3").value = activo;
        document.getElementById('form3:butt3').click();
    }
}

function verificar(){
    var message = document.getElementById("msg").value;
    if (message == 0){
        showToastr('Error al crear usuario, es necesario registrar los datos de contacto en SAP', 'Aviso', {
            type: typeNotification.warning
        });
    }else if (message == 1){
        showToastr('El usuario se creó correctamente y se le informó la manera de acceder al portal.', 'Aviso', {
            type: typeNotification.success
        });
    }else if (message ==2){
        showToastr('El usuario se Actualizó correctamente y se le informó la manera de acceder al portal.', 'Aviso', {
            type: typeNotification.success
        });
    }
}