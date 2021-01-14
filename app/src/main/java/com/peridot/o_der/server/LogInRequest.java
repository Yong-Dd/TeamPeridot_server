package com.peridot.o_der.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LogInRequest extends StringRequest {
    //php url
    final static  private String URL="http://teamperidot.dothome.co.kr/serverLogIn.php";
    private Map<String,String> map;

    public LogInRequest(String ID, String PW, Response.Listener<String> listener) {
        super(Method.POST,URL, listener,null);

        map = new HashMap<>();
        map.put("ID",ID);
        map.put("PW",PW);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
