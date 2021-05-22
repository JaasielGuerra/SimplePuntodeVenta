# SimplePuntodeVenta
Proyecto control de inventario y venta de servicios

## Historial de cambios

2021.05.12
- Realización de Modelo ER

2021.05.16
- Se realizacin las siguientes vista para los modulos
    - Modulo de ventas
    - Modulo de articulos
    - Modulo de inventario
    - Modulo de compras
    - Modulo de clientes
    - Modulo de proveedores
    - Modulo de servicios
    - Modulo de usuarios
    - Modulo de empresa
- Se agrego el campo __saldo__ en tabla __compra__
- Archigo gitignore

2021.05.17
- Base de datos mapeada
- Capa de persistencia
- Patron DAO para persistencia
- CRUD Marcas
- CRUD Categorias
- CRUD Ubicaciones

2021.05.18
- CRUD Articulos completo
- Trigger para inventario inicial

2021-05.20
- Modulo inventario
- FUnciones MariaDB para calculo de totales
- Procedimiento para buscar movimiento por articulo
- CRUD Clientes
- FUncion mariabDB para calcular deuda cliente

2021.05.21
- Cache del ORM desactivado https://wiki.eclipse.org/EclipseLink/Examples/JPA/Caching
- Realizar ventas al contado
- Patron DAO modificado
- Esquema de base de datos modificado (tabla detalle_venta)
- Trigger para desontar inventario y registrar movimiento al realizar venta
- Trigger para sumar inventario y registrar movimiento al cancelar compra
- Consultar y cancelar ventas
- Realizar ventas al credito

2021.05.22
- Tabla para bitacora de ventas diarias
- Tabla para bitacora de compras diarias
- Trigger para regitrar bitacora de ventas
- Trigger para registrar credito a cuenta cliente en una venta al credito
- Manejo de creditos clientes

