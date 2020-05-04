var charts = (function () {
    var presents = {
        colors: {
            background: {
                rojo: { key: 1, value: '#ffb0c1' },
                naranja: { key: 2, value: '#ffcf9f' },
                amarillo: { key: 3, value: '#ffe6aa' },
                verde: { key: 4, value: '#a4dfdf' },
                azul: { key: 5, value: '#9ad0f5' },
                gris: { key: 6, value: '#e4e5e7' },
                morado: { key: 7, value: '#9966ff' },
                vardeTernium: { key: 8, value: '#4ee5c6' },
                grisTernium: { key: 9, value: '#d7d7d7' },
                azulTernium: { key: 10, value: '#55a1f3' }
            },
            border: {
                rojo: { key: 1, value: '#ff6384' },
                naranja: { key: 2, value: '#ff9f40' },
                amarillo: { key: 3, value: '#ffcd56' },
                verde: { key: 4, value: '#4bc0c0' },
                azul: { key: 5, value: '#36a2eb' },
                gris: { key: 6, value: '#d6d7da' },
                morado: { key: 7, value: '#9966ff' },
                vardeTernium: { key: 8, value: '#47d4b7' },
                grisTernium: { key: 9, value: '#969595' },
                azulTernium: { key: 10, value: '#4381c3' }
            }
        },
        fill: {
            false: false,
            origin: 'origin',
            start: 'start',
            end: 'end'
        },
        mode: {
            point: 'point',
            nearest: 'nearest',
            index: 'index',
            dataset: 'dataset',
            x: 'x',
            y: 'y'
        },
        titleAlign: {
            left: 'left',
            right: 'right',
            center: 'center'
        }
    };

    var line = function () {
        var line_dataset_model = function () {
            return {
                label: '',
                data: [],
                backgroundColor: presents.colors.background.vardeTernium.value,
                borderColor: presents.colors.border.vardeTernium.value,
                borderWidth: 1,
                hoverBorderColor: presents.colors.border.vardeTernium.value,
                fill: presents.fill.start,
                hoverBackgroundColor: presents.colors.border.vardeTernium.value,
                hoverBorderWidth: 1,
                pointRadius: 3,
                pointHoverBackgroundColor: presents.colors.border.vardeTernium.value,
                pointHoverBorderWidth: 2,
                pointHoverRadius: 4,
                lineTension: 0.4,
                borderDash: [],
                hoverBorderDash: undefined,
                labels: [],
                datalabels: {
                    color: '#36A2EB',
                    backgroundColor: null,
                    borderColor: null,
                    borderRadius: 15,
                    borderWidth: 3,
                    align: 'center',
                    anchor: 'center',
                    labels: {},
                    listeners: {
                        click: null,
                        enter: null,
                        leave: null,
                        color: null
                    },
                    padding: {
                        left: 0,
                        right: 0,
                        top: 0,
                        bottom: 0
                    },
                    rotation: 0,
                    textAlign: 'center',
                    textStrokeColor: '#000',
                    textStrokeWidth: 0,
                    textShadowBlur: 0,
                    textShadowColor: '#000'
                }
            };
        };
        var line_xaxes_model = function () {
            return {
                stacked: false,
                scaleLabel: {
                    display: false,
                    labelString: ''
                },
                ticks: {
                    beginAtZero: true,
                    stepSize: 50,
                    autoSkip: false,
                    min: 0,
                    max: null,
                    maxRotation: 50,
                    minRotation: 0
                },
                gridLines: {
                    display: true,
                    drawBorder: true,
                    drawOnChartArea: false
                }
            };
        };
        var line_yaxes_model = function () {
            return {
                stacked: false,
                position: 'left',
                scaleLabel: {
                    display: false,
                    labelString: ''
                },
                ticks: {
                    beginAtZero: true,
                    stepSize: 50,
                    autoSkip: false,
                    min: 0,
                    max: null,
                    maxRotation: 50,
                    minRotation: 0
                },
                gridLines: {
                    display: true,
                    drawBorder: true,
                    drawOnChartArea: false
                }
            };
        };
        var line_plugin_datalabels_model = function () {
            return {
                align: 'center',
                anchor: 'center',
                backgroundColor: null,
                borderColor: '#fff',
                borderRadius: 15,
                borderWidth: 3,
                clamp: false,
                clip: false,
                color: '#000',
                display: 'auto',
                font: {
                    family: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
                    size: 9,
                    weight: '300'
                },
                lineHeight: 0,
                formatter: null,
                labels: {},
                listeners: {
                    click: null,
                    enter: null,
                    leave: null,
                    color: null
                },
                offset: 4,
                opacity: 1,
                rotation: 0,
                textAlign: 'center',
                textStrokeColor: '#000',
                textStrokeWidth: 0,
                textShadowBlur: 0,
                textShadowColor: '#000'
            };
        };
        var line_default = function () {
            return {
                plugins: null,
                type: 'line',
                data: {
                    labels: [],
                    datasets: [],
                    freeData: {}
                },
                options: {
                    title: {
                        display: false,
                        text: ''
                    },
                    legend: {
                        display: false,
                        position: 'bottom',
                        labels: {
                            fontColor: '#000',
                            usePointStyle: true
                        },
                        onClick: null
                    },
                    tooltips: {
                        mode: presents.mode.index,
                        titleFontSize: 14,
                        titleAlign: presents.titleAlign.center,
                        bodyFontSize: 11,
                        callbacks: {}
                    },
                    animation: {
                        animateScale: true,
                        animateRotate: true
                    },
                    hover: {
                        mode: presents.titleAlign.center
                    },
                    responsive: true,
                    aspectRatio: 2,
                    responsiveAnimationDuration: 0,
                    maintainAspectRatio: false,
                    onResize: null,
                    scales: {
                        xAxes: [],
                        yAxes: []
                    },
                    onClick: {},
                    layout: {
                        padding: {
                            left: 0,
                            right: 0,
                            top: 0,
                            bottom: 0
                        }
                    },
                    plugins: {
                        datalabels: {}
                    }
                }
            };
        };
        var modelLine_Constructor = function (modelLineModel) {
            utilInternal.findSetMaxValue(modelLineModel);
            this.plugins = modelLineModel.plugins;
            this.type = modelLineModel.type;
            this.data = modelLineModel.data;
            this.options = modelLineModel.options;
        };
        modelLine_Constructor.prototype.set = function () {
            var model = {
                plugins: this.plugins,
                type: this.type,
                data: this.data,
                options: this.options
            };
            return utils.mergeRecursive(line_default(), model);
        };
        var modelLine = function () {
            return {
                plugins: {},
                type: {},
                data: {
                    labels: {},
                    datasets: [],
                    freeData: {}
                },
                options: {
                    title: {
                        display: {},
                        text: {}
                    },
                    legend: {
                        display: {},
                        position: {},
                        labels: {
                            fontColor: {},
                            usePointStyle: {}
                        },
                        onClick: {}
                    },
                    tooltips: {
                        mode: {},
                        titleFontSize: {},
                        titleAlign: {},
                        bodyFontSize: {},
                        callbacks: {}
                    },
                    animation: {
                        animateScale: {},
                        animateRotate: {}
                    },
                    hover: {
                        mode: {}
                    },
                    responsive: {},
                    aspectRatio: {},
                    responsiveAnimationDuration: {},
                    maintainAspectRatio: {},
                    onResize: {},
                    scales: {
                        xAxes: [],
                        yAxes: []
                    },
                    onClick: {},
                    layout: {
                        padding: {
                            left: {},
                            right: {},
                            top: {},
                            bottom: {}
                        }
                    },
                    plugins: {
                        datalabels: {}
                    }
                }
            };
        };
        return {
            models: {
                modelLine: modelLine,
                dataset: line_dataset_model,
                xAxes: line_xaxes_model,
                yAxes: line_yaxes_model,
                plugins: {
                    datalabels: line_plugin_datalabels_model
                }
            },
            constructor: modelLine_Constructor
        };
    };

    var doughnut = function () {
        var doughnut_dataset_model = function () {
            return {
                data: [],
                backgroundColor: [],
                borderColor: [],
                borderWidth: 1,
                hoverBorderColor: [],
                hoverBackgroundColor: [],
                hoverBorderWidth: 1,
                borderAlign: 'center',
                label: '',
                labels: [],
                datalabels: {
                    color: '#36A2EB',
                    backgroundColor: null,
                    borderColor: null,
                    borderRadius: 15,
                    borderWidth: 3,
                    align: 'center',
                    anchor: 'center',
                    labels: {},
                    listeners: {
                        click: null,
                        enter: null,
                        leave: null,
                        color: null
                    },
                    padding: {
                        left: 0,
                        right: 0,
                        top: 0,
                        bottom: 0
                    },
                    rotation: 0,
                    textAlign: 'center',
                    textStrokeColor: '#000',
                    textStrokeWidth: 0,
                    textShadowBlur: 0,
                    textShadowColor: '#000'
                }
            };
        };
        var doughnut_plugin_labels_model = function () {
            return {
                render: 'percentage',
                precision: 0,
                showZero: true,
                fontSize: 12,
                fontColor: '#000',
                fontStyle: 'normal',
                fontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
                textShadow: true,
                shadowBlur: 10,
                shadowOffsetX: -5,
                shadowOffsetY: 5,
                shadowColor: 'rgba(255,0,0,0.75)',
                arc: true,
                position: 'default',
                overlap: true,
                showActualPercentages: true,
                outsidePadding: 2,
                textMargin: 2,
                usePointStyle: true,
            };
        };
        var doughnut_plugin_datalabels_model = function () {
            return {
                align: 'center',
                anchor: 'center',
                backgroundColor: null,
                borderColor: '#fff',
                borderRadius: 15,
                borderWidth: 3,
                clamp: false,
                clip: false,
                color: '#000',
                display: 'auto',
                font: {
                    family: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
                    size: 9,
                    weight: '300'
                },
                lineHeight: 0,
                formatter: null,
                labels: {},
                listeners: {
                    click: null,
                    enter: null,
                    leave: null,
                    color: null
                },
                offset: 4,
                opacity: 1,
                rotation: 0,
                textAlign: 'center',
                textStrokeColor: '#000',
                textStrokeWidth: 0,
                textShadowBlur: 0,
                textShadowColor: '#000'
            };
        };
        var doughnut_default = function () {
            return {
                plugins: null,
                type: "doughnut",
                data: {
                    labels: [],
                    datasets: [],
                    freeData: {}
                },
                options: {
                    title: {
                        display: false,
                        text: ''
                    },
                    legend: {
                        display: false,
                        position: 'bottom',
                        labels: {
                            fontColor: '#000',
                            usePointStyle: true
                        },
                        onClick: null
                    },
                    tooltips: {
                        titleFontSize: 14,
                        titleAlign: presents.titleAlign.center,
                        bodyFontSize: 11,
                        callbacks: {}
                    },
                    animation: {
                        animateScale: true,
                        animateRotate: true
                    },
                    responsive: true,
                    aspectRatio: 2,
                    maintainAspectRatio: false,
                    elements: {
                        center: {
                            text: '',
                            color: '',
                            fontStyle: '',
                            sidePadding: 20
                        }
                    },
                    onClick: {},
                    layout: {
                        padding: {
                            left: 0,
                            right: 0,
                            top: 0,
                            bottom: 0
                        }
                    },
                    plugins: {
                        labels: {},
                        datalabels: {}
                    }
                }
            };
        };
        var modelDoughnut_constructor = function (doughnutMOdel) {
            this.plugins = doughnutMOdel.plugins;
            this.type = doughnutMOdel.type;
            this.data = doughnutMOdel.data;
            this.options = doughnutMOdel.options;
        };
        modelDoughnut_constructor.prototype.set = function () {
            var model = {
                plugins: this.plugins,
                type: this.type,
                data: this.data,
                options: this.options
            };
            return utils.mergeRecursive(doughnut_default(), model);
        };
        var modelDoughnut = function () {
            return {
                plugins: {},
                type: {},
                data: {
                    labels: [],
                    datasets: [],
                    freeData: {}
                },
                options: {
                    title: {
                        display: {},
                        text: {}
                    },
                    legend: {
                        display: {},
                        position: {},
                        labels: {
                            fontColor: {},
                            usePointStyle: {}
                        },
                        onClick: {}
                    },
                    tooltips: {
                        titleFontSize: {},
                        titleAlign: {},
                        bodyFontSize: {},
                        callbacks: {}
                    },
                    animation: {
                        animateScale: {},
                        animateRotate: {}
                    },
                    responsive: {},
                    aspectRatio: {},
                    maintainAspectRatio: {},
                    elements: {
                        center: {
                            text: {},
                            color: {},
                            fontStyle: {},
                            sidePadding: {}
                        }
                    },
                    onClick: {},
                    layout: {
                        padding: {
                            left: {},
                            right: {},
                            top: {},
                            bottom: {}
                        }
                    },
                    plugins: {
                        labels: [],
                        datalabels: {}
                    }
                }
            };
        };
        return {
            models: {
                modelDoughnut: modelDoughnut,
                dataset: doughnut_dataset_model,
                plugin_labels: doughnut_plugin_labels_model,
                plugins: {
                    datalabels: doughnut_plugin_datalabels_model
                }
            },
            constructor: modelDoughnut_constructor
        };
    };

    var bar = function () {
        var bar_dataset_model = function () {
            return {
                label: '',
                data: [],
                backgroundColor: [],
                borderColor: [],
                hoverBorderColor: [],
                borderWidth: 1,
                hoverBackgroundColor: [],
                hoverBorderWidth: 1,
                labels: [],
                barThickness: null,
                maxBarThickness: 60,
                minBarLength: 1,
                datalabels: {
                    color: '#36A2EB',
                    backgroundColor: null,
                    borderColor: null,
                    borderRadius: 15,
                    borderWidth: 3,
                    align: 'center',
                    anchor: 'center',
                    labels: {},
                    listeners: {
                        click: null,
                        enter: null,
                        leave: null,
                        color: null
                    },
                    padding: {
                        left: 0,
                        right: 0,
                        top: 0,
                        bottom: 0
                    },
                    rotation: 0,
                    textAlign: 'center',
                    textStrokeColor: '#000',
                    textStrokeWidth: 0,
                    textShadowBlur: 0,
                    textShadowColor: '#000'
                }
            };
        };
        var bar_xaxes_model = function () {
            return {
                display: true,
                stacked: false,
                scaleLabel: {
                    display: false,
                    labelString: ''
                },
                gridLines: {
                    display: true,
                    drawBorder: true,
                    drawOnChartArea: false
                },
                ticks: {
                    beginAtZero: true,
                    stepSize: 50,
                    autoSkip: false,
                    min: 0,
                    max: null,
                    maxRotation: 50,
                    minRotation: 0
                }
            };
        };
        var bar_yaxes_model = function () {
            return {
                display: true,
                stacked: false,
                position: 'left',
                scaleLabel: {
                    display: false,
                    labelString: ''
                },
                ticks: {
                    beginAtZero: true,
                    stepSize: 50,
                    autoSkipK: false,
                    min: 0,
                    max: null,
                    maxRotation: 50,
                    minRotation: 0
                },
                gridLines: {
                    display: true,
                    drawBorder: true,
                    drawOnChartArea: false
                }
            };
        };
        var bar_plugin_datalabels_model = function () {
            return {
                align: 'center',
                anchor: 'center',
                backgroundColor: null,
                borderColor: '#fff',
                borderRadius: 15,
                borderWidth: 3,
                clamp: false,
                clip: false,
                color: '#000',
                display: 'auto',
                font: {
                    family: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
                    size: 9,
                    weight: '300'
                },
                lineHeight: 0,
                formatter: null,
                labels: {},
                listeners: {
                    click: null,
                    enter: null,
                    leave: null,
                    color: null
                },
                offset: 4,
                opacity: 1,
                rotation: 0,
                textAlign: 'center',
                textStrokeColor: '#000',
                textStrokeWidth: 0,
                textShadowBlur: 0,
                textShadowColor: '#000'
            };
        };
        var bar_default = function () {
            return {
                plugins: null,
                type: 'bar',
                data: {
                    labels: [],
                    datasets: [],
                    freeData: {}
                },
                options: {
                    title: {
                        display: false,
                        text: ''
                    },
                    legend: {
                        display: false,
                        position: 'bottom',
                        labels: {
                            fontColor: '#000',
                            usePointStyle: true
                        },
                        //onClick: null
                    },
                    tooltips: {
                        mode: presents.mode.index,
                        titleFontSize: 14,
                        titleAlign: presents.titleAlign.center,
                        bodyFontSize: 11,
                        callbacks: {}
                    },
                    animation: {
                        animateScale: true,
                        animateRotate: true
                    },
                    hover: {
                        mode: presents.titleAlign.center
                    },
                    responsive: true,
                    aspectRatio: 2,
                    maintainAspectRatio: false,
                    scales: {
                        xAxes: [],
                        yAxes: []
                    },
                    onClick: {},
                    layout: {
                        padding: {
                            left: 0,
                            right: 0,
                            top: 0,
                            bottom: 0
                        }
                    },
                    plugins: {
                        labels: {},
                        datalabels: {}
                    }
                }
            };
        };
        var modelBar_constructor = function (modelbarModel) {
            //utilInternal.findSetMaxValue(modelbarModel);
            this.plugins = modelbarModel.plugins;
            this.type = modelbarModel.type;
            this.data = modelbarModel.data;
            this.options = modelbarModel.options;
        };
        modelBar_constructor.prototype.set = function () {
            var model = {
                plugins: this.plugins,
                type: this.type,
                data: this.data,
                options: this.options
            };
            return utils.mergeRecursive(bar_default(), model);
        };
        var modelBar = function () {
            return {
                plugins: {},
                type: {},
                data: {
                    labels: {},
                    datasets: [],
                    freeData: {}
                },
                options: {
                    title: {
                        display: {},
                        text: {}
                    },
                    legend: {
                        display: {},
                        position: {},
                        labels: {
                            fontColor: {},
                            usePointStyle: {}
                        },
                        onClick: {}
                    },
                    tooltips: {
                        mode: {},
                        titleFontSize: {},
                        titleAlign: {},
                        bodyFontSize: {},
                        callbacks: {}
                    },
                    animation: {
                        animateScale: {},
                        animateRotate: {}
                    },
                    hover: {
                        mode: {}
                    },
                    responsive: {},
                    aspectRatio: {},
                    maintainAspectRatio: {},
                    scales: {
                        xAxes: [],
                        yAxes: []
                    },
                    onClick: {},
                    layout: {
                        padding: {
                            left: {},
                            right: {},
                            top: {},
                            bottom: {}
                        }
                    },
                    plugins: {
                        labels: {},
                        datalabels: {}
                    }
                }
            };
        };
        return {
            models: {
                modelBar: modelBar,
                dataset: bar_dataset_model,
                xAxes: bar_xaxes_model,
                yAxes: bar_yaxes_model,
                plugins: {
                    datalabels: bar_plugin_datalabels_model
                }
            },
            constructor: modelBar_constructor
        };
    };

    var polar = function () {
        var polar_dataset_model = function () {
            return {
                data: [],
                backgroundColor: [],
                borderColor: [],
                borderWidth: 1,
                hoverBorderColor: [],
                hoverBackgroundColor: [],
                hoverBorderWidth: 1,
                borderAlign: 'center',
                label: '',
                labels: [],
                datalabels: {
                    color: '#36A2EB',
                    backgroundColor: null,
                    borderColor: null,
                    borderRadius: 15,
                    borderWidth: 3,
                    align: 'center',
                    anchor: 'center',
                    labels: {},
                    listeners: {
                        click: null,
                        enter: null,
                        leave: null,
                        color: null
                    },
                    padding: {
                        left: 0,
                        right: 0,
                        top: 0,
                        bottom: 0
                    },
                    rotation: 0,
                    textAlign: 'center',
                    textStrokeColor: '#000',
                    textStrokeWidth: 0,
                    textShadowBlur: 0,
                    textShadowColor: '#000'
                }
            };
        };
        var polar_plugin_datalabels_model = function () {
            return {
                align: 'center',
                anchor: 'center',
                backgroundColor: null,
                borderColor: '#fff',
                borderRadius: 15,
                borderWidth: 3,
                clamp: false,
                clip: false,
                color: '#000',
                display: 'auto',
                font: {
                    family: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
                    size: 9,
                    weight: '300'
                },
                lineHeight: 0,
                formatter: null,
                labels: {},
                listeners: {
                    click: null,
                    enter: null,
                    leave: null,
                    color: null
                },
                offset: 4,
                opacity: 1,
                rotation: 0,
                textAlign: 'center',
                textStrokeColor: '#000',
                textStrokeWidth: 0,
                textShadowBlur: 0,
                textShadowColor: '#000'
            }
        };
        var polar_default = function () {
            return {
                plugins: null,
                type: "polarArea",
                data: {
                    labels: [],
                    datasets: [],
                    freeData: {}
                },
                options: {
                    startAngle: -0.5 * Math.PI,
                    title: {
                        display: false,
                        text: ''
                    },
                    legend: {
                        display: false,
                        position: 'bottom',
                        labels: {
                            fontColor: '#000',
                            usePointStyle: true
                        },
                        onClick: null
                    },
                    tooltips: {
                        titleFontSize: 14,
                        titleAlign: presents.titleAlign.center,
                        bodyFontSize: 11,
                        callbacks: {}
                    },
                    animation: {
                        animateScale: true,
                        animateRotate: true
                    },
                    scale: {
                        ticks: {
                            beginAtZero: true
                        },
                        reverse: false
                    },
                    responsive: true,
                    aspectRatio: 2,
                    maintainAspectRatio: false,
                    elements: {
                        center: {
                            text: '',
                            color: '',
                            fontStyle: '',
                            sidePadding: 20
                        }
                    },
                    onClick: {},
                    layout: {
                        padding: {
                            left: 0,
                            right: 0,
                            top: 0,
                            bottom: 0
                        }
                    },
                    plugins: {
                        labels: {},
                        datalabels: {}
                    }
                }
            };
        };
        var modelPolar_constructor = function (polarMOdel) {
            this.plugins = polarMOdel.plugins;
            this.type = polarMOdel.type;
            this.data = polarMOdel.data;
            this.options = polarMOdel.options;
        };
        modelPolar_constructor.prototype.set = function () {
            var model = {
                plugins: this.plugins,
                type: this.type,
                data: this.data,
                options: this.options
            };
            return utils.mergeRecursive(polar_default(), model);
        };
        var modelPolar = function () {
            return {
                plugins: {},
                type: {},
                data: {
                    labels: [],
                    datasets: [],
                    freeData: {}
                },
                options: {
                    startAngle: {},
                    title: {
                        display: {},
                        text: {}
                    },
                    legend: {
                        display: {},
                        position: {},
                        labels: {
                            fontColor: {},
                            usePointStyle: {}
                        },
                        onClick: {}
                    },
                    tooltips: {
                        titleFontSize: {},
                        titleAlign: {},
                        bodyFontSize: {},
                        callbacks: {}
                    },
                    animation: {
                        animateScale: {},
                        animateRotate: {}
                    },
                    scale: {
                        ticks: {
                            beginAtZero: {}
                        },
                        reverse: {}
                    },
                    responsive: {},
                    aspectRatio: {},
                    maintainAspectRatio: {},
                    elements: {
                        center: {
                            text: {},
                            color: {},
                            fontStyle: {},
                            sidePadding: {}
                        }
                    },
                    onClick: {},
                    layout: {
                        padding: {
                            left: {},
                            right: {},
                            top: {},
                            bottom: {}
                        }
                    },
                    plugins: {
                        labels: {},
                        datalabels: {}
                    }
                }
            };
        };
        return {
            models: {
                modelPolar: modelPolar,
                dataset: polar_dataset_model,
                plugins: {
                    datalabels: polar_plugin_datalabels_model
                }
            },
            constructor: modelPolar_constructor
        };
    };

    var radar = function () {
        var radar_dataset_model = function () {
            return {
                backgroundColor: [],
                borderCapStyle: 'butt',
                borderColor: [],
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                borderWidth: 3,
                hoverBackgroundColor: [],
                hoverBorderCapStyle: [],
                hoverBorderColor: [],
                hoverBorderDash: [],
                hoverBorderDashOffset: [],
                hoverBorderJoinStyle: [],
                hoverBorderWidth: 1,
                fill: true,
                label: '',
                order: 0,
                lineTension: 0,
                pointBackgroundColor: 'rgba(0, 0, 0, 0.1)',
                pointBorderColor: 'rgba(0, 0, 0, 0.1)',
                pointBorderWidth: 1,
                pointHitRadius: 1,
                pointHoverBackgroundColor: [],
                pointHoverBorderColor: [],
                pointHoverBorderWidth: 1,
                pointHoverRadius: 4,
                pointRadius: 3,
                pointRotation: 0,
                pointStyle: 'circle',
                spanGaps: [],
                labels: []
            };
        };
    };

    var utils = {
        line: {
            setColorsToDataModel: function (dataModel, cantidad, addBorderColor, addHoverBorderColor, opacityBackground) {
                var colorAsignados = [];
                for (var i = 0; i < cantidad; i++) {
                    var color = charts.util.getRandomColorModel();

                    dataModel.backgroundColor = utils.transparentize(color.background.value, opacityBackground);
                    if (addBorderColor) {
                        dataModel.borderColor = color.border.value;
                    }
                    if (addHoverBorderColor) {
                        dataModel.hoverBorderColor.push(color.border.value);
                    }
                    dataModel.hoverBackgroundColor = color.border.value;
                    dataModel.pointHoverBackgroundColor = color.border.value;
                    colorAsignados.push(color);
                }
                return {
                    coloresAsignados: colorAsignados
                };
            }
        },
        doughnut: {
            setColorsToDataModel: function (dataModel, cantidad, addBorderColor, addHoverBorderColor, opacityBackground) {
                var colorAsignados = [];
                for (var i = 0; i < cantidad; i++) {
                    var color = charts.util.getRandomColorModel();

                    dataModel.backgroundColor.push(utils.transparentize(color.background.value, opacityBackground));
                    if (addBorderColor) {
                        dataModel.borderColor.push(color.border.value);
                    }
                    if (addHoverBorderColor) {
                        dataModel.hoverBorderColor.push(color.border.value);
                    }
                    dataModel.hoverBackgroundColor.push(color.border.value);
                    colorAsignados.push(color);
                }
                return {
                    coloresAsignados: colorAsignados
                };
            }
        },
        bar: {
            setColorsToDataModel: function (dataModel, cantidad, addBorderColor, addHoverBorderColor, opacityBackground) {
                var colorAsignados = [];
                for (var i = 0; i < cantidad; i++) {
                    var color = charts.util.getRandomColorModel();

                    dataModel.backgroundColor.push(utils.transparentize(color.background.value, opacityBackground));
                    if (addBorderColor) {
                        dataModel.borderColor.push(color.border.value);
                    }
                    if (addHoverBorderColor) {
                        dataModel.hoverBorderColor.push(color.border.value);
                    }
                    dataModel.hoverBackgroundColor.push(color.border.value);
                    colorAsignados.push(color);
                }
                return {
                    coloresAsignados: colorAsignados
                };
            }
        },
        polar: {
            setColorsToDataModel: function (dataModel, cantidad, addBorderColor, addHoverBorderColor, opacityBackground) {
                var colorAsignados = [];
                for (var i = 0; i < cantidad; i++) {
                    var color = charts.util.getRandomColorModel();

                    dataModel.backgroundColor.push(utils.transparentize(color.background.value, opacityBackground));
                    if (addBorderColor) {
                        dataModel.borderColor.push(color.border.value);
                    }
                    if (addHoverBorderColor) {
                        dataModel.hoverBorderColor.push(color.border.value);
                    }
                    dataModel.hoverBackgroundColor.push(color.border.value);
                    colorAsignados.push(color);
                }
                return {
                    coloresAsignados: colorAsignados
                };
            }
        },
        mergeRecursive: function (obj1, obj2) {
            for (var item in obj2) {
                try {
                    // Property in destination object set; update its value.
                    if (obj2[item].constructor === Object) {
                        obj1[item] = utils.mergeRecursive(obj1[item], obj2[item]);
                    } else {
                        obj1[item] = obj2[item];
                    }
                } catch (e) {
                    // Property in destination object not set; create it and set its value.
                    obj1[item] = obj2[item];
                }
            }
            return obj1;
        },
        randomColor: function (presentsColor) {
            return _.sample(presentsColor);
        },
        getRandomColorModel: function () {
            var background = utils.randomColor(presents.colors.background);
            var keys = Object.keys(presents.colors.border);
            var border;

            for (var e = 0; e < keys.length; e++) {
                var context = presents.colors.border[keys[e]];
                if (context.key === background.key) {
                    border = context;
                    break;
                }
            }

            return {
                background: background,
                border: border
            };
        },
        transparentize: function (color, opacity) {
            var alpha = opacity === undefined ? 0.5 : opacity;
            return Color(color).alpha(alpha).rgbString();
        },
        setBorderMultiColor: function (id_Class_Object, colorArray) {
            var colorsString = '',
                percents = ['25%', '50%', '75%'],
                obj = id_Class_Object;
            for (var i = 0; i < colorArray.length; i++) {
                if (colorArray.length === i + 1) {
                    colorsString += colorArray[i] + ' ' + percents[Math.floor(Math.random() * percents.length)];
                } else {
                    colorsString += colorArray[i] + ' ' + percents[Math.floor(Math.random() * percents.length)] + ',';
                }
            }
            if (typeof id_Class_Object !== 'object') {
                obj = $(idClass);
            }
            $(obj).css({
                'border': '2px solid',
                'border-image': 'linear-gradient(to right, ' + colorsString + ') 5'
            });
        },
        destoy: function (element) {
            var isArray = Array.isArray(element);
            if (isArray) {
                for (var i = 0; i < element.length; i++) {
                    if (element[i]) {
                        element[i].destroy();
                    }
                }
            } else {
                if (element) {
                    element.destroy();
                }
            }
        },
        numberWithCommas: function (x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }
    };

    var utilInternal = {
        findSetMaxValue: function (modelThisConstructor) {
            var maxValue = 0,
                dataArray = [];

            for (var i = 0; i < modelThisConstructor.data.datasets.length; i++) {
                var datasetCurrent = null, maxCurrentValue = 0;
                if (Array.isArray(modelThisConstructor.data.datasets)) {
                    datasetCurrent = modelThisConstructor.data.datasets[i];

                    maxCurrentValue = Math.max.apply(null, datasetCurrent.data);
                    if (maxCurrentValue > maxValue) {
                        maxValue = maxCurrentValue;
                    }
                    dataArray.push(datasetCurrent.data);
                } else {
                    datasetCurrent = modelThisConstructor.data.datasets[i];

                    maxCurrentValue = datasetCurrent.data;
                    if (maxCurrentValue > maxValue) {
                        maxValue = maxCurrentValue;
                    }
                    dataArray.push(datasetCurrent.data);
                }
            }

            var max = utilInternal.stackedMax(dataArray);

            for (var e = 0; e < modelThisConstructor.options.scales.yAxes.length; e++) {
                var axesCurrent = modelThisConstructor.options.scales.yAxes[e];
                if (!axesCurrent.ticks.max) {
                    if (axesCurrent.stacked) {
                        axesCurrent.ticks.max = max;
                    } else {
                        axesCurrent.ticks.max = maxValue;
                    }
                }
            }

            for (var o = 0; o < modelThisConstructor.options.scales.xAxes.length; o++) {
                var xaxesCurrent = modelThisConstructor.options.scales.xAxes[o];
                if (!xaxesCurrent.ticks.max) {
                    if (xaxesCurrent.stacked) {
                        xaxesCurrent.ticks.max = max;
                    } else {
                        xaxesCurrent.ticks.max = maxValue;
                    }
                }
            }
        },
        stackedMax: function (dataArray) {
            var dataArrayResult = [],
                dtA = dataArray,
                count = dtA.length;

            while (count--) {
                var current = dtA[count],
                    currentCount = current.length,
                    Sum = 0;

                do {
                    dataArrayResult.push(current[Sum] || 0);
                    Sum++;
                } while (Sum < currentCount);

                for (var u = 0; u < current.length; u++) {
                    dataArrayResult[u] += (current[u] || 0);
                }
            }
            return (Math.max.apply(null, dataArrayResult || [25]) || 25);
        }
    };

    return {
        presents: presents,
        line: line(),
        doughnut: doughnut(),
        bar: bar(),
        polar: polar(),
        util: utils
    };
})();


