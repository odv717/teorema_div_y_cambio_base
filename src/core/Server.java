package core;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Server {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", 8080), 0
        );

        server.createContext("/", (exchange) -> {
            String metodo = exchange.getRequestMethod();

            if (metodo.equalsIgnoreCase("GET")) {
                // Mostrar el formulario
                String html = """
                    <!DOCTYPE html>
                    <html lang="es">
                    <head>
                        <meta charset="UTF-8">
                        <title>Calculadora de bases</title>
                    </head>
                    <body>
                        <h1>Datos</h1>

                        <form action="/" method="POST">
                            <label>dividendo</label>
                            <input type="text" name="dividendo" placeholder="29" required>
                            <br><br>

                            <label>divisor</label>
                            <input type="text" name="divisor" placeholder="30" required>
                            <br><br>

                            <button type="submit">Enviar</button>
                        </form>
                    </body>
                    </html>
                    """;

                responder(exchange, 200, html);

            } else if (metodo.equalsIgnoreCase("POST")) {
                // Leer los datos enviados
                String body = new String(
                    exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8
                );

                Map<String, String> datos = leerFormulario(body);

                String dividendo = datos.getOrDefault("dividendo", "");
                String divisor = datos.getOrDefault("divisor", "");


                int [] division = null;
                String base = null;
                int dividendo_i = 0;
                int divisor_i = 0;

                // parse a int
                try{

                    dividendo_i = Integer.parseInt(dividendo);
                    divisor_i =  Integer.parseInt(divisor);

                    if(Math.abs(divisor_i) > 150000 || Math.abs(dividendo_i) > 150000){
                        throw new NumberFormatException();
                    }



                }catch (NumberFormatException e) {
                String html = """
                    <!DOCTYPE html>
                    <html lang="es">
                    <head>
                        <meta charset="UTF-8">
                        <title>Error</title>
                    </head>
                    <body>
                        <h1>Error</h1>
                        <p>Solo se permite ingresar números enteros comprendidos entre %s y %s.</p>
                        <p>Estos límites están establecidos debido al tiempo de procesamiento de las operaciones para números de gran tamaño.</p>
                        <a href="/">Volver a inicio</a>
                    </body>
                    </html>
                    """.formatted("-150000","150000"
                    );

                responder(exchange, 200, html);
                }

                // DIVIDIR

                StringBuilder historialDivision = new StringBuilder();

                long tiempoDiv = System.nanoTime();
                division = TDiv.calcularDivision(dividendo_i, divisor_i,historialDivision);
                tiempoDiv = System.nanoTime() - tiempoDiv;

                //comprobar si la division es valida
                if(division == null){

                    String html = """
                    <!DOCTYPE html>
                    <html lang="es">
                    <head>
                        <meta charset="UTF-8">
                        <title>Error</title>
                    </head>
                    <body>
                        <h1>Error</h1>
                        <p>No se puede dividir entre 0.</p>
                        <a href="/">Volver a inicio</a>
                    </body>
                    </html>
                    """.formatted(
                    );

                    responder(exchange, 200, html);

                    return;
                }

                //calcular cambio de base:
                StringBuilder historialBase = new StringBuilder();
                
                long tiempoBase = System.nanoTime();
                base = Base.calcularBase(dividendo_i, divisor_i,historialBase);
                tiempoBase = System.nanoTime() - tiempoBase;

                // Generar la respuesta valida
                String html = """
                    <!DOCTYPE html>
                    <html lang="es">
                    <head>
                        <meta charset="UTF-8">
                        <title>Resultados</title>
                    </head>
                    <body>
                        <h1>Resultados</h1>
                        <p>Dividendo: %s</p>
                        <p>Divisor: %s</p>
                        <p>Cociente de la división: %s</p>
                        <p>Resto de la división: %s</p>
                        <p>Tiempo invertido en dividir: %s nanosegundos</p>
                        <p>%s</p>
                        <p>Tiempo invertido en calcular la base: %s nanosegundos</p>
                        <h2>Pasos para la división</h2>
                        <p>%s</p>
                        <h2>Pasos para el cambio de base</h2>
                        <p>%s</p>
                        <a href="/">Volver a inicio</a>
                        <br>
                        <hr>
                        <details>

                            <summary>Datos sobre el funcionamiento del sitio</summary>
                            <p>1. Las divisiones están limitadas a 150000 y -150000 debido al tiempo que se tardaría en procesar las divisiones pequeñas como las de divisor 1, lo cual deja colgada la página. Esto es consecuencia directa de seguir estrictamente el teorema de la división (a = bq + r y 0 ≤ r < |b|) implementado con bucles.</p>

                            <p>2. El tiempo registrado para calcular la división y la base también incluye el tiempo necesario para mantener el registro de los pasos que ha realizado. El tiempo real sin este "historial" sería algo menor.</p>

                            <p>3. El cambio de base está limitado al número de caracteres contemplados. Por ejemplo, para base 16 se necesitan los caracteres {0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F}. En este caso he considerado los caracteres de la A a la Z y @,#,$,^, por lo que la base máxima representable es 41. Si queréis obtener ^ probad a dividir 40/41.</p>

                            <p>4. A pesar de que las bases negativas existen matemáticamente, no he logrado implementarlas de forma consistente, así que en esta demo he recortado esa característica. Perdonad las molestias.</p>

                            <p>5. El host web duerme el servicio tras un tiempo de inactividad, así que es posible que tarde en iniciar la primera vez.</p>
                            
                            <p>6. Esta aplicación está implementada en Java mediante HttpServer y contenerizada con Docker. No he hecho uso de ningún framework (ej.: Spring Boot) para mantenerlo lo más ligero y menos dependiente de terceros posible.</p>

                            <hr>

                            </details>
                        <p>Esto es un MVP (mínimo producto viable). Lamento las molestias. </p>
                    </body>
                    </html>
                    """.formatted(
                        escaparHtml(dividendo),
                        escaparHtml(divisor),
                        escaparHtml(division[0] + ""),
                        escaparHtml(division[1] + ""),
                        escaparHtml(String.valueOf(tiempoDiv)),
                        escaparHtml(base),
                        escaparHtml(String.valueOf(tiempoBase)),
                        historialDivision.toString(),
                        historialBase.toString()
                    );

                responder(exchange, 200, html);

            } else {
                exchange.getResponseHeaders().set("Allow", "GET, POST");
                responder(exchange, 405, "Método no permitido");
            }
        });

        server.start();
        System.out.println("Servidor iniciado en http://localhost:8080");
    }

    private static Map<String, String> leerFormulario(String body) {
        Map<String, String> datos = new HashMap<>();

        for (String parametro : body.split("&")) {
            String[] partes = parametro.split("=", 2);

            String clave = URLDecoder.decode(
                partes[0], StandardCharsets.UTF_8
            );

            String valor = partes.length > 1
                ? URLDecoder.decode(partes[1], StandardCharsets.UTF_8)
                : "";

            datos.put(clave, valor);
        }

        return datos;
    }

    private static void responder(HttpExchange exchange, int codigo, String contenido) throws IOException {

        byte[] bytes = contenido.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(codigo, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static String escaparHtml(String texto) {
        return texto.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
