var consultaExistencia = (function () {
    var $dt = null;
var buscarPor = null;
  var init = function () {
    comboBuscarPor.fill();
  };

  var comboBuscarPor = {
    fill: function () {
      return this.data()
        .then(function (rs) {
          if (!rs) {
            showToastr("No existen valores para el combo Buscar por", "Aviso", {
              type: typeNotification.warning,
            });
            return false;
          }

          if ($("#select_buscarPor").data("select2")) {
            $("#select_buscarPor").select2("destroy");
            $("#select_buscarPor").empty();
          }

          var result = [];
          for (var i = 0; i < rs.length; i++) {
            result.push({
              value: rs[i].id,
              descripcion: rs[i].descripcion,
              dataAtributos: 'data-id="' + rs[i].id + '"',
            });
          }

          var selectTemplate = renderMustacheTemplate(
            "select2_template",
            result
          );
          $("#select_buscarPor").append("<option></option>");
          $("#select_buscarPor").append(selectTemplate);
          $("#select_buscarPor").select2({
            theme: "bootstrap",
            allowClear: true,
            placeholder: "Seleccione una opción",
            language: Select2Languaje(),
            multiple: false,
            width: "100%",
          });
          return true;
        })
        .catch(function () {
          showToastr("No existen valores para el combo Buscar por", "Aviso", {
            type: typeNotification.warning,
          });
          return false;
        });
    },
    data: function () {
      return new Promise(function (resolve, reject) {
          var a = document.getElementById("test").value;
          var b = a.split(",")
          var model = [];
          for (var i=0; i<b.length; i++) {
              var dato;
              if(i === b.length-1){
                  var dat = b[i].split("=")[1];
                  dato = dat.split("]")[0];
              }else{
                  dato = b[i].split("=")[1];
              }
              model.push(
                  {
                      id: i+1,
                      descripcion: dato,
                  }
              );
          }
        resolve(model);
      });
    },
  };

  var comboPlanta = {
    fill: function () {
      return this.data()
        .then(function (rs) {
          if (!rs) {
            showToastr("No existen valores", "Aviso", {
              type: typeNotification.warning,
            });
            return false;
          }

          if ($("#select_planta").data("select2")) {
            $("#select_planta").select2("destroy");
            $("#select_planta").empty();
          }

          var result = [];
          for (var i = 0; i < rs.length; i++) {
            result.push({
              value: rs[i].id,
              descripcion: rs[i].descripcion,
              dataAtributos: 'data-id="' + rs[i].id + '"',
            });
          }

          var selectTemplate = renderMustacheTemplate(
            "select2_template",
            result
          );
          $("#select_planta").append("<option></option>");
          $("#select_planta").append(selectTemplate);
          $("#select_planta").select2({
            theme: "bootstrap",
            allowClear: true,
            placeholder: "Seleccione una opción",
            language: Select2Languaje(),
            multiple: false,
            width: "100%",
          });
          return true;
        })
        .catch(function () {
          showToastr("No existen valores", "Aviso", {
            type: typeNotification.warning,
          });
          return false;
        });
    },
    data: function () {
      return new Promise(function (resolve, reject) {
          $("#tipo").val($("#select_buscarPor option:selected").text());
          document.getElementById('hiddenForm:invisibleClickTarget').click();
          setTimeout(() => {
              var a = document.getElementById("some").value;
              var b = a.split(",")
              var model = [];
              for (var i=0; i<b.length; i++) {
                  var dato;
                  if(i === b.length-1){
                      var dat = b[i].split("=")[1];
                      dato = dat.split("]")[0];
                  }else{
                      dato = b[i].split("=")[1];
                  }
                  model.push(
                      {
                          id: i+1,
                          descripcion: dato,
                      }
                  );
              }
              resolve(model);
          }, 1000);


      });
    },
  };
  var comboEstado = {
    fill: function () {
      return this.data()
        .then(function (rs) {
          if (!rs) {
            showToastr("No existen valores", "Aviso", {
              type: typeNotification.warning,
            });
            return false;
          }

          if ($("#select_estado").data("select2")) {
            $("#select_estado").select2("destroy");
            $("#select_estado").empty();
          }

          var result = [];
          for (var i = 0; i < rs.length; i++) {
            result.push({
              value: rs[i].id,
              descripcion: rs[i].descripcion,
              dataAtributos: 'data-id="' + rs[i].id + '"',
            });
          }

          var selectTemplate = renderMustacheTemplate(
            "select2_template",
            result
          );
          $("#select_estado").append("<option></option>");
          $("#select_estado").append(selectTemplate);
          $("#select_estado").select2({
            theme: "bootstrap",
            allowClear: true,
            placeholder: "Seleccione una opción",
            language: Select2Languaje(),
            multiple: false,
            width: "100%",
          });
          return true;
        })
        .catch(function () {
          showToastr("No existen valores", "Aviso", {
            type: typeNotification.warning,
          });
          return false;
        });
    },
    data: function () {
      return new Promise(function (resolve, reject) {
          document.getElementById('hiddenFormp:invisibleClickTargetplanta').click();
          setTimeout(() => {
          var a = document.getElementById("somes").value;
          var b = a.split(",")
          var model = [];
          for (var i=0; i<b.length; i++) {
              var dato;
              if(i === b.length-1){
                  var dat = b[i].split("=")[1];
                  dato = dat.split("]")[0];
              }else{
                  dato = b[i].split("=")[1];
              }
              model.push(
                  {
                      id: i+1,
                      descripcion: dato,
                  }
              );
          }
        resolve(model);
          }, 1000);
      });
    },
  };
  var comboLocaliad = {
    fill: function () {
      return this.data()
        .then(function (rs) {
          if (!rs) {
            showToastr("No existen valores", "Aviso", {
              type: typeNotification.warning,
            });
            return false;
          }

          if ($("#select_localidad").data("select2")) {
            $("#select_localidad").select2("destroy");
            $("#select_localidad").empty();
          }

          var result = [];
          for (var i = 0; i < rs.length; i++) {
            result.push({
              value: rs[i].id,
              descripcion: rs[i].descripcion,
              dataAtributos: 'data-id="' + rs[i].id + '"',
            });
          }

          var selectTemplate = renderMustacheTemplate(
            "select2_template",
            result
          );
          $("#select_localidad").append("<option></option>");
          $("#select_localidad").append(selectTemplate);
          $("#select_localidad").select2({
            theme: "bootstrap",
            allowClear: true,
            placeholder: "Seleccione una opción",
            language: Select2Languaje(),
            multiple: false,
            width: "100%",
          });
          return true;
        })
        .catch(function () {
          showToastr("No existen valores", "Aviso", {
            type: typeNotification.warning,
          });
          return false;
        });
    },
    data: function () {
        $('#tipo').val(document.getElementById("select_buscarPor").value)
        document.getElementById('hiddenForm:invisibleClickTarget').click();

      return new Promise(function (resolve, reject) {
          var a = document.getElementById("localidad").value;
          var b = a.split(",")
          var model = [];
          for (var i=0; i<b.length; i++) {
              var dato;
              if(i === b.length-1){
                  var dat = b[i].split("=")[1];
                  dato = dat.split("]")[0];
              }else{
                  dato = b[i].split("=")[1];
              }
              model.push(
                  {
                      id: i+1,
                      descripcion: dato,
                  }
              );
          }
        resolve(model);
      });
    },
  };

  var cargarDTSearchProductos = {
    fill: function () {
        return this.data()
            .then(function (rs) {
                if (!rs) {
                    showToastr('Error en la carga', 'Aviso', {
                        type: typeNotification.warning
                    })
                    return false;
                }
               

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
                       
                    },
                    rowCallback: function (row, data, api) {
                       
                    }
                });
                return true;
            })
            .catch(function () {
                showToastr('Error en la carga', 'Aviso', {
                    type: typeNotification.warning
                })
                return false;
            });
    },
    data: function () {
        return new Promise(function (resolve, reject) {
            alert(document.getElementById("productois").value)
            setTimeout(() => {
                var model = [{
                    id: 1,
                    material: '5003094',
                    descripcion: 'Prueba material',
                    planta: 'Centro Rotoplas Monterrey',
                    stockLibre: "1",
                    um: "24 PZA"
                },

                ];
                resolve(model);
            }, 1000)
        });
    }
};

  $("#select_buscarPor").on("change", function () {
    buscarPor = $(this).val() ;
    if (buscarPor == 1) {
      $(".localidad").show();
      $(".planta").hide();
      /*comboEstado.fill();
      comboLocaliad.fill();*/
    } else {
      $(".planta").show();
      $(".localidad").show();
      /*comboPlanta.fill();
      comboEstado.fill();
      comboLocaliad.fill();*/
    }
  });

    $("#select_planta").on("change", function () {
        /*comboEstado.fill();*/
    });

  $('#boxMatBoton').off().on('click', function () {

    if(document.getElementById("form:frm_noMaterial").value !== "" && document.getElementById("form:frm_noMaterial").value.length > 0){
    loadMustacheTemplate('searchProducts_template', 'cardDynamicBody');
    cargarDTSearchProductos.fill();
    $('div.hidden').removeClass('hidden');
  
    $('.collapse').collapse('hide');
    $('.isResizable').matchHeight();
    }else{
        showToastr('El No. de Material es obligatorio.', 'Aviso', {
            type: typeNotification.warning
        })
    }
  });

  $('#select_estado').on("change", function ()  {
        comboLocaliad.fill($(this).val());

  });

  return {
    init: init,
  };
})();
function reload(){
   setTimeout(() => {
        var select = document.getElementById("form:select_buscarPor"); //El <select>
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
        }, 200);
}

function filtrar() {
    if(document.getElementById("form:frm_noMaterial").value == "" && document.getElementById("form:frm_noMaterial").value.length == 0){
        showToastr('El No. de Material es obligatorio.', 'Aviso', {
            type: typeNotification.warning
        })
    }else{
        $('.collapse').collapse('hide');
    }
}

$.fn.selectpicker.Constructor.BootstrapVersion = '4';

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




