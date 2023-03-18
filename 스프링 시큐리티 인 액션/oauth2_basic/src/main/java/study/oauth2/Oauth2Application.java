package study.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Oauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2Application.class, args);
	}

}

/**
 * authorizationUri: 이 필드는 OAuth2 공급자의 권한 부여 끝점 URI를 지정합니다.
 * 이 끝점은 사용자가 클라이언트 응용 프로그램을 인증하고 해당 리소스에 액세스할 수 있도록 권한을 부여하도록 리디렉션되는 곳입니다.
 * 권한 부여 코드 부여 흐름 중에 권한 부여 코드를 얻는 데 사용됩니다.
 *
 * issuerUri: 이 필드는 OAuth2 공급자의 발급자 URI를 지정합니다.
 * 발급자는 보호된 리소스에 액세스하는 데 사용되는 액세스 토큰을 발급하고 서명하는 주체입니다.
 * 발급자 URI는 인증 프로세스 중에 OAuth2 제공자가 반환하는 JWT 토큰의 유효성을 검사하는 데 사용됩니다.
 * 일반적으로 토큰 교환 흐름 중에 액세스 토큰을 얻는 데 사용됩니다.
 */