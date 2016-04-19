package gl.com.ggmusic.network;

/**
 * 用于存储Http返回的数据、错误码等
 */
public class HttpResponse {
    /**
     * HttpCode值，比如404
     */
    private int responseCode;
    /**
     * 对于code值的描述
     */
    private String responseMessage;
    /**
     * 如果code等于200，获取字符串
     */
    private String responseContent = "";

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", responseContent='" + responseContent + '\'' +
                '}';
    }
}
    