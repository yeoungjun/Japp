// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.ssl;

import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import javax.net.ssl.*;

// Referenced classes of package org.apache.http.conn.ssl:
//            PrivateKeyStrategy, TrustStrategy, PrivateKeyDetails

public class SSLContextBuilder
{
    static class KeyManagerDelegate
        implements X509KeyManager
    {

        public String chooseClientAlias(String as[], Principal aprincipal[], Socket socket)
        {
            HashMap hashmap = new HashMap();
            int i = as.length;
            for(int j = 0; j < i; j++)
            {
                String s = as[j];
                String as1[] = keyManager.getClientAliases(s, aprincipal);
                if(as1 == null)
                    continue;
                int k = as1.length;
                for(int l = 0; l < k; l++)
                {
                    String s1 = as1[l];
                    hashmap.put(s1, new PrivateKeyDetails(s, keyManager.getCertificateChain(s1)));
                }

            }

            return aliasStrategy.chooseAlias(hashmap, socket);
        }

        public String chooseServerAlias(String s, Principal aprincipal[], Socket socket)
        {
            HashMap hashmap = new HashMap();
            String as[] = keyManager.getServerAliases(s, aprincipal);
            if(as != null)
            {
                int i = as.length;
                for(int j = 0; j < i; j++)
                {
                    String s1 = as[j];
                    hashmap.put(s1, new PrivateKeyDetails(s, keyManager.getCertificateChain(s1)));
                }

            }
            return aliasStrategy.chooseAlias(hashmap, socket);
        }

        public X509Certificate[] getCertificateChain(String s)
        {
            return keyManager.getCertificateChain(s);
        }

        public String[] getClientAliases(String s, Principal aprincipal[])
        {
            return keyManager.getClientAliases(s, aprincipal);
        }

        public PrivateKey getPrivateKey(String s)
        {
            return keyManager.getPrivateKey(s);
        }

        public String[] getServerAliases(String s, Principal aprincipal[])
        {
            return keyManager.getServerAliases(s, aprincipal);
        }

        private final PrivateKeyStrategy aliasStrategy;
        private final X509KeyManager keyManager;

        KeyManagerDelegate(X509KeyManager x509keymanager, PrivateKeyStrategy privatekeystrategy)
        {
            keyManager = x509keymanager;
            aliasStrategy = privatekeystrategy;
        }
    }

    static class TrustManagerDelegate
        implements X509TrustManager
    {

        public void checkClientTrusted(X509Certificate ax509certificate[], String s)
            throws CertificateException
        {
            trustManager.checkClientTrusted(ax509certificate, s);
        }

        public void checkServerTrusted(X509Certificate ax509certificate[], String s)
            throws CertificateException
        {
            if(!trustStrategy.isTrusted(ax509certificate, s))
                trustManager.checkServerTrusted(ax509certificate, s);
        }

        public X509Certificate[] getAcceptedIssuers()
        {
            return trustManager.getAcceptedIssuers();
        }

        private final X509TrustManager trustManager;
        private final TrustStrategy trustStrategy;

        TrustManagerDelegate(X509TrustManager x509trustmanager, TrustStrategy truststrategy)
        {
            trustManager = x509trustmanager;
            trustStrategy = truststrategy;
        }
    }


    public SSLContextBuilder()
    {
        keymanagers = new HashSet();
        trustmanagers = new HashSet();
    }

    public SSLContext build()
        throws NoSuchAlgorithmException, KeyManagementException
    {
        String s;
        SSLContext sslcontext;
        KeyManager akeymanager[];
        TrustManager atrustmanager[];
        if(protocol != null)
            s = protocol;
        else
            s = "TLS";
        sslcontext = SSLContext.getInstance(s);
        if(!keymanagers.isEmpty())
            akeymanager = (KeyManager[])keymanagers.toArray(new KeyManager[keymanagers.size()]);
        else
            akeymanager = null;
        if(!trustmanagers.isEmpty())
            atrustmanager = (TrustManager[])trustmanagers.toArray(new TrustManager[trustmanagers.size()]);
        else
            atrustmanager = null;
        sslcontext.init(akeymanager, atrustmanager, secureRandom);
        return sslcontext;
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keystore, char ac[])
        throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException
    {
        loadKeyMaterial(keystore, ac, null);
        return this;
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keystore, char ac[], PrivateKeyStrategy privatekeystrategy)
        throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException
    {
        KeyManagerFactory keymanagerfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keymanagerfactory.init(keystore, ac);
        KeyManager akeymanager[] = keymanagerfactory.getKeyManagers();
        if(akeymanager != null)
        {
            if(privatekeystrategy != null)
            {
                for(int j = 0; j < akeymanager.length; j++)
                {
                    KeyManager keymanager = akeymanager[j];
                    if(keymanager instanceof X509KeyManager)
                        akeymanager[j] = new KeyManagerDelegate((X509KeyManager)keymanager, privatekeystrategy);
                }

            }
            for(int i = 0; i < akeymanager.length; i++)
                keymanagers.add(akeymanager[i]);

        }
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keystore)
        throws NoSuchAlgorithmException, KeyStoreException
    {
        return loadTrustMaterial(keystore, null);
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keystore, TrustStrategy truststrategy)
        throws NoSuchAlgorithmException, KeyStoreException
    {
        TrustManagerFactory trustmanagerfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustmanagerfactory.init(keystore);
        TrustManager atrustmanager[] = trustmanagerfactory.getTrustManagers();
        if(atrustmanager != null)
        {
            if(truststrategy != null)
            {
                for(int j = 0; j < atrustmanager.length; j++)
                {
                    TrustManager trustmanager = atrustmanager[j];
                    if(trustmanager instanceof X509TrustManager)
                        atrustmanager[j] = new TrustManagerDelegate((X509TrustManager)trustmanager, truststrategy);
                }

            }
            for(int i = 0; i < atrustmanager.length; i++)
                trustmanagers.add(atrustmanager[i]);

        }
        return this;
    }

    public SSLContextBuilder setSecureRandom(SecureRandom securerandom)
    {
        secureRandom = securerandom;
        return this;
    }

    public SSLContextBuilder useProtocol(String s)
    {
        protocol = s;
        return this;
    }

    public SSLContextBuilder useSSL()
    {
        protocol = "SSL";
        return this;
    }

    public SSLContextBuilder useTLS()
    {
        protocol = "TLS";
        return this;
    }

    static final String SSL = "SSL";
    static final String TLS = "TLS";
    private Set keymanagers;
    private String protocol;
    private SecureRandom secureRandom;
    private Set trustmanagers;
}
