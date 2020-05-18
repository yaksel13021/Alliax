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

var catalogoMateriales = (function () {
    var init = function () {
        initEvents();
    };

    var initEvents = function () {
        $("#agregar").off().on("click", function (e) {
            $( "#pantallaBusqueda" ).addClass( "hidden" );
            $( "#filtroBusqueda" ).addClass( "hidden" );
            $( "#pantallaAgregarMateriales" ).removeClass( "hidden" )
        });

        $("#cancelar").off().on("click", function (e) {

            $( "#pantallaAgregarMateriales" ).addClass( "hidden" );
            $( "#pantallaBusqueda" ).addClass( "hidden" );
            $( "#filtroBusqueda" ).removeClass( "hidden" )
        });

        $("#buscar").off().on("click", function (e) {
            $( "#pantallaBusqueda" ).removeClass( "hidden" );
            $("#input_imagen").val("Prueba_Img");
            $("#select_tipoMaterialB").val(1);
            $("#input_descpB").val("descruip_prueba");
            $("#input_umB").val("prueba_um");
            $("#input_nsku").val("prueba_sku");
        });

        $("#guardar").off().on("click", function (e) {
            $( "#pantallaAgregarMateriales" ).addClass( "hidden" );
            $( "#pantallaBusqueda" ).addClass( "hidden" );
            $( "#filtroBusqueda" ).removeClass( "hidden" )
        });

        $("#actualizar").off().on("click", function (e) {
            var tipoMaterial =   $("#select_tipoMaterialB").val();
            var descripcion =   $("#input_descpB").val();
            var um =   $("#input_umB").val();
            var Nsku =   $("#input_nsku").val();
            var imagen =   $("#input_imagen").val()
        });
    };

    return {
        init: init
    };
})();

$(document).ready(function () {
    catalogoMateriales.init();
});

function validaBusqueda() {
    if (document.getElementById("form:input_descp") == ""){
        showToastr('El No. de Material es obligatorio.', 'Aviso', {
            type: typeNotification.warning
        })
    }else{
        document.getElementById('form:buscar').click();
    }
}


function valorDatos() {
    var tipoMat = document.getElementById("form:tipoMatBus").value;
    if (tipoMat == 11){
        document.getElementById("form:tipoMatBus").value = "Almacenamiento mejoramiento Conducción";
    }else {
        document.getElementById("form:tipoMatBus").value = "Indutria";
    }

    $('[id="form:pantallaBusqueda"]').show();
}

function mensajeActualizacion() {
    var tipo = document.getElementById("form:tipoMenssage").value;
    if (tipo == 20){
        showToastr('El material se actualizo correctamente', 'Aviso', {
            type: typeNotification.success
        })
    }else if (tipo == 22) {
        showToastr('Ocurrio un error al actualizar los datos', 'Aviso', {
            type: typeNotification.warning
        })
    }
}

function  validaMat() {
    var input2 = $('[id="form:input_descpB"]').val();
    var input3 = $('[id="form:input_umB"]').val();
    var input4 = document.getElementById("form:input_imagen").value;
    var onerror = false;

    if (input2 == "") {
        showToastr('completa el campo : Descripción', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input3 == "") {
            showToastr('completa el campo : UM', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (!onerror) {
        $('[id="form:actualizainput"]').val(input2 +","+input3+","+input4)
        document.getElementById('form:actualizar').click();
    }
}

    function mensajeSave() {
        var tipo = document.getElementById("tipoSave").value;
        if (tipo == "6"){
            showToastr('El material se guardo correctamente', 'Aviso', {
                type: typeNotification.success
            })
        }else if (tipo == "5") {
            showToastr('El material que trata de guardar ya existe', 'Aviso', {
                type: typeNotification.warning
            })
        } else if (tipo == "4") {
            showToastr('ocurrio un error al tratar de guardar el material', 'Aviso', {
                type: typeNotification.warning
            })
        }
    }

    function cancelar(){
        $( "#pantallaAgregarMateriales" ).hide();
        $('[id="form:pantallaBusqueda"]').hide();
        $('[id="form:pantallaBusq"]').hide();
        $( "#filtroBusqueda" ).show()
    }

    function agregar() {
        $('[id="form2:tipoMaterial"]').select2({
            theme: "bootstrap",
            allowClear: true,
            placeholder: "Seleccione una opción",
            language: Select2Languaje(),
            multiple: false,
            width: "100%",
        });
        $( '[id="form:pantallaBusqueda"]').hide();
        $( "#filtroBusqueda" ).hide();
        $( "#pantallaAgregarMateriales" ).show()
    }

function agregarMasivo() {
    $('[id="formMasivo:inputFileTip"]').select2({
        theme: "bootstrap",
        allowClear: true,
        placeholder: "Seleccione una opción",
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });

    $("#carga").show();
    $("#filtroBusqueda" ).hide();
    $('[id="form:pantallaBusq"]').hide();
}

function  ValidarGuarda() {
    var input1 = $('[id="form2:input_descripcion"]').val();
    var input2 = $('[id="form2:input_um"]').val();
    var input3 = $('[id="form2:input_sku"]').val();
    var onerror = false;

    if (input1 == "") {
        showToastr('completa el campo : Descripcion', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input2 == "") {
        showToastr('completa el campo : UM', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (input3 == "") {
        showToastr('completa el campo : No. Sku', 'Aviso', {
            type: typeNotification.warning
        });
        onerror = true;
    }
    if (!onerror) {
        document.getElementById('form2:guardar').click();
    }
}


