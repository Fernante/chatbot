import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatBot {

    static Map<String, String> baseConocimiento = new HashMap<>();
    static String respuesta;
    public static void main(String[] args) {

        //Carga el archivo de conocimiento
        cargarConocimiento();



        // Creamos nuevo objeto para la lectura por teclado
        Scanner sc = new Scanner(System.in);
        // Mensaje al usuario
        System.out.println("Hola, soy tu ayudante virtual. Escribe 'salir' para terminar");
        //Variable bandera para el While
        boolean verdadero = true;
        // Bucle para repetir la solicitud de entrada del usuario
        while (verdadero) {

        // Solicitamos entrada al usuario
            System.out.println("\nTú: ");
        // Leemos la que teclea el usuario
            String entrada = sc.nextLine().toLowerCase();
        //Validamos si ha introducido "Salir" para finalizar el bucle
            if (entrada.equals("salir")) {
                System.out.println("Hasta pronto!!!");
                break;
            }

            boolean encontrada = false;

            System.out.println(entrada);
            for(String clave : baseConocimiento.keySet()) {
                if (NLPUtils.contienePalabraClave(entrada, clave)) {
                    encontrada = true;

                    respuesta = baseConocimiento.get(clave);
                    System.out.println(respuesta);
                    break;
                }
            }
            if(!encontrada){
                System.out.println("No conozco la respuesta, ¿Quieres encontrarla?");
                System.out.println("Escribe la respuesta que debería ser");
                String nuevaRespuesta =sc.nextLine();

                baseConocimiento.put(entrada, nuevaRespuesta);
                guardarConocimiento(entrada, nuevaRespuesta);
                System.out.println("¡Graciaas! He aprendido algo nuevo.");
                System.out.println("¡Ahora puedes probar escribiendo de nuevo!");
            }
            System.out.println(respuesta);
            registrarConversacion(entrada,respuesta);

        }

    }

    static void cargarConocimiento(){

        try(BufferedReader lector = new BufferedReader(new FileReader("conocimiento.txt"))){

            String linea;

            // Interaccion con el archivo conocimiento.txt
            while ((linea = lector.readLine())!=null){
                String [] partes = linea.split("=");
                baseConocimiento.put(partes[0],partes[1]);
            }
        }catch (IOException e){
            System.out.println("No se ha podido conectar con el archivo");
        }


    }
    static void guardarConocimiento(String clave, String frase){
        File archivo = new File("conocimiento.txt");
        boolean archivoVacio = archivo.length() == 0;

        try(BufferedWriter escritor = new BufferedWriter(new FileWriter("conocimiento.txt",true))){
           if (!archivoVacio){
               escritor.newLine();
           }
            escritor.write(clave+"="+frase);
        }catch (Exception e){
            System.out.println("No se pudo agregar la frase nueva");
        }


    }

    //Método registrar conversación
    public static void registrarConversacion(String entrada, String respuesta){
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter("registro_conversaciones.txt", true))) {
            escritor.write("Usuario: " + entrada);
            escritor.newLine();
            escritor.write("Bot: " + respuesta);
            escritor.newLine();
            escritor.write("------");
            escritor.newLine();
        }catch (IOException e){
            System.out.println("No se pudo guardar la conversación. ");
        }
    }
}

