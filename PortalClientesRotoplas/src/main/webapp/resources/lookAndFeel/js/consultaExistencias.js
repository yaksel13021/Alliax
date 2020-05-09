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
        var model = [
          {
            id: 1,
            descripcion: "Localidad",
          },
          {
            id: 2,
            descripcion: "Planta",
          },
        ];
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
        var model = [
          {
            id: 1,
            descripcion: "Golfo",
          },
          {
            id: 2,
            descripcion: "Guadalajara",
          },
        ];
        resolve(model);
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
        var model = [
          {
            id: 1,
            descripcion: "Tamaulipas",
          },
          {
            id: 2,
            descripcion: "Nuevo León",
          },
        ];
        resolve(model);
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
      return new Promise(function (resolve, reject) {
        var model = [
          {
            id: 1,
            descripcion: "Tamaulipas",
          },
          {
            id: 2,
            descripcion: "Nuevo León",
          },
        ];
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
        });
    }
};

  $("#select_buscarPor").on("change", function () {
    buscarPor = $(this).val() ;
    if (buscarPor == 1) {
      $(".localidad").show();
      $(".planta").hide();
      comboEstado.fill();
      comboLocaliad.fill();
    } else {
      $(".planta").show();
      $(".localidad").show();
      comboPlanta.fill();
      comboEstado.fill();
      comboLocaliad.fill();
    }
  });

  $('#buscar').off().on('click', function () {
  
    if($("#input_nMatDesc").val() !== "" && $("#input_nMatDesc").val().length > 0){
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
$(document).ready(function () {
  consultaExistencia.init();
});
