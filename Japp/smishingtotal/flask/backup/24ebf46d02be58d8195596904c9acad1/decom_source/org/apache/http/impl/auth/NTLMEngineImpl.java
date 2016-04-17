// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.util.EncodingUtils;

// Referenced classes of package org.apache.http.impl.auth:
//            NTLMEngine, NTLMEngineException

final class NTLMEngineImpl
    implements NTLMEngine
{
    protected static class CipherGen
    {

        public byte[] getClientChallenge()
            throws NTLMEngineException
        {
            if(clientChallenge == null)
                clientChallenge = NTLMEngineImpl.makeRandomChallenge();
            return clientChallenge;
        }

        public byte[] getClientChallenge2()
            throws NTLMEngineException
        {
            if(clientChallenge2 == null)
                clientChallenge2 = NTLMEngineImpl.makeRandomChallenge();
            return clientChallenge2;
        }

        public byte[] getLM2SessionResponse()
            throws NTLMEngineException
        {
            if(lm2SessionResponse == null)
            {
                byte abyte0[] = getClientChallenge();
                lm2SessionResponse = new byte[24];
                System.arraycopy(abyte0, 0, lm2SessionResponse, 0, abyte0.length);
                Arrays.fill(lm2SessionResponse, abyte0.length, lm2SessionResponse.length, (byte)0);
            }
            return lm2SessionResponse;
        }

        public byte[] getLMHash()
            throws NTLMEngineException
        {
            if(lmHash == null)
                lmHash = NTLMEngineImpl.lmHash(password);
            return lmHash;
        }

        public byte[] getLMResponse()
            throws NTLMEngineException
        {
            if(lmResponse == null)
                lmResponse = NTLMEngineImpl.lmResponse(getLMHash(), challenge);
            return lmResponse;
        }

        public byte[] getLMUserSessionKey()
            throws NTLMEngineException
        {
            if(lmUserSessionKey == null)
            {
                byte abyte0[] = getLMHash();
                lmUserSessionKey = new byte[16];
                System.arraycopy(abyte0, 0, lmUserSessionKey, 0, 8);
                Arrays.fill(lmUserSessionKey, 8, 16, (byte)0);
            }
            return lmUserSessionKey;
        }

        public byte[] getLMv2Hash()
            throws NTLMEngineException
        {
            if(lmv2Hash == null)
                lmv2Hash = NTLMEngineImpl.lmv2Hash(domain, user, getNTLMHash());
            return lmv2Hash;
        }

        public byte[] getLMv2Response()
            throws NTLMEngineException
        {
            if(lmv2Response == null)
                lmv2Response = NTLMEngineImpl.lmv2Response(getLMv2Hash(), challenge, getClientChallenge());
            return lmv2Response;
        }

        public byte[] getLanManagerSessionKey()
            throws NTLMEngineException
        {
            if(lanManagerSessionKey == null)
            {
                byte abyte0[] = getLMHash();
                byte abyte1[] = getLMResponse();
                try
                {
                    byte abyte2[] = new byte[14];
                    System.arraycopy(abyte0, 0, abyte2, 0, 8);
                    Arrays.fill(abyte2, 8, abyte2.length, (byte)-67);
                    Key key = NTLMEngineImpl.createDESKey(abyte2, 0);
                    Key key1 = NTLMEngineImpl.createDESKey(abyte2, 7);
                    byte abyte3[] = new byte[8];
                    System.arraycopy(abyte1, 0, abyte3, 0, abyte3.length);
                    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
                    cipher.init(1, key);
                    byte abyte4[] = cipher.doFinal(abyte3);
                    Cipher cipher1 = Cipher.getInstance("DES/ECB/NoPadding");
                    cipher1.init(1, key1);
                    byte abyte5[] = cipher1.doFinal(abyte3);
                    lanManagerSessionKey = new byte[16];
                    System.arraycopy(abyte4, 0, lanManagerSessionKey, 0, abyte4.length);
                    System.arraycopy(abyte5, 0, lanManagerSessionKey, abyte4.length, abyte5.length);
                }
                catch(Exception exception)
                {
                    throw new NTLMEngineException(exception.getMessage(), exception);
                }
            }
            return lanManagerSessionKey;
        }

        public byte[] getNTLM2SessionResponse()
            throws NTLMEngineException
        {
            if(ntlm2SessionResponse == null)
                ntlm2SessionResponse = NTLMEngineImpl.ntlm2SessionResponse(getNTLMHash(), challenge, getClientChallenge());
            return ntlm2SessionResponse;
        }

        public byte[] getNTLM2SessionResponseUserSessionKey()
            throws NTLMEngineException
        {
            if(ntlm2SessionResponseUserSessionKey == null)
            {
                byte abyte0[] = getNTLMUserSessionKey();
                byte abyte1[] = getLM2SessionResponse();
                byte abyte2[] = new byte[challenge.length + abyte1.length];
                System.arraycopy(challenge, 0, abyte2, 0, challenge.length);
                System.arraycopy(abyte1, 0, abyte2, challenge.length, abyte1.length);
                ntlm2SessionResponseUserSessionKey = NTLMEngineImpl.hmacMD5(abyte2, abyte0);
            }
            return ntlm2SessionResponseUserSessionKey;
        }

        public byte[] getNTLMHash()
            throws NTLMEngineException
        {
            if(ntlmHash == null)
                ntlmHash = NTLMEngineImpl.ntlmHash(password);
            return ntlmHash;
        }

        public byte[] getNTLMResponse()
            throws NTLMEngineException
        {
            if(ntlmResponse == null)
                ntlmResponse = NTLMEngineImpl.lmResponse(getNTLMHash(), challenge);
            return ntlmResponse;
        }

        public byte[] getNTLMUserSessionKey()
            throws NTLMEngineException
        {
            if(ntlmUserSessionKey == null)
            {
                byte abyte0[] = getNTLMHash();
                MD4 md4 = new MD4();
                md4.update(abyte0);
                ntlmUserSessionKey = md4.getOutput();
            }
            return ntlmUserSessionKey;
        }

        public byte[] getNTLMv2Blob()
            throws NTLMEngineException
        {
            if(ntlmv2Blob == null)
                ntlmv2Blob = NTLMEngineImpl.createBlob(getClientChallenge2(), targetInformation, getTimestamp());
            return ntlmv2Blob;
        }

        public byte[] getNTLMv2Hash()
            throws NTLMEngineException
        {
            if(ntlmv2Hash == null)
                ntlmv2Hash = NTLMEngineImpl.ntlmv2Hash(domain, user, getNTLMHash());
            return ntlmv2Hash;
        }

        public byte[] getNTLMv2Response()
            throws NTLMEngineException
        {
            if(ntlmv2Response == null)
                ntlmv2Response = NTLMEngineImpl.lmv2Response(getNTLMv2Hash(), challenge, getNTLMv2Blob());
            return ntlmv2Response;
        }

        public byte[] getNTLMv2UserSessionKey()
            throws NTLMEngineException
        {
            if(ntlmv2UserSessionKey == null)
            {
                byte abyte0[] = getNTLMv2Hash();
                byte abyte1[] = new byte[16];
                System.arraycopy(getNTLMv2Response(), 0, abyte1, 0, 16);
                ntlmv2UserSessionKey = NTLMEngineImpl.hmacMD5(abyte1, abyte0);
            }
            return ntlmv2UserSessionKey;
        }

        public byte[] getSecondaryKey()
            throws NTLMEngineException
        {
            if(secondaryKey == null)
                secondaryKey = NTLMEngineImpl.makeSecondaryKey();
            return secondaryKey;
        }

        public byte[] getTimestamp()
        {
            if(timestamp == null)
            {
                long l = 10000L * (0xa9730b66800L + System.currentTimeMillis());
                timestamp = new byte[8];
                for(int i = 0; i < 8; i++)
                {
                    timestamp[i] = (byte)(int)l;
                    l >>>= 8;
                }

            }
            return timestamp;
        }

        protected final byte challenge[];
        protected byte clientChallenge[];
        protected byte clientChallenge2[];
        protected final String domain;
        protected byte lanManagerSessionKey[];
        protected byte lm2SessionResponse[];
        protected byte lmHash[];
        protected byte lmResponse[];
        protected byte lmUserSessionKey[];
        protected byte lmv2Hash[];
        protected byte lmv2Response[];
        protected byte ntlm2SessionResponse[];
        protected byte ntlm2SessionResponseUserSessionKey[];
        protected byte ntlmHash[];
        protected byte ntlmResponse[];
        protected byte ntlmUserSessionKey[];
        protected byte ntlmv2Blob[];
        protected byte ntlmv2Hash[];
        protected byte ntlmv2Response[];
        protected byte ntlmv2UserSessionKey[];
        protected final String password;
        protected byte secondaryKey[];
        protected final String target;
        protected final byte targetInformation[];
        protected byte timestamp[];
        protected final String user;

        public CipherGen(String s, String s1, String s2, byte abyte0[], String s3, byte abyte1[])
        {
            this(s, s1, s2, abyte0, s3, abyte1, null, null, null, null);
        }

        public CipherGen(String s, String s1, String s2, byte abyte0[], String s3, byte abyte1[], byte abyte2[], 
                byte abyte3[], byte abyte4[], byte abyte5[])
        {
            lmHash = null;
            lmResponse = null;
            ntlmHash = null;
            ntlmResponse = null;
            ntlmv2Hash = null;
            lmv2Hash = null;
            lmv2Response = null;
            ntlmv2Blob = null;
            ntlmv2Response = null;
            ntlm2SessionResponse = null;
            lm2SessionResponse = null;
            lmUserSessionKey = null;
            ntlmUserSessionKey = null;
            ntlmv2UserSessionKey = null;
            ntlm2SessionResponseUserSessionKey = null;
            lanManagerSessionKey = null;
            domain = s;
            target = s3;
            user = s1;
            password = s2;
            challenge = abyte0;
            targetInformation = abyte1;
            clientChallenge = abyte2;
            clientChallenge2 = abyte3;
            secondaryKey = abyte4;
            timestamp = abyte5;
        }
    }

    static class HMACMD5
    {

        byte[] getOutput()
        {
            byte abyte0[] = md5.digest();
            md5.update(opad);
            return md5.digest(abyte0);
        }

        void update(byte abyte0[])
        {
            md5.update(abyte0);
        }

        void update(byte abyte0[], int i, int j)
        {
            md5.update(abyte0, i, j);
        }

        protected byte ipad[];
        protected MessageDigest md5;
        protected byte opad[];

        HMACMD5(byte abyte0[])
            throws NTLMEngineException
        {
            byte abyte1[] = abyte0;
            int i;
            int j;
            try
            {
                md5 = MessageDigest.getInstance("MD5");
            }
            catch(Exception exception)
            {
                throw new NTLMEngineException((new StringBuilder()).append("Error getting md5 message digest implementation: ").append(exception.getMessage()).toString(), exception);
            }
            ipad = new byte[64];
            opad = new byte[64];
            i = abyte1.length;
            if(i > 64)
            {
                md5.update(abyte1);
                abyte1 = md5.digest();
                i = abyte1.length;
            }
            for(j = 0; j < i; j++)
            {
                ipad[j] = (byte)(0x36 ^ abyte1[j]);
                opad[j] = (byte)(0x5c ^ abyte1[j]);
            }

            for(; j < 64; j++)
            {
                ipad[j] = 54;
                opad[j] = 92;
            }

            md5.reset();
            md5.update(ipad);
        }
    }

    static class MD4
    {

        byte[] getOutput()
        {
            int i = (int)(63L & count);
            int j;
            byte abyte0[];
            if(i < 56)
                j = 56 - i;
            else
                j = 120 - i;
            abyte0 = new byte[j + 8];
            abyte0[0] = -128;
            for(int k = 0; k < 8; k++)
                abyte0[j + k] = (byte)(int)(8L * count >>> k * 8);

            update(abyte0);
            byte abyte1[] = new byte[16];
            NTLMEngineImpl.writeULong(abyte1, A, 0);
            NTLMEngineImpl.writeULong(abyte1, B, 4);
            NTLMEngineImpl.writeULong(abyte1, C, 8);
            NTLMEngineImpl.writeULong(abyte1, D, 12);
            return abyte1;
        }

        protected void processBuffer()
        {
            int ai[] = new int[16];
            for(int i = 0; i < 16; i++)
                ai[i] = (0xff & dataBuffer[i * 4]) + ((0xff & dataBuffer[1 + i * 4]) << 8) + ((0xff & dataBuffer[2 + i * 4]) << 16) + ((0xff & dataBuffer[3 + i * 4]) << 24);

            int j = A;
            int k = B;
            int l = C;
            int i1 = D;
            round1(ai);
            round2(ai);
            round3(ai);
            A = j + A;
            B = k + B;
            C = l + C;
            D = i1 + D;
        }

        protected void round1(int ai[])
        {
            A = NTLMEngineImpl.rotintlft(A + NTLMEngineImpl.F(B, C, D) + ai[0], 3);
            D = NTLMEngineImpl.rotintlft(D + NTLMEngineImpl.F(A, B, C) + ai[1], 7);
            C = NTLMEngineImpl.rotintlft(C + NTLMEngineImpl.F(D, A, B) + ai[2], 11);
            B = NTLMEngineImpl.rotintlft(B + NTLMEngineImpl.F(C, D, A) + ai[3], 19);
            A = NTLMEngineImpl.rotintlft(A + NTLMEngineImpl.F(B, C, D) + ai[4], 3);
            D = NTLMEngineImpl.rotintlft(D + NTLMEngineImpl.F(A, B, C) + ai[5], 7);
            C = NTLMEngineImpl.rotintlft(C + NTLMEngineImpl.F(D, A, B) + ai[6], 11);
            B = NTLMEngineImpl.rotintlft(B + NTLMEngineImpl.F(C, D, A) + ai[7], 19);
            A = NTLMEngineImpl.rotintlft(A + NTLMEngineImpl.F(B, C, D) + ai[8], 3);
            D = NTLMEngineImpl.rotintlft(D + NTLMEngineImpl.F(A, B, C) + ai[9], 7);
            C = NTLMEngineImpl.rotintlft(C + NTLMEngineImpl.F(D, A, B) + ai[10], 11);
            B = NTLMEngineImpl.rotintlft(B + NTLMEngineImpl.F(C, D, A) + ai[11], 19);
            A = NTLMEngineImpl.rotintlft(A + NTLMEngineImpl.F(B, C, D) + ai[12], 3);
            D = NTLMEngineImpl.rotintlft(D + NTLMEngineImpl.F(A, B, C) + ai[13], 7);
            C = NTLMEngineImpl.rotintlft(C + NTLMEngineImpl.F(D, A, B) + ai[14], 11);
            B = NTLMEngineImpl.rotintlft(B + NTLMEngineImpl.F(C, D, A) + ai[15], 19);
        }

        protected void round2(int ai[])
        {
            A = NTLMEngineImpl.rotintlft(0x5a827999 + (A + NTLMEngineImpl.G(B, C, D) + ai[0]), 3);
            D = NTLMEngineImpl.rotintlft(0x5a827999 + (D + NTLMEngineImpl.G(A, B, C) + ai[4]), 5);
            C = NTLMEngineImpl.rotintlft(0x5a827999 + (C + NTLMEngineImpl.G(D, A, B) + ai[8]), 9);
            B = NTLMEngineImpl.rotintlft(0x5a827999 + (B + NTLMEngineImpl.G(C, D, A) + ai[12]), 13);
            A = NTLMEngineImpl.rotintlft(0x5a827999 + (A + NTLMEngineImpl.G(B, C, D) + ai[1]), 3);
            D = NTLMEngineImpl.rotintlft(0x5a827999 + (D + NTLMEngineImpl.G(A, B, C) + ai[5]), 5);
            C = NTLMEngineImpl.rotintlft(0x5a827999 + (C + NTLMEngineImpl.G(D, A, B) + ai[9]), 9);
            B = NTLMEngineImpl.rotintlft(0x5a827999 + (B + NTLMEngineImpl.G(C, D, A) + ai[13]), 13);
            A = NTLMEngineImpl.rotintlft(0x5a827999 + (A + NTLMEngineImpl.G(B, C, D) + ai[2]), 3);
            D = NTLMEngineImpl.rotintlft(0x5a827999 + (D + NTLMEngineImpl.G(A, B, C) + ai[6]), 5);
            C = NTLMEngineImpl.rotintlft(0x5a827999 + (C + NTLMEngineImpl.G(D, A, B) + ai[10]), 9);
            B = NTLMEngineImpl.rotintlft(0x5a827999 + (B + NTLMEngineImpl.G(C, D, A) + ai[14]), 13);
            A = NTLMEngineImpl.rotintlft(0x5a827999 + (A + NTLMEngineImpl.G(B, C, D) + ai[3]), 3);
            D = NTLMEngineImpl.rotintlft(0x5a827999 + (D + NTLMEngineImpl.G(A, B, C) + ai[7]), 5);
            C = NTLMEngineImpl.rotintlft(0x5a827999 + (C + NTLMEngineImpl.G(D, A, B) + ai[11]), 9);
            B = NTLMEngineImpl.rotintlft(0x5a827999 + (B + NTLMEngineImpl.G(C, D, A) + ai[15]), 13);
        }

        protected void round3(int ai[])
        {
            A = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (A + NTLMEngineImpl.H(B, C, D) + ai[0]), 3);
            D = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (D + NTLMEngineImpl.H(A, B, C) + ai[8]), 9);
            C = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (C + NTLMEngineImpl.H(D, A, B) + ai[4]), 11);
            B = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (B + NTLMEngineImpl.H(C, D, A) + ai[12]), 15);
            A = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (A + NTLMEngineImpl.H(B, C, D) + ai[2]), 3);
            D = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (D + NTLMEngineImpl.H(A, B, C) + ai[10]), 9);
            C = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (C + NTLMEngineImpl.H(D, A, B) + ai[6]), 11);
            B = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (B + NTLMEngineImpl.H(C, D, A) + ai[14]), 15);
            A = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (A + NTLMEngineImpl.H(B, C, D) + ai[1]), 3);
            D = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (D + NTLMEngineImpl.H(A, B, C) + ai[9]), 9);
            C = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (C + NTLMEngineImpl.H(D, A, B) + ai[5]), 11);
            B = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (B + NTLMEngineImpl.H(C, D, A) + ai[13]), 15);
            A = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (A + NTLMEngineImpl.H(B, C, D) + ai[3]), 3);
            D = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (D + NTLMEngineImpl.H(A, B, C) + ai[11]), 9);
            C = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (C + NTLMEngineImpl.H(D, A, B) + ai[7]), 11);
            B = NTLMEngineImpl.rotintlft(0x6ed9eba1 + (B + NTLMEngineImpl.H(C, D, A) + ai[15]), 15);
        }

        void update(byte abyte0[])
        {
            int i = (int)(63L & count);
            int j;
            for(j = 0; i + (abyte0.length - j) >= dataBuffer.length; i = 0)
            {
                int l = dataBuffer.length - i;
                System.arraycopy(abyte0, j, dataBuffer, i, l);
                count = count + (long)l;
                j += l;
                processBuffer();
            }

            if(j < abyte0.length)
            {
                int k = abyte0.length - j;
                System.arraycopy(abyte0, j, dataBuffer, i, k);
                count = count + (long)k;
                int _tmp = i + k;
            }
        }

        protected int A;
        protected int B;
        protected int C;
        protected int D;
        protected long count;
        protected byte dataBuffer[];

        MD4()
        {
            A = 0x67452301;
            B = 0xefcdab89;
            C = 0x98badcfe;
            D = 0x10325476;
            count = 0L;
            dataBuffer = new byte[64];
        }
    }

    static class NTLMMessage
    {

        protected void addByte(byte byte0)
        {
            messageContents[currentOutputPosition] = byte0;
            currentOutputPosition = 1 + currentOutputPosition;
        }

        protected void addBytes(byte abyte0[])
        {
            if(abyte0 != null)
            {
                int i = abyte0.length;
                int j = 0;
                while(j < i) 
                {
                    byte byte0 = abyte0[j];
                    messageContents[currentOutputPosition] = byte0;
                    currentOutputPosition = 1 + currentOutputPosition;
                    j++;
                }
            }
        }

        protected void addULong(int i)
        {
            addByte((byte)(i & 0xff));
            addByte((byte)(0xff & i >> 8));
            addByte((byte)(0xff & i >> 16));
            addByte((byte)(0xff & i >> 24));
        }

        protected void addUShort(int i)
        {
            addByte((byte)(i & 0xff));
            addByte((byte)(0xff & i >> 8));
        }

        protected int getMessageLength()
        {
            return currentOutputPosition;
        }

        protected int getPreambleLength()
        {
            return 4 + NTLMEngineImpl.SIGNATURE.length;
        }

        String getResponse()
        {
            byte abyte0[];
            if(messageContents.length > currentOutputPosition)
            {
                byte abyte1[] = new byte[currentOutputPosition];
                System.arraycopy(messageContents, 0, abyte1, 0, currentOutputPosition);
                abyte0 = abyte1;
            } else
            {
                abyte0 = messageContents;
            }
            return EncodingUtils.getAsciiString(Base64.encodeBase64(abyte0));
        }

        protected void prepareResponse(int i, int j)
        {
            messageContents = new byte[i];
            currentOutputPosition = 0;
            addBytes(NTLMEngineImpl.SIGNATURE);
            addULong(j);
        }

        protected byte readByte(int i)
            throws NTLMEngineException
        {
            if(messageContents.length < i + 1)
                throw new NTLMEngineException("NTLM: Message too short");
            else
                return messageContents[i];
        }

        protected void readBytes(byte abyte0[], int i)
            throws NTLMEngineException
        {
            if(messageContents.length < i + abyte0.length)
            {
                throw new NTLMEngineException("NTLM: Message too short");
            } else
            {
                System.arraycopy(messageContents, i, abyte0, 0, abyte0.length);
                return;
            }
        }

        protected byte[] readSecurityBuffer(int i)
            throws NTLMEngineException
        {
            return NTLMEngineImpl.readSecurityBuffer(messageContents, i);
        }

        protected int readULong(int i)
            throws NTLMEngineException
        {
            return NTLMEngineImpl.readULong(messageContents, i);
        }

        protected int readUShort(int i)
            throws NTLMEngineException
        {
            return NTLMEngineImpl.readUShort(messageContents, i);
        }

        private int currentOutputPosition;
        private byte messageContents[];

        NTLMMessage()
        {
            messageContents = null;
            currentOutputPosition = 0;
        }

        NTLMMessage(String s, int i)
            throws NTLMEngineException
        {
            messageContents = null;
            currentOutputPosition = 0;
            messageContents = Base64.decodeBase64(EncodingUtils.getBytes(s, "ASCII"));
            if(messageContents.length < NTLMEngineImpl.SIGNATURE.length)
                throw new NTLMEngineException("NTLM message decoding error - packet too short");
            for(int j = 0; j < NTLMEngineImpl.SIGNATURE.length; j++)
                if(messageContents[j] != NTLMEngineImpl.SIGNATURE[j])
                    throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");

            int k = readULong(NTLMEngineImpl.SIGNATURE.length);
            if(k != i)
            {
                throw new NTLMEngineException((new StringBuilder()).append("NTLM type ").append(Integer.toString(i)).append(" message expected - instead got type ").append(Integer.toString(k)).toString());
            } else
            {
                currentOutputPosition = messageContents.length;
                return;
            }
        }
    }

    static class Type1Message extends NTLMMessage
    {

        String getResponse()
        {
            prepareResponse(40, 1);
            addULong(0xa2088201);
            addUShort(0);
            addUShort(0);
            addULong(40);
            addUShort(0);
            addUShort(0);
            addULong(40);
            addUShort(261);
            addULong(2600);
            addUShort(3840);
            return super.getResponse();
        }

        protected byte domainBytes[];
        protected byte hostBytes[];

        Type1Message(String s, String s1)
            throws NTLMEngineException
        {
            String s2;
            String s3;
            byte abyte0[];
            byte abyte1[];
            try
            {
                s2 = NTLMEngineImpl.convertHost(s1);
                s3 = NTLMEngineImpl.convertDomain(s);
            }
            catch(UnsupportedEncodingException unsupportedencodingexception)
            {
                throw new NTLMEngineException((new StringBuilder()).append("Unicode unsupported: ").append(unsupportedencodingexception.getMessage()).toString(), unsupportedencodingexception);
            }
            if(s2 == null)
                break MISSING_BLOCK_LABEL_66;
            abyte0 = s2.getBytes("ASCII");
_L1:
            hostBytes = abyte0;
            abyte1 = null;
            if(s3 == null)
                break MISSING_BLOCK_LABEL_59;
            abyte1 = s3.toUpperCase(Locale.US).getBytes("ASCII");
            domainBytes = abyte1;
            return;
            abyte0 = null;
              goto _L1
        }
    }

    static class Type2Message extends NTLMMessage
    {

        byte[] getChallenge()
        {
            return challenge;
        }

        int getFlags()
        {
            return flags;
        }

        String getTarget()
        {
            return target;
        }

        byte[] getTargetInfo()
        {
            return targetInfo;
        }

        protected byte challenge[];
        protected int flags;
        protected String target;
        protected byte targetInfo[];

        Type2Message(String s)
            throws NTLMEngineException
        {
            super(s, 2);
            challenge = new byte[8];
            readBytes(challenge, 24);
            flags = readULong(20);
            if((1 & flags) == 0)
                throw new NTLMEngineException((new StringBuilder()).append("NTLM type 2 message has flags that make no sense: ").append(Integer.toString(flags)).toString());
            target = null;
            if(getMessageLength() >= 20)
            {
                byte abyte1[] = readSecurityBuffer(12);
                byte abyte0[];
                if(abyte1.length != 0)
                    try
                    {
                        target = new String(abyte1, "UnicodeLittleUnmarked");
                    }
                    catch(UnsupportedEncodingException unsupportedencodingexception)
                    {
                        throw new NTLMEngineException(unsupportedencodingexception.getMessage(), unsupportedencodingexception);
                    }
            }
            targetInfo = null;
            if(getMessageLength() >= 48)
            {
                abyte0 = readSecurityBuffer(40);
                if(abyte0.length != 0)
                    targetInfo = abyte0;
            }
        }
    }

    static class Type3Message extends NTLMMessage
    {

        String getResponse()
        {
            int i = ntResp.length;
            int j = lmResp.length;
            int k;
            int l;
            int i1;
            int j1;
            int k1;
            int l1;
            int i2;
            int j2;
            int k2;
            if(domainBytes != null)
                k = domainBytes.length;
            else
                k = 0;
            if(hostBytes != null)
                l = hostBytes.length;
            else
                l = 0;
            i1 = userBytes.length;
            if(sessionKey != null)
                j1 = sessionKey.length;
            else
                j1 = 0;
            k1 = j + 72;
            l1 = k1 + i;
            i2 = l1 + k;
            j2 = i2 + i1;
            k2 = j2 + l;
            prepareResponse(k2 + j1, 3);
            addUShort(j);
            addUShort(j);
            addULong(72);
            addUShort(i);
            addUShort(i);
            addULong(k1);
            addUShort(k);
            addUShort(k);
            addULong(l1);
            addUShort(i1);
            addUShort(i1);
            addULong(i2);
            addUShort(l);
            addUShort(l);
            addULong(j2);
            addUShort(j1);
            addUShort(j1);
            addULong(k2);
            addULong(0x2000000 | (0x80 & type2Flags | 0x200 & type2Flags | 0x80000 & type2Flags) | 0x8000 & type2Flags | 0x20 & type2Flags | 0x10 & type2Flags | 0x20000000 & type2Flags | 0x80000000 & type2Flags | 0x40000000 & type2Flags | 0x800000 & type2Flags | 1 & type2Flags | 4 & type2Flags);
            addUShort(261);
            addULong(2600);
            addUShort(3840);
            addBytes(lmResp);
            addBytes(ntResp);
            addBytes(domainBytes);
            addBytes(userBytes);
            addBytes(hostBytes);
            if(sessionKey != null)
                addBytes(sessionKey);
            return super.getResponse();
        }

        protected byte domainBytes[];
        protected byte hostBytes[];
        protected byte lmResp[];
        protected byte ntResp[];
        protected byte sessionKey[];
        protected int type2Flags;
        protected byte userBytes[];

        Type3Message(String s, String s1, String s2, String s3, byte abyte0[], int i, String s4, 
                byte abyte1[])
            throws NTLMEngineException
        {
            String s5;
            String s6;
            CipherGen ciphergen;
            type2Flags = i;
            s5 = NTLMEngineImpl.convertHost(s1);
            s6 = NTLMEngineImpl.convertDomain(s);
            ciphergen = new CipherGen(s6, s2, s3, abyte0, s4, abyte1);
            if((0x800000 & i) == 0 || abyte1 == null || s4 == null) goto _L2; else goto _L1
_L1:
            ntResp = ciphergen.getNTLMv2Response();
            lmResp = ciphergen.getLMv2Response();
            if((i & 0x80) == 0) goto _L4; else goto _L3
_L3:
            byte abyte6[] = ciphergen.getLanManagerSessionKey();
            byte abyte2[] = abyte6;
_L9:
            byte abyte3[];
            byte abyte4[];
            byte abyte5[];
            if((i & 0x10) != 0)
            {
                if((0x40000000 & i) != 0)
                    sessionKey = NTLMEngineImpl.RC4(ciphergen.getSecondaryKey(), abyte2);
                else
                    sessionKey = abyte2;
            } else
            {
                sessionKey = null;
            }
            if(s5 == null) goto _L6; else goto _L5
_L5:
            abyte3 = s5.getBytes("UnicodeLittleUnmarked");
_L10:
            hostBytes = abyte3;
            if(s6 == null) goto _L8; else goto _L7
_L7:
            abyte4 = s6.toUpperCase(Locale.US).getBytes("UnicodeLittleUnmarked");
_L11:
            domainBytes = abyte4;
            userBytes = s2.getBytes("UnicodeLittleUnmarked");
            return;
_L4:
            try
            {
                abyte2 = ciphergen.getNTLMv2UserSessionKey();
            }
            catch(NTLMEngineException ntlmengineexception)
            {
                ntResp = new byte[0];
                lmResp = ciphergen.getLMResponse();
                if((i & 0x80) != 0)
                    abyte2 = ciphergen.getLanManagerSessionKey();
                else
                    abyte2 = ciphergen.getLMUserSessionKey();
            }
              goto _L9
_L2:
            if((0x80000 & i) == 0)
                break MISSING_BLOCK_LABEL_250;
            ntResp = ciphergen.getNTLM2SessionResponse();
            lmResp = ciphergen.getLM2SessionResponse();
            if((i & 0x80) == 0)
                break MISSING_BLOCK_LABEL_240;
            abyte2 = ciphergen.getLanManagerSessionKey();
              goto _L9
            abyte2 = ciphergen.getNTLM2SessionResponseUserSessionKey();
              goto _L9
            ntResp = ciphergen.getNTLMResponse();
            lmResp = ciphergen.getLMResponse();
            if((i & 0x80) == 0)
                break MISSING_BLOCK_LABEL_287;
            abyte2 = ciphergen.getLanManagerSessionKey();
              goto _L9
            abyte5 = ciphergen.getNTLMUserSessionKey();
            abyte2 = abyte5;
              goto _L9
_L6:
            abyte3 = null;
              goto _L10
_L8:
            abyte4 = null;
              goto _L11
            UnsupportedEncodingException unsupportedencodingexception;
            unsupportedencodingexception;
            throw new NTLMEngineException((new StringBuilder()).append("Unicode not supported: ").append(unsupportedencodingexception.getMessage()).toString(), unsupportedencodingexception);
              goto _L10
        }
    }


    NTLMEngineImpl()
    {
        credentialCharset = "ASCII";
    }

    static int F(int i, int j, int k)
    {
        return i & j | k & ~i;
    }

    static int G(int i, int j, int k)
    {
        return i & j | i & k | j & k;
    }

    static int H(int i, int j, int k)
    {
        return k ^ (i ^ j);
    }

    static byte[] RC4(byte abyte0[], byte abyte1[])
        throws NTLMEngineException
    {
        byte abyte2[];
        try
        {
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(1, new SecretKeySpec(abyte1, "RC4"));
            abyte2 = cipher.doFinal(abyte0);
        }
        catch(Exception exception)
        {
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
        return abyte2;
    }

    private static String convertDomain(String s)
    {
        return stripDotSuffix(s);
    }

    private static String convertHost(String s)
    {
        return stripDotSuffix(s);
    }

    private static byte[] createBlob(byte abyte0[], byte abyte1[], byte abyte2[])
    {
        byte abyte3[] = {
            1, 1, 0, 0
        };
        byte abyte4[] = {
            0, 0, 0, 0
        };
        byte abyte5[] = {
            0, 0, 0, 0
        };
        byte abyte6[] = {
            0, 0, 0, 0
        };
        byte abyte7[] = new byte[8 + (abyte3.length + abyte4.length + abyte2.length) + abyte5.length + abyte1.length + abyte6.length];
        System.arraycopy(abyte3, 0, abyte7, 0, abyte3.length);
        int i = 0 + abyte3.length;
        System.arraycopy(abyte4, 0, abyte7, i, abyte4.length);
        int j = i + abyte4.length;
        System.arraycopy(abyte2, 0, abyte7, j, abyte2.length);
        int k = j + abyte2.length;
        System.arraycopy(abyte0, 0, abyte7, k, 8);
        int l = k + 8;
        System.arraycopy(abyte5, 0, abyte7, l, abyte5.length);
        int i1 = l + abyte5.length;
        System.arraycopy(abyte1, 0, abyte7, i1, abyte1.length);
        int j1 = i1 + abyte1.length;
        System.arraycopy(abyte6, 0, abyte7, j1, abyte6.length);
        int _tmp = j1 + abyte6.length;
        return abyte7;
    }

    private static Key createDESKey(byte abyte0[], int i)
    {
        byte abyte1[] = new byte[7];
        System.arraycopy(abyte0, i, abyte1, 0, 7);
        byte abyte2[] = new byte[8];
        abyte2[0] = abyte1[0];
        abyte2[1] = (byte)(abyte1[0] << 7 | (0xff & abyte1[1]) >>> 1);
        abyte2[2] = (byte)(abyte1[1] << 6 | (0xff & abyte1[2]) >>> 2);
        abyte2[3] = (byte)(abyte1[2] << 5 | (0xff & abyte1[3]) >>> 3);
        abyte2[4] = (byte)(abyte1[3] << 4 | (0xff & abyte1[4]) >>> 4);
        abyte2[5] = (byte)(abyte1[4] << 3 | (0xff & abyte1[5]) >>> 5);
        abyte2[6] = (byte)(abyte1[5] << 2 | (0xff & abyte1[6]) >>> 6);
        abyte2[7] = (byte)(abyte1[6] << 1);
        oddParity(abyte2);
        return new SecretKeySpec(abyte2, "DES");
    }

    static byte[] hmacMD5(byte abyte0[], byte abyte1[])
        throws NTLMEngineException
    {
        HMACMD5 hmacmd5 = new HMACMD5(abyte1);
        hmacmd5.update(abyte0);
        return hmacmd5.getOutput();
    }

    private static byte[] lmHash(String s)
        throws NTLMEngineException
    {
        byte abyte5[];
        try
        {
            byte abyte0[] = s.toUpperCase(Locale.US).getBytes("US-ASCII");
            int i = Math.min(abyte0.length, 14);
            byte abyte1[] = new byte[14];
            System.arraycopy(abyte0, 0, abyte1, 0, i);
            Key key = createDESKey(abyte1, 0);
            Key key1 = createDESKey(abyte1, 7);
            byte abyte2[] = "KGS!@#$%".getBytes("US-ASCII");
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, key);
            byte abyte3[] = cipher.doFinal(abyte2);
            cipher.init(1, key1);
            byte abyte4[] = cipher.doFinal(abyte2);
            abyte5 = new byte[16];
            System.arraycopy(abyte3, 0, abyte5, 0, 8);
            System.arraycopy(abyte4, 0, abyte5, 8, 8);
        }
        catch(Exception exception)
        {
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
        return abyte5;
    }

    private static byte[] lmResponse(byte abyte0[], byte abyte1[])
        throws NTLMEngineException
    {
        byte abyte6[];
        try
        {
            byte abyte2[] = new byte[21];
            System.arraycopy(abyte0, 0, abyte2, 0, 16);
            Key key = createDESKey(abyte2, 0);
            Key key1 = createDESKey(abyte2, 7);
            Key key2 = createDESKey(abyte2, 14);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, key);
            byte abyte3[] = cipher.doFinal(abyte1);
            cipher.init(1, key1);
            byte abyte4[] = cipher.doFinal(abyte1);
            cipher.init(1, key2);
            byte abyte5[] = cipher.doFinal(abyte1);
            abyte6 = new byte[24];
            System.arraycopy(abyte3, 0, abyte6, 0, 8);
            System.arraycopy(abyte4, 0, abyte6, 8, 8);
            System.arraycopy(abyte5, 0, abyte6, 16, 8);
        }
        catch(Exception exception)
        {
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
        return abyte6;
    }

    private static byte[] lmv2Hash(String s, String s1, byte abyte0[])
        throws NTLMEngineException
    {
        HMACMD5 hmacmd5;
        byte abyte1[];
        try
        {
            hmacmd5 = new HMACMD5(abyte0);
            hmacmd5.update(s1.toUpperCase(Locale.US).getBytes("UnicodeLittleUnmarked"));
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new NTLMEngineException((new StringBuilder()).append("Unicode not supported! ").append(unsupportedencodingexception.getMessage()).toString(), unsupportedencodingexception);
        }
        if(s == null)
            break MISSING_BLOCK_LABEL_45;
        hmacmd5.update(s.toUpperCase(Locale.US).getBytes("UnicodeLittleUnmarked"));
        abyte1 = hmacmd5.getOutput();
        return abyte1;
    }

    private static byte[] lmv2Response(byte abyte0[], byte abyte1[], byte abyte2[])
        throws NTLMEngineException
    {
        HMACMD5 hmacmd5 = new HMACMD5(abyte0);
        hmacmd5.update(abyte1);
        hmacmd5.update(abyte2);
        byte abyte3[] = hmacmd5.getOutput();
        byte abyte4[] = new byte[abyte3.length + abyte2.length];
        System.arraycopy(abyte3, 0, abyte4, 0, abyte3.length);
        System.arraycopy(abyte2, 0, abyte4, abyte3.length, abyte2.length);
        return abyte4;
    }

    private static byte[] makeRandomChallenge()
        throws NTLMEngineException
    {
        if(RND_GEN == null)
            throw new NTLMEngineException("Random generator not available");
        byte abyte0[] = new byte[8];
        synchronized(RND_GEN)
        {
            RND_GEN.nextBytes(abyte0);
        }
        return abyte0;
        exception;
        securerandom;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private static byte[] makeSecondaryKey()
        throws NTLMEngineException
    {
        if(RND_GEN == null)
            throw new NTLMEngineException("Random generator not available");
        byte abyte0[] = new byte[16];
        synchronized(RND_GEN)
        {
            RND_GEN.nextBytes(abyte0);
        }
        return abyte0;
        exception;
        securerandom;
        JVM INSTR monitorexit ;
        throw exception;
    }

    static byte[] ntlm2SessionResponse(byte abyte0[], byte abyte1[], byte abyte2[])
        throws NTLMEngineException
    {
        byte abyte5[];
        try
        {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(abyte1);
            messagedigest.update(abyte2);
            byte abyte3[] = messagedigest.digest();
            byte abyte4[] = new byte[8];
            System.arraycopy(abyte3, 0, abyte4, 0, 8);
            abyte5 = lmResponse(abyte0, abyte4);
        }
        catch(Exception exception)
        {
            if(exception instanceof NTLMEngineException)
                throw (NTLMEngineException)exception;
            else
                throw new NTLMEngineException(exception.getMessage(), exception);
        }
        return abyte5;
    }

    private static byte[] ntlmHash(String s)
        throws NTLMEngineException
    {
        byte abyte1[];
        try
        {
            byte abyte0[] = s.getBytes("UnicodeLittleUnmarked");
            MD4 md4 = new MD4();
            md4.update(abyte0);
            abyte1 = md4.getOutput();
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new NTLMEngineException((new StringBuilder()).append("Unicode not supported: ").append(unsupportedencodingexception.getMessage()).toString(), unsupportedencodingexception);
        }
        return abyte1;
    }

    private static byte[] ntlmv2Hash(String s, String s1, byte abyte0[])
        throws NTLMEngineException
    {
        HMACMD5 hmacmd5;
        byte abyte1[];
        try
        {
            hmacmd5 = new HMACMD5(abyte0);
            hmacmd5.update(s1.toUpperCase(Locale.US).getBytes("UnicodeLittleUnmarked"));
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new NTLMEngineException((new StringBuilder()).append("Unicode not supported! ").append(unsupportedencodingexception.getMessage()).toString(), unsupportedencodingexception);
        }
        if(s == null)
            break MISSING_BLOCK_LABEL_39;
        hmacmd5.update(s.getBytes("UnicodeLittleUnmarked"));
        abyte1 = hmacmd5.getOutput();
        return abyte1;
    }

    private static void oddParity(byte abyte0[])
    {
        int i = 0;
        while(i < abyte0.length) 
        {
            byte byte0 = abyte0[i];
            boolean flag;
            if((1 & (byte0 >>> 7 ^ byte0 >>> 6 ^ byte0 >>> 5 ^ byte0 >>> 4 ^ byte0 >>> 3 ^ byte0 >>> 2 ^ byte0 >>> 1)) == 0)
                flag = true;
            else
                flag = false;
            if(flag)
                abyte0[i] = (byte)(1 | abyte0[i]);
            else
                abyte0[i] = (byte)(-2 & abyte0[i]);
            i++;
        }
    }

    private static byte[] readSecurityBuffer(byte abyte0[], int i)
        throws NTLMEngineException
    {
        int j = readUShort(abyte0, i);
        int k = readULong(abyte0, i + 4);
        if(abyte0.length < k + j)
        {
            throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
        } else
        {
            byte abyte1[] = new byte[j];
            System.arraycopy(abyte0, k, abyte1, 0, j);
            return abyte1;
        }
    }

    private static int readULong(byte abyte0[], int i)
        throws NTLMEngineException
    {
        if(abyte0.length < i + 4)
            throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
        else
            return 0xff & abyte0[i] | (0xff & abyte0[i + 1]) << 8 | (0xff & abyte0[i + 2]) << 16 | (0xff & abyte0[i + 3]) << 24;
    }

    private static int readUShort(byte abyte0[], int i)
        throws NTLMEngineException
    {
        if(abyte0.length < i + 2)
            throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
        else
            return 0xff & abyte0[i] | (0xff & abyte0[i + 1]) << 8;
    }

    static int rotintlft(int i, int j)
    {
        return i << j | i >>> 32 - j;
    }

    private static String stripDotSuffix(String s)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            int i = s.indexOf(".");
            if(i != -1)
                return s.substring(0, i);
        }
        return s;
    }

    static void writeULong(byte abyte0[], int i, int j)
    {
        abyte0[j] = (byte)(i & 0xff);
        abyte0[j + 1] = (byte)(0xff & i >> 8);
        abyte0[j + 2] = (byte)(0xff & i >> 16);
        abyte0[j + 3] = (byte)(0xff & i >> 24);
    }

    public String generateType1Msg(String s, String s1)
        throws NTLMEngineException
    {
        return getType1Message(s1, s);
    }

    public String generateType3Msg(String s, String s1, String s2, String s3, String s4)
        throws NTLMEngineException
    {
        Type2Message type2message = new Type2Message(s4);
        return getType3Message(s, s1, s3, s2, type2message.getChallenge(), type2message.getFlags(), type2message.getTarget(), type2message.getTargetInfo());
    }

    String getCredentialCharset()
    {
        return credentialCharset;
    }

    final String getResponseFor(String s, String s1, String s2, String s3, String s4)
        throws NTLMEngineException
    {
        if(s == null || s.trim().equals(""))
        {
            return getType1Message(s3, s4);
        } else
        {
            Type2Message type2message = new Type2Message(s);
            return getType3Message(s1, s2, s3, s4, type2message.getChallenge(), type2message.getFlags(), type2message.getTarget(), type2message.getTargetInfo());
        }
    }

    String getType1Message(String s, String s1)
        throws NTLMEngineException
    {
        return (new Type1Message(s1, s)).getResponse();
    }

    String getType3Message(String s, String s1, String s2, String s3, byte abyte0[], int i, String s4, 
            byte abyte1[])
        throws NTLMEngineException
    {
        return (new Type3Message(s3, s2, s, s1, abyte0, i, s4, abyte1)).getResponse();
    }

    void setCredentialCharset(String s)
    {
        credentialCharset = s;
    }

    static final String DEFAULT_CHARSET = "ASCII";
    protected static final int FLAG_DOMAIN_PRESENT = 4096;
    protected static final int FLAG_REQUEST_128BIT_KEY_EXCH = 0x20000000;
    protected static final int FLAG_REQUEST_56BIT_ENCRYPTION = 0x80000000;
    protected static final int FLAG_REQUEST_ALWAYS_SIGN = 32768;
    protected static final int FLAG_REQUEST_EXPLICIT_KEY_EXCH = 0x40000000;
    protected static final int FLAG_REQUEST_LAN_MANAGER_KEY = 128;
    protected static final int FLAG_REQUEST_NTLM2_SESSION = 0x80000;
    protected static final int FLAG_REQUEST_NTLMv1 = 512;
    protected static final int FLAG_REQUEST_SEAL = 32;
    protected static final int FLAG_REQUEST_SIGN = 16;
    protected static final int FLAG_REQUEST_TARGET = 4;
    protected static final int FLAG_REQUEST_UNICODE_ENCODING = 1;
    protected static final int FLAG_REQUEST_VERSION = 0x2000000;
    protected static final int FLAG_TARGETINFO_PRESENT = 0x800000;
    protected static final int FLAG_WORKSTATION_PRESENT = 8192;
    private static final SecureRandom RND_GEN;
    private static final byte SIGNATURE[];
    private String credentialCharset;

    static 
    {
        SecureRandom securerandom1 = SecureRandom.getInstance("SHA1PRNG");
        SecureRandom securerandom = securerandom1;
_L2:
        RND_GEN = securerandom;
        byte abyte0[] = EncodingUtils.getBytes("NTLMSSP", "ASCII");
        SIGNATURE = new byte[1 + abyte0.length];
        System.arraycopy(abyte0, 0, SIGNATURE, 0, abyte0.length);
        SIGNATURE[abyte0.length] = 0;
        return;
        Exception exception;
        exception;
        securerandom = null;
        if(true) goto _L2; else goto _L1
_L1:
    }
















}
