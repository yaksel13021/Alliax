var RESS = (function () {
    var _RESSName = 'RESS';

    function _setCargarDireccionEntrega(cargarDireccionEntrega) {
        _setRESSProperty('cargarDireccionEntrega', cargarDireccionEntrega);
    }

    function _setProductos(productos) {
        _setRESSProperty('productos', productos);
    }

    function _setProductosSeleccionados(productosSeleccionados) {
        _setRESSProperty('productosSeleccionados', productosSeleccionados);
    }

    function _setRESSProperty(key, value) {
        var _RESSJson = sessionStorage.getItem(_RESSName),
            _RESS = {};

        if (_RESSJson === null) {
            _RESS = {
                cargarDireccionEntrega: null,
                productos: null,
                productosSeleccionados: null
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
        setProductosSeleccionados: _setProductosSeleccionados
    };
})();