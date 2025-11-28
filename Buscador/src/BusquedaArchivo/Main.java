package BusquedaArchivo;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int threadsCount = 0;

        //entrada hilos
        while (threadsCount <= 0){

            try{
                System.out.println("Ingrese la cantidad de hilos a utilizar (debe ser >0):");
                threadsCount = scanner.nextInt();

                if(threadsCount <= 0){

                    System.err.println("La cantidad de hilos debe ser un numero positivo");

                }
            }catch(InputMismatchException e){

                    System.err.println("Ingrese un numero entero valido");
                    scanner.nextLine(); //Limpia buffer

                }

            }

        scanner.nextLine(); //Limpia buffer


        //entrada archivos
        System.out.println("Ingrese el nombre del archivo a buscar:");
        String nombreArchivo = scanner.nextLine();


        System.out.println("Ingrese la carpeta donde desea buscar(o presione ENTER para usar el disco completo):");
        String inputPath = scanner.nextLine();

        File startDirectory;

        if(inputPath.isBlank()){ //busqueda en disco

            startDirectory = new File(System.getProperty("user.home"));
            System.out.println("Buscando en: " + startDirectory.getAbsolutePath());

        }else{  //busqueda en otro directorio
            startDirectory = new File(inputPath);
            //Validacion en clase Busqueda

            if(!startDirectory.exists() || !startDirectory.isDirectory()){

                System.err.println("La carpeta de inicio no existe o no es un directorio.");

            }

        }

        //Inicio de busqueda

        try{

            Busqueda busqueda = new Busqueda (nombreArchivo,startDirectory,threadsCount);
            busqueda.comienzaBusqueda();

        } catch (FileSearcherException e){

            System.err.println("Error en la busqueda" + e.getMessage());
        }finally {

            scanner.close();
        }


        }


        }

