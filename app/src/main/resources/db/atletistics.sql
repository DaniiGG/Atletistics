-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 19-11-2024 a las 09:13:09
-- Versión del servidor: 5.7.35-0ubuntu0.18.04.2
-- Versión de PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `atletistics`
--

CREATE DATABASE atletistics;

USE atletistics;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `atletas`
--

CREATE TABLE `atletas` (
  `dni` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `edad` int(11) NOT NULL,
  `club` varchar(100) DEFAULT NULL,
  `activo` tinyint(1) NOT NULL,
  `foto` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `atletas`
--

INSERT INTO `atletas` (`dni`, `nombre`, `apellidos`, `edad`, `club`, `activo`, `foto`) VALUES
('12345678A', 'Juan', 'Pérez', 30, 'Real Madrid', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1yFhJ0XR6CMs10RlOAIRU56Lg4saat5lNAQ&s'),
('13579246C', 'Carlos', 'López', 22, 'Club Deportivo C', 0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1yFhJ0XR6CMs10RlOAIRU56Lg4saat5lNAQ&s'),
('24681357D', 'Ana', 'Martínez', 27, 'Club Deportivo D', 1, 'https://img.freepik.com/vector-gratis/ilustracion-dibujos-animados-corriendo-dibujados-mano_23-2150665601.jpg?semt=ais_hybrid'),
('87654321B', 'María', 'Gómez', 30, 'Club Deportivo B', 1, 'https://img.freepik.com/vector-gratis/ilustracion-dibujos-animados-corriendo-dibujados-mano_23-2150665601.jpg?semt=ais_hybrid');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `competiciones`
--

CREATE TABLE `competiciones` (
  `id_competicion` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `lugar` varchar(100) NOT NULL,
  `fecha` date NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `premio` decimal(10,2) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `competiciones`
--

INSERT INTO `competiciones` (`id_competicion`, `nombre`, `lugar`, `fecha`, `tipo`, `premio`, `imagen`) VALUES
(1, 'Maratón Ciudad', 'Ciudad Real', '2024-10-20', 'Carrera', '1000.00', 'https://www.maratonesespana.es/wp-content/uploads/2024/10/quijote-maraton-2024-cartel.jpg'),
(2, 'Copa Nacional', 'Valencia', '2024-05-26', 'Competencia', '1500.00', 'https://www.feddi.org/wp-content/uploads/2024/04/cartel-atletismo-e1713443338419.png'),
(3, 'Triatlón Playa', 'Torremolinos', '2024-10-16', 'Triatlón', '2000.00', 'https://triatlonandalucia.org/wp-content/uploads/2022/12/cartel-triatlontorremolinos22.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resultados`
--

CREATE TABLE `resultados` (
  `id_resultado` bigint(20) NOT NULL,
  `dni_atleta` varchar(20) NOT NULL,
  `id_competicion` bigint(20) NOT NULL,
  `marca` varchar(20) NOT NULL,
  `puesto` int(11) NOT NULL,
  `dorsal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `resultados`
--

INSERT INTO `resultados` (`id_resultado`, `dni_atleta`, `id_competicion`, `marca`, `puesto`, `dorsal`) VALUES
(1, '12345678A', 1, '2h 15m', 1, 101),
(2, '87654321B', 1, '2h 30m', 3, 102),
(3, '13579246C', 2, '1h 10m', 2, 201),
(4, '24681357D', 3, '30m', 1, 301);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `atletas`
--
ALTER TABLE `atletas`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `competiciones`
--
ALTER TABLE `competiciones`
  ADD PRIMARY KEY (`id_competicion`);

--
-- Indices de la tabla `resultados`
--
ALTER TABLE `resultados`
  ADD PRIMARY KEY (`id_resultado`),
  ADD KEY `dni_atleta` (`dni_atleta`),
  ADD KEY `id_competicion` (`id_competicion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `competiciones`
--
ALTER TABLE `competiciones`
  MODIFY `id_competicion` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `resultados`
--
ALTER TABLE `resultados`
  MODIFY `id_resultado` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `resultados`
--
ALTER TABLE `resultados`
  ADD CONSTRAINT `resultados_ibfk_1` FOREIGN KEY (`dni_atleta`) REFERENCES `atletas` (`dni`),
  ADD CONSTRAINT `resultados_ibfk_2` FOREIGN KEY (`id_competicion`) REFERENCES `competiciones` (`id_competicion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
