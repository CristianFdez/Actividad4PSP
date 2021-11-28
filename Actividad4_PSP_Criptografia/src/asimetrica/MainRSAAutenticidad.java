package asimetrica;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

// Aqui utilizaremos dos claves: una publica y otra privada. Cifraremos con la
// privada y descifraremos con la publica
public class MainRSAAutenticidad {

	public static void main(String[] args) {
		System.out.println("--- INICIAMOS EL GENERADOR DE CLAVES ASIMÉTRICAS"
				+ " PARA CONFIRMAR AUTENTICIDAD\n");
		try (Scanner sc = new Scanner(System.in)){

			KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
			System.out.println("Se ha obtenido el generador de claves");
			
			//Obtenemos el par de claves (publica y privada)
			KeyPair claves = generador.generateKeyPair();
			System.out.println("Se ha obtenido el par de claves");
			
			Cipher cifrador = Cipher.getInstance("RSA");
			System.out.println("Hemos obtenido el descifrador");
			
			// Lo unico que cambia en todo el codigo es lo del KeyPairGenerator
			// y que ahora utilizamos dos claves diferentes para el cifrador en 
			// en vez de la misma claveSecreta
			cifrador.init(Cipher.ENCRYPT_MODE, claves.getPrivate());
			System.out.println("Hemos configurado el cifrador para usar clave privada");
			System.out.println("Cifrando de esta manera garantizamos AUTENTICIDAD");
			
			byte[] bytesMensajeOriginal;
			byte[] bytesMensajeCifrado = null;
			// inicializamos con un mensaje por si la primera opcion elegida
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
			
					String mensajeOriginal = sc.nextLine();

					System.out.println("Mensaje Original: " + mensajeOriginal);
					
					bytesMensajeOriginal = mensajeOriginal.getBytes();
					bytesMensajeCifrado = cifrador.doFinal(bytesMensajeOriginal);

					mensajeCifrado = new String(bytesMensajeCifrado);

					break;
				case 2:
					System.out.println("--- MOSTRAR FRASE ENCRIPTADA ---");
					System.out.println("Mensaje Cifrado: " + mensajeCifrado);
					break;
				case 3:
					System.out.println("--- DESENCRIPTAR FRASE ---");
					// Lo unico que cambia aqui es que usamos la clave publica
					// en vez de la claveSecreta que usabamos en la encriptacion
					// simetrica
					cifrador.init(Cipher.DECRYPT_MODE, claves.getPublic());
					bytesMensajeDescifrado = cifrador.doFinal(bytesMensajeCifrado);
					System.out.println("Mensaje Descifrado: " 
							+ new String(bytesMensajeDescifrado));

					break;
				case 4:
					System.out.println("--- ENCRIPTAR COCHE ---");
					
					// Encriptamos con la clave privada
					cifrador.init(Cipher.ENCRYPT_MODE, claves.getPrivate());
					System.out.println("Hemos configurado el descifrador con"
							+ " clave privada");
					
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
					SealedObject so = new SealedObject(c1, cifrador);
					
					System.out.println("Coche original: " + c1);
					System.out.println("Coche Cifrado: " + so.toString());
					
					//Desencriptamos con la clave pública
					cifrador.init(Cipher.DECRYPT_MODE, claves.getPublic());
					Coche c2 = (Coche)so.getObject(cifrador);
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
			
			
		} catch (Exception e) {
			System.out.println("Error al cifrar o descifrar el mensaje");
			System.out.println("Excepción de tipo: " + e.getClass().getName());
			System.out.println(e.getMessage());
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
