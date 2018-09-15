package com.taobao.api.response;

import com.taobao.api.TaobaoObject;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.internal.mapping.ApiField;

/**
 * TOP API: taobao.tbk.tpwd.create response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class TbkTpwdCreateResponse extends TaobaoResponse {

	private static final long serialVersionUID = 8174498455272937381L;

	/** 
	 * data
	 */
	@ApiField("data")
	private Map data;


	public void setData(Map data) {
		this.data = data;
	}
	public Map getData( ) {
		return this.data;
	}
	
	/**
 * data
 *
 * @author top auto create
 * @since 1.0, null
 */
public static class Map extends TaobaoObject {

	private static final long serialVersionUID = 6182761877372795326L;

	/**
		 * password
		 */
		@ApiField("model")
		private String model;
	

	public String getModel() {
			return this.model;
		}
		public void setModel(String model) {
			this.model = model;
		}

}



}
