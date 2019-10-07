/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import sistemascriptografados.MODELO.Usuarios;

/**
 *
 * @author Ulisses
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public int enviarDadosaoBD(Usuarios usuario) {
        try {
            // TODO code application logic here
            Socket socket = new Socket("10.0.0.112", 5555);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            //String msg = "Hello";
            output.writeObject(usuario);
            output.flush();
            String login = usuario.getLogin();
            System.out.println("Usuario " + login + " enviado ao servidor");

            String msg = input.readUTF();
            System.out.println("Resposta do servidor: " + msg);
            System.out.println("=======================================================\n");
            input.close();
            output.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return 8;
        }
        return 7;
    }

    public int enviarLogin(String login, String senha){
        try {
            // TODO code application logic here
            String loginCripto = criptografar(login);
            Socket socket = new Socket("10.0.0.112", 5555);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            //String msg = "Hello";
            output.writeUTF(loginCripto + "§" + senha);
            output.flush();
            System.out.println("Requisição para acesso do login Criptografado: " + loginCripto);

            String msg = input.readUTF();
            System.out.println("Resposta do servidor: " + msg);
            input.close();
            output.close();
            socket.close();
            if (msg.equals(login + " validado com sucesso")) {
            System.out.println("TUDO CERTO!!!");
                return 0;
            } else {
                
            System.out.println("TUDO ERRADO!!!");
            System.out.println("=======================================================\n");
                return 8;
            }
        } catch (IOException ex) {

            return 8;
        }
    }
    
     public String criptografar(String txt) {
        int contador, tamanho, codigoASCII;
        String senhaCriptografada = "";
        tamanho = txt.length();
       // txt = txt.toUpperCase();
        contador = 0;

        while (contador < tamanho) {
            codigoASCII = txt.charAt(contador) + 130;
            senhaCriptografada = senhaCriptografada + (char) codigoASCII;
            contador++;
        }

        return senhaCriptografada;
    }

    public static void main(String[] args) {

    }

}
