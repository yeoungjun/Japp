// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.bluebox.lab.poc;

import 0458ava.lang.String;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;

public class Action extends Activity
{

    public Action()
    {
        error = "\u0488\u08D5\n\r\u0ABFasdfghjk";
        codeword = new String(mix(code));
    }

    private static byte[] mix(byte abyte0[])
    {
        byte abyte1[] = new byte[abyte0.length];
        int i = 0;
        do
        {
            if(i >= abyte0.length)
                return abyte1;
            abyte1[i] = (byte)(5 ^ abyte0[i]);
            i++;
        } while(true);
    }

    private static native int readmem();

    private int summo(String s)
    {
        int i = 0;
        byte abyte0[] = s.getBytes();
        int j = abyte0.length;
        int k = 0;
        do
        {
            if(i >= j)
                return k;
            k += abyte0[i];
            i++;
        } while(true);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030000);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f060000, menu);
        return true;
    }

    public void verify(View view)
    {
        TextView textview;
        EditText edittext;
        String s;
        String s1;
        int i;
        try
        {
            textview = (TextView)findViewById(0x7f070004);
            edittext = (EditText)findViewById(0x7f070003);
            s = new String(edittext.getText().toString());
            textview.setText(s.toString());
            if(summo(s) != 1704)
            {
                textview.setText("The difference confuses me.");
                return;
            }
        }
        catch(Exception exception)
        {
            (TextView)findViewById(0x7f070004);
            return;
        }
        if(!codeword.startsWith(s.toString()) || s.toString().length() <= 20) goto _L2; else goto _L1
_L1:
        s1 = new String("There you go. ");
        i = 0;
_L3:
        if(i >= msg.length)
        {
            textview.setText(s1);
            return;
        }
        int j = msg[i] ^ edittext.getText().charAt(i % edittext.getText().length());
        s1 = (new StringBuilder(String.valueOf(s1))).append((char)j).toString();
        i++;
        if(true) goto _L3; else goto _L2
_L2:
        textview.setText((new StringBuilder("The difference confuses me.")).append(s.toString()).toString());
        return;
    }

    private byte code[] = {
        87, 78, 66, 72, 78, 68, 84, 64, 66, 79, 
        68, 81, 66, 68, 83, 82, 68, 71, 79, 78, 
        66, 81, 66, 83, 90, 85, 76, 73, 85, 73
    };
    private String codeword;
    private String error;
    private byte msg[] = {
        13, 39, 48, 99, 47, 46, 58, 103, 44, 39, 
        109, 114, 38, 46, 34, 48, 51, 56, 39, 59, 
        107
    };

    static 
    {
        System.loadLibrary("net");
        readmem();
    }
}
