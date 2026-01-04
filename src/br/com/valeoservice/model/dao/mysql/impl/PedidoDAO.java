package br.com.valeoservice.model.dao.mysql.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.valeoservice.model.*;
import br.com.valeoservice.model.dao.*;

public class PedidoDAO extends BaseDAO implements IPedidoDAO {



	@Override
	public List<Pedido> getEnviarSAP() {
			/*
			  Query query = em
                         .createQuery("SELECT p from Pedido p where p.enviadoSAP = 0 and p.hierarquiaFinalizada = 1");
			
			List<Pedido> pedidos = query.getResultList();

              return pedidos;*/
		  
		  List<Pedido> retorno = null;
			try {
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url, user, pass);
				String sql = "select idPedido, "
						+ "		idHierarquiaCabecalho, "
						+ "		material, "
						+ "		descricaoMaterial ,"
						+ "		qtdVendas, "
						+ "		valorVendas, "
						+ "		precoMedio, "
						+ "		precoAtual,"
						+ "		volume, "
						+ "		desconto,"
						+ "		periodoDe, "
						+ "		periodoAte, "
						+ "		grossMargin, "
						+ "		percDCOS, "
						+ "		percBonus, "
						+ "		percFees, "
						+ "		percExtra, "
						+ "		operatingMarginValor, "
						+ "		operatingMarginPerc, "
						+ "		aprovado, "
						+ "		enviadoSAP, "
						+ "		hierarquiaFinalizada, "
						+ "		idSAP, "
						+ "		motivo "
						+ "from workflow_pedido "
						+ "where enviadoSAP = 0 "
						+ "		and hierarquiaFinalizada = 1;";
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
	
	private Pedido montaEntidade(ResultSet rs) throws SQLException {
		Pedido retorno = new Pedido();
		retorno.setAprovado(rs.getBoolean("aprovado"));
		retorno.setDesconto(rs.getDouble("desconto"));
		retorno.setDescricaoMaterial(rs.getString("descricaoMaterial"));
		retorno.setEnviadoSAP(rs.getBoolean("enviadoSAP"));
		retorno.setGrossMargin(rs.getFloat("grossMargin"));
		retorno.setHierarquiaFinalizada(rs.getBoolean("hierarquiaFinalizada"));
		retorno.setIdHierarquiaCabecalho(rs.getLong("idHierarquiaCabecalho"));
		retorno.setIdPedido(rs.getLong("idPedido"));
		retorno.setIdSAP(rs.getLong("idSAP"));
		retorno.setMaterial(rs.getString("material"));
		retorno.setMotivo(rs.getString("motivo"));
		retorno.setOperatingMarginPerc(rs.getFloat("operatingMarginPerc"));
		retorno.setOperatingMarginValor(rs.getDouble("operatingMarginValor"));
		retorno.setPercBonus(rs.getFloat("percBonus"));
		retorno.setPercDCOS(rs.getFloat("percDCOS"));
		retorno.setPercExtra(rs.getFloat("percExtra"));
		retorno.setPercFees(rs.getFloat("percFees"));
		retorno.setPeriodoAte(rs.getDate("periodoAte"));
		retorno.setPeriodoDe(rs.getDate("periodoDe"));
		retorno.setPrecoAtual(rs.getDouble("precoAtual"));
		retorno.setPrecoMedio(rs.getDouble("precoMedio"));
		retorno.setQtdVendas(rs.getInt("qtdVendas"));
		retorno.setValorVendas(rs.getDouble("valorVendas"));
		retorno.setVolume(rs.getString("volume"));
		return retorno;
	}

	@Override
	public List<Pedido> getParaAprovar(Long idGrupo) {

			  /*
			@SuppressWarnings("unchecked")
			List<Pedido> pedidos = em
                         .createQuery(
                                     "SELECT p "
                                     + "from PedidoAprovacao pa "
                                     + "inner join pa.pedido p "
                                     + "where p.hierarquiaFinalizada = 0 and "
                                     + "pa.aprovado = 0 and "
                                     + " pa.hierarquiaItem.grupo.idGrupo = :idGrupo"
                                     )
                       .setParameter("idGrupo", idGrupo)
                         .getResultList();
				
              return pedidos;*/
		
		List<Pedido> retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select workflow_pedido.idPedido, "
					+ "		workflow_pedido.idHierarquiaCabecalho, "
					+ "		workflow_pedido.material, "
					+ "		workflow_pedido.descricaoMaterial ,"
					+ "		workflow_pedido.qtdVendas, "
					+ "		workflow_pedido.valorVendas, "
					+ "		workflow_pedido.precoMedio, "
					+ "		workflow_pedido.precoAtual,"
					+ "		workflow_pedido.volume, "
					+ "		workflow_pedido.desconto,"
					+ "		workflow_pedido.periodoDe, "
					+ "		workflow_pedido.periodoAte, "
					+ "		workflow_pedido.grossMargin, "
					+ "		workflow_pedido.percDCOS, "
					+ "		workflow_pedido.percBonus, "
					+ "		workflow_pedido.percFees, "
					+ "		workflow_pedido.percExtra, "
					+ "		workflow_pedido.operatingMarginValor, "
					+ "		workflow_pedido.operatingMarginPerc, "
					+ "		workflow_pedido.aprovado, "
					+ "		workflow_pedido.enviadoSAP, "
					+ "		workflow_pedido.hierarquiaFinalizada, "
					+ "		workflow_pedido.idSAP, "
					+ "		workflow_pedido.motivo "
					+ "from workflow_pedidoaprovacao "
					+ "		inner join workflow_pedido on workflow_pedidoaprovacao.idPedido = workflow_pedido.idPedido "
					+ "		inner join workflow_hierarquiaitem on workflow_hierarquiaitem.idHierarquiaItem = workflow_pedidoaprovacao.idHierarquiaItem "
					+ "where workflow_pedido.hierarquiaFinalizada = 0 "
					+ "		and workflow_pedidoaprovacao.aprovado = 0 "
					+ "		and workflow_hierarquiaitem.idGrupo = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setFloat(1, idGrupo); 
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
	public Pedido getById(Long ID) {
		Pedido retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idPedido, "
					+ "		idHierarquiaCabecalho, "
					+ "		material, "
					+ "		descricaoMaterial ,"
					+ "		qtdVendas, "
					+ "		valorVendas, "
					+ "		precoMedio, "
					+ "		precoAtual,"
					+ "		volume, "
					+ "		desconto,"
					+ "		periodoDe, "
					+ "		periodoAte, "
					+ "		grossMargin, "
					+ "		percDCOS, "
					+ "		percBonus, "
					+ "		percFees, "
					+ "		percExtra, "
					+ "		operatingMarginValor, "
					+ "		operatingMarginPerc, "
					+ "		aprovado, "
					+ "		enviadoSAP, "
					+ "		hierarquiaFinalizada, "
					+ "		idSAP, "
					+ "		motivo "
					+ "from workflow_pedido "
					+ "where idPedido = ?";
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
	public List<Pedido> getAll() {
		List<Pedido> retorno = null;
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "select idPedido, "
					+ "		idHierarquiaCabecalho, "
					+ "		material, "
					+ "		descricaoMaterial ,"
					+ "		qtdVendas, "
					+ "		valorVendas, "
					+ "		precoMedio, "
					+ "		precoAtual,"
					+ "		volume, "
					+ "		desconto,"
					+ "		periodoDe, "
					+ "		periodoAte, "
					+ "		grossMargin, "
					+ "		percDCOS, "
					+ "		percBonus, "
					+ "		percFees, "
					+ "		percExtra, "
					+ "		operatingMarginValor, "
					+ "		operatingMarginPerc, "
					+ "		aprovado, "
					+ "		enviadoSAP, "
					+ "		hierarquiaFinalizada, "
					+ "		idSAP, "
					+ "		motivo "
					+ "from workflow_pedido ";
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
	public Pedido save(Pedido type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "Insert into workflow_pedido ( "
					+ "		idHierarquiaCabecalho, "
					+ "		material, "
					+ "		descricaoMaterial ,"
					+ "		qtdVendas, "
					+ "		valorVendas, "
					+ "		precoMedio, "
					+ "		precoAtual,"
					+ "		volume, "
					+ "		desconto,"
					+ "		periodoDe, "
					+ "		periodoAte, "
					+ "		grossMargin, "
					+ "		percDCOS, "
					+ "		percBonus, "
					+ "		percFees, "
					+ "		percExtra, "
					+ "		operatingMarginValor, "
					+ "		operatingMarginPerc, "
					+ "		aprovado, "
					+ "		enviadoSAP, "
					+ "		hierarquiaFinalizada, "
					+ "		idSAP, "
					+ "		motivo) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdHierarquiaCabecalho());
				stmt.setString(2, type.getMaterial());
				stmt.setString(3, type.getDescricaoMaterial());
				stmt.setInt(4, type.getQtdVendas());
				stmt.setDouble(5, type.getValorVendas());
				stmt.setDouble(6, type.getPrecoMedio());
				stmt.setDouble(7, type.getPrecoAtual());
				stmt.setString(8, type.getVolume());
				stmt.setDouble(9, type.getDesconto());
				stmt.setDate(10, type.getPeriodoDe());
				stmt.setDate(11, type.getPeriodoAte());
				stmt.setFloat(12, type.getGrossMargin());
				stmt.setFloat(13, type.getPercDCOS());
				stmt.setFloat(14, type.getPercBonus());
				stmt.setFloat(15, type.getPercFees());
				stmt.setFloat(16, type.getPercExtra());
				stmt.setDouble(17, type.getOperatingMarginValor());
				stmt.setFloat(18, type.getOperatingMarginPerc());
				stmt.setBoolean(19, type.getAprovado());
				stmt.setBoolean(20, type.getEnviadoSAP());
				stmt.setBoolean(21, type.getHierarquiaFinalizada());
				stmt.setLong(22, type.getIdSAP());
				stmt.setString(23, type.getMotivo());
				try {
					stmt.execute();
					stmt = conn.prepareStatement("SELECT LAST_INSERT_ID();");
					
					ResultSet rs = stmt.executeQuery();
					try {
						if (rs.next()) {
							type.setIdPedido(rs.getLong("LAST_INSERT_ID()"));
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
	public Pedido update(Pedido type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "Update workflow_pedido "
					+ "	set	idHierarquiaCabecalho = ?, "
							+ "		material = ?, "
							+ "		descricaoMaterial = ? ,"
							+ "		qtdVendas = ?, "
							+ "		valorVendas = ?, "
							+ "		precoMedio = ?, "
							+ "		precoAtual = ?,"
							+ "		volume = ?, "
							+ "		desconto = ?,"
							+ "		periodoDe = ?, "
							+ "		periodoAte = ?, "
							+ "		grossMargin = ?, "
							+ "		percDCOS = ?, "
							+ "		percBonus = ?, "
							+ "		percFees = ?, "
							+ "		percExtra = ?, "
							+ "		operatingMarginValor = ?, "
							+ "		operatingMarginPerc = ?, "
							+ "		aprovado = ?, "
							+ "		enviadoSAP = ?, "
							+ "		hierarquiaFinalizada = ?, "
							+ "		idSAP = ?, "
							+ "		motivo = ? "
					+ "where IdPedido = ?; ";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdHierarquiaCabecalho());
				stmt.setString(2, type.getMaterial());
				stmt.setString(3, type.getDescricaoMaterial());
				stmt.setInt(4, type.getQtdVendas());
				stmt.setDouble(5, type.getValorVendas());
				stmt.setDouble(6, type.getPrecoMedio());
				stmt.setDouble(7, type.getPrecoAtual());
				stmt.setString(8, type.getVolume());
				stmt.setDouble(9, type.getDesconto());
				stmt.setDate(10, type.getPeriodoDe());
				stmt.setDate(11, type.getPeriodoAte());
				stmt.setFloat(12, type.getGrossMargin());
				stmt.setFloat(13, type.getPercDCOS());
				stmt.setFloat(14, type.getPercBonus());
				stmt.setFloat(15, type.getPercFees());
				stmt.setFloat(16, type.getPercExtra());
				stmt.setDouble(17, type.getOperatingMarginValor());
				stmt.setFloat(18, type.getOperatingMarginPerc());
				stmt.setBoolean(19, type.getAprovado());
				stmt.setBoolean(20, type.getEnviadoSAP());
				stmt.setBoolean(21, type.getHierarquiaFinalizada());
				stmt.setLong(22, type.getIdSAP());
				stmt.setString(23, type.getMotivo());
				stmt.setLong(24, type.getIdPedido());
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
	public void delete(Pedido type) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "delete from workflow_pedido "
					+ "where IdPedido = ?;";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setLong(1, type.getIdPedido()); 
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
