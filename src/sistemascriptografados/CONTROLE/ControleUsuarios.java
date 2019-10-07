package sistemascriptografados.CONTROLE;

import Cliente.Cliente;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sistemascriptografados.MODELO.Usuarios;
import sistemascriptografados.DAO.UsuariosDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Ulisses
 */
public class ControleUsuarios {

    public Usuarios usuario;
    public UsuariosDAO usuarioDAO;
    private ResultSet resultSet;

    public ControleUsuarios() throws SQLException, ClassNotFoundException {
        this.usuario = new Usuarios();
        this.usuarioDAO = new UsuariosDAO();
    }

    public int verificarLogin(String loginEntrada, String senha) throws SQLException, ClassNotFoundException {
        // UsuariosDAO usuarioDAO = new UsuariosDAO();
        Cliente cliente = new Cliente();
        int validador;
        validador = cliente.enviarLogin(loginEntrada, senha);
        if (validador == 0) {
            return 0;
        } else {
            return validador;
        }
    }

    public int verificarSenha(String senhaEntrada) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException {
        //  UsuariosDAO usuarioDAO = new UsuariosDAO();
        if (usuario.validaSenha(senhaEntrada) == 0) {
            if (usuarioDAO.verificarSenha(senhaEntrada) == true) {
                return 0;
            }
        }
        return 4;
    }

    public int concederLogin(String login, String senha) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException {
        int veriSenha, veriLogin;
        veriLogin = verificarLogin(login, senha);
        if (veriLogin == 0) {
            return 0;
        }
        return 7;
    }

    public String criptografar(String txt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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

    public int cadastrarUsuarios(String nome, String sobrenome, String email, String login, String senha) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Usuarios usuario = new Usuarios();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = md.digest(senha.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02X", 0xFF & b));
        }
        String senhaCriptografada = sb.toString();
        
        int motivoInvalido;
        usuario.setNome(criptografar(nome));
        usuario.setSobrenome(criptografar(sobrenome));
        usuario.setEmail(criptografar(email));
        usuario.setLogin(criptografar(login));
        usuario.setSenha(senhaCriptografada);
        motivoInvalido = usuario.validaDadosCadastro(usuario);
        if (motivoInvalido == 7) {
            Cliente cliente = new Cliente();
            //  cliente
            motivoInvalido = cliente.enviarDadosaoBD(usuario);
            return motivoInvalido;
        }
        return motivoInvalido;
    }

    public String pegaNome(String login) throws SQLException, ClassNotFoundException {
        //   UsuariosDAO usuarioDAO = new UsuariosDAO();
        return usuarioDAO.pegaNome(login);
    }

    public int pegaId(String login) throws SQLException, ClassNotFoundException {
        return usuarioDAO.recuperaId(login);
    }

}
