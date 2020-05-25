


var inpescIngreso = [{
  DocComercial: 9040471872,
  OrdenCompra: 1054023838,
  monto: "$24,681.00",
  creado: "01/04/2020",
  estatus: "Verificar SAC",
  estatusId: 3,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 9040471812,
  OrdenCompra: 1054023138,
  monto: "$14,681.00",
  creado: "02/04/2020",
  estatus: "Verificar SAC",
  estatusId: 3,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 9040471872,
  OrdenCompra: 1054023838,
  monto: "$24,681.00",
  creado: "01/04/2020",
  estatus: "Verificar SAC",
  estatusId: 3,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 9040471872,
  OrdenCompra: 1054023838,
  monto: "$24,681.00",
  creado: "01/04/2020",
  estatus: "Verificar SAC",
  estatusId: 3,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 9140471872,
  OrdenCompra: 1054023838,
  monto: "$24,681.00",
  creado: "02/04/2020",
  estatus: "Verificar SAC",
  estatusId: 3,
  destino: "Con1. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 9008971874,
  OrdenCompra: 10345023858,
  monto: "$20,681.00",
  creado: "11/02/2020",
  estatus: "Concluido",
  estatusId: 2,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 95471871,
  OrdenCompra: 100345818,
  monto: "$29,681.00",
  creado: "11/04/2020",
  estatus: "Pendiente",
  estatusId: 1,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 93450071875,
  OrdenCompra: 1008923898,
  monto: "$22,681.00",
  creado: "11/02/2020",
  estatus: "Pendiente",
  estatusId: 1,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 90056671873,
  OrdenCompra: 1007823858,
  monto: "$21,681.00",
  creado: "11/03/2020",
  estatus: "Verificar SAC",
  estatusId: 3,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 9003571876,
  OrdenCompra: 1008923828,
  monto: "$25,681.00",
  creado: "11/01/2020",
  estatus: "Concluido",
  estatusId: 2,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
{
  DocComercial: 9007201871,
  OrdenCompra: 100239818,
  monto: "$27,681.00",
  creado: "11/04/2020",
  estatus: "Verificar SAC",
  estatusId: 3,
  destino: "Con. Suc. Periférico / Tablaje Castral 97210 Merida Sin distrito / colonia",
},
];

/*var dataEstCuenta = [{
  tipoDocumento: "Nota de Credito",
  Npedido: "0410040814",
  ordenDeCompra: "507228",
  factSap: "3100041617",
  factFiscal: "8A36FE79-E5A2-40C1-A31C-6E0B9FC7F3A7",
  xml: "",
  pdf: "",
  facturaRelac: "1100537766",
  UUid: "C8368D94-6021-494E-A44E-B308F5BFFE03",
  fechaFact: "11/02/2020",
  vencimiento: "11/02/2020",
  diasMora: "3",
  importe: "$2500",
  estatus: "VENCIDO"
}, {
  tipoDocumento: "Factura",
  Npedido: "0000598133",
  ordenDeCompra: "507175",
  factSap: "1100529741",
  factFiscal: "CAFDDCF0-792B-4803-A417-372A891F451F ",
  xml: "",
  pdf: "",
  facturaRelac: "90187898",
  UUid: "C8368D94-6021-494E-A44E-B308F5BF2FE03",
  fechaFact: "12/02/2020",
  vencimiento: "13/02/2020",
  diasMora: "13",
  importe: "$22,500",
  estatus: "A VENCER"
}
]*/
var $tablaEstCuentaDT = null;
var $tablaCotizaciones = null;
$(document).ready(function () {
  var $expampleDT = null;
  var $example2 = null;
  var $example3 = null;
  
  var Graf_Doughnut_demo = null;
  
  var isRoleExterno = $("[id='form:isRoleExterno']").val();
  if(!isRoleExterno) {	
	  $("[id='form:panelEmpresa']").show();
	  $("[id='form:panelFechaCorte']").show();
	  $("[id='form:panelBotonGenerar']").show();
   }

  var isIE = window.ActiveXObject || "ActiveXObject" in window;
  if (isIE) {
    $('.modal').removeClass('fade');
    $('.tab-pane.fade').removeClass('fade');
  }

  var expampleDTFunc = function (data) {
    $expampleDT = $("#example").DataTable({
      paging: true,
      searching: false,
      lengthChange: false,
      /*data: data,*/
      scrollY: true,
      "scrollX": true,

      columns: [
        { data: "DocComercial", class: "colorLetra", orderable: false },
        { data: "OrdenCompra", class: "colorLetra", orderable: false },
        { data: "monto", class: "colorLetra" },
        { data: "creado", class: "colorLetra" },
        { data: "estatus", class: "colorLetra" },
        { data: "destino", class: "colorLetra", width: '35%' },
      ],
      columnDefs: [{
        targets: -1,
        className: "dt-body-right",
      },],
      fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        if (iDisplayIndex % 2 == 0) {
          $("td", nRow).css({ 'background-color': "rgb(0, 144, 208, 0.3)" });
        } else {
          $("td", nRow).css("background-color", "#ffff");
        }
      },
      initComplete: function (settings, json) {
        setTimeout(function () {
          $($.fn.dataTable.tables(true)).DataTable().columns.adjust();
        }, 200);
      },
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
      },
    });
  }

  // expampleDTFunc(inpescIngreso);

  var example2Func = function (data) {
    $example2 = $("#example2").DataTable({
      paging: true,
      searching: false,
      lengthChange: false,
      /*data: data, */
      scrollY: true,
      "scrollX": true,
      columns: [
        { data: "pos", class: "colorLetra1" },
        { data: "mat", class: "colorLetra1" },
        { data: "Descripcion", class: "colorLetra1" },
        { data: "cant", class: "colorLetra1" },
        { data: "cantEnt", class: "colorLetra1" },
        { data: "um", class: "colorLetra1" },
        { data: "monto", class: "colorLetra1" },
        { data: "precioNeto", class: "colorLetra1" },
        { data: "fecEnt", class: "colorLetra1" },
        { data: "estatus", class: "colorLetra1" },
      ],
      columnDefs: [{
        targets: -1,
        className: "dt-body-right",
      },],
      fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        if (iDisplayIndex % 2 == 0) {
          $("td", nRow).css("background-color", "rgb(0,144,208,.3)");
        } else {
          $("td", nRow).css("background-color", "#ffff");
        }
      },
      initComplete: function (settings, json) {
        setTimeout(function () {
          $($.fn.dataTable.tables(true)).DataTable().columns.adjust();
        }, 200);
      },
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
      },
    });
  }


  var example3Func = function (data) {
    $example3 = $("#example3").DataTable({
      paging: true,
      searching: false,
      lengthChange: false,
      /*data: data, */
      scrollY: true,
      "scrollX": true,
      columns: [
        { data: "docFactura", class: "colorLetra1" },
        { data: "fecha", class: "colorLetra1" },
        { data: "impNeto", class: "colorLetra1" },
        { data: "Monto", class: "colorLetra1" },
      ],
      columnDefs: [{
        targets: -1,
        className: "dt-body-right",
      },],
      fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        if (iDisplayIndex % 2 == 0) {
          $("td", nRow).css("background-color", "rgb(0,144,208,.3)");
        } else {
          $("td", nRow).css("background-color", "#ffff");
        }
      },
      initComplete: function (settings, json) {
        setTimeout(function () {
          $($.fn.dataTable.tables(true)).DataTable().columns.adjust();
        }, 200);
      },
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
      },
    });
  }

  var events = function () {
    $(window).off().on("resize", function () {
      setTimeout(function () {
        $($.fn.dataTable.tables(true)).DataTable().columns.adjust();
      }, 100);
    });

    $('#misPedidosTabs  a[href="#facturas"]').off().on('click', function(){
        $('#misPedidosTabs  a[href="#facturas"]').tab('show');
      });
      $('#misPedidosTabs  a[href="#partidas"]').off().on('click', function(){
        $('#misPedidosTabs  a[href="#partidas"]').tab('show');
      });
    
    // $("tbody > tr .colorLetra").off().on("click", function () {
    //   $("#myModal").modal("toggle");
    //   if ($example2) {
    //     $example2.clear().destroy();
    //   }
    //   if ($example3) {
    //     $example3.clear().destroy();
    //   }
    //   example2Func(data2);
    //   example3Func(data3);
    // });

   /* $("#example tbody").off().on('click', 'tr', function () {
      var data = $expampleDT.rows();
      alert('here');
      alert(data);
      alert(data2);
      alert(data3);
      if(data != "" && data != null ){
          //example2Func(data2);
          //example3Func(data3);
          $("#myModal").modal("toggle");
      }
      if ($example2) {
        $example2.clear().destroy();
      }
      if ($example3) {
        $example3.clear().destroy();
      }
      example2Func(data2);
      example3Func(data3);
    });*/

    // Select all tabs
    $(".nav-tabs a").off().on('click', function () {
      $(this).tab("show");
      // $('div.modal-body').animate({ scrollTop: 0 }, "slow");
      setTimeout(function () {
        $($.fn.dataTable.tables(true)).DataTable().columns.adjust();
      }, 200);
    });

    $("[id='form:setFilters']").off().on('click', function (e) { //[id='from:']
      var estatus = $("[id='form:frm_estatus'] :selected").val(),
        fechaInicio = $("[id='form:frm_fechaIni']").val(),
        fechaFin = $("[id='form:frm_fechaFin']").val(),
        por = $("[id='form:frm_tipoDocumento'] :selected").val();

        var filter = inpescIngreso.filter(function (a, b) {
        var convFecha = moment(a.creado, 'DD/MM/YYYY');
        return (!fechaInicio || convFecha >= moment(fechaInicio, 'DD/MM/YYYY')) &&
          (!fechaFin || convFecha <= moment(fechaFin, 'DD/MM/YYYY')) /*&&
          (!estatus || estatus == 0 || a.estatusId == estatus)*/
      });

      /*if (por) {
        switch (por) {
          case '1':
            filter.sort(function (a, e) {
              return e.DocComercial - a.DocComercial;
            });
            break;
          case '2':
            filter.sort(function (a, e) {
              return e.OrdenCompra - a.OrdenCompra;
            });
            break;
          default:
            break;
        }
      }*/
      if ($expampleDT) {
        $expampleDT.clear().destroy();
      }

      $('.exampleContent').removeClass('hidden');
      expampleDTFunc(filter);
      $('div.hidden').removeClass('hidden');
      $('.collapse').collapse('hide');
      events();
      /*setSelect();*/
    });

    $("[id='form:setFiltersEstadoCuenta']").off().on('click', function () {
      if ($tablaEstCuentaDT) {
        $tablaEstCuentaDT.clear().destroy();
      }

      $('div.hidden').removeClass('hidden');
      tablaEstCuenta();
      $('.collapse').collapse('hide');
      events();
      //$('.isResizable').matchHeight();
    });
  };

  events();

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
  
  /*var setSelect = function () {
    $("id=['form:frm_estatus']").val($('.dropdownActive').val()).trigger('change');
  }*/

  $('.dropdownActive').select2({
    theme: "bootstrap",
    allowClear: true,
    placeholder: "Seleccione una opción",
    language: Select2Languaje(),
    multiple: false,
    width: "100%",
  });

  $("div.date.date-start")
    .datepicker({
      format: "dd/mm/yyyy",
      weekStart: 0,
      maxViewMode: 2,
      todayBtn: "linked",
      language: "es",
      orientation: "bottom auto",
      daysOfWeekHighlighted: "0,6",
      autoclose: true,
      todayHighlight: true,
      startDate: moment("01/01/1900", "DD/MM/YYYY").format("DD/MM/YYYY"),
      endDate: moment(new Date(), "DD/MM/YYYY").format("DD/MM/YYYY"),
    }).off().on("changeDate", function (e) {
      var selectDate = moment(e.date).format("DD/MM/YYYY");
      $("div.date.date-end").datepicker("setStartDate", selectDate);
      $("div.date.date-end").datepicker("setDate", selectDate);
    });

  $("div.date.date-end").datepicker({
    format: "dd/mm/yyyy",
    weekStart: 0,
    maxViewMode: 2,
    todayBtn: "linked",
    language: "es",
    orientation: "bottom auto",
    daysOfWeekHighlighted: "0,6",
    autoclose: true,
    todayHighlight: true,
    startDate: "01/01/1900",
  });
});

