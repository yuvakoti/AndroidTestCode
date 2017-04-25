/**
 * Copyright (C) 2015 BillionApps
 * <p>
 * All rights reserved.
 */
package developer.androidassignment.network;


public class HttpAdapter {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_UPDATE = "UPDATE";
    public static final String METHOD_PUT = "PUT";
    public static final String IP_ADDRESS = "http://sampleIP/MobileApp/";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_APPLICATION_URL_ENCODED = "application/x-www-form-urlencoded";
    public static final String PUSHNOTIFICATIONS = IP_ADDRESS+"AndroidPushnotification.php";
    public static final String REGISTRATION = IP_ADDRESS+"registration.php";
    public static final String LOGIN = IP_ADDRESS+"login.php";


    public static void login(NetworkOperationListener listener, String tag, String username, String password) {

        NetworkOperation operation = new NetworkOperation(listener, tag);
        operation.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        operation.execute(LOGIN+"?email_id="+username+"&password="+password, METHOD_GET, "");
    }

    public static void registration(NetworkOperationListener listener, String tag, String username, String password,
                                    String phoneno, String name, String email, String regId) {

        NetworkOperation operation = new NetworkOperation(listener, tag);
        operation.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        operation.execute(REGISTRATION+"?username="+username+"&password="+password+"&phoneno="+phoneno+"" +
                "&name="+name+"&email="+email+"&regId="+regId, METHOD_GET, "");
    }

    public static void pushNotification(NetworkOperationListener listener, String tag, String message ) {

        NetworkOperation operation = new NetworkOperation(listener, tag);
        operation.setContentType(CONTENT_TYPE_APPLICATION_JSON);
        operation.execute(PUSHNOTIFICATIONS+"?&message="+message.replace(" ","%20") , METHOD_GET, "");
    }


}

