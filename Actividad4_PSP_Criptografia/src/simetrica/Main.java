package simetrica;

import java.security.GeneralSecurityException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;


public class Main {

	public static void main (String args[]) {
		
		try (Scanner sc = new Scanner(System.in)){
			KeyGenerator generador = KeyGenerator.getInstance("AES");
			
			//Generamos la clave simetrica
			SecretKey claveSecreta = generador.generateKey();
			//Si se hiciera otra vez, obtendria otra clave DIFERENTE


			//Objeto que nos permitira encriptar o desencriptar a partir de una
			//clave
			Cipher cifrador = Cipher.getInstance("AES");
			
			//Ahora el cifrador lo configuramos para que use la clave simetrica
			//para encriptar
			cifrador.init(Cipher.ENCRYPT_MODE, claveSecreta);			
			
			byte[] bytesMensajeOriginal;
			byte[] bytesMensajeCifrado = null;
			// Declaramos aqui la variable para que sea accesible desde todos los
			// casos
			// La inicializamos con un mensaje por si la primera opcion elegida
			// fuese la 2, ya que aun no existiria ningun mensaje encriptado
			String mensajeCifrado = "Aun no hay un mensaje cifrado";
			byte[] bytesMensajeDescifrado;
			
			boolean continuar = true;
			
			do {
				menu();
				int opcion;
				opcion = Integer.parseInt(sc.nextLine()); 
				
				switch (opcion) {
				case 1: 
					System.out.println("--- ENCRIPTAR FRASE ---");
					System.out.println("Introduce la frase que deseas encriptar:");
					// el mensajeOriginal lo creamos dentro porque no hace falta
					// acceder a el despues y porque asi nos guardara un mensaje
					// diferente cada vez
					String mensajeOriginal = sc.nextLine();

					System.out.println("Mensaje Original: " + mensajeOriginal);
					
					bytesMensajeOriginal = mensajeOriginal.getBytes();
					bytesMensajeCifrado = cifrador.doFinal(bytesMensajeOriginal);
					// mensajeCifrado lo creamos como variable fuera ya que luego
					// accederemos a ella desde diferentes sitios

					mensajeCifrado = new String(bytesMensajeCifrado);

					break;
				case 2:
					System.out.println("--- MOSTRAR FRASE ENCRIPTADA ---");
					System.out.println("Mensaje Cifrado: " + mensajeCifrado);
					break;
				case 3:
					System.out.println("--- DESENCRIPTAR FRASE ---");
					//Ahora el cifrador lo configuramos para que use la clave simetrica
					//para desencriptar
					cifrador.init(Cipher.DECRYPT_MODE, claveSecreta);
					bytesMensajeDescifrado = cifrador.doFinal(bytesMensajeCifrado);
					System.out.println("Mensaje Descifrado: " + new String(bytesMensajeDescifrado));

					break;
				case 4:
					System.out.println("--- ENCRIPTAR COCHE ---");
					
					Cipher descifrador = Cipher.getInstance("AES");
					System.out.println("Hemos obtenido el descifrador");
					
					descifrador.init(Cipher.ENCRYPT_MODE, claveSecreta);
					System.out.println("Hemos configurado el descifrador");
					
					// Primero pedimos los datos del coche al usuario
					Coche c1 = new Coche();
					System.out.println("Introduce la matricula:");
					c1.setMatricula(sc.nextLine());
					System.out.println("Introduce la marca:");
					c1.setMarca(sc.nextLine());
					System.out.println("Introduce el modelo:");
					c1.setModelo(sc.nextLine());
					System.out.println("Introduce el precio:");
					c1.setPrecio(Double.parseDouble(sc.nextLine()));
					
					
					// Encriptamos el objeto 
					//Usamos este objeto para ayudarnos a cifrar el objeto entero
					//Persona debe de ser Serializable, OJO arroja IOException
					SealedObject so = new SealedObject(c1, descifrador);
					
					System.out.println("Coche original: " + c1);
					System.out.println("Coche Cifrado: " + so.toString());
					
					//Configuramos para descifrar, OJO arroja IOException
					descifrador.init(Cipher.DECRYPT_MODE, claveSecreta);
					Coche c2 = (Coche)so.getObject(descifrador);
					System.out.println("Coche descifrado: " + c2);
					break;
				case 5: 
					System.out.println("--- SALIMOS DEL PROGRAMA -- Bye! :)");
					continuar = false;
					break;
				default:
					System.out.println("Opción incorrecta");
				}
				
				
			} while (continuar);
			
	
		//Simplificamos las excepciones capturando GeneralSecurityException
		} catch (Exception gse) {
			System.out.println("Algo ha fallado.." + gse.getMessage());
			gse.printStackTrace();
		}
	}
	
	public static void menu() {
		System.out.println("\nOpciones a elegir:");
		
		System.out.println("\n1 - Encriptar frase");
		System.out.println("2 - Mostrar frase encriptada");
		System.out.println("3 - Desencriptar frase");
		System.out.println("4 - Encriptar coche");
		System.out.println("5 - Salir de la aplicación");
		
		System.out.println("\nIntroduce el número de la opción elegida: ");
	}
	
}