var initChartDoughnut = function () {
  var model = modelGrafica;
   /*[
    {
      name: 'Dato1',
      value: '125'
    },
    {
      name: 'Dato2',
      value: '125'
    },
    {
      name: 'Dato3',
      value: '125'
    },
    {
      name: 'Dato3',
      value: '568'
    }
  ];*/

  var group = _.groupBy(model, function (a, e) { return a.name; });
  var keys = Object.keys(group);
  var modelDemo = [];

  for (var a = 0; a < keys.length; a++) {
    var current = group[keys[a]];
    modelDemo.push({
      key: keys[a],
      total: current[0].value,
      status: false,
      remaining: current
    });
  }


  var dta = {
    labels: keys,
    data: [
      _.map(modelDemo, function (a, e) { return a.total; })
    ],
    total: modelDemo.reduce(function (a, e, i) { return a + e.total; }, 0),
    modelDemo: modelDemo
  },
    ctx = document.getElementById('chart_puertos_origen'),
    model = charts.doughnut.models.modelDoughnut();

  model = {
    type: {},
    data: {
      datasets: [],
      labels: dta.labels,
      freeData: dta
    },
    plugins: null,
    options: {}
  };

  for (var i = 0; i < dta.data.length; i++) {
    var dataModel = charts.doughnut.models.dataset();
    dataModel = {
      label: 'Saldo',
      labels: dta.labels,
      data: dta.data[i],
      backgroundColor: ["#FF0000" , "#008000"],
      hoverBackgroundColor: ["#FF0000" , "#008000"]
    };
    charts.util.doughnut.setColorsToDataModel(dataModel, dta.data[i].length, false, false, 1);
    model.data.datasets.push(dataModel);
  }

  model.options = {
    title: {
      display: true,
      text: 'Saldo'
    },
    elements: {
      center: {
        text: ''//'100.00%'
      }
    },
    layout: {
      padding: {
        right: 50,
        left: 50
      }
    },
    plugins: {
      labels: false/*[
        {
          render: function (args) {
            //return args.label + '_' + args.percentage + '%';
            return args.percentage + '%';
          },
          fontColor: '#000',
          fontSize: 9,
          position: 'outside',
          precision: 2,
          arc: false
        },
        {
          render: 'label',
          position: 'default',
          fontSize: 8,
          arc: false
        }
      ]*/,
      datalabels: false
    },
    legend: {
      position: 'right',
      display: true,
      onClick: function (legendItem, data) {
        var arc = this.chart.getDatasetMeta(0).data[data.index];
        arc.hidden = !arc.hidden ? true : false;

        var freedata = arc._chart.data.freeData;
        var find = freedata.modelDemo.find(function (e, i) { return e.key === data.text; });
        find.status = arc.hidden;

        var filterFalses = freedata.modelDemo.filter(function (a, e) { return a.status === false; });
        var recalculateTotal = filterFalses.reduce(function (a, e, i) { return a + e.total; }, 0);
        var recalculatePercentage = (recalculateTotal / freedata.total) * 100;
        //this.chart.options.elements.center.text = recalculatePercentage.toFixed(2) + '%';
        this.chart.update();
      }
    },
    tooltips: {
      callbacks: {
        label: function (tooltipItem, data) {
          var dataset = data.datasets[tooltipItem.datasetIndex];
          var index = tooltipItem.index;
          return ['Nombre: ' + dataset.labels[index], 'Monto: ' + formatter.format(dataset.data[index]) ];
        }
      }
    },
    onClick: function (evt, a) {
      var currentClick = Graf_Doughnut_demo.getElementAtEvent(evt)[0];
      if (currentClick) {
        var label = Graf_Doughnut_demo.data.labels[currentClick._index],
          value = Graf_Doughnut_demo.data.datasets[currentClick._datasetIndex].data[currentClick._index],
          getRemaining = Graf_Doughnut_demo.data.freeData.modelDemo.find(function (e, a) { return e.key === label && e.total === value; });
        // initDataTable(label, value, getRemaining);
      }
    }
  };

  var generate = new charts.doughnut.constructor(model).set();
  Graf_Doughnut_demo = new Chart(ctx, generate);
};

