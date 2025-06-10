import java.text.Normalizer;

public class NLPUtils {

    public static String limpiarTexto(String texto){
        //Normaliza el texto descomprimiendo los caracteres
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        //Elimina marcas diacríticas
        textoNormalizado = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]","");
        // Convertir a minúsculas y elimina los caracteres que no sean letras
        textoNormalizado = textoNormalizado.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]","");

                return textoNormalizado;
    }

    public static boolean contienePalabraClave(String texto,String clave){
        return limpiarTexto(texto).equals(limpiarTexto(clave));
    }
}
