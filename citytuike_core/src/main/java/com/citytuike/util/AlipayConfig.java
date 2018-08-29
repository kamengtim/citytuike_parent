package com.citytuike.util;

public class AlipayConfig {
    // 商户appid
    public static String APPID = "2016091800537142";
    public static String UID = "2088102176129450";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCsQ7fNM9aKUs4Ledz2T6Nn1/UbhNJCTVUHZnkHGGiphUCT2yMezshEWk2vkCQ37oHGRcxRUg5Vz93VWNclvtmNrGaOsfx2wvIXOiGgwKSzjK2/7yAEFNc8m0Zk6xmngKRdrNURTC+NVFHXv12hi/earFBhn2gDUIOLEvFNwcIexy5QyfWf+nW1ommupfqGpB5cTfi+tU76WVpPWtbKYmJMhPID99hdcTJxzmCN3ltl3kgjfMSgNJIHOu5kdfHOyM1vi+bSzfesup4LaGP2zUqyD0Bl7QeyjSozDjHJAZRtgAtmBaPbvS2gELbkLeSwRWjM0bFnVtKMZ0JsAsEGBElPAgMBAAECggEAWm0JtLv7aIzi//8uUFWprz8buSBVxtwVQFc4PNNBTYtXRMYJf4iIc+j7UqdVe87FbLEC+cUbJUyCZBXIilXOmaHgDBO+NRFMR4kKUfoN+vpj0pbRtZLDEVto7lzDELxki4/IINZfN88FTz1zBvQUO6ssXrJwxRykkIKXDHwjTEfg+mFgSHPVt0WJg9QT77b8o968QKc4+s0krP7M4tVZNEai1rZdKOt+6b1Qbicxl4modUIyEOY+tbWqbM5fSkFo+zwwqU9kzA1XYx1vPjuAs74FqrWIxvWUBhyMNw2kvmx3KP9wPCGm4m1bjUoI+4ODF6kXnW0srQVkpiMIB4MZIQKBgQDkNDobPyPFgwq7AvKsDSTqa9RYmCqbksmowKCmfBIrNjKZQ9J8VCo8fj9G2W9bazMtN805Ip7UWvlK6PX5zPaSbwbdlCiN5LTKPy0pGih5/1GTNN1p/7wSxNyjouZ4EFB4mn4LuIw3tHv+0O8dexRk9umgeS9khyWnr6gvYlmrGQKBgQDBPzTiU7YItm4u3Ii6eyRa9LEMZeq6S40EwGzQSNHf1PFFvDFqY33/sMNWhSdPpEOcHKU8cIfE0SXwy5u9TfEaay0nU6FsfoAb6qzyeWJuRMQgDVVNJhjCkhoF5jcAtg6n1+hiGdxMpF1a37lOgtyFP4dc29ecvIIjkn2d5hSMpwKBgQDRb4lAhlMbKygG+C6lbq7JokrRQF4iR7Zh9N+7y7SVCfmoJdGgn1HgEPGb4WRM33apckQbwsVIGXIfqOxO5SgKvnDrnNUXXKX7071rsoEhbgx88JCgfr8p7picaZ3g5QAln4w8z5OIcFE4TKKXSOUvwFgsGew/87TAT/sn+bhXyQKBgQClcaOpENoL44O5gv1cCgk6Bbt+TdMD46K7qrJ+L5ne88QMW+5CkyNQQNk7vYvGV2+6u5uLfxv9mMHOwcJ3876zS4bA4IyXAkZToDqo7NskZKM0fbXF8xPHoKW+3zW/2h35/JLrShCdXSq/nkJbwRViztzo1x5lBc60/bml8VEuiQKBgQCBO8+hJxqT/113apK/z8SqlMHAyhMapl3bvoPXvBgi49lNABwXCKhYzr4kDai1wd9lfm/6lL/iIeq6UjEMiw+/4au4senJ3vZdT5nu4rp1c7TkSBuRbVp/b8KGRljEalcRsmwfr8T8YftbmPeL7VlxyW4LAaP2tkvmELSVAMRAGA==";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://citytuike.ngrok.xiaomiqiu.cn/payback";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://citytuike.ngrok.xiaomiqiu.cn";
    // 请求网关地址
//    public static String URL = "https://openapi.alipay.com/gateway.do";
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
