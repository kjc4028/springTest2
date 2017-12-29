package testjc.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class ListHelper {

	public static String  PAGE_PARAM_NAME = "curPage";
	
	private int totalCount;
	private int curPage;
	private String curUrl;
	private String urlParam;
	private int totalPage 	= 0;
	
	
	private List list;
	
	private int startNum 		= 1;
	private int endNum 			= 10;
	private int cntPerPage 		= 10;
	
	private Map<String, Object> paramMap;
	
	public ListHelper() {
		this.paramMap = new HashMap();
	}
	
	public ListHelper(HttpServletRequest request) {	
		initListVO(request, cntPerPage);
	}

	public ListHelper(HttpServletRequest request, int cntPerPage) {
		if(  paramMap == null )
			paramMap = new HashMap();
		initListVO(request, cntPerPage);
	}
	
	private void initListVO(HttpServletRequest request, int cntPerPage) {
		if( request != null ) {
			if(  paramMap == null )
				paramMap = new HashMap();
		
			this.curPage = CrmUtil.convertInt(StringUtil.noNull(request.getParameter(PAGE_PARAM_NAME), ""), 1 );
			this.cntPerPage = CrmUtil.convertInt(cntPerPage, 1);
			
			setPage();
			
			String url = request.getRequestURI();
			setCurUrl(url);
			
			StringBuffer paramBuf = new StringBuffer();
			Enumeration eParam = request.getParameterNames();
			while (eParam.hasMoreElements()) {
				String pName = (String)eParam.nextElement();
				String pValue = request.getParameter(pName);
				if( !ListHelper.PAGE_PARAM_NAME.equals(pName)) {	
					if( paramBuf.length() > 0 )
						paramBuf.append("&amp;");
					paramBuf.append(pName + "=" + pValue);
					
					paramMap.put(pName, pValue);
				}
			}
			setUrlParam(paramBuf.toString());
		}
	}

	private void setPage() {
		this.startNum = ((this.curPage-1) * this.cntPerPage ) ;
		this.endNum = this.startNum + (this.cntPerPage);
		
//		System.out.println( this.curPage + "|" + this.startNum + "|" + this.endNum );
		
		paramMap.put("startNum", 		startNum);
		paramMap.put("endNum", 			endNum);
		// paramMap.put("startNumMysql", 	startNum-1);
		paramMap.put("cntPerPage", 		cntPerPage);
		paramMap.put("curPage", 		curPage);		
	}
	
	public int getTotalPage() {
		if( this.totalPage == 0 ) {
			if ( this.totalCount % this.cntPerPage == 0) {
				this.totalPage = (int) ( this.totalCount / this.cntPerPage);
			} else {
				this.totalPage = (int) ( this.totalCount / this.cntPerPage) + 1;
			}
		}
		return this.totalPage;
	}

	
	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}
	
	public void setCurUrl(String curUrl) {
		this.curUrl = curUrl;
	}
	
	public String getCurUrl() {
		return this.curUrl;
	}
	
	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}
	
	public String getUrlParam() {
		return this.urlParam;
	}
	
	public int getStartNo() {
		return this.totalCount - ( cntPerPage *  (curPage - 1) );
	}
	
	
	public List<Map<String, Object>> getList() {
		return this.list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setList(List list) {
//		this.list = list;
		//?‹œ??–´ì½”ë”© ê²°ê³¼?— ?”°?¼ ë³´ì•ˆê°•í™” ?œ„?•´ ?†Œ?Š¤ ?ˆ˜? •
		this.list = new ArrayList();
		if( list != null && list.size() > 0 ) {
			for( int i = 0; i < list.size(); i++ ) {
				this.list.add(list.get(i));
			}
		}
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getTotalCount() { 
		return this.totalCount;
	}
	
	public int getCntPerPage() {
		return this.cntPerPage;
	}
	
	public Map<String, Object> getParamMap() {
		return this.paramMap;
	}
	
	public int getCurPage() {
		return this.curPage;
	}
	
	public void setParamValue(String key, Object value) {
		if( key != null ) {
			if( CrmUtil.isEmpty(value) ) {
				this.paramMap.put(key, null);
			}
			
			if( value instanceof Integer) {
				this.paramMap.put(key, Integer.parseInt(value.toString()));
			} else if( value instanceof Double) {
				this.paramMap.put(key, Double.parseDouble(value.toString()));
			} else if( value instanceof String ){
				this.paramMap.put(key, value.toString());
			} else {
				this.paramMap.put(key, value);
			}
		}
	}
}
