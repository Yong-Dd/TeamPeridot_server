package com.peridot.o_der.server;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class TeaRequest extends JsonObjectRequest {
    //php url
    final static  private String URL="http://teamperidot.dothome.co.kr/tea.php";

    public TeaRequest(JSONObject request, Response.Listener<JSONObject> listener) {
        super(URL,request,listener,null);

    }
}
