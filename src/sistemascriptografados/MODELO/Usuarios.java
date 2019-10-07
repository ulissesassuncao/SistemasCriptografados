package sistemascriptografados.MODELO;

import java.io.Serializable;
import sistemascriptografados.DAO.UsuariosDAO;
import java.sql.SQLException;

/**
 *
 * @author Ulisses
 */
public class Usuarios implements Serializable {

    private UsuariosDAO usuarioDAO;
    private String nome;
    private String sobrenome;
    private String email;
    private String login;
    private String senha;
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int validaLogin(String login) {
        if (login.length() <= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int validaSenha(String senha) {
        if (senha.length() <= 0) {
            return 2;
        }
        return 0;
    }
    /*Motivos para nao validar
     1 - nome
     2 - sobrenome
     3 - email
     4 - login curto
     5 - login ja existe
     6 - senha*/

    public int validaDadosCadastro(Usuarios usuario) throws SQLException, ClassNotFoundException {
        if (usuario.nome.length() <= 0) {
            return 1;
        } else if (usuario.sobrenome.length() <= 0) {
            return 2;
        } else if (usuario.email.length() <= 0) {
            return 3;
        } else if (usuario.login.length() <= 0) {
            return 4;
        } else if (usuario.senha.length() <= 0) {
            return 6;
        } else {
            return 7;
        }
    }
}
