package br.com.valeoservice.model.dao.mysql.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.valeoservice.model.*;
import br.com.valeoservice.model.dao.IHierarquiaItemDAO;

public class HierarquiaItemDAO extends BaseDAO implements IHierarquiaItemDAO  {

	
	public List<HierarquiaItem> getConsultaItens(Long idHierarquiaCabecalho) {
		/* try {
             @SuppressWarnings("unchecked")
			List<HierarquiaItem> itens = (List<HierarquiaItem>) em	
                        .createQuery("SELECT u from HierarquiaItem u where u.hierarquiaCabecalho.idHierarquiaCabecalho = :idHierarquiaCabecalho")
                        .setParameter("idHierarquiaCabecalho", idHierarquiaCabecalho).getResultList();

             return itens;
       } catch (NoResultException e) {
             return null;
       }return null;*/
		
		List<HierarquiaItem> retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idHierarquiaItem, "
					+ "		idHierarquiaItemFilho, "
					+ "		idGrupo, "
					+ "		idHierarquiaCabecalho "
					+ "from workflow_hierarquiaitem "
					+ "where idHierarquiaCabecalho = ?";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setFloat(1, idHierarquiaCabecalho); 
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

	private HierarquiaItem montaEntidade(ResultSet rs) throws SQLException {
		HierarquiaItem retorno = new HierarquiaItem();
		retorno.setIdGrupo(rs.getLong("idGrupo"));
		retorno.setIdHierarquiaCabecalho(rs.getLong("idHierarquiaCabecalho"));
		retorno.setIdHierarquiaItem(rs.getLong("idHierarquiaItem"));
		retorno.setIdHierarquiaItemFilho(rs.getLong("idHierarquiaItemFilho"));
		return retorno;
	}

	@Override
	public HierarquiaItem getById(Long ID) {
		HierarquiaItem retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idHierarquiaItem, "
					+ "		idHierarquiaItemFilho, "
					+ "		idGrupo, "
					+ "		idHierarquiaCabecalho "
					+ "from workflow_hierarquiaitem "
					+ "where idHierarquiaItem = ?";
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

	@Override
	public List<HierarquiaItem> getAll() {
		List<HierarquiaItem> retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idHierarquiaItem, "
					+ "		idHierarquiaItemFilho, "
					+ "		idGrupo, "
					+ "		idHierarquiaCabecalho "
					+ "from workflow_hierarquiaitem ";
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
	public HierarquiaItem save(HierarquiaItem type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "Insert into workflow_hierarquiaitem ( "
					+ "		idHierarquiaItemFilho, "
					+ "		idGrupo, "
					+ "		idHierarquiaCabecalho) "
					+ "values (?, ?, ?); ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				if(type.getIdHierarquiaItemFilho() == null)
					stmt.setNull(1, Types.BIGINT);
				else
					stmt.setLong(1, type.getIdHierarquiaItemFilho());
				stmt.setLong(2, type.getIdGrupo());
				stmt.setLong(3, type.getIdHierarquiaCabecalho());
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
	public HierarquiaItem update(HierarquiaItem type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "Update workflow_hierarquiaitem "
					+ "	set	idHierarquiaItemFilho = ?, "
					+ "		idGrupo = ?, "
					+ "		idHierarquiaCabecalho = ? "
					+ "where IdHierarquiaItem = ?; ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdHierarquiaItemFilho()); 
				stmt.setLong(2, type.getIdGrupo());
				stmt.setLong(3, type.getIdHierarquiaCabecalho());
				stmt.setLong(4, type.getIdHierarquiaItem());
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
	public void delete(HierarquiaItem type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "delete from workflow_hierarquiaitem "
					+ "where IdHierarquiaItem = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdHierarquiaItem()); 
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
