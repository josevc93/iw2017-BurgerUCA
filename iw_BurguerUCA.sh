#!/bin/bash
echo "Creación de la base de datos."
echo "Introduzca nombre de usuario ya existente en la BD con permisos de administración (root): "
read  usuario
mysql -u $usuario -p<iw_BurguerUCA.sql
echo "Desplegando aplicación..."
sudo mvn tomcat7:run