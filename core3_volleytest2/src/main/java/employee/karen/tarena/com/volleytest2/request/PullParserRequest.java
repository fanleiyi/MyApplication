package employee.karen.tarena.com.volleytest2.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import employee.karen.tarena.com.volleytest2.entity.WeatherBean;

/**
 * Created by tarena on 2017/6/9.
 */

public class PullParserRequest extends Request<XmlPullParser> {
    private final Response.Listener<XmlPullParser> mListener;
    public PullParserRequest(int method,
                             String url,
                             Response.Listener<XmlPullParser> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
    }
    public PullParserRequest(String url,
                             Response.Listener<XmlPullParser> listener,
                             Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse networkResponse) {
        XmlPullParser parser=null;
        try {
            String parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            // 构建一个Pull解析器
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            parser=factory.newPullParser();
            parser.setInput(new StringReader(parsed));

          return Response.success(parser,HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (Exception var4) {
            return Response.error(new ParseError(networkResponse));
        }
    }

    @Override
    protected void deliverResponse(XmlPullParser xmlPullParser) {

    }
}
