package com.x.y.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;

public final class StringUtils {
    private StringUtils() {
    }

    public static String chop(String orignalString, int length, String chopedString) {
        Float float1 = 0.5F;
        if (isNull(orignalString)) {
            return "";
        } else {
            orignalString = orignalString.replaceAll(" ", " ");
            StringBuilder buffer = new StringBuilder();
            float count = 0.0F;
            int stringLength = orignalString.length();
            int i;
            char c;
            for (i = 0; i < stringLength; ++i) {
                c = orignalString.charAt(i);
                if (c < 255) {
                    count += float1;
                } else {
                    ++count;
                }
            }
            if (Math.round(count) <= length) {
                return orignalString;
            } else {
                count = 0.0F;
                for (i = 0; i < stringLength; ++i) {
                    c = orignalString.charAt(i);
                    if (c < 255) {
                        count += float1;
                    } else {
                        ++count;
                    }
                    if (Math.round(count) > length) {
                        break;
                    }
                    buffer.append(c);
                }
                return buffer.toString() + chopedString;
            }
        }
    }

    public static String getParameter(HttpServletRequest request, String paraName, String defaultValue) {
        String param = unescape(request.getParameter(paraName));
        return isNull(param) ? defaultValue : param.trim();
    }

    public static boolean isNull(String checkStr) {
        return checkStr == null || checkStr.trim().length() == 0 || "null".equalsIgnoreCase(checkStr.trim());
    }

    public static boolean isNotNull(String checkStr) {
        return !isNull(checkStr);
    }

    public static String encodeURL(String src) {
        String result = null;
        try {
            if (!isNull(src)) {
                result = URLEncoder.encode(src, "UTF-8");
            } else {
                result = src;
            }
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }
        return result;
    }

    public static String decodeURL(String src) {
        String result = null;
        try {
            if (!isNull(src)) {
                result = URLDecoder.decode(src, "UTF-8");
            } else {
                result = src;
            }
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }
        return result;
    }

    private static String unescape(String src) {
        if (isNull(src)) {
            return "";
        } else {
            try {
                StringBuilder tmp = new StringBuilder();
                tmp.ensureCapacity(src.length());
                int lastPos = 0;
                while (lastPos < src.length()) {
                    int pos = src.indexOf("%", lastPos);
                    if (pos == lastPos) {
                        char ch;
                        if (src.charAt(pos + 1) == 'u') {
                            ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                            tmp.append(ch);
                            lastPos = pos + 6;
                        } else {
                            ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                            tmp.append(ch);
                            lastPos = pos + 3;
                        }
                    } else if (pos == -1) {
                        tmp.append(src.substring(lastPos));
                        lastPos = src.length();
                    } else {
                        tmp.append(src, lastPos, pos);
                        lastPos = pos;
                    }
                }
                return tmp.toString();
            } catch (Exception var5) {
                return src;
            }
        }
    }
}