package com.x.y.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

public final class StringUtils {
    private StringUtils() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (decideIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (decideIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (decideIP(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static Boolean decideIP(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    public static String firstCharToLowerCase(String str) {
        char[] chars = new char[]{str.charAt(0)};
        String temp = new String(chars);
        return chars[0] >= 'A' && chars[0] <= 'Z' ? str.replaceFirst(temp, temp.toLowerCase()) : str;
    }

    public static boolean isPicType(String picContentType) {
        return picContentType.toLowerCase().contains("image");
    }

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("(?i)\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}\\b");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static String encryptByMD5(String str) {
        if (isNull(str)) {
            return "";
        } else {
            try {
                str = str + "58&*)_*(^";
                return DigestUtils.md5Hex(str);
            } catch (Exception var2) {
                return str.trim();
            }
        }
    }

    public static String getRandomString(int size) {
        char[] c = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            sb.append(c[Math.abs(random.nextInt() % c.length)]);
        }
        return sb.toString();
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

    public static int getStrlength(String str) {
        if (isNull(str)) {
            return 0;
        } else {
            String anotherString;
            try {
                anotherString = new String(str.getBytes("GBK"), "ISO8859_1");
            } catch (UnsupportedEncodingException var3) {
                return 0;
            }
            return (int) Math.ceil((double) anotherString.length() / 2.0D);
        }
    }

    public static String clearQuot(String ostr) {
        if (isNull(ostr)) {
            return "";
        } else {
            ostr = ostr.replaceAll("\"", "").replaceAll("'", "");
            return ostr;
        }
    }

    public static String replaceChar(int istr, String strchar) {
        return String.valueOf(istr).replaceAll(strchar, "");
    }

    public static String[] parseTag(String tag) {
        if (isNull(tag)) {
            return null;
        } else {
            String[] tags = null;
            tag = tag.trim().replaceAll("，", ",");
            tag = tag.replaceAll("｜", ",");
            tag = tag.replaceAll("\t", ",");
            tag = tag.replaceAll(" ", ",");
            tag = tag.replaceAll("、", ",");
            tag = tag.replaceAll("　", ",");
            if (tag.contains(",")) {
                tags = tag.split(",");
                return tags;
            } else {
                tags = new String[]{tag};
                return tags;
            }
        }
    }

    public static int twoDateHours(String d1, String d2) {
        try {
            Date date1 = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(d1);
            Date date2 = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(d2);
            long m = (date1.getTime() - date2.getTime()) / 1000L;
            int m2 = Integer.parseInt(String.valueOf(m));
            int nDay = m2 / 86400;
            return (m2 - nDay * 86400) / 3600;
        } catch (Exception var11) {
            var11.printStackTrace();
            return 0;
        }
    }

    public static int twoDateHours(Date date1, Date date2) {
        long m = (date1.getTime() - date2.getTime()) / 1000L;
        int m2 = Integer.parseInt(String.valueOf(m));
        int nDay = m2 / 86400;
        return (m2 - nDay * 86400) / 3600;
    }

    public static boolean isPic(String inputstr) {
        if (isNull(inputstr)) {
            return false;
        } else {
            inputstr = inputstr.toLowerCase();
            return inputstr.indexOf(".gif") > 0 || inputstr.indexOf(".jpg") > 0 || inputstr.indexOf(".jpeg") > 0 || inputstr.indexOf(".bmp") > 0 || inputstr.indexOf(".png") > 0;
        }
    }

    public static String filterHTML(String htmlCode) {
        if (isNull(htmlCode)) {
            return "";
        } else {
            htmlCode = htmlCode.replaceAll("&nbsp;", "");
            String regEx = "<[^>]+>";
            Pattern p = Pattern.compile(regEx, 2);
            Matcher m = p.matcher(htmlCode);
            htmlCode = m.replaceAll("");
            regEx = "[\n\r]";
            p = Pattern.compile(regEx);
            m = p.matcher(htmlCode);
            htmlCode = m.replaceAll("");
            return htmlCode;
        }
    }

    public static String filterHref(String htmlCode) {
        if (isNull(htmlCode)) {
            return "";
        } else {
            String regEx = "<a.*>(.*?)</a>";
            Pattern p = Pattern.compile(regEx, 2);
            Matcher m = p.matcher(htmlCode);
            htmlCode = m.replaceAll("");
            return htmlCode;
        }
    }

    public static String getNumberPot(double input) {
        DecimalFormat dec = new DecimalFormat();
        dec.setMaximumFractionDigits(2);
        return dec.format(input);
    }

    public static boolean isMobileNo(String mobileNo) {
        Pattern pattern = Pattern.compile("^1[3,5,7,8]{1}[0-9]{1}[0-9]{8}$");
        Matcher matcher = pattern.matcher(mobileNo);
        return matcher.find();
    }

    public static String dropCharFromRight(String str, int dropNum) {
        if (str != null && str.length() >= 1 && dropNum >= 1 && str.length() >= dropNum) {
            str = str.substring(0, str.length() - dropNum);
            return str;
        } else {
            return str;
        }
    }

    public static String[] getParameterValues(HttpServletRequest request, String paramName) {
        String[] returnValue = null;
        String[] paramValue = request.getParameterValues(paramName);
        returnValue = paramValue == null ? new String[0] : paramValue;
        return returnValue;
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

    public static String formatNullString(String input) {
        return isNull(input) ? "" : input;
    }

    public static int parseInt(String intStr, int defaultInt) {
        try {
            return Integer.parseInt(intStr);
        } catch (Exception var3) {
            return defaultInt;
        }
    }

    public static int parseInt(String intStr) {
        return parseInt(intStr, 0);
    }

    public static boolean isInt(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String subBefore(String src, String term) {
        if (src != null && term != null) {
            int index = src.indexOf(term);
            return index >= 0 ? src.substring(0, index) : src;
        } else {
            return null;
        }
    }

    public static String subAfter(String src, String term) {
        if (src != null && term != null) {
            int index = src.indexOf(term);
            return index >= 0 ? src.substring(index + term.length()) : src;
        } else {
            return null;
        }
    }

    public static String subLastBefore(String src, String term) {
        if (src != null && term != null) {
            int index = src.lastIndexOf(term);
            return index >= 0 ? src.substring(0, index) : src;
        } else {
            return null;
        }
    }

    public static String subLastAfter(String src, String term) {
        if (src != null && term != null) {
            int index = src.lastIndexOf(term);
            return index >= 0 ? src.substring(index + term.length()) : src;
        } else {
            return null;
        }
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(str.length() - len);
        }
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

    public static String unescape(String src) {
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

    public static String formatXML(String input) {
        if (input == null) {
            return null;
        } else {
            String res = input.replaceAll("&", "&amp;");
            res = res.replaceAll("<", "&lt;");
            res = res.replaceAll(">", "&gt;");
            res = res.replaceAll("'", "&apos;");
            res = res.replaceAll("\"", "&quot;");
            res = res.replaceAll("\n", "<br/>");
            res = res.replaceAll("\r", "");
            return res;
        }
    }

    public static String formatUploadXML(String input) {
        if (input == null) {
            return null;
        } else {
            String res = input.replaceAll("&amp;", "&");
            res = res.replaceAll("&lt;", "<");
            res = res.replaceAll("&gt;", ">");
            res = res.replaceAll("&apos;", "'");
            res = res.replaceAll("&", "&amp;");
            res = res.replaceAll("<", "&lt;");
            res = res.replaceAll(">", "&gt;");
            res = res.replaceAll("'", "&apos;");
            return res;
        }
    }

    public static String formatAndChar(String input) {
        if (input == null) {
            return null;
        } else {
            return input.replaceAll("&amp;", "&");
        }
    }

    public static String formatAndToAmpChar(String input) {
        if (input == null) {
            return null;
        } else {
            return input.replaceAll("&", "&amp;");
        }
    }

    public static String fillStringByArgs(String str, Object... arr) {
        if (arr != null && arr.length != 0) {
            String temp;
            for (Matcher m = Pattern.compile("\\{(\\d)}").matcher(str); m.find(); str = str.replace(m.group(), temp)) {
                Object ob = arr[Integer.parseInt(m.group(1))];
                temp = ob == null ? "NULL" : ob.toString();
            }
            return str;
        } else {
            return str;
        }
    }

    public String filterHtmlTag(String message) {
        if (message == null) {
            return null;
        } else {
            char[] content = new char[message.length()];
            message.getChars(0, message.length(), content, 0);
            StringBuilder result = new StringBuilder(content.length + 50);
            for (char aContent : content) {
                switch (aContent) {
                    case '"':
                        result.append("&quot;");
                        break;
                    case '&':
                        result.append("&amp;");
                        break;
                    case '<':
                        result.append("&lt;");
                        break;
                    case '>':
                        result.append("&gt;");
                        break;
                    default:
                        result.append(aContent);
                }
            }
            return result.toString();
        }
    }

    public static String filterScript(String str) {
        str = Pattern.compile("<script.*?>.*?</script>", 2).matcher(str).replaceAll("");
        return str;
    }
}