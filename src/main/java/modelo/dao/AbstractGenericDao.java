/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.lang.reflect.ParameterizedType;

/**
 *
 * @author maria
 */
public abstract class AbstractGenericDao<E> implements IGenericDao<E>{
    
    // getClass(): accedemos a la clase de la instancia que extienda esta clase (será DepartamentoSQLServerDao u XSQLServerDao)
    //.getGenericSuperclass(): obtenemos el tipo de la clase madre directa: AbstractGenericDao En el caso de que sea una clase parametrizada (con Generics),devuelve el tipo del parámetro de tipo E: ParameterizedType: https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getGenericSuperclass--
    //.getActualTypeArguments(): devuelve un array de los tipos de los argumentos que se le pasan al tipo parametrizado (podrían ser más de uno, aunque en este caso el tipo es uno solo <E>)
    //finalmente obtenemos el nombre del tipo parametrizado: <E> que será Departamento (o empleado cuando se implemente)
    @SuppressWarnings("unchecked")
	final String entityClass = 
        ((Class<E>) ((ParameterizedType)  getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();

    public String getEntityClass() {
                return entityClass;
    }
    
    
    
}
