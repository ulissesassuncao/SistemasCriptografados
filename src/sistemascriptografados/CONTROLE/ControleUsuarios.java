package sistemascriptografados.CONTROLE;

import sistemascriptografados.MODELO.Usuarios;
import sistemascriptografados.DAO.UsuariosDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ulisses
 */
public class ControleUsuarios {

    public Usuarios usuario;
    public UsuariosDAO usuarioDAO;
    private ResultSet resultSet;

    public ControleUsuarios() {
        this.usuario = new Usuarios();
        try {
            this.usuarioDAO = new UsuariosDAO();
        } catch (SQLException ex) {
            Logger.getLogger(ControleUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControleUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int verificarLogin(String loginEntrada) throws SQLException, ClassNotFoundException {
        // UsuariosDAO usuarioDAO = new UsuariosDAO();
        int validador;
        validador = usuario.validaLogin(loginEntrada);
        if (validador == 0) {
            validador = usuarioDAO.existeLogin(loginEntrada);
            if (validador == 0) {
                return 0;
            }
        } else {
            return validador;
        }
        return validador;
    }

    public int verificarSenha(String senhaEntrada) throws SQLException, ClassNotFoundException {
        //  UsuariosDAO usuarioDAO = new UsuariosDAO();
        if (usuario.validaSenha(senhaEntrada) == 0) {
            if (usuarioDAO.verificarSenha(senhaEntrada) == true) {
                return 0;
            }
        }
        return 4;
    }

    public int concederLogin(String login, String Senha) throws SQLException, ClassNotFoundException {
        int veriSenha, veriLogin;
        veriLogin = verificarLogin(login);
        if (veriLogin == 0) {
            veriSenha = verificarSenha(Senha);
            if (veriSenha == 0) {
                return 0;
            } else {
                return veriSenha;
            }
        } else {
            return veriLogin;
        }
    }

    public int cadastrarUsuarios(String nome, String sobrenome, String email, String login, String senha) throws SQLException, ClassNotFoundException {
        Usuarios usuario = new Usuarios();
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        int motivoInvalido;
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setEmail(email);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        motivoInvalido = usuario.validaDadosCadastro(usuario, usuariosDAO);
        if (motivoInvalido == 7) {
            motivoInvalido = usuarioDAO.inserirNovoUsuario(usuario);
            return motivoInvalido;
        }
        return motivoInvalido;
    }

    public String pegaNome(String login) throws SQLException, ClassNotFoundException {
        //   UsuariosDAO usuarioDAO = new UsuariosDAO();
        return usuarioDAO.pegaNome(login);
    }
    
    public int pegaId(String login) throws SQLException, ClassNotFoundException{
        return usuarioDAO.recuperaId(login);
    }

}
