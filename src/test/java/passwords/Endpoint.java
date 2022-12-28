package passwords;

public class Endpoint {
    public static class Post {
        public static String getSecretPasswordHomework(){return "get_secret_password_homework";}
    }

    public static class Get {
        public static String checkAuthCookie(){return "check_auth_cookie";}
        public static String wikiPage(){return "https://en.wikipedia.org/wiki/List_of_the_most_common_passwords";}
    }
}
