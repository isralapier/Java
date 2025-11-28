package BusquedaArchivo;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class Busqueda {


    //atributos
    private final String nombreArchivo;
    private final File directorioInicial;
    private final AtomicBoolean banderaEncontrada = new AtomicBoolean(false);
    private final int threads; // cantidad hilos a utilizar

    //constructor
    public Busqueda(String nombreArchivo, File directorioInicial,int threads){

        this.nombreArchivo = nombreArchivo;
        this.directorioInicial = directorioInicial;
        this.threads = threads;
    }

    //metodo busqueda
    public void comienzaBusqueda() throws FileSearcherException{

        //validacion

        if (!directorioInicial.exists() || !directorioInicial.isDirectory()){

            throw new FileSearcherException("La carpeta inicial '" + directorioInicial.getAbsolutePath() + "' no existe o no es un directorio valido");

        }


        // Inicio de ExecutorService - pool

        System.out.println("Iniciando busqueda con " + threads + " hilos");

        ExecutorService executor = Executors.newFixedThreadPool(threads); //creo que pileta de threads (8)

        File[] dirs;

        //Manejo Excepcion
        try{

            dirs = directorioInicial.listFiles();

        }catch (SecurityException e){

            throw new FileSearcherException("Error de permisos al listar el directorio " + directorioInicial.getAbsolutePath());

        }

        //carpeta vacia
        if (dirs == null || dirs.length == 0){
            System.out.println("La carpeta esta vacia o no existe");
            executor.shutdownNow(); //Apago de inmediato sino hay nada para hacer.
            return;
        }


        List<File> subDirsToSearch = new ArrayList<>();

        //envia tareas
        for(File dir : dirs ){

            if(banderaEncontrada.get()) break;

            if(dir.getName().equalsIgnoreCase(nombreArchivo)){

                System.out.println("Encontrado en el directorio inicial: " + dir.getAbsolutePath());
                banderaEncontrada.set(true);
                break;
            }

            //Sino es el archivo y es directorio se agrega a busqueda concurrente
            if (dir.isDirectory()){

                subDirsToSearch.add(dir);

            }

        }


        //cierre encontro archivo en raiz, se detiene y retorna

        if(banderaEncontrada.get()) {

            executor.shutdown();
            return;
        }


        //enviar tareas solo para los subdirectorios
        for(File dir : subDirsToSearch){

             executor.submit(new TareaBusquedaArchivo(dir,nombreArchivo,banderaEncontrada));

        }

        executor.shutdown();

        try{

            System.out.println("Suspendiendo hilo prinpicipal hasta hijos terminen");
            boolean finished = executor.awaitTermination(1, TimeUnit.DAYS);

            if(!finished){

                System.err.println("Advertencia: busqueda excedido de tiempo");

            }

        } catch (InterruptedException e){

            Thread.currentThread().interrupt(); //restaura estado interrupcion
            throw new FileSearcherException("El proceso de espera fue interrumpido");

        }

        if(!banderaEncontrada.get()){

                System.out.println("El archivo no fue encontrado en la busqueda");

        }

    }

}






