/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controladores;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

/**
 *
 * @author danie
 */
public class Validador {
    
    public static void validacionTexto(ValidationSupport validationSupport, TextField elemento, int n1, int n2, String mensaje) {
    GraphicValidationDecoration graphicValidationDecoration = new GraphicValidationDecoration();

    // Configurar la validación para el campo de texto
    validationSupport.registerValidator(elemento, false, (control, texto) -> {
        // Convertir el texto a String
        String textoStr = texto != null ? texto.toString().trim() : "";
        
        // Verificar si cumple con la longitud requerida
        if (textoStr.length() >= n1 && textoStr.length() <= n2) {
            return ValidationResult.fromInfo(control, "Campo válido"); // Validación exitosa
        } else if(textoStr.trim().isEmpty()){
            return ValidationResult.fromWarning(control, "El campo no puede estar vacio");
        }else {
            return ValidationResult.fromError(control, mensaje); // Advertencia con el mensaje proporcionado
        }
    });

    // Establecer decoración para validación
    validationSupport.setValidationDecorator(graphicValidationDecoration);
}
    
    
    public static void validacionNumero(ValidationSupport validationSupport, TextField elemento, int n1, int n2, String mensaje) {

    GraphicValidationDecoration graphicValidationDecoration = new GraphicValidationDecoration();
    validationSupport.registerValidator(elemento, false, (control, texto) -> {
        // Verificar si el texto es válido
        String textoStr = texto != null ? texto.toString() : "";
        if (textoStr.trim().isEmpty()) {
            // Texto vacío, mostrar advertencia
            return ValidationResult.fromWarning(control, "El campo no puede estar vacio");
        }
        try {
            int numero = Integer.parseInt(textoStr.trim());
            if (numero >= n1 && numero <= n2) {
                // Número dentro del rango, sin error
                return ValidationResult.fromInfo(control, "Campo válido");
            } else {
                // Número fuera de rango, advertencia
                return ValidationResult.fromError(control, mensaje);
            }
        } catch (NumberFormatException e) {
            // No es un número válido, advertencia
            return ValidationResult.fromError(control, "El campo de contener solamente números");
        }
    });
    validationSupport.setValidationDecorator(graphicValidationDecoration);
}
    
    public static void validacionCampoVacio(ValidationSupport validationSupport, ComboBox elemento, String mensaje) {
        GraphicValidationDecoration graphicValidationDecoration = new GraphicValidationDecoration();
        validationSupport.registerValidator(elemento, false, (control, texto) -> {
            // Convertir el texto a String y verificar si está vacío
            String textoStr = texto != null ? texto.toString().trim() : "";
            if (textoStr.isEmpty()) {
                // Si el campo está vacío, devuelve una advertencia
                return ValidationResult.fromWarning(control, mensaje);
            } else {
                // Si no está vacío, se considera válido
                return ValidationResult.fromInfo(control, "Campo válido");
            }
        });
        validationSupport.setValidationDecorator(graphicValidationDecoration);
    }
    
}
