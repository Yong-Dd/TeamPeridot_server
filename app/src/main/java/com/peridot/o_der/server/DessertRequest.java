package com.peridot.o_der.server;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class DessertRequest extends JsonObjectRequest {
    //php url
    final static  private String URL="http://teamperidot.dothome.co.kr/dessert.php";

    public DessertRequest(JSONObject request, Response.Listener<JSONObject> listener) {
        super(URL,request,listener,null);

    }

}
