function Select2Languaje() {
    return {
        noResults: function () { return "No hay resultados" },
        searching: function () { return "Buscando..." },
        errorLoading: function () { return "No se pudieron cargar los resultados" },
        inputTooShort: function (e) {
            t = e.minimum - e.input.length, n = "Por favor, introduzca " + t + " car";
            return t == 1 ? n += "ácter" : n += "acteres", n
        }
    };
}
var progressAnimationEnum = {
    decreasing: 'decreasing',
    increasing: 'increasing'
};
var typeNotification = {
    error: 'error',
    info: 'info',
    success: 'success',
    warning: 'warning'
};

function showToastr(message, title, override) {
    var propertiesDefault = {
        type: typeNotification.success,
        html: false,
        closeBtn: false,
        timeOut: 5000,
        progressBar: false,
        progressAnimation: progressAnimationEnum.decreasing,
        tapToDismiss: false,
        disableTimeOut: false
    };
    _.defaults(override, propertiesDefault);

    toastr[override.type](message, title, {
        enableHtml: override.html,
        closeButton: override.closeBtn,
        timeOut: override.timeOut,
        progressBar: override.progressBar,
        progressAnimation: override.progressAnimation,
        tapToDismiss: override.tapToDismiss,
        disableTimeOut: override.disableTimeOut
    });
}



function contactanos() {
    $('[id="00N55000003mwze"]').select2({
        theme: "bootstrap",
        allowClear: true,
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });
    $('[id="00N55000003mpKy"]').select2({
        theme: "bootstrap",
        allowClear: true,
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });
    $('[id="00N55000003mq19"]').select2({
        theme: "bootstrap",
        allowClear: true,
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });
}

$( document ).ready(function() {
    contactanos();
});


function succesReload() {
    showToastr('Petición enviada satisfactoriamente', 'Aviso', {
        type: typeNotification.success
    });
}

function valida() {
    var input1 = $("#name").val();
    var input2 = $("#phone").val();
    var input3 = $("#00N55000003mpKo").val();
    var input4 = $("#00N55000003mpLD").val();
    var input5 = $("#00N55000003mpLI").val();
    var onerror = false;
    var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

    if (input1 == "") {
        showToastr('completa el campo : Nombre del contacto', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input2 == "") {
        showToastr('completa el campo : Teléfono', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input3 == "") {
        showToastr('completa el campo : Email', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    } else if (!regex.test(input3)) {
        showToastr('incluye un signo "@" en dirección de correo. La dirección "' + input3 + '" no incluye el signo "@"', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input4 == "") {
        showToastr('completa el campo : No. de documento', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input5 == "") {
        showToastr('completa el campo : Comentarios', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (!onerror) {
        document.getElementById('form:butt').click();
    }
}

    function execCommandButtonAjax(data) {
        var status = data.status;
        switch (status) {
            case 'begin': {
                break;
            }
            case 'complete': {
                break;
            }
            case 'success': {
                succesReload();
            }
        }
    }

