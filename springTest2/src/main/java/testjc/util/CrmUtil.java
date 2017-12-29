package testjc.util;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.util.StringUtils;

/*import lifesafety.common.type.CtprvnDataKeyNo;
import lifesafety.common.type.CtprvnDataType;
import lifesafety.common.type.DataKeyNo;
import lifesafety.common.type.DataType;*/

public class CrmUtil extends StringUtils {


	public static int convertInt(Object obj) throws NumberFormatException {
		if( isEmpty(obj) ) {
			return 0;
		}
		
		return Integer.parseInt(obj.toString());
	}
	
	public static int convertInt(Object obj, int defaultInt)  {
		try {
			if( isEmpty(obj) ) {
				return defaultInt;
			}
			return convertInt(obj);
		} catch(NumberFormatException ne) {
			return defaultInt;
		}
	}
	
	public static String convertString(Object obj) {
		if( isEmpty(obj) )
			return "";
		else 
			return obj.toString();
	}
	
	public static String getToday() {
		return getToday("");
	}
	
	public static String getYear() {
		Calendar aCalendar = Calendar.getInstance();
		int year = aCalendar.get(Calendar.YEAR);
		return Integer.toString(year);
	}
	
	public static String getMonth() {
		Calendar aCalendar = Calendar.getInstance();
		int month = aCalendar.get(Calendar.MONTH) + 1;
		return lpad(month, 2);
	}
	
