var RESS = (function () {
    var _RESSName = 'RESS';

    function _setCargarDireccionEntrega(cargarDireccionEntrega) {
        _setRESSProperty('cargarDireccionEntrega', cargarDireccionEntrega);
    }

    function _setSeleccionMateriales(seleccionMateriales) {
        _setRESSProperty('seleccionMateriales', seleccionMateriales);
    }

    function _setProductos(productos) {
        _setRESSProperty('productos', productos);
    }

    function _setProductosSeleccionados(productosSeleccionados) {
        _setRESSProperty('productosSeleccionados', productosSeleccionados);
    }

    function _setcargarCFDI(cargarCFDI) {
        _setRESSProperty('cargarCFDI', cargarCFDI);
    }

    function _setCFDISeleccionado(CFDISeleccionado) {
        _setRESSProperty('CFDISeleccionado', CFDISeleccionado);
    }

    function _setcargarMetodosPago(cargarMetodosPago) {
        _setRESSProperty('cargarMetodosPago', cargarMetodosPago);
    }

    function _setMetodoPagoSeleccionado(metodoPagoSeleccionado) {
        _setRESSProperty('metodoPagoSeleccionado', metodoPagoSeleccionado);
    }

    function _setUsoMaterialSeleccionado(usoMaterialSeleccionado) {
        _setRESSProperty('usoMaterialSeleccionado', usoMaterialSeleccionado);
    }

    function _setComentarios(comentarios) {
        _setRESSProperty('comentarios', comentarios);
    }

    function _setCotizaciones(seleccionCotizaciones) {
        _setRESSProperty('seleccionCotizaciones', seleccionCotizaciones);
    }
    function _setRESSProperty(key, value) {
        var _RESSJson = sessionStorage.getItem(_RESSName),
            _RESS = {};

        if (_RESSJson === null) {
            _RESS = {
                cargarDireccionEntrega: null,
                productos: null,
                productosSeleccionados: null,
                cargarCFDI: null,
                CFDISeleccionado: null,
                cargarMetodosPago: null,
                metodoPagoSeleccionado: null,
                seleccionMateriales: null,
                usoMaterialSeleccionado: null,
                comentarios: null,
                seleccionCotizaciones: null
            };
        } else {
            _RESS = JSON.parse(_RESSJson);
        }

        _RESS[key] = value;
        sessionStorage.setItem(_RESSName, JSON.stringify(_RESS));
    }

    function _removeRESSObjext() {
        sessionStorage.removeItem(_RESSName);
    }

    function _getRESSObject() {
        var _RESSJson = sessionStorage.getItem(_RESSName);
        if (_RESSJson === null) {
            return null;
        } else {
            return JSON.parse(_RESSJson);
        }
    }

    return {
        getRESSObject: _getRESSObject,
        removeRESSObjext: _removeRESSObjext,
        setCargarDireccionEntrega: _setCargarDireccionEntrega,
        setProductos: _setProductos,
        setProductosSeleccionados: _setProductosSeleccionados,
        setcargarCFDI: _setcargarCFDI,
        setCFDISeleccionado: _setCFDISeleccionado,
        setcargarMetodosPago: _setcargarMetodosPago,
        setMetodoPagoSeleccionado: _setMetodoPagoSeleccionado,
        setSeleccionMateriales: _setSeleccionMateriales,
        setUsoMaterialSeleccionado: _setUsoMaterialSeleccionado,
        setComentarios: _setComentarios,
        setCotizaciones: _setCotizaciones
    };
})();