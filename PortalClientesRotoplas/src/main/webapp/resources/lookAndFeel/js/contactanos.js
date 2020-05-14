
function contactanos() {
    $('[id="00N55000003mwze"]').select2({
        theme: "bootstrap",
        allowClear: true,
        placeholder: "Seleccione una opción",
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });
    $('[id="00N55000003mpKy"]').select2({
        theme: "bootstrap",
        allowClear: true,
        placeholder: "Seleccione una opción",
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });
    $('[id="00N55000003mq19"]').select2({
        theme: "bootstrap",
        allowClear: true,
        placeholder: "Seleccione una opción",
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
    }else if(!regex.test(input3)){
        showToastr('incluye un signo "@" en dirección de correo. La dirección "'+input3+'" no incluye el signo "@"' , 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input4 == ""){
        showToastr('completa el campo : No. de documento', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input5 == ""){
        showToastr('completa el campo : Comentarios', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (!onerror){
        document.getElementById('form').submit();
    }
}

