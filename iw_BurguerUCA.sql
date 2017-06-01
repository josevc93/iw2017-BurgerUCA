-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-06-2017 a las 15:19:16
-- Versión del servidor: 10.1.13-MariaDB
-- Versión de PHP: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `burgueruca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customer`
--

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grid_ticket`
--

CREATE TABLE `grid_ticket` (
  `id` bigint(20) NOT NULL,
  `cantidad` bigint(20) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` bigint(20) DEFAULT NULL,
  `tipo` bit(1) NOT NULL,
  `family` varchar(255) DEFAULT NULL,
  `iva` varchar(255) DEFAULT NULL,
  `orderp_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menu`
--

CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `menu_image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orderp`
--

CREATE TABLE `orderp` (
  `id` bigint(20) NOT NULL,
  `coste` bigint(20) DEFAULT NULL,
  `num_mesa` bigint(20) DEFAULT NULL,
  `state` bit(1) NOT NULL,
  `take_away` bit(1) NOT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `worker_id` bigint(20) DEFAULT NULL,
  `zona_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `order_line_menu`
--

CREATE TABLE `order_line_menu` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` double NOT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `order_line_product`
--

CREATE TABLE `order_line_product` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` double NOT NULL,
  `orderp_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `family` varchar(255) DEFAULT NULL,
  `iva` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `product_image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product_menu`
--

CREATE TABLE `product_menu` (
  `id` bigint(20) NOT NULL,
  `cantidad` bigint(20) DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `restaurant`
--

CREATE TABLE `restaurant` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `restaurant_users_list`
--

CREATE TABLE `restaurant_users_list` (
  `restaurant_id` bigint(20) NOT NULL,
  `users_list_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `restaurant_zonas_list`
--

CREATE TABLE `restaurant_zonas_list` (
  `restaurant_id` bigint(20) NOT NULL,
  `zonas_list_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `telephone_number` varchar(255) DEFAULT NULL,
  `url_avatar` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `restaurant_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zona`
--

CREATE TABLE `zona` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `num_mesas` bigint(20) DEFAULT NULL,
  `state` bit(1) NOT NULL,
  `restaurante_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Indices de la tabla `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `grid_ticket`
--
ALTER TABLE `grid_ticket`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsmvl3rr864ugbek7mirwf3q49` (`orderp_id`);

--
-- Indices de la tabla `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `orderp`
--
ALTER TABLE `orderp`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1gikhq7bgp0lucbe8irioneg` (`customer_id`),
  ADD KEY `FKq7t62b7mar8m9yi09ngrb3fj` (`worker_id`),
  ADD KEY `FK5uibbu19oudope5xyaq9okolk` (`zona_id`);

--
-- Indices de la tabla `order_line_menu`
--
ALTER TABLE `order_line_menu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9j7i2n44bd11qhl91gkyax05` (`menu_id`),
  ADD KEY `FKrfw5ahpo054j823bdq3r9kmr5` (`order_id`);

--
-- Indices de la tabla `order_line_product`
--
ALTER TABLE `order_line_product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtqi1ad44s5tudoru7hq02dl5r` (`orderp_id`),
  ADD KEY `FK1eu7eebpyh13sskqpnec6poyi` (`product_id`);

--
-- Indices de la tabla `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `product_menu`
--
ALTER TABLE `product_menu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK775t72dv4m7jfhimcpxx96dpm` (`menu_id`),
  ADD KEY `FK7gnnslccghvbla35wnxu7c2wq` (`product_id`);

--
-- Indices de la tabla `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `restaurant_users_list`
--
ALTER TABLE `restaurant_users_list`
  ADD PRIMARY KEY (`restaurant_id`,`users_list_id`),
  ADD UNIQUE KEY `UK_ab7cg3g1cnc7ue4soi53aom7` (`users_list_id`);

--
-- Indices de la tabla `restaurant_zonas_list`
--
ALTER TABLE `restaurant_zonas_list`
  ADD PRIMARY KEY (`restaurant_id`,`zonas_list_id`),
  ADD UNIQUE KEY `UK_2eae4eyv4rnopmt5y9p6m54kg` (`zonas_list_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK62ym98by1jxbb27knhj3gse7v` (`restaurant_id`);

--
-- Indices de la tabla `zona`
--
ALTER TABLE `zona`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq7u3kv78oe6thgrlj1d6uwa4y` (`restaurante_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `customer`
--
ALTER TABLE `customer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `grid_ticket`
--
ALTER TABLE `grid_ticket`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `menu`
--
ALTER TABLE `menu`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `orderp`
--
ALTER TABLE `orderp`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `order_line_menu`
--
ALTER TABLE `order_line_menu`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `order_line_product`
--
ALTER TABLE `order_line_product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `product_menu`
--
ALTER TABLE `product_menu`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `zona`
--
ALTER TABLE `zona`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `grid_ticket`
--
ALTER TABLE `grid_ticket`
  ADD CONSTRAINT `FKsmvl3rr864ugbek7mirwf3q49` FOREIGN KEY (`orderp_id`) REFERENCES `orderp` (`id`);

--
-- Filtros para la tabla `orderp`
--
ALTER TABLE `orderp`
  ADD CONSTRAINT `FK1gikhq7bgp0lucbe8irioneg` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `FK5uibbu19oudope5xyaq9okolk` FOREIGN KEY (`zona_id`) REFERENCES `zona` (`id`),
  ADD CONSTRAINT `FKq7t62b7mar8m9yi09ngrb3fj` FOREIGN KEY (`worker_id`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `order_line_menu`
--
ALTER TABLE `order_line_menu`
  ADD CONSTRAINT `FK9j7i2n44bd11qhl91gkyax05` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  ADD CONSTRAINT `FKrfw5ahpo054j823bdq3r9kmr5` FOREIGN KEY (`order_id`) REFERENCES `orderp` (`id`);

--
-- Filtros para la tabla `order_line_product`
--
ALTER TABLE `order_line_product`
  ADD CONSTRAINT `FK1eu7eebpyh13sskqpnec6poyi` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKtqi1ad44s5tudoru7hq02dl5r` FOREIGN KEY (`orderp_id`) REFERENCES `orderp` (`id`);

--
-- Filtros para la tabla `product_menu`
--
ALTER TABLE `product_menu`
  ADD CONSTRAINT `FK775t72dv4m7jfhimcpxx96dpm` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  ADD CONSTRAINT `FK7gnnslccghvbla35wnxu7c2wq` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Filtros para la tabla `restaurant_users_list`
--
ALTER TABLE `restaurant_users_list`
  ADD CONSTRAINT `FKbs2feep4ff3g7p55b9fvrapxi` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
  ADD CONSTRAINT `FKen3lrm3f8x9lq5pksp43sp1dj` FOREIGN KEY (`users_list_id`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `restaurant_zonas_list`
--
ALTER TABLE `restaurant_zonas_list`
  ADD CONSTRAINT `FK10mku5epapladptr99lnswkvd` FOREIGN KEY (`zonas_list_id`) REFERENCES `zona` (`id`),
  ADD CONSTRAINT `FKgcaoc6scy9cb9uw1rtp6npgwk` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`);

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK62ym98by1jxbb27knhj3gse7v` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`);

--
-- Filtros para la tabla `zona`
--
ALTER TABLE `zona`
  ADD CONSTRAINT `FKq7u3kv78oe6thgrlj1d6uwa4y` FOREIGN KEY (`restaurante_id`) REFERENCES `restaurant` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
