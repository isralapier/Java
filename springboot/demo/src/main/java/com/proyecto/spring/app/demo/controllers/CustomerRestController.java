package com.proyecto.spring.app.demo.controllers;
import com.proyecto.spring.app.demo.domain.Customer;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;



//--------------------------------------------------------------------------------------------------------------------------------------------------------

@RestController
//@RequestMapping("/clientes") // puedo evitar poner todas las rutas "clientes, solo lo que sigue"
public class CustomerRestController {

    private List<Customer> customers = new ArrayList<>(Arrays.asList(

        new Customer(123,"Gerardo Lopez","gerardold","contrasea123"),       //LISTA CLIENTES
        new Customer(456,"Alejandra Garcia","alegarcia","clave456"),
        new Customer(789,"Sanchez","sanc","secret789"),
        new Customer(234,"Carlos Martinez","carlosm","password234")));

 //----------------------------------------------------------------------------------------------------------------------------------------------------       
//METODO GET CUSTOMERS - solicitar y recuperar datos o recursos de un servidor web
// otra alternativa usando @RequestMapping( method = RequestMethod.GET) 
//usamos ResponseEntity para hacer devolver la lista y el status 
    @GetMapping("/clientes") //METODO GET CUSTOMERS                                 //GET CLIENTES
    public ResponseEntity <List<Customer>> getCustomers(){

            //return customers;
        return ResponseEntity.ok(customers); //devuelve la lista y el status 

    }

//------------------------------------------------------------------------------------------------------------------------------------------------

 //METODO GET CLIENTE - lo mismo pero especifico
 //otra alternativa usando @RequestMapping (value = "/username", method = RequestMethod.GET)
    @GetMapping("/clientes/{username}") //mapeo + paso de parametro
    public ResponseEntity<?> getCliente(@PathVariable String username){                     //GET CLIENTE USERNAME

        for(Customer c : customers){

            if(c.getUsername().equalsIgnoreCase(username)){

               return  ResponseEntity.ok(c); //devuelve tipo de dato ResponseEntity (c) 

               //return c;
            }
            


        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con username:" + username);

    }

   // ----------------------------------------------------------------------------------------------------------------------------------------------------------------


//METODO POST CLIENTE - enviar datos desde un cliente(navegador) a un servidor web - crear o actualizar recursos
// otra alternativa usando @RequestMapping( method = RequestMethod.POST) 
@PostMapping("/clientes")
    public  ResponseEntity<Customer> postCliente(@RequestBody Customer customer){

        customers.add(customer);
        return ResponseEntity.ok(customer); //persistencia de datos, retorna lo que se guardo          
                        // se agrega por si hizo alguna modificacion y poder check                          //POST CLIENTE
                        //persistencia es que los datos  por ejemplo  no se borren / postman / memoria RAM
                        //se usa una base de datos real

    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//METODO PUT CLIENTE - actualiza un recurso existente o crea uno nuevo sino existe 
// otra alternativa usando @RequestMapping( method = RequestMethod.PUT) 
@PutMapping("/clientes")
public ResponseEntity <?> putCliente(@RequestBody Customer customer){

for(Customer c : customers){

    if(c.getID() == customer.getID()){

        c.setName(customer.getName());                                                                   //PUT CLIENTE 
        c.setUsername(customer.getUsername());
        c.setPassword(customer.getPassword());

        return ResponseEntity.ok(c);
    }
    
}


      return ResponseEntity.status(HttpStatusCode.NOT_FOUND).body("cliente no encontrado usarname:" + customer.getUsername());

}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//METODO DELETE - se utiliza para eliminar recursos especificos 
// otra alternativa usando @RequestMapping(value = "{id}" method = RequestMethod.DELETE) 
@DeleteMapping("/clientes/{id}") 
public ResponseEntity<?>deleteCliente(@PathVariable Integer id){

 for (Customer c : customers ){

    if(c.getID() == id){                                                                        //METODO DELETE

        customers.remove(c);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente eleminado satisfactoriamente id:" + id + "username" + c.getUsername())
    } //NO_CONTENT no va a mostrar el mensaje personalizado

 }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado" + id);

}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------


//METODO PATCH - realiza modificaciones parciales en un recurso existente, enviando solo los cambios(atributos a actualizar) en lugar del recurso completo
// otra alternativa usando @RequestMapping( method = RequestMethod.PATCH) 
@PatchMapping("/clientes")
public Customer patchCliente(@RequestBody Customer customer){

    for(Customer c : customers){

        if(c.getID() == customer.getID() ){

            if(customer.getName() != null){

                c.setName(customer.getName());                                              //METODO PATCH 

            }

            if(customer.getUsername() != null){

                c.setUsername(customer.getUsername());

            }

            if(customer.getPassword() != null){

                c.setPassword(customer.getPassword());

            }

            return ResponseEntity.ok(c);

        }



    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado:" + customer.getID();



 }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

}
