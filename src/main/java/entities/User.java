package entities;

public class User {
    private int id;
    private String login;
    private String password;
    private String FIO;
    private String role;
    private String telephone;
    private String email;
    private UserRole userRole;
    public static final String MANAGER_ROLE = "managerRole";
    public static final String USER_ROLE = "userRole";
    public static final String ADMIN_ROLE = "adminRole";
    public static final String GUEST_ROLE = "guestRole";

    public User() {
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String password){
        this.login = name;
        this.password = password;
        this.role = GUEST_ROLE;
    }

    public User(int id, String login, String password, String FIO, String role, String telephone, String email, UserRole userRole){
        this.id = id;
        this.login = login;
        this.password = password;
        this.FIO = FIO;
        this.role = role;
        this.telephone = telephone;
        this.email = email;
        this.userRole = userRole;
    }

    public User(int id, String login, String FIO, String telephone, String email) {
        this.id = id;
        this.login = login;
        this.FIO = FIO;
        this.telephone = telephone;
        this.email = email;
    }
}
