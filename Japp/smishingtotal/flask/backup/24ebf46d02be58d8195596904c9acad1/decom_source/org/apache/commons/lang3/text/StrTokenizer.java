// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text;

import java.util.*;
import org.apache.commons.lang3.ArrayUtils;

// Referenced classes of package org.apache.commons.lang3.text:
//            StrMatcher, StrBuilder

public class StrTokenizer
    implements ListIterator, Cloneable
{

    public StrTokenizer()
    {
        delimMatcher = StrMatcher.splitMatcher();
        quoteMatcher = StrMatcher.noneMatcher();
        ignoredMatcher = StrMatcher.noneMatcher();
        trimmerMatcher = StrMatcher.noneMatcher();
        emptyAsNull = false;
        ignoreEmptyTokens = true;
        chars = null;
    }

    public StrTokenizer(String s)
    {
        delimMatcher = StrMatcher.splitMatcher();
        quoteMatcher = StrMatcher.noneMatcher();
        ignoredMatcher = StrMatcher.noneMatcher();
        trimmerMatcher = StrMatcher.noneMatcher();
        emptyAsNull = false;
        ignoreEmptyTokens = true;
        if(s != null)
        {
            chars = s.toCharArray();
            return;
        } else
        {
            chars = null;
            return;
        }
    }

    public StrTokenizer(String s, char c)
    {
        this(s);
        setDelimiterChar(c);
    }

    public StrTokenizer(String s, char c, char c1)
    {
        this(s, c);
        setQuoteChar(c1);
    }

    public StrTokenizer(String s, String s1)
    {
        this(s);
        setDelimiterString(s1);
    }

    public StrTokenizer(String s, StrMatcher strmatcher)
    {
        this(s);
        setDelimiterMatcher(strmatcher);
    }

    public StrTokenizer(String s, StrMatcher strmatcher, StrMatcher strmatcher1)
    {
        this(s, strmatcher);
        setQuoteMatcher(strmatcher1);
    }

    public StrTokenizer(char ac[])
    {
        delimMatcher = StrMatcher.splitMatcher();
        quoteMatcher = StrMatcher.noneMatcher();
        ignoredMatcher = StrMatcher.noneMatcher();
        trimmerMatcher = StrMatcher.noneMatcher();
        emptyAsNull = false;
        ignoreEmptyTokens = true;
        chars = ArrayUtils.clone(ac);
    }

    public StrTokenizer(char ac[], char c)
    {
        this(ac);
        setDelimiterChar(c);
    }

    public StrTokenizer(char ac[], char c, char c1)
    {
        this(ac, c);
        setQuoteChar(c1);
    }

    public StrTokenizer(char ac[], String s)
    {
        this(ac);
        setDelimiterString(s);
    }

    public StrTokenizer(char ac[], StrMatcher strmatcher)
    {
        this(ac);
        setDelimiterMatcher(strmatcher);
    }

    public StrTokenizer(char ac[], StrMatcher strmatcher, StrMatcher strmatcher1)
    {
        this(ac, strmatcher);
        setQuoteMatcher(strmatcher1);
    }

    private void addToken(List list, String s)
    {
        if(s == null || s.length() == 0)
        {
            if(isIgnoreEmptyTokens())
                return;
            if(isEmptyTokenAsNull())
                s = null;
        }
        list.add(s);
    }

    private void checkTokenized()
    {
label0:
        {
            if(tokens == null)
            {
                if(chars != null)
                    break label0;
                List list1 = tokenize(null, 0, 0);
                tokens = (String[])list1.toArray(new String[list1.size()]);
            }
            return;
        }
        List list = tokenize(chars, 0, chars.length);
        tokens = (String[])list.toArray(new String[list.size()]);
    }

    private static StrTokenizer getCSVClone()
    {
        return (StrTokenizer)CSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getCSVInstance()
    {
        return getCSVClone();
    }

    public static StrTokenizer getCSVInstance(String s)
    {
        StrTokenizer strtokenizer = getCSVClone();
        strtokenizer.reset(s);
        return strtokenizer;
    }

    public static StrTokenizer getCSVInstance(char ac[])
    {
        StrTokenizer strtokenizer = getCSVClone();
        strtokenizer.reset(ac);
        return strtokenizer;
    }

    private static StrTokenizer getTSVClone()
    {
        return (StrTokenizer)TSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getTSVInstance()
    {
        return getTSVClone();
    }

    public static StrTokenizer getTSVInstance(String s)
    {
        StrTokenizer strtokenizer = getTSVClone();
        strtokenizer.reset(s);
        return strtokenizer;
    }

    public static StrTokenizer getTSVInstance(char ac[])
    {
        StrTokenizer strtokenizer = getTSVClone();
        strtokenizer.reset(ac);
        return strtokenizer;
    }

    private boolean isQuote(char ac[], int i, int j, int k, int l)
    {
        for(int i1 = 0; i1 < l; i1++)
            if(i + i1 >= j || ac[i + i1] != ac[k + i1])
                return false;

        return true;
    }

    private int readNextToken(char ac[], int i, int j, StrBuilder strbuilder, List list)
    {
label0:
        do
        {
            int i1;
label1:
            {
                if(i < j)
                {
                    i1 = Math.max(getIgnoredMatcher().isMatch(ac, i, i, j), getTrimmerMatcher().isMatch(ac, i, i, j));
                    if(i1 != 0 && getDelimiterMatcher().isMatch(ac, i, i, j) <= 0 && getQuoteMatcher().isMatch(ac, i, i, j) <= 0)
                        break label1;
                }
                if(i >= j)
                {
                    addToken(list, "");
                    return -1;
                }
                break label0;
            }
            i += i1;
        } while(true);
        int k = getDelimiterMatcher().isMatch(ac, i, i, j);
        if(k > 0)
        {
            addToken(list, "");
            return i + k;
        }
        int l = getQuoteMatcher().isMatch(ac, i, i, j);
        if(l > 0)
            return readWithQuotes(ac, i + l, j, strbuilder, list, i, l);
        else
            return readWithQuotes(ac, i, j, strbuilder, list, 0, 0);
    }

    private int readWithQuotes(char ac[], int i, int j, StrBuilder strbuilder, List list, int k, int l)
    {
        strbuilder.clear();
        int i1 = i;
        boolean flag;
        int j1;
        if(l > 0)
            flag = true;
        else
            flag = false;
        j1 = 0;
        while(i1 < j) 
            if(flag)
            {
                if(isQuote(ac, i1, j, k, l))
                {
                    if(isQuote(ac, i1 + l, j, k, l))
                    {
                        strbuilder.append(ac, i1, l);
                        i1 += l * 2;
                        j1 = strbuilder.size();
                    } else
                    {
                        i1 += l;
                        flag = false;
                    }
                } else
                {
                    int k2 = i1 + 1;
                    strbuilder.append(ac[i1]);
                    j1 = strbuilder.size();
                    i1 = k2;
                }
            } else
            {
                int k1 = getDelimiterMatcher().isMatch(ac, i1, i, j);
                if(k1 > 0)
                {
                    addToken(list, strbuilder.substring(0, j1));
                    return i1 + k1;
                }
                if(l > 0 && isQuote(ac, i1, j, k, l))
                {
                    flag = true;
                    i1 += l;
                } else
                {
                    int l1 = getIgnoredMatcher().isMatch(ac, i1, i, j);
                    if(l1 > 0)
                    {
                        i1 += l1;
                    } else
                    {
                        int i2 = getTrimmerMatcher().isMatch(ac, i1, i, j);
                        if(i2 > 0)
                        {
                            strbuilder.append(ac, i1, i2);
                            i1 += i2;
                        } else
                        {
                            int j2 = i1 + 1;
                            strbuilder.append(ac[i1]);
                            j1 = strbuilder.size();
                            i1 = j2;
                        }
                    }
                }
            }
        addToken(list, strbuilder.substring(0, j1));
        return -1;
    }

    public volatile void add(Object obj)
    {
        add((String)obj);
    }

    public void add(String s)
    {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    public Object clone()
    {
        Object obj;
        try
        {
            obj = cloneReset();
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            return null;
        }
        return obj;
    }

    Object cloneReset()
        throws CloneNotSupportedException
    {
        StrTokenizer strtokenizer = (StrTokenizer)super.clone();
        if(strtokenizer.chars != null)
            strtokenizer.chars = (char[])strtokenizer.chars.clone();
        strtokenizer.reset();
        return strtokenizer;
    }

    public String getContent()
    {
        if(chars == null)
            return null;
        else
            return new String(chars);
    }

    public StrMatcher getDelimiterMatcher()
    {
        return delimMatcher;
    }

    public StrMatcher getIgnoredMatcher()
    {
        return ignoredMatcher;
    }

    public StrMatcher getQuoteMatcher()
    {
        return quoteMatcher;
    }

    public String[] getTokenArray()
    {
        checkTokenized();
        return (String[])tokens.clone();
    }

    public List getTokenList()
    {
        checkTokenized();
        ArrayList arraylist = new ArrayList(tokens.length);
        String as[] = tokens;
        int i = as.length;
        for(int j = 0; j < i; j++)
            arraylist.add(as[j]);

        return arraylist;
    }

    public StrMatcher getTrimmerMatcher()
    {
        return trimmerMatcher;
    }

    public boolean hasNext()
    {
        checkTokenized();
        return tokenPos < tokens.length;
    }

    public boolean hasPrevious()
    {
        checkTokenized();
        return tokenPos > 0;
    }

    public boolean isEmptyTokenAsNull()
    {
        return emptyAsNull;
    }

    public boolean isIgnoreEmptyTokens()
    {
        return ignoreEmptyTokens;
    }

    public volatile Object next()
    {
        return next();
    }

    public String next()
    {
        if(hasNext())
        {
            String as[] = tokens;
            int i = tokenPos;
            tokenPos = i + 1;
            return as[i];
        } else
        {
            throw new NoSuchElementException();
        }
    }

    public int nextIndex()
    {
        return tokenPos;
    }

    public String nextToken()
    {
        if(hasNext())
        {
            String as[] = tokens;
            int i = tokenPos;
            tokenPos = i + 1;
            return as[i];
        } else
        {
            return null;
        }
    }

    public volatile Object previous()
    {
        return previous();
    }

    public String previous()
    {
        if(hasPrevious())
        {
            String as[] = tokens;
            int i = -1 + tokenPos;
            tokenPos = i;
            return as[i];
        } else
        {
            throw new NoSuchElementException();
        }
    }

    public int previousIndex()
    {
        return -1 + tokenPos;
    }

    public String previousToken()
    {
        if(hasPrevious())
        {
            String as[] = tokens;
            int i = -1 + tokenPos;
            tokenPos = i;
            return as[i];
        } else
        {
            return null;
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public StrTokenizer reset()
    {
        tokenPos = 0;
        tokens = null;
        return this;
    }

    public StrTokenizer reset(String s)
    {
        reset();
        if(s != null)
        {
            chars = s.toCharArray();
            return this;
        } else
        {
            chars = null;
            return this;
        }
    }

    public StrTokenizer reset(char ac[])
    {
        reset();
        chars = ArrayUtils.clone(ac);
        return this;
    }

    public volatile void set(Object obj)
    {
        set((String)obj);
    }

    public void set(String s)
    {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public StrTokenizer setDelimiterChar(char c)
    {
        return setDelimiterMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setDelimiterMatcher(StrMatcher strmatcher)
    {
        if(strmatcher == null)
        {
            delimMatcher = StrMatcher.noneMatcher();
            return this;
        } else
        {
            delimMatcher = strmatcher;
            return this;
        }
    }

    public StrTokenizer setDelimiterString(String s)
    {
        return setDelimiterMatcher(StrMatcher.stringMatcher(s));
    }

    public StrTokenizer setEmptyTokenAsNull(boolean flag)
    {
        emptyAsNull = flag;
        return this;
    }

    public StrTokenizer setIgnoreEmptyTokens(boolean flag)
    {
        ignoreEmptyTokens = flag;
        return this;
    }

    public StrTokenizer setIgnoredChar(char c)
    {
        return setIgnoredMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setIgnoredMatcher(StrMatcher strmatcher)
    {
        if(strmatcher != null)
            ignoredMatcher = strmatcher;
        return this;
    }

    public StrTokenizer setQuoteChar(char c)
    {
        return setQuoteMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setQuoteMatcher(StrMatcher strmatcher)
    {
        if(strmatcher != null)
            quoteMatcher = strmatcher;
        return this;
    }

    public StrTokenizer setTrimmerMatcher(StrMatcher strmatcher)
    {
        if(strmatcher != null)
            trimmerMatcher = strmatcher;
        return this;
    }

    public int size()
    {
        checkTokenized();
        return tokens.length;
    }

    public String toString()
    {
        if(tokens == null)
            return "StrTokenizer[not tokenized yet]";
        else
            return (new StringBuilder()).append("StrTokenizer").append(getTokenList()).toString();
    }

    protected List tokenize(char ac[], int i, int j)
    {
        Object obj;
        if(ac == null || j == 0)
        {
            obj = Collections.emptyList();
        } else
        {
            StrBuilder strbuilder = new StrBuilder();
            obj = new ArrayList();
            int k = i;
            while(k >= 0 && k < j) 
            {
                k = readNextToken(ac, k, j, strbuilder, ((List) (obj)));
                if(k >= j)
                    addToken(((List) (obj)), "");
            }
        }
        return ((List) (obj));
    }

    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE;
    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;
    private char chars[];
    private StrMatcher delimMatcher;
    private boolean emptyAsNull;
    private boolean ignoreEmptyTokens;
    private StrMatcher ignoredMatcher;
    private StrMatcher quoteMatcher;
    private int tokenPos;
    private String tokens[];
    private StrMatcher trimmerMatcher;

    static 
    {
        CSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        CSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.commaMatcher());
        CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
        TSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        TSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.tabMatcher());
        TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
    }
}
