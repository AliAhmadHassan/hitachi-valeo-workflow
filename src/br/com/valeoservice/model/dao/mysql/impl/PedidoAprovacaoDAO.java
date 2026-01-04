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
import br.com.valeoservice.model.dao.*;

public class PedidoAprovacaoDAO extends BaseDAO implements IPedidoAprovacaoDAO {

	public PedidoAprovacao getPedidoAprovacao(Long idPedido, Long idHierarquiaItem) {

		/*
		 * PedidoAprovacao pedidoAprovacao = (PedidoAprovacao) this.em .createQuery(
		 * "SELECT pa " + "from PedidoAprovacao pa " +
		 * "where pa.hierarquiaItem.idHierarquiaItem = :idHierarquiaItem and " +
		 * "		pa.pedido.idPedido = :idPedido" ) .setParameter("idHierarquiaItem",
		 * idHierarquiaItem) .setParameter("idPedido", idPedido) .getSingleResult();
		 * 
		 * return pedidoAprovacao;
		 */

		PedidoAprovacao retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idPedidoAprovacao, " + "		idUsuario, " + "		idHierarquiaItem, "
					+ "		idPedido, " + "		aprovado, " + "		eMailEnviado, " + "		dtAprovacao "
					+ "from workflow_pedidoaprovacao " + "where idHierarquiaItem = ? " + "		and idPedido = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setFloat(1, idHierarquiaItem);
				stmt.setFloat(2, idPedido);
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

	private PedidoAprovacao montaEntidade(ResultSet rs) throws SQLException {
		PedidoAprovacao retorno = new PedidoAprovacao();
		retorno.setAprovado(rs.getBoolean("aprovado"));
		retorno.setDtAprovacao(rs.getDate("dtAprovacao"));
		retorno.seteMailEnviado(rs.getBoolean("eMailEnviado"));
		retorno.setIdHierarquiaItem(rs.getLong("idHierarquiaItem"));
		retorno.setIdPedido(rs.getLong("idPedido"));
		retorno.setIdPedidoAprovacao(rs.getLong("idPedidoAprovacao"));
		retorno.setIdUsuario(rs.getLong("idUsuario"));
		return retorno;
	}

	@Override
	public PedidoAprovacao getById(Long ID) {
		PedidoAprovacao retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idPedidoAprovacao, " + "		idUsuario, " + "		idHierarquiaItem, "
					+ "		idPedido, " + "		aprovado, " + "		eMailEnviado, " + "		dtAprovacao "
					+ "from workflow_pedidoaprovacao " + "where idPedidoAprovacao = ? ;";
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
	public List<PedidoAprovacao> getAll() {
		List<PedidoAprovacao> retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idPedidoAprovacao, " + "		idUsuario, " + "		idHierarquiaItem, "
					+ "		idPedido, " + "		aprovado, " + "		eMailEnviado, " + "		dtAprovacao "
					+ "from workflow_pedidoaprovacao ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);

				try {
					ResultSet rs = stmt.executeQuery();
					try {

						while (rs.next()) {
							if (retorno == null)
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
	public PedidoAprovacao save(PedidoAprovacao type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "Insert into workflow_pedidoaprovacao ( " + "		idUsuario, " + "		idHierarquiaItem, "
					+ "		idPedido, " + "		aprovado, " + "		eMailEnviado, " + "		dtAprovacao) "
					+ "values (?, ?, ?, ?, ?, ?); ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				if(type.getIdUsuario() == null)
					stmt.setNull(1, Types.BIGINT);
				else
					stmt.setLong(1, type.getIdUsuario());
				stmt.setLong(2, type.getIdHierarquiaItem());
				stmt.setLong(3, type.getIdPedido());
				stmt.setBoolean(4, type.getAprovado());
				stmt.setBoolean(5, type.geteMailEnviado());
				stmt.setDate(6, type.getDtAprovacao());
				try {
					stmt.execute();
					stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");

					ResultSet rs = stmt.executeQuery();
					try {
						if (rs.next()) {
							type.setIdPedidoAprovacao(rs.getLong("LAST_INSERT_ID()"));
						}
					} catch (Exception e) {
						System.out.println("Erro de banco: " + e.getMessage());
					} finally {
						rs.close();
					}
				} catch (Exception e) {
					System.out.println("Erro de banco: " + e.getMessage());
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
	public PedidoAprovacao update(PedidoAprovacao type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "Update workflow_pedidoaprovacao " + "	set	idUsuario = ?, " + "		idHierarquiaItem = ?, "
					+ "		idPedido = ?, " + "		aprovado = ?, " + "		eMailEnviado = ?, "
					+ "		dtAprovacao = ? " + "where idPedidoAprovacao = ?; ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdUsuario());
				stmt.setLong(2, type.getIdHierarquiaItem());
				stmt.setLong(3, type.getIdPedido());
				stmt.setBoolean(4, type.getAprovado());
				stmt.setBoolean(5, type.geteMailEnviado());
				stmt.setDate(6, type.getDtAprovacao());
				stmt.setLong(7, type.getIdPedidoAprovacao());
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
	public void delete(PedidoAprovacao type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "delete from workflow_pedidoaprovacao " + "where idPedidoAprovacao = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdPedidoAprovacao());
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