	public static String addMonth(int add) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.add(aCalendar.MONTH, add);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String year = dateFormat.format(aCalendar.getTime()).substring(0,4);
		String month = dateFormat.format(aCalendar.getTime()).substring(4,6);
		return year + month;
	}
	
	public static String lpad(int value, int len) {
		String str = Integer.toString(value);
		
		if( str.length() >= len )
			return str;
		
		int diff = len - str.length();
		for( int i = 0; i < diff  ; i++  ) {
			str = "0" + str;
		}
		return str;
	}
	
    /**
     * ?˜„?¬(?•œêµ?ê¸°ì?) ?‚ ì§œì •ë³´ë?? ?–»?Š”?‹¤.                     
     * ?‘œê¸°ë²•?? yyyy-mm-dd                                  
     * @return  String      yyyymmdd?˜•?ƒœ?˜ ?˜„?¬ ?•œêµ??‹œê°?.   
     */
    public static String getToday(String dateType) {
        Calendar aCalendar = Calendar.getInstance();

        int year = aCalendar.get(Calendar.YEAR);
        int month = aCalendar.get(Calendar.MONTH) + 1;
        int date = aCalendar.get(Calendar.DATE);
        String strDate = Integer.toString(year) +
                ((month<10) ? "0" + Integer.toString(month) : Integer.toString(month)) +
                ((date<10) ? "0" + Integer.toString(date) : Integer.toString(date));
        
        if(!"".equals(dateType)) strDate = convertDate(strDate, "yyyyMMdd", dateType);

        return  strDate;
    }
    
	public static String getTimeStamp() {

		String rtnStr = null;

		// ë¬¸ì?—´ë¡? ë³??™˜?•˜ê¸? ?œ„?•œ ?Œ¨?„´ ?„¤? •(?…„?„-?›”-?¼ ?‹œ:ë¶?:ì´?:ì´?(?? •?´?›„ ì´?))
		String pattern = "yyyyMMddhhmmssSSS";

		try {
			SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
			Timestamp ts = new Timestamp(System.currentTimeMillis());

			rtnStr = sdfCurrent.format(ts.getTime());
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e); // 2011.10.10 ë³´ì•ˆ? ê²? ?›„?†ì¡°ì¹˜
		}

		return rtnStr;
	}

	public static String formatDate(String date) {
		if( date != null ) {
			if( date.length() == 8 ) {
				return date.substring(0,4) + "-" + date.substring(4,6)  + "-" + date.substring(6);
			}
			return date;
		} else {
			return "";
		}
	}
    
    
    public static String convertDate(String sDate, String sTime, String sFormatStr) {
        String dateStr = validChkDate(sDate);
        String timeStr = validChkTime(sTime);
        
        Calendar cal = null;
        cal = Calendar.getInstance() ;
        
        cal.set(Calendar.YEAR        , Integer.parseInt(dateStr.substring(0,4)));
        cal.set(Calendar.MONTH       , Integer.parseInt(dateStr.substring(4,6))-1 );
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6,8)));
        cal.set(Calendar.HOUR_OF_DAY , Integer.parseInt(timeStr.substring(0,2)));
        cal.set(Calendar.MINUTE      , Integer.parseInt(timeStr.substring(2,4)));
        
        SimpleDateFormat sdf = new SimpleDateFormat(sFormatStr,Locale.ENGLISH);
        
        return sdf.format(cal.getTime());
    }   

    /**
     * ?…? ¥?œ ?¼? ë¬¸ì?—´?„ ?™•?¸?•˜ê³? 8?ë¦¬ë¡œ ë¦¬í„´   
     * @param sDate
     * @return
     */
    public static String validChkDate(String dateStr) {
        String _dateStr = dateStr;
        
        if (dateStr == null || !(dateStr.trim().length() == 8 || dateStr.trim().length() == 10)) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
        if (dateStr.length() == 10) {
                _dateStr = removeMinusChar(dateStr);
        }
        return _dateStr;
    }
 
    
    
    /**
     * <p>ê¸°ì? ë¬¸ì?—´?— ?¬?•¨?œ ëª¨ë“  ???ƒ ë¬¸ì(char)ë¥? ? œê±°í•œ?‹¤.</p>
     *
     * <pre>
     * StringUtil.remove(null, *)       = null
     * StringUtil.remove("", *)         = ""
     * StringUtil.remove("queued", 'u') = "qeed"
     * StringUtil.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str  ?…? ¥ë°›ëŠ” ê¸°ì? ë¬¸ì?—´
     * @param remove  ?…? ¥ë°›ëŠ” ë¬¸ì?—´?—?„œ ? œê±°í•  ???ƒ ë¬¸ì?—´
     * @return ? œê±°ë??ƒ ë¬¸ì?—´?´ ? œê±°ëœ ?…? ¥ë¬¸ì?—´. ?…? ¥ë¬¸ì?—´?´ null?¸ ê²½ìš° ì¶œë ¥ë¬¸ì?—´?? null
     */
    public static String remove(String str, char remove) {
    	if( str != null ) {
	        if (isEmpty(str) || str.indexOf(remove) == -1) {
	            return str;
	        }
	        char[] chars = str.toCharArray();
	        int pos = 0;
	        for (int i = 0; i < chars.length; i++) {
	            if (chars[i] != remove) {
	                chars[pos++] = chars[i];
	            }
	        }
	        return new String(chars, 0, pos);
    	} else {
    		return "";
    	}
    }
    
    /**
     * ?…? ¥?œ ?¼? ë¬¸ì?—´?„ ?™•?¸?•˜ê³? 8?ë¦¬ë¡œ ë¦¬í„´   
     * @param sDate
     * @return
     */
    public static String validChkTime(String timeStr) {
    	if( timeStr != null ) {
	        String _timeStr = timeStr;
	        
	        if (_timeStr.length() == 5) {
	                _timeStr = remove(_timeStr,':');
	        }
	        if (_timeStr == null || !(_timeStr.trim().length() == 4)) {
	            throw new IllegalArgumentException("Invalid time format: " + _timeStr);
	        }
	
	        return _timeStr;
    	} else {
    		return "";
    	}
    }
    
    public static String removeMinusChar(String str) {
        return remove(str, '-');
    }
    
    public static String secSSN(String str) {
    	if( str != null ) {
	    	if( str.length() == 13 ) {
	    		return str.substring(0,6) + "-" + str.substring(6,7) + "******";
	    	}
	    	return str;
    	} else {
    		return "";
    	}
    }
    
    public static String arrayToString(Object[] obj ) {
    	String rtn = "";
    	for(Object o : obj ) {
    		if( !isEmpty(rtn) ) 
    			rtn += ",";
    		rtn += o.toString().trim();	
    	}
    	return rtn;
    }
    
    
    public static boolean isNotEmpty(final Object obj) {
		if(null == obj) return false;
		else {
			if(obj instanceof String) return "".equals(obj) ? false : true;
			else if(obj instanceof List) return !((List<?>)obj).isEmpty();
			else if(obj instanceof Map) return !((Map<?,?>)obj).isEmpty();
			else if(obj instanceof Object[]) return 0 == Array.getLength(obj) ? false : true;
			else if(obj instanceof Integer) return !(null == obj);
			else return false;
		}
	}

	public static boolean isEmpty(final Object obj) {
    	return !isNotEmpty(obj);
    }

	public static String getTimeStampString(final String format) {
    	return getTimeStampString(new Date(), format);
    }
	
	public static String getTimeStampString(final Date date) {
		return getTimeStampString(date, "yyyyMMddHHmmss");
	}
    
    public static String getTimeStampString(final Date date, final String format){
    	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
    	return formatter.format(date);
    }
    
    public static String getTimeStampString(String date, final String format) {
    	try {
    	    if(null == date || "".equals(date)) return "";
    	    
    	    Date	d	= null;
    	    		date= replace(date, "-", "");
    	    
    	    switch(date.length()) {
    	    	case 14: break;
    	    	case 12: date += "00";			break;
    	    	case 10: date += "0000";		break;
    	    	case 8:	 date += "000000";		break;
    	    	case 6:	 date += "01000000";	break;
    	    	case 4:	 date += "0101000000";	break;
    	    	default: return "";
    	    }
    	    
    		java.text.SimpleDateFormat tmpFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);

	    	if("".equals(date)) d = new Date();
	    	else {
	    	    tmpFormat.setLenient(true);
	    		d = tmpFormat.parse(date);
	    	}
	    	
	    	return getTimeStampString(d, format);
    	} catch(Exception e) {
    		
    		return "";
    	}
    }
    
    public static boolean displayNewIcon(String date) {
    	
    	date = date.replaceAll("-", "");
    	date = date.substring(0, 8);
    	if( getToday().equals(date) )
    		return true;
    	return false;
    	
    }
    
 /*   public static DataType getDataType(String dataType) {
    	for (DataType type : DataType.values()) {
    		if (type.getName().equals(dataType)) {
    			return type;
    		}
    	}
    	return null;
    }
    
    public static DataKeyNo getDataKeyNo(String dataType) {
    	for (DataKeyNo type : DataKeyNo.values()) {
    		if (type.getName().equals(dataType)) {
    			return type;
    		}
    	}
    	return null;
    }
    
    public static CtprvnDataKeyNo getCtprvnDataKeyNo(String dataType) {
    	for (CtprvnDataKeyNo type : CtprvnDataKeyNo.values()) {
    		if (type.getName().equals(dataType)) {
    			return type;
    		}
    	}
    	return null;
    }
    
    public static CtprvnDataType getCtprvnDataType(String dataType) {
    	for (CtprvnDataType type : CtprvnDataType.values()) {
    		if (type.getName().equals(dataType)) {
    			return type;
    		}
    	}
    	return null;
    }*/
    
    /**
     * ?‚¬?—…? ?“±ë¡ë²ˆ?˜¸ ?¬ë§·ìœ¼ë¡? ë³?ê²½í•œ?‹¤.
     * @param str ?‚¬?—…? ?“±ë¡ë²ˆ?˜¸
     * @return ë³?ê²½ëœ ?‚¬?—…? ?“±ë¡ë²ˆ?˜¸
     */
    public static String convertWrkrRegNo(String str) {
    	if (str.length() < 6) {
    		return str;
    	}
    	String str1 = str.substring(0,3);
    	String str2 = str.substring(3,5);
    	String str3 = str.substring(5);
    	return str1 + "-" + str2 + "-" + str3;
    }
    
    /**
     * ?¸?—ˆê°?ë²ˆí˜¸ ?¬ë§·ìœ¼ë¡? ë³?ê²½í•œ?‹¤.(dash ì¶”ê?)
     * @param str ?¸?—ˆê°?ë²ˆí˜¸
     * @return ë³?ê²½ëœ ?¸?—ˆê°?ë²ˆí˜¸
     */
    public static String convertApvRegNo(String str) {
    	if (str.length() < 7) {
    		return str;
    	}
    	String str1 = str.substring(0, 7);
    	if (str.length() < 9) {
    		return str1 + "-" + str.substring(7);
    	}
    	String str2 = str.substring(7, 9);
    	if (str.length() < 13) {
    		return str1 + "-" + str2 + "-" + str.substring(9);
    	}
    	String str3 = str.substring(9, 13);
   		return str1 + "-" + str2 + "-" + str3 + "-" + str.substring(13);
    }
    
    /**
     * ?“±ë¡ë²ˆ?˜¸(ê°œì¸/ë²•ì¸) ?¬ë§·ìœ¼ë¡? ë³?ê²½í•œ?‹¤.(dash ì¶”ê?)
     * @param str ?“±ë¡ë²ˆ?˜¸
     * @return ë³?ê²½ëœ ?“±ë¡ë²ˆ?˜¸
     */
    public static String convertRegNo(String str) {
    	if (str == null) {
    		return null;
    	}
    	
    	if (!"".equals(str) && str.length() == 13) {
    		return str.substring(0, 6) + "-" + str.substring(6, 13);
    	} else {
    		return "";
    	}
    }
    
    /**
     * ? „?™”ë²ˆí˜¸ ?¬ë§·ìœ¼ë¡? ë³?ê²½í•œ?‹¤. (dash ì¶”ê?)
     * @param phoneNo ? „?™”ë²ˆí˜¸
     * @return ë³?ê²½ëœ ? „?™”ë²ˆí˜¸
     */
    public static String phoneFormat(String phoneNo) {
    	
    	if (phoneNo == null) {
    		return "";
    	}
    	if (phoneNo.length() == 0 || phoneNo.length() < 9) {
    		return phoneNo;
    	}
    	String strTel = phoneNo;
    	
    	
    	String[] strDDD = {"02", 
    						"031", "032", "033", 
    						"041", "042", "043", "044", 
    						"051", "052", "053", "054", "055", 
    						"061", "062", "063", "064",
    						"010", "011", "012", "013", "015",
    						"016", "017", "018", "019", "070"};
    	

    	if (strTel.substring(0, 2).equals(strDDD[0])) {
    		strTel = strTel.substring(0, 2) 
    				+ "-" + strTel.substring(2, strTel.length() - 4)
    				+ "-" + strTel.substring(strTel.length() - 4, strTel.length());
    		return strTel;
    	}
    	
    	for (int i = 1; i < strDDD.length; i++) {
   			if (strTel.substring(0, 3).equals(strDDD[i])) {
   				strTel = strTel.substring(0, 3)
   						+ "-" + strTel.substring(3, strTel.length() - 4)
   						+ "-" + strTel.substring(strTel.length() - 4, strTel.length());
   				return strTel;
   			}
   		}
    	
    	return phoneNo;
    }
}
