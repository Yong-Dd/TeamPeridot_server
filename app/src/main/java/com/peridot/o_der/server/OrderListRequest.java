package com.peridot.o_der.server;

import com.android.volley.Response;
import com.android.volley.request.JsonObjectRequest;


import org.json.JSONObject;

public class OrderListRequest extends JsonObjectRequest {
    //php url
    final static  private String URL="http://teamperidot.dothome.co.kr/orderList.php";

    public OrderListRequest(JSONObject request, Response.Listener<JSONObject> listener) {
        super(URL,request,listener,null);
    }
}

