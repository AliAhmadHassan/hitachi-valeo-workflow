package br.com.valeoservice.model.dao.mysql.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.valeoservice.model.Grupo;
import br.com.valeoservice.model.HierarquiaCabecalho;
import br.com.valeoservice.model.dao.IHierarquiaCabecalhoDAO;

public class HierarquiaCabecalhoDAO extends BaseDAO implements IHierarquiaCabecalhoDAO {
	
	public HierarquiaCabecalho getByCodSAP(String codSAP) {
      /*  try {
        	HierarquiaCabecalho retorno = (HierarquiaCabecalho) this.em
                         .createQuery(
                                     "SELECT h from HierarquiaCabecalho h where h.codSAP = :codSAP")
                         .setParameter("codSAP", codSAP).getSingleResult();

              return retorno;
        } catch (NoResultException e) {
              return null;
        }
        return null;*/
		
		
		HierarquiaCabecalho retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idHierarquiaCabecalho, "
					+ "		idUsuario, "
					+ "		nome, "
					+ "		codSAP, "
					+ "		dtCriacao, "
					+ "		dtModificacao "
					+ "from workflow_hierarquiacabecalho "
					+ "where codSAP = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, codSAP); 
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
	public HierarquiaCabecalho getById(Long ID) {
		HierarquiaCabecalho retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idHierarquiaCabecalho, "
					+ "		idUsuario, "
					+ "		nome, "
					+ "		codSAP, "
					+ "		dtCriacao, "
					+ "		dtModificacao "
					+ "from workflow_hierarquiacabecalho "
					+ "where idHierarquiaCabecalho = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setFloat(1, ID); 
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

	private HierarquiaCabecalho montaEntidade(ResultSet rs) throws SQLException {
		HierarquiaCabecalho retorno = new HierarquiaCabecalho();
		retorno.setCodSAP(rs.getString("codSAP"));
		retorno.setDtCriacao(rs.getDate("dtCriacao"));
		retorno.setDtModificacao(rs.getDate("dtModificacao"));
		retorno.setIdHierarquiaCabecalho(rs.getLong("idHierarquiaCabecalho"));
		retorno.setIdUsuario(rs.getLong("idUsuario"));
		retorno.setNome(rs.getString("nome"));
		return retorno;
	}

	@Override
	public List<HierarquiaCabecalho> getAll() {
		List<HierarquiaCabecalho> retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idHierarquiaCabecalho, "
					+ "		idUsuario, "
					+ "		nome, "
					+ "		codSAP, "
					+ "		dtCriacao, "
					+ "		dtModificacao "
					+ "from workflow_hierarquiacabecalho ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				try {
					ResultSet rs = stmt.executeQuery();
					try {
						
						
						while (rs.next()) {
							if(retorno == null) 
								retorno = new ArrayList<>();
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
	public HierarquiaCabecalho save(HierarquiaCabecalho type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "Insert into workflow_hierarquiacabecalho ( "
					+ "		idUsuario, "
					+ "		nome, "
					+ "		codSAP, "
					+ "		dtCriacao, "					
					+ "		dtModificacao) "
					+ "values (?, ?, ?, ?, ?); ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdUsuario()); 
				stmt.setString(2, type.getNome());
				stmt.setString(3, type.getCodSAP());
				stmt.setDate(4, type.getDtCriacao()); 
				stmt.setDate(5, type.getDtModificacao()); 
				try {
					stmt.execute();
					stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");
					
					ResultSet rs = stmt.executeQuery();
					try {
						if (rs.next()) {
							type.setIdHierarquiaCabecalho(rs.getLong("LAST_INSERT_ID()"));
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
	public HierarquiaCabecalho update(HierarquiaCabecalho type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "update workflow_hierarquiacabecalho  "
					+ "	set	idUsuario = ?, "
					+ "		nome = ?, "
					+ "		codSAP = ?, "
					+ "		dtCriacao = ?, "					
					+ "		dtModificacao = ? "
					+ " where idHierarquiaCabecalho = ?; ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdUsuario()); 
				stmt.setString(2, type.getNome());
				stmt.setString(3, type.getCodSAP());
				stmt.setDate(4, type.getDtCriacao()); 
				stmt.setDate(5, type.getDtModificacao()); 
				stmt.setLong(6, type.getIdHierarquiaCabecalho()); 
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
	public void delete(HierarquiaCabecalho type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "delete from workflow_hierarquiacabecalho "
					+ "where IdHierarquiaCabecalho = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdHierarquiaCabecalho()); 
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