// funcion timeline

var completes = document.querySelectorAll(".complete");

function toggleComplete() {
  var lastComplete = completes[completes.length - 1];
  lastComplete.classList.toggle("complete");
}

var tablaEstCuenta = function () {

  if ($tablaEstCuentaDT) {
      //  $tablaEstCuentaDT.$("#idtablaEstCuenta").DataTable.destroy;
  }
  $("#idtablaEstCuenta").DataTable().destroy();
  $tablaEstCuentaDT = $("#idtablaEstCuenta").DataTable({
    paging: true,
    searching: true,
    lengthChange: false,
    data: dataEstCuenta,
    scrollY: true,

    "scrollX": true,
    columns: [
      { data: "tipoDocumento", class: "colorLetra1" },
      { data: "Npedido", class: "colorLetra1" },
      { data: "ordenDeCompra", class: "colorLetra1" },
      { data: "factSap", class: "colorLetra1" },
      { data: "factFiscal", class: "colorLetra1" },
      { data: "xml" },
      { data: "pdf" },
      { data: "facturaRelac", class: "colorLetra1" },
      { data: "UUid", class: "colorLetra1" },
      { data: "fechaFact", class: "colorLetra1" },
      { data: "vencimiento", class: "colorLetra1" },
      { data: "diasMora", class: "colorLetra1" },
      { data: "importe", class: "colorLetra1" },
      { data: "estatus", class: "colorLetra1" },
    ],
    columnDefs: [{
      targets: -1,
      className: "dt-body-right",
    },
    {
      targets: 5,
      render: function (data, type, row, meta) {
        return '<a type="button" target="_blank" class="btn btn-primary btn-xs btn_Action" href="' + row.xml + '">XML</a>';
      }
    },
    {
      targets: 6,
      render: function (data, type, row, meta) {
        return '<a type="button" target="_blank" class="btn btn-danger btn-xs btn_Action" href="' + row.pdf + '">PDF</a>';
      }
    }
    ],
    fnRowCallback: function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
      if (iDisplayIndex % 2 == 0) {
        $("td", nRow).css("background-color", "rgb(0,144,208,.3)");
      } else {
        $("td", nRow).css("background-color", "#ffff");
      }
    },
    initComplete: function (settings, json) {
      setTimeout(function () {
        $($.fn.dataTable.tables(true)).DataTable().columns.adjust();
      }, 200);
    }
    ,
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
    },
  });
};

