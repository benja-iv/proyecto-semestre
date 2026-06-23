CREATE DATABASE IF NOT EXISTS db_carrito
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_catalogo
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_clientes
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_despacho
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_inventario
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_notificaciones
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_pagos
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_pedidos
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_promociones
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS db_reportes
 CHARACTER SET utf8mb4
 COLLATE utf8mb4_unicode_ci;

GRANT ALL PRIVILEGES ON db_carrito.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_catalogo.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_clientes.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_despacho.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_inventario.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_notificaciones.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_pagos.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_pedidos.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_promociones.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON db_reportes.* TO 'root'@'%';

FLUSH PRIVILEGES;