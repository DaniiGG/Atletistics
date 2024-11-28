/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Database;

import java.sql.SQLException;

/**
 *
 * @author danie
 */
@FunctionalInterface
public interface SQLFunction<T, R> {
    R apply(T t) throws SQLException;
}