//Tomará cualquier cantidad de texto en el donut del tamaño perfecto para el donut.
//Para evitar tocar los bordes, puede establecer un relleno lateral como un porcentaje del diámetro del interior del círculo.
//Si no lo configura, el valor predeterminado será 20. También el color, la fuente y el texto.
//options: {
//    elements: {
//        center: {
//            text: 'Desktop',
//            color: '#36A2EB', //Default black
//            fontStyle: 'Helvetica', //Default Arial
//            sidePadding: 15 //Default 20 (as a percentage)
//            top: null, ((chart.chartArea.top + chart.chartArea.bottom) / 2) + el margen de top que se requiera,
//            font: 30 //Default tamaño de la fuente
//        }
//    }
//}
Chart.pluginService.register({
    beforeDraw: function (chart) {
        if (chart.config.options.elements.center) {
            //Get ctx from string
            var ctx = chart.chart.ctx;

            //Get options from the center object in options
            var centerConfig = chart.config.options.elements.center;
            var fontStyle = centerConfig.fontStyle || 'Arial';
            var txt = centerConfig.text;
            var color = centerConfig.color || '#000';
            var sidePadding = centerConfig.sidePadding || 20;
            var sidePaddingCalculated = (sidePadding / 100) * (chart.innerRadius * 2);
            //Start with a base font of 30px
            ctx.font = (centerConfig.font || "30px") + ' ' + fontStyle;

            //Get the width of the string and also the width of the element minus 10 to give it 5px side padding
            var stringWidth = ctx.measureText(txt).width;
            var elementWidth = (chart.innerRadius * 2) - sidePaddingCalculated;

            // Find out how much the font can grow in width.
            var widthRatio = elementWidth / stringWidth;
            var newFontSize = Math.floor(30 * widthRatio);
            var elementHeight = (chart.innerRadius * 2);

            // Pick a new font size so it will not be larger than the height of label.
            var fontSizeToUse = Math.min(newFontSize, elementHeight);

            //Set font settings to draw it correctly.
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            var centerX = (chart.chartArea.left + chart.chartArea.right) / 2;
            var centerY = 0;

            if (!centerConfig.isMaximizer) {
                centerY = centerConfig.top || (chart.chartArea.top + chart.chartArea.bottom) / 2;
            } else {
                centerY = centerConfig.top || (chart.chartArea.top + chart.chartArea.bottom * 2) / 4;
            }

            if (centerConfig.isSemi) {
                centerY = centerConfig.top || (chart.chartArea.top + chart.chartArea.bottom * 2) / 2.5;
            }

            ctx.font = fontSizeToUse + "px " + fontStyle;
            ctx.fillStyle = color;

            //Draw text in center
            ctx.fillText(txt, centerX, centerY);
        }
    }
});


Chart.plugins.register({
    // plugin implementation
    ChartDataLabels: 'datalabels'
});

Chart.plugins.unregister(ChartDataLabels);