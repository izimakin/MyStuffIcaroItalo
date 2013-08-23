package com.mystufficaroitalo;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	public Util() {
		
	}
	
	public static Usuario jsonParaUsuario(JSONObject jsonObject) throws JSONException {
		Usuario result = Usuario.getInstance();
		
		String telefone = jsonObject.getString("numeroTelefone");
		String email = jsonObject.getString("email");
		long id = jsonObject.getJSONObject("key").getLong("value");
		
		result.setId(id);
		result.setNumeroTelefone(telefone);
		result.setEmail(email);
		
		return result;
	}
}
