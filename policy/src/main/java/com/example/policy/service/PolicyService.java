package com.example.policy.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.dto.PolicyModel;

@Repository
public class PolicyService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Map<String,ArrayList<Object>> getWhereClause(Map<String, Object> queryMap) {
		ArrayList<Object> param = new ArrayList<Object>();
		Map<String,ArrayList<Object>> plm = new LinkedHashMap<String,ArrayList<Object>>();
		int i = 1;
		StringBuffer whereClause = new StringBuffer ("");
		for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
		   // System.out.println("--> "+entry.getKey()+"----"+entry.getValue());
		    if (queryMap.size() <= 0)
				return null;
		    if(i == 1 && queryMap.size() >= 1){
		    	whereClause.append (" WHERE ").append (entry.getKey());
				param.add((Object) queryMap.get(entry.getKey()));
		    }else{
		    	whereClause.append (" AND ").append(entry.getKey());
				param.add((Object) queryMap.get(entry.getKey()));
		    }
			
			i++;
		}
		plm.put(whereClause.toString(), param);
		return plm;
	}
	
	
	public List<PolicyModel> getAllPoplicyDetails() {
		try{
			List<PolicyModel> modelList = new ArrayList<PolicyModel>();
			 String sql = "SELECT policy_id,policy_name,company_name,policy_cc_email,policy_cc_phone,policy_term_years FROM loc_pm_policy ";


			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql );
			Map<String, String> objMap = new HashMap<String, String>();
			for (Map row : rows) {
				PolicyModel model = new PolicyModel();
				model.setPolicyId(new BigDecimal(row.get("policy_id").toString()).intValue());
				model.setPolicyCompany(row.get("company_name").toString());
				model.setPolicyName(row.get("policy_name").toString());
				model.setPolicyEmail(row.get("policy_cc_email").toString());
				model.setPolicyYrs(new BigDecimal(row.get("policy_term_years").toString()).intValue());
				modelList.add(model);
			}
			return modelList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<Integer, List<PolicyModel>> getCustomerPolicyDetails(PolicyModel policy) {
		try{
			List<PolicyModel> modelList = new ArrayList<PolicyModel>();
			 String sql = "SELECT lup.user_id,lpp.policy_id,lpp.policy_name,lpp.company_name,lpp.policy_cc_email,lpp.policy_cc_phone,lpp.policy_term_years " + 
			 		"FROM loc_pm_policy lpp " + 
			 		"LEFT JOIN loc_user_policy lup on lup.policy_id = lpp.policy_id " ;
			 String orderSql =	"order by lup.user_id,lpp.policy_id ";
			 if(policy != null && policy.getCustomerId() > 0) {
				String whereSql = "  ";
			    Map<String,Object> qryMap = new LinkedHashMap<String,Object>();
		        ArrayList<Object> paramList = new ArrayList<Object>();
		        if(policy.getPolicyId() > 0 ){
		        	qryMap.put(" lup.user_id = ? ", policy.getCustomerId());
		        }
		        Map<String,ArrayList<Object>> retmap = this.getWhereClause(qryMap);
		        for (Map.Entry<String, ArrayList<Object>> entry : retmap.entrySet()) {
				    whereSql = entry.getKey();
				    paramList = entry.getValue();
				}
		        sql += whereSql;
			 }

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql + orderSql);
			Map<Integer, List<PolicyModel>> objMap = new HashMap<Integer, List<PolicyModel>>();
			for (Map row : rows) {
				PolicyModel model = new PolicyModel();
				int customerId = -1;
				if(row.get("user_id") != null) {
					customerId = new BigDecimal(row.get("user_id").toString()).intValue();
				}
				
				model.setPolicyId(new BigDecimal(row.get("policy_id").toString()).intValue());
				model.setPolicyCompany(row.get("company_name").toString());
				model.setPolicyName(row.get("policy_name").toString());
				model.setPolicyEmail(row.get("policy_cc_email").toString());
				model.setPolicyYrs(new BigDecimal(row.get("policy_term_years").toString()).intValue());
				
				if(objMap.containsKey(customerId)) {
					objMap.get(customerId).add(model);
				}else {
					List<PolicyModel> pList = new ArrayList<PolicyModel>();
					pList.add(model);
					objMap.put(customerId, pList);
				}								
			}
			return objMap;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public PolicyModel getPolicyById(PolicyModel policy) {
        String sql = "SELECT policy_id,policy_name,company_name,policy_cc_email,policy_cc_phone,policy_term_years FROM loc_pm_policy ";
		try{
			    String whereSql = "  ";
			    Map<String,Object> qryMap = new LinkedHashMap<String,Object>();
		        ArrayList<Object> paramList = new ArrayList<Object>();
		        if(policy.getPolicyId() > 0 ){
		        	qryMap.put(" policy_id = ? ", policy.getPolicyId());
		        }
		        Map<String,ArrayList<Object>> retmap = this.getWhereClause(qryMap);
		        for (Map.Entry<String, ArrayList<Object>> entry : retmap.entrySet()) {
				    whereSql = entry.getKey();
				    paramList = entry.getValue();
				}
		        sql += whereSql;
		        //if(paramList.size() > 0){
		        	Object[] param = paramList.toArray(new Object[paramList.size()]);
		        	PolicyModel policyModel = jdbcTemplate.queryForObject(
		                    sql,
		                    param,
		                    new RowMapper<PolicyModel>() {
		                        public PolicyModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		                        	PolicyModel bean = new PolicyModel();
		                            bean.setPolicyId(rs.getInt("policy_id")); 
		                            bean.setPolicyCompany(rs.getString("company_name"));
		                            bean.setPolicyName(rs.getString("policy_name"));
		                            bean.setPolicyEmail(rs.getString("policy_cc_email"));
		                            bean.setPolicyYrs(rs.getInt("policy_term_years"));
		                            /*List<String> list = Arrays.asList(rs.getString("role_name"));
		                            String result = String.join(",", list);*/
		                            return bean;
		                        }
		                    });
		        	return policyModel;
		        /*}else{
		        	 return null;
		        }*/
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
      
        
    }


	public int addNewPolicy(PolicyModel policy) {
		String query = "INSERT INTO loc_pm_policy(policy_name,company_name,policy_cc_email,policy_cc_phone,policy_term_years) VALUES( ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		    	@Override
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"policy_name,company_name,policy_cc_email,policy_cc_phone,policy_term_years"});
		            ps.setString(1, policy.getPolicyName());
		            ps.setString(2, policy.getPolicyCompany());
		            ps.setString(3, policy.getPolicyEmail());
		            ps.setInt(4, policy.getPolicyContactNo());
		            ps.setInt(5, policy.getPolicyYrs());
		            return ps;
		        }

				
		    },
		    keyHolder);
		//String rolequery = "INSERT INTO loc_user_roles( user_id, role_id) VALUES(?, ?)";
		//int ret = jdbcTemplate.update(rolequery, keyHolder.getKey(), customerModel.getRoleId());
		return keyHolder.getKey().intValue();
	}

	
	public int addNewCustomerPolicy(PolicyModel policy) {
		String query = "INSERT INTO loc_user_policy( user_id, policy_id) VALUES(?, ?)";
		int ret = jdbcTemplate.update(query, policy.getCustomerId(), policy.getPolicyId());
		return ret;
	}

}
