package br.com.valeoservice.model.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.valeoservice.model.*;
import br.com.valeoservice.model.dao.IGruposDAO;

public class GruposDAO extends BaseDAO implements IGruposDAO {

	@Override
	public Grupo getById(Long ID) {
		Grupo retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idGrupo, "
					+ "		nome, "
					+ "		adm, "
					+ "		dtCriacao, "
					+ "		dtModificacao "
					+ "from workflow_grupo "
					+ "where idGrupo = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setFloat(1, ID); // o primeiro deve ser substituído pelo valor 0
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

	private Grupo montaEntidade(ResultSet rs) throws SQLException {
		Grupo retorno = new Grupo();
		retorno.setIdGrupo(rs.getLong("idGrupo"));
		retorno.setNome(rs.getString("nome"));
		retorno.setAdm(rs.getBoolean("adm"));
		retorno.setDtCriacao(rs.getDate("dtCriacao"));
		retorno.setDtModificacao(rs.getDate("dtModificacao"));
		return retorno;
	}

	@Override
	public List<Grupo> getAll() {
		List<Grupo> retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idGrupo, "
					+ "		nome, "
					+ "		adm, "
					+ "		dtCriacao, "
					+ "		dtModificacao "
					+ "from workflow_grupo ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				try {
					ResultSet rs = stmt.executeQuery();
					try {
						
						
						while (rs.next()) {
							if(retorno == null) retorno = new ArrayList<>();
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
	public Grupo save(Grupo type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "Insert into workflow_grupo ( "
					+ "		nome, "
					+ "		adm, "
					+ "		dtCriacao, "
					+ "		dtModificacao) "
					+ "values (?, ?, ?, ?); ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, type.getNome()); 
				stmt.setBoolean(2, type.getAdm()); 
				stmt.setDate(3, type.getDtCriacao()); 
				stmt.setDate(4, type.getDtModificacao()); 
				try {
					stmt.execute();
					stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");
					
					ResultSet rs = stmt.executeQuery();
					try {
						if (rs.next()) {
							type.setIdGrupo(rs.getLong("LAST_INSERT_ID()"));
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
	public Grupo update(Grupo type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "update workflow_grupo  "
					+ "	set	nome = ?, "
					+ "		adm = ?, "
					+ "		dtCriacao = ?, "
					+ "		dtModificacao = ? "
					+ "where idGrupo = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, type.getNome()); 
				stmt.setBoolean(2, type.getAdm()); 
				stmt.setDate(3, type.getDtCriacao()); 
				stmt.setDate(4, type.getDtModificacao()); 
				stmt.setLong(5, type.getIdGrupo());
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
	public void delete(Grupo type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "delete from workflow_grupo  "
					+ "where idGrupo = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql); 
				stmt.setLong(1, type.getIdGrupo());
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
