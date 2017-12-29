package testjc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtil
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
	
	/**
	 * org.apache.struts2.util.StrutsUtil
	 * 
	 * @param obj
	 * @return
	 */
	public static String htmlEncode( Object obj ) {
		if( obj == null ) {
			return null;
		}

		return htmlEncode( obj.toString(), true, false );
	}

	// public String urlEncode(String s) {
	// public String urlDecode(String s) {

	/**
	 * <pre>
	 * ?«?κ°μ 2?λ¦? λ¬Έμ?΄λ‘? λ°κΎΌ?€.
	 * 1 ?? 01, 12 ? 12, ...
	 * </pre>
	 */
	public static String toStrZero( int number ) {
		return toStrZero( number, 2 );
	}

	/**
	 * <pre>
	 * int?? &lt;code&gt;number&lt;/code&gt;λ₯? &lt;code&gt;length&lt;/code&gt; κΈΈμ΄ λ§νΌ? λ¬Έμ?΄λ‘? λ³????€.
	 * length λ³΄λ€ ??? κΈΈμ΄? ?«??Ό? &quot;0&quot;? λΆν?μ£ΌκΈ° ??΄ ?¬?©.
	 * </pre>
	 * 
	 * <pre>
	 * toStrZero( 123, 5 ); // return &quot;00123&quot;;
	 * </pre>
	 * 
	 * @param number
	 *          ?¬λ§·ν  κ°?
	 * @param length
	 *          0? λΆν λ¬Έμ?΄? μ΄? κΈΈμ΄
	 * @return
	 */
	public static String toStrZero( int number, int length ) {
		String num = String.valueOf( number );
		String result = num;

		for( int i = num.length(); i < length; i++ ) {
			result = "0" + result;
		}

		return result;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String toStrDate( String date ) {
		return toStrDate( date, "%Y.%M.%D" );
	}

	/**
	 * ?¨? ???Ό? ???₯?? κ²½μ° DATE/TIMESTAMPκ°? ?? CHAR(8)? ?°κΈ°λ‘ κ²°μ . VIEW ?? μΆλ ₯? ? ?¬λ§·μ λ³?κ²½νκΈ? ?½?λ‘? ?¬?©??€.
	 * 
	 * @param date
	 *          ?«?λ‘λ§ κ΅¬μ±? ? μ§? λ¬Έμ?΄( yymmdd ? yyyymmdd ??λ§? κ°??₯)
	 * @param format
	 *          ?¬λ§·ν?( %Y:??4?λ¦?, %y:??2?λ¦?, %M, %D, %m, %d )
	 * @return
	 */
	public static String toStrDate( String date, String format ) {

		// 
		String value, yyyy, yy, mm, dd;

		value = date.replaceAll( "[^0-9]", "" );

		// ???λΆ??° 2??© ?λ₯΄μ. // λ©μ?λ₯? μ°ΎκΈ΄ κ·Έλ κ³? ?°?  ?΄??λ‘? ?κ³? ?μ€μ λ³?κ²½ν?€.
		int pos;
		pos = value.length();
		
		if( pos < 6 )
			return "";
		
		dd = value.substring( pos - 2, pos );
		pos -= 2;
		mm = value.substring( pos - 2, pos );
		pos -= 2;
		yy = value.substring( 0, pos );

		if( yy.length() == 2 ) {
			if( Integer.parseInt( yy ) >= 50 )
				yyyy = "19" + yy;
			else
				yyyy = "20" + yy;
		}
		else {
			yyyy = yy;
			yy = yy.substring( 1 );
		}

		format = format.replaceFirst( "\\%Y", yyyy );
		format = format.replaceFirst( "\\%y", yy );
		format = format.replaceFirst( "\\%M", mm );
		format = format.replaceFirst( "\\%m", String.valueOf( Integer.parseInt( mm ) ) );
		format = format.replaceFirst( "\\%D", dd );
		format = format.replaceFirst( "\\%d", String.valueOf( Integer.parseInt( dd ) ) );

		return format;

	}

	public static String toStrDate( Date date, String format ) {
		// ?΄κ±? ?μ€μ ?κ°λ΄? λ°κΎΈ?.
		return new SimpleDateFormat( format ).format( date );
	}
	
	public static String getCurDateStr(String formatString) {
        SimpleDateFormat formatter  = new SimpleDateFormat(formatString);
        Date currentTime= new Date();
        return formatter.format(currentTime);
    }
	

	public static int toInt( String string ) {
		return toInt( string, 0 );
	}

	public static int toInt( Object string, int defaultInt ) {
		return toInt( noNull( string ), defaultInt );
	}

	public static int toInt( Object object ) {
		return toInt( noNull( object ), 0 );
	}

	/**
	 * Integer.parseInt() κ°? κ·?μ°???, ??±??Έ? κΈ°λ³Έκ°μΌλ‘? μ²λ¦¬
	 * 
	 * @param string
	 * @return
	 */
	public static int toInt( String string, int defaultInt ) {
		/*
		 * if( string == null || string.equals("") ) return 0; return Integer.parseInt( string );
		 */
		try {
			return Integer.parseInt( string );
		}
		catch( NumberFormatException e ) {
			return defaultInt;
		}
	}

	public static int toInt( long aLong ) {
		return (int) aLong;
	}

	public static long toLong( int anInt ) {
		return (long) anInt;
	}

	public static long toLong( String aLong ) {
		if( aLong == null ) {
			return 0;
		}

		return Long.parseLong( aLong );
	}

	public static long toLong( Object aLong ) {
		if( aLong == null || aLong.toString().equals( "" ) ) {
			return 0;
		}
		return Long.parseLong( aLong.toString() );
	}
	public static double toDouble( int anInt ) {
		return (long) anInt;
	}
	
	public static double toDouble( String aDouble ) {
		if( aDouble == null ) {
			return 0;
		}
		
		return Double.parseDouble( aDouble );
	}
	
	public static double toDouble( Object aDouble ) {
		if( aDouble == null || aDouble.toString().equals( "" ) ) {
			return 0;
		}
		return Double.parseDouble( aDouble.toString() );
	}
	
	public static double toDouble(String str, double defaultValue)
    {
        try
        {
            return Double.parseDouble(str);
        }
        catch(Exception e)
        {
            System.err.print("Double Value Error : " + str);
        }
        return defaultValue;
    }

	public static String toString( long aLong ) {
		return Long.toString( aLong );
	}

	public static String toString( int anInt ) {
		return Integer.toString( anInt );
	}

	/**
	 * Escape html entity characters and high characters (eg "curvy" Word quotes). Note this method can also be used to encode XML.
	 * 
	 * @param s
	 *          the String to escape.
	 * @param encodeSpecialChars
	 *          if true high characters will be encode other wise not.
	 * @return the escaped string
	 */
	public final static String htmlEncode( String s, boolean nl2br, boolean encodeSpecialChars ) {
		s = noNull( s );

		StringBuffer str = new StringBuffer();

		for( int j = 0; j < s.length(); j++ ) {
			char c = s.charAt( j );

			// encode standard ASCII characters into HTML entities where needed
			if( c < '\200' ) {
				switch( c ) {
				case '"':
					str.append( "&quot;" );
					break;

				case '&':
					str.append( "&amp;" );
					break;

				case '<':
					str.append( "&lt;" );
					break;

				case '>':
					str.append( "&gt;" );
					break;

				case '\n':
					str.append( "<br/>" );
					break;

				default:
					str.append( c );
				}
			}
			// encode 'ugly' characters (ie Word "curvy" quotes etc)
			else if( encodeSpecialChars && ( c < '\377' ) ) {
				String hexChars = "0123456789ABCDEF";
				int a = c % 16;
				int b = ( c - a ) / 16;
				String hex = "" + hexChars.charAt( b ) + hexChars.charAt( a );
				str.append( "&#x" + hex + ";" );
			}
			// add other characters back in - to handle charactersets
			// other than ascii
			else {
				str.append( c );
			}
		}

		return str.toString();
	}

	/**
	 * Return <code>string</code>, or <code>defaultString</code> if <code>string</code> is <code>null</code> or <code>""</code>. Never returns <code>null</code>.
	 * 
	 * <p>
	 * Examples:
	 * </p>
	 * 
	 * <pre>
	 * // prints &quot;hello&quot;
	 * String s=null;
	 * System.out.println(TextUtils.noNull(s,&quot;hello&quot;);
	 * 
	 * // prints &quot;hello&quot;
	 * s=&quot;&quot;;
	 * System.out.println(TextUtils.noNull(s,&quot;hello&quot;);
	 * 
	 * // prints &quot;world&quot;
	 * s=&quot;world&quot;;
	 * System.out.println(TextUtils.noNull(s, &quot;hello&quot;);
	 * </pre>
	 * 
	 * @param string
	 *          the String to check.
	 * @param defaultString
	 *          The default string to return if <code>string</code> is <code>null</code> or <code>""</code>
	 * @return <code>string</code> if <code>string</code> is non-empty, and <code>defaultString</code> otherwise
	 * @see #stringSet(java.lang.String)
	 */
	public final static String noNull( String string, String defaultString ) {
		return ( stringSet( string ) ) ? string : defaultString;
	}

	/**
	 * Return <code>string</code>, or <code>""</code> if <code>string</code> is <code>null</code>. Never returns <code>null</code>.
	 * <p>
	 * Examples:
	 * </p>
	 * 
	 * <pre>
	 * // prints 0
	 * String s = null;
	 * System.out.println( TextUtils.noNull( s ).length() );
	 * 
	 * // prints 1
	 * s = &quot;a&quot;;
	 * System.out.println( TextUtils.noNull( s ).length() );
	 * </pre>
	 * 
	 * @param string
	 *          the String to check
	 * @return a valid (non-null) string reference
	 */
	public final static String noNull( Object string ) {
		return noNull( string, "" );
	}

	/**
	 * Check whether <code>string</code> has been set to something other than <code>""</code> or <code>null</code>.
	 * 
	 * @param string
	 *          the <code>String</code> to check
	 * @return a boolean indicating whether the string was non-empty (and non-null)
	 */
	public final static boolean stringSet( String string ) {
		return ( string != null ) && !"".equals( string );
	}

	public final static Long noNull( Long vLong, Long defaultLong ) {
		return ( longSet( vLong ) ) ? vLong : defaultLong;
	}

	public final static Long noNull( Long vLong ) {
		return noNull( vLong, 0L );
	}

	public final static boolean longSet( Long vLong ) {
		return ( vLong != null ) && ( vLong != 0L );
	}

	public final static String noNull( Object string, String defaultString ) {
		if( string == null )
			return defaultString;

		return noNull( string.toString(), defaultString );
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty(&quot;&quot;)        = true
	 * StringUtils.isEmpty(&quot; &quot;)       = false
	 * StringUtils.isEmpty(&quot;bob&quot;)     = false
	 * StringUtils.isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty( String str ) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank(&quot;&quot;)        = true
	 * StringUtils.isBlank(&quot; &quot;)       = true
	 * StringUtils.isBlank(&quot;bob&quot;)     = false
	 * StringUtils.isBlank(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isBlank( String str ) {
		int strLen;
		if( str == null || ( strLen = str.length() ) == 0 ) {
			return true;
		}
		for( int i = 0; i < strLen; i++ ) {
			if( ( Character.isWhitespace( str.charAt( i ) ) == false ) ) {
				return false;
			}
		}
		return true;
	}

	public static boolean isBlank( Object object ) {

		if( object == null )
			return true;
		else
			return isBlank( object.toString() );

	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("") will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isAlpha(null)   = false
	 * StringUtils.isAlpha(&quot;&quot;)     = true
	 * StringUtils.isAlpha(&quot;  &quot;)   = false
	 * StringUtils.isAlpha(&quot;abc&quot;)  = true
	 * StringUtils.isAlpha(&quot;ab2c&quot;) = false
	 * StringUtils.isAlpha(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if only contains letters, and is non-null
	 */
	public static boolean isAlpha( String str ) {
		if( str == null ) {
			return false;
		}
		int sz = str.length();
		for( int i = 0; i < sz; i++ ) {
			if( Character.isLetter( str.charAt( i ) ) == false ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode digits. A decimal point is not a unicode digit and returns false.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("") will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric(&quot;&quot;)     = true
	 * StringUtils.isNumeric(&quot;  &quot;)   = false
	 * StringUtils.isNumeric(&quot;123&quot;)  = true
	 * StringUtils.isNumeric(&quot;12 3&quot;) = false
	 * StringUtils.isNumeric(&quot;ab2c&quot;) = false
	 * StringUtils.isNumeric(&quot;12-3&quot;) = false
	 * StringUtils.isNumeric(&quot;12.3&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if only contains digits, and is non-null
	 */
	public static boolean isNumeric( String str ) {
		if( str == null ) {
			return false;
		}
		int sz = str.length();
		for( int i = 0; i < sz; i++ ) {
			if( Character.isDigit( str.charAt( i ) ) == false ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only whitespace.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("") will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isWhitespace(null)   = false
	 * StringUtils.isWhitespace(&quot;&quot;)     = true
	 * StringUtils.isWhitespace(&quot;  &quot;)   = true
	 * StringUtils.isWhitespace(&quot;abc&quot;)  = false
	 * StringUtils.isWhitespace(&quot;ab2c&quot;) = false
	 * StringUtils.isWhitespace(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if only contains whitespace, and is non-null
	 * @since 2.0
	 */
	public static boolean isWhitespace( String str ) {
		if( str == null ) {
			return false;
		}
		int sz = str.length();
		for( int i = 0; i < sz; i++ ) {
			if( ( Character.isWhitespace( str.charAt( i ) ) == false ) ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * λ¬Έμ?΄ ?λ₯΄κΈ°
	 * 
	 * @param src
	 * @param length
	 * @return
	 */
	public static String leftKor( String src, int length ) {
		return strCut( src, "", length, 0, true, true );
	}

	/**
	 * λ¬Έμ?΄ ?λ₯΄κΈ°
	 * 
	 * @param src
	 * @param length
	 * @param ignoreTag
	 * @param addDot
	 * @return
	 * @see strCut(String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot)
	 */
	public static String leftKor( String src, int length, boolean ignoreTag, boolean addDot ) {
		return strCut( src, "", length, 0, ignoreTag, addDot );
	}

	/**
	 * λ°μ΄?Έ ?¨? λ¬Έμ?΄ ?λ₯΄κΈ° {@link http://loea.tistory.com/entry/javajsp-??-λ°μ΄?Έ?¨?λ‘?-λ¬Έμ?΄-?λ₯΄κΈ°?κΈ?κΉ¨μ§??΄}
	 * 
	 * @param szText
	 *          ??? λ¬Έμ?΄
	 * @param szKey
	 *          ???μΉλ‘ ?  ?€??
	 * @param nLength
	 *          ?λ₯? κΈΈμ΄
	 * @param nPrev
	 *          ?€?? ?μΉμ? ?Όλ§λ ?΄? κΈΈμ΄λ§νΌ ?¬?¨?  κ²μΈκ°?
	 * @param isNotag
	 *          ?κ·Έλ?? ??¨κ²μΈκ°?
	 * @param isAdddot
	 *          κΈ΄λ¬Έ??΄?Ό κ²½μ° "..."? μΆκ??  κ²μΈκ°?
	 * @return
	 */
	public static String strCut( String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot ) {

		String r_val = szText;
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int nLengthPrev = 0;
		Pattern p = Pattern.compile( "<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE ); // ?κ·Έμ κ±? ?¨?΄

		if( isNotag ) {
			r_val = p.matcher( r_val ).replaceAll( "" );
		} // ?κ·? ? κ±?
		r_val = r_val.replaceAll( "&amp;", "&" );
		r_val = r_val.replaceAll( "(!/|\r|\n|&nbsp;)", "" ); // κ³΅λ°±? κ±?

		try {
			byte[] bytes = r_val.getBytes( "UTF-8" ); // λ°μ΄?Έλ‘? λ³΄κ?
			if( szKey != null && !szKey.equals( "" ) ) {
				nLengthPrev = ( r_val.indexOf( szKey ) == -1 ) ? 0 : r_val.indexOf( szKey ); // ?Ό?¨ ?μΉμ°Ύκ³?
				nLengthPrev = r_val.substring( 0, nLengthPrev ).getBytes( "MS949" ).length; // ?μΉκΉμ§?κΈΈμ΄λ₯? byteλ‘? ?€? κ΅¬ν?€
				nLengthPrev = ( nLengthPrev - nPrev >= 0 ) ? nLengthPrev - nPrev : 0; // μ’? ?λΆ?λΆλ??° κ°?? Έ?€?λ‘ν?€.
			}

			// xλΆ??° yκΈΈμ΄λ§νΌ ??Ό?Έ?€. ?κΈ??κΉ¨μ?κ²?.
			int j = 0;

			if( nLengthPrev > 0 )
				while( j < bytes.length ) {
					if( ( bytes[j] & 0x80 ) != 0 ) {
						oF += 2;
						rF += 3;
						if( oF + 2 > nLengthPrev ) {
							break;
						}
						j += 3;
					}
					else {
						if( oF + 1 > nLengthPrev ) {
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}

			j = rF;

			while( j < bytes.length ) {
				if( ( bytes[j] & 0x80 ) != 0 ) {
					if( oL + 2 > nLength ) {
						break;
					}
					oL += 2;
					rL += 3;
					j += 3;
				}
				else {
					if( oL + 1 > nLength ) {
						break;
					}
					++oL;
					++rL;
					++j;
				}
			}

			if( bytes.length < rL )
				rL = bytes.length;
			
			r_val = new String( bytes, rF, rL, "UTF-8" ); // charset ?΅?

			if( isAdddot && rF + rL + 3 <= bytes.length ) {
				r_val += "...";
			} // ...? λΆμΌμ§?λ§μ? ?΅?
		}
		catch( UnsupportedEncodingException e ) {
			LOGGER.warn("strcut error");
		}

		return r_val;
	}

	public static boolean isImage( Object fileNm ) {

		String[] IMG_EXT = new String[] { ".jpg", ".jpeg", ".png", ".gif", ".bmp" };

		String imageNm = StringUtil.noNull( fileNm );

		if( imageNm.equals( "" ) )
			return false;

		for( int i = 0; i < IMG_EXT.length; i++ )
			if( imageNm.toLowerCase().endsWith( IMG_EXT[i] ) )
				return true;

		return false;
	}

/*	*//**
	 * 
	 * @param data
	 * @return
	 *//*
	private static String toHex( byte[] data ) {
		if( data == null ) return null;
		
		StringBuffer buf = new StringBuffer();
		for( int i = 0; i < data.length; i++ ) {
			int halfbyte = ( data[i] >>> 4 ) & 0x0F;
			int two_halfs = 0;
			do {
				if( ( 0 <= halfbyte ) && ( halfbyte <= 9 ) )
					buf.append( (char) ( '0' + halfbyte ) );
				else
					buf.append( (char) ( 'a' + ( halfbyte - 10 ) ) );
				halfbyte = data[i] & 0x0F;
			} while( two_halfs++ < 1 );
		}
		return buf.toString();
	}*/

	/**
	 * ?°?΄?°λ² μ΄?€?? ?£?? VARCHAR ???? ? μ§λ?? ?¬λ§·νκΈ?<br/>
	 * 8?λ¦?, 12?λ¦?, 14?λ¦¬μ ???΄ ?:λΆ?:μ΄? κΉμ? λ¦¬ν΄
	 * @param stringDt
	 * @return yyyy.MM.dd hh:mm:ss ??
	 */
	public static String convertDt( Object stringDt ) {
		if( stringDt == null )
			return "";
		
		String result = stringDt.toString();
		
		result = result.replaceAll( "\\D", "" );
		
		if( result.length() < 8 )
			return "";
		
		// ? μ§?+?κ°κΉμ§? ???₯? κ²½μ°κ°? ??Όλ©?? ?°?΄?°λ² μ΄?€? ?κ°μ ???₯?μ§? ??.
		
		// ??? κ²½μ° format? μΆκ?λ‘? ?μ§? ? ?κ²? μ§?? . yyyy? mm? dd?Ό ?±
		if( result.length() == 8 )
			return result.substring( 0, 4 ) + "." + result.substring( 4, 6 ) + "." + result.substring( 6, 8 );
		else if( result.length() == 12 )
			return result.substring( 0, 4 ) + "." + result.substring( 4, 6 ) + "." + result.substring( 6, 8 ) + " " + result.substring( 8, 10 ) + ":" + result.substring( 10, 12 );
		else
			return result.substring( 0, 4 ) + "." + result.substring( 4, 6 ) + "." + result.substring( 6, 8 ) + " " + result.substring( 8, 10 ) + ":" + result.substring( 10, 12 ) + ":" + result.substring( 12, 14 );
			
	}
	
	/**
	 * 
	 * @param stringDt
	 * @return hh:mm:ss ??
	 */
	public static String convertTm( Object stringDt ) {
		if( stringDt == null )
			return "";
		
		String result = stringDt.toString();
		
		result = result.replaceAll( "\\D", "" );
		
		if( result.length() < 4 )
			return "";
		
		// ??? κ²½μ° format? μΆκ?λ‘? ?μ§? ? ?κ²? μ§?? .
		
		if( result.length() == 4 )
			return result.substring( 0, 2 ) + ":" + result.substring( 2, 4 );
		else if( result.length() == 6 )
			return result.substring( 0, 2 ) + ":" + result.substring( 2, 4 ) + ":" + result.substring( 4, 6 );
		else
			return "";
			
	}
	
	
	/**
	 * ?°?΄?°λ² μ΄?€?? ?£?? VARCHAR ???? ?κ°? ?¬λ§·νκΈ?<br/>
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static Integer dateDiff( Object date1, Object date2, String format ) {
		// String ?Ό κ²½μ°? μ²λ¦¬ μΆκ??  κ²?!
		if( date1 == null || date2 == null || format == null )
			return null;
		
		return dateDiff( (Date) date1, (Date) date2, format );
	}
	
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static Integer dateDiff( Date date1, Date date2, String format ) {
		if( date1 == null || date2 == null || format == null )
			return null;
		
		long time1 = ((Date) date1).getTime();
		long time2 = ((Date) date2).getTime();
		long diff = time1 - time2;
		
		if( format.equals( "d")) {
			return Math.round( ((float) diff) / 1000 / 60 / 60 / 24 );
		}
		else if( format.equals( "h")) {
			return Math.round( ((float) diff) / 1000 / 60 / 60 );
		}
		else if( format.equals( "mi")) {
			return Math.round( ((float) diff) / 1000 / 60 );
		}
		else if( format.equals( "s")) {
			return Math.round( ((float) diff) / 1000 );
		}
		else
			return null;
		
	}
	
	/**
	 * ? ?λ²νΈ ?± ?°?΄?°λ² μ΄?€? ??΄?(-)?΄ ?€?΄κ°? κ°μ ??  ?Ό ?±?? μͺΌκ° ?£? κ²½μ°<br/>
	 *
	 * @param item	?λ³? λ§?
	 * @param itemNm ??±?  ?€
	 * @return ??±? λ§?
	 */
	public static HashMap<String, Object> splitDash( HashMap<String, Object> item, String itemNm ) {
		if( item == null || itemNm == null ) return null;
		
		// κΈ°λ³Έκ°μ ??΄?(-)?Όλ‘? ?κ³?, ? ?°? ?? ₯λ°μ ? ?κ²? ??.
		HashMap<String, Object> myMap = new HashMap<String, Object>();
		// ?λ³Έλ ?΄λ¦¬μ.
		myMap.put( itemNm, item.get( itemNm ) );
		
		if( item.containsKey( itemNm )) {
			String[] telArray = noNull(item.get( itemNm )).split( "-" );
			for( int i = 0; i < telArray.length; i++ ) {
				myMap.put( itemNm + (i + 1), telArray[i] );
			}
		}
		
		return myMap;
	}
	
	/**
	 * splitDash ? λ°λ? λ²μ 
	 * @param item
	 * @param itemNm
	 * @return
	 * @deprecated	λ³΄λ₯. ?¬?©λ°©λ²?΄ λ³λ‘ ?μ’λ€.
	 */
	public static String mergeDash( HashMap<String, Object> item, String itemNm ) {
		if( item == null || itemNm == null ) return null;
		
		// ? μ§λ?? 1, 2, 3?Όλ‘? ?? Έ?Ό??? κ³ λ?Όν΄λ³΄μ.
		String myStr = "";
		
		// μ΅λ? 10κ°? λ£¨ν
		for( int i = 1; i < 10; i++ ) {
			if( !item.containsKey( itemNm + i ) )
				break;
			myStr += noNull(item.get( itemNm + i )) ;
		}
		
		// λ§μ?λ§μ λΆμ? ??΄? ? κ±?
		if( myStr.length() > 0 )
			myStr = myStr.substring( 0, myStr.length() - 1 );
		
		return myStr;
	}
	
	
	/**
	 * JSTL ??κΈ? ??κ°?? Έ?€κΈ?.
	 * @param item
	 * @param itemNm
	 * @return
	 */
	public static Float divideFloat( Float upVal, Float downVal ) {
		if(downVal==0 || downVal==null || upVal==null) 
			return (float)0;
		return upVal/downVal;
	}
	
	/**
	 * encode XOR
	 * @param val
	 * @return
	 */
	public static String encodeXor( String val ) {
		if( val == null ) return null;
		
		int xorKey = 3;	// ?΄λ§?
		
		String result = "";
		
		for( int i = 0; i < val.length(); i++ ) {
			result += Character.toString( (char) (val.charAt( i ) ^ xorKey) );
		}

		return result;
	}
	
	/**
	 * Base64 ?Έμ½λ©
	 * 
	 * @param input
	 * @return
	 * @deprecated ?¬?©?μ§?λ§? κ²?
	 */
	public static String base64Encode( String input ) {
		if( input == null ) return null;
		
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		byte[] b1 = input.getBytes();
		String result = encoder.encode( b1 );

		return result;

	}



	/**
	 * Base64 ?μ½λ©
	 * 
	 * @param input
	 * @return 
	 * @throws IOException
	 * @deprecated ?¬?©?μ§?λ§? κ²?
	 */
	public static String base64Decode( String input ) throws IOException {
		if( input == null ) return null;
		
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		byte[] b1 = decoder.decodeBuffer( input );
		String result = new String( b1 );

		return result;

	}
	
	public static Date dateAdd( Date date1, String flag, int value ) {
		
		if( date1 == null)
			return null;
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime( date1 );
		
		if( flag.equalsIgnoreCase( "d" ) )
			cal1.add( Calendar.DATE, value );
		else if( flag.equalsIgnoreCase( "h" ))
			cal1.add( Calendar.HOUR_OF_DAY, value );
		
		return cal1.getTime();
		
	}
	
	public static String replaceAll( String input, String regex, String replacement ) {
		if( !(input instanceof String) )
			return null;
		
		return input.replaceAll( regex, replacement );
	}
	
	public static boolean isMatch(CharSequence input, String regex) {
    return Pattern.compile(regex).matcher(input).matches();
  }
	
	/**
	 * ?λ°μ€?¬λ¦½νΈ? escape κ΅¬ν
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		if( src == null ) return null;
		
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * ?λ°μ€?¬λ¦½νΈ? unescape κ΅¬ν
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		if( src == null ) return null;
		
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	/**
	 * ??€λ²νΈ μΆμΆ?κΈ?
	 * @param src
	 * @return
	 */
	public static String getAuthNumber(int length)
	{
	     Random rnd = new Random();
	     String numTemp[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	     String auth = "";

	     for(int i=0; i<length; i++)
	     {
	    	 	int num = rnd.nextInt(10);
	    	 	if( num > Integer.MAX_VALUE ) {
	    	 		return "";
	    	 	}
	           auth = auth+numTemp[num];
	      }

	      return auth;
	}

	public static String todayText()
	{
		String day = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return day;
	}
	/**
	 * ??Ό κ°?? Έ?€κΈ?
	 * @param src
	 * @param length
	 * @return
	 */
	public static String getDay( String someday) {
		someday = someday.replace("-", "");
		someday = someday.replace(".", "");
		if(someday == null || (someday.length() != 8) ){
			return "";
		}
		String []weeks2 = {"?Ό","?","?","?","λͺ?","κΈ?","? "};
		Calendar c2= Calendar.getInstance();

		c2.set(Integer.parseInt(someday.substring(0,4))
				,Integer.parseInt(someday.substring(4,6))-1
				,Integer.parseInt(someday.substring(6,8)) );

		return weeks2[c2.get(Calendar.DAY_OF_WEEK)-1];
	}
	
	public static String getDay( Object yyyy, Object mm, Object dd) {
		if( yyyy == null || mm == null || dd == null ) return null;
		
		String y = yyyy.toString();
		String m = mm.toString();
		String d = dd.toString();
		
		if(m.length() <2)
			m = "0"+m;
		if(d.length() <2)
			d = "0"+d;
		String ymd = y+m+d;
		return getDay(ymd);
		
	}
	// λ©°μΉ ?? ? ? κ΅¬ν?€.
	public static String getNextDay( int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,day);
		String curYear = StringUtil.toString(cal.get(Calendar.YEAR));
		String nowMonth = StringUtil.toStrZero(cal.get(Calendar.MONTH)+1);
		String nowDay = StringUtil.toStrZero(cal.get(Calendar.DATE));
		return curYear+nowMonth+nowDay;
		
	}
	
	//?΄?Ή??? λ§μ?λ§? ? μ§λ?? κ΅¬ν?€.
	public static String getLastDayOfMonth(String yearMonth){
		if( yearMonth == null ) return null;
		  String year = yearMonth.substring(0,4);
		  String month = yearMonth.substring(4,6);
		  
		  int _year = Integer.parseInt(year);
		  int _month = Integer.parseInt(month);
		  
		  Calendar calendar = Calendar.getInstance();
		  calendar.set(_year, (_month-1), 1); //??? 0λΆ??° ??  
		  String lastDay = String.valueOf(calendar.getActualMaximum(Calendar.DATE));
		  System.out.println("lastDay of present month  : " + lastDay);
		  
		  return lastDay;
		 }

	/**
	 * κ΅¬λΆ?λ‘? κ΅¬μ±? λ¬Έμ?΄? λ¬Έμλ°°μ΄λ‘? λ³????€.
	 * 
	 * @param str λ¬Έμ?΄
	 * @param delimiter κ΅¬λΆ?
	 */
	public static String[] toStringArray(String str, String delimiter) {
		if( str == null || delimiter == null ) return null;
        String[] result = null;
        result =  str.split(delimiter);
		return result;
	}

	/**
     * λ¬Έμ?΄ ?? λ¬Έμλ₯? μ±μ΄?€
     * 
     * @param s
     * @param chr
     *            null?΄λ©? "0"?Όλ‘? λ³?κ²?
     * @param len
     * @return
     */
    public static String leftPad(String s, int len, String chr) {
        if (s == null || "".equals(s))
            return "";
        if (chr == null || "".equals(chr))
            chr = "0";
        if (s.length() >= len)
            return s.substring(0, len);

        for (int i = s.length(); i < len; i++) {
            s = chr + s;
        }
        return s;
    }

    /**
     * μ£Όμ΄μ§? λ¬Έμλ‘? κΈΈμ΄λ§νΌ μ±μ΄ ? ?? €μ€??€.
     * 
     * @param i ? ?
     * @param chr μ±μΈ λ¬Έμ?΄
     *            null?΄λ©? "0"?Όλ‘? λ³?κ²?
     * @param len μ±μΈ κ°μ
     * @return
     */
    public static String leftPad(int i, int len, String chr) {
        return leftPad(i + "", len, chr);
    }

    /**
     * μ£Όμ΄μ§? λ¬Έμλ‘? κΈΈμ΄λ§νΌ "0"?Όλ‘? μ±μ΄ ? ?? €μ€??€.
     * 
     * @param s
     * @param len μ±μΈ κ°μ
     * @return
     */
    public static String leftPad(String s, int len) {
        return leftPad(s, len, null);
    }

    /**
     * μ£Όμ΄μ§? λ¬Έμλ‘? κΈΈμ΄λ§νΌ "0"?Όλ‘? μ±μ΄ ? ?? €μ€??€.
     * 
     * @param i
     * @param len μ±μΈ κ°μ
     * @return
     */
    public static String leftPad(int i, int len) {
        return leftPad(i + "", len, null);
    }
    
    public static String clobToString(Clob clob) throws SQLException, IOException {
    	if (clob == null) {
    		return "";
    	}
    	String str = "";
    	
    	StringBuilder sb = new StringBuilder();
    	BufferedReader br = new BufferedReader(clob.getCharacterStream());
    	
    	while ((str = br.readLine()) != null) {
    		sb.append(str);
    	}
    	return sb.toString();
    }

}
