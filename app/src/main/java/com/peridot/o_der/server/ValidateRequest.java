package com.peridot.o_der.server;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


//회원 아이디 체크(회원가입이 가능한 아이디인지)

public class ValidateRequest extends StringRequest {

    final static  private String URL = "http://teamperidot.dothome.co.kr/serverValidate.php"; //DB파일이 없으므로 샘플 사이트 첨부
    private Map<String, String> parameters;

    public ValidateRequest(String ID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID",ID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