function filtrarCotizaciones() {
	  if ($tablaCotizaciones) {
		  $tablaCotizaciones.clear().destroy();
	  }  
	  $tablaCotizaciones = $("#tblCot").DataTable({
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
        $tablaCotizaciones;
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

function validarPanelesFilters() {
	var selectClienteCompany = $("[id='form:panelSelectEmpresa']").val();
	if(selectClienteCompany != null) {
	  	$("[id='form:panelEmpresa']").show();
	  	$("[id='form:panelFechaCorte']").show();
	  	$("[id='form:panelBotonGenerar']").show();
	} else {
        showToastr('No tienes asignado este cliente, favor de solicitarlo a Cuentas x Cobrar', 'Aviso', {
            type: typeNotification.info
        });
	}
}

function agregarEstiloSelectEmpresa() {
	$('[id="form:panelSelectEmpresa"]').select2({
        theme: "bootstrap",
        allowClear: true,
        placeholder: "Seleccione una opción",
        language: Select2Languaje(),
        multiple: false,
        width: "100%",
    });
	
	var selectClienteCompany = $("[id='form:panelSelectEmpresa']").val();
	if(selectClienteCompany != null) {
	  	$("[id='form:panelEmpresa']").show();
	  	$("[id='form:panelFechaCorte']").show();
	  	$("[id='form:panelBotonGenerar']").show(); 
	}
}

var formatter = new Intl.NumberFormat('es-MX', {
	  style: 'currency',
	  currency: 'MXN',
});