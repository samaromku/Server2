public class User {
    private String id;
    private String name;
    private String password;
    private String role;

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.role = "simpleUser";
    }
}
