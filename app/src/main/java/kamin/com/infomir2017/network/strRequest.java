package kamin.com.infomir2017.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import static kamin.com.infomir2017.Model.DataHolder.curentCharset;
import static kamin.com.infomir2017.Model.DataHolder.encoding;


public class strRequest extends StringRequest {
    public strRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        //Log.d("","head "+response.headers.toString());
        Log.d("Charset", "Charset " + HttpHeaderParser.parseCharset(response.headers));
        Log.d("Charset", "Charset " + (response.headers));
        curentCharset = HttpHeaderParser.parseCharset(response.headers);
        Log.d("Charset", "Charset  getParamsEncoding()  " + getParamsEncoding());
        Log.d("Charset", "Charset " + response.headers.get("content-type"));
        String firstLine = response.headers.get("content-type");
        int start = firstLine.indexOf("Charset=");
        if (start > 0)
            encoding = firstLine.substring(start+8);
        else
            encoding = "UTF-8";
        Log.d("Charset", "encoding " + encoding);

        return super.parseNetworkResponse(response);

    }


}
