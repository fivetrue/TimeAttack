package com.fivetrue.timeattack.ga;

public class GoogleAnalyticsConstants {
	static public class FIELD{
		static public String VIEW_NAME = "VIEW_NAME";
	}
	
	static public class ACTION{
		static public String TAG = "동작";
		static public String GO_HOME = "홈 이동";
		static public String GO_SEARCH = "검색 이동";
		static public String GO_MAP = "지도 이동";
		static public String GO_SETTING = "설정 이동";
		
		static public String DO_SEARCH = "검색 실행";
		
		static public String DO_CURRENT_LOCATION_SEARCH = "현재위치 검색 실행";
		static public String DO_FIND_NEAR_BY_SEARCH = "주변 검색 실행";
		static public String DO_FIND_DIRECTION = "길 찾기 실행";
	}
	
	static public class ACCOUNT{
		static public String TAG = "계정";
		static public String LOGIN_KAKAO = "카카오 로그인";
		static public String LOGOUT_KAKAO = "카카오 로그아웃";
	}
	
	static public class SETTING{
		static public String TAG = "설정";
		static public String ROTATION_MAP = "지도 회전";
		static public String ZOOM_BUTTON = "줌 버튼 노출";
		static public String PLACE_RADIUS = "주변 검색 범위";
		
		static public String MAP_NORMAL = "일반지도 설정";
		static public String MAP_SATELLITE = "위성지도 설정";
		static public String MAP_HYBRID = "복합지도 설정";
		static public String MAP_TERRAIN = "지형지도 설정";
	}
	
}
