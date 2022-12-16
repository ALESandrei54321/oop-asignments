package input;

import java.util.Objects;

public class UsersInput {
    @Override
    public String toString() {
        return "UsersInput{" +
                "name='" + name + '\'' +
                '}';
    }

    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    private int tokens;
    private int numFreeMovies;



    public UsersInput(UsersInput user) {
        tokens = 0;
        numFreeMovies = 15;
        name = user.getName();
        password = user.getPassword();
        accountType = user.getAccountType();
        country = user.getCountry();
        balance = user.getBalance();
    }

    public UsersInput() {
        tokens = 0;
        numFreeMovies = 15;
    }

    public boolean equals(UsersInput usersInput) {
        return Objects.equals(this.getName(), usersInput.getName()) && Objects.equals(this.getPassword(), usersInput.getPassword());
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getNumFreeMovies() {
        return numFreeMovies;
    }

    public void setNumFreeMovies(int numFreeMovies) {
        this.numFreeMovies = numFreeMovies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
