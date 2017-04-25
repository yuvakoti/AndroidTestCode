package developer.androidassignment.network;

public class NetworkResponse {
    private Object responseObject;
    private String responseStatus;
    private String responseString;
    private int statusCode;
    private Object tag;


    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }


    public void setStatusCode(int statuscode) {
        this.statusCode = statuscode;
    }

    public String getResponseString() {
        return this.responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }



    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }


    public void setTag(Object tag) {
        this.tag = tag;
    }
}
