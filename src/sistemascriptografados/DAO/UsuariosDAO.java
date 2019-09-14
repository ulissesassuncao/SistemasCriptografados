package sistemascriptografados.DAO;

import sistemascriptografados.MODELO.Usuarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Ulisses
 */
public class UsuariosDAO {

    Usuarios usuario;
    private PreparedStatement statement;
    public ConexaoDAO bd;
    private ResultSet resultSet;
    private String sql, msg;

    public UsuariosDAO() throws SQLException, ClassNotFoundException {
        bd = new ConexaoDAO();
    }

    public int inserirNovoUsuario(Usuarios usuario) throws SQLException, ClassNotFoundException {
        sql = "insert into tab_usuarios (ID_USR, NOME_USR,SOBRENOME_USR, EMAIL_USR, LOGIN_USR, SENHA_USR) values (usuariosID.nextval,upper(?),upper(?), upper(?), upper(?), ?)";
        statement = new ConexaoDAO().con.prepareStatement(sql);
        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getSobrenome());
        statement.setString(3, usuario.getEmail());
        statement.setString(4, usuario.getLogin());
        statement.setString(5, usuario.getSenha());
        try {
            statement.execute();
            resultSet = statement.getResultSet();
            JOptionPane.showMessageDialog(null, "Registro inserido com sucesso!");
            return 7;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir registro " + e);
            return 8;
        }
    }

    public int existeLogin(String login) throws SQLException, ClassNotFoundException {
        sql = "Select 1 from tab_usuarios where upper(LOGIN_USR) = upper(?) ";
        statement = new ConexaoDAO().con.prepareStatement(sql);
        statement.setString(1, login);
        statement.executeQuery();
        resultSet = statement.getResultSet();
        while (resultSet.next()) {
            return 0;
        }
        return 3;
    }

    public boolean verificarSenha(String senhaEntrada) throws SQLException, ClassNotFoundException {
        sql = "Select LOGIN_USR from tab_usuarios where SENHA_USR = ? ";
        statement = new ConexaoDAO().con.prepareStatement(sql);
        statement.setString(1, senhaEntrada);
        statement.executeQuery();
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            return true;
        }
        return false;
    }

    public String pegaNome(String login) throws SQLException, ClassNotFoundException {
        sql = "Select nome_usr, sobrenome_usr from tab_usuarios where login_usr = upper(?) ";
        statement = new ConexaoDAO().con.prepareStatement(sql);
        statement.setString(1, login);
        statement.executeQuery();
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            String nomeUsuAtual = rs.getString(1);
            String sobreNomeUsuAtual = rs.getString(2);
            return (nomeUsuAtual + ' ' + sobreNomeUsuAtual);
        }
        return null;
    }
//apagar se nao for usar ===========================================================

    public String carregaUsuario(int id) throws SQLException, ClassNotFoundException {
        sql = "select nome_usr from usuarios where id_usr = ? ";
        statement = new ConexaoDAO().con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeQuery();
        ResultSet rs = statement.getResultSet();
        return rs.toString();
    }

    public int recuperaId(String login) throws SQLException, ClassNotFoundException {
        sql = "select id_usr from tab_usuarios where login_usr = upper(?) ";
        statement = new ConexaoDAO().con.prepareStatement(sql);
        statement.setString(1, login);
        statement.executeQuery();
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 1;
    }

}
