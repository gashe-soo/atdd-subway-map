package nextstep.subway.utils;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ResponseUtil {

    public static String ID_추출(ExtractableResponse<Response> response){
        return response.header("Location").split("/")[2];
    }
}
