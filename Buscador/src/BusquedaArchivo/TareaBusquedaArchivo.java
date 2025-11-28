package BusquedaArchivo;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;


public class TareaBusquedaArchivo implements Runnable {

    //Atributos
    private final File directorio;
    private final String nombreArchivoBuscado;
    private final AtomicBoolean banderaEncontrada;


    //Constructor
    public TareaBusquedaArchivo(File directorio, String nombreArchivoBuscado, AtomicBoolean banderaEncontrada) {

        this.directorio = directorio;
        this.nombreArchivoBuscado = nombreArchivoBuscado;
        this.banderaEncontrada = banderaEncontrada;

    }

    //metodo - sobre escribe run de Runnable
    @Override
    public void run() { // metodo run

        busqueda(directorio);
    }


    //metodo busqueda
    private void busqueda(File dir) {

        //detiene cuando otro hilo encontro
        if (banderaEncontrada.get()) return;

                //array para los files
        File[] files = null;

        //manejo acceso archivo
        try {
            files = dir.listFiles();

        } catch (SecurityException e) {

            System.err.println("No se pudo acceder a " + dir.getAbsolutePath());
            return;

        }

        if (files == null) return;

        //busqueda
        for (File file : files) {

            if (banderaEncontrada.get()) return;

            if(file.getName().equalsIgnoreCase(nombreArchivoBuscado)){

                System.out.println("Encontrado por un hilo en: " + file.getAbsolutePath());
                banderaEncontrada.set(true);
                return;
            }

            if (file.isDirectory()) {

                busqueda(file);

            }


            }
        }


    }

