package webhook.teamcity;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;

public class WebHookHttpClientFactoryImpl implements WebHookHttpClientFactory {
	
	@Override
	public HttpClient getHttpClient(){
    HttpClient client = null;
    try {
		  client = HttpClients.custom()
            .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();
            }
     catch (Exception e) {
       // Do Nothing; can actually never happen
     }

     return client;
	}

}
