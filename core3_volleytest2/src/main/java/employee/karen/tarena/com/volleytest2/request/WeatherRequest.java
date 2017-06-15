package employee.karen.tarena.com.volleytest2.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import employee.karen.tarena.com.volleytest2.entity.WeatherBean;

/**
 * Created by tarena on 2017/6/9.
 */

public class WeatherRequest extends Request<WeatherBean> {
    private final Response.Listener<WeatherBean> mListener;

    public WeatherRequest(int method,
                          String url,
                          Response.Listener<WeatherBean> listener,
                          Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
    }

    public WeatherRequest(String url,
                          Response.Listener<WeatherBean> listener,
                          Response.ErrorListener errorListener) {
        this(0, url, listener, errorListener);
    }

    @Override
    protected Response<WeatherBean> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Gson gson=new Gson();
            WeatherBean weatherBean=gson.fromJson(parsed,WeatherBean.class);
            return Response.success(weatherBean, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException var4) {
            return Response.error(new ParseError(var4));
        }
    }

    @Override
    protected void deliverResponse(WeatherBean weatherBean) {
        this.mListener.onResponse(weatherBean);
    }
}
