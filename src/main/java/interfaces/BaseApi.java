package interfaces;

import com.google.gson.Gson;
import dto.RegistrationBodyDto;
import dto.UserDto;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public interface BaseApi {

    String BASE_URL = "https://contactapp-telran-backend.herokuapp.com";

    String Registration_PATH = "/v1/user/registration/usernamepassword";

    String LOGIN_PATH = "/v1/user/login/usernamepassword";

    String GET_ALL_CONTACTS_PATH = "/v1/contacts";

    //Gson GSON = new Gson();

    MediaType JSON = MediaType.get("application/json");

    OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

   RegistrationBodyDto USER_LOGIN = RegistrationBodyDto.builder()
           .username("rom@gmail.com")
           .password("7206@Rom")
           .build();
}