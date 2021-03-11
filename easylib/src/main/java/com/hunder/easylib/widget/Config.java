package com.hunder.easylib.widget;

public class Config {
    public static final int CustomerID = -1513283270;
    public static String REQUEST_HEADER;
    public static boolean isWhiteBoardTest = false;
    public static boolean isWhiteVideoBoardTest = false;
    public static String[] mColor;
    public static final int port = 29987;
    public static final String url = "http://61.54.85.18/v1/httpdns/clouddns?ws_domain=www.chinanetcenter.com&ws_ret_type=json";

    static {
        REQUEST_HEADER = "http://";
        mColor = new String[]{"#000000", "#9B9B9B", "#FFFFFF", "#FF87A3", "#FF515F", "#FF0000", "#E18838", "#AC6B00", "#864706", "#FF7E0B", "#FFD33B", "#FFF52B", "#B3D330", "#88BA44", "#56A648", "#53B1A4", "#68C1FF", "#058CE5", "#0B48FF", "#C1C7FF", "#D25FFA", "#6E3087", "#3D2484", "#142473"};
    }
}
