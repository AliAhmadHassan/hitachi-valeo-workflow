package br.com.valeoservice.model.dao.mysql.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.valeoservice.model.Grupo;
import br.com.valeoservice.model.Usuario;
import br.com.valeoservice.model.dao.IUsuariosDAO;

public class UsuariosDAO extends BaseDAO implements IUsuariosDAO {

	public Usuario getConsultaLogin(String email, String password) {
		Usuario retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT idUsuario, idGrupo, eMail, login, nome, senha, dtCriacao, dtModificacao "
					+ "from workflow_usuario "
					+ "where eMail = ? and senha = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, email);
				stmt.setString(2, password);
				try {
					ResultSet rs = stmt.executeQuery();
					try {
						if (rs.next()) {
							retorno = montaEntidade(rs);
						}
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();

			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver não encontrado!");
		} catch (SQLException ex) {
			System.out.println("Erro de banco: " + ex.getMessage());
		}
		return retorno;
	}

	private Usuario montaEntidade(ResultSet rs) throws SQLException {
		Usuario retorno = new Usuario();
		retorno.setIdUsuario(rs.getLong("idUsuario"));
		retorno.setIdGrupo(rs.getLong("idGrupo"));
		retorno.seteMail(rs.getString("eMail"));
		retorno.setLogin(rs.getString("login"));
		retorno.setNome(rs.getString("nome"));
		retorno.setSenha(rs.getString("senha"));
		retorno.setDtCriacao(rs.getDate("dtCriacao")); 
		retorno.setDtModificacao(rs.getDate("dtModificacao"));
		return retorno;
	}

	@Override
	public Usuario getById(Long ID) {
		Usuario retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT idUsuario, idGrupo, eMail, login, nome, senha, dtCriacao, dtModificacao "
					+ "from workflow_usuario " + "where idUsuario = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, ID);

				try {
					ResultSet rs = stmt.executeQuery();
					try {
						if (rs.next()) {
							retorno = montaEntidade(rs);
						}
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();

			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver não encontrado!");
		} catch (SQLException ex) {
			System.out.println("Erro de banco: " + ex.getMessage());
		}
		return retorno;
	}

	@Override
	public List<Usuario> getAll() {
		List<Usuario> retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT idUsuario, idGrupo, eMail, login, nome, senha, dtCriacao, dtModificacao "
					+ "from workflow_usuario ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				try {
					ResultSet rs = stmt.executeQuery();
					try {
						while (rs.next()) {
							if(retorno == null)
								retorno = new ArrayList<Usuario>();
							
							retorno.add(montaEntidade(rs));
						}
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();

			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver não encontrado!");
		} catch (SQLException ex) {
			System.out.println("Erro de banco: " + ex.getMessage());
		}
		return retorno;
	}

	@Override
	public Usuario save(Usuario type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "insert into workflow_usuario(idGrupo, eMail, login, nome, senha, dtCriacao, dtModificacao) "
					+ "values (?, ?, ?, ?, ?, ?, ?); ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdGrupo());
				stmt.setString(2, type.geteMail());
				stmt.setString(3, type.getLogin());
				stmt.setString(4, type.getNome());
				stmt.setString(5, type.getSenha());
				stmt.setDate(6, type.getDtCriacao());
				stmt.setDate(7, type.getDtModificacao());
				
				try {
					stmt.execute();
					stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");
					stmt.clearParameters();
					
					ResultSet rs = stmt.executeQuery();
					
					try {
						if (rs.next()) {
							type.setIdUsuario(rs.getLong("LAST_INSERT_ID()"));
						}
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();

			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver não encontrado!");
		} catch (SQLException ex) {
			System.out.println("Erro de banco: " + ex.getMessage());
		}
		return type;
	}

	@Override
	public Usuario update(Usuario type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "update workflow_usuario set idGrupo = ?, eMail = ?, login = ?, nome = ?, senha = ?, dtCriacao = ?, dtModificacao = ? "
					+ "where idUsuario = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdGrupo());
				stmt.setString(2, type.geteMail());
				stmt.setString(3, type.getLogin());
				stmt.setString(4, type.getNome());
				stmt.setString(5, type.getSenha());
				stmt.setDate(6, type.getDtCriacao());
				stmt.setDate(7, type.getDtModificacao());
				stmt.setLong(8, type.getIdUsuario());
				
				try {
					stmt.execute();
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();

			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver não encontrado!");
		} catch (SQLException ex) {
			System.out.println("Erro de banco: " + ex.getMessage());
		}
		return type;
	}

	@Override
	public void delete(Usuario type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "delete workflow_usuario  "
					+ "where idusuario = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql); 
				stmt.setLong(1, type.getIdUsuario());
				try {
					stmt.execute();
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();

			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver não encontrado!");
		} catch (SQLException ex) {
			System.out.println("Erro de banco: " + ex.getMessage());
		}

	}
}