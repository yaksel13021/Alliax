/**
 * Renderiza y carga el template de mustache
 * @param {any} mustacheTemplateId el id donde se encuentra el template de mustache
 * @param {any} mustacheTemplateTargetId el id donde se cargara el template renderizado
 * @param {any} data la informacion que se usara para renderizar el template
 */
function loadMustacheTemplate(mustacheTemplateId, mustacheTemplateTargetId, data) {
    var mustacheTemplate = $('#' + mustacheTemplateId).html();

    Mustache.parse(mustacheTemplate); // optional, speeds up future uses

    var rendered = Mustache.render(mustacheTemplate, data);

    $('#' + mustacheTemplateTargetId).html(rendered);
}

function renderMustacheTemplate(mustacheTemplateId, data) {
    var mustacheTemplate = $('#' + mustacheTemplateId).html();

    Mustache.parse(mustacheTemplate); // optional, speeds up future uses

    return Mustache.render(mustacheTemplate, data);
}

function Select2Languaje() {
    return {
        noResults: function () { return "No hay resultados" },
        searching: function () { return "Buscando..." },
        errorLoading: function () { return "No se pudieron cargar los resultados" },
        inputTooShort: function (e) {
            t = e.minimum - e.input.length, n = "Por favor, introduzca " + t + " car"; return t == 1 ? n += "ácter" : n += "acteres", n
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

$.fn.setupValidate = function (rules, messages) {
    if (this[0].tagName !== 'FORM') {
        return;
    }

    return $(this).validate({
        lang: 'es',
        errorElement: "span",
        rules: rules,
        messages: messages,
        ignore: 'input[type=hidden]',
        errorPlacement: function (error, element) {
            $(element)
                .closest("form")
                .find("label[for='" + element.attr("id") + "']")
                .append(error);

            //add error class for select2
            $(element)
                .parent()
                .find('span.select2')
                .first()
                .addClass('validate-error');
        },
        highlight: function (element) {
            jQuery(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            jQuery(element).closest('.form-group').removeClass('has-error');
        }
    });
};

$.fn.serializeForm = function () {
    if (this[0].tagName !== 'FORM') {
        return null;
    }

    var o = {};

    var a = this.serializeArray();

    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (o[this.name] !== null) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
            }
            o[this.name].push(getValue(this, o));
        } else { // new property
            o[this.name] = getValue(this, o);
        }
    });

    var $radios = $('input[type=radio]', this);
    $.each($radios, function () {
        if (!o.hasOwnProperty(this.name)) {
            o[this.name] = null;
        }
    });

    var $checks = $('input[type=checkbox]', this);
    $.each($checks, function () {
        if (!o.hasOwnProperty(this.name)) {
            o[this.name] = false;
        } else {
            o[this.name] = true;
        }
    });

    function getValue(prop, obj) {
        var customType = $('[name=' + prop.name + ']').attr('data-custom-type') || null;

        if (customType !== null) {
            switch (customType) {
                case 'asp-net-json-date':
                    var mFormat = $('input[name=' + prop.name + ']').attr('data-custom-moment-format') || 'DD/MM/YYYY';

                    var date = moment(prop.value, mFormat);
                    return date.isValid() ? date.toDate() : null;
                case 'select2-multi':
                    if (obj[prop.name]) {
                        return prop.value;
                    } else {
                        var values = [];
                        values.push(prop.value);
                        return values;
                    }
                //case 'icheck':
                //    return $('input[name=' + prop.name + ']')
                //    return '';
            }
        }

        return prop.value || null;
    }

    //! summernotes needs manual retrieving
    $(this).find('div[data-custom-type="summernote"]').each(function (i, item) {
        var $summernote = $(item);

        o[$(item).attr('name')] = $summernote.summernote('code');
    });

    return o;
};

function regexValidate(regexp, cadena) {
    if (regexp.test(cadena)) {
        return true;
    } else {
        return false;
    }
}

function validNumber(e) {
    if (!/^[0-9]*$/.test(e.currentTarget.value)) {
        var indexLetter = /[a-zA-ZÀ-ÿ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?\s]*$/.exec(e.currentTarget.value).index;
        e.currentTarget.value = e.currentTarget.value.substring(indexLetter, 0);
    }
}

function confirmModal(titulo, mensaje, cancelarTexto, confirmarTexto, show, callback) {
    loadMustacheTemplate('modalConfirm_template', 'modalConfirm_target', {
        titulo: titulo,
        mensaje: mensaje,
        cancelarTexto: cancelarTexto,
        confirmarTexto: confirmarTexto
    });
    if (show) {
        $('#modelConfirm').modal({
            backdrop: 'static',
            keyboard: false,
            show: true
        });
    }
    $('[data-identifier="confirmBtnModel"]').off().on('click', function (e) {
        e.preventDefault();
        setTimeout(() => {
            $('#modelConfirm').modal('hide');
        }, 0);

        if (callback) {
            var rtn = e.currentTarget.dataset.btn == 'false' ? false : true;
            callback(rtn);
        }
    });
}

function mensajes() {
    return {
        Generico01: 'Favor de contactarnos Correo servicioaclientes@rotoplas.com o al Teléfono 800 506 3000'
    }
}