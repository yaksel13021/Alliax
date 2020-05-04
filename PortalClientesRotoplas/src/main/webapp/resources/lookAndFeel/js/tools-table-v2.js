HTMLElement.prototype.rssDataTable = function (options) {
    var dt = ddt.init.call(this, options);
    return dt;
};

$.fn.rssDataTable = function (options) {
    var dt = ddt.init.call(this, options);
    return dt;
};

//$.fn.dataTable.ext.errMode = 'none';

var ddt = (function () {
    var $dt = null;

    var dt_init = function (options) {
        if (!options) {
            console.log('no options contain');
            return;
        }

        if (this.tagName !== 'TABLE') {
            console.error('is not a table');
            return;
        }

        $dt = $(this);
        $dt.options = {
            paging: options.paging || false,
            searching: options.searching || false,
            info: options.info || false,
            fixedHeader: options.fixedHeader || false,
            scrollY: options.scrollY || false,
            scrollX: options.scrollX || false,
            scrollCollapse: options.scrollCollapse || false,
            responsive: options.responsive ? DT.responsive.render : false,
            order: options.order || [1, "asc"],
            data: options.data || null,
            defaultEDDataSrc: options.defaultEDDataSrc || null,
            rowCallback: options.rowCallback || null,
            drawCallback: options.drawCallback || null,
            initComplete: options.initComplete || null,
            footerCallback: options.footerCallback || null,
            lengthMenu: options.lengthMenu || null,
            actions: options.actions || null,
            checkbox: options.checkbox || null,
            free: options.free || null,
            doom: {
                header: options.doom ? options.doom.header || DT.doom.header : DT.doom.header,
                body: options.doom ? options.doom.body || DT.doom.body : DT.doom.body,
                footer: options.doom ? options.doom.footer || DT.doom.footer : DT.doom.footer
            }
        };
        $dt.columns = DTcolumns.init($dt);
        $dt.buttons = DTbuttons.init($dt);
        DT.events.error_dt();

        var dtOptionsFull = DT.options();

        if ($dt.options.lengthMenu) {
            dtOptionsFull.lengthMenu = true;
            dtOptionsFull.lengthMenu = $dt.options.lengthMenu;
        }
        else {
            dtOptionsFull.lengthChange = false;
        }

        DT.events.resize();
        return $dt.DataTable(dtOptionsFull);
    };

    var DTcolumns = {
        init: function (dt) {
            var $this = dt.find('thead > tr > th'),
                columnas = [],
                columnasDefs = [],
                target = 0;

            $.each($this, function (i, item) {
                var $item = $(item),
                    columns = DTcolumns.getColumn($item),
                    columnsDef = DTcolumns.getColumnDef($item, target, dt);

                if (columnsDef) {
                    columnas.push(columns);
                    columnasDefs.push(columnsDef);
                    target++;
                }
            });

            return {
                columns: columnas,
                columnDef: columnasDefs
            };
        },
        getColumn: function ($item) {
            return { data: $item.attr('data-dt-columnName') || null };
        },
        getColumnDef: function ($item, index, dt) {
            var render = $item.attr('data-dt-render') || null;

            if (render === "complexHeader") {
                var renderType = $item.attr('data-dt-complexHeaderType') || null;
                if (renderType === "colspan") {
                    return null;
                }

                if (renderType === "rowspan") {
                    return null;
                }
            }

            var columnaDef = {
                targets: index,
                searcheable: $item.is('[data-dt-searcheable]') || false,
                orderable: $item.is('[data-dt-orderable]') || false,
                width: $item.attr('data-columnWidth') || '100%',
                className: $item.attr('data-dt-className') || ''
            };

            if (render === "fecha") {
                columnaDef.mFormat = $item.attr('data-dt-mFormat') || 'DD/MM/YYYY';
                columnaDef.render = util.renderDate;
            }
            if (render === "controlRender") {
                columnaDef.className += " details-control control";
                columnaDef.data = null;
                columnaDef.render = function (data, type, row, meta) {
                    return '<div style="text-align: center;font-size: 15px;color: #1f2c38; width: 100%;"><i class="fa fa-plus iconDT" aria-hidden="true"></i></div>';
                };
                columnaDef.defaultContent = '';
            }
            if (render === "checkbox") {
                columnaDef.searcheable = false;
                columnaDef.orderable = false;
                columnaDef.render = dt.options.checkbox;
            }
            if (render === "OcultarColumnaRender") {
                columnaDef.searcheable = false;
                columnaDef.orderable = false;
                columnaDef.visible = false;
            }
            if (render === "actions") {
                columnaDef.searcheable = false;
                columnaDef.orderable = false;
                columnaDef.render = dt.options.actions;
            }
            if (render === "free") {
                columnaDef.searcheable = false;
                columnaDef.orderable = false;
                columnaDef.render = dt.options.free;
            }
            if (render === "index") {
                columnaDef.searcheable = false;
                columnaDef.orderable = true;
                columnaDef.render = function (data, type, row, meta) {
                    return '<div class="numberIndex">' + (index + 1) + '</div>';
                };
            }
            if (render === "empty") {
                columnaDef.searcheable = false;
                columnaDef.orderable = false;
                columnaDef.render = function (data, type, row, meta) {
                    return '';
                };
            }
            return columnaDef;
        }
    };

    var DTbuttons = {
        init: function (dt) {
            var topButtons = [],
                pdf = DTbuttons.pdf(dt),
                excel = DTbuttons.excel(dt),
                print = DTbuttons.print(dt);

            if (pdf) { topButtons.push(pdf); }
            if (excel) { topButtons.push(excel); }
            if (print) { topButtons.push(print); }

            return topButtons;
        },
        pdf: function (dt) {
            if (dt.is('[data-dt-pdfBtn]')) {
                return {
                    extend: 'pdf',
                    text: 'PDF',
                    className: 'btn btn-sm btn-warning buttonsDownloadDT',
                    //orientation: 'landscape',
                    pageSize: "LEGAL",
                    footer: true,
                    exportOptions: {
                        columns: [$(dt).attr('data-dt-pdfBtnColumns') ? $(dt).attr('data-dt-pdfBtnColumns') : ':visible'],
                        modifier: {
                            search: 'applied',
                            order: 'applied',
                            page: 'current'
                        }
                    }
                };
            }
        },
        excel: function (dt) {
            if (dt.is('[data-dt-excelBtn]')) {
                return {
                    extend: 'excel',
                    text: 'EXCEL',
                    className: 'btn btn-sm btn-warning buttonsDownloadDT',
                    footer: true,
                    exportOptions: {
                        columns: [$(dt).attr('data-dt-excelBtnColumns') ? $(dt).attr('data-dt-excelBtnColumns') : ':visible'],
                        modifier: {
                            search: 'applied',
                            order: 'applied',
                            page: 'current'
                        }
                    }
                };
            }
        },
        print: function (dt) {
            if (dt.is('[data-dt-printBtn]')) {
                return {
                    extend: 'print',
                    text: 'IMPRIMIR',
                    className: 'btn btn-sm btn-warning buttonsDownloadDT',
                    footer: true,
                    //orientation: 'landscape',
                    pageSize: "LEGAL",
                    exportOptions: {
                        columns: [$(dt).attr('data-dt-printBtnColumns') ? $(dt).attr('data-dt-printBtnColumns') : ':visible'],
                        modifier: {
                            search: 'applied',
                            order: 'applied',
                            page: 'current'
                        }
                    }
                };
            }
        }
    };

    var DT = {
        doom: {
            header: '<"row"<"col-xl-3 col-lg-3 col-md-3 col-sm-12 col-xs-12"l><"col-xl-9 col-lg-9 col-md-9 col-sm-12 col-xs-12"f>>',
            body: '<"row"<"col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12"tr>>',
            footer: '<"row"<"col-3"i>><"row"<"col-xl-6 col-lg-6 col-md-6 col-sm-12 col-xs-12"B><"col-xl-6 col-lg-6 col-md-6 col-sm-12 col-xs-12"p>>'
        },
        options: function () {
            return {
                dom: $dt.options.doom.header + $dt.options.doom.body + $dt.options.doom.footer,
                language: {
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "Ningún dato disponible en esta tabla",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    }
                },
                lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
                processing: true,
                serverSide: false,
                deferLoading: 0,
                deferRender: true,
                buttons: $dt.buttons,
                paging: $dt.options.paging,
                searching: $dt.options.searching,
                info: $dt.options.info,
                fixedHeader: $dt.options.fixedHeader,
                scrollY: $dt.options.scrollY,
                scrollX: $dt.options.scrollX,
                scrollCollapse: $dt.options.scrollCollapse,
                responsive: $dt.options.responsive,
                //retrieve: true,
                //contentPadding: "5px",
                columns: $dt.columns.columns,
                columnDefs: $dt.columns.columnDef,
                destroy: true,
                order: $dt.options.order,
                autoWidth: false,
                data: $dt.options.data,
                rowCallback: function (row, data) {
                    var api = this.api();
                    if ($dt.options.rowCallback) {
                        $dt.options.rowCallback(row, data, api);
                    }
                },
                drawCallback: function (settings) {
                    var api = this.api();
                    if ($dt.options.defaultEDDataSrc) {
                        $dt.find('thead tr th:last-child').addClass('td-actions');
                        $dt.find('tbody tr td:last-child').addClass('td-actions');
                    }
                    if ($dt.options.drawCallback) {
                        $dt.options.drawCallback(api);
                    }
                    //DT.events.responsive_display(api);
                    //$dt.css('width', '');
                },
                initComplete: function (settings, json) {
                    var api = this.api();
                    if ($dt.options.initComplete) {
                        $dt.options.initComplete(settings, json, api);
                    }
                    setTimeout(function () {
                        DT.events.columns_adjust_recalc();
                    }, 200);
                },
                footerCallback: function (row, data, start, end, display) {
                    if ($dt.options.footerCallback) {
                        $dt.options.footerCallback(row, data, start, end, display, this.api());
                    }
                }
            };
        },
        events: {
            error_dt: function () {
                $dt.on('error.dt', function (e, settings, techNote, message) {
                    console.log('An error has been reported by DataTables: ', message);
                }).DataTable();
            },
            responsive_display: function (api) {
                $dt.off().on('responsive-display', function (e, datatable, row, showHide, update) {
                    if ($dt.options.drawCallback) {
                        $dt.options.drawCallback(api);
                    }
                });
            },
            columns_adjust_recalc: function () {
                $($.fn.dataTable.tables(true)).DataTable().columns.adjust().responsive.recalc().draw();
            },
            columns_adjust: function () {
                $($.fn.dataTable.tables(true)).DataTable().columns.adjust();
            },
            columns_recalc: function () {
                $($.fn.dataTable.tables(true)).DataTable().responsive.recalc().draw();
            },
            resize: function () {
                $(window).off().on('resize', function () {
                    setTimeout(function () {
                        $($.fn.dataTable.tables(true)).DataTable().columns.adjust().responsive.recalc().draw();
                    }, 100);
                });
            }
        },
        responsive: {
            render: {
                details: {
                    type: 'column',
                    renderer: function (api, rowIdx, columns) {
                        var data = DT.responsive.formatMoreInfo(api, rowIdx, columns);
                        return data ? data : false;
                    },
                    display: function (row, update, render) {
                        if (update) {
                            if ($(row.node()).hasClass('parent')) {
                                row.child(render(), 'child').show();
                                $('div.slider', row.child()).slideDown();
                                return true;
                            }
                        } else {
                            if (!row.child.isShown()) {
                                row.child(render(), 'child').show();
                                $(row.node()).addClass('parent shown');
                                $('div.slider', row.child()).slideDown();
                                $(row.node()).find('i.iconDT, svg.iconDT').removeClass('fa-plus').addClass('fa-minus');
                                return true;
                            } else {
                                $('div.slider', row.child()).slideUp(function () {
                                    row.child(false);
                                    $(row.node()).removeClass('parent shown');
                                });
                                $(row.node()).find('i.iconDT, svg.iconDT').removeClass('fa-minus').addClass('fa-plus');
                                return false;
                            }
                        }

                    }
                }
            },
            formatMoreInfo: function (api, rowIdx, columns) {
                var data = $.map(columns, function (col, i) {
                    return col.hidden ? '<tr data-dt-row="' + col.rowIndex + '" data-dt-column="' + col.columnIndex + '">' +
                        '<td><strong>' + col.title + ': </strong></td>' +
                        '<td>' + col.data || '' + '</td>' +
                        '</tr>' : '';
                }).join('');
                return data ? '<div class="slider"><table>' + data + '</table></div>' : undefined;
            }
        }
    };

    var util = {
        renderDate: function (data, type, row, meta) {
            var mFormat = meta.settings.aoColumns[meta.col].mFormat;
            var date = moment(data);
            return date.isValid() ? date.format(mFormat) : '';
        },
        Checkbox: {
            render: function (idClass, id, titleTooltip, text) {
                var divContainer = $('<div class="checkboxRender" style="width: 100%; text-align: center;"></div>');
                var checkbox = $('<input id="" type="checkbox" class="cell-checkbox" />').addClass(idClass).attr('data-id', id).attr('title', titleTooltip).attr('data-toggle', 'tooltip');
                divContainer.html(checkbox).append(text);
                return divContainer[0].outerHTML;
            }
        },
        actions: {
            iconButton: function (idClass, iconClass, id, titleTooltip) {
                var $a = util.actions.aElement(idClass, iconClass, id, titleTooltip);
                return $a[0].outerHTML;
            },
            buttonIcon: function (idClass, iconClass, id, titleTooltip, text) {
                var $a = util.actions.aElement(idClass, iconClass, id, titleTooltip).append(text);
                return $a[0].outerHTML;
            },
            aElement: function (idClass, iconClass, id, titleTooltip) {
                var $a = util.actions.icon(iconClass);
                $a.addClass(idClass).attr('data-id', id).attr('title', titleTooltip).attr('data-toggle', 'tooltip');
                return $a;
            },
            icon: function (iconClass) {
                var $i = $('<i></i>', {
                    class: iconClass
                });

                var $a = $('<a></a>');
                $a.append($i);

                return $a;
            }
        },
    };

    return {
        init: dt_init,
        util: {
            renderDate: util.renderDate,
            Checkbox: util.Checkbox,
            actions: util.actions,
            columns_adjust_recalc: DT.events.columns_adjust_recalc,
            columns_adjust: DT.events.columns_adjust,
            columns_recalc: DT.events.columns_recalc
        }
    };
})();