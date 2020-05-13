function reload(){
        var select = document.getElementById("form:select_buscarPor");
        var value = select.value;
        if (value == 1) {
            $('[id="form:select_localidad"]').select2({
                theme: "bootstrap",
                allowClear: true,
                placeholder: "Seleccione una opción",
                language: Select2Languaje(),
                multiple: false,
                width: "100%",
            });
            $('[id="form:frm_localidad"]').select2({
                theme: "bootstrap",
                allowClear: true,
                placeholder: "Seleccione una opción",
                language: Select2Languaje(),
                multiple: false,
                width: "100%",
            });
            $(".local").show();
            $(".plantas").hide();
        } else if(value ==2){
            $('[id="form:frm_PlantaPta"]').select2({
                theme: "bootstrap",
                allowClear: true,
                placeholder: "Seleccione una opción",
                language: Select2Languaje(),
                multiple: false,
                width: "100%",
            });
            $('[id="form:frm_estadoPta"]').select2({
                theme: "bootstrap",
                allowClear: true,
                placeholder: "Seleccione una opción",
                language: Select2Languaje(),
                multiple: false,
                width: "100%",
            });
            $('[id="form:frm_localidadPta"]').select2({
                theme: "bootstrap",
                allowClear: true,
                placeholder: "Seleccione una opción",
                language: Select2Languaje(),
                multiple: false,
                width: "100%",
            });
            $(".plantas").show();
            $(".local").hide();
        }else{
            $(".plantas").hide();
            $(".local").hide();
        }
}

    function validaInput() {
        if (document.getElementById("form:frm_noMaterial").value == "" && document.getElementById("form:frm_noMaterial").value.length == 0) {
            showToastr('El No. de Material es obligatorio.', 'Aviso', {
                type: typeNotification.warning
            })
        }else{
            document.getElementById('form:butt').click();
        }
    }


function filtrar() {
    var table = $('[id="form:productois"]').DataTable();
    table.destroy();
    $('.collapse').collapse('hide');
    $('[id="form:productois"]').DataTable({
        paging: true,
        searching: true,
        lengthChange: false,
        scrollY: true,
        "scrollX": true,
        language: {
            "decimal": "",
            "emptyTable": "No hay información",
            "info": "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
            "infoEmpty": "Mostrando 0 to 0 of 0 Entradas",
            "infoFiltered": "(Filtrado de _MAX_ total entradas)",
            "infoPostFix": "",
            "thousands": ",",
            "lengthMenu": "Mostrar _MENU_ Entradas",
            "loadingRecords": "Cargando...",
            "processing": "Procesando...",
            "search": "Buscar:",
            "zeroRecords": "Sin resultados encontrados",
            "paginate": {
                "first": "Primero",
                "last": "Ultimo",
                "next": "Siguiente",
                "previous": "Anterior"
            }
        },})
}

function tablaMessage(){
    showToastr('No se encontraron resultados con los criterios de busqueda especificados.', 'Aviso', {
        type: typeNotification.warning
    })
}

$(document).ready(function(e) {
    $('[id="form:select_buscarPor"]').select2({
        theme: "bootstrap",
        allowClear: true,
        placeholder: "Seleccione una opción",
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });

});

function firstFill(){
    $('[id="form:select_buscarPor"]').select2({
        theme: "bootstrap",
        allowClear: true,
        placeholder: "Seleccione una opción",
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });
}





